package qtc.project.pos.fragment.customer;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.customer.CustomerRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.history.FragmentHistoryOrderCustomer;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.customer.FragmentCustomerView;
import qtc.project.pos.ui.views.fragment.customer.FragmentCustomerViewCallback;
import qtc.project.pos.ui.views.fragment.customer.FragmentCustomerViewInterface;
import qtc.project.pos.ui.views.fragment.product.FragmentProductViewCallback;
import qtc.project.pos.ui.views.fragment.product.FragmentProductViewInterface;

public class FragmentCustomer extends BaseFragment<FragmentCustomerViewInterface, BaseParameters> implements FragmentCustomerViewCallback {
    HomeActivity activity;
    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentCustomerViewInterface getViewInstance() {
        return new FragmentCustomerView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackprogress() {
        if (activity!=null)
            activity.checkBack();
    }

    @Override
    public void getDataCustomer() {
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
                    view.mappingRecyclerView(list);
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
    public void getHistoryOrderCustomer(CustomerModel model) {
        activity.addFragment(new FragmentHistoryOrderCustomer().newIntance(model),true,null);
    }

    @Override
    public void createCustomer() {
        activity.replaceFragment(new FragmentCreateCustomer(),true,null);
    }

    @Override
    public void getCustomerDetail(CustomerModel model) {
        activity.replaceFragment(new FragmentCustomerDetail().newIntance(model),true,null);
    }

}
