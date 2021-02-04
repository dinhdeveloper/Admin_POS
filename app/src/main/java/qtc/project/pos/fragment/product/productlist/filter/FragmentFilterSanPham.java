package qtc.project.pos.fragment.product.productlist.filter;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.event.BackShowRootViewEvent;
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
        BackShowRootViewEvent.post();
    }

    @Override
    public void searchProduct(String name, String id) {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
            return;
        }
        showProgress();
        if (name != null && id != null) {
            ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
            params.type_manager = "list_product";
            if (!TextUtils.isEmpty(name)) {
                params.product = name;
            }
            if (!TextUtils.isEmpty(id)) {
                params.id_code = id;
            }

            AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    dismissProgress();
                    if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equalsIgnoreCase("true")){
                        if (body != null) {
                            if (activity != null) {
                                activity.checkBack();
                                BackShowRootViewEvent.post();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        activity.setDataSearchProduct(body);
                                    }
                                }, 100);

                            }
                        }
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                    showAlert("Không tải được dữ liệu",KAlertDialog.ERROR_TYPE);
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                    showAlert("Không tải được dữ liệu",KAlertDialog.ERROR_TYPE);
                }
            });
        }
    }

    public void setOnBack() {
        if (activity != null)
            activity.checkBack();
            BackShowRootViewEvent.post();
    }
}
