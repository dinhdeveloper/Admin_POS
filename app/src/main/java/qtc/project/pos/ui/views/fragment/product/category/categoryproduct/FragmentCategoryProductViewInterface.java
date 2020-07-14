package qtc.project.pos.ui.views.fragment.product.category.categoryproduct;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductCategoryModel;

public interface FragmentCategoryProductViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentCategoryProductViewCallback callback);
    void initGetListCategoryProduct(ArrayList<ProductCategoryModel> list);
}
