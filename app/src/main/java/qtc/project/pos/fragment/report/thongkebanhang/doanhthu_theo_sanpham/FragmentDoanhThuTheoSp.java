package qtc.project.pos.fragment.report.thongkebanhang.doanhthu_theo_sanpham;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.api.report.tomtatdoanhso.ThongKeRequest;
import qtc.project.pos.api.report.tomtatdoanhso.TomTatDoanhSoRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentFilterTomTatDoanhSo;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.TongDoanhThuModel;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_sanpham.FragmentDoanhThuTheoSpView;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_sanpham.FragmentDoanhThuTheoSpViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_sanpham.FragmentDoanhThuTheoSpViewInterface;

public class FragmentDoanhThuTheoSp extends BaseFragment<FragmentDoanhThuTheoSpViewInterface, BaseParameters> implements FragmentDoanhThuTheoSpViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentDoanhThuTheoSpViewInterface getViewInstance() {
        return new FragmentDoanhThuTheoSpView();
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
    public void searchProduct(String search) {
        showProgress();
        if (search!=null){
            ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
            params.product = search;
            params.type_manager = "list_product";
            AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    if (body != null) {
                        dismissProgress();
                        ArrayList<ProductListModel> list = new ArrayList<>();
                        list.addAll(Arrays.asList(body.getData()));
                        view.mappingListRecyclerview(list);
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

    @Override
    public void goToFragmentFilter(int status) {
        activity.addFragment(new FragmentFilterTomTatDoanhSo().newIntance(status),true,null);
    }

    @Override
    public void chooseYear() {
        if (activity!=null)
            activity.addFragment(new FragmentFilterTomTatDoanhSo(),true,null);
    }

    @Override
    public void getDataFilterTime(String date_start, String date_end, String id_product) {
        showProgress();
        TomTatDoanhSoRequest.ApiParams params = new TomTatDoanhSoRequest.ApiParams();
        params.type_manager = "income_manager";
        params.product_id = id_product;
        params.date_start = date_start + "-01";
        params.date_end = date_end + "-31";

        AppProvider.getApiManagement().call(TomTatDoanhSoRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<TongDoanhThuModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<TongDoanhThuModel> body) {
                dismissProgress();
                if (body != null) {
                    List<TongDoanhThuModel> models = new ArrayList<>();
                    models.addAll(Arrays.asList(body.getData()));
                    view.sentDataTongDoanhThu(models);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                Log.e("", error.message);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                Log.e("", error.name());
            }
        });
    }

    @Override
    public void getDataToYear(String nam, String id_product) {
        if (nam!=null && id_product!=null){
            showProgress();
            if (nam!=null){
                ThongKeRequest.ApiParams params = new ThongKeRequest.ApiParams();
                params.type_manager = "income_manager";
                params.date_option = nam;
                params.product_id = id_product;

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
    }

    public void filterDataTheoThang(String nam, String thang, int ngay) {
        if (view!=null)
            view.mappingDateToView(nam,thang,ngay);
    }

    public void filterDataYear(String nam) {
        if (nam!=null){
            view.sentYearToView(nam);
        }
    }
}
