package qtc.project.pos.ui.views.fragment.employee.list;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.employee.EmployeeListAdapter;
import qtc.project.pos.model.EmployeeModel;

public class FragmentEmployeeListView extends BaseView<FragmentEmployeeListView.UIContainer> implements FragmentEmployeeListViewInterface {

    HomeActivity activity;
    FragmentEmployeeListViewCallback callback;
    EmployeeListAdapter adapter;

    ArrayList<EmployeeModel> listAll;
    String employee_id;

    @Override
    public void init(HomeActivity activity, FragmentEmployeeListViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        KeyboardUtils.setupUI(getView(), activity);

        ui.edit_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter != null)
                    adapter.getFilter().filter(s);
            }
        });

        onClick();
        getDataEmployee();
    }

    @Override
    public void mappingRecyclerView(ArrayList<EmployeeModel> list) {
        listAll = new ArrayList<>();
        listAll.clear();
        listAll.addAll(list);
        adapter = new EmployeeListAdapter(activity, listAll);
        adapter.getListData().addAll(listAll);
        adapter.getListDataBackup().addAll(listAll);
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
            public void onItemCheckedChanged(int position, boolean isChecked) {

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
                        if (isChecked == true) {
                            listAll.get(position).setStatus("Y");
                            callback.updateEmployee(listAll.get(position));
                        } else {
                            employee_id = listAll.get(position).getId();
                            listAll.get(position).setStatus("N");
                            callback.updateEmployee(listAll.get(position));
                        }
                        dialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public void showPopup() {
        if (callback != null) {
            callback.getAllDataEmployee();
            adapter.notifyDataSetChanged();
        }
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

        //close
        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
                if (callback != null) {
                    callback.getAllDataEmployee();
                    adapter.notifyDataSetChanged();
                }
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

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;


    }
}
