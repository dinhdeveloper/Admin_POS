package qtc.project.pos.ui.views.activity.home_activity;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.fragment.account.information.FragmentThongTinUser;
import qtc.project.pos.model.EmployeeModel;

public class HomeActivityView extends BaseView<HomeActivityView.UIContainer> implements HomeActivityViewInterface {

    HomeActivity activity;
    HomeActivityViewCallback callback;
    Fragment fragment;


    @Override
    public void init(HomeActivity activity, HomeActivityViewCallback callback) {
        this.callback = callback;
        this.activity = activity;

        KeyboardUtils.setupUI(getView(),activity);

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

    @Override
    public void updatePopup() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã cập nhật thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void toggleNav() {
        if (isDrawerOpen()){
            closeDrawer();
        }else{
            openDrawer();
        }
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
        LinearLayout thongtin_taikhoan = headerview.findViewById(R.id.thongtin_taikhoan);
        LinearLayout thaydoi_mk = headerview.findViewById(R.id.thaydoi_mk);
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
        //dang xuat
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

        //thong tin tai khoan
        thongtin_taikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity!=null)
                    activity.replaceFragment(new FragmentThongTinUser(),true,null);
                if (isDrawerOpen()) {
                    ui.drawer_layout.closeDrawer(GravityCompat.START);
                }
            }
        });

        //thay doi mat khau:
        thaydoi_mk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.custom_popup_change_pass_username, null);
                EditText old_pass = popupView.findViewById(R.id.old_pass);
                EditText new_pass = popupView.findViewById(R.id.new_pass);
                EditText re_new_pass = popupView.findViewById(R.id.re_new_pass);
                LinearLayout layout_update = popupView.findViewById(R.id.layout_update);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(popupView);
                AlertDialog dialog = alert.create();
                //dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                layout_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (new_pass.getText().toString().equals(re_new_pass.getText().toString())){
                            if (!TextUtils.isEmpty(old_pass.getText()) && !TextUtils.isEmpty(new_pass.getText())) {
                                callback.onClickLogin(old_pass.getText()
                                        .toString(), new_pass
                                        .getText()
                                        .toString(),employeeModel.getId());
                            } else if (TextUtils.isEmpty(old_pass.getText())) {
                                old_pass.setError("Nhập mật khẩu cũ");
                                old_pass.requestFocus();
                            } else {
                                new_pass.setError("Nhập mật khẩu mới");
                                new_pass.requestFocus();
                            }
                        }else {
                            Toast.makeText(getContext(), "Bạn nhập mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



//        if (!TextUtils.isEmpty(old_pass.getText()) && !TextUtils.isEmpty(new_pass.getText())){
//            callback.onClickLogin(old_pass.getText()
//                    .toString(), new_pass
//                    .getText()
//                    .toString());
//        } else if (TextUtils.isEmpty(old_pass.getText())) {
//            old_pass.setError("Nhập mật khẩu cũ");
//            old_pass.requestFocus();
//        } else {
//            new_pass.setError("Mật khẩu mới");
//            new_pass.requestFocus();
//        }
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
