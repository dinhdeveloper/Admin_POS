package qtc.project.pos.ui.views.fragment.product.quanlylohang.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.SupplierModel;

public interface FragmentChiTietLoHangViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentChiTietLoHangViewCallback callback);

    void sendDataToView(PackageInfoModel infoModel, String name,String id);

    void sendDataToViewTwo(SupplierModel supplierModel);
}
