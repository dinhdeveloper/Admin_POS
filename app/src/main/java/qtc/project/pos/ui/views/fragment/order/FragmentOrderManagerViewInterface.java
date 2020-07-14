package qtc.project.pos.ui.views.fragment.order;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentOrderManagerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentOrderManagerViewCallback callback);

    void initRecyclerViewOrder(ArrayList<OrderCustomerModel> list,String dateStart, String dateEnd);
}
