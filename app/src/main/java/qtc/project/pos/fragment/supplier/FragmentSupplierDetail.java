package qtc.project.pos.fragment.supplier;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.supplier.SupplierRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.quanlylohang.FragmentChiTietLoHang;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.SupplierModel;
import qtc.project.pos.ui.views.fragment.supplier.detail.FragmentSupplierDetailView;
import qtc.project.pos.ui.views.fragment.supplier.detail.FragmentSupplierDetailViewCallback;
import qtc.project.pos.ui.views.fragment.supplier.detail.FragmentSupplierDetailViewInterface;

public class FragmentSupplierDetail extends BaseFragment<FragmentSupplierDetailViewInterface, BaseParameters> implements FragmentSupplierDetailViewCallback {

    HomeActivity activity;

    public static FragmentSupplierDetail newIntance(SupplierModel model) {
        FragmentSupplierDetail frag = new FragmentSupplierDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("infoModel", model);
        frag.setArguments(bundle);
        return frag;
    }
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
        getDataIntance();
    }

    private void getDataIntance() {
        Bundle extras = getArguments();
        if (extras != null) {
            SupplierModel infoModel = (SupplierModel) extras.get("infoModel");
            view.sendDataToView(infoModel);
        }
    }

    @Override
    protected FragmentSupplierDetailViewInterface getViewInstance() {
        return new FragmentSupplierDetailView();
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
    public void updateSupplier(SupplierModel supplierModel) {
        showProgress();
        if (supplierModel!=null){
            SupplierRequest.ApiParams params = new SupplierRequest.ApiParams();
            params.type_manager = "update_supplier";
            params.id_supplier = supplierModel.getId();
            params.id_code = supplierModel.getId_code();
            params.name = supplierModel.getName();
            params.email = supplierModel.getEmail();
            params.phone_number = supplierModel.getPhone_number();
            params.address = supplierModel.getAddress();

            AppProvider.getApiManagement().call(SupplierRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<SupplierModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<SupplierModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")){
                       view.showDialog();
                    } else if (body.getSuccess().equals("false")){
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

    @Override
    public void deleteSupplier(String id) {
        showProgress();;
        if (id!=null){
            SupplierRequest.ApiParams params = new SupplierRequest.ApiParams();
            params.type_manager = "delete_supplier";
            params.id_supplier = id;

            AppProvider.getApiManagement().call(SupplierRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<SupplierModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<SupplierModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")){
                        view.showDialogSucc();
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
