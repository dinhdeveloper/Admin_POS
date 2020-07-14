package qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_sanpham;

public interface FragmentDoanhThuTheoSpViewCallback {
    void onBackProgress();

    void searchProduct(String search);

    void goToFragmentFilter(int status);

    void chooseYear();

    void getDataFilterTime(String date_start, String date_end, String id_product);

    void getDataToYear(String nam, String id_product);
}
