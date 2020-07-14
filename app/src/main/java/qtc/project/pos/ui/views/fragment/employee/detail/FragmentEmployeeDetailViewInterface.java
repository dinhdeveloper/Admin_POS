package qtc.project.pos.ui.views.fragment.employee.detail;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.model.LevelEmployeeModel;

public interface FragmentEmployeeDetailViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentEmployeeDetailViewCallback callback);

    void sentDataToView(EmployeeModel model);

    void sendLevelEmployee(ArrayList<LevelEmployeeModel> arrayList);

    void showDiaLogUpdate();

    void showDiaLogDelete();
}
