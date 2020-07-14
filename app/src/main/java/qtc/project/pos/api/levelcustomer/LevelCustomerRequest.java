package qtc.project.pos.api.levelcustomer;

import android.text.TextUtils;

import java.io.File;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.LevelCustomerModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("list_level")
public class LevelCustomerRequest extends ApiRequest<LevelCustomerRequest.Service, BaseResponseModel<LevelCustomerModel>, LevelCustomerRequest.ApiParams> {

    public LevelCustomerRequest() {
        super(LevelCustomerRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<LevelCustomerModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<LevelCustomerModel>> call(ApiParams params) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (!TextUtils.isEmpty(params.image)) {
            File fileAvatar = new File(params.image);
            if (fileAvatar.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), fileAvatar);
                builder.addFormDataPart("image", fileAvatar.getName(), fileBody);
            }
        }
        if (!TextUtils.isEmpty((params.id_level))) {
            builder.addFormDataPart("id_level", params.id_level);
        }
        if (!TextUtils.isEmpty((params.id_code))) {
            builder.addFormDataPart("id_code", params.id_code);
        }
        if (!TextUtils.isEmpty((params.name))) {
            builder.addFormDataPart("name", params.name);
        }
        if (!TextUtils.isEmpty((params.discount))) {
            builder.addFormDataPart("discount", params.discount);
        }
        if (!TextUtils.isEmpty((params.description))) {
            builder.addFormDataPart("description", params.description);
        }
        if (!TextUtils.isEmpty((params.type_manager))) {
            builder.addFormDataPart("type_manager", params.type_manager);
        }

        params.detect = "customer_level_manager";
        builder.addFormDataPart("detect", params.detect)
                .setType(MultipartBody.FORM);
        RequestBody requestBody = builder.build();
        return getService().call(requestBody);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<LevelCustomerModel>> call(@Body RequestBody params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String type_manager;
        public String id_level;
        public String id_code;
        public String name;
        public String discount;
        public String description;
        public String image;
    }
}
