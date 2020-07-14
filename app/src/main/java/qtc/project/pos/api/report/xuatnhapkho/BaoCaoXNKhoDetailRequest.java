package qtc.project.pos.api.report.xuatnhapkho;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.BaseApiParams;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductListModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

@ApiRequest.ApiName("report_manager")
public class BaoCaoXNKhoDetailRequest  extends ApiRequest<BaoCaoXNKhoDetailRequest.Service, BaseResponseModel<ProductListModel>, BaoCaoXNKhoDetailRequest.ApiParams> {

    public BaoCaoXNKhoDetailRequest() {
        super(BaoCaoXNKhoDetailRequest.Service.class, RequestOrigin.NONE, Consts.HOST_API, Consts.MODE, Consts.TRUST_CERTIFICATE);
    }

    @Override
    protected void postAfterRequest(BaseResponseModel<ProductListModel> result) throws Exception {

    }

    @Override
    protected Call<BaseResponseModel<ProductListModel>> call(BaoCaoXNKhoDetailRequest.ApiParams params) {
        params.detect = "statictis_report_manager";
        return getService().call(params);
    }


    interface Service {
        @Headers(Consts.HEADES)
        @POST(Consts.REST_ENDPOINT)
        Call<BaseResponseModel<ProductListModel>> call(@Body BaoCaoXNKhoDetailRequest.ApiParams params);
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
