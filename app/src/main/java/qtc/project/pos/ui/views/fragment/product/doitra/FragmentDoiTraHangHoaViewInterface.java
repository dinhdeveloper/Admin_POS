package qtc.project.pos.ui.views.fragment.product.doitra;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.PackageReturnModel;

public interface FragmentDoiTraHangHoaViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentDoiTraHangHoaViewCallback callback);

    void mappingRecyclerView(ArrayList<PackageReturnModel> list);
}
