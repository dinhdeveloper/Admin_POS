package qtc.project.pos.ui.views.fragment.supplier.detail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.SupplierModel;

public interface FragmentSupplierDetailViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentSupplierDetailViewCallback callback);

    void sendDataToView(SupplierModel infoModel);

    void showDialog();

    void showDialogSucc();
}
