package qtc.project.pos.ui.views.fragment.supplier.detail;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.SupplierModel;

public class FragmentSupplierDetailView extends BaseView<FragmentSupplierDetailView.UIContainer> implements FragmentSupplierDetailViewInterface{
    HomeActivity activity;
    FragmentSupplierDetailViewCallback callback;
    @Override
    public void init(HomeActivity activity, FragmentSupplierDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    private void onClick() {
        //back
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });
    }

    @Override
    public void sendDataToView(SupplierModel infoModel) {
        if (infoModel!=null){
            ui.id_supplier.setText(infoModel.getId_code());
            ui.name_sipplier.setText(infoModel.getName());
            ui.email_supplier.setText(infoModel.getEmail());
            ui.phone_supplier.setText(infoModel.getPhone_number());
            ui.address_supplier.setText(infoModel.getAddress());

            //update
            ui.layout_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SupplierModel supplierModel = new SupplierModel();
                    supplierModel.setId(infoModel.getId());
                    supplierModel.setId_code(ui.id_supplier.getText().toString());
                    supplierModel.setAddress(ui.address_supplier.getText().toString());
                    supplierModel.setName(ui.name_sipplier.getText().toString());
                    supplierModel.setEmail(ui.email_supplier.getText().toString());
                    supplierModel.setPhone_number(ui.phone_supplier.getText().toString());

                    if (callback!=null)
                        callback.updateSupplier(supplierModel);
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
                    content_text.setText("Bạn có muốn xóa tài khoản này không?");

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
                            if (callback!=null)
                                callback.deleteSupplier(infoModel.getId());
                        }
                    });
                }
            });
        }
    }

    @Override
    public void showDialog() {
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
    public void showDialogSucc() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã xóa tài khoản thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                callback.onBackProgress();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentSupplierDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_supplier_detail;
    }


    public class UIContainer extends BaseUiContainer {

        @UiElement(R.id.id_supplier)
        public EditText id_supplier;

        @UiElement(R.id.name_sipplier)
        public EditText name_sipplier;

        @UiElement(R.id.email_supplier)
        public EditText email_supplier;

        @UiElement(R.id.phone_supplier)
        public EditText phone_supplier;

        @UiElement(R.id.address_supplier)
        public EditText address_supplier;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.layout_update)
        public LinearLayout layout_update;

        @UiElement(R.id.layout_delete)
        public LinearLayout layout_delete;

    }
}
