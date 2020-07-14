package qtc.project.pos.fragment.report.thongkebanhang;

import android.view.View;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.FragmentSalesSummaryManagerView;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.FragmentSalesSummaryManagerViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.FragmentSalesSummaryManagerViewInterface;

public class FragmentSalesSummaryManager extends BaseFragment<FragmentSalesSummaryManagerViewInterface, BaseParameters> implements FragmentSalesSummaryManagerViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentSalesSummaryManagerViewInterface getViewInstance() {
        return new FragmentSalesSummaryManagerView();
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
