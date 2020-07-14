package qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.thongke;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.report.tomtatdoanhso.ThongKeAdapter;
import qtc.project.pos.model.DataChartModel;
import qtc.project.pos.model.TongDoanhThuModel;

public class FragmentThongKeView extends BaseView<FragmentThongKeView.UIContainer> implements FragmentThongKeViewInterface {
    HomeActivity activity;
    FragmentThongKeViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentThongKeViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        ui.choose_status.setText("Thống kê");

        onClick();
    }

    @Override
    public void mappingYear(ArrayList<TongDoanhThuModel> list) {
        if (list != null) {
            ui.layout_status.setVisibility(View.VISIBLE);
            ThongKeAdapter thongKeAdapter = new ThongKeAdapter(activity, list.get(0).getListDataChartModel());
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(thongKeAdapter);
            thongKeAdapter.notifyDataSetChanged();

            int tongtien = 0;
            for (int i = 0; i < list.get(0).getListDataChartModel().size(); i++) {
                tongtien += Integer.parseInt(list.get(0).getListDataChartModel().get(i).getValue());
            }
            String pattern = "###,###,###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            ui.tong_tien.setText(decimalFormat.format(tongtien));
        }
    }

    @Override
    public void sentYearToView(String nam) {
        ui.date_to.setText(nam);
        ui.layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.getDataToYear(nam);
            }
        });
    }


    private void onClick() {

        //back
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

                item_tong_doanh_thu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.goToTongDoanhThu();
                            dialog.dismiss();
                        }
                    }
                });

                item_thong_ke.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        //choose day
        ui.layout_choose_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.goToChooseYear();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentThongKeView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_thong_ke;
    }


    public class UIContainer extends BaseUiContainer {

        @UiElement(R.id.layout_choose_day)
        public LinearLayout layout_choose_day;

        @UiElement(R.id.layout_choose)
        public LinearLayout layout_choose;

        @UiElement(R.id.choose_status)
        public TextView choose_status;

        @UiElement(R.id.date_to)
        public TextView date_to;

        @UiElement(R.id.layout_search)
        public LinearLayout layout_search;

        @UiElement(R.id.layout_status)
        public LinearLayout layout_status;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.tong_tien)
        public TextView tong_tien;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
