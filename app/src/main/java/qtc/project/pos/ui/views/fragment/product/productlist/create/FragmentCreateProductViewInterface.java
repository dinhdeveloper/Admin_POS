package qtc.project.pos.ui.views.fragment.product.productlist.create;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductCategoryModel;

public interface FragmentCreateProductViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentCreateProductViewCallback callback);

    void  setDataProductImage(String image);

    void initViewPopup(ArrayList<ProductCategoryModel> list);

    void showPopupSuccess();
}
