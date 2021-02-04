package qtc.project.pos.activity;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragmentActivity;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.R;
import qtc.project.pos.api.account.login.LoginRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewInterface;
import qtc.project.pos.ui.views.activity.login.ActivityLoginView;
import qtc.project.pos.ui.views.activity.login.ActivityLoginViewCallback;
import qtc.project.pos.ui.views.activity.login.ActivityLoginViewInterface;

//public class LoginActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        LinearLayout btnLogin = findViewById(R.id.btnLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//            }
//        });
//    }
//}

public class LoginActivity extends BaseFragmentActivity<ActivityLoginViewInterface, BaseMainActionbarViewInterface, BaseParameters> implements ActivityLoginViewCallback {

    LoginActivity activity;

    @Override
    protected void initialize(Bundle savedInstanceState) {
        activity = LoginActivity.this;
        view.initialize(activity,this);

        if (AppProvider.getPreferences().getUserModel() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }else {
            //checkUpdateAppVersion();
        }
    }

    public void checkUpdateAppVersion() {
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;
        new CheckAppVersionAsync(currentVersion, LoginActivity.this).execute();
    }

    private void showAlertUpdateAppVersion() {
        String title = "Thông báo cập nhật!!!";
        String message = "Ứng dụng có bản cập nhật mới, vui lòng cập nhật để sử dụng.";
        showConfirmAlert(title, message, kAlertDialog -> {
            kAlertDialog.dismiss();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(intent);
        }, null, KAlertDialog.WARNING_TYPE);
    }

    public class CheckAppVersionAsync extends AsyncTask<String, String, JSONObject> {

        private String latestVersion;
        private String currentVersion;
        private Context context;

        public CheckAppVersionAsync(String currentVersion, Context context) {
            this.currentVersion = currentVersion;
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            try {
                latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=vi")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();
                //compareVersion(latestVersion, currentVersion);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    showAlertUpdateDialog();
                }
            }
            super.onPostExecute(jsonObject);
        }

        public void showAlertUpdateDialog() {

            showAlertUpdateAppVersion();
        }
    }

    @Override
    protected ActivityLoginViewInterface getViewInstance() {
        return new ActivityLoginView();
    }

    @Override
    protected BaseMainActionbarViewInterface getActionbarInstance() {
        return null;
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.activityLogin;
    }

    @Override
    public void onClickLogin(String username, String password) {
        requestLogin(username, password);
    }

    private void requestLogin(String username, String password) {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
            return;
        }
        showProgress(getString(R.string.loading));
        LoginRequest.ApiParams params = new LoginRequest.ApiParams();
        params.id_code = username;
        params.detect = "employee_login";
        params.password = password;

        AppProvider.getApiManagement().call(LoginRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<EmployeeModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<EmployeeModel> result) {
                dismissProgress();

                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {

                    EmployeeModel userModel = result.getData()[0];
                    if (userModel.getLevel().equals("2")) {
                        //luu trang thai login.
                        AppProvider.getPreferences().saveStatusLogin(true);
                        //goi firebase
                        FirebaseMessaging.getInstance().subscribeToTopic("pos_notifycation_employee_" + userModel.getId());
                        FirebaseMessaging.getInstance().subscribeToTopic("pos_notifycation_app_admin");
                        if (result.getData() != null && result.getData().length > 0) {
                            AppProvider.getPreferences().saveUserModel(userModel);
                        }
                        if (activity != null) {
                            activity.goToHome();
                        }
                    } else {
                        Toast.makeText(activity, "Bạn không có quyền!!!", Toast.LENGTH_SHORT).show();
                        finish();
                    }


                } else {
                    if (!TextUtils.isEmpty(result.getMessage())) {
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    }
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {

            }

            @Override
            public void onFail(ApiRequest.RequestError error) {

            }
        });
    }

    private void goToHome() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }
}