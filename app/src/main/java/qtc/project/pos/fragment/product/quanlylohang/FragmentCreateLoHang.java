package qtc.project.pos.fragment.product.quanlylohang;

import android.util.Log;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.packageproduct.PackageProductRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.productlist.FragmentProductList;
import qtc.project.pos.fragment.supplier.FragmentSupplier;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.SupplierModel;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.create.FragmentCreateLoHangView;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.create.FragmentCreateLoHangViewCallback;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.create.FragmentCreateLoHangViewInterface;

public class FragmentCreateLoHang extends BaseFragment<FragmentCreateLoHangViewInterface, BaseParameters> implements FragmentCreateLoHangViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentCreateLoHangViewInterface getViewInstance() {
        return new FragmentCreateLoHangView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity!=null)
            activity.checkBack();
    }

    @Override
    public void getAllDataNhaCungUng() {
        activity.addFragment(new FragmentSupplier(), true, null);
    }

    @Override
    public void getAllDataProduct(boolean check) {
        activity.addFragment(new FragmentProductList().newInstance(check), true, null);
    }

    @Override
    public void createPackageInfo(PackageInfoModel infoModel,String id_product) {
        if (infoModel!=null){
            showProgress();
            PackageProductRequest.ApiParams params = new PackageProductRequest.ApiParams();
            params.type_manager = "create_package";
            params.product_id = id_product;
            params.id_code = infoModel.getPack_id_code();
            params.manufacturer_id = infoModel.getManufacturer_id();
            params.manufacturing_date = infoModel.getManufacturing_date();
            params.sale_price = infoModel.getSale_price();
            params.import_price = infoModel.getImport_price();
            params.description = infoModel.getDescription();
            params.employee_id = AppProvider.getPreferences().getUserModel().getId();
            params.expiry_date = infoModel.getExpiry_date();
            params.import_date = infoModel.getImport_date();
            params.import_price = infoModel.getImport_price();
            params.quantity_order = infoModel.getQuantity_order();

            AppProvider.getApiManagement().call(PackageProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")) {
                        view.onShowSuccess();
                    } else if (body.getSuccess().equals("false")) {
                        Toast.makeText(activity, ""+body.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void setDataNhaCungUng(SupplierModel model) {
        if (view != null) {
            view.sendDataToView(model);
        }
    }

    public void setDataProduct(ProductListModel model) {
        if (model!=null){
            view.sentDataProductToView(model);
        }
    }
}
