package qtc.project.pos.ui.views.fragment.account.login;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import qtc.project.pos.R;

public class LoginView extends BaseView<LoginView.UiContainer> implements LoginViewInterface {
    private boolean isVisiblePassword;
    private boolean isLoginAdmin = false;
    private LoginViewCallback callback;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void initialize(final LoginViewCallback callback) {
        this.callback = callback;

        ui.btnBackHeader.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
            @Override
            public void onClick(View v) {
                callback.onClickBackHeader();
            }
        });

        ui.btnForgotPassword.setPaintFlags(ui.btnForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        ui.edtLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (ui.edtLoginPhone.getText().length() > 0) {
                    ui.edtLoginPhone.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ui.edtLoginPhone.getText().length() > 0) {
                    ui.edtLoginPhone.setError(null);
                }
            }
        });
        ui.edtLoginPassowrd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ui.edtLoginPassowrd.getText().length() > 0) {
                    ui.edtLoginPassowrd.setError(null);
                }
            }
        });
        ui.edtLoginPassowrd.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doLogin();
            }
            return false;
        });

        ui.btnVisiblePassword.setOnClickListener(v -> {
            if (!isVisiblePassword) {
                isVisiblePassword = true;
                ui.edtLoginPassowrd.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                if (!"".contentEquals(ui.edtLoginPassowrd.getText()))
                    ui.edtLoginPassowrd.setSelection(ui.edtLoginPassowrd.getText().length());
                ui.btnVisiblePassword.setBackgroundResource(R.drawable.eyes_selected_64);
            } else {
                isVisiblePassword = false;
                ui.edtLoginPassowrd.setInputType(129);
                if (!"".contentEquals(ui.edtLoginPassowrd.getText()))
                    ui.edtLoginPassowrd.setSelection(ui.edtLoginPassowrd.getText().length());
                ui.btnVisiblePassword.setBackgroundResource(R.drawable.ic_hide_eys);
            }
        });


        ui.btnLogin.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
            @Override
            public void onClick(View v) {
                AppUtils.hideKeyBoard(ui.btnLogin);
                doLogin();
            }
        });

        ui.btnChangeLoginAdminForm.setOnClickListener(view -> {
            if (isLoginAdmin) {
                isLoginAdmin = false;
                ui.btnChangeLoginAdminForm.setText("Đăng nhập quản lý");
                setVisible(ui.layoutSocialLogin);
                setVisible(ui.btnShowRegisterForm);
            } else {
                isLoginAdmin = true;
                ui.btnChangeLoginAdminForm.setText("Đăng nhập người dùng");
                setGone(ui.layoutSocialLogin);
                setGone(ui.btnShowRegisterForm);
            }
        });

        ui.btnForgotPassword.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
            @Override
            public void onClick(View v) {
                callback.onClickForgotPassword();
            }
        });

        ui.btnShowRegisterForm.setOnClickListener(new ScaleTouchListener(confScaleTouch) {
            @Override
            public void onClick(View v) {
                callback.onClickShowRegister();
            }
        });

        ui.btnLoginWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLoginWithFacebook();
            }
        });

        ui.btnLoginWithGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onLoginWithGoogle();
            }
        });
    }

    private void doLoginWithFacebook() {
        ui.login_button_facebook.performClick();
    }

    private void doLogin() {
        if (!TextUtils.isEmpty(ui.edtLoginPhone.getText()) && !TextUtils
                .isEmpty(ui.edtLoginPhone.getText())) {

            callback.onClickLogin(ui.edtLoginPhone.getText()
                    .toString(), ui.edtLoginPassowrd
                    .getText()
                    .toString());
        } else if (TextUtils.isEmpty(ui.edtLoginPhone.getText())) {
            ui.edtLoginPhone.setError("Nhập username");
            ui.edtLoginPhone.requestFocus();
        } else {
            ui.edtLoginPassowrd.setError("Nhập mật khẩu");
            ui.edtLoginPassowrd.requestFocus();
        }
    }

    public void showLoginLayout() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                setGone(ui.rLayoutSplash);
//                setVisible(ui.rLayoutLogin);
//                ObjectAnimator anim = ObjectAnimator.ofFloat(ui.rLayoutLogin, "translationY", 300, 0);
//                anim.setDuration(500);
//                anim.start();
//            }
//        }, 1000);
    }

    @Override
    public void setUpFacebookLoginButton(Fragment fragment, CallbackManager callbackManager) {
        ui.login_button_facebook.setFragment(fragment);
        ui.login_button_facebook.setReadPermissions("public_profile");
        ui.login_button_facebook.setReadPermissions("email");
        ui.login_button_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                Toast.makeText(getContext(), "login facebook success", Toast.LENGTH_SHORT).show();

                callback.loginFacebookSuccess(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "login facebook cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getContext(), "login facebook error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void showRootViewLogin() {
        setVisible(ui.layoutRootViewLogin);
    }

    @Override
    public void hideRootViewLogin() {
        setGone(ui.layoutRootViewLogin);
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new UiContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_login;
    }

    public static class UiContainer extends BaseUiContainer {

        @UiElement(R.id.layoutRootViewLogin)
        public View layoutRootViewLogin;

        @UiElement(R.id.btnBackHeader)
        public View btnBackHeader;

        @UiElement(R.id.edtLoginPhone)
        public EditText edtLoginPhone;

        @UiElement(R.id.edtLoginPassowrd)
        public EditText edtLoginPassowrd;

        @UiElement(R.id.btnVisiblePassword)
        public View btnVisiblePassword;

        @UiElement(R.id.btnLoginButton)
        public View btnLogin;

        @UiElement(R.id.btnForgotPassword)
        public TextView btnForgotPassword;

        @UiElement(R.id.btnShowRegisterForm)
        public View btnShowRegisterForm;

        @UiElement(R.id.login_button_facebook)
        public LoginButton login_button_facebook;

        @UiElement(R.id.btnLoginFacebook)
        public View btnLoginWithFacebook;

        @UiElement(R.id.btnLoginGoogle)
        public View btnLoginWithGoogle;

        @UiElement(R.id.btnChangeLoginAdminForm)
        public TextView btnChangeLoginAdminForm;

        @UiElement(R.id.layoutSocialLogin)
        public View layoutSocialLogin;
    }
}
