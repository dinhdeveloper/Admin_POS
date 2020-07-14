package qtc.project.pos.fragment.report.thongkekho.antoankho;

import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import b.laixuantam.myaarlibrary.api.ApiRequest;
import b.laixuantam.myaarlibrary.api.ErrorApiResponse;
import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.api.report.antoankho.BaoCaoAnToanKhoRequest;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.AnToanKhoModel;
import qtc.project.pos.model.BaseResponseModel;
import qtc.project.pos.model.ReportXuatNhapKhoModel;
import qtc.project.pos.ui.views.fragment.report.thongkekho.antoankho.FragmentAnToanKhoView;
import qtc.project.pos.ui.views.fragment.report.thongkekho.antoankho.FragmentAnToanKhoViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkekho.antoankho.FragmentAnToanKhoViewInterface;

public class FragmentAnToanKho extends BaseFragment<FragmentAnToanKhoViewInterface, BaseParameters> implements FragmentAnToanKhoViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentAnToanKhoViewInterface getViewInstance() {
        return new FragmentAnToanKhoView();
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
    public void getAllDataAnToanKho() {
        showProgress();
        BaoCaoAnToanKhoRequest.ApiParams params = new BaoCaoAnToanKhoRequest.ApiParams();
        params.type_manager = "safe_stock_manager";
        AppProvider.getApiManagement().call(BaoCaoAnToanKhoRequest.class, params, new ApiRequest.ApiCallback<BaseResponseModel<AnToanKhoModel>>() {
            @Override
            public void onSuccess(BaseResponseModel<AnToanKhoModel> body) {
                if (body!=null){
                    dismissProgress();
                    ArrayList<AnToanKhoModel> list = new ArrayList<>();
                    list.addAll(Arrays.asList(body.getData()));
                    view.sendDataToView(list);
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
