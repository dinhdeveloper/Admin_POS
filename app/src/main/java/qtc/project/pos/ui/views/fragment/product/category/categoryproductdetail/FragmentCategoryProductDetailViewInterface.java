package qtc.project.pos.ui.views.fragment.product.category.categoryproductdetail;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductCategoryModel;

public interface FragmentCategoryProductDetailViewInterface extends BaseViewInterface {
    void init(HomeActivity activity, FragmentCategoryProductDetailViewCallback callback);

    void sendDataToView(ProductCategoryModel model);
    void setDataProductImage(String outfile);

    void onBack();

    void confirmDialog();

    void confirm();
}
