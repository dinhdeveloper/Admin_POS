package qtc.project.pos.ui.views.fragment.product.quanlylohang.trahang;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.PackageInfoModel;

public interface FragmentDonTraHangViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentDonTraHangViewCallback callback);

    void sendDataToView(PackageInfoModel infoModel,String name,String id);

    void setOnBack();
}
