package qtc.project.pos.fragment.product.productcategory;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import id.zelory.compressor.Compressor;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productcategory.ProductCategoryUpdateRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.ui.views.fragment.product.category.categoryproductdetail.FragmentCategoryProductDetailView;
import qtc.project.pos.ui.views.fragment.product.category.categoryproductdetail.FragmentCategoryProductDetailViewCallback;
import qtc.project.pos.ui.views.fragment.product.category.categoryproductdetail.FragmentCategoryProductDetailViewInterface;

public class FragmentCategoryProductDetail extends BaseFragment<FragmentCategoryProductDetailViewInterface, BaseParameters> implements FragmentCategoryProductDetailViewCallback {
    HomeActivity activity;

    public static FragmentCategoryProductDetail newIntance(ProductCategoryModel item) {
        FragmentCategoryProductDetail frag = new FragmentCategoryProductDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", item);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        getDataToBundle();
    }

    private void getDataToBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            ProductCategoryModel model = (ProductCategoryModel) extras.get("model");
            view.sendDataToView(model);
        }
    }

    @Override
    protected FragmentCategoryProductDetailViewInterface getViewInstance() {
        return new FragmentCategoryProductDetailView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity != null)
            activity.checkBack();
    }

    public void setImageSelected(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            showAlert(getString(R.string.error_load_file_image), KAlertDialog.ERROR_TYPE);
            return;
        }

        reduceImageSize(filePath);
    }

    private void reduceImageSize(String filePath) {

        File fileImage = new File(filePath);

        if (fileImage.exists()) {

            try {
                File compressedImageFile = new Compressor(getContext()).compressToFile(fileImage);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (compressedImageFile.exists()) {
                            view.setDataProductImage(compressedImageFile.getAbsolutePath());
                        } else {
                            view.setDataProductImage(filePath);
                        }
                    }
                }, 300);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showDialogSelecteImage() {
        if (activity != null)
            activity.changeToActivitySelectImage();
    }

    @Override
    public void showDialogTakePicture() {
        if (activity != null)
            activity.captureImageFromCamera();
    }

    @Override
    public void updateData(ProductCategoryModel categoryModel) {
        if (categoryModel != null) {
            showProgress();
            ProductCategoryUpdateRequest.ApiParams params = new ProductCategoryUpdateRequest.ApiParams();
            params.type_manager = "update_category";
            params.id_category = categoryModel.getId();
            params.name = categoryModel.getName();
            params.image = categoryModel.getImage();
            params.description = categoryModel.getDescription();
            params.id_code  = categoryModel.getId_code();
            AppProvider.getApiManagement().call(ProductCategoryUpdateRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductCategoryModel> body) {
                    if (body.getSuccess().equals("true")) {
                        dismissProgress();
                        Toast.makeText(activity, body.getMessage(), Toast.LENGTH_SHORT).show();
                        view.confirmDialog();
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                }
            });
        }
    }

    @Override
    public void deleteProductCategoryModel(String id) {
        if (id!=null){
            showProgress();
            ProductCategoryUpdateRequest.ApiParams params = new ProductCategoryUpdateRequest.ApiParams();
            params.type_manager = "delete_category";
            params.id_category = id;
            AppProvider.getApiManagement().call(ProductCategoryUpdateRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductCategoryModel> body) {
                    if (body.getSuccess().equals("true")) {
                        dismissProgress();
                        Toast.makeText(activity, body.getMessage(), Toast.LENGTH_SHORT).show();
                        view.confirm();
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                }
            });
        }
    }
}
