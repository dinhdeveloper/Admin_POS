package qtc.project.pos.ui.views.fragment.customer.create;

import qtc.project.pos.model.CustomerModel;

public interface FragmentCreateCustomerViewCallback {
    void onBackProgress();

    void getAllLevelCustomer();

    void createCustomer(CustomerModel customerModel);
}
