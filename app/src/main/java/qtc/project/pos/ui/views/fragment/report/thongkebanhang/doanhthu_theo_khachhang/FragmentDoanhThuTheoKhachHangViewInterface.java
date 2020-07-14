package qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_khachhang;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.TongDoanhThuModel;

public interface FragmentDoanhThuTheoKhachHangViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentDoanhThuTheoKhachHangViewCallback callback);

    void mappingView(ArrayList<CustomerModel> list);

    void mappingDateToView(String nam, String thang, int ngay);

    void sentDataTongDoanhThu(List<TongDoanhThuModel> models);

    void sentYearToView(String nam);

    void mappingYear(ArrayList<TongDoanhThuModel> list);
}
