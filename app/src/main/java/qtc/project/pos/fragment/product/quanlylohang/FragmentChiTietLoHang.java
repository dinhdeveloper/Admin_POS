package qtc.project.pos.fragment.product.quanlylohang;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.product.packageproduct.PackageProductRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.event.BackShowRootViewEvent;
import qtc.project.pos.event.BackShowRootViewEvent2;
import qtc.project.pos.event.UpdateCreateProductEvent;
import qtc.project.pos.event.UpdateListProductEvent;
import qtc.project.pos.fragment.supplier.FragmentSupplier;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.SupplierModel;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.detail.FragmentChiTietLoHangView;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.detail.FragmentChiTietLoHangViewCallback;
import qtc.project.pos.ui.views.fragment.product.quanlylohang.detail.FragmentChiTietLoHangViewInterface;

public class FragmentChiTietLoHang extends BaseFragment<FragmentChiTietLoHangViewInterface, BaseParameters> implements FragmentChiTietLoHangViewCallback {

    HomeActivity activity;

    public static FragmentChiTietLoHang newIntance(PackageInfoModel infoModel, String name_product, String id_product) {
        FragmentChiTietLoHang frag = new FragmentChiTietLoHang();
        Bundle bundle = new Bundle();
        bundle.putSerializable("infoModel", infoModel);
        bundle.putString("name_product", name_product);
        bundle.putString("id_product", id_product);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
        getDataIntance();
    }

    public void setDataNhaCungUng(SupplierModel model) {
        if (view != null) {
            view.sendDataToViewTwo(model);
        }
    }

    private void getDataIntance() {
        Bundle extras = getArguments();
        if (extras != null) {
            PackageInfoModel infoModel = (PackageInfoModel) extras.get("infoModel");
            String name_product = (String) extras.get("name_product");
            String id_product = (String) extras.get("id_product");
            if (infoModel != null) {
                view.sendDataToView(infoModel, name_product, id_product);
            }else {
                view.sendDataToView(null, null, null);
            }
        }
    }

    @Override
    protected FragmentChiTietLoHangViewInterface getViewInstance() {
        return new FragmentChiTietLoHangView();
    }

    @Override
    protected BaseParameters getParametersContainer() {
        return null;
    }

    @Override
    public void onBackProgress() {
        if (activity != null)
            activity.checkBack();
            BackShowRootViewEvent.post();
    }

    @Override
    public void getAllDataNhaCungUng() {
        activity.addFragment(new FragmentSupplier(), true, null);
    }

    @Override
    public void getAllDataProduct(boolean check) {
        if (activity!=null)
            activity.changToFragmentGetAllProduct(check);
    }

    @Override
    public void updateDataPackage(PackageInfoModel infoModel, String id_product) {
        if (infoModel != null) {
            PackageProductRequest.ApiParams params = new PackageProductRequest.ApiParams();
            params.type_manager = "update_package";
            params.product_id = id_product;
            params.id_package = infoModel.getPack_id();
            params.manufacturer_id = infoModel.getManufacturer_id();
            params.description = infoModel.getDescription();
            params.employee_id = AppProvider.getPreferences().getUserModel().getId();
            params.expiry_date = infoModel.getExpiry_date();
            params.import_date = infoModel.getImport_date();
            params.import_price = infoModel.getImport_price();
            params.quantity_order = infoModel.getQuantity_order();
            params.quantity_storage = infoModel.getQuantity_storage();

            AppProvider.getApiManagement().call(PackageProductRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<ProductListModel>>() {
                @Override
                public void onSuccess(BaseResponseModel<ProductListModel> body) {
                    dismissProgress();
                    if (!TextUtils.isEmpty(body.getSuccess()) && body.getSuccess().equals("true")) {
                        showAlert(body.getMessage(), KAlertDialog.SUCCESS_TYPE);
                        UpdateListProductEvent.post();
                    } else {
                        showAlert(body.getMessage(), KAlertDialog.ERROR_TYPE);
                    }
                }

                @Override
                public void onError(ErrorApiResponse error) {
                    dismissProgress();
                    showAlert("Không tải được dữ liệu", KAlertDialog.ERROR_TYPE);
                }

                @Override
                public void onFail(ApiRequest.RequestError error) {
                    dismissProgress();
                    showAlert("Không tải được dữ liệu", KAlertDialog.ERROR_TYPE);
                }
            });
        }
    }

    @Override
    public void taoDonTraHang(PackageInfoModel infoModel, String name, String id) {
        if (activity != null) {
            activity.changToFragmentCreateDonTraHang(infoModel, name, id);
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
    public void onBackShowRootViewEvent2(BackShowRootViewEvent2 event) {
        isUpdateItem = true;
        if (isUpdateItem) {
            view.showRootView();
            isUpdateItem = false;
        }
    }

    public void setDataProduct(ProductListModel model) {
        if (model!=null){
            view.sentDataProductToView(model);
        }
    }

//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onUpdateCreateProductEvent(UpdateCreateProductEvent event) {
//        isUpdateItem = true;
//        if (isUpdateItem) {
//            view.showRootView();
//            isUpdateItem = false;
//        }
//    }
}
