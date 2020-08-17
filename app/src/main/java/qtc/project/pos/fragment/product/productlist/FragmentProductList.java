package qtc.project.pos.fragment.product.productlist;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryView;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryViewCallback;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryViewInterface;

public class FragmentProductList extends BaseFragment<FragmentProductListCategoryViewInterface, BaseParameters> implements FragmentProductListCategoryViewCallback {

    HomeActivity activity;
    boolean checked;
    int page =1;
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
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
        
        callApi();
        getDataToBundle();
    }

    private void getDataToBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            boolean check = (boolean) extras.get("check");
            checked = check;
        }
    }
    public void callApi() {
        showProgress();
        ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
        params.type_manager = "list_product";
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
                    view.mappingRecyclerView(result.getData());
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
    protected FragmentProductListCategoryViewInterface getViewInstance() {
        return new FragmentProductListCategoryView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackprogress() {
        if (activity!=null){
            activity.checkBack();
            callApi();
        }
    }

    @Override
    public void goToProductListDetail(ProductListModel model) {
        if (checked == true){
            if (activity != null) {
                activity.checkBack();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.setDataProduct(model);
                    }
                }, 100);

            }
        }
        else {
            activity.addFragment( FragmentProductListDetail.newIntance(model),true,null);
        }
    }

    @Override
    public void searchProduct(String name) {
        showProgress();
        ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
        params.type_manager = "list_product";
        params.product = name;
        AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductListModel> body) {
                dismissProgress();
                if (body != null) {
                    view.clearListDataProduct();
                    view.setNoMoreLoading();
                    view.mappingRecyclerView(body.getData());
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
    public void callAllData() {
        view.clearListDataProduct();
        callApi();
    }

    @Override
    public void reQuestList() {

    }

    @Override
    public void loadMore() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {
            callApi();
        } else {
            view.setNoMoreLoading();
        }
    }

    public void setDataSearchProduct(ProductListModel[] list,String name,String id) {
        if (view!=null)
        {
            view.clearListDataProduct();
            view.setNoMoreLoading();
            view.mappingDataFilterProduct(list,name,id);
        }
    }
}
