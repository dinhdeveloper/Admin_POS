package qtc.project.pos.fragment.product.doitra;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.packageproduct.PackageReturnRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.quanlylohang.FragmentChiTietLoHang;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.PackageReturnModel;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaViewCallback;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaViewInterface;
import qtc.project.pos.ui.views.fragment.product.doitra.detail.FragmentChiTietDonTraHangHoaView;
import qtc.project.pos.ui.views.fragment.product.doitra.detail.FragmentChiTietDonTraHangHoaViewCallback;
import qtc.project.pos.ui.views.fragment.product.doitra.detail.FragmentChiTietDonTraHangHoaViewInterface;

public class FragmentChiTietDonTraHangHoa extends BaseFragment<FragmentChiTietDonTraHangHoaViewInterface, BaseParameters> implements FragmentChiTietDonTraHangHoaViewCallback {

    HomeActivity activity;

    public static FragmentChiTietDonTraHangHoa newIntance(PackageReturnModel model) {
        FragmentChiTietDonTraHangHoa frag = new FragmentChiTietDonTraHangHoa();
        Bundle bundle = new Bundle();
        bundle.putSerializable("infoModel", model);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
        getDataIntance();
    }

    private void getDataIntance() {
        Bundle extras = getArguments();
        if (extras != null) {
            PackageReturnModel infoModel = (PackageReturnModel) extras.get("infoModel");
            view.sendDataToView(infoModel);
        }
    }

    @Override
    protected FragmentChiTietDonTraHangHoaViewInterface getViewInstance() {
        return new FragmentChiTietDonTraHangHoaView();
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
    public void updateStatus(PackageReturnModel infoModel) {
        showProgress();
        if (infoModel != null) {
            PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
            params.type_manager = "update_status_pack_return";
            params.id_product_return = infoModel.getProduct_return_id();
            params.return_date = infoModel.getProduct_return_return_date();
            params.status = "Y";
            AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")) {
                        Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                        onBack();
                    } else if (body.getSuccess().equals("false")) {
                        Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void deleteStatus(PackageReturnModel infoModel) {
        showProgress();
        if (infoModel != null) {
            PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
            params.type_manager = "update_status_pack_return";
            params.id_product_return = infoModel.getProduct_return_id();
            params.return_date = infoModel.getProduct_return_return_date();
            params.status = "D";
            AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")) {
                        view.showConfirmDelete();
                    } else if (body.getSuccess().equals("false")) {
                        Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void onBack() {
        if (activity!=null)
            activity.checkBack();
    }
}
