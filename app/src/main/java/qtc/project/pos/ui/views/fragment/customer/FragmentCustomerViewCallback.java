package qtc.project.pos.ui.views.fragment.customer;

import qtc.project.pos.model.CustomerModel;

public interface FragmentCustomerViewCallback {
    void onBackprogress();

    void getDataCustomer();

    void getCustomerDetail(CustomerModel model);

    void getHistoryOrderCustomer(CustomerModel model);

    void createCustomer();
}
