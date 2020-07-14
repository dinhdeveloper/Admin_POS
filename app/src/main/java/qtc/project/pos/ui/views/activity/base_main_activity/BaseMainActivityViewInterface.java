//package qtc.project.pos.ui.views.activity.base_main_activity;
//
//import android.support.annotation.IdRes;
//
//import b.laixuantam.myaarlibrary.base.BaseViewInterface;
//import b.laixuantam.myaarlibrary.widgets.tutorial.TutorialModel;
//import b.laixuantam.myaarlibrary.widgets.tutorial.TutorialView;
//import qtc.project.pos.activity.HomeActivityBackup;
//import qtc.project.pos.ui.views.activity.home_activity.HomeActivityViewCallback;
//
///**
// * Created by laixuantam on 7/6/17.
// */
//
//public interface BaseMainActivityViewInterface extends BaseViewInterface {
//    void init(BaseMainActivityViewCallback callback);
//
//    void init(HomeActivityBackup activity, HomeActivityViewCallback callback);
//
//    void showToolBar();
//
//    void hideToolBar();
//
//    void configLayoutBaseMainActivity();
//
//    void configLayoutBaseMainFragmentActivity();
//
//    void openDrawer();
//
//    void closeDrawer();
//
//    boolean isDrawerOpen();
//
//    void setNavigationItemSelected(@IdRes int id);
//
//    void clearNavigationItemSelected();
//
//    void reloadMenuNavigation();
//
//    void showTutorial(TutorialModel tutorial, TutorialView.TutorialListener listener);
//
//    void hideTutorial();
//
//    void showMenuNavigation();
//
//    void hideMenuNavigation();
//
//    void toggleMenuNavigation();
//
//    void setDrawerEnabled(boolean enabled);
//
//    void showBottomMenuBar();
//
//    void hideBottomMenuBar();
//
//    void setBadgeCart(int countItemOrder);
//
//    void setBadgeNumberBottomMenu(int pos, int value);
//
//    void setBottomMenuBarPossitionSelected(int pos);
//
//    void setBadgePromotionCart(int countItemOrder);
//}
