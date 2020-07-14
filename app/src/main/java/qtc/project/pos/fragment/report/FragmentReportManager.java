package qtc.project.pos.fragment.report;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.report.FragmentReportManagerView;
import qtc.project.pos.ui.views.fragment.report.FragmentReportManagerViewCallback;
import qtc.project.pos.ui.views.fragment.report.FragmentReportManagerViewInterface;

public class FragmentReportManager extends BaseFragment<FragmentReportManagerViewInterface, BaseParameters> implements FragmentReportManagerViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentReportManagerViewInterface getViewInstance() {
        return new FragmentReportManagerView();
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
