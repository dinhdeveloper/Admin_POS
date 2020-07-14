package qtc.project.pos.ui.views.fragment.history.history.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentOrderDetailCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentOrderDetailCustomerViewCallback callback);

    void sentDataToView(OrderCustomerModel model);
}
