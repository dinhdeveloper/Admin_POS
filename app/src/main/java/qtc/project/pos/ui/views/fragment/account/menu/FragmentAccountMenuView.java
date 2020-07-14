//package qtc.project.pos.ui.views.fragment.account.menu;
//
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import b.laixuantam.myaarlibrary.base.BaseUiContainer;
//import b.laixuantam.myaarlibrary.base.BaseView;
//import qtc.project.pos.R;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.helper.Consts;
//import qtc.project.pos.model.UserResponseModel;
//
//public class FragmentAccountMenuView extends BaseView<FragmentAccountMenuView.UIContainer> implements FragmentAccountMenuViewInterface {
//
//    @Override
//    public void init(FragmentAccountMenuViewCallback callback) {
//
//        ui.btnLogout.setOnClickListener(v -> {
//            if (callback != null)
//                callback.onClickLogout();
//        });
//
//        ui.btnGoToLogin.setOnClickListener(v -> {
//            if (callback != null)
//                callback.changeToFragmentLogin();
//        });
//
//        ui.btnChangeProfile.setOnClickListener(view -> {
//            if (callback != null)
//                callback.onClickChangeToFragmentProfileManager();
//        });
//
//        ui.btnChangePassword.setOnClickListener(view -> {
//            if (callback != null)
//                callback.onClickChangeToFragmentChangePassword();
//        });
//
//        fillDataUser();
//    }
//
//    @Override
//    public void showLayoutLoginRequest() {
//        setVisible(ui.layoutLoginRequest);
//    }
//
//    private void fillDataUser() {
//        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
//
//        if (userResponseModel == null) {
//            return;
//        }
//
//        StringBuilder fullname = new StringBuilder();
//
//        fullname.append(userResponseModel.getLast_name()).append(" ").append(userResponseModel.getFirst_name());
//
//        ui.tvCustomerName.setText(fullname);
//        ui.tvCustomerPhone.setText(userResponseModel.getPhone_number());
//        ui.tvCustomerEmail.setText(userResponseModel.getEmail());
//
//        if (!TextUtils.isEmpty(userResponseModel.getLogin_type())) {
//            String imageLink = "";
//            switch (userResponseModel.getLogin_type()) {
//                case "fb":
//                case "gg":
//                    if (!TextUtils.isEmpty(userResponseModel.getAvatar())) {
//                        imageLink = Consts.HOST_API + userResponseModel.getAvatar();
//                    } else {
//                        if (!TextUtils.isEmpty(userResponseModel.getImg_social_link())) {
//                            imageLink = Consts.HOST_API + userResponseModel.getImg_social_link();
//                        }
//                    }
//                    break;
//
//                default:
//                    if (!TextUtils.isEmpty(userResponseModel.getAvatar())) {
//                        imageLink = Consts.HOST_API + userResponseModel.getAvatar();
//                    }
//                    break;
//            }
//            if (!TextUtils.isEmpty(imageLink))
//                AppProvider.getImageHelper().displayImage(imageLink, ui.imvUserAvata, null, R.drawable.ic_account_circle_black_24dp, true);
//        } else {
//            if (!TextUtils.isEmpty(userResponseModel.getAvatar())) {
//                String imageLink = Consts.HOST_API + userResponseModel.getAvatar();
//                AppProvider.getImageHelper().displayImage(imageLink, ui.imvUserAvata, null, R.drawable.ic_account_circle_black_24dp, true);
//            }
//        }
//    }
//
//    @Override
//    public BaseUiContainer getUiContainer() {
//        return new FragmentAccountMenuView.UIContainer();
//    }
//
//    @Override
//    public int getViewId() {
//        return R.layout.layout_fragment_profile;
//    }
//
//    public static class UIContainer extends BaseUiContainer {
//
//        @UiElement(R.id.imvUserAvata)
//        public ImageView imvUserAvata;
//
//        @UiElement(R.id.tvCustomerName)
//        public TextView tvCustomerName;
//
//        @UiElement(R.id.tvCustomerPhone)
//        public TextView tvCustomerPhone;
//
//        @UiElement(R.id.tvCustomerEmail)
//        public TextView tvCustomerEmail;
//
//        @UiElement(R.id.btnChangeProfile)
//        public View btnChangeProfile;
//
//        @UiElement(R.id.btnChangePassword)
//        public View btnChangePassword;
//
//        @UiElement(R.id.btnLogout)
//        public View btnLogout;
//
//        @UiElement(R.id.layoutLoginRequest)
//        public View layoutLoginRequest;
//
//        @UiElement(R.id.btnGoToLogin)
//        public View btnGoToLogin;
//
//    }
//}
