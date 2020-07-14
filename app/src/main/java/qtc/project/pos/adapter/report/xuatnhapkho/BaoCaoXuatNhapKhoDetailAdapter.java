package qtc.project.pos.adapter.report.xuatnhapkho;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import qtc.project.pos.R;
import qtc.project.pos.model.PackageInfoModel;


public class BaoCaoXuatNhapKhoDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<PackageInfoModel> list;
    private Context context;

    BaoCaoXuatNhapKhoDetailAdapterListener listener;

    public interface BaoCaoXuatNhapKhoDetailAdapterListener{
        void onClickItem(PackageInfoModel model);
    }

    public void setListener(BaoCaoXuatNhapKhoDetailAdapterListener listener){
        this.listener = listener;
    }

    public BaoCaoXuatNhapKhoDetailAdapter(Context context, List<PackageInfoModel> list) {
        this.context = context;
        this.list = list;
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

            headerViewHolder.id_order.setText("Mã lô");
            headerViewHolder.tonkho.setText("Tồn kho");
            headerViewHolder.price.setText("Giá trị");


            headerViewHolder.id_order.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.tonkho.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.price.setTextColor(Color.parseColor("#726A95"));

            headerViewHolder.id_order.setTypeface(headerViewHolder.id_order.getTypeface(), Typeface.BOLD);
            headerViewHolder.tonkho.setTypeface(headerViewHolder.tonkho.getTypeface(), Typeface.BOLD);
            headerViewHolder.price.setTypeface(headerViewHolder.price.getTypeface(), Typeface.BOLD);

            headerViewHolder.id_order.setGravity(Gravity.CENTER);
            headerViewHolder.tonkho.setGravity(Gravity.CENTER);
            headerViewHolder.price.setGravity(Gravity.CENTER);

        } else if (holder instanceof ItemViewHolder) {

            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            String pattern = "###,###.###";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);


            itemViewHolder.id_order.setText(list.get(position-1).getPack_id_code());
            itemViewHolder.tonkho.setTextColor(Color.parseColor("#FF6D4C"));
            itemViewHolder.tonkho.setText(list.get(position-1).getQuantity_storage());
            itemViewHolder.price.setText(decimalFormat.format(Integer.parseInt(list.get(position-1).getImport_price())));
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
        return list.size()+1;
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

//            table_row.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener!=null)
//                        listener.onClickItem(list.get(getLayoutPosition()));
//                }
//            });
        }
    }
}