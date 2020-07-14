package qtc.project.pos.api.history;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.api.customer.CustomerRequest;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.OrderCustomerModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("list_order")
public class HistoryOrderCustomerRequest  extends ApiRequest<HistoryOrderCustomerRequest.Service, BaseResponseModel<OrderCustomerModel>, HistoryOrderCustomerRequest.ApiParams> {
    public HistoryOrderCustomerRequest() {
        super(HistoryOrderCustomerRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<OrderCustomerModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<OrderCustomerModel>> call(HistoryOrderCustomerRequest.ApiParams params) {
        params.detect = "list_order";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<OrderCustomerModel>> call(@Body HistoryOrderCustomerRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String type_manager;
        public String filter;
        public String employee_id;
        public String date_begin;
        public String date_end;
    }
}
