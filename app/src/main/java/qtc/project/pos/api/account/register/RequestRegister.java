package qtc.project.pos.api.account.register;

import javax.annotation.Nullable;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.UserResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("customer_register")
public class RequestRegister extends ApiRequest<RequestRegister.Service, BaseResponseModel<UserResponseModel>, RequestRegister.ApiParams> {

    public RequestRegister() {
        super(RequestRegister.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel result) {

    }

    @Override
    protected Call<BaseResponseModel<UserResponseModel>> call(ApiParams params) {
        params.detect = "customer_register";
        return getService().call(params);
    }

    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<UserResponseModel>> call(@Body RequestRegister.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        String detect;
        public String phone_number;
        public String password;
        public String last_name;
        public String first_name;
        public String address_personal;

        @Nullable
        public String email;

        @Nullable
        public String user_social_id;

        @Nullable
        public String type_login;

        @Nullable
        public String img_social_link;




    }
}
