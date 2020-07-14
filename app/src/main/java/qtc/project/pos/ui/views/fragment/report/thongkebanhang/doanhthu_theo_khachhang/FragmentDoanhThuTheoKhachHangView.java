package qtc.project.pos.ui.views.fragment.report.thongkebanhang.doanhthu_theo_khachhang;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import qtc.project.pos.adapter.report.tomtatdoanhso.ThongKeAdapter;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.DataChartModel;
import qtc.project.pos.model.TongDoanhThuModel;

public class FragmentDoanhThuTheoKhachHangView extends BaseView<FragmentDoanhThuTheoKhachHangView.UIContainer> implements FragmentDoanhThuTheoKhachHangViewInterface {
    HomeActivity activity;
    FragmentDoanhThuTheoKhachHangViewCallback callback;

    String date_start;
    String date_end;
    String customer_id = null;

    @Override
    public void init(HomeActivity activity, FragmentDoanhThuTheoKhachHangViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    @Override
    public void mappingView(ArrayList<CustomerModel> list) {
        if (list != null) {
            ui.layout_nodata.setVisibility(View.GONE);
            ui.layout_data.setVisibility(View.VISIBLE);

            ui.name_customer.setText(list.get(0).getFull_name());
            ui.id_customer.setText(list.get(0).getId_code());
            ui.phoneCustomer.setText(list.get(0).getPhone_number());
            customer_id = list.get(0).getId();

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
                            ui.chon_moc_time.setVisibility(View.VISIBLE);
                            ui.layout_nodata.setVisibility(View.GONE);
                            ui.layout_thongke.setVisibility(View.GONE);
                            dialog.dismiss();
                            ui.text_status.setText("Tổng doanh thu");

                            ui.layout_to.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (callback != null) {
                                        callback.goToFragmentFilter(0);
                                    }
                                }
                            });
                            ui.layout_from.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (callback != null) {
                                        callback.goToFragmentFilter(1);
                                    }
                                }
                            });
                        }
                    });

                    item_thong_ke.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ui.chon_moc_time.setVisibility(View.GONE);
                            ui.layout_nodata.setVisibility(View.GONE);
                            ui.layout_thongke.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                            ui.text_status.setText("Thống kê");

                            //chon nam
                            ui.layout_choose_year.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (callback!=null)
                                        callback.chooseYear();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    @Override
    public void mappingDateToView(String thang, String nam, int ngay) {
        if (ngay == 0) {
            date_start = nam + "-" + thang;
        } else if (ngay == 1) {
            date_end = nam + "-" + thang;
        }
        ui.date_to.setText(date_start);
        ui.date_end.setText(date_end);

        ui.layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.getDataFilterTime(date_start, date_end);
                }
            }
        });
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

    @Override
    public void sentYearToView(String nam) {
        ui.date_year.setText(nam);
        ui.layout_search_thongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.getDataToYear(nam,customer_id);
            }
        });
    }

    @Override
    public void mappingYear(ArrayList<TongDoanhThuModel> list) {
        if (list != null) {
            ui.layout_show_tien_thongke.setVisibility(View.VISIBLE);
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

            ui.tong_tien_thongke.setText(decimalFormat.format(tongtien));
        }
    }

    private void onClick() {
        //onBack
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });

        //search
        ui.text_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        ui.text_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCustomer(ui.text_search.getText().toString());
                    return true;
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.text_search.getText().toString() != null) {
                    searchCustomer(ui.text_search.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void searchCustomer(String sdt) {
        if (callback != null)
            callback.searchCustomer(sdt);
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentDoanhThuTheoKhachHangView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_doanh_thu_theo_khach_hang;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;

        @UiElement(R.id.text_search)
        public EditText text_search;

        @UiElement(R.id.layout_nodata)
        public LinearLayout layout_nodata;

        @UiElement(R.id.layout_data)
        public NestedScrollView layout_data;

        @UiElement(R.id.chon_moc_time)
        public LinearLayout chon_moc_time;

        //item customer
        @UiElement(R.id.id_customer)
        public TextView id_customer;

        @UiElement(R.id.name_customer)
        public TextView name_customer;

        @UiElement(R.id.phoneCustomer)
        public TextView phoneCustomer;

        @UiElement(R.id.layout_choose)
        public LinearLayout layout_choose;

        @UiElement(R.id.text_status)
        public TextView text_status;

        @UiElement(R.id.layout_to)
        public LinearLayout layout_to;

        @UiElement(R.id.layout_from)
        public LinearLayout layout_from;

        @UiElement(R.id.date_to)
        public TextView date_to;

        @UiElement(R.id.date_end)
        public TextView date_end;

        @UiElement(R.id.layout_search)
        public LinearLayout layout_search;

        @UiElement(R.id.layout_status)
        public LinearLayout layout_status;

        @UiElement(R.id.tong_tien)
        public TextView tong_tien;

        //thongke
        @UiElement(R.id.layout_choose_year)
        public LinearLayout layout_choose_year;

        @UiElement(R.id.layout_thongke)
        public LinearLayout layout_thongke;

        @UiElement(R.id.layout_show_tien_thongke)
        public LinearLayout layout_show_tien_thongke;

        @UiElement(R.id.layout_search_thongke)
        public LinearLayout layout_search_thongke;

        @UiElement(R.id.date_year)
        public TextView date_year;

        @UiElement(R.id.tong_tien_thongke)
        public TextView tong_tien_thongke;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;


    }
}
