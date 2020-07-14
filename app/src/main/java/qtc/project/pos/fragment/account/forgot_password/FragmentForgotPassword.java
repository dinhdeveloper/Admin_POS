//package qtc.project.pos.fragment.account.forgot_password;
//
//import android.text.TextUtils;
//import android.widget.Toast;
//
//import com.google.firebase.FirebaseException;
//import com.google.firebase.FirebaseTooManyRequestsException;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.Objects;
//import java.util.concurrent.TimeUnit;
//
//import b.laixuantam.myaarlibrary.api.ApiRequest;
//import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
//import b.laixuantam.myaarlibrary.base.BaseFragment;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
//import b.laixuantam.myaarlibrary.widgets.dialog.AppDialog;
//import b.laixuantam.myaarlibrary.widgets.dialog.ConfirmDialog;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.api.account.check_phone_exists.RequestCheckPhoneExists;
//import qtc.project.pos.api.account.reset_password.RequestResetPassword;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.model.BaseResponseModel;
//import qtc.project.pos.ui.views.fragment.account.forgot_password.FragmentForgotPasswordView;
//import qtc.project.pos.ui.views.fragment.account.forgot_password.FragmentForgotPasswordViewCallback;
//import qtc.project.pos.ui.views.fragment.account.forgot_password.FragmentForgotPasswordViewInterface;
//
//public class FragmentForgotPassword extends BaseFragment<FragmentForgotPasswordViewInterface, BaseParameters> implements FragmentForgotPasswordViewCallback {
//    private HomeActivityBackup activity;
//
//    @Override
//    protected void initialize() {
//        activity = (HomeActivityBackup) getActivity();
//        view.init(activity, this);
//
//        KeyboardUtils.setupUI(Objects.requireNonNull(getView()), activity);
//    }
//
//    @Override
//    protected FragmentForgotPasswordViewInterface getViewInstance() {
//        return new FragmentForgotPasswordView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//    @Override
//    public void onClickBackHeader() {
//        if (activity != null)
//            activity.checkBack();
//    }
//
//    @Override
//    public void onRequestCheckPhoneRegister(String phone) {
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
//            return;
//        }
//        showProgress();
//
//        RequestCheckPhoneExists.ApiParams params = new RequestCheckPhoneExists.ApiParams();
//        params.phone_number = phone;
//
//        AppProvider.getApiManagement().call(RequestCheckPhoneExists.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
//            @Override
//            public void onSuccess(BaseResponseModel result) {
//                dismissProgress();
//                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//                    view.checkPhoneRegisterSuccessExists();
//                    onRequestAuthPhoneNumber(phone);
//                } else {
//                    if (!TextUtils.isEmpty(result.getMessage())) {
//                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
//                    }
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
//    private String verificationID;
//
//
//    @Override
//    public void verifyVerificationCode(String code) {
//        if (TextUtils.isEmpty(verificationID))
//            return;
//
//        showProgress();
//        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, code);
//        signInWithPhoneAuthCredential(credential);
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
//            dismissProgress();
//            if (task.isSuccessful()) {
//                view.verifySMSSuccess();
//            } else {
//                view.verifySMSFailed();
//                showAlert("Mã xác thực không đúng, xin hãy kiểm tra lại SĐT.", KAlertDialog.ERROR_TYPE);
//            }
//        });
//    }
//
//    @Override
//    public void onRequestAuthPhoneNumber(String phone) {
//
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_internet_connection));
//            return;
//        }
//
//        showProgress();
//
//        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential credential) {
//
//                if (credential != null) {
////                    MyLog.e(TAG, "onVerificationCompleted: SmsCode" + credential.getSmsCode());
//                    final String smsCode = credential.getSmsCode();
//
//                    if (!TextUtils.isEmpty(smsCode)) {
//                        ConfirmDialog confirmDialog = ConfirmDialog.newInstance("Mã xác nhận của bạn là: " + smsCode, "Mã xác nhận", false, R.string.dongy, false, R.string.huybo, new ConfirmDialog.ConfirmDialogListener() {
//                            @Override
//                            public void onOk(AppDialog<?> f) {
//                                view.setCodeSMS(smsCode);
//                            }
//
//                            @Override
//                            public void onCancel(AppDialog<?> f) {
//
//                            }
//                        });
//                        confirmDialog.show(activity.getSupportFragmentManager(), "PhoneAuth");
//                    }
//
//                }
//
//                dismissProgress();
//
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
////                MyLog.e(TAG, "onVerificationFailed" + e);
//
//                dismissProgress();
//
//                if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                    //The format of the phone number provided is incorrect
//                    //phone numbers are written in the format [+][country code][subscriber number including area code]
//
//                    showAlert("Không thể gửi mã xác thực, hãy kiểm tra lại số điện thoại của bạn.", KAlertDialog.ERROR_TYPE);
//
//                } else if (e instanceof FirebaseTooManyRequestsException) {
//                    showAlert("Bạn đã gửi quá nhiều yêu cầu xác thực, xin hãy thử lại sau!!!", KAlertDialog.ERROR_TYPE);
//                }
//
//            }
//
//            @Override
//            public void onCodeSent(String verificationId,
//                                   PhoneAuthProvider.ForceResendingToken token) {
//                // The SMS verification code has been sent to the provided phone number, we
//                // now need to ask the user to enter the code and then construct a credential
//                // by combining the code with a verification ID.
//                super.onCodeSent(verificationId, token);
//                verificationID = verificationId;
//
//                dismissProgress();
//
//            }
//        };
//
////        String phoneNumber = phone.replaceFirst("0", "");
//
////        MyLog.e(TAG, "PhoneNumber:" + phone);
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                "+84" + phone,        // Phone number to verify
//                30L,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                activity,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//    }
//
//    @Override
//    public void onRequestUpdatePassword(String phone, String newPassword) {
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_internet_connection));
//            return;
//        }
//        if (newPassword.length() < 6) {
//            if (activity != null) {
//                activity.showAlert("Mật khẩu phải chứa ít nhất 6 ký tự.");
//            }else{
//                Toast.makeText(getContext(), "Mật khẩu phải chứa ít nhất 6 ký tự.", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//
//        showProgress();
//
//        RequestResetPassword.ApiParams params = new RequestResetPassword.ApiParams();
//
//        params.new_password = newPassword;
//        params.phone_number = phone;
//
//        AppProvider.getApiManagement().call(RequestResetPassword.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
//            @Override
//            public void onSuccess(BaseResponseModel result) {
//                dismissProgress();
//                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
//
//                    showConfirmAlert("Cập nhật thành công", "Mật khẩu của bạn đã được cập nhật lại.", kAlertDialog -> {
//                        kAlertDialog.dismiss();
//                        if (activity != null)
//                            activity.checkBack();
//                    }, null, KAlertDialog.SUCCESS_TYPE);
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
//                dismissProgress();
//                showAlert("Xảy ra lỗi trong quá trình cập nhật mật khẩu, xin thử lại sau", KAlertDialog.ERROR_TYPE);
//            }
//
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                dismissProgress();
//                showAlert("Xảy ra lỗi trong quá trình cập nhật mật khẩu, xin thử lại sau", KAlertDialog.ERROR_TYPE);
//            }
//        });
//    }
//}
