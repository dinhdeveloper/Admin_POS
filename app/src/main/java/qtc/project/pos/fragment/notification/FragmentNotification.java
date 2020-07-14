//package qtc.project.pos.fragment.notification;
//
//import android.content.Context;
//
//import java.util.Objects;
//
//import b.laixuantam.myaarlibrary.base.BaseFragment;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import b.laixuantam.myaarlibrary.helper.MyLog;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import qtc.project.pos.R;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.event.FragmentNotificationBackEvent;
//import qtc.project.pos.helper.Consts;
//import qtc.project.pos.model.UserResponseModel;
//import qtc.project.pos.ui.views.fragment.notification.FragmentNotificationView;
//import qtc.project.pos.ui.views.fragment.notification.FragmentNotificationViewCallback;
//import qtc.project.pos.ui.views.fragment.notification.FragmentNotificationViewInterface;
//
//public class FragmentNotification extends BaseFragment<FragmentNotificationViewInterface, BaseParameters> implements FragmentNotificationViewCallback {
//
//    private HomeActivityBackup activity;
//    private int page = 1;
//    private int totalPage = 0;
//
//    @Override
//    protected void initialize() {
////        activity = (HomeActivityBackup) getActivity();
//        view.init(activity, this);
//
//        handler.postDelayed(loopCheckViewInit, 300);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if (context instanceof HomeActivityBackup) {
//            activity = (HomeActivityBackup) getActivity();
//            if (activity != null) {
//                Objects.requireNonNull(activity).hideBottomMenuBar();
//                activity.FullScreencall();
//            }
//        }
//    }
//
//
//    private final Runnable loopCheckViewInit = new Runnable() {
//        public void run() {
//            if (getView() != null && getView().isShown()) {
//                MyLog.e(Consts.TAG_FRAGMENT_CHECK, " is on screen");
//
//                handler.removeCallbacks(this);
//                requestGetListNotification();
//
//                return;
//            }
//
//            MyLog.e(Consts.TAG_FRAGMENT_CHECK, " is NOT on screen");
//            handler.postDelayed(this, 500);
//        }
//    };
//
//    @Override
//    protected FragmentNotificationViewInterface getViewInstance() {
//        return new FragmentNotificationView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//    @Override
//    public void onClickBackHeader() {
//        if (activity != null) {
//            activity.checkBack();
//            activity.showBottomMenuBar();
//        }
//        FragmentNotificationBackEvent.post();
//    }
//
//    @Override
//    public void onClickShowShoppingCart() {
//    }
//
//    @Override
//    public void onClickFilterProduct() {
//    }
//
//    @Override
//    public void onRequestRefreshListNotification() {
//        page = 1;
//
//        totalPage = 0;
//
//        requestGetListNotification();
//    }
//
//
//    @Override
//    public void onRequestLoadMoreListNotification() {
//        ++page;
//
//        if (totalPage > 0 && page <= totalPage) {
//
//            requestGetListNotification();
//        } else {
//            view.setNoMoreLoading();
//        }
//    }
//
//    private void requestGetListNotification() {
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
//            view.setDataNotification(null);
//            return;
//        }
//
//        UserResponseModel userModel = AppProvider.getPreferences().getUserModel();
//
//        if (userModel == null) {
//            view.setDataNotification(null);
//            return;
//        }
//
////        showProgress();
////
////        RequestGetListNotification.ApiParams params = new RequestGetListNotification.ApiParams();
////
////        params.page = String.valueOf(page);
////        params.limit = "20";
////        params.id_customer = userModel.getId();
////
////        AppProvider.getApiManagement().call(RequestGetListNotification.class, params, new ApiRequest.ApiCallback<BaseResponseModel<NotificationModel>>() {
////            @Override
////            public void onSuccess(BaseResponseModel<NotificationModel> result) {
////                dismissProgress();
////                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
////                    if (!TextUtils.isEmpty(result.getTotal_page())) {
////                        totalPage = Integer.valueOf(result.getTotal_page());
////                        if (page == totalPage) {
////                            view.setNoMoreLoading();
////                        }
////                    } else {
////                        view.setNoMoreLoading();
////                    }
////
////                    view.setDataNotification(result.getData());
////                } else {
////                    if (!TextUtils.isEmpty(result.getMessage()))
////                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
////                    else
////                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
////                }
////
////            }
////
////            @Override
////            public void onError(ErrorApiResponse error) {
////                dismissProgress();
////                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
////                MyLog.e("Notification", error.message);
////                view.setDataNotification(null);
////            }
////
////            @Override
////            public void onFail(ApiRequest.RequestError error) {
////                dismissProgress();
////                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
////                MyLog.e("Notification", error.toString());
////                view.setDataNotification(null);
////            }
////        });
//    }
//
//    @Override
//    public void onRequestCheckViewNotify(String id_notify) {
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            return;
//        }
//
//        UserResponseModel userModel = AppProvider.getPreferences().getUserModel();
//
//        if (userModel == null) {
//            return;
//        }
//
////        RequestCheckSeenNotify.ApiParams params = new RequestCheckSeenNotify.ApiParams();
////        params.id_customer = userModel.getId();
////        params.id_notify = id_notify;
////
////        AppProvider.getApiManagement().call(RequestCheckSeenNotify.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
////            @Override
////            public void onSuccess(BaseResponseModel result) {
////                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
////                    view.validateCheckSeenNotifySuccess();
////                }
////            }
////
////            @Override
////            public void onError(ErrorApiResponse error) {
////
////            }
////
////            @Override
////            public void onFail(ApiRequest.RequestError error) {
////
////            }
////        });
//
//
//    }
//}
