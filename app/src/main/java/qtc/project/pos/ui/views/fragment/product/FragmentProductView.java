package qtc.project.pos.ui.views.fragment.product;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.product.doitra.FragmentDoiTraHangHoa;
import qtc.project.pos.fragment.product.product_disable.FragmentProductDisable;
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
        ui.title_header.setText("Quản lý sản phẩm");
        ui.tvTitlePermisstion.setText("Danh sách quản lý sản phẩm");
        ui.tvTitlePermisstionDescription.setText("Đây là danh mục sản phẩm ta sẽ quản lý sửa đổi vận hành hệ thống");
        onClickItem();
    }

    private void onClickItem() {
        ui.imageNavLeft.setOnClickListener(view -> {
            if(callback != null)
                callback.onClickBackHeader();
        });
        ui.layoutDMSP.setOnClickListener(view -> {
            if (callback!=null)
                callback.changToFragmentCategoryProduct();
        });

        ui.layoutDSSP.setOnClickListener(view -> {
            if (callback!=null)
                callback.changToFragmentListProduct();
        });
        ui.layoutQLLH.setOnClickListener(v -> {
            if (callback!=null)
                callback.changToFragmentLoHangManager();
        });

        ui.layoutDTHH.setOnClickListener(v -> {
            if (callback!=null)
                callback.changToFragmentDoiTraHangHoa();
        });

        ui.layoutVHH.setOnClickListener(v -> {
            if (callback!=null)
                callback.changToFragmentDisableProduct();
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
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.imvAction1)
        public ImageView imvAction1;

        @UiElement(R.id.tvTitlePermisstion)
        public TextView tvTitlePermisstion;

        @UiElement(R.id.tvTitlePermisstionDescription)
        public TextView tvTitlePermisstionDescription;

        @UiElement(R.id.layoutDMSP)
        public LinearLayout layoutDMSP;

        @UiElement(R.id.layoutDSSP)
        public LinearLayout layoutDSSP;

        @UiElement(R.id.layoutQLLH)
        public LinearLayout layoutQLLH;

        @UiElement(R.id.layoutDTHH)
        public LinearLayout layoutDTHH;

        @UiElement(R.id.layoutVHH)
        public LinearLayout layoutVHH;
    }
}
