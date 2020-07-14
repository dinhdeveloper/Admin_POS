package qtc.project.pos.ui.views.fragment.employee.list;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

    @Override
    public void init(HomeActivity activity, FragmentEmployeeListViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
        getDataEmployee();
    }

    @Override
    public void mappingRecyclerView(ArrayList<EmployeeModel> list) {
        EmployeeListAdapter adapter = new EmployeeListAdapter(activity, list);
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
            public void setStatusSwich(EmployeeModel item, boolean isCheked) {
                EmployeeModel employeeModel = new EmployeeModel();
                employeeModel.setId(item.getId());

                if (isCheked == true) {
                    employeeModel.setStatus("Y");
                } else {
                    employeeModel.setStatus("N");
                }

                if (callback != null)
                    callback.updateEmployee(employeeModel);
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
