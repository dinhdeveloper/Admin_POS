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
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.OrderCustomerModel;
import qtc.project.pos.ui.views.fragment.history.history.detail.FragmentOrderDetailCustomerView;
import qtc.project.pos.ui.views.fragment.history.history.detail.FragmentOrderDetailCustomerViewCallback;
import qtc.project.pos.ui.views.fragment.history.history.detail.FragmentOrderDetailCustomerViewInterface;

public class FragmentOrderDetailCustomer extends BaseFragment<FragmentOrderDetailCustomerViewInterface, BaseParameters> implements FragmentOrderDetailCustomerViewCallback {

    HomeActivity activity;

    public static FragmentOrderDetailCustomer newIntance(OrderCustomerModel item) {
        FragmentOrderDetailCustomer frag = new FragmentOrderDetailCustomer();
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
            OrderCustomerModel model = (OrderCustomerModel) extras.get("model");
            view.sentDataToView(model);
        }
    }

    @Override
    protected FragmentOrderDetailCustomerViewInterface getViewInstance() {
        return new FragmentOrderDetailCustomerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackPregress() {
        if (activity!=null)
            activity.checkBack();
    }
}
