package qtc.project.pos.ui.views.fragment.supplier.create;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;

public interface FragmentCreateSupplierViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentCreateSupplierViewCallback callback);

    void showAlerSucess();
}
