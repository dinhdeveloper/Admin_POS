package qtc.project.pos.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

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
        view.initialize(this);
        activity = LoginActivity.this;
        if (AppProvider.getPreferences().getUserModel()!=null){
            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            finish();
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
                    if (userModel.getLevel().equals("2")){
                        //luu trang thai login.
                        AppProvider.getPreferences().saveStatusLogin(true);
                        if (result.getData() != null && result.getData().length > 0) {
                            AppProvider.getPreferences().saveUserModel(userModel);
                        }
                        if (activity != null) {
                            activity.goToHome();
                        }
                    }else {
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