package qtc.project.pos.fragment.product.quanlylohang;

import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.productlist.ProductListRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.event.UpdateListProductEvent;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.FragmentQuanLyLoHangView;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.FragmentQuanLyLoHangViewCallback;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.FragmentQuanLyLoHangViewInterface;

public class FragmentQuanLyLoHang extends BaseFragment<FragmentQuanLyLoHangViewInterface, BaseParameters> implements FragmentQuanLyLoHangViewCallback {

    HomeActivity activity;
    String search;
    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentQuanLyLoHangViewInterface getViewInstance() {
        return new FragmentQuanLyLoHangView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackprogress() {
        if (activity != null)
            activity.checkBack();
    }

    @Override
    public void callDataSearch(String toString) {
        if (toString != null) {
            showProgress();
            ProductListRequest.ApiParams params = new ProductListRequest.ApiParams();
            params.type_manager = "list_product";
            if (!TextUtils.isEmpty(toString)) {
                search = toString;
                params.product = toString;
            }
            AppProvider.getApiManagement().call(ProductListRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    if (body != null) {
                        dismissProgress();
                        ArrayList<ProductListModel> list = new ArrayList<>();
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
    public void sentDataToDetail(PackageInfoModel model, String name_product, String id_product) {
        if (activity != null) {
            activity.changToFragmentLoHangDetail(model, name_product, id_product);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.hideRootView();
                }
            }, 500);
        }
    }
    boolean isUpdateItem = false;
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBackShowRootViewEvent(BackShowRootViewEvent event) {
        isUpdateItem = true;
        if (isUpdateItem) {
            view.showRootView();
            isUpdateItem = false;
        }
    }
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateListProductEvent(UpdateListProductEvent event) {
        isUpdateItem = true;
        callDataSearch(search);
        if (isUpdateItem) {
            view.showRootView();
            view.hideRecyclerViewDetail();
            isUpdateItem = false;
        }
    }
}
