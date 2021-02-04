package qtc.project.pos.fragment.product.productcategory;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
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
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.event.UpdateListProductEvent;
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
            if (model != null) {
                view.setDataCategoryDetail(model);
            } else {
                view.setDataCategoryDetail(null);
            }
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
        if (activity != null) {
            activity.deleteTempMedia();
            activity.checkBack();
            BackShowRootViewEvent.post();
        }
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
    public void updateCategoryDetail(ProductCategoryModel categoryModel) {
        if (categoryModel != null) {
            if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
                showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
                return;
            }
            showProgress();
            ProductCategoryUpdateRequest.ApiParams params = new ProductCategoryUpdateRequest.ApiParams();
            if (!TextUtils.isEmpty(categoryModel.getId())){
                params.type_manager = "update_category";
                params.id_category = categoryModel.getId();
            }else {
                params.type_manager = "create_category";
            }
            params.name = categoryModel.getName();
            params.image = categoryModel.getImage();
            params.description = categoryModel.getDescription();
            params.id_code = categoryModel.getId_code();
            AppProvider.getApiManagement().call(ProductCategoryUpdateRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductCategoryModel> body) {
                    if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equalsIgnoreCase("true")) {
                        dismissProgress();
                        UpdateListProductEvent.post();
                        showAlert(body.getMessage(), KAlertDialog.SUCCESS_TYPE);
                    } else {
                        showAlert(body.getMessage(), KAlertDialog.ERROR_TYPE);
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                    showAlert("Không tải được dữ liệu", KAlertDialog.ERROR_TYPE);
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                    showAlert("Không tải được dữ liệu", KAlertDialog.ERROR_TYPE);
                }
            });
        }
    }

    @Override
    public void onClickOptionSelectImageFromCamera() {
        if (activity != null)
            activity.captureImageFromCamera();
    }

    @Override
    public void onClickOptionSelectImageFromGallery() {
        if (activity != null)
            activity.changeToActivitySelectImage();
    }

    @Override
    public void deleteProductCategoryModel(String id) {
        String title = "Xóa danh mục sản phẩm";
        String message = "Bạn có muốn xóa danh mục sản phẩm?";

        showConfirmAlert(title, message, new KAlertDialog.KAlertClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                //confirm
                kAlertDialog.dismiss();
                //request active or lock account
                final KAlertDialog mCustomAlert = new KAlertDialog(getContext());
                mCustomAlert.setContentText("Đang xử lý...")
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .changeAlertType(KAlertDialog.PROGRESS_TYPE);

                mCustomAlert.setCancelable(false);
                mCustomAlert.setCanceledOnTouchOutside(false);
                mCustomAlert.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        showProgress();
                        ProductCategoryUpdateRequest.ApiParams params = new ProductCategoryUpdateRequest.ApiParams();
                        params.type_manager = "delete_category";
                        params.id_category = id;
                        AppProvider.getApiManagement().call(ProductCategoryUpdateRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
                            @Override
                            public void onSuccess(BaseResponseModel<ProductCategoryModel> body) {
                                if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equals("true")) {
                                    mCustomAlert.setContentText("Xóa danh mục thành công.")
                                            .setConfirmText("OK")
                                            .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                                @Override
                                                public void onClick(KAlertDialog kAlertDialog) {
                                                    dismissProgress();
                                                    onBackProgress();
                                                    UpdateListProductEvent.post();
                                                    mCustomAlert.dismissWithAnimation();
                                                    mCustomAlert.dismiss();
                                                }
                                            })
                                            .changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                } else {
                                    showAlert(body.getMessage(), KAlertDialog.ERROR_TYPE);
                                }
                            }

                            @Override
                            public void onError(ErrorApiResponse error) {
                                dismissProgress();
                                mCustomAlert.dismissWithAnimation();
                                mCustomAlert.dismiss();
                            }

                            @Override
                            public void onFail(ApiRequest.RequestError error) {
                                dismissProgress();
                                mCustomAlert.dismissWithAnimation();
                                mCustomAlert.dismiss();
                            }
                        });
                    }
                }, 500);
            }
        }, new KAlertDialog.KAlertClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                //cancel
                kAlertDialog.dismiss();
            }
        }, KAlertDialog.WARNING_TYPE);
    }
}
