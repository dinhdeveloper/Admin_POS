package qtc.project.pos.ui.views.activity.home_activity;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;

public interface HomeActivityViewInterface extends BaseViewInterface {

    void init(HomeActivity activity, HomeActivityViewCallback callback);

    void openDrawer();

    void closeDrawer();

    boolean isDrawerOpen();

}
