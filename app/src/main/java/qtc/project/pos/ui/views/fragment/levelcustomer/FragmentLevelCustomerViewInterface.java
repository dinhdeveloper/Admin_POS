package qtc.project.pos.ui.views.fragment.levelcustomer;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.LevelCustomerModel;

public interface FragmentLevelCustomerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentLevelCustomerViewCallback callback);

    void initRecyclerView(ArrayList<LevelCustomerModel> list);
}
