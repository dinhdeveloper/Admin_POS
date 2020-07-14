package qtc.project.pos.ui.views.fragment.order.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentOrderDetailViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentOrderDetailViewCallback callback);

    void sentDataToView(OrderCustomerModel model);
}
