package qtc.project.pos.adapter.employee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import b.laixuantam.myaarlibrary.widgets.superadapter.SuperAdapter;
import b.laixuantam.myaarlibrary.widgets.superadapter.SuperViewHolder;
import qtc.project.pos.R;
import qtc.project.pos.model.EmployeeModel;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    Context context;
    List<EmployeeModel> lists;
    EmployeeListAdapterListener listener;
    boolean isTouched = false;
    private ClickInterface click;

    public EmployeeListAdapter(Context context, List<EmployeeModel> lists) {
        this.context = context;
        this.lists = lists;
    }



    public interface EmployeeListAdapterListener {
        void onClickItem(EmployeeModel model);
        void setStatusSwich(int position, boolean isCheked);
    }
    interface ClickInterface{void posClicked(short p);}

    public void setListener(EmployeeListAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_employee_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        EmployeeModel item = lists.get(i);
            try{

                holder.name_employee.setText(item.getFull_name());
                holder.phone_employee.setText(item.getPhone_number());
                if (item.getLevel().equals("2")) {
                    holder.level_employee.setText("Admin");
                } else if (item.getLevel().equals("1")) {
                    holder.level_employee.setText("Nhân Viên");
                }

                if (item.getStatus().equals("Y")) {
                    holder.status_employee.setChecked(true);
                } else if (item.getStatus().equals("N")) {
                    holder.status_employee.setChecked(false);
                }

                //on click
                holder.layout_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null)
                            listener.onClickItem(item);
                    }
                });
                //set trang thai

                holder.status_employee.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        isTouched = true;
                        return false;
                    }
                });

                holder.status_employee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isTouched) {
                            isTouched = false;

                            if (isChecked) {
                                click.posClicked((short) holder.getAdapterPosition());
                                Toast.makeText(context, ""+isChecked, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, ""+isChecked, Toast.LENGTH_SHORT).show();
                                // Do something on un-checking the SwitchCompat
                            }
                        }
                    }
                });

            }catch (Exception e){
                Log.e("Ex",e.getMessage());
            }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_item;
        TextView name_employee;
        TextView level_employee;
        TextView phone_employee;
        SwitchCompat status_employee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            name_employee = itemView.findViewById(R.id.name_employee);
            level_employee = itemView.findViewById(R.id.level_employee);
            phone_employee = itemView.findViewById(R.id.phone_employee);
            status_employee = itemView.findViewById(R.id.status_employee_list);
        }
    }
}

