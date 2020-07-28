package qtc.project.pos.ui.views.fragment.product.productlist.create;

import qtc.project.pos.model.ProductListModel;

public interface FragmentCreateProductViewCallback {
    void onBackprogress();

    //void showDialogSelecteImage();

    void getAllProductCategory();

    void createProduct(ProductListModel listModel);

    void onClickOptionSelectImageFromCamera();

    void onClickOptionSelectImageFromGallery();
}
