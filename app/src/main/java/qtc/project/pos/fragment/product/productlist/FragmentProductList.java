package qtc.project.pos.fragment.product.productlist;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.event.UpdateListProductEvent;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryView;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryViewCallback;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryViewInterface;

public class FragmentProductList extends BaseFragment<FragmentListBaseViewInterface, BaseParameters> implements FragmentListBaseViewCallback {

    HomeActivity activity;
    boolean checked;
    int page = 1;
    private int totalPage = 0;

    public static FragmentProductList newInstance(boolean check) {
        FragmentProductList detail = new FragmentProductList();
        Bundle bundle = new Bundle();
        bundle.putBoolean("check", check);
        detail.setArguments(bundle);

        return detail;
    }


    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        onRequestGetListProduct();
        getDataBundle();
    }

    private void getDataBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            boolean check = (boolean) extras.get("check");
            checked = check;
        }
    }

    private void onRequestGetListProduct() {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
            view.setDataList(null);
            return;
        }
        showProgress();
        ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
        params.type_manager = "list_product";
        params.page = String.valueOf(page);
        params.limit = "20";
        AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductListModel> result) {
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
        return new FragmentProductListCategoryView();
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
        onRequestGetListProduct();
    }

    @Override
    public void onRequestLoadMoreList() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {
            onRequestGetListProduct();
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
        if (!TextUtils.isEmpty(key)) {
            params.product = key;
        }
        params.page = String.valueOf(page);
        params.limit = "20";
        AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductListModel> result) {
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
                    view.clearListData();
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
        ProductListModel model = (ProductListModel) item.getDtaCustom();
        if (checked == true) {
            if (activity != null) {
                activity.checkBack();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.setDataProduct(model);
                    }
                }, 100);

            }
        } else {
            activity.changToFragmentProductDetail(model);
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
        activity.changToFragmentProductDetail(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideRootView();
            }
        }, 100);

    }

    @Override
    public void onDeleteItemSelected(OptionModel item) {

    }

    @Override
    public void onClickFilter() {
        activity.changToFragmentFilterProduct();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.hideRootView();
            }
        }, 100);

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
        onRequestGetListProduct();
        if (isUpdateItem) {
            view.showRootView();
            isUpdateItem = false;
        }
    }

    public void setDataSearchProduct(BaseResponseModel dataList) {
        if (view != null) {
            view.clearListData();
            view.setDataList(dataList);
        }
    }
}
