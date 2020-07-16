package qtc.project.pos.fragment.product.doitra;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.report.thongkekho.xuatnhapkho.FragmentBaoCaoXuatNhapKho;
import qtc.project.pos.ui.views.fragment.product.doitra.filter.FragmentFilterDoiTraHangHoaView;
import qtc.project.pos.ui.views.fragment.product.doitra.filter.FragmentFilterDoiTraHangHoaViewCallback;
import qtc.project.pos.ui.views.fragment.product.doitra.filter.FragmentFilterDoiTraHangHoaViewInterface;

public class FragmentFilterDoiTraHangHoa extends BaseFragment<FragmentFilterDoiTraHangHoaViewInterface, BaseParameters>implements FragmentFilterDoiTraHangHoaViewCallback {

    HomeActivity activity;

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
    }

    @Override
    protected FragmentFilterDoiTraHangHoaViewInterface getViewInstance() {
        return new FragmentFilterDoiTraHangHoaView();
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
                    activity.setDataSearch(thang,nam);
                }
            }, 100);

        }

        //activity.replaceFragment(new FragmentDoiTraHangHoa().newIntance(thang,nam),true,null);
    }
}
