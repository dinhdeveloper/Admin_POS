package qtc.project.pos.ui.views.fragment.product.category.categoryproduct;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrClassicFrameLayout;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrDefaultHandler;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrFrameLayout;
import b.laixuantam.myaarlibrary.widgets.cptr.loadmore.OnLoadMoreListener;
import b.laixuantam.myaarlibrary.widgets.cptr.recyclerview.RecyclerAdapterWithHF;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.category.ProductCategoryAdapter;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.fragment.product.productcategory.FragmentCreateProductCategory;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;

public class FragmentCategoryProductView extends BaseView<FragmentCategoryProductView.UIContainer> implements FragmentListBaseViewInterface {

    HomeActivity activity;
    FragmentListBaseViewCallback callback;
    ProductCategoryAdapter categoryAdapter;
    private RecyclerAdapterWithHF recyclerAdapterWithHF;
    private ArrayList<OptionModel> listDatas = new ArrayList<>();
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private boolean isRefreshList = false;
    boolean enableLoadMore = true;

    @Override
    public void init(HomeActivity activity, FragmentListBaseViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(), activity);
        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback!=null)
                callback.onClickBackHeader();
        });
        ui.title_header.setText("Danh mục sản phẩm");
        ui.edit_filter.setHint("Tên sản phẩm");
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
        ui.actionAdd.setOnClickListener(v -> {
            if (callback != null)
                callback.onClickAddItem();
        });
        setUpListData();
    }

    private void setUpListData() {
        ui.recycler_view_list_order.getRecycledViewPool().clear();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ui.recycler_view_list_order.setLayoutManager(linearLayoutManager);

        //todo setup list with adapter

        categoryAdapter = new ProductCategoryAdapter(getContext(), listDatas);

        categoryAdapter.setListener(item -> {
            if (callback != null)
                callback.onItemListSelected(item);
        });

        recyclerAdapterWithHF = new RecyclerAdapterWithHF(categoryAdapter);

        ui.recycler_view_list_order.setAdapter(recyclerAdapterWithHF);

        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);

        ui.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler(true) {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                AppUtils.hideKeyBoard(getView());
                isRefreshList = true;
                ui.edit_filter.setText("");
                listDatas.clear();
                categoryAdapter.notifyDataSetChanged();
                ui.recycler_view_list_order.getRecycledViewPool().clear();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ui.ptrClassicFrameLayout.refreshComplete();

                        if (callback != null) {
                            callback.refreshLoadingList();
                            isRefreshList = false;
                        }
                    }
                }, 100);


            }
        });

        ui.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                handler.postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        if (callback != null)
                            callback.onRequestLoadMoreList();

                    }
                }, 100);
            }
        });
    }

    @Override
    public void showEmptyList() {
        setVisible(ui.layoutEmptyList);
        setGone(ui.ptrClassicFrameLayout);
    }

    @Override
    public void hideEmptyList() {
        setGone(ui.layoutEmptyList);
        setVisible(ui.ptrClassicFrameLayout);
    }

    @Override
    public void setDataList(BaseResponseModel dataList) {
        ui.recycler_view_list_order.getRecycledViewPool().clear();

        if (dataList.getData() == null || dataList.getData().length == 0) {
            if (listDatas.size() == 0)
                showEmptyList();
            return;
        }

        hideEmptyList();

//        listDatas.addAll(Arrays.asList(arrDatas));
        ProductCategoryModel[] arrOrder = (ProductCategoryModel[]) dataList.getData();

        for (ProductCategoryModel itemOrderModel : arrOrder) {
            OptionModel optionModel = new OptionModel();
            optionModel.setDtaCustom(itemOrderModel);
            listDatas.add(optionModel);
            if (categoryAdapter != null) {
                categoryAdapter.getListData().add(optionModel);
                categoryAdapter.getListDataBackup().add(optionModel);
            }
        }

        recyclerAdapterWithHF.notifyDataSetChanged();
        ui.ptrClassicFrameLayout.loadMoreComplete(true);
        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);
    }

    @Override
    public void setNoMoreLoading() {
        ui.ptrClassicFrameLayout.loadMoreComplete(true);
        ui.ptrClassicFrameLayout.setLoadMoreEnable(false);
    }

    @Override
    public void resetListData() {
        listDatas.clear();
        categoryAdapter.notifyDataSetChanged();
        ui.recycler_view_list_order.getRecycledViewPool().clear();
    }

    @Override
    public void hideRootView() {
        setGone(ui.layoutRootView);
    }

    @Override
    public void showRootView() {
        setVisible(ui.layoutRootView);
    }

    @Override
    public void clearListData() {
        listDatas.clear();
        categoryAdapter.notifyDataSetChanged();
        ui.recycler_view_list_order.getRecycledViewPool().clear();
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCategoryProductView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_admin_fragment_list_base;
    }

    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutRootView)
        public View layoutRootView;

        @UiElement(R.id.imageNavLeft)
        public View imageNavLeft;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.btnAction1)
        public ImageView actionAdd;

        @UiElement(R.id.btnAction2)
        public View actionFilter;

        @UiElement(R.id.ptrClassicFrameLayout)
        public PtrClassicFrameLayout ptrClassicFrameLayout;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list_order;

        @UiElement(R.id.layoutEmptyList)
        public View layoutEmptyList;
    }
}
