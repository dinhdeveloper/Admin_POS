package qtc.project.pos.ui.views.fragment.product.quanlylohang;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductListModel;

public interface FragmentQuanLyLoHangViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentQuanLyLoHangViewCallback callback);

    void mappingRecyclerView(ArrayList<ProductListModel> list);
}
