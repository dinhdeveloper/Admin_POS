package qtc.project.pos.ui.views.fragment.history;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.product.FragmentProductView;
import qtc.project.pos.ui.views.fragment.product.FragmentProductViewInterface;

public class FragmentOrderView  extends BaseView<FragmentOrderView.UIContainer> implements FragmentOrderViewInterface {
    @Override
    public void init(HomeActivity activity, FragmentOrderViewCallback callback) {

    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentOrderView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_order;

    }

    public static class UIContainer extends BaseUiContainer{
    }
}
