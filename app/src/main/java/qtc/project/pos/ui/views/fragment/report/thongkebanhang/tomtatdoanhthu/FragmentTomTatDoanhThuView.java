package qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;

public class FragmentTomTatDoanhThuView extends BaseView<FragmentTomTatDoanhThuView.UIContainer> implements FragmentTomTatDoanhThuViewInterface {

    HomeActivity activity;
    FragmentTomTatDoanhThuViewCallback callback;


    @Override
    public void init(HomeActivity activity, FragmentTomTatDoanhThuViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

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
        //chon trang thai
        ui.layout_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.custom_popup_choose_tom_tat_doanh_thu, null);
                LinearLayout item_tong_doanh_thu = popupView.findViewById(R.id.item_tong_doanh_thu);
                LinearLayout item_thong_ke = popupView.findViewById(R.id.item_thong_ke);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(popupView);
                AlertDialog dialog = alert.create();
                //dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                //tong doanh thu
                item_tong_doanh_thu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.goToFragmentTongDoanhThu();
                            dialog.dismiss();
                        }
                    }
                });

                //thong ke
                item_thong_ke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null){
                            callback.goToFragmentThongKe();
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentTomTatDoanhThuView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_tong_doanh_thu_manager;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.layout_choose)
        public LinearLayout layout_choose;

        @UiElement(R.id.layout_status)
        public LinearLayout layout_status;

    }
}
