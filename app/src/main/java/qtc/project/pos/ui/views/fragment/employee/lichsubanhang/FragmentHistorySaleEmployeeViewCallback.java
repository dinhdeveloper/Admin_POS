package qtc.project.pos.ui.views.fragment.employee.lichsubanhang;

import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentHistorySaleEmployeeViewCallback {
    void onBackProgress();

    void getAllDataHistory();

    void goToDetailOrder(OrderCustomerModel model);
}
