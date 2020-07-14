package qtc.project.pos.api.supplier;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.api.customer.CustomerRequest;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.SupplierModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("supplier_manager")
public class SupplierRequest  extends ApiRequest<SupplierRequest.Service, BaseResponseModel<SupplierModel>, SupplierRequest.ApiParams> {

    public SupplierRequest() {
        super(SupplierRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<SupplierModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<SupplierModel>> call(SupplierRequest.ApiParams params) {
        params.detect = "supplier_manager";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<SupplierModel>> call(@Body SupplierRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String filter;
        public String id_supplier;
        public String type_manager;

        //update
        public String id_code;
        public String name;
        public String email;
        public String phone_number;
        public String address;
    }
}