package qtc.project.pos.fragment.order;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.history.HistoryOrderCustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.OrderCustomerModel;
import qtc.project.pos.ui.views.fragment.order.FragmentOrderManagerView;
import qtc.project.pos.ui.views.fragment.order.FragmentOrderManagerViewCallback;
import qtc.project.pos.ui.views.fragment.order.FragmentOrderManagerViewInterface;

public class FragmentOrderManager extends BaseFragment<FragmentOrderManagerViewInterface, BaseParameters> implements FragmentOrderManagerViewCallback {
    HomeActivity activity;
    String dateStartSelected =null;
    String dateEndSelected =null;

    public static FragmentOrderManager newIntance(String dateStartSelected, String dateEndSelected) {
        FragmentOrderManager frag = new FragmentOrderManager();
        Bundle bundle = new Bundle();
        bundle.putString("dateStartSelected", dateStartSelected);
        bundle.putString("dateEndSelected", dateEndSelected);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentOrderManagerViewInterface getViewInstance() {
        return new FragmentOrderManagerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void callDataOrder() {
        showProgress();
        HistoryOrderCustomerRequest.ApiParams params = new HistoryOrderCustomerRequest.ApiParams();
        Bundle extras = getArguments();
        if (extras != null) {
            dateStartSelected = (String) extras.get("dateStartSelected");
            dateEndSelected = (String) extras.get("dateEndSelected");
            params.date_begin = dateStartSelected;
            params.date_end = dateEndSelected;

        }
        AppProvider.getApiManagement().call(HistoryOrderCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<OrderCustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<OrderCustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<OrderCustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initRecyclerViewOrder(list,dateStartSelected,dateEndSelected);
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
    public void onBackProgress() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void goToOrderDetail(OrderCustomerModel model) {
        activity.addFragment(new FragmentOrderDetail().newIntance(model), true, null);
    }

    @Override
    public void goToFilter() {
        activity.replaceFragment(new FragmentFilterOrder(), true, null);
    }
}
