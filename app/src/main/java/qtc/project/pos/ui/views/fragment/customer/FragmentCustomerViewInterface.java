package qtc.project.pos.ui.views.fragment.customer;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.CustomerModel;

public interface FragmentCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentCustomerViewCallback callback);

    void mappingRecyclerView(ArrayList<CustomerModel> list);
}
