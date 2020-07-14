package qtc.project.pos.ui.views.fragment.employee.list;

import qtc.project.pos.model.EmployeeModel;

public interface FragmentEmployeeListViewCallback {
    void getAllDataEmployee();

    void onBackProgress();

    void goToDetailEmployee(EmployeeModel model);

    void updateEmployee(EmployeeModel item);

    void createEmployee();

    void getHistoryOrderEmployee(EmployeeModel model);
}
