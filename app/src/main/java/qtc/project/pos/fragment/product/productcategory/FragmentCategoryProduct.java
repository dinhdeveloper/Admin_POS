package qtc.project.pos.fragment.product.productcategory;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productcategory.ProductCategoryRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductView;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductViewCallback;
import qtc.project.pos.ui.views.fragment.product.category.categoryproduct.FragmentCategoryProductViewInterface;

public class FragmentCategoryProduct extends BaseFragment<FragmentCategoryProductViewInterface, BaseParameters> implements FragmentCategoryProductViewCallback {
    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        requestDataProductCategory();
    }

    private void requestDataProductCategory() {
        showProgress();
        ProductCategoryRequest.ApiParams params = new ProductCategoryRequest.ApiParams();
        params.type_manager = "list_category";
        AppProvider.getApiManagement().call(ProductCategoryRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductCategoryModel> body) {
                dismissProgress();
                if (body != null) {
                    ArrayList<ProductCategoryModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initGetListCategoryProduct(list);
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
    protected FragmentCategoryProductViewInterface getViewInstance() {
        return new FragmentCategoryProductView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }


    @Override
    public void setBackProgress() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void onSendData(ProductCategoryModel model) {
        activity.replaceFragment( FragmentCategoryProductDetail.newIntance(model),true,null);
    }

    @Override
    public void callAllData() {
        requestDataProductCategory();
    }

    @Override
    public void callDataToFilter(String toString) {

    }
}
