package qtc.project.pos.adapter.customer;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.CustomerModel;

public class CustomerAdapter extends SuperAdapter<CustomerModel> {

    private CustomerAdapterListener listener;

    public CustomerAdapter(Context context, List<CustomerModel> items) {
        super(context, items, R.layout.custom_item_level_customer);
    }

    public interface CustomerAdapterListener{
        void onItemClick(CustomerModel model);
    }

    public void setListener(CustomerAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CustomerModel item) {
        TextView nameCustomer = holder.findViewById(R.id.nameCustomer);
        TextView phoneCustomer = holder.findViewById(R.id.phoneCustomer);
        TextView addressCustomer = holder.findViewById(R.id.addressCustomer);
        LinearLayout layout_cus = holder.findViewById(R.id.layout_cus);

        if (item!=null){
            nameCustomer.setText(item.getFull_name());
            phoneCustomer.setText(item.getPhone_number());
            addressCustomer.setText(item.getAddress());

            layout_cus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        listener.onItemClick(item);
                    }
                }
            });
        }
    }
}
