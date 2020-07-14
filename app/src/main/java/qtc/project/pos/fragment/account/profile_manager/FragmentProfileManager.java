//package qtc.project.pos.fragment.account.profile_manager;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Objects;
//
//import b.laixuantam.myaarlibrary.api.ApiRequest;
//import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
//import b.laixuantam.myaarlibrary.base.BaseFragment;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import b.laixuantam.myaarlibrary.helper.MyLog;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import id.zelory.compressor.Compressor;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.api.account.update_profile.RequestUpdateUserProfile;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.event.FragmentProfileManagerBackEvent;
//import qtc.project.pos.event.KeyboardInEvent;
//import qtc.project.pos.event.KeyboardOutEvent;
//import qtc.project.pos.helper.Consts;
//import qtc.project.pos.model.BaseResponseModel;
//import qtc.project.pos.model.UserResponseModel;
//import qtc.project.pos.ui.views.fragment.account.profile_manager.FragmentProfileManagerView;
//import qtc.project.pos.ui.views.fragment.account.profile_manager.FragmentProfileManagerViewCallback;
//import qtc.project.pos.ui.views.fragment.account.profile_manager.FragmentProfileManagerViewInterface;
//
//public class FragmentProfileManager extends BaseFragment<FragmentProfileManagerViewInterface, BaseParameters> implements FragmentProfileManagerViewCallback {
//
//    public static FragmentProfileManager newInstance(String type_change) {
//        FragmentProfileManager frag = new FragmentProfileManager();
//        Bundle bundle = new Bundle();
//        bundle.putString("type_change", type_change);
//        frag.setArguments(bundle);
//        return frag;
//    }
//
//    private HomeActivityBackup activity;
//
//    private String type_change = "user";
//
//    @Override
//    protected void initialize() {
//        activity = (HomeActivityBackup) getActivity();
//        view.init(activity, this);
////        handler.postDelayed(loopCheckViewInit, 300);
//
//        if (getArguments() != null) {
//            type_change = getArguments().getString("type_change", "user");
//        }
//
//        view.configTypeShowUpdateInfo(type_change);
//    }
//
//    private final Runnable loopCheckViewInit = new Runnable() {
//        public void run() {
//            if (getView() != null && getView().isShown()) {
//                MyLog.e(Consts.TAG_FRAGMENT_CHECK, " is on screen");
//
//                handler.removeCallbacks(this);
//
//                return;
//            }
//
//            MyLog.e(Consts.TAG_FRAGMENT_CHECK, " is NOT on screen");
//            handler.postDelayed(this, 500);
//        }
//    };
//
//    @Override
//    protected FragmentProfileManagerViewInterface getViewInstance() {
//        return new FragmentProfileManagerView();
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
//            activity.deleteTempMedia();
//            FragmentProfileManagerBackEvent.post();
//        }
//    }
//
//    public void setImageSelected(String filePath) {
//        if (TextUtils.isEmpty(filePath)) {
//            showAlert(getString(R.string.error_load_file_image), KAlertDialog.ERROR_TYPE);
//            return;
//        }
//
//        reduceImageSize(filePath);
//
//    }
//
//    private void reduceImageSize(String filePath) {
//
//        File fileImage = new File(filePath);
//
//        if (fileImage.exists()) {
//
//            try {
//                File compressedImageFile = new Compressor(Objects.requireNonNull(getContext())).compressToFile(fileImage);
//                handler.postDelayed(() -> {
//                    if (compressedImageFile.exists()) {
//                        view.setDataUserImage(compressedImageFile.getAbsolutePath());
//                    } else {
//                        view.setDataUserImage(filePath);
//                    }
//                }, 300);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void showDialogSelecteImage() {
//        if (activity != null)
//            activity.changeToActivitySelectImage();
//    }
//
//    @Override
//    public void showDialogTakePicture() {
//        if (activity != null)
//            activity.captureImageFromCamera();
//    }
//
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onKeyboardShowing(KeyboardInEvent event) {
//        if (view != null) {
//        }
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onKeyboardDissmiss(KeyboardOutEvent event) {
//        if (view != null) {
//        }
//    }
//
//    @Override
//    public void onRequestUpdateUserProfile(String user_avata, String userFirstName, String userLastName, String userAddress, String email, String phone_contact) {
//
//        if (type_change.equalsIgnoreCase("order")) {
//
//            if (activity != null) {
//                StringBuilder fullname = new StringBuilder();
//                fullname.append(userLastName).append(" ").append(userFirstName);
//
//                activity.showConfirmAlert("Thông tin giao hàng", "Cập nhật thông tin giao thành công.", new KAlertDialog.KAlertClickListener() {
//                    @Override
//                    public void onClick(KAlertDialog kAlertDialog) {
//                        kAlertDialog.dismiss();
//                        onClickBackHeader();
//                    }
//                }, KAlertDialog.SUCCESS_TYPE);
//
//            }
//
//            return;
//        }
//
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_internet_connection));
//            return;
//        }
//        showProgress();
//
//        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
//
//        RequestUpdateUserProfile.ApiParams params = new RequestUpdateUserProfile.ApiParams();
//        params.detect = "customer_change_info";
//        params.id = userResponseModel.getId();
//        params.first_name = userFirstName;
//        params.last_name = userLastName;
//        params.address_personal = userAddress;
//        params.avatar = user_avata;
//        params.email = email;
//        params.phone_contact = phone_contact;
//
//        AppProvider.getApiManagement().call(RequestUpdateUserProfile.class, params, new ApiRequest.ApiCallback<BaseResponseModel<UserResponseModel>>() {
//            @Override
//            public void onSuccess(BaseResponseModel<UserResponseModel> result) {
//                dismissProgress();
//                if (result != null && !TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
//                    showAlert(getString(R.string.update_success), KAlertDialog.SUCCESS_TYPE);
//
//                    if (result.getData() != null && result.getData().length > 0) {
//                        UserResponseModel modelResponse = result.getData()[0];
//     //                   AppProvider.getPreferences().saveUserModel(modelResponse);
//
//                    }
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
//                showAlert(getString(R.string.update_failed), KAlertDialog.ERROR_TYPE);
//            }
//
//            @Override
//            public void onFail(ApiRequest.RequestError error) {
//                dismissProgress();
//                showAlert(getString(R.string.update_failed), KAlertDialog.ERROR_TYPE);
//            }
//        });
//
//    }
//}
