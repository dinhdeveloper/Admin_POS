package qtc.project.pos.ui.views.fragment.employee.list;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.employee.EmployeeListAdapter;
import qtc.project.pos.model.EmployeeModel;

public class FragmentEmployeeListView extends BaseView<FragmentEmployeeListView.UIContainer> implements FragmentEmployeeListViewInterface {

    HomeActivity activity;
    FragmentEmployeeListViewCallback callback;

    ArrayList<EmployeeModel> listAll = new ArrayList<>();

    @Override
    public void init(HomeActivity activity, FragmentEmployeeListViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
        getDataEmployee();
    }

    @Override
    public void mappingRecyclerView(ArrayList<EmployeeModel> list) {
        listAll.addAll(list);
        EmployeeListAdapter adapter = new EmployeeListAdapter(activity, listAll);
        ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ui.recycler_view_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new EmployeeListAdapter.EmployeeListAdapterListener() {
            @Override
            public void onClickItem(EmployeeModel model) {


                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.custom_popup_choose_employee, null);
                LinearLayout item_history_order = popupView.findViewById(R.id.item_history_order);
                LinearLayout item_detail = popupView.findViewById(R.id.item_detail);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(popupView);
                AlertDialog dialog = alert.create();
                //dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                item_history_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.getHistoryOrderEmployee(model);
                            dialog.dismiss();
                        }
                    }
                });

                item_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback != null) {
                            callback.goToDetailEmployee(model);
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void setStatusSwich(int position, boolean isCheked) {
                for (int i = 0; i < listAll.size(); i++){
                    if (listAll.get(i).getId().equalsIgnoreCase(list.get(position).getId())){
                        Toast.makeText(activity, ""+isCheked, Toast.LENGTH_SHORT).show();
                    }adapter.notifyDataSetChanged();
                    break;
                }

              //  Toast.makeText(activity, ""+item.getFull_name()+ "Status: "+isCheked, Toast.LENGTH_SHORT).show();

//                if (callback != null)
//                    callback.updateEmployee(employeeModel);


//                EmployeeModel employeeModel = new EmployeeModel();
//                employeeModel.setId(item.getId());
//
//                for (int i = 0; i < listAll.size(); i++) {
//                    if (listAll.get(i).getId().equalsIgnoreCase(item.getId())) {
//                        if (isCheked == true) {
//                            listAll.get(i).setStatus("Y");
//                            employeeModel.setStatus("Y");
//                        } else {
//                            listAll.get(i).setStatus("N");
//                            employeeModel.setStatus("N");
//                        }
//                        adapter.notifyDataSetChanged();
//                        break;
//
//                    }
//                }

//                if (callback != null)
//                    callback.updateEmployee(employeeModel);
            }
        });
    }

    @Override
    public void showPopup() {
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

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });

        //new
        ui.image_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.createEmployee();
            }
        });
    }

    private void getDataEmployee() {
        if (callback != null) {
            callback.getAllDataEmployee();
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentEmployeeListView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_employee_list;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.ic_search)
        public ImageView ic_search;

        @UiElement(R.id.image_create)
        public ImageView image_create;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;


    }
}
