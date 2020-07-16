package qtc.project.pos.ui.views.fragment.product.quanlylohang.create;

import qtc.project.pos.model.PackageInfoModel;

public interface FragmentCreateLoHangViewCallback {
    void onBackProgress();

    void getAllDataNhaCungUng();

    void getAllDataProduct(boolean check);

    void createPackageInfo(PackageInfoModel listModel,String id_product);
}
