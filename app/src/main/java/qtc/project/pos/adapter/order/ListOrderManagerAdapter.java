package qtc.project.pos.adapter.order;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.OrderCustomerModel;

public class ListOrderManagerAdapter extends SuperAdapter<OrderCustomerModel> {
    ListOrderManagerAdapterListener listener;

    public ListOrderManagerAdapter(Context context, List<OrderCustomerModel> items) {
        super(context, items, R.layout.custom_item_order);
    }

    public interface ListOrderManagerAdapterListener{
        void onItemClick(OrderCustomerModel model);
    }

    public void setListener(ListOrderManagerAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OrderCustomerModel item) {
        TextView nameCustomer = holder.findViewById(R.id.name_customer);
        TextView orderId = holder.findViewById(R.id.id_order);
        TextView phoneCustomer = holder.findViewById(R.id.phone_customer);
        TextView addressCustomer = holder.findViewById(R.id.address_customer);
        TextView createDay = holder.findViewById(R.id.date_order);
        TextView status = holder.findViewById(R.id.status);
        LinearLayout btnSubmit = holder.findViewById(R.id.layout_status);
        LinearLayout layoutItemOrder = holder.findViewById(R.id.layoutItemOrder);


        nameCustomer.setText(item.getCustomer_fullname());
        orderId.setText("Mã đơn: "+item.getId_order());
        phoneCustomer.setText(item.getCustomer_phone_number());
        addressCustomer.setText(item.getEmployee_address());
        createDay.setText(item.getOrder_created_date());
        if (item.getOrder_status().equals("N")){
            status.setText("Đã hủy");
            status.setTextColor(ContextCompat.getColor(getContext(),R.color.colorYellow));
            btnSubmit.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.custom_border_button_item_ordel));

        }else if (item.getOrder_status().equals("Y")){
            status.setText("Hoàn thành");
            status.setTextColor(ContextCompat.getColor(getContext(),R.color.color_success));
            btnSubmit.setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.custom_border_button_item_success_order));
        }
        layoutItemOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener !=null){
                    listener.onItemClick(item);
                }
            }
        });
    }
}
