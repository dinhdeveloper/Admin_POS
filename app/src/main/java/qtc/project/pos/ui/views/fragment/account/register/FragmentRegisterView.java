//package qtc.project.pos.ui.views.fragment.account.register;
//
//import android.text.InputType;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import b.laixuantam.myaarlibrary.base.BaseUiContainer;
//import b.laixuantam.myaarlibrary.base.BaseView;
//import b.laixuantam.myaarlibrary.helper.AppUtils;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.model.UserRegisterModel;
//
//public class FragmentRegisterView extends BaseView<FragmentRegisterView.UIContainer> implements FragmentRegisterViewInterface {
//
//    private FragmentRegisterViewCallback callback;
//
//    private UserRegisterModel userRegisterModel;
//    private boolean isVisiblePassword, isVisibleConfirmPassword;
//    private HomeActivityBackup activity;
//
//    @Override
//    public void init(HomeActivityBackup activity, final FragmentRegisterViewCallback callback) {
//        this.activity = activity;
//        this.callback = callback;
//
//        userRegisterModel = new UserRegisterModel();
//
//        ui.btnBackHeader.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
//            @Override
//            public void onClick(View view) {
//                callback.onClickShowLoginForm();
//            }
//        });
//
//        ui.btnSubmitRegister.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
//            @Override
//            public void onClick(View v) {
//                if (checkDataInput()) {
//                    AppUtils.hideKeyBoard(getView());
//                    onRequestSignUp();
//                }
//            }
//        });
//
//        ui.btnVisiblePassword.setOnClickListener(v -> {
//            if (!isVisiblePassword) {
//                isVisiblePassword = true;
//                ui.edtRegisterPassowrd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                if (!"".contentEquals(ui.edtRegisterPassowrd.getText()))
//                    ui.edtRegisterPassowrd.setSelection(ui.edtRegisterPassowrd.getText().length());
//                ui.btnVisiblePassword.setBackgroundResource(R.drawable.eyes_selected_64);
//            } else {
//                isVisiblePassword = false;
//                ui.edtRegisterPassowrd.setInputType(129);
//                if (!"".contentEquals(ui.edtRegisterPassowrd.getText()))
//                    ui.edtRegisterPassowrd.setSelection(ui.edtRegisterPassowrd.getText().length());
//                ui.btnVisiblePassword.setBackgroundResource(R.drawable.ic_hide_eys);
//            }
//        });
//
//        ui.btnVisibleConfirmPassword.setOnClickListener(v -> {
//            if (!isVisibleConfirmPassword) {
//                isVisibleConfirmPassword = true;
//                ui.edtConfirmRegisterPassowrd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                if (!"".contentEquals(ui.edtConfirmRegisterPassowrd.getText()))
//                    ui.edtConfirmRegisterPassowrd.setSelection(ui.edtConfirmRegisterPassowrd.getText().length());
//                ui.btnVisibleConfirmPassword.setBackgroundResource(R.drawable.eyes_selected_64);
//            } else {
//                isVisibleConfirmPassword = false;
//                ui.edtConfirmRegisterPassowrd.setInputType(129);
//                if (!"".contentEquals(ui.edtConfirmRegisterPassowrd.getText()))
//                    ui.edtConfirmRegisterPassowrd.setSelection(ui.edtConfirmRegisterPassowrd.getText().length());
//                ui.btnVisibleConfirmPassword.setBackgroundResource(R.drawable.ic_hide_eys);
//            }
//        });
//    }
//
//    private void onRequestSignUp() {
//        if (callback == null) {
//            return;
//        }
//        if (ui.edtRegisterPassowrd.getText().length() < 6) {
//            if (activity != null) {
//                activity.showAlert("Mật khẩu phải chứa ít nhất 6 ký tự.", KAlertDialog.ERROR_TYPE);
//            } else {
//                Toast.makeText(getContext(), "Mật khẩu phải chứa ít nhất 6 ký tự.", Toast.LENGTH_SHORT).show();
//            }
//            return;
//        }
//
//        if (!ui.edtRegisterPassowrd.getText().toString().trim().equalsIgnoreCase(ui.edtConfirmRegisterPassowrd.getText().toString().trim())) {
//            if (activity != null) {
//                activity.showAlert("Mật khẩu không trùng nhau.", KAlertDialog.ERROR_TYPE);
//                return;
//            }
//
//        }
//
//        String fullname = ui.edtRegisterFullname.getText().toString().trim();
//        String pass = ui.edtRegisterPassowrd.getText().toString().trim();
//        String phone = ui.edtRegisterPhone.getText().toString().trim();
//        String address = ui.edtRegisterAddress.getText().toString().trim();
//        String email = ui.edtRegisterEmail.getText().toString().trim();
//
//        String[] arrFistLastName = fullname.split(" ");
//
//        if (arrFistLastName.length > 1) {
//            userRegisterModel.setUserFirstName(arrFistLastName[arrFistLastName.length - 1]);
//
//            StringBuilder userLastName = new StringBuilder();
//            for (int i = 0; i < arrFistLastName.length - 1; i++) {
//                userLastName.append(arrFistLastName[i]).append(" ");
//            }
//
//            userRegisterModel.setUserLastName(userLastName.toString().trim());
//
//        } else {
//            userRegisterModel.setUserFirstName(fullname);
//            userRegisterModel.setUserLastName("");
//        }
//
//        userRegisterModel.setUserName(fullname);
//        userRegisterModel.setUserPassword(pass);
//        userRegisterModel.setUserAddress(address);
//        userRegisterModel.setUserEmail(email);
//        userRegisterModel.setUserPhone(phone);
//
//        callback.onRequestCheckPhoneRegister(userRegisterModel);
//    }
//
//    private boolean checkDataInput() {
//
//        if (TextUtils.isEmpty(ui.edtRegisterPhone.getText())) {
//            ui.edtRegisterPhone.setError("Nhập số điện thoại đăng ký");
//            ui.edtRegisterPhone.requestFocus();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(ui.edtRegisterPassowrd.getText())) {
//            ui.edtRegisterPassowrd.setError("Nhập mật khẩu đăng ký");
//            ui.edtRegisterPassowrd.requestFocus();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(ui.edtConfirmRegisterPassowrd.getText())) {
//            ui.edtConfirmRegisterPassowrd.setError("Nhập lại mật khẩu đăng ký");
//            ui.edtConfirmRegisterPassowrd.requestFocus();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(ui.edtRegisterFullname.getText())) {
//            ui.edtRegisterFullname.setError("Nhập họ tên");
//            ui.edtRegisterFullname.requestFocus();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(ui.edtRegisterAddress.getText())) {
//            ui.edtRegisterAddress.setError("Nhập địa chỉ");
//            ui.edtRegisterAddress.requestFocus();
//            return false;
//        }
//
//        return true;
//    }
//
//
//    @Override
//    public BaseUiContainer getUiContainer() {
//        return new FragmentRegisterView.UIContainer();
//    }
//
//    @Override
//    public int getViewId() {
//        return R.layout.layout_fragment_register;
//    }
//
//    public static class UIContainer extends BaseUiContainer {
//
//        @UiElement(R.id.btnBackHeader)
//        public View btnBackHeader;
//
//        @UiElement(R.id.edtRegisterPhone)
//        public EditText edtRegisterPhone;
//
//        @UiElement(R.id.edtRegisterFullname)
//        public EditText edtRegisterFullname;
//
//        @UiElement(R.id.edtRegisterPassowrd)
//        public EditText edtRegisterPassowrd;
//
//        @UiElement(R.id.edtConfirmRegisterPassowrd)
//        public EditText edtConfirmRegisterPassowrd;
//
//        @UiElement(R.id.edtRegisterEmail)
//        public EditText edtRegisterEmail;
//
//        @UiElement(R.id.edtRegisterAddress)
//        public EditText edtRegisterAddress;
//
//        @UiElement(R.id.btnVisiblePassword)
//        public ImageView btnVisiblePassword;
//
//        @UiElement(R.id.btnVisibleConfirmPassword)
//        public ImageView btnVisibleConfirmPassword;
//
//        @UiElement(R.id.btnSubmitRegister)
//        public View btnSubmitRegister;
//    }
//}
