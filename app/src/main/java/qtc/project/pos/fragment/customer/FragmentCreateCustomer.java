package qtc.project.pos.fragment.customer;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.customer.CustomerRequest;
import qtc.project.pos.api.levelcustomer.LevelCustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.ui.views.fragment.customer.create.FragmentCreateCustomerView;
import qtc.project.pos.ui.views.fragment.customer.create.FragmentCreateCustomerViewCallback;
import qtc.project.pos.ui.views.fragment.customer.create.FragmentCreateCustomerViewInterface;

public class FragmentCreateCustomer extends BaseFragment<FragmentCreateCustomerViewInterface, BaseParameters> implements FragmentCreateCustomerViewCallback {

    HomeActivity activity;
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentCreateCustomerViewInterface getViewInstance() {
        return new FragmentCreateCustomerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity!=null)
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
    public void createCustomer(CustomerModel customerModel) {
        showProgress();
        CustomerRequest.ApiParams params = new CustomerRequest.ApiParams();
        params.type_manager = "create_customer";
        params.employee_id = AppProvider.getPreferences().getUserModel().getId();
        params.full_name = customerModel.getFull_name();
        params.customer_code = customerModel.getId_code();
        params.email = customerModel.getEmail();
        params.phone_number = customerModel.getPhone_number();
        params.address = customerModel.getAddress();

        AppProvider.getApiManagement().call(CustomerRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<CustomerModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<CustomerModel> body) {
                if (body != null) {
                    dismissProgress();
                    if (body.getSuccess().equals("true")){
                        view.showAlert();
                    }else if (body.getSuccess().equals("false")){
                        Toast.makeText(activity, ""+body.getMessage(), Toast.LENGTH_SHORT).show();
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
}
