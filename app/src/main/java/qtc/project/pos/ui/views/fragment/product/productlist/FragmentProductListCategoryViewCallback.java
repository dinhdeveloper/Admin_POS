package qtc.project.pos.ui.views.fragment.product.productlist;

import qtc.project.pos.model.ProductListModel;

public interface FragmentProductListCategoryViewCallback {
    void onBackprogress();

    void goToProductListDetail(ProductListModel model);
}
