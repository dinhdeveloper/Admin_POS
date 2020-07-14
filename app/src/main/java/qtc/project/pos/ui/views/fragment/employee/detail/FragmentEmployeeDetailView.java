package qtc.project.pos.ui.views.fragment.employee.detail;

import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.employee.LevelEmployeeChooseAdapter;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.model.LevelEmployeeModel;

public class FragmentEmployeeDetailView extends BaseView<FragmentEmployeeDetailView.UIContainer> implements FragmentEmployeeDetailViewInterface {
    HomeActivity activity;
    FragmentEmployeeDetailViewCallback callback;

    DatePickerDialog picker;
    String id_level=null;

    @Override
    public void init(HomeActivity activity, FragmentEmployeeDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        
        onClick();
    }

    private void onClick() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        //back
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });
        //choose day
        ui.image_choose_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                ui.birthday_employee.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //choose level
        ui.choose_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.callLevelEmployee();
                }
            }
        });
    }

    @Override
    public void sentDataToView(EmployeeModel model) {
        try{
            if (model != null) {
                ui.user_name.setText(model.getId_code());
                ui.name_employee.setText(model.getFull_name());
                ui.full_name_header.setText(model.getFull_name());
                ui.email_employee.setText(model.getEmail());
                ui.phone_employee.setText(model.getPhone_number());
                ui.address_employee.setText(model.getAddress());
                ui.birthday_employee.setText(model.getBirthday());
                if (model.getLevel().equals("1")){
                    ui.name_level_employee.setText("Nhân Viên");
                }else if (model.getLevel().equals("2")){
                    ui.name_level_employee.setText("Admin");
                }
                if (model.getStatus().equals("Y")){
                    ui.status_employee.setChecked(true);
                }else if (model.getStatus().equals("N")){
                    ui.status_employee.setChecked(false);
                }

                //update
                ui.layout_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EmployeeModel employeeModel = new EmployeeModel();
                        employeeModel.setId(model.getId());
                        employeeModel.setId_code(ui.user_name.getText().toString());
                        employeeModel.setFull_name(ui.name_employee.getText().toString());
                        employeeModel.setEmail(ui.email_employee.getText().toString());
                        employeeModel.setPhone_number(ui.phone_employee.getText().toString());
                        employeeModel.setAddress(ui.address_employee.getText().toString());
                        employeeModel.setBirthday(ui.birthday_employee.getText().toString());
                        employeeModel.setLevel(id_level);
                        if (ui.status_employee.isChecked() ==true){
                            employeeModel.setStatus("Y");
                        }else if (ui.status_employee.isChecked() ==false){
                            employeeModel.setStatus("N");
                        }

                        if (callback!=null)
                            callback.updateEmployee(employeeModel);
                    }
                });

                //delete
                ui.layout_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = activity.getLayoutInflater();
                        View popupView = layoutInflater.inflate(R.layout.alert_dialog_waiting, null);
                        TextView title_text = popupView.findViewById(R.id.title_text);
                        TextView content_text = popupView.findViewById(R.id.content_text);
                        Button cancel_button = popupView.findViewById(R.id.cancel_button);
                        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

                        title_text.setText("Cảnh báo");
                        content_text.setText("Bạn có muốn xóa khách hàng này không?");

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
                                if (callback != null)
                                    callback.deleteEmployee(model);
                                dialog.dismiss();
                            }
                        });
                    }
                });

                //resetpass
                ui.reset_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LayoutInflater layoutInflater = activity.getLayoutInflater();
                        View popupView = layoutInflater.inflate(R.layout.custom_popup_change_password_employee, null);
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
                                if (new_pass.getText().toString() == re_new_pass.getText().toString()){
                                    if (callback!=null){
                                        callback.reSetPass(model.getId() ,re_new_pass.getText().toString());
                                    }
                                }else {
                                    Toast.makeText(activity, "Mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        }catch (Exception e){
            Log.e("Exception",e.getMessage());
        }
    }

    @Override
    public void sendLevelEmployee(ArrayList<LevelEmployeeModel> arrayList) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.custom_popup_choose_level_customer, null);
        TextView choose_item = popupView.findViewById(R.id.choose_item);
        TextView cancel = popupView.findViewById(R.id.cancel);
        RecyclerView recycler_view_list = popupView.findViewById(R.id.recycler_view_list);

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        //dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        LevelEmployeeChooseAdapter chooseAdapter = new LevelEmployeeChooseAdapter(activity,arrayList);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recycler_view_list.setAdapter(chooseAdapter);

        chooseAdapter.setOnItemClickListener(new LevelEmployeeChooseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(LevelEmployeeModel model) {
                choose_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_level = model.getId();
                        ui.name_level_employee.setText(model.getName());
                        dialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public void showDiaLogUpdate() {
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
    public void showDiaLogDelete() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã xóa sản phẩm thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.onBackProgress();
                    dialog.dismiss();
                }

            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentEmployeeDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_employee_detail;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.user_name)
        public EditText user_name;

        @UiElement(R.id.name_employee)
        public EditText name_employee;

        @UiElement(R.id.full_name_header)
        public TextView full_name_header;

        @UiElement(R.id.email_employee)
        public EditText email_employee;

        @UiElement(R.id.phone_employee)
        public EditText phone_employee;

        @UiElement(R.id.address_employee)
        public EditText address_employee;

        @UiElement(R.id.birthday_employee)
        public TextView birthday_employee;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_choose_day)
        public ImageView image_choose_day;

        @UiElement(R.id.choose_level)
        public LinearLayout choose_level;

        @UiElement(R.id.name_level_employee)
        public TextView name_level_employee;

        @UiElement(R.id.layout_update)
        public LinearLayout layout_update;

        @UiElement(R.id.layout_delete)
        public LinearLayout layout_delete;

        @UiElement(R.id.reset_pass)
        public LinearLayout reset_pass;

        @UiElement(R.id.status_employee)
        public Switch status_employee;


    }
}
