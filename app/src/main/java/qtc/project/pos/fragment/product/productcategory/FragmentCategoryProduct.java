package qtc.project.pos.fragment.product.productcategory;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import qtc.project.pos.api.product.productcategory.ProductCategoryRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.event.UpdateListProductEvent;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductView;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductViewCallback;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductViewInterface;

public class FragmentCategoryProduct extends BaseFragment<FragmentListBaseViewInterface, BaseParameters> implements FragmentListBaseViewCallback {
    HomeActivity activity;
    int page = 1;
    private int totalPage = 0;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
        requestDataProductCategory();
    }

    private void requestDataProductCategory() {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
            view.setDataList(null);
            return;
        }
        showProgress();
        ProductCategoryRequest.ApiParams params = new ProductCategoryRequest.ApiParams();
        params.type_manager = "list_category";
        params.page = String.valueOf(page);
        AppProvider.getApiManagement().call(ProductCategoryRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductCategoryModel> result) {
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
    protected FragmentListBaseViewInterface getViewInstance() {
        return new FragmentCategoryProductView();
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
        requestDataProductCategory();
    }

    @Override
    public void onRequestLoadMoreList() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {

            requestDataProductCategory();
        } else {
            view.setNoMoreLoading();
            showToast(getString(R.string.error_loadmore));
        }
    }

    @Override
    public void onRequestSearchWithFilter(String key) {

    }

    @Override
    public void onItemListSelected(OptionModel item) {
        if (activity != null) {
            ProductCategoryModel model = (ProductCategoryModel)item.getDtaCustom();
            activity.changToFragmentCategoryProductDetail(model);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.hideRootView();
                }
            }, 500);
        }
    }

    @Override
    public void onClickAddItem() {
        if (activity != null) {
            activity.changToFragmentCategoryProductDetail(null);
        }
    }

    @Override
    public void onDeleteItemSelected(OptionModel item) {

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
        requestDataProductCategory();
        if (isUpdateItem) {
            view.showRootView();
            isUpdateItem = false;
        }
    }
}
