package qtc.project.pos.adapter.customer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.CustomerModel;

public class ListCustomerAdapter extends SuperAdapter<CustomerModel> {
    ListCustomerAdapterListener listener;

    public interface ListCustomerAdapterListener{
        void onClickItem(CustomerModel model);
    }

    public void setListener(ListCustomerAdapterListener listener){
        this.listener = listener;
    }

    public ListCustomerAdapter(Context context, List<CustomerModel> items) {
        super(context, items, R.layout.custom_item_profile_customer);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CustomerModel item) {
        RelativeLayout layout_item = holder.findViewById(R.id.layout_item);
        TextView name_customer = holder.findViewById(R.id.name_customer);
        TextView phoneCustomer = holder.findViewById(R.id.phoneCustomer);
        TextView addressCustomer = holder.findViewById(R.id.addressCustomer);

        try{
            if (item!=null){
                name_customer.setText(item.getFull_name());
                phoneCustomer.setText(item.getPhone_number());
                addressCustomer.setText(item.getAddress());

                layout_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener!=null){
                            listener.onClickItem(item);
                        }
                    }
                });
            }
        }catch (Exception e){
            Log.e("Exception",e.getMessage());
        }
    }
}
