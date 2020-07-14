package qtc.project.pos.fragment.report.thongkekho.xuatnhapkho;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.filter.FragmentFilterXuatNhapKhoView;
import qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.filter.FragmentFilterXuatNhapKhoViewCallback;

public class FragmentFilterXuatNhapKho extends BaseFragment<FragmentFilterXuatNhapKhoView, BaseParameters> implements FragmentFilterXuatNhapKhoViewCallback {

    HomeActivity activity;
    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentFilterXuatNhapKhoView getViewInstance() {
        return new FragmentFilterXuatNhapKhoView();
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
        activity.replaceFragment(new FragmentBaoCaoXuatNhapKho().newIntance(thang,nam),false,null);
    }
}
