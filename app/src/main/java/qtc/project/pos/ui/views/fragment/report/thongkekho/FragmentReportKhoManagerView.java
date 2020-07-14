package qtc.project.pos.ui.views.fragment.report.thongkekho;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.report.thongkekho.antoankho.FragmentAnToanKho;
import qtc.project.pos.fragment.report.thongkekho.tonkho_vs_doanhthu.FragmentTK_TonKho_VS_DoanhThu;
import qtc.project.pos.fragment.report.thongkekho.xuatnhapkho.FragmentBaoCaoXuatNhapKho;

public class FragmentReportKhoManagerView extends BaseView<FragmentReportKhoManagerView.UIContainer> implements FragmentReportKhoManagerViewInterface{

    HomeActivity activity;
    FragmentReportKhoManagerViewCallback callback;
    @Override
    public void init(HomeActivity activity, FragmentReportKhoManagerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        
        onClick();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });

        //bao cao xuat nhap kho
        ui.layoutBCXN_KHO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentBaoCaoXuatNhapKho(),true,null);
            }
        });
        //bao cao an toan kho
        ui.layoutBCAT_KHO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentAnToanKho(),true,null);
            }
        });
        //thong ke ton kho so vs doanh thu
        ui.layoutBCTK_VS_DT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.addFragment(new FragmentTK_TonKho_VS_DoanhThu(),true,null);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentReportKhoManagerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_report_kho_manager;
    }



    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.layoutBCXN_KHO)
        public LinearLayout layoutBCXN_KHO;

        @UiElement(R.id.layoutBCAT_KHO)
        public LinearLayout layoutBCAT_KHO;

        @UiElement(R.id.layoutBCTK_VS_DT)
        public LinearLayout layoutBCTK_VS_DT;
        
    }
}
