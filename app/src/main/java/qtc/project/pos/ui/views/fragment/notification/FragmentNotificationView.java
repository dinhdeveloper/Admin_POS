//package qtc.project.pos.ui.views.fragment.notification;
//
//import android.annotation.SuppressLint;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import b.laixuantam.myaarlibrary.base.BaseUiContainer;
//import b.laixuantam.myaarlibrary.base.BaseView;
//import b.laixuantam.myaarlibrary.widgets.cptr.PtrClassicFrameLayout;
//import b.laixuantam.myaarlibrary.widgets.cptr.PtrDefaultHandler;
//import b.laixuantam.myaarlibrary.widgets.cptr.PtrFrameLayout;
//import b.laixuantam.myaarlibrary.widgets.cptr.recyclerview.RecyclerAdapterWithHF;
//import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.adapter.NotificationAdapter;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.helper.Consts;
//import qtc.project.pos.model.NotificationModel;
//
//public class FragmentNotificationView extends BaseView<FragmentNotificationView.UIContainer> implements FragmentNotificationViewInterface {
//
//    private final ArrayList<NotificationModel> listDataNotification = new ArrayList<>();
//    private RecyclerAdapterWithHF recyclerAdapterWithHF;
//    private FragmentNotificationViewCallback callback;
//    private HomeActivityBackup activity;
//
//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public void init(HomeActivityBackup activity, FragmentNotificationViewCallback callback) {
//        this.callback = callback;
//        this.activity = activity;
//
//        ui.tvTitleHeader.setText("Thông báo");
//        setGone(ui.tvTitleHeader);
//
//        ui.btnBackHeader.setOnTouchListener(new ScaleTouchListener(confScaleTouch) {
//            @Override
//            public void onClick(View v) {
//                if (isShowDetailNotify) {
//                    showListNotify();
//                } else
//                    callback.onClickBackHeader();
//            }
//        });
//
//        setupListNotification();
//
//    }
//
//
//    @Override
//    public void setNoMoreLoading() {
//        ui.ptrClassicFrameLayout.loadMoreComplete(true);
//        ui.ptrClassicFrameLayout.setLoadMoreEnable(false);
//    }
//
//    @Override
//    public void setDataNotification(NotificationModel[] data) {
//        if (data == null || data.length == 0) {
//            if (listDataNotification.size() == 0)
//                showEmptyList();
//            return;
//        }
//
//        hideEmptyList();
//
//        listDataNotification.addAll(Arrays.asList(data));
//
//        recyclerAdapterWithHF.notifyDataSetChanged();
//        ui.ptrClassicFrameLayout.loadMoreComplete(true);
//        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);
//    }
//
//    private void hideEmptyList() {
//        setGone(ui.layoutEmptyList);
//        setVisible(ui.recycler_view_list_notification);
//    }
//
//    private void showEmptyList() {
//        setVisible(ui.layoutEmptyList);
//        setGone(ui.recycler_view_list_notification);
//
//    }
//
//    private void setupListNotification() {
//
//        ui.recycler_view_list_notification.getRecycledViewPool().clear();
//
//        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//
//        ui.recycler_view_list_notification.setLayoutManager(manager);
//
//        ui.recycler_view_list_notification.setHasFixedSize(true);
//
//        NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), listDataNotification);
//
//        notificationAdapter.setListener(new NotificationAdapter.NotificationAdapterListener() {
//            @Override
//            public void onItemSelected(NotificationModel item, int pos) {
//                showDetailItemNotify(item, pos);
//            }
//        });
//        recyclerAdapterWithHF = new RecyclerAdapterWithHF(notificationAdapter);
//
//        ui.recycler_view_list_notification.setAdapter(recyclerAdapterWithHF);
//
//        ui.ptrClassicFrameLayout.setLoadMoreEnable(true);
//
//        ui.ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler(true) {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                handler.postDelayed(() -> {
//                    ui.ptrClassicFrameLayout.refreshComplete();
//                    listDataNotification.clear();
//                    callback.onRequestRefreshListNotification();
//                }, 100);
//
//
//            }
//        });
//
//        ui.ptrClassicFrameLayout.setOnLoadMoreListener(() -> handler.postDelayed(() -> {
//
//            if (callback != null)
//                callback.onRequestLoadMoreListNotification();
//
//        }, 100));
//
//    }
//
//    private boolean isShowDetailNotify = false;
//    private int positionItemNotifySelected = -1;
//
//    private void showDetailItemNotify(NotificationModel item, int pos) {
//        positionItemNotifySelected = pos;
//        isShowDetailNotify = true;
//        setVisible(ui.layoutNotifyDetail);
//        setGone(ui.layoutListNotify);
//
//        ui.tvTitleNotification.setText(item.getNotify_title());
//        ui.tvNotificationDescription.setText(item.getMessage());
//        ui.tvNotificationDateCreate.setText(item.getCreated_at());
//
//        String categoryImageLink = Consts.HOST_API + item.getImg_photo();
//        AppProvider.getImageHelper().displayImage(categoryImageLink, ui.imgNotificationDetailLayoutNotifyDetail, null, R.drawable.no_image_full);
//
////        if (callback != null && item.getStatus_view().equalsIgnoreCase("N"))
////            callback.onRequestCheckViewNotify(item.getId());
//    }
//
//    private void showListNotify() {
//        positionItemNotifySelected = -1;
//        isShowDetailNotify = false;
//        setGone(ui.layoutNotifyDetail);
//        setVisible(ui.layoutListNotify);
//    }
//
//    @Override
//    public void validateCheckSeenNotifySuccess() {
//        if (positionItemNotifySelected != -1) {
//            if (listDataNotification.size() > positionItemNotifySelected) {
//                listDataNotification.get(positionItemNotifySelected).setStatus_view("Y");
//                recyclerAdapterWithHF.notifyDataSetChanged();
//            }
//            positionItemNotifySelected = -1;
//        }
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
//    @Override
//    public BaseUiContainer getUiContainer() {
//        return new FragmentNotificationView.UIContainer();
//    }
//
//    @Override
//    public int getViewId() {
//        return R.layout.layout_fragment_notification;
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
//        @UiElement(R.id.layoutListNotify)
//        public View layoutListNotify;
//
//        @UiElement(R.id.ptrClassicFrameLayout)
//        public PtrClassicFrameLayout ptrClassicFrameLayout;
//
//        @UiElement(R.id.recycler_view_list_notification)
//        public RecyclerView recycler_view_list_notification;
//
//        @UiElement(R.id.layoutEmptyList)
//        public View layoutEmptyList;
//
//        @UiElement(R.id.layoutNotifyDetail)
//        public View layoutNotifyDetail;
//
//        @UiElement(R.id.imgNotificationDetailLayoutNotifyDetail)
//        public ImageView imgNotificationDetailLayoutNotifyDetail;
//
//        @UiElement(R.id.tvNotificationTitleLayoutNotifyDetail)
//        public TextView tvTitleNotification;
//
//        @UiElement(R.id.tvNotificationDateCreateLayoutNotifyDetail)
//        public TextView tvNotificationDateCreate;
//
//        @UiElement(R.id.tvNotificationDescriptionLayoutNotifyDetail)
//        public TextView tvNotificationDescription;
//
//
//    }
//}
