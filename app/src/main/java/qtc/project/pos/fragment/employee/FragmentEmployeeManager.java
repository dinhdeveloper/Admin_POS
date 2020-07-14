package qtc.project.pos.fragment.employee;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.employee.FragmentEmployeeManagerView;
import qtc.project.pos.ui.views.fragment.employee.FragmentEmployeeManagerViewCallback;
import qtc.project.pos.ui.views.fragment.employee.FragmentEmployeeManagerViewInterface;

public class FragmentEmployeeManager extends BaseFragment<FragmentEmployeeManagerViewInterface, BaseParameters> implements FragmentEmployeeManagerViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentEmployeeManagerViewInterface getViewInstance() {
        return new FragmentEmployeeManagerView();
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
    public void goToDSNV() {
        activity.addFragment(new FragmentEmployeeList(), true, null);
    }
//
//    @Override
//    public void goToKSLSBH() {
//        activity.addFragment(new FragmentHistorySaleEmployee(), true, null);
//    }
}
