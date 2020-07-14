package qtc.project.pos.ui.views.fragment.report.thongkekho.tonkho_vs_doanhthu;

import java.util.List;

import qtc.project.pos.model.Stock_Income_Model;

public interface FragmentTK_TonKho_VS_DoanhThuViewCallback {
    void onBackProgress();

    void goToFilterDate(int status);

    void searchDataToDate(String date_start, String date_end);
    
}
