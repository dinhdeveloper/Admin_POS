package qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.tongdoanhthu;

public interface FragmentTongDoanhThuViewCallback {
    void onBackProgress();

    void chooseTimeStart(int i);

    void chooseTimeEnd(int i);

    void searchData(String date_start,String date_end);

    void goToFragmentThongKe();
}
