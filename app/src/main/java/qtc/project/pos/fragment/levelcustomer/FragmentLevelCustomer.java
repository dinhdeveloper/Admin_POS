package qtc.project.pos.fragment.levelcustomer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.levelcustomer.LevelCustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.productlist.FragmentProductListDetail;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.ui.views.fragment.levelcustomer.FragmentLevelCustomerView;
import qtc.project.pos.ui.views.fragment.levelcustomer.FragmentLevelCustomerViewCallback;
import qtc.project.pos.ui.views.fragment.levelcustomer.FragmentLevelCustomerViewInterface;

public class FragmentLevelCustomer extends BaseFragment<FragmentLevelCustomerViewInterface, BaseParameters> implements FragmentLevelCustomerViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentLevelCustomerViewInterface getViewInstance() {
        return new FragmentLevelCustomerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void callDataLevelCustomer() {
        showProgress();
        LevelCustomerRequest.ApiParams params = new LevelCustomerRequest.ApiParams();
        params.type_manager = "list_level";
        AppProvider.getApiManagement().call(LevelCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<LevelCustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<LevelCustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<LevelCustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerView(list);
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
    public void sendDataToDetail(LevelCustomerModel model) {
        activity.replaceFragment(FragmentLevelCustomerDetail.newIntance(model), true, null);
    }

    @Override
    public void onBackProgress() {
        if (activity!=null)
            activity.checkBack();
    }
}
