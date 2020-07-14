package qtc.project.pos.fragment.employee;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.employee.EmployeeRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.model.LevelEmployeeModel;
import qtc.project.pos.ui.views.fragment.employee.detail.FragmentEmployeeDetailView;
import qtc.project.pos.ui.views.fragment.employee.detail.FragmentEmployeeDetailViewCallback;
import qtc.project.pos.ui.views.fragment.employee.detail.FragmentEmployeeDetailViewInterface;

public class FragmentEmployeeDetail extends BaseFragment<FragmentEmployeeDetailViewInterface, BaseParameters> implements FragmentEmployeeDetailViewCallback {
    HomeActivity activity;

    public static FragmentEmployeeDetail newInstance(EmployeeModel model) {
        FragmentEmployeeDetail detail = new FragmentEmployeeDetail();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", model);
        detail.setArguments(bundle);

        return detail;
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
            EmployeeModel model = (EmployeeModel) extras.get("model");
            view.sentDataToView(model);
        }
    }

    @Override
    protected FragmentEmployeeDetailViewInterface getViewInstance() {
        return new FragmentEmployeeDetailView();
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
    public void callLevelEmployee() {
        ArrayList<LevelEmployeeModel> arrayList = new ArrayList<>();
        LevelEmployeeModel model = new LevelEmployeeModel();
        model.setId("1");
        model.setName("Nhân Viên");
        arrayList.add(model);

        LevelEmployeeModel model2 = new LevelEmployeeModel();
        model2.setId("2");
        model2.setName("Admin");
        arrayList.add(model2);

        view.sendLevelEmployee(arrayList);
    }

    @Override
    public void updateEmployee(EmployeeModel employeeModel) {
        if (employeeModel != null) {
            showProgress();
            EmployeeRequest.ApiParams params = new EmployeeRequest.ApiParams();
            params.type_manager = "update_employee";
            params.id_code = employeeModel.getId_code();
            params.id_employee = AppProvider.getPreferences().getUserModel().getId();
            params.full_name = employeeModel.getFull_name();
            params.email = employeeModel.getEmail();
            params.phone_number = employeeModel.getPhone_number();
            params.address = employeeModel.getAddress();
            params.birthday = employeeModel.getBirthday();
            params.employee_level = employeeModel.getLevel();
            params.status = employeeModel.getStatus();

            AppProvider.getApiManagement().call(EmployeeRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<EmployeeModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<EmployeeModel> body) {
                    dismissProgress();
                    ;
                    if (body.getSuccess().equals("true")) {
                        view.showDiaLogUpdate();
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
    public void deleteEmployee(EmployeeModel employeeModel) {
        if (employeeModel != null) {
            showProgress();
            EmployeeRequest.ApiParams params = new EmployeeRequest.ApiParams();
            params.type_manager = "delete_employee";
            params.id_employee = AppProvider.getPreferences().getUserModel().getId();

            AppProvider.getApiManagement().call(EmployeeRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<EmployeeModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<EmployeeModel> body) {
                    dismissProgress();
                    ;
                    if (body.getSuccess().equals("true")) {
                        view.showDiaLogDelete();
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
    public void reSetPass(String id,String newPass) {
        if (id!=null){
            showProgress();
            EmployeeRequest.ApiParams params = new EmployeeRequest.ApiParams();
            params.type_manager = "update_employee";
            params.id_code = id;
            params.password_reset = newPass;

            AppProvider.getApiManagement().call(EmployeeRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<EmployeeModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<EmployeeModel> body) {
                    dismissProgress();
                    ;
                    if (body.getSuccess().equals("true")) {
                        view.showDiaLogUpdate();
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
}
