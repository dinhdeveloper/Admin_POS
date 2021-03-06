package qtc.project.pos.ui.views.activity.login;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.LoginActivity;

public class ActivityLoginView extends BaseView<ActivityLoginView.UiContainer> implements ActivityLoginViewInterface {

    LoginActivity activity;
    ActivityLoginViewCallback callback;

    @Override
    public void initialize(LoginActivity activity, ActivityLoginViewCallback callback) {
        this.callback = callback;
        this.activity = activity;

        KeyboardUtils.setupUI(getView(), activity);

        ui.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.hideKeyBoard(ui.btnLogin);
                doLogin();
            }
        });

        ui.pass_word.setOnEditorActionListener((v, actionId, event) -> {
            if (EditorInfo.IME_ACTION_DONE == actionId) {
                doLogin();
                return true;
            }
            return false;
        });
    }

    private void doLogin() {
        if (!TextUtils.isEmpty(ui.user_name.getText()) && !TextUtils.isEmpty(ui.pass_word.getText())) {
            callback.onClickLogin(ui.user_name.getText()
                    .toString(), ui.pass_word
                    .getText()
                    .toString());
        } else if (TextUtils.isEmpty(ui.user_name.getText())) {
            ui.user_name.setError("Tên đăng nhập");
            ui.user_name.requestFocus();
        } else {
            ui.pass_word.setError("Mật khẩu");
            ui.pass_word.requestFocus();
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new UiContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.activity_login;
    }

    public class UiContainer extends BaseUiContainer {
        @UiElement(R.id.user_name)
        public EditText user_name;

        @UiElement(R.id.pass_word)
        public EditText pass_word;

        @UiElement(R.id.btnLogin)
        public LinearLayout btnLogin;


    }
}
