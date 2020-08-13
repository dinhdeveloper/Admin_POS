package qtc.project.pos.ui.views.fragment.product.quanlylohang.create;

import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.SupplierModel;

public class FragmentCreateLoHangView extends BaseView<FragmentCreateLoHangView.UIContainer> implements FragmentCreateLoHangViewInterface {

    HomeActivity activity;
    FragmentCreateLoHangViewCallback callback;
    DatePickerDialog picker;
    String id_product;
    String id_nha_cung_ung;
    String nha_cung_ung;
    String id_code_ncu;
    String phone_ncu;
    boolean check = true;

    @Override
    public void init(HomeActivity activity, FragmentCreateLoHangViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        KeyboardUtils.setupUI(getView(),activity);

        EmployeeModel employeeModel = AppProvider.getPreferences().getUserModel();
        ui.nguoi_tao_don.setText(employeeModel.getFull_name());

        onClick();
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        ui.ngay_nhap.setText(year+"-"+(month+1)+"-"+day);
    }

    @Override
    public void sendDataToView(SupplierModel model) {
        if (model != null) {
            id_nha_cung_ung = model.getId();
            id_code_ncu = model.getId_code();
            nha_cung_ung = model.getName();
            phone_ncu = model.getPhone_number();
            ui.nhacungung.setText(model.getName());
        }
    }

    @Override
    public void sentDataProductToView(ProductListModel model) {
        if (model!=null)
        {
            id_product = model.getId();
            ui.name_product_category.setText(model.getName());
        }
    }

    @Override
    public void onShowSuccess() {
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
                //ui.malohang.setText(null);
                ui.gia_nhap.setText(null);
                ui.nhacungung.setText("Chọn");
                ui.ngay_nhap.setText(null);
                ui.ngay_sx.setText(null);
                ui.han_su_dung.setText(null);
                ui.gia_ban.setText(null);
                ui.mota.setText(null);
                ui.soluong_nhapvao.setText(null);
                ui.name_product_category.setText("Chọn");
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

        //get tat ca nha cung ung
        ui.layout_nhacungung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.getAllDataNhaCungUng();
            }
        });

        // get tat ca san pham
        ui.layout_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.getAllDataProduct(check);
            }
        });

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        ui.date_ngay_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                ui.ngay_nhap.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        ui.date_ngay_sx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                ui.ngay_sx.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
                picker.getDatePicker().setMaxDate(new Date().getTime());
            }
        });

        ui.date_han_su_dung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                ui.han_su_dung.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        //create
        ui.layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageInfoModel listModel = new PackageInfoModel();
                //listModel.setPack_id_code(ui.malohang.getText().toString());
                listModel.setManufacturer_id(id_nha_cung_ung);
                listModel.setManufacturer_id_code(id_code_ncu);
                listModel.setImport_date(ui.ngay_nhap.getText().toString());
                listModel.setExpiry_date(ui.han_su_dung.getText().toString());
                listModel.setManufacturing_date(ui.ngay_sx.getText().toString());
                listModel.setDescription(ui.mota.getText().toString());
                listModel.setQuantity_order(ui.soluong_nhapvao.getText().toString());
                listModel.setSale_price(ui.gia_ban.getText().toString());
                listModel.setImport_price(ui.gia_nhap.getText().toString());
                listModel.setManufacturer_name(nha_cung_ung);
                listModel.setManufacturer_phone_number(phone_ncu);

                if (callback!=null)
                    callback.createPackageInfo(listModel,id_product);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCreateLoHangView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_create_lo_hang;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.malohang)
        public EditText malohang;

        @UiElement(R.id.nhacungung)
        public TextView nhacungung;

        @UiElement(R.id.name_product_category)
        public TextView name_product_category;

        @UiElement(R.id.ngay_nhap)
        public TextView ngay_nhap;

        @UiElement(R.id.ngay_sx)
        public TextView ngay_sx;

        @UiElement(R.id.han_su_dung)
        public TextView han_su_dung;

        @UiElement(R.id.gia_nhap)
        public EditText gia_nhap;

        @UiElement(R.id.gia_ban)
        public EditText gia_ban;

        @UiElement(R.id.mota)
        public EditText mota;

        @UiElement(R.id.soluong_nhapvao)
        public EditText soluong_nhapvao;

        @UiElement(R.id.nguoi_tao_don)
        public TextView nguoi_tao_don;

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

        @UiElement(R.id.layout_nhacungung)
        public LinearLayout layout_nhacungung;

        @UiElement(R.id.layout_product)
        public LinearLayout layout_product;



        @UiElement(R.id.date_ngay_nhap)
        public ImageView date_ngay_nhap;

        @UiElement(R.id.date_ngay_sx)
        public ImageView date_ngay_sx;

        @UiElement(R.id.date_han_su_dung)
        public ImageView date_han_su_dung;

        @UiElement(R.id.tonkho)
        public TextView tonkho;

    }
}
