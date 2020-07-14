package qtc.project.pos.api.product.packageproduct;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageReturnModel;
import qtc.project.pos.model.ProductListModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("list_package_return")
public class PackageReturnRequest  extends ApiRequest<PackageReturnRequest.Service, BaseResponseModel<PackageReturnModel>, PackageReturnRequest.ApiParams> {

    public PackageReturnRequest() {
        super(PackageReturnRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<PackageReturnModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<PackageReturnModel>> call(PackageReturnRequest.ApiParams params) {
        params.detect = "package_product_manager";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<PackageReturnModel>> call(@Body PackageReturnRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String type_manager;
        public String id_code;
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
