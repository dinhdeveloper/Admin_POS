package qtc.project.pos.ui.views.fragment.customer.create;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.LevelCustomerModel;

public interface FragmentCreateCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentCreateCustomerViewCallback callback);

    void mappingPopup(ArrayList<LevelCustomerModel> list);

    void showAlert();
}
