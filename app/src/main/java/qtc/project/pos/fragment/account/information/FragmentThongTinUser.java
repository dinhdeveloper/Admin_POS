package qtc.project.pos.fragment.account.information;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.account.information.FragmentThongTinUserView;
import qtc.project.pos.ui.views.fragment.account.information.FragmentThongTinUserViewCallback;
import qtc.project.pos.ui.views.fragment.account.information.FragmentThongTinUserViewInterface;

public class FragmentThongTinUser extends BaseFragment<FragmentThongTinUserViewInterface, BaseParameters> implements FragmentThongTinUserViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentThongTinUserViewInterface getViewInstance() {
        return new FragmentThongTinUserView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity!=null)
            activity.checkBack();
    }
}
