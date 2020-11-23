package qtc.project.pos.ui.views.fragment.product.product_disable;


import qtc.project.pos.model.ProductListModel;

public interface FragmentProductDisableViewCallback {
    void loadMore();

    void goToProductDisable(ProductListModel model);

    void onBackprogress();

    void searchProduct(String name);

    void callAllData();
}
