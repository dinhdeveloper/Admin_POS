package qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.thongke;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.DataChartModel;
import qtc.project.pos.model.TongDoanhThuModel;

public interface FragmentThongKeViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentThongKeViewCallback callback);

    void mappingYear(ArrayList<TongDoanhThuModel> list);

    void sentYearToView(String nam);
}
