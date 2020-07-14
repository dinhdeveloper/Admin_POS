package qtc.project.pos.adapter.report.hangbanchay;

import android.content.Context;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.TopProductModel;

public class HangBanChayAdapter extends SuperAdapter<TopProductModel> {

    public HangBanChayAdapter(Context context, List<TopProductModel> items) {
        super(context, items, R.layout.custom_item_sanpham_banchay);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, TopProductModel item) {
        TextView top_product = holder.findViewById(R.id.top_product);
        TextView name_product = holder.findViewById(R.id.name_product);
        TextView quantity_product = holder.findViewById(R.id.quantity_product);
        TextView total_price = holder.findViewById(R.id.total_price);

        if (item != null) {
            String pattern = "###,###,###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            top_product.setText("TOP " + (layoutPosition + 1));
            name_product.setText(item.getProduct_name());
            quantity_product.setText(decimalFormat.format(Integer.parseInt(item.getTotal_quantity_item_order())));
            total_price.setText(decimalFormat.format(Integer.parseInt(item.getTotal_payment_item_order())));
        }
    }
}
