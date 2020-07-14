package qtc.project.pos.ui.views.fragment.levelcustomer.create;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.levelcustomer.FragmentCreateLevelCustomer;

public interface FragmentCreateLevelCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentCreateLevelCustomerViewCallback callback);

    void setDataProductImage(String filePath);
}
