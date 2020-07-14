package qtc.project.pos.adapter.report.xuatnhapkho;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import qtc.project.pos.R;
import qtc.project.pos.model.ReportXuatNhapKhoModel;

public class BaoCaoXuatNhapKhoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<ReportXuatNhapKhoModel> list;
    private Context context;

    BaoCaoXuatNhapKhoAdapterListener listener;

    public interface BaoCaoXuatNhapKhoAdapterListener{
        void onClickItem(ReportXuatNhapKhoModel model);
    }

    public void setListener(BaoCaoXuatNhapKhoAdapterListener listener){
        this.listener = listener;
    }



    public BaoCaoXuatNhapKhoAdapter(Context context, List<ReportXuatNhapKhoModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_report_xuat_nhap_kho, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_report_xuat_nhap_kho, parent, false);
            return new HeaderViewHolder(itemView);
        } else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            headerViewHolder.id_stt.setText("STT");
            headerViewHolder.name_product.setText("Tên SP");
            headerViewHolder.nhap.setText("Nhập");
            headerViewHolder.tonkho.setText("Tồn");
            headerViewHolder.xuat.setText("Xuất");

            headerViewHolder.id_stt.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.name_product.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.nhap.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.tonkho.setTextColor(Color.parseColor("#726A95"));
            headerViewHolder.xuat.setTextColor(Color.parseColor("#726A95"));

            headerViewHolder.id_stt.setTypeface(headerViewHolder.id_stt.getTypeface(), Typeface.BOLD);
            headerViewHolder.name_product.setTypeface(headerViewHolder.name_product.getTypeface(), Typeface.BOLD);
            headerViewHolder.nhap.setTypeface(headerViewHolder.nhap.getTypeface(), Typeface.BOLD);
            headerViewHolder.tonkho.setTypeface(headerViewHolder.tonkho.getTypeface(), Typeface.BOLD);
            headerViewHolder.xuat.setTypeface(headerViewHolder.xuat.getTypeface(), Typeface.BOLD);

            headerViewHolder.id_stt.setGravity(Gravity.CENTER);
            headerViewHolder.name_product.setGravity(Gravity.CENTER);
            headerViewHolder.nhap.setGravity(Gravity.CENTER);
            headerViewHolder.tonkho.setGravity(Gravity.CENTER);
            headerViewHolder.xuat.setGravity(Gravity.CENTER);
        } else if (holder instanceof ItemViewHolder) {

            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.id_stt.setText(String.valueOf(position));
            itemViewHolder.name_product.setText(String.valueOf(list.get(position - 1).getProduct_name()));
            itemViewHolder.nhap.setText("+" + list.get(position-1).getStock_in());
            itemViewHolder.tonkho.setTextColor(Color.parseColor("#FF6D4C"));
            itemViewHolder.tonkho.setText(list.get(position-1).getStock());
            itemViewHolder.xuat.setText("-" + list.get(position-1).getStock_out());
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
        TextView name_product;
        TextView tonkho;
        TextView nhap;
        TextView xuat;

        public HeaderViewHolder(View holder) {
            super(holder);
            id_stt = holder.findViewById(R.id.id_stt);
            name_product = holder.findViewById(R.id.name_product);
            tonkho = holder.findViewById(R.id.tonkho);
            nhap = holder.findViewById(R.id.nhap);
            xuat = holder.findViewById(R.id.xuat);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView id_stt;
        TextView name_product;
        TextView tonkho;
        TextView nhap;
        TextView xuat;
        TableRow table_row;

        public ItemViewHolder(View holder) {
            super(holder);

            id_stt = holder.findViewById(R.id.id_stt);
            name_product = holder.findViewById(R.id.name_product);
            tonkho = holder.findViewById(R.id.tonkho);
            nhap = holder.findViewById(R.id.nhap);
            xuat = holder.findViewById(R.id.xuat);
            table_row = holder.findViewById(R.id.table_row);

            table_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                        listener.onClickItem(list.get(getLayoutPosition()-1));
                }
            });
        }
    }
}
