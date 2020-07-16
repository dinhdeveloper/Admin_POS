package qtc.project.pos.ui.views.fragment.product.productlist;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.ProductListAdapter;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.productcategory.FragmentCreateProductCategory;
import qtc.project.pos.fragment.product.productlist.create.FragmentCreateProduct;
import qtc.project.pos.fragment.product.productlist.filter.FragmentFilterSanPham;
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

    @Override
    public void mappingDataFilterProduct(ArrayList<ProductListModel> list,String name,String id) {
        if (list!=null){
            ui.layout_filter.setVisibility(View.VISIBLE);
            ui.name_product.setText("Tên : "+name);
            ui.id_product.setText("Mã: "+id);

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

            ui.close_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ui.layout_filter.setVisibility(View.GONE);
                    if (callback!=null)
                        callback.callAllData();
                }
            });
        }
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

        //search customer
        ui.edit_filter.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //ui.edit_filter.setInputType();
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString()!=null){
                        searchProduct(ui.edit_filter.getText().toString());
                        return true;
                    }
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null) {
                    searchProduct(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //xos search
        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
                callback.callAllData();
            }
        });

        //filter
        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentFilterSanPham(),true,null);
            }
        });

        //tao moi san pham
        ui.image_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new FragmentCreateProduct(),true,null);
            }
        });
    }

    private void searchProduct(String name) {
        if (name!=null){
            if (callback!=null)
                callback.searchProduct(name);
        }
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

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.layout_filter)
        public RelativeLayout layout_filter;

        @UiElement(R.id.name_product)
        public TextView name_product;

        @UiElement(R.id.id_product)
        public TextView id_product;

        @UiElement(R.id.close_layout)
        public ImageView close_layout;

        @UiElement(R.id.image_create)
        public ImageView image_create;


    }
}
