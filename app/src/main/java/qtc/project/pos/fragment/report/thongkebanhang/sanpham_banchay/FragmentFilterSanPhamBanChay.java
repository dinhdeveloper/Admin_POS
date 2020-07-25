package qtc.project.pos.fragment.report.thongkebanhang.sanpham_banchay;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay.filter.FragmentFilterSanPhamBanChayView;
import qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.filter.FragmentFilterXuatNhapKhoViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.filter.FragmentFilterXuatNhapKhoViewInterface;

public class FragmentFilterSanPhamBanChay extends BaseFragment<FragmentFilterXuatNhapKhoViewInterface, BaseParameters> implements FragmentFilterXuatNhapKhoViewCallback {
    HomeActivity activity;
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentFilterXuatNhapKhoViewInterface getViewInstance() {
        return new FragmentFilterSanPhamBanChayView();
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
    public void filterDataTheoThang(String thang, String nam) {
        if (activity != null) {
            activity.checkBack();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.setDataDateSP_BChay(nam, thang);
                }
            }, 100);
        }

       // activity.replaceFragment(new FragmentSanPhamBanChay().newIntance(thang,nam),true,null);
    }
}
