package qtc.project.pos.fragment.home;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.home.FragmentHomeView;
import qtc.project.pos.ui.views.fragment.home.FragmentHomeViewCallback;
import qtc.project.pos.ui.views.fragment.home.FragmentHomeViewInterface;

public class FragmentHome extends BaseFragment<FragmentHomeViewInterface, BaseParameters> implements FragmentHomeViewCallback {
    @Override
    protected void initialize() {
        HomeActivity activity = (HomeActivity) getActivity();
        view.init(activity, this);

    }

    @Override
    protected FragmentHomeViewInterface getViewInstance() {
        return new FragmentHomeView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

}
