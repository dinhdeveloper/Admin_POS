package qtc.project.pos.ui.views.fragment.account.profile_manager;

public interface FragmentProfileManagerViewCallback {
    void onClickBackHeader();

    void showDialogSelecteImage();

    void showDialogTakePicture();

    void onRequestUpdateUserProfile(String user_avata, String userFirstName, String userLastName, String userAddress, String email, String phone);

}
