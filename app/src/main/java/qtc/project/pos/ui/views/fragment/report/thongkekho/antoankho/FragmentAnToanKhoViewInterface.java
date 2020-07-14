package qtc.project.pos.ui.views.fragment.report.thongkekho.antoankho;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.AnToanKhoModel;

public interface FragmentAnToanKhoViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentAnToanKhoViewCallback callback);

    void sendDataToView(ArrayList<AnToanKhoModel> list);
}
