package qtc.project.pos.ui.views.fragment.product.productlist;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.ProductListAdapter;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductView;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductViewInterface;

public class FragmentProductListCategoryView extends BaseView<FragmentProductListCategoryView.UIContainer> implements FragmentProductListCategoryViewInterface {

    HomeActivity activity;
    FragmentProductListCategoryViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentProductListCategoryViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();

    }

    @Override
    public void mappingRecyclerView(ArrayList<ProductListModel> list) {
        ProductListAdapter adapter = new ProductListAdapter(activity, list);
        ui.recycler_view_list_product.setLayoutManager(new GridLayoutManager(activity, 2));
        ui.recycler_view_list_product.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new ProductListAdapter.ProductListAdapterListener() {
            @Override
            public void setOnClick(ProductListModel model) {
                if (callback!=null)
                    callback.goToProductListDetail(model);
            }
        });
    }


    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onBackprogress();
                }
            }
        });
    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductListCategoryView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_product_list_category;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_list_product)
        public RecyclerView recycler_view_list_product;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;


    }
}
