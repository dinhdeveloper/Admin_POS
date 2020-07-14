package qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct;

import qtc.project.pos.model.ProductCategoryModel;

public interface FragmentCreateProductCategoryViewCallback {

    void onBackProgress();
    void showDialogSelecteImage();
    void showDialogTakePicture();

    void createCategoryProduct(ProductCategoryModel categoryModel);
}
