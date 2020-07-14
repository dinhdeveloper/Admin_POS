//package qtc.project.pos.ui.views.fragment.list_base;
//
//import android.annotation.SuppressLint;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import b.laixuantam.myaarlibrary.base.BaseUiContainer;
//import b.laixuantam.myaarlibrary.base.BaseView;
//import b.laixuantam.myaarlibrary.helper.AppUtils;
//import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
//import b.laixuantam.myaarlibrary.widgets.cptr.PtrClassicFrameLayout;
//import b.laixuantam.myaarlibrary.widgets.cptr.PtrDefaultHandler;
//import b.laixuantam.myaarlibrary.widgets.cptr.PtrFrameLayout;
//import b.laixuantam.myaarlibrary.widgets.cptr.loadmore.OnLoadMoreListener;
//import b.laixuantam.myaarlibrary.widgets.cptr.recyclerview.RecyclerAdapterWithHF;
//import b.laixuantam.myaarlibrary.widgets.dialog.single_choise.MyCustomSingleChoise;
//import b.laixuantam.myaarlibrary.widgets.popupmenu.ActionItem;
//import b.laixuantam.myaarlibrary.widgets.popupmenu.MyCustomPopupMenu;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.dialog.option.OptionModel;
//import qtc.project.pos.model.BaseResponseModel;
//
//public class FragmentAdminManagerListBaseView extends BaseView<FragmentAdminManagerListBaseView.UIContainer> implements FragmentAdminManagerListBaseViewInterface {
//
//    private HomeActivityBackup activity;
//    private FragmentAdminManagerListBaseViewCallback callback;
////    private OrderFilterManagerAdapter orderManagerAdapter;
//    private RecyclerAdapterWithHF recyclerAdapterWithHF;
//    private ArrayList<OptionModel> listDatas = new ArrayList<>();
//    private Timer timer = new Timer();
//    private final long DELAY = 1000; // in ms
//    private boolean isRefreshList = false;
//    private String order_status, filter_string;
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public void init(HomeActivityBackup activity, FragmentAdminManagerListBaseViewCallback callback) {
//        this.activity = activity;
//        this.callback = callback;
//
//        KeyboardUtils.setupUI(getView(), activity);
//
//        ui.tvTitleHeader.setText("Quản lý giao dịch");
//
//        setGone(ui.actionAdd);
//        setVisible(ui.actionFilter);
//
//        ui.btnBackHeader.setOnClickListener(view -> {
//            if (callback != null)
//                callback.onClickBackHeader();
//        });
//
//        ui.actionFilter.setOnClickListener(view -> {
//            showDialogSelectOrderStatus();
//        });
//
//        ui.edit_filter_.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (timer != null)
//                    timer.cancel();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s.length() >= 1) {
//
//                    timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            String key = s.toString().trim();
//                            handler.post(new Runnable() {
//                                @Override
//                                public void run() {
//                                    AppUtils.hideKeyBoard(getView());
//                                    listDatas.clear();
////                                    orderManagerAdapter.notifyDataSetChanged();
//                                    ui.recycler_view_list_.getRecycledViewPool().clear();
//                                    callback.onRequestSearchWithFilter(order_status, key);
//                                }
//                            });
//                        }
//
//                    }, DELAY);
//                } else {
//                    if (!isRefreshList) {
//                        AppUtils.hideKeyBoard(getView());
//                        listDatas.clear();
////                        orderManagerAdapter.notifyDataSetChanged();
//                        ui.recycler_view_list_.getRecycledViewPool().clear();
//                        callback.onRequestSearchWithFilter(order_status, "");
//                    }
//                }
//            }
//        });
//
//        initOrderStatusFilter();
//        setUpListData();
//    }
//
//    @Override
//    public void showEmptyList() {
//        setVisible(ui.layoutEmptyList);
//        setGone(ui.ptrClassicFrameLayout);
//    }
//
//    @Override
//    public void hideEmptyList() {
//        setGone(ui.layoutEmptyList);
//        setVisible(ui.ptrClassicFrameLayout);
//    }
//
//    private void setUpListData() {
//        ui.recycler_view_list_.getRecycledViewPool().clear();
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        ui.recycler_view_list_.setLayoutManager(linearLayoutManager);
//
//        //todo setup list with adapter
//
////        orderManagerAdapter = new OrderFilterManagerAdapter(getContext(), listDatas);
////
////        orderManagerAdapter.setListener(new OrderFilterManagerAdapter.OrderFilterManagerAdapterListener() {
////            @Override
////            public void onItemOrderSelected(OrderModel item, View itemView, int position) {
////                if (callback != null)
////                    callback.showDetailOrder(item);
////            }
////
////        });
//
////        recyclerAdapterWithHF = new RecyclerAdapterWithHF(orderManagerAdapter);
//
//        ui.recycler_view_list_.setAdapter(recyclerAdapterWithHF);
//
//        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);
//
//        ui.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler(true) {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                AppUtils.hideKeyBoard(getView());
//                isRefreshList = true;
////                ui.edit_filter_transaction.setText("");
//                listDatas.clear();
////                orderManagerAdapter.notifyDataSetChanged();
//                ui.recycler_view_list_.getRecycledViewPool().clear();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        ui.ptrClassicFrameLayout.refreshComplete();
//
//                        if (callback != null) {
//                            callback.refreshLoadingList();
//                            isRefreshList = false;
//                        }
//                    }
//                }, 100);
//
//
//            }
//        });
//
//        ui.ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//
//            @Override
//            public void loadMore() {
//                handler.postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        if (callback != null)
//                            callback.onRequestLoadMoreList();
//
//                    }
//                }, 100);
//            }
//        });
//    }
//
//    @Override
//    public void setDataList(BaseResponseModel arrDatas) {
//        ui.recycler_view_list_.getRecycledViewPool().clear();
//
//        if (arrDatas.getData() == null || arrDatas.getData().length == 0) {
//            if (listDatas.size() == 0)
//                showEmptyList();
//            return;
//        }
//
//        hideEmptyList();
//
////        listDatas.addAll(Arrays.asList(arrDatas));
//
//        recyclerAdapterWithHF.notifyDataSetChanged();
//        ui.ptrClassicFrameLayout.loadMoreComplete(true);
//        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);
//    }
//
//    @Override
//    public void setNoMoreLoading() {
//        ui.ptrClassicFrameLayout.loadMoreComplete(true);
//        ui.ptrClassicFrameLayout.setLoadMoreEnable(false);
//    }
//
//    @Override
//    public void resetListData() {
//        listDatas.clear();
////        orderManagerAdapter.notifyDataSetChanged();
//        ui.recycler_view_list_.getRecycledViewPool().clear();
//    }
//
//    @Override
//    public void hideRootView() {
//        setGone(ui.layoutRootView);
//    }
//
//    @Override
//    public void showRootView() {
//        setVisible(ui.layoutRootView);
//    }
//
//    private String[] arr_schedule_order_status = new String[6];
//
//    private void initOrderStatusFilter() {
//        arr_schedule_order_status[0] = "Tất cả";
//        arr_schedule_order_status[1] = "Đang chờ";
//        arr_schedule_order_status[2] = "Chờ cọc";
//        arr_schedule_order_status[3] = "Đã cọc";
//        arr_schedule_order_status[4] = "Hoàn tất";
//        arr_schedule_order_status[5] = "Đã hủy";
//    }
//
//    private int positionOrderStatusSelected = -1;
//
//    private void showDialogSelectOrderStatus() {
//        MyCustomSingleChoise option = new MyCustomSingleChoise(getContext(), "Trạng thái đơn hàng", positionOrderStatusSelected, arr_schedule_order_status, new MyCustomSingleChoise.MyCustomSingleChoiseListener() {
//            @Override
//            public void onItemSelected(int pos) {
//
//                if (pos == 0) {
//                    order_status = "";
//                } else
//                    order_status = String.valueOf(pos);
//
//                if (callback != null) {
//                    listDatas.clear();
//                    String keyFilter = !TextUtils.isEmpty(ui.edit_filter_.getText()) ? ui.edit_filter_.getText().toString().trim() : "";
//                    callback.onRequestSearchWithFilter(order_status, keyFilter);
//                }
//            }
//        });
//        option.setCancelable(true);
//        option.show();
//    }
//
//    private void showPopupMenu(View view) {
//        ActionItem change_password = new ActionItem(1, "Chi tiết công việc", null);
//        ActionItem employee_tracking = new ActionItem(2, "Danh sách nhân viên", null);
//        int backgroundCustom = ContextCompat.getColor(getContext(), R.color.red);
//        int arrowColorCustom = ContextCompat.getColor(getContext(), R.color.red);
//
//        MyCustomPopupMenu quickAction = new MyCustomPopupMenu(getContext(), backgroundCustom, arrowColorCustom);
//        quickAction.addActionItem(change_password);
//        quickAction.addActionItem(employee_tracking);
//
//        quickAction.setOnActionItemClickListener(new MyCustomPopupMenu.OnActionItemClickListener() {
//            @Override
//            public void onItemClick(MyCustomPopupMenu source, int pos, int actionId) {
//                switch (actionId) {
//                    case 1:
//                        break;
//
//                    case 2:
//                        break;
//                }
//            }
//        });
//
//        quickAction.show(view);
//    }
//
//    @Override
//    public void clearListData() {
//
//    }
//
//    @Override
//    public BaseUiContainer getUiContainer() {
//        return new FragmentAdminManagerListBaseView.UIContainer();
//    }
//
//    @Override
//    public int getViewId() {
//        return R.layout.layout_admin_fragment_list_base;
//    }
//
//    public static class UIContainer extends BaseUiContainer {
//
//        @UiElement(R.id.layoutRootView)
//        public View layoutRootView;
//
//        @UiElement(R.id.btnBackHeader)
//        public View btnBackHeader;
//
//        @UiElement(R.id.tvTitleHeader)
//        public TextView tvTitleHeader;
//
//        @UiElement(R.id.edit_filter)
//        public EditText edit_filter_;
//
//        @UiElement(R.id.btnAction1)
//        public View actionAdd;
//
//        @UiElement(R.id.btnAction2)
//        public View actionFilter;
//
//        @UiElement(R.id.ptrClassicFrameLayout)
//        public PtrClassicFrameLayout ptrClassicFrameLayout;
//
//        @UiElement(R.id.recycler_view_list)
//        public RecyclerView recycler_view_list_;
//
//        @UiElement(R.id.layoutEmptyList)
//        public View layoutEmptyList;
//
//    }
//}
