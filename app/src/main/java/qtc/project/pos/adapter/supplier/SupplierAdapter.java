package qtc.project.pos.adapter.supplier;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.adapter.product.ProductListAdapter;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.SupplierModel;

public class SupplierAdapter  extends SuperAdapter<SupplierModel> {

    SupplierAdapterListener listener;

    public interface  SupplierAdapterListener{
        void setOnClick(SupplierModel model);
        //void setOnClickToDetail(SupplierModel model);
    }
    public void setListener(SupplierAdapterListener listener){
        this.listener = listener;
    }


    public SupplierAdapter(Context context, List<SupplierModel> items) {
        super(context, items, R.layout.custom_item_supplier);
    }


    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, SupplierModel item) {
        LinearLayout layout_sup = holder.findViewById(R.id.layout_sup);
        TextView name_sipplier = holder.findViewById(R.id.name_sipplier);
        TextView id_supplier = holder.findViewById(R.id.id_supplier);
        TextView phone_supplier = holder.findViewById(R.id.phone_supplier);

        if (item!=null){
            name_sipplier.setText(item.getName());
            id_supplier.setText(item.getId_code());
            phone_supplier.setText(item.getPhone_number());
            layout_sup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                        listener.setOnClick(item);
                }
            });
        }
    }
}
