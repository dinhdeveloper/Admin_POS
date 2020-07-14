package qtc.project.pos.ui.views.fragment.report.thongkekho.tonkho_vs_doanhthu;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.DataChartModel;
import qtc.project.pos.model.TonKho_Vs_DoanhThuModel;

public interface FragmentTK_TonKho_VS_DoanhThuViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentTK_TonKho_VS_DoanhThuViewCallback callback);

    void sentDateToView(String nam, String thang, int ngay);

    void mappingDateToView(ArrayList<TonKho_Vs_DoanhThuModel> list);
}
