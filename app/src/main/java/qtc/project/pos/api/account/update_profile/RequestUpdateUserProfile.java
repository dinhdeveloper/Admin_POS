package qtc.project.pos.api.account.update_profile;

import android.text.TextUtils;

import java.io.File;
import java.util.Objects;

import javax.annotation.Nullable;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.UserResponseModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("update_profile")
public class RequestUpdateUserProfile extends ApiRequest<RequestUpdateUserProfile.Service, BaseResponseModel<UserResponseModel>, RequestUpdateUserProfile.ApiParams> {

    public RequestUpdateUserProfile() {
        super(RequestUpdateUserProfile.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }


    @Override
    protected void postAfterRequest(BaseResponseModel result) {

    }

    @Override
    protected Call<BaseResponseModel<UserResponseModel>> call(ApiParams params) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (!TextUtils.isEmpty(params.avatar)) {
            File fileAvata = new File(params.avatar);
            if (fileAvata.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), fileAvata);
                builder.addFormDataPart("img_default", fileAvata.getName(), fileBody);
            }
        }
        if (!TextUtils.isEmpty((params.id))) {
            builder.addFormDataPart("id", params.id);
        }

        if (!TextUtils.isEmpty((params.last_name))) {
            builder.addFormDataPart("last_name", Objects.requireNonNull(params.last_name));
        }

        if (!TextUtils.isEmpty((params.first_name))) {
            builder.addFormDataPart("first_name", Objects.requireNonNull(params.first_name));
        }
        if (!TextUtils.isEmpty((params.address_personal))) {
            builder.addFormDataPart("address_personal", Objects.requireNonNull(params.address_personal));
        }
        if (!TextUtils.isEmpty((params.password))) {
            if (!TextUtils.isEmpty((params.type_manager))) {
                builder.addFormDataPart("new_password", params.password);
            } else
                builder.addFormDataPart("password", Objects.requireNonNull(params.password));
        }

        if (!TextUtils.isEmpty((params.old_password))) {
            builder.addFormDataPart("old_password", Objects.requireNonNull(params.old_password));
        }

        if (!TextUtils.isEmpty((params.email))) {
            builder.addFormDataPart("email", Objects.requireNonNull(params.email));
        }

        if (!TextUtils.isEmpty((params.birthday))) {
            builder.addFormDataPart("birthday", Objects.requireNonNull(params.birthday));
        }

        if (!TextUtils.isEmpty((params.id_company))) {
            builder.addFormDataPart("id_company", Objects.requireNonNull(params.id_company));
        }

        if (!TextUtils.isEmpty((params.id_industrial))) {
            builder.addFormDataPart("id_industrial", Objects.requireNonNull(params.id_industrial));
        }
        if (!TextUtils.isEmpty((params.sex))) {
            builder.addFormDataPart("sex", Objects.requireNonNull(params.sex));
        }

        if (!TextUtils.isEmpty((params.phone_contact))) {
            builder.addFormDataPart("phone_contact", Objects.requireNonNull(params.phone_contact));
        }

        if (!TextUtils.isEmpty((params.id_user))) {
            builder.addFormDataPart("id_user", params.id_user);
        }

        if (!TextUtils.isEmpty((params.type_manager))) {
            builder.addFormDataPart("type_manager", params.type_manager);
        }

        builder.addFormDataPart("detect", params.detect)
                .setType(MultipartBody.FORM);


        RequestBody requestBody = builder.build();
        return getService().call(requestBody);
    }

    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<UserResponseModel>> call(@Body RequestBody data);

    }

    public static class ApiParams extends BaseApiParams {

        public String detect;

        public String id;

        @Nullable
        public String last_name;

        @Nullable
        public String first_name;

        @Nullable
        public String address_personal;

        @Nullable
        public String avatar;

        @Nullable
        public String password;

        @Nullable
        public String old_password;

        @Nullable
        public String email;

        @Nullable
        public String birthday;

        @Nullable
        public String id_industrial;

        @Nullable
        public String id_company;

        @Nullable
        public String sex;

        @Nullable
        public String phone_contact;

        @Nullable
        public String type_manager;

        @Nullable
        public String id_user;


    }
}
