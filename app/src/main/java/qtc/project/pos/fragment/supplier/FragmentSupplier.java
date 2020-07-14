package qtc.project.pos.fragment.supplier;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.supplier.SupplierRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.SupplierModel;
import qtc.project.pos.ui.views.fragment.supplier.list.FragmentSupplierView;
import qtc.project.pos.ui.views.fragment.supplier.list.FragmentSupplierViewCallback;
import qtc.project.pos.ui.views.fragment.supplier.list.FragmentSupplierViewInterface;

public class FragmentSupplier extends BaseFragment<FragmentSupplierViewInterface, BaseParameters> implements FragmentSupplierViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        callApiDataSupp();
    }

    private void callApiDataSupp() {
        showProgress();
        SupplierRequest.ApiParams params = new SupplierRequest.ApiParams();
        params.type_manager = "list_supplier";
        AppProvider.getApiManagement().call(SupplierRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<SupplierModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<SupplierModel> body) {
                dismissProgress();
                if (body != null) {
                    ArrayList<SupplierModel> list = new ArrayList<>();
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
    protected FragmentSupplierViewInterface getViewInstance() {
        return new FragmentSupplierView();
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
    public void cancelNhaCungUng(SupplierModel model) {
        if (activity != null) {
            activity.checkBack();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.setDataNhaCungUng(model);
                }
            }, 100);

        }
//        activity.addFragment(FragmentChiTietLoHang.newIntancetwo(model),true,null);
    }
}
