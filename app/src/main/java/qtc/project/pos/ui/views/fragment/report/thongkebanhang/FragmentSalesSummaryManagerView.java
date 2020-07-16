package qtc.project.pos.ui.views.fragment.report.thongkebanhang;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.fragment.report.thongkebanhang.doanhthu_theo_khachhang.FragmentDoanhThuTheoKhachHang;
import qtc.project.pos.fragment.report.thongkebanhang.doanhthu_theo_sanpham.FragmentDoanhThuTheoSp;
import qtc.project.pos.fragment.report.thongkebanhang.sanpham_banchay.FragmentSanPhamBanChay;
import qtc.project.pos.fragment.report.thongkebanhang.tomtatdoanhthu.FragmentTomTatDoanhThu;

public class FragmentSalesSummaryManagerView extends BaseView<FragmentSalesSummaryManagerView.UIContainer> implements FragmentSalesSummaryManagerViewInterface {

    HomeActivity activity;
    FragmentSalesSummaryManagerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentSalesSummaryManagerViewCallback callback) {
        this.callback = callback;
        this.activity = activity;

        onClick();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });

        //ton tat doanh thu
        ui.layoutTTDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new FragmentTomTatDoanhThu(), true, null);
            }
        });
        //san pham ban chay
        ui.layoutSP_BC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new FragmentSanPhamBanChay(),true,null);
            }
        });

        //doanh thu theo khach hang
        ui.layoutDT_KH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new FragmentDoanhThuTheoKhachHang(),true,null);
            }
        });

        //doanh thu theo san pham
        ui.layoutDT_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new FragmentDoanhThuTheoSp(),true,null);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentSalesSummaryManagerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_sales_summary_manager;
    }


    public class UIContainer extends BaseUiContainer {

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.layoutTTDT)
        public LinearLayout layoutTTDT;

        @UiElement(R.id.layoutDT_KH)
        public LinearLayout layoutDT_KH;

        @UiElement(R.id.layoutDT_SP)
        public LinearLayout layoutDT_SP;

        @UiElement(R.id.layoutSP_BC)
        public LinearLayout layoutSP_BC;


    }
}
