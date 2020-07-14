package qtc.project.pos.ui.views.fragment.account.register;


import qtc.project.pos.model.UserRegisterModel;

public interface FragmentRegisterViewCallback {
    void onClickBackHeader();

    void onClickShowLoginForm();

    void onSignUp(UserRegisterModel userRegisterModel);

    void onRequestCheckPhoneRegister(UserRegisterModel userRegisterModel);

}
