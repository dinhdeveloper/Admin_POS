package qtc.project.pos.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;

public class UserResponseModel extends BaseResponseModel {

    private String id_customer;
    @Nullable
    private String last_name;
    @Nullable
    private String first_name;
    @Nullable
    private String phone_number;

    @Nullable
    private String phone_contact;
    @Nullable
    private String address_personal;
    @Nullable
    private String img_default;
    @Nullable
    private String status;

    @Nullable
    private String birthday;

    @Nullable
    private String email;

    @Nullable
    private String sex;

    @Nullable
    private String login_type;

    @Nullable
    private String img_social_link;

    @Nullable
    private String user_social_id;

    @Nullable
    private String full_name;

    public String getId() {
        return id_customer;
    }

    @Nullable
    public String getLast_name() {
        return !TextUtils.isEmpty(last_name) ? last_name : "";
    }

    @Nullable
    public String getFirst_name() {
        return !TextUtils.isEmpty(first_name) ? first_name : "";
    }

    @Nullable
    public String getPhone_number() {
        return phone_number;
    }

    @Nullable
    public String getAddress_personal() {
        return address_personal;
    }

    @Nullable
    public String getAvatar() {
        return img_default;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    @Nullable
    public String getBirthday() {
        return !TextUtils.isEmpty(birthday) ? birthday : "";
    }

    @Nullable
    public String getEmail() {
        return !TextUtils.isEmpty(email) ? email : "";
    }

    public void setLast_name(@Nullable String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(@Nullable String first_name) {
        this.first_name = first_name;
    }

    public void setAddress_personal(@Nullable String address_personal) {
        this.address_personal = address_personal;
    }

    public void setAvatar(@Nullable String avatar) {
        this.img_default = avatar;
    }

    public void setBirthday(@Nullable String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getSex() {
        return sex;
    }

    @Nullable
    public String getPhone_contact() {
        return phone_contact;
    }

    @Nullable
    public String getLogin_type() {
        return login_type;
    }

    @Nullable
    public String getImg_social_link() {
        return img_social_link;
    }

    @Nullable
    public String getUser_social_id() {
        return user_social_id;
    }

    @Nullable
    public String getAccount_level() {
        return !TextUtils.isEmpty(account_level) ? account_level : "normal";
    }

    @Nullable
    private String id;
    @Nullable
    private String username;

    @Nullable
    private String account_level;

    @Nullable
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getIDManager() {
        return id;
    }

    @Nullable
    private RolePermissionModel[] role_permission;

    public boolean checkHaveRole(RoleType role) {

        if (role == null || TextUtils.isEmpty(role.id) || role.id.equalsIgnoreCase("null"))
            return false;

        if (role_permission != null && role_permission.length > 0) {
            for (RolePermissionModel item : role_permission) {
                if (role.isEqual(item)) {
                    return true;
                }
            }
        }

        return false;
    }

    public RolePermissionModel getDetailRolePermission(RoleType role) {
        if (role_permission != null && role_permission.length > 0) {
            for (RolePermissionModel item : role_permission) {
                if (role.isEqual(item)) {
                    return item;
                }
            }
        }

        return null;
    }


    public enum RoleType {
        ATTENDANCE_MANAGER("", "1"),
        EMPLOYEE_MANAGER("", "2"),
        PRODUCT_MANAGER("", "3"),
        NOTIFICATION_MANAGER("", "4"),
        ORDER_MANAGER("", "5"),
        REPORT_MANAGER("", "6"),
        TRANSACTION_MANAGER("", "7"),
        CUSTOMER_MANAGER("", "8"),
        TASK_MANAGER("", "9");


        private String ten_chuc_nang;
        private String id;

        RoleType(String ten_chuc_nang, String id) {
            this.ten_chuc_nang = ten_chuc_nang;
            this.id = id;
        }

        public boolean isEqual(RoleType role) {
            return role.id.equals(id);
        }

        public boolean isEqual(RolePermissionModel role) {
            if (role == null || TextUtils.isEmpty(role.getId()) || role.getId().equalsIgnoreCase("null"))
                return false;

            return role.getId().equals(id);
        }
    }

    @Nullable
    public String getFull_name() {
        return full_name;
    }
}
