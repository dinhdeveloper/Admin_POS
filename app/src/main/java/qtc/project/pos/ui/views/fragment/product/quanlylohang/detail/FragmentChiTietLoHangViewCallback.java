package qtc.project.pos.ui.views.fragment.product.quanlylohang.detail;

import qtc.project.pos.model.PackageInfoModel;

public interface FragmentChiTietLoHangViewCallback {
    void onBackProgress();

    void getAllDataNhaCungUng();

    void updateDataPackage(PackageInfoModel infoModel,String id);

    void taoDonTraHang(PackageInfoModel infoModel,String name,String id);
}
