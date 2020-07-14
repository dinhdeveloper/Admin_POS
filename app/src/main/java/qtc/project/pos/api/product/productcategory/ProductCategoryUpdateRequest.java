package qtc.project.pos.api.product.productcategory;

import android.text.TextUtils;

import java.io.File;

import javax.annotation.Nullable;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("product_category_update_manager")
public class ProductCategoryUpdateRequest extends ApiRequest<ProductCategoryUpdateRequest.Service, BaseResponseModel<ProductCategoryModel>, ProductCategoryUpdateRequest.ApiParams> {

    public ProductCategoryUpdateRequest() {
        super(ProductCategoryUpdateRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<ProductCategoryModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<ProductCategoryModel>> call(ApiParams params) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (!TextUtils.isEmpty(params.image)) {
            File fileAvatar = new File(params.image);
            if (fileAvatar.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), fileAvatar);
                builder.addFormDataPart("image", fileAvatar.getName(), fileBody);
            }
        }
        if (!TextUtils.isEmpty((params.id_category))) {
            builder.addFormDataPart("id_category", params.id_category);
        }
        if (!TextUtils.isEmpty((params.name))) {
            builder.addFormDataPart("name", params.name);
        }
        if (!TextUtils.isEmpty((params.description))) {
            builder.addFormDataPart("description", params.description);
        }
        if (!TextUtils.isEmpty((params.id_code))) {
            builder.addFormDataPart("id_code", params.id_code);
        }
        if (!TextUtils.isEmpty((params.type_manager))) {
            builder.addFormDataPart("type_manager", params.type_manager);
        }

        params.detect = "product_category_manager";
        builder.addFormDataPart("detect", params.detect)
                .setType(MultipartBody.FORM);

        RequestBody requestBody = builder.build();
        return getService().call(requestBody);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<ProductCategoryModel>> call(@Body RequestBody params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        @Nullable
        public String type_manager;
        @Nullable
        public String name;
        @Nullable
        public String id_category;
        @Nullable
        public String id_code;
        @Nullable
        public String image;
        @Nullable
        public String description;
    }
}
