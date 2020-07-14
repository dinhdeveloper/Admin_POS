//package qtc.project.pos.fragment.account.menu;
//
//import b.laixuantam.myaarlibrary.base.BaseFragment;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.ui.views.fragment.account.menu.FragmentAccountMenuView;
//import qtc.project.pos.ui.views.fragment.account.menu.FragmentAccountMenuViewCallback;
//import qtc.project.pos.ui.views.fragment.account.menu.FragmentAccountMenuViewInterface;
//
//public class FragmentAccountMenu extends BaseFragment<FragmentAccountMenuViewInterface, BaseParameters> implements FragmentAccountMenuViewCallback {
//
//    private HomeActivityBackup activity;
//
//    @Override
//    protected void initialize() {
//        view.init(this);
//        activity = (HomeActivityBackup) getActivity();
//
//        boolean userHasLogin = AppProvider.getPreferences().checkLoginStatus();
//
//        if (!userHasLogin) {
//            view.showLayoutLoginRequest();
//        }
//    }
//
//    @Override
//    public void changeToFragmentLogin() {
//        if (activity != null)
//            activity.changeToFragmentLogin();
//    }
//
//    @Override
//    protected FragmentAccountMenuViewInterface getViewInstance() {
//        return new FragmentAccountMenuView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//    @Override
//    public void onClickChangeToFragmentProfileManager() {
//        if (activity != null) {
//            activity.changeToFragmentProfileManager();
//        }
//    }
//
//    @Override
//    public void onClickChangeToFragmentChangePassword() {
//        if (activity != null) {
//            activity.changeToFragmentPasswordManager();
//        }
//    }
//
//    @Override
//    public void onClickLogout() {
//        if (activity != null)
//            activity.doLogout();
//    }
//
//}
