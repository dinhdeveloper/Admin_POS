package qtc.project.pos.ui.views.fragment.history.history.detail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;

import java.text.DecimalFormat;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.history.ListOrderDetailAdapter;
import qtc.project.pos.model.OrderCustomerModel;

public class FragmentOrderDetailCustomerView extends BaseView<FragmentOrderDetailCustomerView.UIContainer> implements FragmentOrderDetailCustomerViewInterface {

    HomeActivity activity;
    FragmentOrderDetailCustomerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentOrderDetailCustomerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        onClick();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackPregress();
            }
        });
    }

    @Override
    public void sentDataToView(OrderCustomerModel model) {
        try {
            if (model != null) {
                ui.id_order.setText(model.getId_order());
                ui.name_customer.setText(model.getCustomer_fullname());
                ui.date_create.setText(model.getOrder_created_date());
                if (model.getOrder_status().equals("Y")){
                    ui.status_order.setText("Hoàn thành");
                }else if (model.getOrder_status().equals("N")){
                    ui.status_order.setText("Đã hủy");
                }

                String pattern = "###,###.###";
                DecimalFormat decimalFormat = new DecimalFormat(pattern);

                ui.priceGiam.setText(decimalFormat.format(Integer.parseInt(model.getCustomer_level_discount())) + " VNĐ");
                ui.priceTotal.setText(decimalFormat.format(Integer.parseInt(model.getOrder_total())) + " VNĐ");
                int tientemp = Integer.parseInt(model.getOrder_total()) + Integer.parseInt(model.getCustomer_level_discount());
                ui.priceTemp.setText(decimalFormat.format(tientemp) + " VNĐ");

                ListOrderDetailAdapter adapter = new ListOrderDetailAdapter(activity, model.getListDataProduct());
                ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                ui.recycler_view_list.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentOrderDetailCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_order_detail_customer;
    }

    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.id_order)
        public TextView id_order;

        @UiElement(R.id.name_customer)
        public TextView name_customer;

        @UiElement(R.id.date_create)
        public TextView date_create;

        @UiElement(R.id.status_order)
        public TextView status_order;



        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.priceTemp)
        public TextView priceTemp;

        @UiElement(R.id.priceGiam)
        public TextView priceGiam;

        @UiElement(R.id.priceTotal)
        public TextView priceTotal;


    }
}
