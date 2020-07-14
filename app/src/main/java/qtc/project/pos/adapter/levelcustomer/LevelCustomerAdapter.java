package qtc.project.pos.adapter.levelcustomer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.dependency.AppProvider;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.LevelCustomerModel;

public class LevelCustomerAdapter extends SuperAdapter<LevelCustomerModel> {

    private CustomerLevelAdapterListener listener;

    public LevelCustomerAdapter(Context context, List<LevelCustomerModel> items) {
        super(context, items, R.layout.custom_level_customer);
    }

    public interface CustomerLevelAdapterListener{
        void onItemClick(LevelCustomerModel model);
    }

    public void setListener(CustomerLevelAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, LevelCustomerModel item) {
        ImageView imageLevel = holder.findViewById(R.id.imageLevel);
        TextView description = holder.findViewById(R.id.description);
        TextView nameLevel = holder.findViewById(R.id.nameLevel);
        TextView discount = holder.findViewById(R.id.discount);
        LinearLayout layoutLevelCus = holder.findViewById(R.id.layoutLevelCus);

        AppProvider.getImageHelper().displayImage(item.getImage(), imageLevel, null, R.drawable.imageloading);
        description.setText(item.getDescription());
        nameLevel.setText(item.getName());
        discount.setText("Có "+item.getDiscount()+" người hiển thị.");

        layoutLevelCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener !=null){
                    listener.onItemClick(item);
                }
            }
        });
    }
}
