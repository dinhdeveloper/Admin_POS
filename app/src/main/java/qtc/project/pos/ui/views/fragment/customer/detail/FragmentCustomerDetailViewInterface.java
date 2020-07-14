package qtc.project.pos.ui.views.fragment.customer.detail;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;

public interface FragmentCustomerDetailViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentCustomerDetailViewCallback callback);

    void sentDataToView(CustomerModel model);

    void mappingPopup(ArrayList<LevelCustomerModel> list);

    void confirmDialog();
}
