package qtc.project.pos.api.report.thongke_tonkho_vs_doanhthu;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.TonKho_Vs_DoanhThuModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("stock_income_report")
public class TonKho_Vs_DoanhThuRequest  extends ApiRequest<TonKho_Vs_DoanhThuRequest.Service, BaseResponseModel<TonKho_Vs_DoanhThuModel>, TonKho_Vs_DoanhThuRequest.ApiParams> {

    public TonKho_Vs_DoanhThuRequest() {
        super(TonKho_Vs_DoanhThuRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<TonKho_Vs_DoanhThuModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<TonKho_Vs_DoanhThuModel>> call(TonKho_Vs_DoanhThuRequest.ApiParams params) {
        params.detect = "statictis_report_manager";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<TonKho_Vs_DoanhThuModel>> call(@Body TonKho_Vs_DoanhThuRequest.ApiParams params);
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