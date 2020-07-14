package qtc.project.pos.fragment.order;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.order.filter.FragmentFilterOrderView;
import qtc.project.pos.ui.views.fragment.order.filter.FragmentFilterOrderViewCallback;
import qtc.project.pos.ui.views.fragment.order.filter.FragmentFilterOrderViewInterface;

public class FragmentFilterOrder extends BaseFragment<FragmentFilterOrderViewInterface, BaseParameters> implements FragmentFilterOrderViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentFilterOrderViewInterface getViewInstance() {
        return new FragmentFilterOrderView();
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
    public void filterOnDay(String dateStartSelected, String dateEndSelected) {
        activity.replaceFragment(new FragmentOrderManager().newIntance(dateStartSelected,dateEndSelected),false,null);
    }
}
