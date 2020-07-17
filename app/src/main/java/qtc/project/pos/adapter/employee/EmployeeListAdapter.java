package qtc.project.pos.adapter.employee;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import b.laixuantam.myaarlibrary.helper.AccentRemove;
import qtc.project.pos.R;
import qtc.project.pos.adapter.report.antoankho.BaoCaoAnToanKhoAdapter;
import qtc.project.pos.model.EmployeeModel;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    Context context;
    List<EmployeeModel> lists;
    EmployeeListAdapterListener listener;
    public EmployeeListAdapter(Context context, List<EmployeeModel> lists) {
        this.context = context;
        this.lists = lists;
        filter = new EmployeeFilter();
    }



    public interface EmployeeListAdapterListener {
        void onClickItem(EmployeeModel model);
        void onItemCheckedChanged(int position, boolean isChecked);
    }
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
                holder.status_employee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int adapterPosition = holder.getAdapterPosition();

                        EmployeeModel tapped = lists.get(adapterPosition);
                        tapped.setStatus(String.valueOf(isChecked));

                        if (listener != null) {
                            listener.onItemCheckedChanged(adapterPosition, isChecked);
                        }
                    }
                });

            }catch (Exception e){
                Log.e("Ex",e.getMessage());
            }
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);

        holder.status_employee.setOnCheckedChangeListener(null);
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

    //filter

    private String filterString;
    private ArrayList<EmployeeModel> listData = new ArrayList<>();
    private ArrayList<EmployeeModel> listDataBackup = new ArrayList<>();
    private EmployeeFilter filter;

    public EmployeeFilter getFilter() {
        return filter;
    }

    public ArrayList<EmployeeModel> getListData() {
        return listData;
    }

    public ArrayList<EmployeeModel> getListDataBackup() {
        return listDataBackup;
    }

    public class EmployeeFilter extends Filter {
        public EmployeeFilter() {
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (!TextUtils.isEmpty(constraint)) {
                filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (listData != null && listData.size() > 0) {
                    int count = listData.size();
                    List<EmployeeModel> tempItems = new ArrayList<EmployeeModel>();

                    // search exactly
                    for (int i = 0; i < count; i++) {
                        String name = listData.get(i).getFull_name().toLowerCase();
                        if (name.contains(filterString)) {
                            tempItems.add(listData.get(i));
                        }
                    }
                    // search for no accent if no exactly result
                    filterString = AccentRemove.removeAccent(filterString);
                    if (tempItems.size() == 0) {
                        for (int i = 0; i < count; i++) {
                            String name = AccentRemove.removeAccent(listData.get(i).getFull_name().toLowerCase());
                            if (name.contains(filterString)) {
                                tempItems.add(listData.get(i));
                            }
                        }
                    }
                    results.values = tempItems;
                    results.count = tempItems.size();
                    return results;
                } else {
                    return null;
                }
            } else {
                filterString = "";
                return null;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listData.clear();
            if (results != null) {
                List<EmployeeModel> listProductResult = (List<EmployeeModel>) results.values;
                if (listProductResult != null && listProductResult.size() > 0) {
                    listData.addAll(listProductResult);
                }
            } else {
                listData.addAll(listDataBackup);
            }

            replaceAll(listData);
        }
    }

    private void replaceAll(ArrayList<EmployeeModel> listData) {
        lists.clear();
        lists.addAll(listData);
        notifyDataSetChanged();
    }

}

