package qtc.project.pos.ui.views.fragment.employee.lichsubanhang.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentOrderDetailEmployeeViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentOrderDetailEmployeeViewCallback callback);

    void sentDataToView(OrderCustomerModel model);
}
