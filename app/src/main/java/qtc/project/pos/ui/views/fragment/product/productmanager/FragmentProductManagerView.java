package qtc.project.pos.ui.views.fragment.product.productmanager;

import android.view.View;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.product.FragmentProductManager;

public class FragmentProductManagerView extends BaseView<FragmentProductManagerView.UIContainer> implements FragmentProductManagerViewInterface {

    HomeActivity activity;
    FragmentProductManagerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentProductManagerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClickItem();
    }

    private void onClickItem() {
        ui.layoutDMSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addFragment(new FragmentProductManager(), true, null);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductManagerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_product;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutDMSP)
        public LinearLayout layoutDMSP;
    }
}