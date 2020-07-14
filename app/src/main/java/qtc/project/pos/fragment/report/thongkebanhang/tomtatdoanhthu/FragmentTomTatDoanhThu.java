package qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.thongke.FragmentThongKe;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.tongdoanhthu.FragmentTongDoanhThu;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentTomTatDoanhThuView;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentTomTatDoanhThuViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentTomTatDoanhThuViewInterface;

public class FragmentTomTatDoanhThu extends BaseFragment<FragmentTomTatDoanhThuViewInterface, BaseParameters> implements FragmentTomTatDoanhThuViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity) getActivity();
        view.init(activity, this);
    }

    @Override
    protected FragmentTomTatDoanhThuViewInterface getViewInstance() {
        return new FragmentTomTatDoanhThuView();
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
    public void goToFragmentTongDoanhThu() {
        activity.addFragment(new FragmentTongDoanhThu(),true,null);
    }

    @Override
    public void goToFragmentThongKe() {
        activity.addFragment(new FragmentThongKe(),true,null);
    }
}
