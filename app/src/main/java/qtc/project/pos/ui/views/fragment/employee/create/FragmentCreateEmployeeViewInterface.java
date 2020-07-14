package qtc.project.pos.ui.views.fragment.employee.create;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.LevelEmployeeModel;

public interface FragmentCreateEmployeeViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentCreateEmployeeViewCallback callback);

    void sendLevelEmployee(ArrayList<LevelEmployeeModel> arrayList);

    void showDiaLogSucess();
}
