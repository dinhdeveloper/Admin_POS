package qtc.project.pos.fragment.product.doitra;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.packageproduct.PackageReturnRequest;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.quanlylohang.FragmentChiTietLoHang;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageReturnModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaView;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaViewCallback;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaViewInterface;

public class FragmentDoiTraHangHoa extends BaseFragment<FragmentDoiTraHangHoaViewInterface, BaseParameters> implements FragmentDoiTraHangHoaViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentDoiTraHangHoaViewInterface getViewInstance() {
        return new FragmentDoiTraHangHoaView();
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
    public void getDataDoiTraHangHoa() {
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.type_manager = "list_package_return";
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                dismissProgress();
                ArrayList<PackageReturnModel> list = new ArrayList<>();
                list.addAll(Arrays.asList(body.getData()));
                view.mappingRecyclerView(list);
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
    public void sentDataToDetailDTHH(PackageReturnModel model) {
        activity.replaceFragment( FragmentChiTietDonTraHangHoa.newIntance(model),true,null);
    }
}
