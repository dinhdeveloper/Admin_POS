package qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.thongke;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.report.tomtatdoanhso.ThongKeRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentFilterTomTatDoanhSo;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.tongdoanhthu.FragmentTongDoanhThu;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.DataChartModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.model.TongDoanhThuModel;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.thongke.FragmentThongKeView;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.thongke.FragmentThongKeViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.thongke.FragmentThongKeViewInterface;

public class FragmentThongKe extends BaseFragment<FragmentThongKeViewInterface, BaseParameters> implements FragmentThongKeViewCallback {


    HomeActivity activity;
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentThongKeViewInterface getViewInstance() {
        return new FragmentThongKeView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void goToChooseYear() {
        if (activity!=null)
            activity.addFragment(new FragmentFilterTomTatDoanhSo(),true,null);
    }

    @Override
    public void goToTongDoanhThu() {
        if (activity!=null)
            activity.replaceFragment(new FragmentTongDoanhThu(),true,null);
    }

    @Override
    public void onBackProgress() {
        if (activity!=null)
            activity.checkBack();
    }

    @Override
    public void getDataToYear(String nam) {
        showProgress();
        if (nam!=null){
            ThongKeRequest.ApiParams params = new ThongKeRequest.ApiParams();
            params.type_manager = "income_manager";
            params.date_option = nam;

            AppProvider.getApiManagement().call(ThongKeRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<TongDoanhThuModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<TongDoanhThuModel> body) {
                    dismissProgress();
                    ArrayList<TongDoanhThuModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.mappingYear(list);
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

    public void filterDataYear(String nam) {
        if (nam!=null){
            view.sentYearToView(nam);
        }
    }
}
