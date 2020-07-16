package qtc.project.pos.adapter.report.antoankho;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.helper.AccentRemove;
import qtc.project.pos.R;
import qtc.project.pos.model.AnToanKhoModel;


public class BaoCaoAnToanKhoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<AnToanKhoModel> list;
    private Context context;

    BaoCaoAnToanKhoAdapterListener listener;

    public interface BaoCaoAnToanKhoAdapterListener {
        void onClickItem(AnToanKhoModel model);
    }

    public void setListener(BaoCaoAnToanKhoAdapterListener listener) {
        this.listener = listener;
    }

    public BaoCaoAnToanKhoAdapter(Context context, List<AnToanKhoModel> list) {
        this.context = context;
        this.list = list;
        filter = new AnToanKhoFilter();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_bcxn_kho_detail, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_bcxn_kho_detail, parent, false);
            return new HeaderViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.id_stt.setVisibility(View.VISIBLE);
            headerViewHolder.id_stt.setText("STT");
            headerViewHolder.id_order.setText("Tên SP");
            headerViewHolder.tonkho.setText("SL");
            headerViewHolder.price.setText("An toàn(kho)");


            headerViewHolder.id_stt.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.id_order.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.tonkho.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.price.setTextColor(Color.parseColor("#726A95"));

            headerViewHolder.id_stt.setTypeface(headerViewHolder.id_stt.getTypeface(), Typeface.BOLD);
            headerViewHolder.id_order.setTypeface(headerViewHolder.id_order.getTypeface(), Typeface.BOLD);
            headerViewHolder.tonkho.setTypeface(headerViewHolder.tonkho.getTypeface(), Typeface.BOLD);
            headerViewHolder.price.setTypeface(headerViewHolder.price.getTypeface(), Typeface.BOLD);

            headerViewHolder.id_stt.setGravity(Gravity.CENTER);
            headerViewHolder.id_order.setGravity(Gravity.CENTER);
            headerViewHolder.tonkho.setGravity(Gravity.CENTER);
            headerViewHolder.price.setGravity(Gravity.CENTER);

        } else if (holder instanceof ItemViewHolder) {

            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            String pattern = "###,###.###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            itemViewHolder.id_stt.setVisibility(View.VISIBLE);
            itemViewHolder.id_stt.setText(String.valueOf(position));
            itemViewHolder.id_order.setText(list.get(position - 1).getProduct_name());
            itemViewHolder.tonkho.setTextColor(Color.parseColor("#FF6D4C"));

            //int soluongxuat = Integer.parseInt(list.get(position - 1).getQuantity_order()) - Integer.parseInt(list.get(position - 1).getQuantity_storage());

            itemViewHolder.tonkho.setText(decimalFormat.format(Integer.parseInt(list.get(position - 1).getTotal_quantity_storage())));
            itemViewHolder.price.setText(decimalFormat.format(Integer.parseInt(list.get(position - 1).getQuantity_safetystock())));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


    // getItemCount increasing the position to 1. This will be the row of header
    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView id_stt;
        TextView id_order;
        TextView tonkho;
        TextView price;

        public HeaderViewHolder(View holder) {
            super(holder);
            id_stt = holder.findViewById(R.id.id_stt);
            tonkho = holder.findViewById(R.id.tonkho);
            id_order = holder.findViewById(R.id.id_order);
            price = holder.findViewById(R.id.price);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView id_stt;
        TextView id_order;
        TextView tonkho;
        TextView price;
        TableRow table_row;

        public ItemViewHolder(View holder) {
            super(holder);

            id_stt = holder.findViewById(R.id.id_stt);
            tonkho = holder.findViewById(R.id.tonkho);
            id_order = holder.findViewById(R.id.id_order);
            price = holder.findViewById(R.id.price);
            table_row = holder.findViewById(R.id.table_row);

        }
    }

    private String filterString;
    private ArrayList<AnToanKhoModel> listData = new ArrayList<>();
    private ArrayList<AnToanKhoModel> listDataBackup = new ArrayList<>();
    private AnToanKhoFilter filter;

    public AnToanKhoFilter getFilter() {
        return filter;
    }

    public ArrayList<AnToanKhoModel> getListData() {
        return listData;
    }

    public ArrayList<AnToanKhoModel> getListDataBackup() {
        return listDataBackup;
    }

    public class AnToanKhoFilter extends Filter {
        public AnToanKhoFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (!TextUtils.isEmpty(constraint)) {
                filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (listData != null && listData.size() > 0) {
                    int count = listData.size();
                    List<AnToanKhoModel> tempItems = new ArrayList<AnToanKhoModel>();

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
                List<AnToanKhoModel> listProductResult = (List<AnToanKhoModel>) results.values;
                if (listProductResult != null && listProductResult.size() > 0) {
                    listData.addAll(listProductResult);
                }
            } else {
                listData.addAll(listDataBackup);
            }

            replaceAll(listData);
        }
    }

    private void replaceAll(ArrayList<AnToanKhoModel> listData) {
        list.clear();
        list.addAll(listData);
        notifyDataSetChanged();
    }


}