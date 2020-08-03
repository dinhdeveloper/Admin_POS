package qtc.project.pos.ui.views.fragment.product.category.categoryproduct;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.category.ProductCategoryAdapter;
import qtc.project.pos.fragment.product.productcategory.FragmentCreateProductCategory;
import qtc.project.pos.model.ProductCategoryModel;

public class FragmentCategoryProductView extends BaseView<FragmentCategoryProductView.UIContainer> implements FragmentCategoryProductViewInterface {

    HomeActivity activity;
    FragmentCategoryProductViewCallback callback;
    ProductCategoryAdapter categoryAdapter;
    ArrayList<ProductCategoryModel> listCategory = new ArrayList<>();
    boolean enableLoadMore = true;

    @Override
    public void init(HomeActivity activity, FragmentCategoryProductViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(), activity);
        initRecyclerview() ;
        onClickBack();
    }

    private void onClickBack() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.setBackProgress();
            }
        });

        ui.image_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.replaceFragment(new FragmentCreateProductCategory(), true, null);
            }
        });


        ui.edit_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (categoryAdapter != null)
                    categoryAdapter.getFilter().filter(s);
                    enableLoadMore = false;
            }
        });

        ui.image_close.setOnClickListener(v -> {
            ui.edit_filter.setText(null);
            enableLoadMore = true;
            callback.callAllData();
        });
    }


    @Override
    public void initGetListCategoryProduct(ProductCategoryModel[] list) {
        if (list == null || list.length == 0) {
            if (listCategory.size() == 0)
                showEmptyList();
            return;
        }
        listCategory.addAll(Arrays.asList(list));
        categoryAdapter.notifyDataSetChanged();
        categoryAdapter.getListData().addAll(listCategory);
        categoryAdapter.getListDataBackup().addAll(listCategory);
    }

    @Override
    public void setNoMoreLoading() {
        enableLoadMore = false;
    }

    private void showEmptyList() {
    }

    public void initRecyclerview(){
        ui.recycler_view_category_product.getRecycledViewPool().clear();
        categoryAdapter = new ProductCategoryAdapter(activity,listCategory);
        ui.recycler_view_category_product.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false));
        ui.recycler_view_category_product.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        categoryAdapter.setListener(new ProductCategoryAdapter.ProductCategoryAdapterListener() {
            @Override
            public void onClickItem(ProductCategoryModel model) {
                if (callback!=null){
                    callback.onSendData(model);
                }
            }

            @Override
            public void onRequestLoadMoreProduct() {
                if (callback!=null){
                    callback.loadMore();
                }
            }
        });
    }


    @Override

    public BaseUiContainer getUiContainer() {
        return new FragmentCategoryProductView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_category_product;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_category_product)
        public RecyclerView recycler_view_category_product;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_create)
        public ImageView image_create;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_close)
        public ImageView image_close;

    }
}
