package qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu;

import android.os.Bundle;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.tongdoanhthu.FragmentTongDoanhThu;
import qtc.project.pos.fragment.report.thongkekho.xuatnhapkho.FragmentBaoCaoXuatNhapKho;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.filter.FragmentFilterTomTatDoanhSoView;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.filter.FragmentFilterTomTatDoanhSoViewCallback;
import qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.filter.FragmentFilterTomTatDoanhSoViewInterface;

public class FragmentFilterTomTatDoanhSo extends BaseFragment<FragmentFilterTomTatDoanhSoViewInterface, BaseParameters> implements FragmentFilterTomTatDoanhSoViewCallback {

    HomeActivity activity;

    public static FragmentFilterTomTatDoanhSo newIntance(int date) {
        FragmentFilterTomTatDoanhSo frag = new FragmentFilterTomTatDoanhSo();
        Bundle bundle = new Bundle();
        bundle.putInt("date", date);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    protected void initialize() {
        activity = (HomeActivity)getActivity();
        view.init(activity,this);
        sentDataChooseDay();
    }
    public void sentDataChooseDay() {
        Bundle extras = getArguments();
        if (extras != null) {
            int date = (int) extras.get("date");
            view.sentDataDayChoose(date);
        }
    }

    @Override
    protected FragmentFilterTomTatDoanhSoViewInterface getViewInstance() {
        return new FragmentFilterTomTatDoanhSoView();
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
    public void filterDataTheoThang(String thang, String nam, int date) {
        if (activity != null) {
            activity.checkBack();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.setDataDate(nam, thang, date);
                }
            }, 100);

        }
    }

    @Override
    public void filterDataYear(String nam) {
        if (activity != null) {
            activity.checkBack();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.setDataYear(nam);
                }
            }, 100);

        }
    }

//    @Override
//    public void filterDataTheoThang(String nam, String thang,int date) {
//        if (activity != null) {
//            activity.checkBack();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    activity.setDataDate(nam,thang,date);
//                }
//            }, 100);
//
//        }
//        //activity.replaceFragment(new FragmentTongDoanhThu().newIntance(thang,nam,date),false,null);
//    }
}
