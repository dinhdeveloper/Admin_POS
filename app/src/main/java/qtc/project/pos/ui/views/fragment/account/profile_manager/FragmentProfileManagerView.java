//package qtc.project.pos.ui.views.fragment.account.profile_manager;
//
//import android.annotation.SuppressLint;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import b.laixuantam.myaarlibrary.base.BaseUiContainer;
//import b.laixuantam.myaarlibrary.base.BaseView;
//import b.laixuantam.myaarlibrary.helper.AppUtils;
//import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import b.laixuantam.myaarlibrary.widgets.popupmenu.ActionItem;
//import b.laixuantam.myaarlibrary.widgets.popupmenu.MyCustomPopupMenu;
//import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.helper.Consts;
//import qtc.project.pos.model.UserResponseModel;
//
//public class FragmentProfileManagerView extends BaseView<FragmentProfileManagerView.UIContainer> implements FragmentProfileManagerViewInterface {
//
//    private FragmentProfileManagerViewCallback callback;
//    private String user_avata;
//    private int feildValueChange = 0;
//
//    private UserResponseModel userResponseModel;
//
//    private HomeActivityBackup activity;
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public void init(HomeActivityBackup activity, FragmentProfileManagerViewCallback callback) {
//        this.activity = activity;
//        this.callback = callback;
//
//        KeyboardUtils.setupUI(getView(), activity);
//
//        userResponseModel = AppProvider.getPreferences().getUserModel();
//
//        ui.tvTitleHeader.setText("Hồ sơ của tôi");
//        setGone(ui.tvTitleHeader);
//
//        ui.btnBackHeader.setOnTouchListener(new ScaleTouchListener(confScaleTouch) {
//            @Override
//            public void onClick(View v) {
//                AppUtils.hideKeyBoard(getView());
//                callback.onClickBackHeader();
//            }
//        });
//
//        ui.imvUserAvata.setOnTouchListener(new ScaleTouchListener(confScaleTouch) {
//            @Override
//            public void onClick(View v) {
//                showOptionSelectImage(ui.imvUserAvata);
//            }
//        });
//
//
//        ui.btnSubmit.setOnClickListener(v -> {
//
//            if (TextUtils.isEmpty(ui.edtPhoneNumber.getText())) {
//                activity.showAlert("Nhập số điện thoại", KAlertDialog.ERROR_TYPE);
//                return;
//            }
//
//            if (TextUtils.isEmpty(ui.edtFullname.getText())) {
//                activity.showAlert("Nhập họ tên", KAlertDialog.ERROR_TYPE);
//                return;
//            }
//
//            if (TextUtils.isEmpty(ui.edtAddress.getText())) {
//                activity.showAlert("Nhập địa chỉ", KAlertDialog.ERROR_TYPE);
//                return;
//            }
//
//            AppUtils.hideKeyBoard(getView());
//            String fullname = ui.edtFullname.getText().toString();
//            String userFirstName;
//            String userLastName = "";
//            String[] arrFistLastName = fullname.split(" ");
//
//            if (arrFistLastName.length > 1) {
////                userLastName = arrFistLastName[0];
//                userFirstName = arrFistLastName[arrFistLastName.length - 1];
//
//                StringBuilder LastName = new StringBuilder();
//                for (int i = 0; i < arrFistLastName.length - 1; i++) {
//                    LastName.append(arrFistLastName[i]).append(" ");
//                }
//
//                userLastName = LastName.toString().trim();
//
//            } else {
//                userFirstName = fullname;
//            }
//
//            String userAddress = ui.edtAddress.getText().toString();
//            String userPhone = ui.edtPhoneNumber.getText().toString();
//            String userEmail = !TextUtils.isEmpty(ui.edtEmail.getText()) ? ui.edtEmail.getText().toString() : "";
//
//            callback.onRequestUpdateUserProfile(user_avata, userFirstName, userLastName, userAddress, userEmail, userPhone);
//        });
//
//        setUpPopupMenu();
//
//    }
//
//    @Override
//    public void configTypeShowUpdateInfo(String type) {
//        switch (type) {
//            case "user":
//                setVisible(ui.layoutChangeImageAvatar);
//                setVisible(ui.layoutChangeEmail);
//                setVisible(ui.dividerLayoutChangeEmail);
//                break;
//
//            case "order":
//                setGone(ui.layoutChangeImageAvatar);
//                setGone(ui.layoutChangeEmail);
//                setGone(ui.dividerLayoutChangeEmail);
//                break;
//        }
//
//        fillDataProfile(type);
//    }
//
//    @Override
//    public void setDataUserImage(String outfile) {
//        user_avata = outfile;
//        AppProvider.getImageHelper().displayImage(outfile, ui.imvUserAvata, null, R.drawable.ic_account_circle_black_24dp, true);
//
//        changeStateBtnSubmitUpdateProfile(true);
//    }
//
//    private void showOptionSelectImage(View view) {
//        AppUtils.hideKeyBoard(getView());
//        quickAction.show(view);
//
//    }
//
//    private MyCustomPopupMenu quickAction;
//
//    private void setUpPopupMenu() {
//        ActionItem selectImageFromGallery = new ActionItem(1, getString(R.string.title_select_image_from_gallery), null);
//        ActionItem captureImage = new ActionItem(2, getString(R.string.title_select_image_from_camera), null);
//        quickAction = new MyCustomPopupMenu(getContext());
//        quickAction.addActionItem(selectImageFromGallery);
//        quickAction.addActionItem(captureImage);
//
//        quickAction.setOnActionItemClickListener((source, pos, actionId) -> {
//            switch (actionId) {
//                case 1:
//                    if (callback != null)
//                        callback.showDialogSelecteImage();
//                    break;
//
//                case 2:
//                    if (callback != null)
//                        callback.showDialogTakePicture();
//                    break;
//            }
//        });
//    }
//
//    private void fillDataProfile(String type_change) {
//
//        if (userResponseModel != null && type_change.equalsIgnoreCase("user")) {
//
//            if (!TextUtils.isEmpty(userResponseModel.getLogin_type())) {
//                String imageLink = "";
//                switch (userResponseModel.getLogin_type()) {
//                    case "fb":
//                    case "gg":
//                        if (!TextUtils.isEmpty(userResponseModel.getAvatar())) {
//                            imageLink = Consts.HOST_API + userResponseModel.getAvatar();
//                        } else {
//                            if (!TextUtils.isEmpty(userResponseModel.getImg_social_link())) {
//                                imageLink = Consts.HOST_API + userResponseModel.getImg_social_link();
//                            }
//                        }
//                        break;
//
//                    default:
//                        if (!TextUtils.isEmpty(userResponseModel.getAvatar())) {
//                            imageLink = Consts.HOST_API + userResponseModel.getAvatar();
//                        }
//                        break;
//                }
//                if (!TextUtils.isEmpty(imageLink))
//                    AppProvider.getImageHelper().displayImage(imageLink, ui.imvUserAvata, null, R.drawable.ic_account_circle_black_24dp, true);
//            } else {
//                if (!TextUtils.isEmpty(userResponseModel.getAvatar())) {
//                    String imageLink = Consts.HOST_API + userResponseModel.getAvatar();
//                    AppProvider.getImageHelper().displayImage(imageLink, ui.imvUserAvata, null, R.drawable.ic_account_circle_black_24dp, true);
//                }
//            }
//
//            StringBuilder fullname = new StringBuilder();
//            fullname.append(userResponseModel.getLast_name()).append(" ").append(userResponseModel.getFirst_name());
//
//            ui.edtFullname.setText(fullname.toString());
//
//            if (!TextUtils.isEmpty(userResponseModel.getPhone_contact()))
//                ui.edtPhoneNumber.setText(userResponseModel.getPhone_contact());
//            else if (!TextUtils.isEmpty(userResponseModel.getPhone_number()))
//                ui.edtPhoneNumber.setText(userResponseModel.getPhone_number());
//
//
//            ui.edtAddress.setText(userResponseModel.getAddress_personal());
//
//            ui.edtEmail.setText(userResponseModel.getEmail());
//
//        } else {
//            StringBuilder fullname = new StringBuilder();
//
//            fullname.append(userResponseModel.getLast_name()).append(" ").append(userResponseModel.getFirst_name());
//
//            ui.edtFullname.setText(fullname.toString());
//
//            if (!TextUtils.isEmpty(userResponseModel.getPhone_contact()))
//                ui.edtPhoneNumber.setText(userResponseModel.getPhone_contact());
//            else if (!TextUtils.isEmpty(userResponseModel.getPhone_number()))
//                ui.edtPhoneNumber.setText(userResponseModel.getPhone_number());
//
//            ui.edtAddress.setText(userResponseModel.getAddress_personal());
//
//            ui.edtEmail.setText(userResponseModel.getEmail());
//        }
//
//    }
//
//    private void checkValueChange() {
//        if (feildValueChange > 0) {
//            changeStateBtnSubmitUpdateProfile(true);
//        } else {
//            changeStateBtnSubmitUpdateProfile(false);
//        }
//    }
//
//    public void changeStateBtnSubmitUpdateProfile(boolean active) {
//        if (active) {
//            ui.btnSubmit.setEnabled(true);
//            ui.btnSubmit.setBackgroundResource(R.drawable.bg_btn_primary);
//        } else {
//            ui.btnSubmit.setEnabled(false);
//            ui.btnSubmit.setBackgroundResource(R.drawable.button_login_background_disable);
//        }
//    }
//
//    @Override
//    public BaseUiContainer getUiContainer() {
//        return new FragmentProfileManagerView.UIContainer();
//    }
//
//    @Override
//    public int getViewId() {
//        return R.layout.layout_fragment_update_profile_info;
//    }
//
//    public static class UIContainer extends BaseUiContainer {
//
//        @UiElement(R.id.btnBackHeader)
//        public View btnBackHeader;
//
//        @UiElement(R.id.tvTitleHeader)
//        public TextView tvTitleHeader;
//
//        @UiElement(R.id.layoutChangeImageAvatar)
//        public View layoutChangeImageAvatar;
//
//        @UiElement(R.id.imvUserAvata)
//        public ImageView imvUserAvata;
//
//        @UiElement(R.id.edtPhoneNumber)
//        public EditText edtPhoneNumber;
//
//        @UiElement(R.id.edtFullname)
//        public EditText edtFullname;
//
//        @UiElement(R.id.layoutChangeEmail)
//        public View layoutChangeEmail;
//
//        @UiElement(R.id.dividerLayoutChangeEmail)
//        public View dividerLayoutChangeEmail;
//
//        @UiElement(R.id.edtEmail)
//        public EditText edtEmail;
//
//        @UiElement(R.id.edtAddress)
//        public EditText edtAddress;
//
//        @UiElement(R.id.btnSubmit)
//        public View btnSubmit;
//
//
//    }
//}
