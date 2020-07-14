//package qtc.project.pos.ui.views.fragment.account.forgot_password;
//
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.infideap.blockedittext.BlockEditText;
//
//import b.laixuantam.myaarlibrary.base.BaseUiContainer;
//import b.laixuantam.myaarlibrary.base.BaseView;
//import b.laixuantam.myaarlibrary.helper.AnimationHelper;
//import b.laixuantam.myaarlibrary.helper.AppUtils;
//import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//
//public class FragmentForgotPasswordView extends BaseView<FragmentForgotPasswordView.UIContainer> implements FragmentForgotPasswordViewInterface {
//
//    private boolean isShowFormInputPhoneCheck = true;
//    private boolean isVisiblePassword;
//
//    @Override
//    public void init(HomeActivityBackup activity, FragmentForgotPasswordViewCallback callback) {
//        ui.tvTitleHeader.setText("Quên mật khẩu");
//
//        ui.btnBackHeader.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
//            @Override
//            public void onClick(View v) {
//                if (!isShowFormInputPhoneCheck) {
//                    showLayoutInputPhoneCheck();
//                    return;
//                }
//
//                callback.onClickBackHeader();
//            }
//        });
//
//        ui.btnShowFormInputSmsCode.setOnClickListener(v -> {
//            if (TextUtils.isEmpty(ui.edtUserPhoneRegister.getText())) {
//                ui.edtUserPhoneRegister.setError("Nhập số điện thoại đăng ký");
//                ui.edtUserPhoneRegister.requestFocus();
//                return;
//            }
//
//            String phone = ui.edtUserPhoneRegister.getText().toString();
//
//            callback.onRequestCheckPhoneRegister(phone);
//        });
//
//        ui.blockEditTextAuthPhone.setTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() >= ui.blockEditTextAuthPhone.getMaxLength()) {
//
//                    AppUtils.hideKeyBoard(getView());
//
//                    if (!TextUtils.isEmpty(smsCode)) {
//                        if (ui.blockEditTextAuthPhone.getText().equalsIgnoreCase(smsCode)) {
//                            verifySMSSuccess();
//                        } else {
//                            verifySMSFailed();
//                        }
//                    } else {
//                        String code = ui.blockEditTextAuthPhone.getText();
//                        callback.verifyVerificationCode(code);
//                    }
//                } else {
//                    setGone(ui.tvWrongSMSCode);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        ui.btnShowFormInputNewPassword.setOnClickListener(v -> showLayoutInputNewPassword());
//
//        ui.btnReSendSmsCode.setOnClickListener(new ScaleTouchListener() {
//            @Override
//            public void onClick(View v) {
//                if (TextUtils.isEmpty(ui.edtUserPhoneRegister.getText())) {
//                    Toast.makeText(getContext(), "Nhập số điện thoại", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                String phone = ui.edtUserPhoneRegister.getText().toString().replaceAll(" ", "");
//
//                callback.onRequestAuthPhoneNumber(phone);
//            }
//        });
//
//        ui.btnSubmitNewPass.setOnClickListener(v -> {
//            if (TextUtils.isEmpty(ui.edtNewsPassword.getText())) {
//                ui.edtNewsPassword.setError("Nhập mật khẩu mới");
//                ui.edtNewsPassword.requestFocus();
//                return;
//            }
//
//            String phone = ui.edtUserPhoneRegister.getText().toString().replaceAll(" ", "");
//            String newPassword = ui.edtNewsPassword.getText().toString().trim();
//
//            callback.onRequestUpdatePassword(phone, newPassword);
//        });
//
//        ui.btnVisiblePassword.setOnClickListener(v -> {
//            if (!isVisiblePassword) {
//                isVisiblePassword = true;
//                ui.edtNewsPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                if (!"".contentEquals(ui.edtNewsPassword.getText()))
//                    ui.edtNewsPassword.setSelection(ui.edtNewsPassword.getText().length());
//                ui.btnVisiblePassword.setBackgroundResource(R.drawable.eyes_selected_64);
//            } else {
//                isVisiblePassword = false;
//                ui.edtNewsPassword.setInputType(129);
//                if (!"".contentEquals(ui.edtNewsPassword.getText()))
//                    ui.edtNewsPassword.setSelection(ui.edtNewsPassword.getText().length());
//                ui.btnVisiblePassword.setBackgroundResource(R.drawable.ic_hide_eys);
//            }
//        });
//
//
//        showLayoutInputPhoneCheck();
//
//    }
//
//    @Override
//    public void checkPhoneRegisterSuccessExists() {
//        showLayoutInputSMSCode();
//    }
//
//    private void showLayoutInputPhoneCheck() {
//        isShowFormInputPhoneCheck = true;
//
//        setVisible(ui.layout_form_input_phone);
//        setGone(ui.layout_form_input_sms_code);
//        setGone(ui.layout_form_input_new_pass);
//
//        AnimationHelper.fade(ui.layout_form_input_phone, true);
//    }
//
//    private void showLayoutInputSMSCode() {
//        isShowFormInputPhoneCheck = false;
//
//        setGone(ui.layout_form_input_phone);
//        setVisible(ui.layout_form_input_sms_code);
//        setGone(ui.layout_form_input_new_pass);
//
//        AnimationHelper.fade(ui.layout_form_input_sms_code, true);
//    }
//
//    private void showLayoutInputNewPassword() {
//        isShowFormInputPhoneCheck = false;
//
//        setGone(ui.layout_form_input_phone);
//        setGone(ui.layout_form_input_sms_code);
//        setVisible(ui.layout_form_input_new_pass);
//
//        AnimationHelper.fade(ui.layout_form_input_new_pass, true);
//    }
//
//    private String smsCode = "";
//
//    @Override
//    public void setCodeSMS(String code) {
//        smsCode = code;
//        ui.blockEditTextAuthPhone.setText(code);
//    }
//
//    @Override
//    public void verifySMSSuccess() {
//        setGone(ui.tvWrongSMSCode);
//        setVisible(ui.btnShowFormInputNewPassword);
//        setGone(ui.btnReSendSmsCode);
//    }
//
//    @Override
//    public void verifySMSFailed() {
//        setVisible(ui.tvWrongSMSCode);
//        setGone(ui.btnShowFormInputNewPassword);
//        setVisible(ui.btnReSendSmsCode);
//    }
//
//    @Override
//    public BaseUiContainer getUiContainer() {
//        return new FragmentForgotPasswordView.UIContainer();
//    }
//
//    @Override
//    public int getViewId() {
//        return R.layout.layout_fragment_forgot_password;
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
//        ///////////////////////////////////////////////////////////////////////////
//        // layout_form_input_phone
//        ///////////////////////////////////////////////////////////////////////////
//        @UiElement(R.id.layout_form_input_phone)
//        public View layout_form_input_phone;
//
//        @UiElement(R.id.edtUserPhoneRegister)
//        public EditText edtUserPhoneRegister;
//
//        @UiElement(R.id.btnShowFormInputSmsCode)
//        public View btnShowFormInputSmsCode;
//
//        ///////////////////////////////////////////////////////////////////////////
//        // layout_form_input_sms_code
//        ///////////////////////////////////////////////////////////////////////////
//
//        @UiElement(R.id.layout_form_input_sms_code)
//        public View layout_form_input_sms_code;
//
//        @UiElement(R.id.blockEditTextAuthPhone)
//        public BlockEditText blockEditTextAuthPhone;
//
//        @UiElement(R.id.tvWrongSMSCode)
//        public View tvWrongSMSCode;
//
//        @UiElement(R.id.btnShowFormInputNewPassword)
//        public View btnShowFormInputNewPassword;
//
//        @UiElement(R.id.btnReSendSmsCode)
//        public View btnReSendSmsCode;
//
//        ///////////////////////////////////////////////////////////////////////////
//        // layout_form_input_new_pass
//        ///////////////////////////////////////////////////////////////////////////
//
//        @UiElement(R.id.layout_form_input_new_pass)
//        public View layout_form_input_new_pass;
//
//        @UiElement(R.id.edtNewsPassword)
//        public EditText edtNewsPassword;
//
//        @UiElement(R.id.btnVisiblePassword)
//        public ImageView btnVisiblePassword;
//
//        @UiElement(R.id.btnSubmitNewPass)
//        public View btnSubmitNewPass;
//
//    }
//}
