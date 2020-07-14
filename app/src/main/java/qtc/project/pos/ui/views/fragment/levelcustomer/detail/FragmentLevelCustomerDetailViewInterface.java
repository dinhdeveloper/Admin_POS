package qtc.project.pos.ui.views.fragment.levelcustomer.detail;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;

public interface FragmentLevelCustomerDetailViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentLevelCustomerDetailViewCallback callback);

    void sendDataToView(LevelCustomerModel model);

    void setDataLevelImage(String filePath);

    void initCustomer(ArrayList<CustomerModel> list);

}
