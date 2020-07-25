package qtc.project.pos.ui.views.fragment.report.thongkekho.tonkho_vs_doanhthu;

public interface FragmentTK_TonKho_VS_DoanhThuViewCallback {
    void onBackProgress();

    void goToFilterDate(int status);

    void searchDataToDate(String date_start, String date_end);
    
}
