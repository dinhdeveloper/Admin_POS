package qtc.project.pos.ui.views.fragment.product.productlist;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductListModel;

public interface FragmentProductListCategoryViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentProductListCategoryViewCallback callback);

    void mappingRecyclerView(ArrayList<ProductListModel> list);
}
