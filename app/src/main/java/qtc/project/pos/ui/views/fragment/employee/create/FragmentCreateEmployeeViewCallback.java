package qtc.project.pos.ui.views.fragment.employee.create;

import qtc.project.pos.model.EmployeeModel;

public interface FragmentCreateEmployeeViewCallback {
    void onBackProgress();

    void callLevelEmployee();

    void createEmployee(EmployeeModel model);
}
