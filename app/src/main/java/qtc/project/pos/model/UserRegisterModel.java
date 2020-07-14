package qtc.project.pos.model;

import java.io.Serializable;

public class UserRegisterModel implements Serializable {

    private String userPhone;
    private String userName;
    private String userPassword;
    private String userAddress;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String img_social_link;
    private String user_social_id;
    private String type_login;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getImg_social_link() {
        return img_social_link;
    }

    public void setImg_social_link(String img_social_link) {
        this.img_social_link = img_social_link;
    }

    public String getUser_social_id() {
        return user_social_id;
    }

    public void setUser_social_id(String user_social_id) {
        this.user_social_id = user_social_id;
    }

    public String getType_login() {
        return type_login;
    }

    public void setType_login(String type_login) {
        this.type_login = type_login;
    }
}
