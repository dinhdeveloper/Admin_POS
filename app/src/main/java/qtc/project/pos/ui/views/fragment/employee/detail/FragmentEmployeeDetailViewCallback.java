package qtc.project.pos.ui.views.fragment.employee.detail;

import qtc.project.pos.model.EmployeeModel;

public interface FragmentEmployeeDetailViewCallback {
    void onBackProgress();

    void callLevelEmployee();

    void updateEmployee(EmployeeModel employeeModel);

    void deleteEmployee(EmployeeModel model);

    void reSetPass(String id,String newPass);
}
