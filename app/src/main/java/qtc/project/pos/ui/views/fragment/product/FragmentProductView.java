package qtc.project.pos.ui.views.fragment.product;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.product.doitra.FragmentDoiTraHangHoa;
import qtc.project.pos.fragment.product.productcategory.FragmentCategoryProduct;
import qtc.project.pos.fragment.product.productlist.FragmentProductList;
import qtc.project.pos.fragment.product.quanlylohang.FragmentQuanLyLoHang;

public class FragmentProductView  extends BaseView<FragmentProductView.UIContainer> implements FragmentProductViewInterface {

    HomeActivity activity;
    FragmentProductViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentProductViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        
        onClickItem();
    }

    private void onClickItem() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null)
                    callback.onClickBackHeader();
            }
        });
        ui.layoutDMSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addFragment(new FragmentCategoryProduct(),true,null);
            }
        });

        ui.layoutDSSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.addFragment(new FragmentProductList(),true,null);
            }
        });
        ui.layoutQLLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentQuanLyLoHang(),true,null);
            }
        });

        ui.layoutDTHH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentDoiTraHangHoa(),true,null);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_product;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutDMSP)
        public LinearLayout layoutDMSP;
        @UiElement(R.id.layoutDSSP)
        public LinearLayout layoutDSSP;
        @UiElement(R.id.layoutQLLH)
        public LinearLayout layoutQLLH;
        @UiElement(R.id.layoutDTHH)
        public LinearLayout layoutDTHH;
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

    }
}
