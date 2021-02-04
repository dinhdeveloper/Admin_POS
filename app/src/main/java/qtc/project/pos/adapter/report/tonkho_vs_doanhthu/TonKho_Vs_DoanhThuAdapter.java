package qtc.project.pos.adapter.report.tonkho_vs_doanhthu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

//import b.laixuantam.myaarlibrary.helper.NumericFormater;
import b.laixuantam.myaarlibrary.helper.CurrencyFormater;
import qtc.project.pos.R;
import qtc.project.pos.model.Stock_Income_Model;

public class TonKho_Vs_DoanhThuAdapter extends RecyclerView.Adapter<TonKho_Vs_DoanhThuAdapter.ViewHolder> {

    List<Stock_Income_Model> list;
    Context context;

    public TonKho_Vs_DoanhThuAdapter(List<Stock_Income_Model> modelList, Context context) {
        this.list = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_chart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Stock_Income_Model model = list.get(position);
            holder.thang.setText(model.getTitle());

            LinearLayout.LayoutParams paramStock = new LinearLayout.LayoutParams(
                    40,
                    Integer.parseInt(list.get(position).getHeightStock())
            );
            holder.layout_one.setLayoutParams(paramStock);

            LinearLayout.LayoutParams paramIncome = new LinearLayout.LayoutParams(
                    40,
                    Integer.parseInt(list.get(position).getHeightIncome())
            );
            holder.layout_two.setLayoutParams(paramIncome);


            long valueStock = Long.valueOf(list.get(position).getValueStock());
            long valueIncome = Long.valueOf(list.get(position).getValueIncome());

            String valueStockF = CurrencyFormater.getStringPrice(valueStock, CurrencyFormater.SHORT_PRICE);
            String valueIncomeF = CurrencyFormater.getStringPrice(valueIncome, CurrencyFormater.SHORT_PRICE);

            holder.id_one.setText(valueStockF);
            holder.id_two.setText(valueIncomeF);
        } catch (Exception e) {
            Log.e("Ex", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout_two;
        LinearLayout layout_one;
        TextView thang;
        TextView id_two;
        TextView id_one;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_one = itemView.findViewById(R.id.layout_one);
            layout_two = itemView.findViewById(R.id.layout_two);
            thang = itemView.findViewById(R.id.thang);
            id_two = itemView.findViewById(R.id.id_two);
            id_one = itemView.findViewById(R.id.id_one);
        }
    }
}