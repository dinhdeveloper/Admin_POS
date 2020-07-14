package qtc.project.pos.ui.views.fragment.supplier;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.SupplierModel;

public interface FragmentSupplierManagerViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentSupplierManagerViewCallback callback);

    void mappingRecyclerView(ArrayList<SupplierModel> list);
}
