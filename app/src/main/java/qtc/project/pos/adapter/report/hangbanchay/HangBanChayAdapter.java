package qtc.project.pos.adapter.report.hangbanchay;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.helper.AccentRemove;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.TopProductModel;

public class HangBanChayAdapter extends SuperAdapter<TopProductModel> {

    List<TopProductModel> list;

    public HangBanChayAdapter(Context context, List<TopProductModel> items) {
        super(context, items, R.layout.custom_item_sanpham_banchay);
        this.list = items;
        filter = new HangBanChayFilter();
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
            list.get(layoutPosition).setTop("TOP "+(layoutPosition+1));
            top_product.setText(item.getTop());
            name_product.setText(item.getProduct_name());
            quantity_product.setText(decimalFormat.format(Integer.parseInt(item.getTotal_quantity_item_order())));
            total_price.setText(decimalFormat.format(Integer.parseInt(item.getTotal_payment_item_order())));
        }
    }

    //filter

    private String filterString;
    private ArrayList<TopProductModel> listData = new ArrayList<>();
    private ArrayList<TopProductModel> listDataBackup = new ArrayList<>();
    private HangBanChayFilter filter;

    public HangBanChayFilter getFilter() {
        return filter;
    }

    public ArrayList<TopProductModel> getListData() {
        return listData;
    }

    public ArrayList<TopProductModel> getListDataBackup() {
        return listDataBackup;
    }

    public class HangBanChayFilter extends Filter {
        public HangBanChayFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (!TextUtils.isEmpty(constraint)) {
                filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (listData != null && listData.size() > 0) {
                    int count = listData.size();
                    List<TopProductModel> tempItems = new ArrayList<TopProductModel>();

                    // search exactly
                    for (int i = 0; i < count; i++) {
                        String name = listData.get(i).getProduct_name().toLowerCase();
                        if (name.contains(filterString)) {
                            tempItems.add(listData.get(i));
                        }
                    }
                    // search for no accent if no exactly result
                    filterString = AccentRemove.removeAccent(filterString);
                    if (tempItems.size() == 0) {
                        for (int i = 0; i < count; i++) {
                            String name = AccentRemove.removeAccent(listData.get(i).getProduct_name().toLowerCase());
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
                List<TopProductModel> listProductResult = (List<TopProductModel>) results.values;
                if (listProductResult != null && listProductResult.size() > 0) {
                    listData.addAll(listProductResult);
                }
            } else {
                listData.addAll(listDataBackup);
            }

            replaceAll(listData);
        }
    }

    private void replaceAll(ArrayList<TopProductModel> listData) {
        list.clear();
        list.addAll(listData);
        notifyDataSetChanged();
    }
}
