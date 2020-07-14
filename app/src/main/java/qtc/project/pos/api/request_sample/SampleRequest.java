package qtc.project.pos.api.request_sample;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.model.BaseResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by laixuantam on 7/18/17.
 */
@ApiRequest.ApiName("SampleRequest")
public class SampleRequest extends ApiRequest<SampleRequest.Service, BaseResponseModel, SampleRequest.ApiParams> {
    public SampleRequest() {

//        String hostAPI = "https://httpbin.org/post";

        super(SampleRequest.Service.class, getCustomAdapter("https://httpbin.org/", "production", true), RequestOrigin.NONE);

//        super(SampleRequest.Service.class, RequestOrigin.NONE, "hostAPI/", Environments.MODE, Environments.ENABLE_LOG, Environments.TRUSTED_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel> call(ApiParams params) {
        params.param = "param";
        return getService().call(params);
    }

    public static class ApiParams extends BaseApiParams {

        public String param;

    }

    public interface Service {
        @Headers(CONTENT_TYPE_JSON)
        @POST("post")
        Call<BaseResponseModel> call(@Body SampleRequest.ApiParams params);
    }
}

//todo: cách sử dụng SampleRequest
/*
 - dữ liệu trả về được định nghĩa trong class BaseResponseModel (json data được map với các key mình định nghĩa sẵn)


SampleRequest.ApiParams params = new SampleRequest.ApiParams();

        AppProvider.getApiManagement().call(SampleRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {

            @Override
            public void onSuccess(BaseResponseModel result) {

                MyLog.e("RequestSample", "result: " + result);

            }

            @Override
            public void onError(ErrorApiResponse error) {
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
            }
        });
 */
