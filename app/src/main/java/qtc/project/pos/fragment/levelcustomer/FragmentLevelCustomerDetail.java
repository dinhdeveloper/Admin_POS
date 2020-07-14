package qtc.project.pos.fragment.levelcustomer;

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
import qtc.project.pos.api.customer.CustomerRequest;
import qtc.project.pos.api.levelcustomer.LevelCustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.productlist.FragmentProductListDetail;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.levelcustomer.FragmentLevelCustomerViewCallback;
import qtc.project.pos.ui.views.fragment.levelcustomer.FragmentLevelCustomerViewInterface;
import qtc.project.pos.ui.views.fragment.levelcustomer.detail.FragmentLevelCustomerDetailView;
import qtc.project.pos.ui.views.fragment.levelcustomer.detail.FragmentLevelCustomerDetailViewCallback;
import qtc.project.pos.ui.views.fragment.levelcustomer.detail.FragmentLevelCustomerDetailViewInterface;

public class FragmentLevelCustomerDetail extends BaseFragment<FragmentLevelCustomerDetailViewInterface, BaseParameters> implements FragmentLevelCustomerDetailViewCallback {

    HomeActivity activity;

    public static FragmentLevelCustomerDetail newIntance(LevelCustomerModel item) {
        FragmentLevelCustomerDetail frag = new FragmentLevelCustomerDetail();
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
            LevelCustomerModel model = (LevelCustomerModel) extras.get("model");
            view.sendDataToView(model);
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
                            view.setDataLevelImage(compressedImageFile.getAbsolutePath());
                        } else {
                            view.setDataLevelImage(filePath);
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

    public void onBack() {
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
    public void updateData(LevelCustomerModel levelCustomerModel) {
        if (levelCustomerModel != null) {
            showProgress();
            LevelCustomerRequest.ApiParams params = new LevelCustomerRequest.ApiParams();
            params.type_manager = "update_level";
            params.id_code = levelCustomerModel.getId_code();
            params.description = levelCustomerModel.getDescription();
            params.discount = levelCustomerModel.getDiscount();
            params.id_level = levelCustomerModel.getId();
            params.image = levelCustomerModel.getImage();
            params.name = levelCustomerModel.getName();
            AppProvider.getApiManagement().call(LevelCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<LevelCustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<LevelCustomerModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")) {
                        Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                        onBack();
                    } else if (body.getSuccess().equals("false")) {
                        Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void xemSoNguoiCoCapDo(String id) {
        showProgress();
        if (id != null) {
            CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
            params.type_manager = "list_customer";
            params.level_id = id;
            AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<CustomerModel> body) {
                    dismissProgress();
                    ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initCustomer(list);
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
    }

    @Override
    public void callDataSearchCus(String toString, String id) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        if (toString != null) {
            params.type_manager = "list_customer";
            params.customer_filter = toString;
            params.level_id = id;
        }
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initCustomer(list);
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

    @Override
    public void callAllDataCustomer() {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.type_manager = "list_customer";
        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<CustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.initCustomer(list);
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

    @Override
    public void deleteLevelCustomer(String level_id) {
        if (level_id != null) {
            LevelCustomerRequest.ApiParams params = new LevelCustomerRequest.ApiParams();
            params.type_manager = "delete_level";
            params.id_level = level_id;

            AppProvider.getApiManagement().call(LevelCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<LevelCustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<LevelCustomerModel> body) {
                    if (body.getSuccess().equals("true")) {
                        Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                        onBack();
                    } else if (body.getSuccess().equals("false")) {
                        Toast.makeText(activity, "" + body.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {

                }

                @Override
                public void onFail(ApiRequest.RequestError error) {

                }
            });
        }
    }

    @Override
    protected FragmentLevelCustomerDetailViewInterface getViewInstance() {
        return new FragmentLevelCustomerDetailView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }
}
