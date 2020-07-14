package qtc.project.pos.ui.views.fragment.levelcustomer.detail;

import qtc.project.pos.model.LevelCustomerModel;

public interface FragmentLevelCustomerDetailViewCallback {

    void onBackProgress();
    void showDialogSelecteImage();
    void showDialogTakePicture();

    void updateData(LevelCustomerModel levelCustomerModel);

    void xemSoNguoiCoCapDo(String  id);

    void callDataSearchCus(String toString,String id);

    void callAllDataCustomer();

    void deleteLevelCustomer(String level_id);
}
