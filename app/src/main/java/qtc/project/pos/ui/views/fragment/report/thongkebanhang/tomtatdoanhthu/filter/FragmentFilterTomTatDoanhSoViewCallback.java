package qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.filter;

public interface FragmentFilterTomTatDoanhSoViewCallback {
    void onBackProgress();

    void filterDataTheoThang(String thang, String nam,int date);

    void filterDataYear(String nam);
}
