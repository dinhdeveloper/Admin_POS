package qtc.project.pos.ui.views.fragment.account.login;

import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;

public interface LoginViewInterface extends BaseViewInterface {
    void initialize(LoginViewCallback callback);

    void showRootViewLogin();

    void hideRootViewLogin();

    void setUpFacebookLoginButton(Fragment fragment, CallbackManager callbackManager);
}
