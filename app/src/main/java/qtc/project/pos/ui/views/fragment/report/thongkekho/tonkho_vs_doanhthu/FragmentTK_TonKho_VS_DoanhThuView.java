package qtc.project.pos.ui.views.fragment.report.thongkekho.tonkho_vs_doanhthu;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.report.tonkho_vs_doanhthu.TonKho_Vs_DoanhThuAdapter;
import qtc.project.pos.model.Stock_Income_Model;
import qtc.project.pos.model.TonKho_Vs_DoanhThuModel;

public class FragmentTK_TonKho_VS_DoanhThuView extends BaseView<FragmentTK_TonKho_VS_DoanhThuView.UIContainer> implements FragmentTK_TonKho_VS_DoanhThuViewInterface {

    HomeActivity activity;
    FragmentTK_TonKho_VS_DoanhThuViewCallback callback;

    String date_start;
    String date_end;
    int thangLocalStart;
    int thangLocalEnd;

    @Override
    public void init(HomeActivity activity, FragmentTK_TonKho_VS_DoanhThuViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    @Override
    public void sentDateToView(String thang, String nam, int ngay) {
        if (ngay == 0) {
            date_start = nam + "-" + thang;
            thangLocalStart = Integer.parseInt(thang);
        } else if (ngay == 1) {
            thangLocalEnd = Integer.parseInt(thang);
            date_end = nam + "-" + thang;
        }

        ui.date_to.setText(date_start);
        ui.date_end.setText(date_end);

        //tim kiem
        ui.layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.layout_status.setVisibility(View.VISIBLE);
                if (callback != null)
                    callback.searchDataToDate(date_start, date_end);
            }
        });
    }

    @Override
    public void mappingDateToView(ArrayList<TonKho_Vs_DoanhThuModel> list) {

        List<Stock_Income_Model> model = new ArrayList<>();

        Log.e("DDAA",thangLocalStart+ "  "+thangLocalEnd);

        for (int i = (thangLocalStart-1); i < thangLocalEnd; i++) {
            Stock_Income_Model stock_income_model = new Stock_Income_Model();
            stock_income_model.setTitle(list.get(0).getListIncomeModel().get(i).getTitle());
            stock_income_model.setValueIncome(list.get(0).getListIncomeModel().get(i).getValue());
            stock_income_model.setValueStock(list.get(0).getListStockModel().get(i).getValue());
            model.add(stock_income_model);
        }

        setHeight(model);

    }

    public void setHeight(List<Stock_Income_Model> list) {
        //stock
        int minStock = Integer.parseInt(list.get(0).getValueStock());
        int maxStock = Integer.parseInt(list.get(0).getValueStock());
        for (int i = 0; i < list.size(); i++) {
            int number = Integer.parseInt(list.get(i).getValueStock());
            if (number < minStock) {
                minStock = number;
            }
            if (number > maxStock) {
                maxStock = number;
            }
        }
        //min max income
        int minIncome = Integer.parseInt(list.get(0).getValueIncome());
        int maxIncome = Integer.parseInt(list.get(0).getValueIncome());
        for (int i = 0; i < list.size(); i++) {
            int number = Integer.parseInt(list.get(i).getValueIncome());
            if (number < minIncome) {
                minIncome = number;
            }
            if (number > maxIncome) {
                maxIncome = number;
            }
        }
        //tim max all
        int maxAll = 0;
        if (maxIncome > maxStock) {
            maxAll = maxIncome;
        } else {
            maxAll = maxStock;
        }

        int maxHeight = 270;

        for (int i = 0; i < list.size(); i++) {
            long heightIncome = (Long.parseLong(list.get(i).getValueIncome()) * maxHeight) / maxAll;
            long heightStock = (Long.parseLong(list.get(i).getValueStock()) * maxHeight) / maxAll;
            list.get(i).setHeightIncome(String.valueOf(heightIncome));
            list.get(i).setHeightStock(String.valueOf(heightStock));
        }

        TonKho_Vs_DoanhThuAdapter thuAdapter = new TonKho_Vs_DoanhThuAdapter(list, activity);
        ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ui.recycler_view_list.setAdapter(thuAdapter);

    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });

        //show calendar
        ui.layout_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.goToFilterDate(0);
                }
            }
        });

        ui.layout_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.goToFilterDate(1);
                }
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentTK_TonKho_VS_DoanhThuView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_tonkho_vs_doanhthu;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

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


        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.layout_status)
        public LinearLayout layout_status;


    }
}
