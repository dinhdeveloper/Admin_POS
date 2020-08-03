package qtc.project.pos.ui.views.fragment.product.productlist;

import android.support.v7.widget.GridLayoutManager;
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
import java.util.Arrays;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.ProductListAdapter;
import qtc.project.pos.fragment.product.productlist.create.FragmentCreateProduct;
import qtc.project.pos.fragment.product.productlist.filter.FragmentFilterSanPham;
import qtc.project.pos.model.ProductListModel;

public class FragmentProductListCategoryView extends BaseView<FragmentProductListCategoryView.UIContainer> implements FragmentProductListCategoryViewInterface {

    ArrayList<ProductListModel> listProduct = new ArrayList<>();
    ProductListAdapter adapter;
    HomeActivity activity;
    FragmentProductListCategoryViewCallback callback;

    boolean enableLoadMore = true;

    @Override
    public void init(HomeActivity activity, FragmentProductListCategoryViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
        initRecyclerview();

    }

    @Override
    public void clearListDataProduct() {
        listProduct.clear();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void mappingRecyclerView(ProductListModel[] list) {
        if (list == null || list.length == 0) {
            if (listProduct.size() == 0)
                showEmptyList();
            return;
        }
        listProduct.addAll(Arrays.asList(list));
        adapter.notifyDataSetChanged();
    }

    private void showEmptyList() {

    }

    public void initRecyclerview() {
        ui.recycler_view_list_product.getRecycledViewPool().clear();
        adapter = new ProductListAdapter(activity, listProduct);
        ui.recycler_view_list_product.setLayoutManager(new GridLayoutManager(activity, 2));
        ui.recycler_view_list_product.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new ProductListAdapter.ProductListAdapterListener() {
            @Override
            public void setOnClick(ProductListModel model) {
                if (callback != null)
                    callback.goToProductListDetail(model);
            }

            @Override
            public void onRequestLoadMoreProduct() {
                if (callback != null && enableLoadMore)
                    callback.loadMore();
            }
        });
    }

    @Override
    public void mappingDataFilterProduct(ProductListModel[] list, String name, String id) {
        if (list != null) {
            ui.layout_filter.setVisibility(View.VISIBLE);
            ui.name_product.setText("Tên : " + name);
            ui.id_product.setText("Mã: " + id);

//            adapter = new ProductListAdapter(activity, list);
//            ui.recycler_view_list_product.setLayoutManager(new GridLayoutManager(activity, 2));
//            ui.recycler_view_list_product.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//
//            adapter.setListener(new ProductListAdapter.ProductListAdapterListener() {
//                @Override
//                public void setOnClick(ProductListModel model) {
//                    if (callback != null)
//                        callback.goToProductListDetail(model);
//                }
//
//                @Override
//                public void onRequestLoadMoreProduct() {
//                    if (callback != null)
//                        callback.loadMore();
//                }
//            });
            if (list == null || list.length == 0) {
                if (listProduct.size() == 0)
                    showEmptyList();
                return;
            }
            listProduct.addAll(Arrays.asList(list));
            adapter.notifyDataSetChanged();

            ui.close_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ui.layout_filter.setVisibility(View.GONE);
                    if (callback != null)
                    {
                        listProduct.clear();
                        adapter.notifyDataSetChanged();
                        callback.callAllData();
                        enableLoadMore = true;
                    }
                }
            });
        }
    }

    @Override
    public void setNoMoreLoading() {
        enableLoadMore = false;
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
                    if (ui.edit_filter.getText().toString() != null) {
                        listProduct.clear();
                        adapter.notifyDataSetChanged();
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
                    listProduct.clear();
                    adapter.notifyDataSetChanged();
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
                listProduct.clear();
                adapter.notifyDataSetChanged();
                ui.edit_filter.setText(null);
                callback.callAllData();
                enableLoadMore = true;
            }
        });

        //filter
        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentFilterSanPham(), true, null);
            }
        });

        //tao moi san pham
        ui.image_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new FragmentCreateProduct(), true, null);
            }
        });
    }

    private void searchProduct(String name) {
        if (name != null) {
            if (callback != null) {
                callback.searchProduct(name);
            }

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
