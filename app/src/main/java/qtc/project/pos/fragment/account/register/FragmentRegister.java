//package qtc.project.pos.fragment.account.register;
//
//import android.text.TextUtils;
//
//import java.util.Objects;
//
//import b.laixuantam.myaarlibrary.api.ApiRequest;
//import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
//import b.laixuantam.myaarlibrary.base.BaseFragment;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.api.account.check_phone_exists.RequestCheckPhoneExists;
//import qtc.project.pos.api.account.register.RequestRegister;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.event.FragmentRegisterBackEvent;
//import qtc.project.pos.model.BaseResponseModel;
//import qtc.project.pos.model.UserRegisterModel;
//import qtc.project.pos.model.UserResponseModel;
//import qtc.project.pos.ui.views.fragment.account.register.FragmentRegisterView;
//import qtc.project.pos.ui.views.fragment.account.register.FragmentRegisterViewCallback;
//import qtc.project.pos.ui.views.fragment.account.register.FragmentRegisterViewInterface;
//
//public class FragmentRegister extends BaseFragment<FragmentRegisterViewInterface, BaseParameters> implements FragmentRegisterViewCallback {
//
//    private HomeActivityBackup activity;
//
//    @Override
//    protected void initialize() {
//        activity = (HomeActivityBackup) getActivity();
//        view.init(activity, this);
//
//        KeyboardUtils.setupUI(Objects.requireNonNull(getView()), activity);
//
//        activity.FullScreencall();
//    }
//
//    @Override
//    protected FragmentRegisterViewInterface getViewInstance() {
//        return new FragmentRegisterView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//    @Override
//    public void onClickBackHeader() {
//        if (activity != null) {
//            activity.checkBack();
//
//            FragmentRegisterBackEvent.post();
//        }
//    }
//
//    @Override
//    public void onClickShowLoginForm() {
//        if (activity != null) {
//            activity.checkBack();
//
//            FragmentRegisterBackEvent.post();
//        }
//    }
//
//
////    private String verificationID;
////
////    @Override
////    public void onRequestAuthPhoneNumber(String phone) {
////        String TAG = "PhoneAuthCredential";
////
////        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
////            showAlert(getString(R.string.error_internet_connection));
////            return;
////        }
////
////        showProgress();
////
////        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
////
////            @Override
////            public void onVerificationCompleted(PhoneAuthCredential credential) {
////
////                if (credential != null) {
//////                    MyLog.e(TAG, "onVerificationCompleted: SmsCode" + credential.getSmsCode());
////                    final String smsCode = credential.getSmsCode();
////
////                    if (!TextUtils.isEmpty(smsCode)) {
////                        ConfirmDialog confirmDialog = ConfirmDialog.newInstance("Mã xác nhận của bạn là: " + smsCode, "Mã xác nhận", false, R.string.dongy, false, R.string.huybo, new ConfirmDialog.ConfirmDialogListener() {
////                            @Override
////                            public void onOk(AppDialog<?> f) {
////                                view.setCodeSMS(smsCode);
////                            }
////
////                            @Override
////                            public void onCancel(AppDialog<?> f) {
////
////                            }
////                        });
////                        confirmDialog.show(activity.getSupportFragmentManager(), "PhoneAuth");
////                    }
////
////                }
////
////                dismissProgress();
////
////            }
////
////            @Override
////            public void onVerificationFailed(FirebaseException e) {
//////                MyLog.e(TAG, "onVerificationFailed" + e);
////
////                dismissProgress();
////
////                if (e instanceof FirebaseAuthInvalidCredentialsException) {
////                    //The format of the phone number provided is incorrect
////                    //phone numbers are written in the format [+][country code][subscriber number including area code]
////
////                    showAlert("Không thể gửi mã xác thực, hãy kiểm tra lại số điện thoại của bạn.", KAlertDialog.ERROR_TYPE);
////
////                } else if (e instanceof FirebaseTooManyRequestsException) {
////                    showAlert("Bạn đã gửi quá nhiều yêu cầu xác thực, xin hãy thử lại sau!!!", KAlertDialog.ERROR_TYPE);
////                }
////
////            }
////
////            @Override
////            public void onCodeSent(String verificationId,
////                                   PhoneAuthProvider.ForceResendingToken token) {
////                // The SMS verification code has been sent to the provided phone number, we
////                // now need to ask the user to enter the code and then construct a credential
////                // by combining the code with a verification ID.
//////                super.onCodeSent(verificationId, token);
////                verificationID = verificationId;
////
////                dismissProgress();
////
////            }
////        };
////
//////        String phoneNumber = phone.replaceFirst("0", "");
////
//////        MyLog.e(TAG, "PhoneNumber:" + phone);
////
////        PhoneAuthProvider.getInstance().verifyPhoneNumber(
////                "+84" + phone,        // Phone number to verify
////                30L,                 // Timeout duration
////                TimeUnit.SECONDS,   // Unit of timeout
////                activity,               // Activity (for callback binding)
////                mCallbacks);        // OnVerificationStateChangedCallbacks
////    }
////
////    @Override
////    public void verifyVerificationCode(String code) {
////        if (TextUtils.isEmpty(verificationID))
////            return;
////
////        showProgress();
////        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
////        signInWithPhoneAuthCredential(credential);
////    }
////
////    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
////        FirebaseAuth auth = FirebaseAuth.getInstance();
////        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
////            dismissProgress();
////            if (task.isSuccessful()) {
////                view.verifySMSSuccess();
////            } else {
////                view.verifySMSFailed();
////                showAlert("Mã xác thực không đúng, xin hãy kiểm tra lại SĐT.", KAlertDialog.ERROR_TYPE);
////            }
////        });
////    }
//
//    @Override
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
//        params.address_personal = userRegisterModel.getUserAddress();
//        params.email = userRegisterModel.getUserEmail();
//
//        AppProvider.getApiManagement().call(RequestRegister.class, params, new ApiRequest.ApiCallback<BaseResponseModel<UserResponseModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<UserResponseModel> result) {
//
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//
//                    AppProvider.getPreferences().saveStatusLogin(true);
//                    if (result.getData() != null && result.getData().length > 0)
////                        AppProvider.getPreferences().saveUserModel(result.getData()[0]);
//
////                    String deviceImei = String.valueOf(System.currentTimeMillis());
////                    String topicCheckAuth = "channel_" + deviceImei;
////                    FirebaseMessaging.getInstance().subscribeToTopic(topicCheckAuth);
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
//                    showToast("Đăng ký thành công!");
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
//
//    @Override
//    public void onRequestCheckPhoneRegister(UserRegisterModel registerModel) {
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
//            return;
//        }
//        showProgress();
//
//        RequestCheckPhoneExists.ApiParams params = new RequestCheckPhoneExists.ApiParams();
//        params.phone_number = registerModel.getUserPhone();
//
//        AppProvider.getApiManagement().call(RequestCheckPhoneExists.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
//            @Override
//            public void onSuccess(BaseResponseModel result) {
//                dismissProgress();
//                if (!TextUtils.isEmpty(result.getSuccess()) && !Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//                    showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
//
//                } else {
//                    onSignUp(registerModel);
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                dismissProgress();
//                showAlert("Xảy ra lỗi trong quá trình xác thực số điện thoại, xin thử lại sau", KAlertDialog.ERROR_TYPE);
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                dismissProgress();
//                showAlert("Xảy ra lỗi trong quá trình xác thực số điện thoại, xin thử lại sau", KAlertDialog.ERROR_TYPE);
//            }
//        });
//
//    }
//
//}
