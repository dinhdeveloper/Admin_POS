package qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_sanpham;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.TongDoanhThuModel;

public interface FragmentDoanhThuTheoSpViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentDoanhThuTheoSpViewCallback callback);

    void mappingListRecyclerview(ArrayList<ProductListModel> list);

    void mappingDateToView(String nam, String thang, int ngay);

    void sentYearToView(String nam);

    void sentDataTongDoanhThu(List<TongDoanhThuModel> models);

    void mappingYear(ArrayList<TongDoanhThuModel> list);
}
