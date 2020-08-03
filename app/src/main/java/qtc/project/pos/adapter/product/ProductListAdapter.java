package qtc.project.pos.adapter.product;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.ProductListModel;

public class ProductListAdapter extends SuperAdapter<ProductListModel> {

    ProductListAdapterListener listener;

    public ProductListAdapter(Context context, List<ProductListModel> items) {
        super(context, items, R.layout.custom_item_product_in_product_list);
    }

    public interface  ProductListAdapterListener{
        void setOnClick(ProductListModel model);
        void onRequestLoadMoreProduct();
    }
    public void setListener(ProductListAdapterListener listener){
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ProductListModel item) {
        LinearLayout layout_item = holder.findViewById(R.id.layout_item);
        ImageView image_product = holder.findViewById(R.id.image_product);
        TextView id_product = holder.findViewById(R.id.id_product);
        TextView name_product = holder.findViewById(R.id.name_product);

        AppProvider.getImageHelper().displayImage(Consts.HOST_API+item.getImage(),image_product,null,R.drawable.imageloading);
        id_product.setText(item.getId_code());
        name_product.setText(item.getName());

        layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.setOnClick(item);
                }
            }
        });
        if (layoutPosition == getCount() - 1) {
            if (listener != null)
                listener.onRequestLoadMoreProduct();
        }
    }
}
