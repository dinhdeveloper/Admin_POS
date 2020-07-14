package qtc.project.pos.ui.views.fragment.levelcustomer;

import qtc.project.pos.model.LevelCustomerModel;

public interface FragmentLevelCustomerViewCallback {
    void callDataLevelCustomer();

    void sendDataToDetail(LevelCustomerModel model);

    void onBackProgress();
}
