package qtc.project.pos.fragment.report.thongkekho.tonkho_vs_doanhthu;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.report.thongke_tonkho_vs_doanhthu.TonKho_Vs_DoanhThuRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentFilterTomTatDoanhSo;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.DataChartModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.model.Stock_Income_Model;
import qtc.project.pos.model.TonKho_Vs_DoanhThuModel;
import qtc.project.pos.ui.views.fragment.report.thongkekho.tonkho_vs_doanhthu.FragmentTK_TonKho_VS_DoanhThuView;
import qtc.project.pos.ui.views.fragment.report.thongkekho.tonkho_vs_doanhthu.FragmentTK_TonKho_VS_DoanhThuViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkekho.tonkho_vs_doanhthu.FragmentTK_TonKho_VS_DoanhThuViewInterface;

public class FragmentTK_TonKho_VS_DoanhThu extends BaseFragment<FragmentTK_TonKho_VS_DoanhThuViewInterface, BaseParameters> implements FragmentTK_TonKho_VS_DoanhThuViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentTK_TonKho_VS_DoanhThuViewInterface getViewInstance() {
        return new FragmentTK_TonKho_VS_DoanhThuView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void goToFilterDate(int status) {
        if (activity != null)
            activity.addFragment(new FragmentFilterTomTatDoanhSo().newIntance(status), true, null);
    }

    @Override
    public void searchDataToDate(String date_start, String date_end) {
        showProgress();
        if (date_start != null && date_end != null) {
            TonKho_Vs_DoanhThuRequest.ApiParams params = new TonKho_Vs_DoanhThuRequest.ApiParams();
            params.type_manager = "stock_income_report";
            params.date_start = date_start + "-01";
            params.date_end = date_end + "-31";

            AppProvider.getApiManagement().call(TonKho_Vs_DoanhThuRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<TonKho_Vs_DoanhThuModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<TonKho_Vs_DoanhThuModel> body) {
                    if (body != null) {
                        dismissProgress();
                        ArrayList<TonKho_Vs_DoanhThuModel> list = new ArrayList<>();
                        list.addAll(Arrays.asList(body.getData()));
                        view.mappingDateToView(list);
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                    Log.e("onError", error.message);
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                    Log.e("onFail", error.name());
                }
            });
        }
    }


    public void filterDataDate(String nam, String thang, int ngay) {
        if (view != null)
            view.sentDateToView(nam, thang, ngay);
    }
}
