package qtc.project.pos.ui.views.fragment.product.doitra;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrClassicFrameLayout;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrDefaultHandler;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrFrameLayout;
import b.laixuantam.myaarlibrary.widgets.cptr.recyclerview.RecyclerAdapterWithHF;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.ProductListAdapter;
import qtc.project.pos.adapter.product.doitra.ProductListDTHHAdapter;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.fragment.product.doitra.FragmentFilterDoiTraHangHoa;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageReturnModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;

public class FragmentDoiTraHangHoaView extends BaseView<FragmentDoiTraHangHoaView.UIContainer> implements FragmentListBaseViewInterface {

    HomeActivity activity;
    FragmentListBaseViewCallback callback;
    private RecyclerAdapterWithHF recyclerAdapterWithHF;
    private ArrayList<OptionModel> listDatas = new ArrayList<>();
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private boolean isRefreshList = false;
    boolean enableLoadMore = true;

    ProductListDTHHAdapter productListAdapter;

    @Override
    public void init(HomeActivity activity, FragmentListBaseViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(), activity);
        ui.title_header.setText("Đổi trả hàng hóa");
        ui.edit_filter.setHint("Mã trả hàng");
        ui.actionAdd.setVisibility(View.GONE);
        ui.actionFilter.setVisibility(View.VISIBLE);
        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback!=null)
                callback.onClickBackHeader();
        });

        ui.actionFilter.setOnClickListener(v -> {
            if (callback!=null)
                callback.onClickFilter();
        });

        ui.edit_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (timer != null)
                    timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {

                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            String key = s.toString().trim();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    AppUtils.hideKeyBoard(getView());
                                    listDatas.clear();
                                    productListAdapter.notifyDataSetChanged();
                                    ui.recycler_view_list_order.getRecycledViewPool().clear();
                                    callback.onRequestSearchWithFilter( key);
                                }
                            });
                        }

                    }, DELAY);
                } else {
                    if (!isRefreshList) {
                        AppUtils.hideKeyBoard(getView());
                        listDatas.clear();
                        productListAdapter.notifyDataSetChanged();
                        ui.recycler_view_list_order.getRecycledViewPool().clear();
                        callback.onRequestSearchWithFilter( "");
                    }
                }
            }
        });

        setUpListData();
    }

    private void setUpListData() {
        ui.recycler_view_list_order.getRecycledViewPool().clear();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity,  LinearLayoutManager.VERTICAL, false);
        ui.recycler_view_list_order.setLayoutManager(linearLayoutManager);

        //todo setup list with adapter

        productListAdapter = new ProductListDTHHAdapter(getContext(), listDatas);

        productListAdapter.setListener(item -> {
            if (callback != null)
                callback.onItemListSelected(item);
        });

        recyclerAdapterWithHF = new RecyclerAdapterWithHF(productListAdapter);

        ui.recycler_view_list_order.setAdapter(recyclerAdapterWithHF);

        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);

        ui.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler(true) {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                AppUtils.hideKeyBoard(getView());
                isRefreshList = true;
                ui.edit_filter.setText("");
                listDatas.clear();
                productListAdapter.notifyDataSetChanged();
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

        ui.ptrClassicFrameLayout.setOnLoadMoreListener(() -> handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                if (callback != null)
                    callback.onRequestLoadMoreList();

            }
        }, 100));
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
        PackageReturnModel[] arrOrder = (PackageReturnModel[]) dataList.getData();
        for (PackageReturnModel itemOrderModel : arrOrder) {
            OptionModel optionModel = new OptionModel();
            optionModel.setDtaCustom(itemOrderModel);
            listDatas.add(optionModel);
//            if (productListAdapter != null) {
//                productListAdapter.getListData().add(optionModel);
//                productListAdapter.getListDataBackup().add(optionModel);
//            }
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
        productListAdapter.notifyDataSetChanged();
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
        productListAdapter.notifyDataSetChanged();
        ui.recycler_view_list_order.getRecycledViewPool().clear();
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentDoiTraHangHoaView.UIContainer();
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

        @UiElement(R.id.imvAction1)
        public ImageView imvAction1;

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
