package qtc.project.pos.ui.views.fragment.product.productlist.filter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;

public class FragmentFilterSanPhamView extends BaseView<FragmentFilterSanPhamView.UIContainer> implements FragmentFilterSanPhamViewInterface {

    HomeActivity activity;
    FragmentFilterSanPhamViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentFilterSanPhamViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    private void onClick() {
        //back
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });

        //quay lai
        ui.layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });
        // tim kiem
        ui.layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.searchProduct(ui.name_product.getText().toString(),ui.id_product.getText().toString());
                }
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentFilterSanPhamView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_filter_product;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.name_product)
        public EditText name_product;

        @UiElement(R.id.id_product)
        public EditText id_product;

        @UiElement(R.id.layout_cancel)
        public LinearLayout layout_cancel;

        @UiElement(R.id.layout_search)
        public LinearLayout layout_search;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
