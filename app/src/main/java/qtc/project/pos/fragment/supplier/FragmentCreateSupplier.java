package qtc.project.pos.fragment.supplier;

import android.util.Log;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.supplier.SupplierRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.SupplierModel;
import qtc.project.pos.ui.views.fragment.supplier.create.FragmentCreateSupplierView;
import qtc.project.pos.ui.views.fragment.supplier.create.FragmentCreateSupplierViewCallback;
import qtc.project.pos.ui.views.fragment.supplier.create.FragmentCreateSupplierViewInterface;

public class FragmentCreateSupplier extends BaseFragment<FragmentCreateSupplierViewInterface, BaseParameters> implements FragmentCreateSupplierViewCallback {
   HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentCreateSupplierViewInterface getViewInstance() {
        return new FragmentCreateSupplierView();
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
    public void createSupplier(SupplierModel supplierModel) {
        if (supplierModel!=null){
            SupplierRequest.ApiParams params = new SupplierRequest.ApiParams();

            params.type_manager = "create_supplier";
            params.id_code = supplierModel.getId_code();
            params.address = supplierModel.getAddress();
            params.name = supplierModel.getName();
            params.phone_number = supplierModel.getPhone_number();
            params.email = supplierModel.getEmail();

            AppProvider.getApiManagement().call(SupplierRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<SupplierModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<SupplierModel> body) {
                    if (body.getSuccess().equals("true")){
                        view.showAlerSucess();
                    } else if (body.getSuccess().equals("true")){
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
}
