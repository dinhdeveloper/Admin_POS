package qtc.project.pos.fragment.product.productlist;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.employee.FragmentCreateEmployee;
import qtc.project.pos.fragment.employee.FragmentEmployeeDetail;
import qtc.project.pos.fragment.product.productcategory.FragmentCategoryProductDetail;
import qtc.project.pos.fragment.product.quanlylohang.FragmentCreateLoHang;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct.FragmentCreateProductCategoryViewCallback;
import qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct.FragmentCreateProductCategoryViewInterface;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryView;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryViewCallback;
import qtc.project.pos.ui.views.fragment.product.productlist.FragmentProductListCategoryViewInterface;

public class FragmentProductList extends BaseFragment<FragmentProductListCategoryViewInterface, BaseParameters> implements FragmentProductListCategoryViewCallback {

    HomeActivity activity;
    boolean checked;

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

    private void callApi() {
        showProgress();
        ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
        params.type_manager = "list_product";
        AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductListModel> body) {
                dismissProgress();
                if (body != null) {
                    ArrayList<ProductListModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.mappingRecyclerView(list);
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
            activity.replaceFragment( FragmentProductListDetail.newIntance(model),true,null);
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
                    ArrayList<ProductListModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.mappingRecyclerView(list);
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
        callApi();
    }

    public void setDataSearchProduct(ArrayList<ProductListModel> list,String name,String id) {
        if (view!=null)
            view.mappingDataFilterProduct(list,name,id);
    }
}
