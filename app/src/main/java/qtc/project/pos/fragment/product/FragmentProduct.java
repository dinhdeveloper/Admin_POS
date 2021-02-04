package qtc.project.pos.fragment.product;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
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
    public void changToFragmentCategoryProduct() {
        if (activity!=null)
            activity.changToFragmentCategoryProduct();
    }

    @Override
    public void changToFragmentListProduct() {
        if (activity!=null)
            activity.changToFragmentListProduct();
    }

    @Override
    public void changToFragmentLoHangManager() {
        if (activity!=null)
            activity.changToFragmentLoHangManager();
    }

    @Override
    public void changToFragmentDoiTraHangHoa() {
        if (activity!=null)
            activity.changToFragmentDoiTraHangHoa();
    }

    @Override
    public void changToFragmentDisableProduct() {
        if (activity!=null)
            activity.changToFragmentDisableProduct();
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
