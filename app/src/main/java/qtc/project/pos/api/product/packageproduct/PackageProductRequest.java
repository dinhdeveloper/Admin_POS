package qtc.project.pos.api.product.packageproduct;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductListModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("package_product_manager")
public class PackageProductRequest  extends ApiRequest<PackageProductRequest.Service, BaseResponseModel<ProductListModel>, PackageProductRequest.ApiParams> {

    public PackageProductRequest() {
        super(PackageProductRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<ProductListModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<ProductListModel>> call(PackageProductRequest.ApiParams params) {
        params.detect = "package_product_manager";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<ProductListModel>> call(@Body PackageProductRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String type_manager;
        public String product_id;
        public String id_package;
        public String manufacturing_date;
        public String expiry_date;
        public String import_date;
        public String import_price ;
        public String sale_price ;
        public String quantity_order ;
        public String quantity_storage ;
        public String description ;
        public String employee_id ;
        public String manufacturer_id ;
        public String quantity_return ;
        public String return_date ;
        public String product_detail_id ;
        public String filter_product_return ;
        public String status ;
        public String id_product_return ;
    }
}
