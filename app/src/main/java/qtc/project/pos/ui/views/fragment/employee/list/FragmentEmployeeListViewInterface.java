package qtc.project.pos.ui.views.fragment.employee.list;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.EmployeeModel;

public interface FragmentEmployeeListViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentEmployeeListViewCallback callback);

    void mappingRecyclerView(ArrayList<EmployeeModel> list);
}
