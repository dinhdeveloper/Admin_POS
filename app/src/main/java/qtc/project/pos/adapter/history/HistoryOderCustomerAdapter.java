package qtc.project.pos.adapter.history;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.OrderCustomerModel;

public class HistoryOderCustomerAdapter extends SuperAdapter<OrderCustomerModel> {

    HistoryOderCustomerAdapterListener listener;

    public interface HistoryOderCustomerAdapterListener {
        void onClickItem(OrderCustomerModel model);
    }

    public void setListener(HistoryOderCustomerAdapterListener listener) {
        this.listener = listener;
    }


    public HistoryOderCustomerAdapter(Context context, List<OrderCustomerModel> items) {
        super(context, items, R.layout.custom_item_history_order_customer);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OrderCustomerModel item) {
        LinearLayout layout_item = holder.findViewById(R.id.layout_item);
        TextView id_customer = holder.findViewById(R.id.id_customer);
        TextView name_customer = holder.findViewById(R.id.name_customer);
        TextView phoneCustomer = holder.findViewById(R.id.phoneCustomer);

        try {
            if (item != null) {
                id_customer.setText(item.getOrder_id_code());
                name_customer.setText(item.getCustomer_fullname());
                phoneCustomer.setText(item.getCustomer_phone_number());

                layout_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.onClickItem(item);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }
}
