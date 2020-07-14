package qtc.project.pos.ui.views.fragment.product.doitra.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.PackageReturnModel;

public interface FragmentChiTietDonTraHangHoaViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentChiTietDonTraHangHoaViewCallback callback);

    void sendDataToView(PackageReturnModel infoModel);

    void showConfirmDelete();
}
