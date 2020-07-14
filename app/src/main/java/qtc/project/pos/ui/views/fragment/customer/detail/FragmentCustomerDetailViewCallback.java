package qtc.project.pos.ui.views.fragment.customer.detail;

import qtc.project.pos.model.CustomerModel;

public interface FragmentCustomerDetailViewCallback {
    void onBackProgress();

    void getAllLevelCustomer();

    void updateCustomerDetail(CustomerModel model);

    void deleteCustomer(CustomerModel model);
}
