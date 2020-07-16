package qtc.project.pos.ui.views.fragment.product.quanlylohang.create;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.SupplierModel;

public interface FragmentCreateLoHangViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentCreateLoHangViewCallback callback);

    void sendDataToView(SupplierModel model);

    void sentDataProductToView(ProductListModel model);

    void onShowSuccess();
}
