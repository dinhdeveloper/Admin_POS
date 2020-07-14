package qtc.project.pos.fragment.report.thongkekho.xuatnhapkho;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.report.xuatnhapkho.BaoCaoXuatNhapKhoRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.report.thongkekho.xuatnhapkho.detail.FragmentDetailXuatNhapKho;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ReportXuatNhapKhoModel;
import qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.FragmentBaoCaoXuatNhapKhoView;
import qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.FragmentBaoCaoXuatNhapKhoViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.FragmentBaoCaoXuatNhapKhoViewInterface;

public class FragmentBaoCaoXuatNhapKho extends BaseFragment<FragmentBaoCaoXuatNhapKhoViewInterface, BaseParameters> implements FragmentBaoCaoXuatNhapKhoViewCallback {

    HomeActivity activity;

    public static FragmentBaoCaoXuatNhapKho newIntance(String thang, String nam) {
        FragmentBaoCaoXuatNhapKho frag = new FragmentBaoCaoXuatNhapKho();
        Bundle bundle = new Bundle();
        bundle.putString("thang", thang);
        bundle.putString("nam", nam);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        activity  = (HomeActivity)getActivity();
        view.init(activity,this);

        getDataToBundle();
    }

    private void getDataToBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            String thang = (String) extras.get("thang");
            String nam = (String) extras.get("nam");

            showProgress();
            BaoCaoXuatNhapKhoRequest.ApiParams params = new BaoCaoXuatNhapKhoRequest.ApiParams();
            params.type_manager = "stock_in_out";
            params.date_start = nam+"-"+thang+"-01";
            params.date_end = nam+"-"+thang+"-31";

            AppProvider.getApiManagement().call(BaoCaoXuatNhapKhoRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ReportXuatNhapKhoModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ReportXuatNhapKhoModel> body) {
                    dismissProgress();
                    ArrayList<ReportXuatNhapKhoModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.sendDataToView(list);
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

    @Override
    protected FragmentBaoCaoXuatNhapKhoViewInterface getViewInstance() {
        return new FragmentBaoCaoXuatNhapKhoView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity!=null)
            activity.checkBack();
    }

    @Override
    public void filterData() {
        activity.replaceFragment(new FragmentFilterXuatNhapKho(),true,null);
    }

    @Override
    public void goToDetailXuatNhapKho(ReportXuatNhapKhoModel model) {
        activity.addFragment(new FragmentDetailXuatNhapKho().newIntance(model),true,null);
    }
}
