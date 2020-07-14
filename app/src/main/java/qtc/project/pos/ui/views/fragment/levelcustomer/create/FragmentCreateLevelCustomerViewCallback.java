package qtc.project.pos.ui.views.fragment.levelcustomer.create;

import qtc.project.pos.model.LevelCustomerModel;

public interface FragmentCreateLevelCustomerViewCallback {
    void onBackProgress();

    void showDialogSelecteImage();

    void createLevelCustomer(LevelCustomerModel levelCustomerModel);
}
