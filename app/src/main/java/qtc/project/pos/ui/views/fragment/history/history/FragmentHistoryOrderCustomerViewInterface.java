package qtc.project.pos.ui.views.fragment.history.history;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentHistoryOrderCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentHistoryOrderCustomerViewCallback callback);

    void sendDataToView(ArrayList<OrderCustomerModel> model);
}
