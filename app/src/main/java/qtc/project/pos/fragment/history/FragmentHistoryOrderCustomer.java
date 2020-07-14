package qtc.project.pos.fragment.history;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.history.HistoryOrderCustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.levelcustomer.FragmentLevelCustomerDetail;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.model.OrderCustomerModel;
import qtc.project.pos.ui.views.fragment.history.history.FragmentHistoryOrderCustomerView;
import qtc.project.pos.ui.views.fragment.history.history.FragmentHistoryOrderCustomerViewCallback;
import qtc.project.pos.ui.views.fragment.history.history.FragmentHistoryOrderCustomerViewInterface;

public class FragmentHistoryOrderCustomer extends BaseFragment<FragmentHistoryOrderCustomerViewInterface, BaseParameters> implements FragmentHistoryOrderCustomerViewCallback {

    HomeActivity activity;
    public static FragmentHistoryOrderCustomer newIntance(CustomerModel item) {
        FragmentHistoryOrderCustomer frag = new FragmentHistoryOrderCustomer();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", item);
        frag.setArguments(bundle);
        return frag;
    }
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);

        getDataToBundle();
    }

    private void getDataToBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            CustomerModel model = (CustomerModel) extras.get("model");
            showProgress();
            HistoryOrderCustomerRequest.ApiParams params = new HistoryOrderCustomerRequest.ApiParams();
            params.filter = model.getId_code();
            AppProvider.getApiManagement().call(HistoryOrderCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderCustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<OrderCustomerModel> body) {
                    dismissProgress();
                    ArrayList<OrderCustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.sendDataToView(list);
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
    protected FragmentHistoryOrderCustomerViewInterface getViewInstance() {
        return new FragmentHistoryOrderCustomerView();
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
    public void sentDataToDetail(OrderCustomerModel model) {
        activity.addFragment(new FragmentOrderDetailCustomer().newIntance(model),true,null);
    }
}
