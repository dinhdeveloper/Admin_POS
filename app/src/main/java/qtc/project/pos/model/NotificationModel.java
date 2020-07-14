package qtc.project.pos.model;

import android.support.annotation.Nullable;

public class NotificationModel extends BaseResponseModel {

    private String id;
    @Nullable
    private String type_sender;

    @Nullable
    private String id_sender;

    @Nullable
    private String created_at;

    @Nullable
    private String updated_at;

    @Nullable
    private String notify_code;

    @Nullable
    private String notify_title;

    @Nullable
    private String notify_message;

    @Nullable
    private String img_photo;

    @Nullable
    private String id_industrial;

    @Nullable
    private String id_company;

    @Nullable
    private String id_customer;

    @Nullable
    private String notify_action;

    @Nullable
    private String status_view;

    public String getId() {
        return id;
    }

    @Nullable
    public String getType_sender() {
        return type_sender;
    }

    @Nullable
    public String getId_sender() {
        return id_sender;
    }

    @Nullable
    public String getCreated_at() {
        return created_at;
    }

    @Nullable
    public String getUpdated_at() {
        return updated_at;
    }

    @Nullable
    public String getNotify_code() {
        return notify_code;
    }

    @Nullable
    public String getNotify_title() {
        return notify_title;
    }

    @Nullable
    public String getMessage() {
        return notify_message;
    }

    @Nullable
    public String getImg_photo() {
        return img_photo;
    }

    @Nullable
    public String getId_industrial() {
        return id_industrial;
    }

    @Nullable
    public String getId_company() {
        return id_company;
    }

    @Nullable
    public String getId_customer() {
        return id_customer;
    }

    @Nullable
    public String getStatus_view() {
        return status_view;
    }

    public void setStatus_view(@Nullable String status_view) {
        this.status_view = status_view;
    }

    @Nullable
    public String getNotify_action() {
        return notify_action;
    }

    @Nullable
    public String getNotify_message() {
        return notify_message;
    }

    public void setImg_photo(@Nullable String img_photo) {
        this.img_photo = img_photo;
    }
}
