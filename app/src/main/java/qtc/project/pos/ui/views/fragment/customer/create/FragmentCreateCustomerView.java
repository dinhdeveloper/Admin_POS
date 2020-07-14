package qtc.project.pos.ui.views.fragment.customer.create;

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
import qtc.project.pos.adapter.levelcustomer.LevelCustomerChooseAdapter;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;

public class FragmentCreateCustomerView extends BaseView<FragmentCreateCustomerView.UIContainer> implements FragmentCreateCustomerViewInterface {

    HomeActivity activity;
    FragmentCreateCustomerViewCallback callback;
    String  id_level = null;
    @Override
    public void init(HomeActivity activity, FragmentCreateCustomerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        onClick();
    }
    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });

        //chon level
        ui.choose_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.getAllLevelCustomer();
            }
        });

        //tao moi customer
        ui.layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    CustomerModel customerModel = new CustomerModel();
                    customerModel.setId_code(ui.id_customer.getText().toString());
                    customerModel.setFull_name(ui.name_customer.getText().toString());
                    customerModel.setPhone_number(ui.phone_customer.getText().toString());
                    customerModel.setAddress(ui.address_customer.getText().toString());
                    customerModel.setEmail(ui.email_customer.getText().toString());
                    customerModel.setLevel_name(ui.name_level_customer.getText().toString());
                    customerModel.setLevel_id(id_level);

                    if (callback!=null)
                        callback.createCustomer(customerModel);
                }catch (Exception e){
                    Log.e("Exception",e.getMessage());
                }
            }
        });
    }

    @Override
    public void mappingPopup(ArrayList<LevelCustomerModel> list) {
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

        LevelCustomerChooseAdapter chooseAdapter = new LevelCustomerChooseAdapter(activity,list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recycler_view_list.setAdapter(chooseAdapter);

        chooseAdapter.setOnItemClickListener(new LevelCustomerChooseAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(LevelCustomerModel model) {
                choose_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_level = model.getId();
                        ui.name_level_customer.setText(model.getName());
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
    public void showAlert() {
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
                ui.id_customer.setText(null);
                ui.name_customer.setText(null);
                ui.phone_customer.setText(null);
                ui.address_customer.setText(null);
                ui.email_customer.setText(null);
                ui.name_level_customer.setText("Chọn");
                dialog.dismiss();
            }
        });
    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCreateCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_create_customer;
    }


    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.name_customer)
        public EditText name_customer;

        @UiElement(R.id.id_customer)
        public EditText id_customer;

        @UiElement(R.id.phone_customer)
        public EditText  phone_customer;

        @UiElement(R.id.address_customer)
        public EditText address_customer;

        @UiElement(R.id.email_customer)
        public EditText email_customer;

        @UiElement(R.id.name_level_customer)
        public TextView name_level_customer;

        @UiElement(R.id.choose_level)
        public LinearLayout choose_level;

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

    }
}
