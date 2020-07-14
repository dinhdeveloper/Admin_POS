package qtc.project.pos.ui.views.fragment.history;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.home.FragmentHomeViewCallback;

public interface FragmentOrderViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentOrderViewCallback callback);
}
