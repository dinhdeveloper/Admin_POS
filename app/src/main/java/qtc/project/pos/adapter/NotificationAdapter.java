package qtc.project.pos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.NotificationModel;

public class NotificationAdapter extends SuperAdapter<NotificationModel> {

    private NotificationAdapterListener listener;

    public NotificationAdapter(Context context, List<NotificationModel> items) {
        super(context, items, R.layout.row_item_notification);
    }

    public interface NotificationAdapterListener {
        void onItemSelected(NotificationModel item, int pos);
    }

    public void setListener(NotificationAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final NotificationModel item) {

        View rLayoutItem = holder.findViewById(R.id.rLayoutItem);
        View loading_view_notification_imv = holder.findViewById(R.id.loading_view_notification_imv);

        ImageView imvNotificaiton = holder.findViewById(R.id.imvNotificaiton);

        TextView tvTitleNotification = holder.findViewById(R.id.tvTitleNotification);
        TextView tvNotificationDescription = holder.findViewById(R.id.tvNotificationDescription);
        TextView tvNotificationDateCreate = holder.findViewById(R.id.tvNotificationDateCreate);

        tvTitleNotification.setText(item.getNotify_title());
        tvNotificationDescription.setText(item.getMessage());
        tvNotificationDateCreate.setText(item.getCreated_at());

        String categoryImageLink = Consts.HOST_API + item.getImg_photo();
        AppProvider.getImageHelper().displayImage(categoryImageLink, imvNotificaiton, loading_view_notification_imv, R.drawable.no_image_full);

        rLayoutItem.setOnClickListener(view -> {
            if (listener != null)
                listener.onItemSelected(item, layoutPosition);
        });

        if (item.getStatus_view().equalsIgnoreCase("Y")){
            tvTitleNotification.setTextColor(ContextCompat.getColor(getContext(),R.color.gray_light_opacity));
            tvNotificationDescription.setTextColor(ContextCompat.getColor(getContext(),R.color.gray_light_opacity));
            tvNotificationDateCreate.setTextColor(ContextCompat.getColor(getContext(),R.color.gray_light_opacity));
        }else{
            tvTitleNotification.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            tvNotificationDescription.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            tvNotificationDateCreate.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
        }
    }
}

