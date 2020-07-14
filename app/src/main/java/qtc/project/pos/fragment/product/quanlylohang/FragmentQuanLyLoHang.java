package qtc.project.pos.fragment.product.quanlylohang;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.productcategory.FragmentCategoryProductDetail;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.FragmentQuanLyLoHangView;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.FragmentQuanLyLoHangViewCallback;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.FragmentQuanLyLoHangViewInterface;

public class FragmentQuanLyLoHang extends BaseFragment<FragmentQuanLyLoHangViewInterface, BaseParameters> implements FragmentQuanLyLoHangViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentQuanLyLoHangViewInterface getViewInstance() {
        return new FragmentQuanLyLoHangView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackprogress() {
        if (activity!= null)
            activity.checkBack();
    }

    @Override
    public void callDataSearch(String toString) {
        if (toString!=null){
            showProgress();
            ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
            params.type_manager = "list_product";
            params.product = toString;
            AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    if (body != null) {
                        dismissProgress();
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
    }

    @Override
    public void sentDataToDetail(PackageInfoModel model, String name_product,String id_product) {
        activity.addFragment( FragmentChiTietLoHang.newIntance(model,name_product,id_product),true,null);
    }

}
