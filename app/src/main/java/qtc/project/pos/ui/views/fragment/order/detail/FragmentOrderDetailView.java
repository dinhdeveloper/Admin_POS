package qtc.project.pos.ui.views.fragment.order.detail;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.history.ListOrderDetailAdapter;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.OrderCustomerModel;

public class FragmentOrderDetailView extends BaseView<FragmentOrderDetailView.UIContainer> implements FragmentOrderDetailViewInterface {
    HomeActivity activity;
    FragmentOrderDetailViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentOrderDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(),activity);
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
    }

    @Override
    public void sentDataToView(OrderCustomerModel model) {
        try {
            if (model != null) {
                ui.name_customer.setText(model.getCustomer_fullname());
                ui.id_order.setText(model.getOrder_id_code());
                ui.id_customer.setText(model.getCustomer_id_code());
                ui.id_employee.setText(model.getEmployee_code());
                ui.date_order.setText(model.getOrder_created_date());
                ui.phone_customer.setText(model.getCustomer_phone_number());
                AppProvider.getImageHelper().displayImage(Consts.HOST_API + model.getCustomer_level_image(), ui.image_level, null, R.drawable.imageloading);

                if (model.getOrder_status().equals("N")) {
                    ui.status.setText("Đã hủy");
                    ui.status.setTextColor(ContextCompat.getColor(getContext(), R.color.colorYellow));
                    ui.layout_status.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_border_button_item_ordel));

                } else if (model.getOrder_status().equals("Y")) {
                    ui.status.setText("Hoàn thành");
                    ui.status.setTextColor(ContextCompat.getColor(getContext(), R.color.color_success));
                    ui.layout_status.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.custom_border_button_item_success_order));
                }
            }

            ListOrderDetailAdapter adapter = new ListOrderDetailAdapter(activity, model.getListOrderDetailModel());
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(adapter);

            String pattern = "###,###.###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            int tongtien = Integer.parseInt(model.getOrder_total());
            int tamtinh = 0;
            for (int i = 0; i < model.getListOrderDetailModel().size(); i++) {
                int tien_one_item = Integer.parseInt(model.getListOrderDetailModel().get(i).getPrice()) * Integer.parseInt(model.getListOrderDetailModel().get(i).getQuantity());
                tamtinh += tien_one_item;
            }

            ui.priceGiam.setText(decimalFormat.format(tamtinh - tongtien) + " VNĐ");
            ui.priceTotal.setText(decimalFormat.format(Integer.parseInt(model.getOrder_total())) + " VNĐ");
            //int tientemp = Integer.parseInt(model.getOrder_total()) + Integer.parseInt(model.getCustomer_level_discount());
            ui.priceTemp.setText(decimalFormat.format(tamtinh) + " VNĐ");

        } catch (Exception e) {
            Log.e("Ex", e.getMessage());

        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentOrderDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_order_detail;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.name_customer)
        public TextView name_customer;

        @UiElement(R.id.id_order)
        public TextView id_order;

        @UiElement(R.id.id_customer)
        public TextView id_customer;

        @UiElement(R.id.id_employee)
        public TextView id_employee;

        @UiElement(R.id.phone_customer)
        public TextView phone_customer;

        @UiElement(R.id.date_order)
        public TextView date_order;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_level)
        public ImageView image_level;


        @UiElement(R.id.layout_status)
        public LinearLayout layout_status;

        @UiElement(R.id.status)
        public TextView status;

        @UiElement(R.id.priceTemp)
        public TextView priceTemp;

        @UiElement(R.id.priceGiam)
        public TextView priceGiam;

        @UiElement(R.id.priceTotal)
        public TextView priceTotal;


        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;


    }
}
