package qtc.project.pos.fragment.report.thongkebanhang.sanpham_banchay;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.report.sanpham_banchay.TopProductRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentFilterTomTatDoanhSo;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.model.TopProductModel;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay.FragmentSanPhamBanChayView;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay.FragmentSanPhamBanChayViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay.FragmentSanPhamBanChayViewInterface;

public class FragmentSanPhamBanChay extends BaseFragment<FragmentSanPhamBanChayViewInterface, BaseParameters> implements FragmentSanPhamBanChayViewCallback {
    HomeActivity activity;

    public static FragmentSanPhamBanChay newIntance(String thang, String nam) {
        FragmentSanPhamBanChay frag = new FragmentSanPhamBanChay();
        Bundle bundle = new Bundle();
        bundle.putString("thang", thang);
        bundle.putString("nam", nam);
        frag.setArguments(bundle);
        return frag;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        getTopSanPhamBanChay();
        getDataBundle();
    }

    private void getDataBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            String nam = (String) extras.get("nam");
            String thang = (String) extras.get("thang");

            showProgress();

            TopProductRequest.ApiParams params = new TopProductRequest.ApiParams();
            params.type_manager = "report_top_product";
            params.date_start = nam + "-" + thang + "-01";
            params.date_end = nam + "-" + thang + "-31";

            AppProvider.getApiManagement().call(TopProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<TopProductModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<TopProductModel> body) {
                    if (body != null) {
                        dismissProgress();
                        ArrayList<TopProductModel> list = new ArrayList<>();
                        list.addAll(Arrays.asList(body.getData()));
                        view.mappingRecyclerView(list);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTopSanPhamBanChay() {
        showProgress();
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();

        TopProductRequest.ApiParams params = new TopProductRequest.ApiParams();
        params.type_manager = "report_top_product";
        params.date_start = year + "-" + month + "-01";
        params.date_end = year + "-" + month + "-31";

        AppProvider.getApiManagement().call(TopProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<TopProductModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<TopProductModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<TopProductModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.mappingRecyclerView(list);
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

    @Override
    protected FragmentSanPhamBanChayViewInterface getViewInstance() {
        return new FragmentSanPhamBanChayView();
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
    public void goToChooseDate() {
        activity.addFragment(new FragmentFilterSanPhamBanChay(), true, null);
    }
}
