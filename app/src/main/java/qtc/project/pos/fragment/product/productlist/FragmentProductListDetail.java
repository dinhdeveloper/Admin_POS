package qtc.project.pos.fragment.product.productlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import id.zelory.compressor.Compressor;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.activity.Qr_BarcodeActivity;
import qtc.project.pos.api.product.productcategory.ProductCategoryRequest;
import qtc.project.pos.api.product.productcategory.ProductCategoryUpdateRequest;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.event.UpdateListProductEvent;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.productlist.detail.FragmentProductListDetailView;
import qtc.project.pos.ui.views.fragment.product.productlist.detail.FragmentProductListDetailViewCallback;
import qtc.project.pos.ui.views.fragment.product.productlist.detail.FragmentProductListDetailViewInterface;

public class FragmentProductListDetail extends BaseFragment<FragmentProductListDetailViewInterface, BaseParameters> implements FragmentProductListDetailViewCallback {


    public static FragmentProductListDetail newIntance(ProductListModel item) {
        FragmentProductListDetail frag = new FragmentProductListDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", item);
        frag.setArguments(bundle);
        return frag;
    }

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
        callDataToBundle();
    }

    private void callDataToBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            ProductListModel model = (ProductListModel) extras.get("model");
            if (model != null) {
                view.setProductDetail(model);
            } else {
                view.setProductDetail(null);
            }
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

    public void gotoQr_BarcodeActivity() {
        startActivity(new Intent(activity, Qr_BarcodeActivity.class));
    }

    @Override
    public void getAllProductCategory() {
        showProgress();
        ProductCategoryRequest.ApiParams params = new ProductCategoryRequest.ApiParams();
        params.type_manager = "list_category";

        AppProvider.getApiManagement().call(ProductCategoryRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductCategoryModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<ProductCategoryModel> body) {
                dismissProgress();
                if (body != null) {
                    ArrayList<ProductCategoryModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initViewPopup(list);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("", error.name());
            }
        });
    }

    @Override
    public void updateProductDetail(ProductListModel listModel) {
        showProgress();
        if (listModel != null) {
            ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
            if (!TextUtils.isEmpty(listModel.getId())) {
                params.type_manager = "update_product";
                params.id_product = listModel.getId();
            } else {
                params.type_manager = "create_product";
            }
            params.id_code = listModel.getId_code();
            params.name = listModel.getName();
            params.description = listModel.getDescription();
            params.barcode = listModel.getBarcode();
            params.category_id = listModel.getCategory_id();
            params.quantity_safetystock = listModel.getQuantity_safetystock();
            params.qr_code = listModel.getQr_code();
            params.image = listModel.getImage();
            params.price_sell = listModel.getPrice_sell();

            AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    dismissProgress();
                    if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equals("true")) {
                        showAlert(body.getMessage(), KAlertDialog.SUCCESS_TYPE);
                        UpdateListProductEvent.post();
                        if (params.type_manager.equalsIgnoreCase("create_product")) {
                            view.clearData();
                        }
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
    public void deleteProduct(ProductListModel model) {
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
                        if (model != null) {
                            ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
                            params.type_manager = "delete_product";
                            params.id_product = model.getId();

                            AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                                @Override
                                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                                    dismissProgress();
                                    if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equals("true")) {
                                        mCustomAlert.setContentText("Xóa sản phẩm thành công.")
                                                .setConfirmText("OK")
                                                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                                    @Override
                                                    public void onClick(KAlertDialog kAlertDialog) {
                                                        dismissProgress();
                                                        onBackprogress();
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

    @Override
    public void disableProduct(String id) {
        String title = "Vô hiệu hóa sản phẩm";
        String message = "Bạn có muốn vô hiệu hóa sản phẩm?";

        activity.showConfirmAlert(title, message, new KAlertDialog.KAlertClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                //confirm
                kAlertDialog.dismiss();
                //request active or lock account

                requestDisableProduct(id);
            }
        }, new KAlertDialog.KAlertClickListener() {
            @Override
            public void onClick(KAlertDialog kAlertDialog) {
                //cancel
                kAlertDialog.dismiss();
            }
        }, KAlertDialog.WARNING_TYPE);
    }

    private void requestDisableProduct(String id) {
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

                if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
                    showAlert(getString(R.string.error_connect_internet), KAlertDialog.ERROR_TYPE);
                    return;
                }
                showProgress();

                showProgress();
                ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
                params.type_manager = "update_status_product";
                params.id_product = id;
                params.status_product = "N";
                AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                    @Override
                    public void onSuccess(BaseResponseModel<ProductListModel> body) {
                        dismissProgress();

                        if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equalsIgnoreCase("true")) {
                            mCustomAlert.setContentText("Cập nhật sản phẩm thành công.")
                                    .setConfirmText("OK")
                                    .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                        @Override
                                        public void onClick(KAlertDialog kAlertDialog) {
                                            mCustomAlert.dismissWithAnimation();
                                            onBackprogress();
                                            UpdateListProductEvent.post();
                                        }
                                    })
                                    .changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        } else {
                            if (!TextUtils.isEmpty(body.getMessage())) {
                                showAlert(body.getMessage(), KAlertDialog.ERROR_TYPE);
                            } else {
                                activity.showAlert("Không thể tải dịch vụ", KAlertDialog.ERROR_TYPE);
                            }
                        }
                    }

                    @Override
                    public void onError(ErrorApiResponse error) {
                        dismissProgress();
                        Log.e("onError", error.message);
                    }

                    @Override
                    public void onFail(ApiRequest.RequestError error) {
                        dismissProgress();
                        Log.e("onFail", error.name());
                    }
                });
            }
        }, 500);
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
    protected FragmentProductListDetailViewInterface getViewInstance() {
        return new FragmentProductListDetailView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackprogress() {
        if (activity != null) {
            activity.deleteTempMedia();
            activity.checkBack();
            BackShowRootViewEvent.post();
        }
    }

}
