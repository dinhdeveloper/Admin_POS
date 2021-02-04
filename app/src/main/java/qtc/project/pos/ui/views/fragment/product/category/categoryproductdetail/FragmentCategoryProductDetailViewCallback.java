package qtc.project.pos.ui.views.fragment.product.category.categoryproductdetail;

import qtc.project.pos.model.ProductCategoryModel;

public interface FragmentCategoryProductDetailViewCallback {
    void onBackProgress();
    void showDialogSelecteImage();

    void showDialogTakePicture();

    void updateCategoryDetail(ProductCategoryModel categoryModel);

    void deleteProductCategoryModel(String id);

    void onClickOptionSelectImageFromCamera();

    void onClickOptionSelectImageFromGallery();
}
