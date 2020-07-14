package qtc.project.pos.ui.views.fragment.supplier.create;

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

public class FragmentCreateSupplierView extends BaseView<FragmentCreateSupplierView.UIContainer> implements FragmentCreateSupplierViewInterface {

    HomeActivity activity;
    FragmentCreateSupplierViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentCreateSupplierViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        
        onClick();
    }

    @Override
    public void showAlerSucess() {
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
                ui.id_supplier.setText(null);
                ui.name_sipplier.setText(null);
                ui.phone_supplier.setText(null);
                ui.email_supplier.setText(null);
                ui.address_supplier.setText(null);
                dialog.dismiss();
            }
        });
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
        //create
        ui.layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SupplierModel supplierModel = new SupplierModel();
                supplierModel.setId_code(ui.id_supplier.getText().toString());
                supplierModel.setPhone_number(ui.phone_supplier.getText().toString());
                supplierModel.setEmail(ui.email_supplier.getText().toString());
                supplierModel.setName(ui.name_sipplier.getText().toString());
                supplierModel.setAddress(ui.address_supplier.getText().toString());

                if (callback!=null)
                    callback.createSupplier(supplierModel);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCreateSupplierView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_create_supplier;
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

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
