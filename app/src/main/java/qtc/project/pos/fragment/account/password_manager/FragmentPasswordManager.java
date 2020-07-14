//package qtc.project.pos.fragment.account.password_manager;
//
//import android.text.TextUtils;
//import android.widget.Toast;
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
//import qtc.project.pos.api.account.update_profile.RequestUpdateUserProfile;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.model.BaseResponseModel;
//import qtc.project.pos.model.UserResponseModel;
//import qtc.project.pos.ui.views.fragment.account.password_manager.FragmentPasswordManagerView;
//import qtc.project.pos.ui.views.fragment.account.password_manager.FragmentPasswordManagerViewCallback;
//import qtc.project.pos.ui.views.fragment.account.password_manager.FragmentPasswordManagerViewInterface;
//
//public class FragmentPasswordManager extends BaseFragment<FragmentPasswordManagerViewInterface, BaseParameters> implements FragmentPasswordManagerViewCallback {
//
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
//    protected FragmentPasswordManagerViewInterface getViewInstance() {
//        return new FragmentPasswordManagerView();
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
//    public void onSubmitChangePassword(String oldPassword, String newPass) {
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_internet_connection));
//            return;
//        }
//
//        if (newPass.length() < 6) {
//            if (activity != null) {
//                activity.showAlert("Mật khẩu phải chứa ít nhất 6 ký tự.");
//            } else {
//                Toast.makeText(getContext(), "Mật khẩu phải chứa ít nhất 6 ký tự.", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
//        if (userResponseModel == null)
//            return;
//
//        showProgress();
//
//        RequestUpdateUserProfile.ApiParams params = new RequestUpdateUserProfile.ApiParams();
//        if (userResponseModel.getAccount_level().equalsIgnoreCase("normal")) {
//            params.detect = "customer_change_info";
//            params.id = userResponseModel.getId();
//        } else {
//            params.detect = "account_manager";
//            params.type_manager = "update_password";
//            params.id_user = userResponseModel.getIDManager();
//        }
//
//        params.password = newPass;
//        params.old_password = oldPassword;
//
//        AppProvider.getApiManagement().call(RequestUpdateUserProfile.class, params, new ApiRequest.ApiCallback<BaseResponseModel<UserResponseModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<UserResponseModel> result) {
//                dismissProgress();
//                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//                    showAlert(getString(R.string.update_success), KAlertDialog.SUCCESS_TYPE);
//
//                    view.validateUpdatePasswordSuccess();
//                } else {
//                    if (result != null && !TextUtils.isEmpty(result.getMessage())) {
//                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
//                    }
//                }
//            }
//
//            @Override
//            public void onError(ErrorApiResponse error) {
//                dismissProgress();
//                showAlert(getString(R.string.update_failed), KAlertDialog.SUCCESS_TYPE);
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                dismissProgress();
//                showAlert(getString(R.string.update_failed), KAlertDialog.SUCCESS_TYPE);
//            }
//        });
//    }
//
//    @Override
//    public void onClickForgotPassword() {
//        if (activity != null)
//            activity.changeToFragmentForgotPassword();
//    }
//}
