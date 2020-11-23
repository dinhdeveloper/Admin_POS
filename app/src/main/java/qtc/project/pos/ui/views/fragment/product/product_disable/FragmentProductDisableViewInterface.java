package qtc.project.pos.ui.views.fragment.product.product_disable;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductListModel;

public interface FragmentProductDisableViewInterface extends BaseViewInterface {

    void init(HomeActivity activity, FragmentProductDisableViewCallback callback);

    void setNoMoreLoading();

    void setListData(ProductListModel[] data);

    void clearListDataProduct();

}
