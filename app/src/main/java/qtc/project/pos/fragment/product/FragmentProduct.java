package qtc.project.pos.fragment.product;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.home.FragmentHomeViewCallback;
import qtc.project.pos.ui.views.fragment.home.FragmentHomeViewInterface;
import qtc.project.pos.ui.views.fragment.product.FragmentProductView;
import qtc.project.pos.ui.views.fragment.product.FragmentProductViewCallback;
import qtc.project.pos.ui.views.fragment.product.FragmentProductViewInterface;

public class FragmentProduct extends BaseFragment<FragmentProductViewInterface, BaseParameters> implements FragmentProductViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentProductViewInterface getViewInstance() {
        return new FragmentProductView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onClickBackHeader() {
        if (activity != null)
            activity.checkBack();
    }
}
