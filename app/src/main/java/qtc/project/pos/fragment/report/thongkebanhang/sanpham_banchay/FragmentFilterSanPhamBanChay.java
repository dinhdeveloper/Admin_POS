package qtc.project.pos.fragment.report.thongkebanhang.sanpham_banchay;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay.FragmentSanPhamBanChayViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay.FragmentSanPhamBanChayViewInterface;
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
        activity.replaceFragment(new FragmentSanPhamBanChay().newIntance(thang,nam),false,null);
    }
}
