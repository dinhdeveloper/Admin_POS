package qtc.project.pos.adapter.product.doitra;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dialog.option.OptionModel;
import qtc.project.pos.model.PackageReturnModel;

public class ProductListDTHHAdapter extends SuperAdapter<OptionModel> {

    ProductListDTHHAdapterListener listener;

    public interface ProductListDTHHAdapterListener {
        void onClickItem(OptionModel model);
    }

    public void setListener(ProductListDTHHAdapterListener listener) {
        this.listener = listener;
    }

    public ProductListDTHHAdapter(Context context, List<OptionModel> items) {
        super(context, items, R.layout.custom_item_doi_tra_hang_hoa);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, OptionModel model) {
        RelativeLayout layout_item = holder.findViewById(R.id.layout_item);
        TextView ma_tra_hang = holder.findViewById(R.id.ma_tra_hang);
        TextView ma_nha_cung_ung = holder.findViewById(R.id.ma_nha_cung_ung);
        TextView sdt_nha_cung_ung = holder.findViewById(R.id.sdt_nha_cung_ung);
        TextView ngay_tra = holder.findViewById(R.id.ngay_tra);
        ImageView img_status = holder.findViewById(R.id.img_status);

        PackageReturnModel item = (PackageReturnModel)model.getDtaCustom();

        if (item!=null){
            ma_tra_hang.setText(item.getProduct_return_id_code());
            ma_nha_cung_ung.setText(item.getManufacturer_id());
            sdt_nha_cung_ung.setText(item.getManufacturer_phone_number());
            ngay_tra.setText(item.getProduct_return_return_date());
            if (item.getProduct_return_status().equals("Y")){
                img_status.setVisibility(View.VISIBLE);
                img_status.setImageResource(R.drawable.ic_hoantat);
            }else if (item.getProduct_return_status().equals("N")){
                img_status.setVisibility(View.VISIBLE);
                img_status.setImageResource(R.drawable.ic_chuatra);
            }

            layout_item.setOnClickListener(v -> {
                if (listener!=null){
                    listener.onClickItem(model);
                }
            });
        }
    }
}
