package qtc.project.pos.model;

public class RolePermissionModel extends BaseResponseModel {

    private String id;
    private String permission;
    private String description;

    public String getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }
}
