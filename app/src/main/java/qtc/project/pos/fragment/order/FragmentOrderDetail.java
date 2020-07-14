package qtc.project.pos.fragment.order;

import android.os.Bundle;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.customer.FragmentCustomerDetail;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.OrderCustomerModel;
import qtc.project.pos.ui.views.fragment.order.detail.FragmentOrderDetailView;
import qtc.project.pos.ui.views.fragment.order.detail.FragmentOrderDetailViewCallback;
import qtc.project.pos.ui.views.fragment.order.detail.FragmentOrderDetailViewInterface;

public class FragmentOrderDetail extends BaseFragment<FragmentOrderDetailViewInterface, BaseParameters> implements FragmentOrderDetailViewCallback {

   HomeActivity activity;

    public static FragmentOrderDetail newIntance(OrderCustomerModel item) {
        FragmentOrderDetail frag = new FragmentOrderDetail();
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
    protected FragmentOrderDetailViewInterface getViewInstance() {
        return new FragmentOrderDetailView();
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
}
