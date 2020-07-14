package qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;

public interface FragmentCreateProductCategoryViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentCreateProductCategoryViewCallback callback);
    void setDataProductImage(String image);

    void onBack();

    void confirmDialog();
}
