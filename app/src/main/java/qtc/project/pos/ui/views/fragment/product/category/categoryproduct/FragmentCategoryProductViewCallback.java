package qtc.project.pos.ui.views.fragment.product.category.categoryproduct;

import qtc.project.pos.model.ProductCategoryModel;

public interface FragmentCategoryProductViewCallback {
    void setBackProgress();

    void onSendData(ProductCategoryModel model);

    void callAllData();

    void callDataToFilter(String toString);
}
