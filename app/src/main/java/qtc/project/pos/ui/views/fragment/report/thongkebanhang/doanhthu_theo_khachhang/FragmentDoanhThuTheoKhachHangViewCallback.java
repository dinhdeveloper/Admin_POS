package qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_khachhang;

public interface FragmentDoanhThuTheoKhachHangViewCallback {
    void onBackProgress();

    void searchCustomer(String sdt);

    void goToFragmentFilter(int status);

    void getDataFilterTime(String date_start, String date_end,String id_customer);

    void chooseYear();

    void getDataToYear(String nam,String customer_id);
}
