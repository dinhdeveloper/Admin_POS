package qtc.project.pos.ui.views.activity.login;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.LoginActivity;

public interface ActivityLoginViewInterface extends BaseViewInterface {
    void initialize(LoginActivity activity, ActivityLoginViewCallback callback);
}
