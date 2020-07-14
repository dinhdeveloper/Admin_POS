package qtc.project.pos.ui.views.fragment.employee.create;

import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class FragmentCreateEmployeeView extends BaseView<FragmentCreateEmployeeView.UIContainer> implements FragmentCreateEmployeeViewInterface {
    HomeActivity activity;
    FragmentCreateEmployeeViewCallback callback;
    DatePickerDialog picker;
    String id_level = null;

    @Override
    public void init(HomeActivity activity, FragmentCreateEmployeeViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        onClick();
    }

    private void onClick() {

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);

        //onBACK
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
        //chon ngay sinh
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
                if (callback != null) {
                    callback.callLevelEmployee();
                }
            }
        });

        //tao moi
        ui.layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeModel model = new EmployeeModel();
                model.setId_code(ui.user_name.getText().toString());
                model.setFull_name(ui.name_employee.getText().toString());
                model.setPassword(ui.pass_word.getText().toString());
                model.setEmail(ui.email_employee.getText().toString());
                model.setPhone_number(ui.phone_employee.getText().toString());
                model.setAddress(ui.address_employee.getText().toString());
                if (ui.birthday_employee.getText().toString() == null) {
                    Toast.makeText(activity, "Chọn ngày tháng năm sinh", Toast.LENGTH_SHORT).show();
                } else {
                    model.setBirthday(ui.birthday_employee.getText().toString());
                }
                model.setLevel(id_level);

                if (callback != null)
                    callback.createEmployee(model);
            }
        });
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

        LevelEmployeeChooseAdapter chooseAdapter = new LevelEmployeeChooseAdapter(activity, arrayList);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
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
    public void showDiaLogSucess() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã tạo mới thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.user_name.setText(null);
                ui.name_employee.setText(null);
                ui.pass_word.setText(null);
                ui.email_employee.setText(null);
                ui.phone_employee.setText(null);
                ui.address_employee.setText(null);
                ui.birthday_employee.setText(null);
                ui.name_level_employee.setText("Chọn");
                dialog.dismiss();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCreateEmployeeView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_create_employee;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.user_name)
        public EditText user_name;

        @UiElement(R.id.name_employee)
        public EditText name_employee;

        @UiElement(R.id.pass_word)
        public EditText pass_word;

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

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

    }
}
