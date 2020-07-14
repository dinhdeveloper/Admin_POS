package qtc.project.pos.api.account.reset_password;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("customer_forgot_pw")
public class RequestResetPassword extends ApiRequest<RequestResetPassword.Service, BaseResponseModel, RequestResetPassword.ApiParams> {


    public RequestResetPassword() {
        super(RequestResetPassword.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel result) {

    }

    @Override
    protected Call<BaseResponseModel> call(ApiParams params) {
        params.detect = "customer_forgot_pw";
        return getService().call(params);
    }

    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel> call(@Body RequestResetPassword.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        String detect;
        public String new_password;
        public String phone_number;
    }
}
