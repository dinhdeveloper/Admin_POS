package qtc.project.pos.ui.views.fragment.product.productlist.detail;

import qtc.project.pos.model.ProductListModel;

public interface FragmentProductListDetailViewCallback {
    void onBackprogress();

    //void showDialogSelecteImage();

    void getAllProductCategory();

    void updateProductDetail(ProductListModel listModel);

    void deleteProduct(ProductListModel model);

    void onClickOptionSelectImageFromCamera();

    void onClickOptionSelectImageFromGallery();

    void disableProduct(String id);
}
