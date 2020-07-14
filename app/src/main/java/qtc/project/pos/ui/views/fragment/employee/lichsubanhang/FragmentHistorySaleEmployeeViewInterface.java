package qtc.project.pos.ui.views.fragment.employee.lichsubanhang;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentHistorySaleEmployeeViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentHistorySaleEmployeeViewCallback callback);

    void sendDataToView(ArrayList<OrderCustomerModel> list);
}
