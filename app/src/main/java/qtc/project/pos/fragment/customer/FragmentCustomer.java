package qtc.project.pos.fragment.customer;

import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.customer.CustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.event.UpdateListProductEvent;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.ui.views.fragment.customer.FragmentCustomerView;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;

public class FragmentCustomer extends BaseFragment<FragmentListBaseViewInterface, BaseParameters> implements FragmentListBaseViewCallback {
    HomeActivity activity;
    boolean checked;
    int page = 1;
    private int totalPage = 0;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
        onRequestGetListCustomer();
    }

    private void onRequestGetListCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.type_manager = "list_customer";
        params.page = String.valueOf(page);
        params.limit = "20";
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.setDataList(result);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }
        });
    }

    @Override
    protected FragmentListBaseViewInterface getViewInstance() {
        return new FragmentCustomerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onClickBackHeader() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void refreshLoadingList() {
        page = 1;
        totalPage = 0;
        view.resetListData();
        onRequestGetListCustomer();
    }

    @Override
    public void onRequestLoadMoreList() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {
            onRequestGetListCustomer();
        } else {
            view.setNoMoreLoading();
            showToast(getString(R.string.error_loadmore));
        }
    }

    @Override
    public void onRequestSearchWithFilter(String key) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.type_manager = "list_customer";
        if (!TextUtils.isEmpty(key)) {
            params.customer_filter = key;
        }
        params.page = String.valueOf(page);
        params.limit = "20";
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.setDataList(result);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }
        });
    }

    @Override
    public void onItemListSelected(OptionModel item) {
        CustomerModel model = (CustomerModel) item.getDtaCustom();
        if (activity != null) {
            activity.changToFragmentCustomerDetail(model);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.hideRootView();
                }
            }, 100);
        }
    }

    @Override
    public void onClickAddItem() {
        if (activity != null) {
            activity.changToFragmentCustomerDetail(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.hideRootView();
                }
            }, 100);
        }
    }

    @Override
    public void onDeleteItemSelected(OptionModel item) {
        CustomerModel model = (CustomerModel) item.getDtaCustom();
        if (activity != null) {
            activity.changToFragmentHistoryOrderCustomer(model);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.hideRootView();
                }
            }, 100);
        }
    }

    @Override
    public void onClickFilter() {

    }

    boolean isUpdateItem = false;

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackShowRootViewEvent(BackShowRootViewEvent event) {
        isUpdateItem = true;
        if (isUpdateItem) {
            view.showRootView();
            isUpdateItem = false;
        }
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateListProductEvent(UpdateListProductEvent event) {
        isUpdateItem = true;
        view.clearListData();
        onRequestGetListCustomer();
        if (isUpdateItem) {
            view.showRootView();
            isUpdateItem = false;
        }
    }
}
