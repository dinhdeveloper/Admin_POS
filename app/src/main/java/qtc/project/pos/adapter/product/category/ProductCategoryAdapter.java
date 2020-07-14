package qtc.project.pos.adapter.product.category;

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
import qtc.project.pos.model.ProductCategoryModel;

public class ProductCategoryAdapter extends SuperAdapter<ProductCategoryModel> {

    ProductCategoryAdapterListener listener;

    public ProductCategoryAdapter(Context context, List<ProductCategoryModel> items) {
        super(context, items, R.layout.custom_item_category_product);
    }

    public interface ProductCategoryAdapterListener {
        void onClickItem(ProductCategoryModel model);
    }

    public void setListener(ProductCategoryAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, ProductCategoryModel item) {
        LinearLayout item_category_product = holder.findViewById(R.id.item_category_product);
        ImageView image_product = holder.findViewById(R.id.image_product);
        TextView name_product_category = holder.findViewById(R.id.name_product_category);
        TextView description_product = holder.findViewById(R.id.description_product);

        AppProvider.getImageHelper().displayImage(Consts.HOST_API+item.getImage(), image_product, null, R.drawable.imageloading);
        name_product_category.setText(item.getName());
        description_product.setText(item.getDescription());

        item_category_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onClickItem(item);
                }
            }
        });
    }
}
