package qtc.project.pos.fragment.product;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.product.productmanager.FragmentProductManagerView;
import qtc.project.pos.ui.views.fragment.product.productmanager.FragmentProductManagerViewCallback;
import qtc.project.pos.ui.views.fragment.product.productmanager.FragmentProductManagerViewInterface;

public class FragmentProductManager extends BaseFragment<FragmentProductManagerViewInterface, BaseParameters> implements FragmentProductManagerViewCallback {
    @Override
    protected void initialize() {
        HomeActivity activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentProductManagerViewInterface getViewInstance() {
        return new FragmentProductManagerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }
}
