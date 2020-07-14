package qtc.project.pos.api.report.tomtatdoanhso;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.TongDoanhThuModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
@ApiRequest.ApiName("date_option")
public class ThongKeRequest  extends ApiRequest<ThongKeRequest.Service, BaseResponseModel<TongDoanhThuModel>, ThongKeRequest.ApiParams> {

    public ThongKeRequest() {
        super(ThongKeRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<TongDoanhThuModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<TongDoanhThuModel>> call(ThongKeRequest.ApiParams params) {
        params.detect = "statictis_report_manager";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<TongDoanhThuModel>> call(@Body ThongKeRequest.ApiParams params);
    }

    public static class ApiParams extends BaseApiParams {
        public String detect;
        public String date_option;
        public String product;
        public String type_manager;
        public String date_start;
        public String date_end;
        public String customer_id;
        public String product_id;
    }
}