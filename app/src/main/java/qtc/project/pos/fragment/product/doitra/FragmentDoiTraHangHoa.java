package qtc.project.pos.fragment.product.doitra;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.packageproduct.PackageReturnRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageReturnModel;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewCallback;
import qtc.project.pos.ui.views.fragment.list_base.FragmentListBaseViewInterface;
import qtc.project.pos.ui.views.fragment.product.doitra.FragmentDoiTraHangHoaView;

public class FragmentDoiTraHangHoa extends BaseFragment<FragmentListBaseViewInterface, BaseParameters> implements FragmentListBaseViewCallback {

    HomeActivity activity;
    boolean checked;
    int page = 1;
    private int totalPage = 0;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);

        getDataToListDoiTra();
    }

    private void getDataToListDoiTra() {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
            //view.setDataList(null);
            return;
        }
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.type_manager = "list_package_return";
        params.page = String.valueOf(page);
        params.limit = "20";
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.setDataList(result);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }
        });
    }

    @Override
    protected FragmentListBaseViewInterface getViewInstance() {
        return new FragmentDoiTraHangHoaView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onClickBackHeader() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void refreshLoadingList() {
        page = 1;
        totalPage = 0;
        view.resetListData();
        getDataToListDoiTra();
    }

    @Override
    public void onRequestLoadMoreList() {
        ++page;

        if (totalPage > 0 && page <= totalPage) {
            getDataToListDoiTra();
        } else {
            view.setNoMoreLoading();
            showToast(getString(R.string.error_loadmore));
        }
    }

    @Override
    public void onRequestSearchWithFilter(String key) {
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.type_manager = "list_package_return";
        if (!TextUtils.isEmpty(key)) {
            params.filter_product_return = key;
        }
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> result) {
                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.clearListData();
                    view.setDataList(result);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
                }
            }

            @Override
            public void onError(ErrorApiResponse error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }

            @Override
            public void onFail(ApiRequest.RequestError error) {
                dismissProgress();
                showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
            }
        });
    }

    @Override
    public void onItemListSelected(OptionModel item) {
        PackageReturnModel model = (PackageReturnModel) item.getDtaCustom();
        if (activity != null) {
            activity.changeToFragmentChiTietDonTraHangHoa(model);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.hideRootView();
                }
            }, 100);
        }
    }

    @Override
    public void onClickAddItem() {

    }

    @Override
    public void onDeleteItemSelected(OptionModel item) {

    }

    @Override
    public void onClickFilter() {
        if (activity != null) {
            activity.changToFragmentFilterDoiTraHangHoa();
        }
    }

    public void filterDataDate(String thang, String nam) {
        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
            showAlert(getContext().getResources().getString(R.string.error_internet_connection), KAlertDialog.ERROR_TYPE);
            view.setDataList(null);
            return;
        }
        showProgress();
        PackageReturnRequest.ApiParams params = new PackageReturnRequest.ApiParams();
        params.date_start = nam + "-" + thang + "-01";
        params.date_end = nam + "-" + thang + "-31";

        params.type_manager = "list_package_return";
        AppProvider.getApiManagement().call(PackageReturnRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<PackageReturnModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<PackageReturnModel> result) {
                dismissProgress();
                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {

                    if (!TextUtils.isEmpty(result.getTotal_page())) {
                        totalPage = Integer.valueOf(result.getTotal_page());
                        if (page == totalPage) {
                            view.setNoMoreLoading();
                        }
                    } else {
                        view.setNoMoreLoading();
                    }
                    view.resetListData();
                    view.setDataList(result);
                } else {
                    if (!TextUtils.isEmpty(result.getMessage()))
                        showAlert(result.getMessage(), KAlertDialog.ERROR_TYPE);
                    else
                        showAlert("Không thể tải dữ liệu.", KAlertDialog.ERROR_TYPE);
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

    boolean isUpdateItem = true;

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackShowRootViewEvent(BackShowRootViewEvent event) {
        isUpdateItem = true;
        if (isUpdateItem) {
            view.showRootView();
            isUpdateItem = false;
        }
    }
}
