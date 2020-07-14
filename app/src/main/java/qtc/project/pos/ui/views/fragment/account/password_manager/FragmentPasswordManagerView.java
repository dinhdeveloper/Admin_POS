//package qtc.project.pos.ui.views.fragment.account.password_manager;
//
//import android.text.InputType;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import b.laixuantam.myaarlibrary.base.BaseUiContainer;
//import b.laixuantam.myaarlibrary.base.BaseView;
//import b.laixuantam.myaarlibrary.helper.AppUtils;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//
//public class FragmentPasswordManagerView extends BaseView<FragmentPasswordManagerView.UIContainer> implements FragmentPasswordManagerViewInterface {
//
//    private boolean isVisibleCurrentPassword;
//    private boolean isVisibleNewPassword;
//    private boolean isVisibleConfirmNewPassword;
//
//    @Override
//    public void init(HomeActivityBackup activity, FragmentPasswordManagerViewCallback callback) {
//
//        ui.tvTitleHeader.setText("Đổi mật khẩu");
//
//        ui.btnBackHeader.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
//            @Override
//            public void onClick(View v) {
//
//                callback.onClickBackHeader();
//            }
//        });
//
//        ui.btnSubmitChangePassword.setOnClickListener(v -> {
//            AppUtils.hideKeyBoard(getView());
//            if (checkPasswordInput()) {
//                String oldPassword = ui.edtCurrentPassword.getText().toString();
//                String newPass = ui.edtNewsPassword.getText().toString();
//                String confirmNewPass = ui.edtConfirmNewsPassword.getText().toString();
//
//                if (newPass.equals(confirmNewPass)) {
//                    callback.onSubmitChangePassword(oldPassword, newPass);
//                } else {
////                        Toast.makeText(getContext(), getString(R.string.error_confirm_password), Toast.LENGTH_SHORT).show();
//                    activity.showAlert("Mật khẩu mới không trùng nhau.", KAlertDialog.ERROR_TYPE);
//                }
//            }
//        });
//
//        ui.btnForgotPassword.setOnClickListener(v -> callback.onClickForgotPassword());
//
//
//        ui.btnVisibleCurrentPassword.setOnClickListener(v -> {
//            if (!isVisibleCurrentPassword) {
//                isVisibleCurrentPassword = true;
//                ui.edtCurrentPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                if (!"".contentEquals(ui.edtCurrentPassword.getText()))
//                    ui.edtCurrentPassword.setSelection(ui.edtCurrentPassword.getText().length());
//                ui.btnVisibleCurrentPassword.setBackgroundResource(R.drawable.eyes_selected_64);
//            } else {
//                isVisibleCurrentPassword = false;
//                ui.edtCurrentPassword.setInputType(129);
//                if (!"".contentEquals(ui.edtCurrentPassword.getText()))
//                    ui.edtCurrentPassword.setSelection(ui.edtCurrentPassword.getText().length());
//                ui.btnVisibleCurrentPassword.setBackgroundResource(R.drawable.ic_hide_eys);
//            }
//        });
//
//        ui.btnVisibleNewPassword.setOnClickListener(v -> {
//            if (!isVisibleNewPassword) {
//                isVisibleNewPassword = true;
//                ui.edtNewsPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                if (!"".contentEquals(ui.edtNewsPassword.getText()))
//                    ui.edtNewsPassword.setSelection(ui.edtNewsPassword.getText().length());
//                ui.btnVisibleNewPassword.setBackgroundResource(R.drawable.eyes_selected_64);
//            } else {
//                isVisibleNewPassword = false;
//                ui.edtNewsPassword.setInputType(129);
//                if (!"".contentEquals(ui.edtNewsPassword.getText()))
//                    ui.edtNewsPassword.setSelection(ui.edtNewsPassword.getText().length());
//                ui.btnVisibleNewPassword.setBackgroundResource(R.drawable.ic_hide_eys);
//            }
//        });
//
//        ui.btnVisibleConfirmNewPassword.setOnClickListener(v -> {
//            if (!isVisibleConfirmNewPassword) {
//                isVisibleConfirmNewPassword = true;
//                ui.edtConfirmNewsPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                if (!"".contentEquals(ui.edtConfirmNewsPassword.getText()))
//                    ui.edtConfirmNewsPassword.setSelection(ui.edtConfirmNewsPassword.getText().length());
//                ui.btnVisibleConfirmNewPassword.setBackgroundResource(R.drawable.eyes_selected_64);
//            } else {
//                isVisibleConfirmNewPassword = false;
//                ui.edtConfirmNewsPassword.setInputType(129);
//                if (!"".contentEquals(ui.edtConfirmNewsPassword.getText()))
//                    ui.edtConfirmNewsPassword.setSelection(ui.edtConfirmNewsPassword.getText().length());
//                ui.btnVisibleConfirmNewPassword.setBackgroundResource(R.drawable.ic_hide_eys);
//            }
//        });
//    }
//
//    @Override
//    public void validateUpdatePasswordSuccess() {
//        ui.edtCurrentPassword.setText("");
//        ui.edtNewsPassword.setText("");
//        ui.edtConfirmNewsPassword.setText("");
//    }
//
//    private boolean checkPasswordInput() {
//        if (TextUtils.isEmpty(ui.edtCurrentPassword.getText())) {
//            ui.edtCurrentPassword.setError("Nhập mật khẩu hiện tại");
//            ui.edtCurrentPassword.requestFocus();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(ui.edtNewsPassword.getText())) {
//            ui.edtNewsPassword.setError("Nhập mật khẩu mới");
//            ui.edtNewsPassword.requestFocus();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(ui.edtConfirmNewsPassword.getText())) {
//            ui.edtConfirmNewsPassword.setError("Nhập lại mật khẩu mới");
//            ui.edtConfirmNewsPassword.requestFocus();
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public BaseUiContainer getUiContainer() {
//        return new FragmentPasswordManagerView.UIContainer();
//    }
//
//    @Override
//    public int getViewId() {
//        return R.layout.layout_fragment_profile_manager_password;
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
//        @UiElement(R.id.edtCurrentPassowrd)
//        public EditText edtCurrentPassword;
//
//        @UiElement(R.id.btnVisibleCurrentPassword)
//        public ImageView btnVisibleCurrentPassword;
//
//        @UiElement(R.id.edtNewPassowrd)
//        public EditText edtNewsPassword;
//
//        @UiElement(R.id.btnVisibleNewPassword)
//        public ImageView btnVisibleNewPassword;
//
//        @UiElement(R.id.edtConfirmNewsPassword)
//        public EditText edtConfirmNewsPassword;
//
//        @UiElement(R.id.btnVisibleConfirmNewPassword)
//        public ImageView btnVisibleConfirmNewPassword;
//
//        @UiElement(R.id.btnSubmitChangePassword)
//        public View btnSubmitChangePassword;
//
//        @UiElement(R.id.btnForgotPassword)
//        public View btnForgotPassword;
//
//
//    }
//}
