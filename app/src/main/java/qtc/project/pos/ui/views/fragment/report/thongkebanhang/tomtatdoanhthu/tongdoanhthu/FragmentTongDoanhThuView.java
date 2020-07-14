package qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.tongdoanhthu;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.DataChartModel;
import qtc.project.pos.model.TongDoanhThuModel;

public class FragmentTongDoanhThuView extends BaseView<FragmentTongDoanhThuView.UIContainer> implements FragmentTongDoanhThuViewInterface {

    HomeActivity activity;
    FragmentTongDoanhThuViewCallback callback;

    String date_start;
    String date_end;

    @Override
    public void init(HomeActivity activity, FragmentTongDoanhThuViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        onClick();
    }

    @Override
    public void sentDataDayChoose(String thang, String nam, int date) {
        if (date == 0) {
            date_start = nam + "-" + thang;
        } else if (date == 1) {
            date_end = nam + "-" + thang;
        }

        ui.date_to.setText(date_start);
        ui.date_end.setText(date_end);
    }

    @Override
    public void sentDataTongDoanhThu(List<TongDoanhThuModel> models) {
        ui.layout_status.setVisibility(View.VISIBLE);

        List<DataChartModel> chartModels = models.get(0).getListDataChartModel();

        int size = chartModels.size();
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += Integer.parseInt(chartModels.get(i).getValue());
        }
        String pattern = "###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        ui.tong_tien.setText(decimalFormat.format(total));

    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
        //CHON NGAY
        //layout tong doanh thu
        ui.layout_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.chooseTimeStart(0);
                }
            }
        });

        ui.layout_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.chooseTimeEnd(1);
                }
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

                item_tong_doanh_thu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                item_thong_ke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.goToFragmentThongKe();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        //search
        ui.layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date_start == null && date_end == null) {
                    Toast.makeText(activity, "Hãy chọn đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    if (callback != null)
                        callback.searchData(date_start, date_end);
                }
            }
        });

//        //thong ke
//        ui.layout_choose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback!=null)
//                    callback.goToFragmentThongKe();
//            }
//        });
    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentTongDoanhThuView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_tom_tat_doanh_thu;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.choose_status)
        public TextView choose_status;

        @UiElement(R.id.tong_tien)
        public TextView tong_tien;

        @UiElement(R.id.layout_choose)
        public LinearLayout layout_choose;

        @UiElement(R.id.layout_status)
        public LinearLayout layout_status;

        @UiElement(R.id.layout_to)
        public LinearLayout layout_to;

        @UiElement(R.id.layout_from)
        public LinearLayout layout_from;

        @UiElement(R.id.layout_search)
        public LinearLayout layout_search;

        @UiElement(R.id.date_to)
        public TextView date_to;

        @UiElement(R.id.date_end)
        public TextView date_end;

    }
}
