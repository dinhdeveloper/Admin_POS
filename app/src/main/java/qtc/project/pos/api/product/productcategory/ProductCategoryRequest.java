package qtc.project.pos.api.product.productcategory;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductCategoryModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("product_category_manager")
public class ProductCategoryRequest extends ApiRequest<ProductCategoryRequest.Service, BaseResponseModel<ProductCategoryModel>, ProductCategoryRequest.ApiParams> {

    public ProductCategoryRequest() {
        super(ProductCategoryRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<ProductCategoryModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<ProductCategoryModel>> call(ProductCategoryRequest.ApiParams params) {
        params.detect = "product_category_manager";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<ProductCategoryModel>> call(@Body ProductCategoryRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String name;
        public String id_category;
        public String id_code;
        public String type_manager;
        public String image;
        public String description;
        public String product;
    }
}
