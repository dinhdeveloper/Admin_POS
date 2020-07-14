package qtc.project.pos.fragment.product.productcategory;

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
import qtc.project.pos.api.product.productcategory.ProductCategoryCreateRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct.FragmentCreateProductCategoryView;
import qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct.FragmentCreateProductCategoryViewCallback;
import qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct.FragmentCreateProductCategoryViewInterface;

public class FragmentCreateProductCategory extends BaseFragment<FragmentCreateProductCategoryViewInterface, BaseParameters> implements FragmentCreateProductCategoryViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
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
    public void onBackProgress() {
        if (activity != null)
            activity.checkBack();
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
    public void createCategoryProduct(ProductCategoryModel categoryModel) {
        showProgress();
        ProductCategoryCreateRequest.ApiParams params = new ProductCategoryCreateRequest.ApiParams();
        if (categoryModel != null) {
            params.type_manager = "create_category";
            params.name = categoryModel.getName();
            params.description = categoryModel.getDescription();
            params.image = categoryModel.getImage();
            params.id_code = categoryModel.getId_code();
        }

        AppProvider.getApiManagement().call(ProductCategoryCreateRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductCategoryModel> body) {
                dismissProgress();
                if (body.getSuccess().equals("true")) {
                    Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                    view.confirmDialog();
                }
                else if (body.getSuccess().equals("false")){
                    Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
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


    @Override
    protected FragmentCreateProductCategoryViewInterface getViewInstance() {
        return new FragmentCreateProductCategoryView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }
}
