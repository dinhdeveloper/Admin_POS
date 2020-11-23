package qtc.project.pos.ui.views.fragment.product.product_disable;

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
import qtc.project.pos.adapter.product.ProductListDisableAdapter;
import qtc.project.pos.model.ProductListModel;

public class FragmentProductDisableView extends BaseView<FragmentProductDisableView.UIContainer> implements FragmentProductDisableViewInterface {
    HomeActivity activity;
    FragmentProductDisableViewCallback callback;
    boolean enableLoadMore = true;

    ArrayList<ProductListModel> listProduct = new ArrayList<>();
    ProductListDisableAdapter adapter;

    @Override
    public void init(HomeActivity activity, FragmentProductDisableViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        ui.image_filter.setVisibility(View.GONE);
        ui.image_create.setVisibility(View.GONE);
        ui.tvTitle.setText("Sản phẩm vô hiệu hóa");
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

        setDataView();
    }

    private void searchProduct(String name) {
        if (name != null) {
            if (callback != null) {
                callback.searchProduct(name);
            }

        }
    }

    @Override
    public void clearListDataProduct() {
        listProduct.clear();
        adapter.notifyDataSetChanged();
    }

    private void setDataView() {
        ui.recycler_view_list_product.getRecycledViewPool().clear();
        adapter = new ProductListDisableAdapter(activity, listProduct);
        ui.recycler_view_list_product.setLayoutManager(new GridLayoutManager(activity, 2));
        ui.recycler_view_list_product.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new ProductListDisableAdapter.ProductListDisableListener() {
            @Override
            public void setOnClick(ProductListModel model) {
                if (callback!=null)
                    callback.goToProductDisable(model);
            }

            @Override
            public void onRequestLoadMoreProduct() {
                if (callback!=null)
                    callback.loadMore();
            }
        });
    }

    @Override
    public void setNoMoreLoading() {
        enableLoadMore = false;
    }

    @Override
    public void setListData(ProductListModel[] list) {
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

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductDisableView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_product_list_category;
    }


    public class UIContainer extends BaseUiContainer {
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

        @UiElement(R.id.tvTitle)
        public TextView tvTitle;


    }
}
