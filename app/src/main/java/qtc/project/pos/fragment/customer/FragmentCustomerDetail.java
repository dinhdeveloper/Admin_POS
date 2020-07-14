package qtc.project.pos.fragment.customer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.customer.CustomerRequest;

import qtc.project.pos.api.levelcustomer.LevelCustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.history.FragmentHistoryOrderCustomer;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.model.OrderCustomerModel;
import qtc.project.pos.ui.views.fragment.customer.detail.FragmentCustomerDetailView;
import qtc.project.pos.ui.views.fragment.customer.detail.FragmentCustomerDetailViewCallback;
import qtc.project.pos.ui.views.fragment.customer.detail.FragmentCustomerDetailViewInterface;

public class FragmentCustomerDetail extends BaseFragment<FragmentCustomerDetailViewInterface, BaseParameters> implements FragmentCustomerDetailViewCallback {

    HomeActivity activity;

    public static FragmentCustomerDetail newIntance(CustomerModel item) {
        FragmentCustomerDetail frag = new FragmentCustomerDetail();
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
            CustomerModel model = (CustomerModel) extras.get("model");
            view.sentDataToView(model);
        }
    }

    @Override
    protected FragmentCustomerDetailViewInterface getViewInstance() {
        return new FragmentCustomerDetailView();
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

    @Override
    public void getAllLevelCustomer() {
        showProgress();
        LevelCustomerRequest.ApiParams params = new LevelCustomerRequest.ApiParams();
        params.type_manager = "list_level";
        AppProvider.getApiManagement().call(LevelCustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<LevelCustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<LevelCustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<LevelCustomerModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.mappingPopup(list);
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
    public void updateCustomerDetail(CustomerModel model) {

        showProgress();
        if (model != null) {
            CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
            params.type_manager = "update_customer";
            params.id_customer = AppProvider.getPreferences().getUserModel().getId();
            params.customer_code = model.getId_code();
            params.employee_id = AppProvider.getPreferences().getUserModel().getId();
            params.full_name = model.getFull_name();
            params.email = model.getEmail();
            params.address = model.getAddress();
            params.phone_number = model.getPhone_number();
            params.level_id = model.getLevel_id();

            AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<CustomerModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")){
                        view.confirmDialog();
                    }else if (body.getSuccess().equals("false")){
                        Toast.makeText(activity, ""+body.getMessage(), Toast.LENGTH_SHORT).show();
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
    public void deleteCustomer(CustomerModel model) {
        showProgress();
        if (model != null) {
            CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
            params.type_manager = "delete_customer";
            params.id_customer = model.getId();
            params.customer_code = model.getId_code();
            params.employee_id = AppProvider.getPreferences().getUserModel().getId();
            AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<CustomerModel> body) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")){
                        Toast.makeText(activity, ""+body.getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (body.getSuccess().equals("false")){
                        Toast.makeText(activity, ""+body.getMessage(), Toast.LENGTH_SHORT).show();
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
}
