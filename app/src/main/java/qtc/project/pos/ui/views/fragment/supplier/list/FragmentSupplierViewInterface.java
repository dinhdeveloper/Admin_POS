package qtc.project.pos.ui.views.fragment.supplier.list;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.SupplierModel;

public interface FragmentSupplierViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentSupplierViewCallback callback);

    void mappingRecyclerView(ArrayList<SupplierModel> list);
}
