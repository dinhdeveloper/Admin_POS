package qtc.project.pos.adapter.customer;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.CustomerModel;

public class ListCustomerAdapter extends SuperAdapter<OptionModel> {
    ListCustomerAdapterListener listener;

    public interface ListCustomerAdapterListener{
        void onClickItem(OptionModel model);
    }

    public void setListener(ListCustomerAdapterListener listener){
        this.listener = listener;
    }

    public ListCustomerAdapter(Context context, List<OptionModel> items) {
        super(context, items, R.layout.custom_item_profile_customer);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OptionModel model) {
        RelativeLayout layout_item = holder.findViewById(R.id.layout_item);
        TextView name_customer = holder.findViewById(R.id.name_customer);
        TextView phoneCustomer = holder.findViewById(R.id.phoneCustomer);
        TextView addressCustomer = holder.findViewById(R.id.addressCustomer);
        ImageView image_level = holder.findViewById(R.id.image_level);

        CustomerModel item = (CustomerModel)model.getDtaCustom();
        try{
            if (item!=null){
                name_customer.setText(item.getFull_name());
                phoneCustomer.setText(item.getPhone_number());
                addressCustomer.setText(item.getAddress());
                AppProvider.getImageHelper().displayImage(Consts.HOST_API+item.getLevel_image(),image_level,null,R.drawable.no_image_full);
                layout_item.setOnClickListener(v -> {
                    if (listener!=null){
                        listener.onClickItem(model);
                    }
                });
            }
        }catch (Exception e){
            Log.e("Exception",e.getMessage());
        }
    }
}
