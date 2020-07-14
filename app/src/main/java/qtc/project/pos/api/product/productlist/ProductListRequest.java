package qtc.project.pos.api.product.productlist;


import android.text.TextUtils;

import java.io.File;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import qtc.project.pos.api.product.productcategory.ProductCategoryRequest;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.model.ProductListModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("product_manager")
public class ProductListRequest extends ApiRequest<ProductListRequest.Service, BaseResponseModel<ProductListModel>, ProductListRequest.ApiParams> {

    public ProductListRequest() {
        super(ProductListRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<ProductListModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<ProductListModel>> call(ProductListRequest.ApiParams params) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (!TextUtils.isEmpty(params.image)) {
            File fileAvatar = new File(params.image);
            if (fileAvatar.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), fileAvatar);
                builder.addFormDataPart("image", fileAvatar.getName(), fileBody);
            }
        }
        if (!TextUtils.isEmpty((params.id_product))) {
            builder.addFormDataPart("id_product", params.id_product);
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
        if (!TextUtils.isEmpty((params.barcode))) {
            builder.addFormDataPart("barcode", params.barcode);
        }
        if (!TextUtils.isEmpty((params.category_id))) {
            builder.addFormDataPart("category_id", params.category_id);
        }
        if (!TextUtils.isEmpty((params.quantity_safetystock))) {
            builder.addFormDataPart("quantity_safetystock", params.quantity_safetystock);
        }
        if (!TextUtils.isEmpty((params.qr_code))) {
            builder.addFormDataPart("qr_code", params.qr_code);
        }
        if (!TextUtils.isEmpty((params.product))) {
            builder.addFormDataPart("product", params.product);
        }

        params.detect = "product_manager";
        builder.addFormDataPart("detect", params.detect)
                .setType(MultipartBody.FORM);

        RequestBody requestBody = builder.build();
        return getService().call(requestBody);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<ProductListModel>> call(@Body RequestBody params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String type_manager;
        public String id_product;
        public String id_code;
        public String name;
        public String description;
        public String barcode;
        public String category_id;
        public String quantity_safetystock;
        public String qr_code;
        public String image;
        public String product;
    }
}
