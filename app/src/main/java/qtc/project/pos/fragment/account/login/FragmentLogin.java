package qtc.project.pos.fragment.account.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import b.laixuantam.myaarlibrary.helper.MyLog;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
import qtc.project.pos.api.account.login.LoginRequest;
import qtc.project.pos.api.account.register.RequestRegister;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.event.FragmentRegisterBackEvent;
import qtc.project.pos.event.RequestLoginWithGoogleEvent;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.UserRegisterModel;
import qtc.project.pos.model.UserResponseModel;
import qtc.project.pos.ui.views.fragment.account.login.LoginView;
import qtc.project.pos.ui.views.fragment.account.login.LoginViewCallback;
import qtc.project.pos.ui.views.fragment.account.login.LoginViewInterface;

//public class FragmentLogin extends BaseFragment<LoginViewInterface, BaseParameters> implements LoginViewCallback {
//
//    private HomeActivityBackup activity;
//
//    private CallbackManager callbackManager;
//
//    @Override
//    protected void initialize() {
//        view.initialize(this);
//        activity = (HomeActivityBackup) getActivity();
//        KeyboardUtils.setupUI(Objects.requireNonNull(getView()), getActivity());
//
//        activity.FullScreencall();
//
//        clearAccessTokenFacebook();
//        callbackManager = CallbackManager.Factory.create();
//        view.setUpFacebookLoginButton(this, callbackManager);
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public void clearAccessTokenFacebook() {
//        if (AccessToken.getCurrentAccessToken() != null) {
//            LoginManager.getInstance().logOut();
//            AccessToken.setCurrentAccessToken(null);
//        }
//    }
//
//    @Override
//    public void loginFacebookSuccess(AccessToken accessToken) {
//        showProgress();
//
//        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                //                Log.i("LoginActivity", response.toString());
//                // Get facebook data from login
//                getFacebookData(object);
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, address"); // Parámetros que pedimos a facebook
//        request.setParameters(parameters);
//        request.executeAsync();
//    }
//
//    private void getFacebookData(JSONObject object) {
//        String TAG = "FacebookData ";
//
//        try {
//            String id = object.getString("id").toString();
//            AppProvider.getPreferences().saveFacebookId(id);
//
//            MyLog.e(TAG, "id_fb" + id);
//
//            String email = "";
//            String profile_pic_link = "";
//            try {
//                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
//                MyLog.e(TAG, "profile_pic " + profile_pic + "");
//                profile_pic_link = profile_pic.toString();
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            String firstName = "";
//            String lastName = "";
//
//            if (object.has("first_name")) {
//                MyLog.e(TAG, "first_name " + object.getString("first_name"));
//                firstName = object.getString("first_name");
//                AppProvider.getPreferences().saveUserFirstName(object.getString("first_name"));
//            }
//            if (object.has("last_name")) {
//                lastName = object.getString("last_name");
//                MyLog.e(TAG, "last_name " + object.getString("last_name"));
//                AppProvider.getPreferences().saveUserLastName(object.getString("last_name"));
//            }
//
//            String userName = firstName + " " + lastName;
//
//            AppProvider.getPreferences().saveUsername(userName);
//
//
//            if (object.has("email")) {
//                email = object.getString("email");
//                MyLog.e(TAG, "email " + email);
//                AppProvider.getPreferences().saveUserEmail(email);
//
//            }
//            if (object.has("gender")) {
//                MyLog.e(TAG, "gender " + object.getString("gender"));
//                AppProvider.getPreferences().saveUserGender(object.getString("gender"));
//            }
//            if (object.has("birthday")) {
//                MyLog.e(TAG, "birthday " + object.getString("birthday"));
//                AppProvider.getPreferences().saveUserBirthday(object.getString("birthday"));
//            }
//
//            String address = "";
//            if (object.has("address")) {
//                address = object.getString("address");
//                MyLog.e(TAG, "address " + object.getString("address"));
//                AppProvider.getPreferences().saveUserGender(object.getString("address"));
//            }
//
//            UserRegisterModel userRegisterModel = new UserRegisterModel();
//            userRegisterModel.setUserEmail(email);
//            userRegisterModel.setUserFirstName(firstName);
//            userRegisterModel.setUserLastName(lastName);
//            userRegisterModel.setUser_social_id(id);
//            userRegisterModel.setType_login("fb");
//            userRegisterModel.setImg_social_link(profile_pic_link);
//            userRegisterModel.setUserAddress(address);
//
//            onSignUp(userRegisterModel);
//
//            dismissProgress();
//
//        } catch (JSONException e) {
//            MyLog.e("FacebookData", "Error parsing JSON");
//            dismissProgress();
//        }
//    }
//
//    @Override
//    protected LoginViewInterface getViewInstance() {
//        return new LoginView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//
//    @Override
//    public void onClickForgotPassword() {
//        if (activity != null)
//            activity.changeToFragmentForgotPassword();
//    }
//
//    @Override
//    public void onClickShowRegister() {
//        if (activity != null) {
//            activity.changeToFragmentRegister();
//
//            handler.postDelayed(() -> view.hideRootViewLogin(), 700);
//        }
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onBackRegister(FragmentRegisterBackEvent event) {
//        if (view != null) {
//            activity.hideBottomMenuBar();
//            view.showRootViewLogin();
//        }
//    }
//
//    @Override
//    public void onClickBackHeader() {
//        showToast("onClickBackHeader");
////        if (activity != null) {
////            activity.checkBack();
////            activity.changeToFragmentDashboard();
////            activity.setPositionBottomMenu(0);
////        }
//    }
//
//    @Override
//    public void onClickLogin(String phone, String password, boolean isAdminLogin) {
//        requestLogin(phone, password, isAdminLogin);
//    }
//
//    private void requestLogin(String phone, String password, boolean isAdminLogin) {
//
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
//            return;
//        }
//
//        showProgress(getString(R.string.loading));
//
//        LoginRequest.ApiParams params = new LoginRequest.ApiParams();
//        params.phone_number = phone;
//        if (isAdminLogin) {
//            params.detect = "admin_login";
//            params.username = phone;
//        } else
//            params.detect = "customer_login";
//        params.password = password;
//
//        String deviceImei = AppProvider.getPreferences().getDeviceImei();
//
//        if (!TextUtils.isEmpty(deviceImei)) {
//            params.device_imei = deviceImei;
//        } else {
//            String deviceUniqueIdentifier = String.valueOf(System.currentTimeMillis());
//
//            AppProvider.getPreferences().saveDeviceImei(deviceUniqueIdentifier);
//            params.device_imei = deviceUniqueIdentifier;
//        }
//
//        AppProvider.getApiManagement().call(LoginRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<UserResponseModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<UserResponseModel> result) {
//
//                dismissProgress();
//
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//
//                    AppProvider.getPreferences().saveStatusLogin(true);
//                    if (result.getData() != null && result.getData().length > 0) {
//                        UserResponseModel userModel = result.getData()[0];
//                        AppProvider.getPreferences().saveUserModel(userModel);
//
////                        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
////                        String tokenSave = AppProvider.getPreferences().getToken();
////                        String topic = "user" + userModel.getId();
////                        FirebaseMessaging.getInstance().subscribeToTopic(topic);
////
////                        AppProvider.getPreferences().saveToken(refreshedToken);
////                        Log.e("MyFirebaseIIDService", "Refreshed token FCM: " + refreshedToken);
//
////                        String deviceImei = AppProvider.getPreferences().getDeviceImei();
////                        String topicCheckAuth = "channel_" + deviceImei;
////                        FirebaseMessaging.getInstance().subscribeToTopic(topicCheckAuth);
////
//////                        requestUpdateDeviceToken(refreshedToken, userModel.getId());
////
////                        if (userModel.getAccount_level().equalsIgnoreCase("admin")) {
////                            String notifycation_nino_admin = "nino_notifycation_admin" + userModel.getIDManager();
////                            FirebaseMessaging.getInstance().subscribeToTopic(notifycation_nino_admin);
////                        } else if (userModel.getAccount_level().equalsIgnoreCase("employee")) {
////                            String notifycation_nino_employee = "nino_notifycation_employee_" + userModel.getIDManager();
////
////                            MyLog.e("MyApplication", "subscribeToTopic: " + notifycation_nino_employee);
////
////                            FirebaseMessaging.getInstance().subscribeToTopic(notifycation_nino_employee);
////
////                            FirebaseMessaging.getInstance().subscribeToTopic("update_role_permission");
////                            FirebaseMessaging.getInstance().subscribeToTopic("update_role_permission_" + userModel.getIDManager());
////                        } else {
////                            String notifycation_529 = "nino_notifycation";
////
////                            if (userModel != null) {
////                                String notifycation_529_user = "nino_notifycation_user_" + userModel.getId();
////                                FirebaseMessaging.getInstance().subscribeToTopic(notifycation_529_user);
////                            }
////
////                            FirebaseMessaging.getInstance().subscribeToTopic(notifycation_529);
////                        }
//                    }
//
//                    showAlert("Đăng nhập thành công!", KAlertDialog.SUCCESS_TYPE);
//
//                    if (activity != null) {
//                        if (!isAdminLogin) {
//                            activity.changeToFragmentProfileManager();
//                            activity.showBottomMenuBar();
//                            activity.setPositionBottomMenu(0);
//                        } else {
//                            activity.changeToFragmentProfileManager();
//                        }
//                    }
//
//                } else {
//                    if (!TextUtils.isEmpty(result.getMessage())) {
//                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
//                    }
//                }
//
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                dismissProgress();
//                showAlert(getString(R.string.login_error), KAlertDialog.ERROR_TYPE);
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                dismissProgress();
//                showAlert(getString(R.string.login_error), KAlertDialog.ERROR_TYPE);
//            }
//        });
//
//    }
//
//    private void requestUpdateDeviceToken(String token, String userId) {
//
////        RequestUpdateDiviceImei.ApiParams params = new RequestUpdateDiviceImei.ApiParams();
////
////        params.fcm_token = token;
////        params.id_user = userId;
////
////        AppProvider.getApiManagement().call(RequestUpdateDiviceImei.class, params, null);
//
//
//    }
//
//    @Override
//    public void onLoginWithGoogle() {
//        if (activity != null)
//            activity.signInGoogle();
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onRequestLoginWithGoogleEvent(RequestLoginWithGoogleEvent event) {
//        String userEmail = AppProvider.getPreferences().getUserEmail();
//        String userGgId = AppProvider.getPreferences().getUserGGID();
//        String userFullname = AppProvider.getPreferences().getUsername();
//        String userImageGGLink = AppProvider.getPreferences().getUserImage();
//
//        String firstname = AppProvider.getPreferences().getUserFirstName();
//        String lastname = AppProvider.getPreferences().getUserLastName();
//
//
//        UserRegisterModel userRegisterModel = new UserRegisterModel();
//        userRegisterModel.setUserEmail(userEmail);
//        userRegisterModel.setUserFirstName(firstname);
//        userRegisterModel.setUserLastName(lastname);
//        userRegisterModel.setUser_social_id(userGgId);
//        userRegisterModel.setType_login("gg");
//        userRegisterModel.setImg_social_link(userImageGGLink);
//
//        onSignUp(userRegisterModel);
//
//
//    }
//
//
//    public void onSignUp(UserRegisterModel userRegisterModel) {
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_internet_connection));
//            return;
//        }
//        RequestRegister.ApiParams params = new RequestRegister.ApiParams();
//
//        params.phone_number = userRegisterModel.getUserPhone();
//        params.password = userRegisterModel.getUserPassword();
//        params.last_name = userRegisterModel.getUserLastName();
//        params.first_name = userRegisterModel.getUserFirstName();
//
//        params.address_personal = userRegisterModel.getUserAddress();
//
//        if (!TextUtils.isEmpty(userRegisterModel.getUserEmail()))
//            params.email = userRegisterModel.getUserEmail();
//        if (!TextUtils.isEmpty(userRegisterModel.getType_login())) {
//            params.type_login = userRegisterModel.getType_login();
//            params.user_social_id = userRegisterModel.getUser_social_id();
//            params.img_social_link = userRegisterModel.getImg_social_link();
//        }
//
//        AppProvider.getApiManagement().call(RequestRegister.class, params, new ApiRequest.ApiCallback<BaseResponseModel<UserResponseModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<UserResponseModel> result) {
//
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//
//                    AppProvider.getPreferences().saveStatusLogin(true);
//                    if (result.getData() != null && result.getData().length > 0)
//                        AppProvider.getPreferences().saveUserModel(result.getData()[0]);
//
//                    String deviceImei = String.valueOf(System.currentTimeMillis());
//                    String topicCheckAuth = "channel_" + deviceImei;
//                    FirebaseMessaging.getInstance().subscribeToTopic(topicCheckAuth);
////                    AppProvider.getPreferences().saveDeviceImei(deviceImei);
////
////                    String notifycation_529 = "nino_notifycation";
////                    UserResponseModel userModel = AppProvider.getPreferences().getUserModel();
////                    if (userModel != null) {
////                        String notifycation_529_user = "nino_notifycation_user_" + userModel.getId();
////                        FirebaseMessaging.getInstance().subscribeToTopic(notifycation_529_user);
////                    }
////
////                    FirebaseMessaging.getInstance().subscribeToTopic(notifycation_529);
//
//                    if (!TextUtils.isEmpty(result.getMessage())) {
//                        showToast(result.getMessage());
//                    } else {
//                        showToast("Đăng ký thành công!");
//                    }
//                    if (activity != null) {
////                        activity.changeToFragmentIntroCode();
//
//                        activity.changeToFragmentDashboard();
//                        activity.showBottomMenuBar();
////                        activity.checkAuthUser();
//                        activity.setPositionBottomMenu(0);
////                        ReloadMenuBottomEvent.post();
//                    }
//
//                } else {
//                    if (!TextUtils.isEmpty(result.getMessage())) {
//                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//
//            }
//        });
//    }
//}
