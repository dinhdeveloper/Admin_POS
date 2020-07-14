package qtc.project.pos.adapter.employee;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.EmployeeModel;

public class EmployeeListAdapter extends SuperAdapter<EmployeeModel> {

    EmployeeListAdapterListener listener;

    public interface EmployeeListAdapterListener {
        void onClickItem(EmployeeModel model);

        void setStatusSwich(EmployeeModel item, boolean isCheked);
    }

    public void setListener(EmployeeListAdapterListener listener) {
        this.listener = listener;
    }

    public EmployeeListAdapter(Context context, List<EmployeeModel> items) {
        super(context, items, R.layout.custom_item_employee_list);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, EmployeeModel item) {
        LinearLayout layout_item = holder.findViewById(R.id.layout_item);
        TextView name_employee = holder.findViewById(R.id.name_employee);
        TextView level_employee = holder.findViewById(R.id.level_employee);
        TextView phone_employee = holder.findViewById(R.id.phone_employee);
        Switch status_employee = holder.findViewById(R.id.status_employee);

        try {
            if (item != null) {
                name_employee.setText(item.getFull_name());
                phone_employee.setText(item.getPhone_number());
                if (item.getLevel().equals("2")) {
                    level_employee.setText("Admin");
                } else if (item.getLevel().equals("1")) {
                    level_employee.setText("Nhân Viên");
                }

                if (item.getStatus().equals("Y")) {
                    status_employee.setChecked(true);
                } else if (item.getStatus().equals("N")) {
                    status_employee.setChecked(false);
                }

                layout_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.onClickItem(item);
                    }
                });

                status_employee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (listener != null)
                            listener.setStatusSwich(item, isChecked);
                    }
                });
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }
}
