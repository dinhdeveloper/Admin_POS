package qtc.project.pos.adapter.employee;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.OrderCustomerModel;


public class HistoryOderEmployeeAdapter extends SuperAdapter<OrderCustomerModel> {

    HistoryOderEmployeeAdapterListener listener;

    public interface HistoryOderEmployeeAdapterListener {
        void onClickItem(OrderCustomerModel model);
    }

    public void setListener(HistoryOderEmployeeAdapterListener listener) {
        this.listener = listener;
    }


    public HistoryOderEmployeeAdapter(Context context, List<OrderCustomerModel> items) {
        super(context, items, R.layout.custom_tem_history_saler_employee);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OrderCustomerModel item) {
        LinearLayout layout_item = holder.findViewById(R.id.layout_item);
        TextView id_employee = holder.findViewById(R.id.id_employee);
        TextView name_employee = holder.findViewById(R.id.name_employee);
        TextView date_create = holder.findViewById(R.id.date_create);

        try {
            if (item != null) {
                id_employee.setText(item.getEmployee_code());
                name_employee.setText(item.getEmployee_fullname());
                date_create.setText(item.getOrder_created_date());

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