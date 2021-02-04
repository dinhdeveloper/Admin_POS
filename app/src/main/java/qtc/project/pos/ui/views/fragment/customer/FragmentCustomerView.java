package qtc.project.pos.ui.views.fragment.customer;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrClassicFrameLayout;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrDefaultHandler;
import b.laixuantam.myaarlibrary.widgets.cptr.PtrFrameLayout;
import b.laixuantam.myaarlibrary.widgets.cptr.recyclerview.RecyclerAdapterWithHF;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.customer.ListCustomerAdapter;
import qtc.project.pos.adapter.product.ProductListAdapter;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;

public class FragmentCustomerView extends BaseView<FragmentCustomerView.UIContainer> implements FragmentListBaseViewInterface {
    HomeActivity activity;
    FragmentListBaseViewCallback callback;
    private RecyclerAdapterWithHF recyclerAdapterWithHF;
    private ArrayList<OptionModel> listDatas = new ArrayList<>();
    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    private boolean isRefreshList = false;
    boolean enableLoadMore = true;

    ListCustomerAdapter listCustomerAdapter;

    @Override
    public void init(HomeActivity activity, FragmentListBaseViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        ui.layout_permisition.setVisibility(View.VISIBLE);
        ui.title_header.setText("Quản lý khách hàng");
        ui.tvTitlePermisstion.setText("Danh sách quản lý khách hàng");
        ui.tvTitlePermisstionDescription.setText("Đây là danh mục khách hàng ta sẽ quản lý sửa đổi vận hành hệ thống");
        ui.edit_filter.setHint("Tên khách hàng");
        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback!=null)
                callback.onClickBackHeader();
        });
        ui.actionAdd.setOnClickListener(v -> {
            if (callback!=null)
                callback.onClickAddItem();
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
                                    listCustomerAdapter.notifyDataSetChanged();
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
                        listCustomerAdapter.notifyDataSetChanged();
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

        listCustomerAdapter = new ListCustomerAdapter(getContext(), listDatas);

        listCustomerAdapter.setListener(item -> {
            if (callback != null) {
                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.custom_popup_choose_customer, null);
                LinearLayout item_history_order = popupView.findViewById(R.id.item_history_order);
                LinearLayout item_detail = popupView.findViewById(R.id.item_detail);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(popupView);
                AlertDialog dialog = alert.create();
                //dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                item_history_order.setOnClickListener(v -> {
                    if (callback != null) {
                        callback.onDeleteItemSelected(item);
                        dialog.dismiss();
                    }
                });

                item_detail.setOnClickListener(v -> {
                    if (callback != null) {
                        callback.onItemListSelected(item);
                        dialog.dismiss();
                    }
                });
            }
        });

        recyclerAdapterWithHF = new RecyclerAdapterWithHF(listCustomerAdapter);

        ui.recycler_view_list_order.setAdapter(recyclerAdapterWithHF);

        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);

        ui.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler(true) {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                AppUtils.hideKeyBoard(getView());
                isRefreshList = true;
                ui.edit_filter.setText("");
                listDatas.clear();
                listCustomerAdapter.notifyDataSetChanged();
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
        CustomerModel[] arrOrder = (CustomerModel[]) dataList.getData();
        for (CustomerModel itemOrderModel : arrOrder) {
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
        listCustomerAdapter.notifyDataSetChanged();
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
        listCustomerAdapter.notifyDataSetChanged();
        ui.recycler_view_list_order.getRecycledViewPool().clear();
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCustomerView.UIContainer();
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

        @UiElement(R.id.layout_permisition)
        public View layout_permisition;

        @UiElement(R.id.tvTitlePermisstionDescription)
        public TextView tvTitlePermisstionDescription;

        @UiElement(R.id.tvTitlePermisstion)
        public TextView tvTitlePermisstion;

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
