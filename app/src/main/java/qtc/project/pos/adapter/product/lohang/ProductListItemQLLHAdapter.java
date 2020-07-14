package qtc.project.pos.adapter.product.lohang;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;

public class ProductListItemQLLHAdapter extends SuperAdapter<PackageInfoModel> {

    ProductListItemQLLHAdapterListener listener;
    String name;
    String  id_product;
    public ProductListItemQLLHAdapter(Context context, List<PackageInfoModel> items,String name,String id_product) {
        super(context, items, R.layout.custom_item_list_product_on_qllh);
        this.name = name;
        this.id_product = id_product;
    }

    public interface ProductListItemQLLHAdapterListener {
        void setOnClick(PackageInfoModel model);
        void sentDataOnClick(PackageInfoModel infoModel,String name, String id_product);
    }

    public void setListener(ProductListItemQLLHAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PackageInfoModel item) {
        TextView date_create = holder.findViewById(R.id.date_create);
        TextView date_end = holder.findViewById(R.id.date_end);
        TextView price_sale = holder.findViewById(R.id.price_sale);
        TextView price_buy = holder.findViewById(R.id.price_buy);
        LinearLayout layout_item = holder.findViewById(R.id.layout_item);

        if (item != null) {
            String pattern = "###,###.###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            date_create.setText(item.getImport_date());
            date_end.setText(item.getExpiry_date());
            price_buy.setText(decimalFormat.format(Integer.parseInt(item.getImport_price())) + " VNĐ");
            price_sale.setText(decimalFormat.format(Integer.parseInt(item.getSale_price())) + " VNĐ");

            layout_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.sentDataOnClick(item,name,id_product);
                    }
                }
            });
        }
    }
}