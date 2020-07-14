package qtc.project.pos.ui.views.fragment.product.doitra.detail;

import qtc.project.pos.model.PackageReturnModel;

public interface FragmentChiTietDonTraHangHoaViewCallback {
    void onBackProgress();

    void updateStatus(PackageReturnModel infoModel);

    void deleteStatus(PackageReturnModel infoModel);
}
