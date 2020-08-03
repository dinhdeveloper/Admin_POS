package qtc.project.pos.fragment.product.productlist.filter;

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
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.productlist.filter.FragmentFilterSanPhamView;
import qtc.project.pos.ui.views.fragment.product.productlist.filter.FragmentFilterSanPhamViewCallback;
import qtc.project.pos.ui.views.fragment.product.productlist.filter.FragmentFilterSanPhamViewInterface;

public class FragmentFilterSanPham extends BaseFragment<FragmentFilterSanPhamViewInterface, BaseParameters> implements FragmentFilterSanPhamViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentFilterSanPhamViewInterface getViewInstance() {
        return new FragmentFilterSanPhamView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void searchProduct(String name, String id) {
        showProgress();
        if (name != null && id != null) {
            ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
            params.type_manager = "list_product";
            params.product = name;
            params.id_code = id;

            AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    dismissProgress();
                    if (body !=null){
                        if (activity != null) {
                            activity.checkBack();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    activity.setDataSearchProduct(body.getData(),name,id);
                                }
                            }, 100);

                        }
//                        if (activity!=null)
//                            activity.setDataSearchProduct(list,name,id);
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                    Log.e("", error.message);
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                    Log.e("", error.name());
                }
            });
        }
    }

    public void setOnBack() {
        if (activity!=null)
            activity.checkBack();
    }
}
