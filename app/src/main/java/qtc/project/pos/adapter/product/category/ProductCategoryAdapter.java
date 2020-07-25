package qtc.project.pos.adapter.product.category;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.helper.AccentRemove;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.ProductCategoryModel;

public class ProductCategoryAdapter extends SuperAdapter<ProductCategoryModel> {

    ProductCategoryAdapterListener listener;
    List<ProductCategoryModel> lists;

    public ProductCategoryAdapter(Context context, List<ProductCategoryModel> items) {
        super(context, items, R.layout.custom_item_category_product);
        this.lists = items;
        filter = new ProductCategoryFilter();
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

    private String filterString;
    private ArrayList<ProductCategoryModel> listData = new ArrayList<>();
    private ArrayList<ProductCategoryModel> listDataBackup = new ArrayList<>();
    private ProductCategoryFilter filter;

    public ProductCategoryFilter getFilter() {
        return filter;
    }

    public ArrayList<ProductCategoryModel> getListData() {
        return listData;
    }

    public ArrayList<ProductCategoryModel> getListDataBackup() {
        return listDataBackup;
    }

    public class ProductCategoryFilter extends Filter {
        public ProductCategoryFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (!TextUtils.isEmpty(constraint)) {
                filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (listData != null && listData.size() > 0) {
                    int count = listData.size();
                    List<ProductCategoryModel> tempItems = new ArrayList<ProductCategoryModel>();

                    // search exactly
                    for (int i = 0; i < count; i++) {
                        String name = listData.get(i).getName().toLowerCase();
                        if (name.contains(filterString)) {
                            tempItems.add(listData.get(i));
                        }
                    }
                    // search for no accent if no exactly result
                    filterString = AccentRemove.removeAccent(filterString);
                    if (tempItems.size() == 0) {
                        for (int i = 0; i < count; i++) {
                            String name = AccentRemove.removeAccent(listData.get(i).getName().toLowerCase());
                            if (name.contains(filterString)) {
                                tempItems.add(listData.get(i));
                            }
                        }
                    }
                    results.values = tempItems;
                    results.count = tempItems.size();
                    return results;
                } else {
                    return null;
                }
            } else {
                filterString = "";
                return null;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listData.clear();
            if (results != null) {
                List<ProductCategoryModel> listProductResult = (List<ProductCategoryModel>) results.values;
                if (listProductResult != null && listProductResult.size() > 0) {
                    listData.addAll(listProductResult);
                }
            } else {
                listData.addAll(listDataBackup);
            }

            replaceAll(listData);
        }
    }

    private void replaceAll(ArrayList<ProductCategoryModel> listData) {
        lists.clear();
        lists.addAll(listData);
        notifyDataSetChanged();
    }
}
