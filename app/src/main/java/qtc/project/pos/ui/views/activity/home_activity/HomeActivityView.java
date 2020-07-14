package qtc.project.pos.ui.views.activity.home_activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.base.BaseFragmentActivity;
import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.customer.FragmentCustomer;
import qtc.project.pos.fragment.history.FragmentOrder;
import qtc.project.pos.fragment.home.FragmentHome;
import qtc.project.pos.fragment.levelcustomer.FragmentLevelCustomer;
import qtc.project.pos.fragment.product.FragmentProduct;
import qtc.project.pos.helper.SharePrefs;
import qtc.project.pos.model.EmployeeModel;

public class HomeActivityView extends BaseView<HomeActivityView.UIContainer> implements HomeActivityViewInterface {

    HomeActivity activity;
    HomeActivityViewCallback callback;
    Fragment fragment;


    @Override
    public void init(HomeActivity activity, HomeActivityViewCallback callback) {
        this.callback = callback;
        this.activity = activity;

        addEventDragLayout();
        addEventsHeaderNavigationLeft();
    }

    @Override
    public void openDrawer() {
        AppUtils.hideKeyBoard(getView());
        ui.drawer_layout.openDrawer(GravityCompat.START);
    }

    @Override
    public void closeDrawer() {
        if (isDrawerOpen()) {
            ui.drawer_layout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    public boolean isDrawerOpen() {
        return ui.drawer_layout.isDrawerOpen(GravityCompat.START);
    }


    private void addEventDragLayout() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.drawer_layout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void addEventsHeaderNavigationLeft() {
        View headerview = ui.nav_view.getHeaderView(0);
        LinearLayout layoutHome = headerview.findViewById(R.id.layoutHome);
        LinearLayout log_out = headerview.findViewById(R.id.log_out);
        ImageView image_em = headerview.findViewById(R.id.image_em);

        EmployeeModel employeeModel = AppProvider.getPreferences().getUserModel();

        TextView full_name_employee = headerview.findViewById(R.id.full_name_employee);
        TextView level_employee = headerview.findViewById(R.id.level_employee);

        full_name_employee.setText(employeeModel.getFull_name());

        if (employeeModel.getLevel().equals("2")){
            level_employee.setText("Admin");
        }
        else if (employeeModel.getLevel().equals("1")){
            level_employee.setText("Nhân Viên");
        }
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.alert_dialog_waiting, null);
                TextView title_text = popupView.findViewById(R.id.title_text);
                TextView content_text = popupView.findViewById(R.id.content_text);
                Button cancel_button = popupView.findViewById(R.id.cancel_button);
                Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

                title_text.setText("Cảnh báo");
                content_text.setText("Bạn có muốn đăng xuất tài khoản này không?");

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(popupView);
                AlertDialog dialog = alert.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


                cancel_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                custom_confirm_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback!=null){
                            callback.logOut();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

//        LinearLayout layoutHistory = headerview.findViewById(R.id.layoutHistory);
//        LinearLayout layoutProduct = headerview.findViewById(R.id.layoutProduct);
//        LinearLayout layoutCustomer = headerview.findViewById(R.id.layoutCustomer);
//        LinearLayout layoutLevelCus = headerview.findViewById(R.id.layoutLevelCus);
//        LinearLayout layoutLogout = headerview.findViewById(R.id.layoutLogout);
//
//        layoutHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.onClickItemNav(new FragmentHome());
//                ui.drawer_layout.closeDrawer(GravityCompat.START);
//            }
//        });
//        layoutHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.onClickItemNav(new FragmentOrder());
//                ui.drawer_layout.closeDrawer(GravityCompat.START);
//            }
//        });
//        layoutProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.onClickItemNav(new FragmentProduct());
//                ui.drawer_layout.closeDrawer(GravityCompat.START);
//            }
//        });
//        layoutCustomer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.onClickItemNav(new FragmentCustomer());
//                ui.drawer_layout.closeDrawer(GravityCompat.START);
//            }
//        });
//
//        layoutLevelCus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.onClickItemNav(new FragmentLevelCustomer());
//                ui.drawer_layout.closeDrawer(GravityCompat.START);
//            }
//        });
//        layoutLogout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("Đăng xuất?")
//                        .setContentText("Bạn có chắc chắn thoát khỏi thiết bị này không?")
//                        .setConfirmText("Đồng ý!")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//                            }
//                        })
//                        .setCancelButton("Từ chối", new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//                            }
//                        })
//                        .show();
//            }
//        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new HomeActivityView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.activity_home;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.drawer_layout)
        public DrawerLayout drawer_layout;

        @UiElement(R.id.nav_view)
        public NavigationView nav_view;

        @UiElement(R.id.content_frame)
        public FrameLayout frameLayout;
    }
}
