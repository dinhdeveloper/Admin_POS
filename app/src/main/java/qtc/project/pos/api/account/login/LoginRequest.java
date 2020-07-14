package qtc.project.pos.api.account.login;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Objects;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.model.UserResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("employee_login")
public class LoginRequest extends ApiRequest<LoginRequest.Service, BaseResponseModel<EmployeeModel>, LoginRequest.ApiParams> {

    public LoginRequest() {
        super(LoginRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<EmployeeModel> result) {
        if (result != null && !TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {

            AppProvider.getPreferences().saveStatusLogin(true);
        }
    }

    @Override
    protected Call<BaseResponseModel<EmployeeModel>> call(ApiParams params) {
        params.detect = "employee_login";
        return getService().call(params);
    }

    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<EmployeeModel>> call(@Body LoginRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String id_code;
        public String password;

    }
}
