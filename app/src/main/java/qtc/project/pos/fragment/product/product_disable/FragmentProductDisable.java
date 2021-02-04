package qtc.project.pos.fragment.product.product_disable;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.cptr.recyclerview.RecyclerAdapterWithHF;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;
import qtc.project.pos.ui.views.fragment.product.product_disable.FragmentProductDisableView;
import qtc.project.pos.ui.views.fragment.product.product_disable.FragmentProductDisableViewCallback;
import qtc.project.pos.ui.views.fragment.product.product_disable.FragmentProductDisableViewInterface;

public class FragmentProductDisable extends BaseFragment<FragmentListBaseViewInterface, BaseParameters> implements FragmentListBaseViewCallback {
    HomeActivity activity;
    boolean checked;
    int page = 1;
    private int totalPage = 0;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        requestListProductDisable();
    }

    private void requestListProductDisable() {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
            //view.setDataList(null);
            return;
        }
        showProgress();
        ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
        params.type_manager = "list_product";
        params.status_product = "N";
        params.page = String.valueOf(page);
        AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductListModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
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
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    @Override
    protected FragmentListBaseViewInterface getViewInstance() {
        return new FragmentProductDisableView();
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
        requestListProductDisable();
    }

    @Override
    public void onRequestLoadMoreList() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {
            requestListProductDisable();
        } else {
            view.setNoMoreLoading();
            showToast(getString(R.string.error_loadmore));
        }
    }

    @Override
    public void onRequestSearchWithFilter(String key) {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
            view.setDataList(null);
            return;
        }
        showProgress();
        ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
        params.type_manager = "list_product";
        params.status_product = "N";
        if (!TextUtils.isEmpty(key)) {
            params.product = key;
        }
        params.page = String.valueOf(page);
        AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductListModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
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
                Log.e("onError", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("onFail", error.name());
            }
        });
    }

    @Override
    public void onItemListSelected(OptionModel item) {
        ProductListModel model = (ProductListModel) item.getDtaCustom();
        final KAlertDialog mCustomAlert = new KAlertDialog(getContext());
        mCustomAlert.setContentText("Đang xử lý...")
                .showCancelButton(false)
                .setCancelClickListener(null)
                .changeAlertType(KAlertDialog.PROGRESS_TYPE);

        mCustomAlert.setCancelable(false);
        mCustomAlert.setCanceledOnTouchOutside(false);
        mCustomAlert.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
                    showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
                    return;
                }
                showProgress();

                showProgress();
                ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
                params.type_manager = "update_status_product";
                params.id_product = model.getId();
                params.status_product = "Y";
                AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                    @Override
                    public void onSuccess(BaseResponseModel<ProductListModel> body) {
                        dismissProgress();

                        if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equalsIgnoreCase("true")) {
                            mCustomAlert.setContentText("Cập nhật sản phẩm thành công.")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(kAlertDialog -> {
                                        mCustomAlert.dismissWithAnimation();
                                        mCustomAlert.dismiss();
                                        view.clearListData();
                                        refreshLoadingList();
                                    })
                                    .changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        } else {
                            if (!TextUtils.isEmpty(body.getMessage())) {
                                showAlert(body.getMessage(), KAlertDialog.ERROR_TYPE);
                            } else {
                                activity.showAlert("Không thể tải dịch vụ", KAlertDialog.ERROR_TYPE);
                            }
                        }
                    }

                    @Override
                    public void onError(ErrorApiResponse error) {
                        dismissProgress();
                        Log.e("onError", error.message);
                    }

                    @Override
                    public void onFail(ApiRequest.RequestError error) {
                        dismissProgress();
                        Log.e("onFail", error.name());
                    }
                });
            }
        }, 500);
    }

    @Override
    public void onClickAddItem() {

    }

    @Override
    public void onDeleteItemSelected(OptionModel item) {

    }

    @Override
    public void onClickFilter() {

    }
}
