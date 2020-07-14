package qtc.project.pos.adapter.history;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.OrderDetailModel;

public class ListOrderDetailAdapter extends SuperAdapter<OrderDetailModel> {

    public ListOrderDetailAdapter(Context context, List<OrderDetailModel> items) {
        super(context, items, R.layout.custom_item_order_detail);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OrderDetailModel item) {
        TextView name_product = holder.findViewById(R.id.name_product);
        ImageView image_product = holder.findViewById(R.id.image_product);
        TextView price_buy = holder.findViewById(R.id.price_buy);
        TextView quantity_product = holder.findViewById(R.id.quantity_product);
        TextView total_price = holder.findViewById(R.id.total_price);

        try {
            if (item != null) {
                name_product.setText(item.getName());
                Glide.with(getContext()).load(Consts.HOST_API +item.getImage()).error(R.drawable.imageloading).into(image_product);

                String pattern = "###,###.###";
                DecimalFormat decimalFormat = new DecimalFormat(pattern);

                price_buy.setText(decimalFormat.format(Integer.parseInt(item.getPrice())));
                quantity_product.setText(item.getQuantity());
                int total = Integer.parseInt(item.getQuantity()) * Integer.parseInt(item.getPrice());
                total_price.setText(decimalFormat.format(total));
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }
}
