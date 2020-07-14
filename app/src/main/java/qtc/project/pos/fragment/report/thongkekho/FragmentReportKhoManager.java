package qtc.project.pos.fragment.report.thongkekho;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.report.thongkekho.FragmentReportKhoManagerView;
import qtc.project.pos.ui.views.fragment.report.thongkekho.FragmentReportKhoManagerViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkekho.FragmentReportKhoManagerViewInterface;

public class FragmentReportKhoManager extends BaseFragment<FragmentReportKhoManagerViewInterface, BaseParameters> implements FragmentReportKhoManagerViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentReportKhoManagerViewInterface getViewInstance() {
        return new FragmentReportKhoManagerView();
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
