package qtc.project.pos.fragment.product.doitra;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.packageproduct.PackageReturnRequest;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.api.report.xuatnhapkho.BaoCaoXuatNhapKhoRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.product.quanlylohang.FragmentChiTietLoHang;
import qtc.project.pos.fragment.report.thongkekho.xuatnhapkho.FragmentBaoCaoXuatNhapKho;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageReturnModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.ReportXuatNhapKhoModel;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaView;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaViewCallback;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaViewInterface;

public class FragmentDoiTraHangHoa extends BaseFragment<FragmentDoiTraHangHoaViewInterface, BaseParameters> implements FragmentDoiTraHangHoaViewCallback {

    HomeActivity activity;

    public static FragmentDoiTraHangHoa newIntance(String thang, String nam) {
        FragmentDoiTraHangHoa frag = new FragmentDoiTraHangHoa();
        Bundle bundle = new Bundle();
        bundle.putString("thang", thang);
        bundle.putString("nam", nam);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        // getDataToBundle();
    }

    private void getDataToBundle() {
        Bundle extras = getArguments();
        if (extras != null) {
            String thang = (String) extras.get("thang");
            String nam = (String) extras.get("nam");

            showProgress();
            PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
            params.date_start = nam + "-" + thang + "-01";
            params.date_end = nam + "-" + thang + "-31";

            params.type_manager = "list_package_return";
            AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                    dismissProgress();
                    if (body != null) {
                        ArrayList<PackageReturnModel> list = new ArrayList<>();
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

    @Override
    protected FragmentDoiTraHangHoaViewInterface getViewInstance() {
        return new FragmentDoiTraHangHoaView();
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
    public void getDataDoiTraHangHoa() {
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.type_manager = "list_package_return";
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                dismissProgress();
                ArrayList<PackageReturnModel> list = new ArrayList<>();
                list.addAll(Arrays.asList(body.getData()));
                view.mappingRecyclerView(list);
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
    public void sentDataToDetailDTHH(PackageReturnModel model) {
        activity.replaceFragment(FragmentChiTietDonTraHangHoa.newIntance(model), true, null);
    }

    @Override
    public void getAllData() {
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.type_manager = "list_package_return";
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                if (body != null) {
                    dismissProgress();
                    ArrayList<PackageReturnModel> list = new ArrayList<>();
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
    public void searchData(String search) {
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.type_manager = "list_package_return";
        params.filter_product_return = search;
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                dismissProgress();
                ArrayList<PackageReturnModel> list = new ArrayList<>();
                list.addAll(Arrays.asList(body.getData()));
                view.mappingRecyclerView(list);
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

    public void filterDataDate(String thang, String nam) {
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.date_start = nam + "-" + thang + "-01";
        params.date_end = nam + "-" + thang + "-31";

        params.type_manager = "list_package_return";
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> body) {
                dismissProgress();
                if (body != null) {
                    ArrayList<PackageReturnModel> list = new ArrayList<>();
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
