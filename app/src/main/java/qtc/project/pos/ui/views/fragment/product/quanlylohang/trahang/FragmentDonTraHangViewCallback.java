package qtc.project.pos.ui.views.fragment.product.quanlylohang.trahang;

import qtc.project.pos.model.PackageReturnModel;

public interface FragmentDonTraHangViewCallback {
    void onBackProgress();

    void setDataDoiTraHang(PackageReturnModel returnModel);

    void setOnBack();
}
