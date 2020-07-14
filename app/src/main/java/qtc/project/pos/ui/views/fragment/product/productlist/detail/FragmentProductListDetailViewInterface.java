package qtc.project.pos.ui.views.fragment.product.productlist.detail;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.model.ProductListModel;

public interface FragmentProductListDetailViewInterface extends BaseViewInterface {
    void  init(HomeActivity activity,FragmentProductListDetailViewCallback callback);

    void sendDataToView(ProductListModel model);
    void  setDataProductImage(String image);
    void initViewPopup(ArrayList<ProductCategoryModel> list);

    void showConfirm();

    void showConfirmDelete();
}
