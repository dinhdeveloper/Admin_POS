package qtc.project.pos.ui.views.fragment.product.quanlylohang.detail;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import b.laixuantam.myaarlibrary.widgets.currencyedittext.CurrencyEditText;
import b.laixuantam.myaarlibrary.widgets.slidedatetimepicker.SlideDateTimeListener;
import b.laixuantam.myaarlibrary.widgets.slidedatetimepicker.SlideDateTimePicker;
import b.laixuantam.myaarlibrary.widgets.ultils.ConvertDate;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.EmployeeModel;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;
import qtc.project.pos.model.SupplierModel;

public class FragmentChiTietLoHangView extends BaseView<FragmentChiTietLoHangView.UIContainer> implements FragmentChiTietLoHangViewInterface {
    HomeActivity activity;
    FragmentChiTietLoHangViewCallback callback;
    DatePickerDialog picker;
    String id_product;
    String id_nha_cung_ung;
    String nha_cung_ung;
    String id_code_ncu;
    String phone_ncu;

    @Override
    public void init(HomeActivity activity, FragmentChiTietLoHangViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(), activity);
        ui.title_header.setText("Chi tiết lô hàng");
        onClick();
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

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        ui.date_ngay_nhap.setOnClickListener(v -> {
            new SlideDateTimePicker.Builder(activity.getSupportFragmentManager())
                    .setListener(listener)
                    .setInitialDate(new Date())
                    .setTypeShowDialog(1)
                    .build()
                    .show();
        });

        ui.date_ngay_sx.setOnClickListener(v ->
                new SlideDateTimePicker.Builder(activity.getSupportFragmentManager())
                        .setListener(listener1)
                        .setInitialDate(new Date())
                        .setTypeShowDialog(1)
                        .build()
                        .show());

        ui.date_han_su_dung.setOnClickListener(v -> {
            new SlideDateTimePicker.Builder(activity.getSupportFragmentManager())
                    .setListener(listener2)
                    .setInitialDate(new Date())
                    .setTypeShowDialog(1)
                    .build()
                    .show();
        });


    }

    String scheduleDateNhap;
    String scheduleDateSanXuat;
    String scheduleDateHSD;
    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat sdfTimeServer = new SimpleDateFormat("yyyy-MM-dd");
            String niceFormatDate = sdf.format(date);
            scheduleDateNhap = sdfTimeServer.format(date);

            ui.ngay_nhap.setText(niceFormatDate);

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };
    private SlideDateTimeListener listener1 = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat sdfTimeServer = new SimpleDateFormat("yyyy-MM-dd");
            String niceFormatDate = sdf.format(date);
            scheduleDateSanXuat = sdfTimeServer.format(date);

            ui.ngay_sx.setText(niceFormatDate);

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };

    private SlideDateTimeListener listener2 = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat sdfTimeServer = new SimpleDateFormat("yyyy-MM-dd");
            String niceFormatDate = sdf.format(date);
            scheduleDateHSD = sdfTimeServer.format(date);

            ui.han_su_dung.setText(niceFormatDate);

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };
    boolean check = true;

    @Override
    public void sendDataToView(PackageInfoModel infoModel, String name, String id) {
        if (infoModel != null) {
            id_product = id;

            setVisible(ui.layout_id);
            ui.malohang.setText(infoModel.getPack_id_code());
            ui.nhacungung.setText(infoModel.getManufacturer_name());
            ui.name_product_category.setText(name);
            ui.ngay_nhap.setText(infoModel.getImport_date());
            ui.ngay_sx.setText(infoModel.getManufacturing_date());
            ui.han_su_dung.setText(infoModel.getExpiry_date());
            ui.edtGiaNhap.setText(infoModel.getImport_price());
            ui.gia_ban.setText(infoModel.getSale_price());
            ui.mota.setText(infoModel.getDescription());
            ui.soluong_nhapvao.setText(infoModel.getQuantity_order());
            if (!Float.valueOf(infoModel.getQuantity_order()).equals(Float.valueOf(infoModel.getQuantity_storage()))) {
                ui.soluong_nhapvao.setEnabled(false);
            }
            ui.nguoi_tao_don.setText(infoModel.getEmployee_fullname());
            ui.tonkho.setText(infoModel.getQuantity_storage());

            if (Float.valueOf(infoModel.getQuantity_storage()) > 0) {
                ui.layout_trahang.setVisibility(View.VISIBLE);
            }
            //cap nhat
            ui.layout_update.setOnClickListener(v -> {
                if (callback != null) {
                    PackageInfoModel info = new PackageInfoModel();
                    info.setPack_id(infoModel.getPack_id());
                    info.setManufacturer_id(infoModel.getManufacturer_id());
                    info.setManufacturer_name(ui.nhacungung.getText().toString());
                    info.setManufacturing_date(ui.ngay_sx.getText().toString());
                    info.setImport_date(ui.ngay_nhap.getText().toString());
                    info.setExpiry_date(ui.han_su_dung.getText().toString());
                    info.setImport_price(ui.edtGiaNhap.getStringValue());
                    info.setSale_price(ui.gia_ban.getStringValue());
                    info.setDescription(ui.mota.getText().toString());
                    info.setQuantity_order(ui.soluong_nhapvao.getStringValue());
                    info.setEmployee_fullname(ui.nguoi_tao_don.getText().toString());
                    callback.updateDataPackage(info, id_product);
                }
            });

            //tra hang
            ui.layout_trahang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.taoDonTraHang(infoModel, name, id);
                    }
                }
            });
        } else {
            ui.title_header.setText("Tạo mới lô hàng");
            setGone(ui.layout_id);
            setGone(ui.layout_tonkho);
            ui.soluong_nhapvao.setEnabled(true);
            setVisible(ui.imvChooseProduct);
            ui.tvTitleUpdate.setText("Tạo mới");
            EmployeeModel employeeModel = AppProvider.getPreferences().getUserModel();
            ui.nguoi_tao_don.setText(employeeModel.getFull_name());
            ui.imvChooseProduct.setOnClickListener(v -> {
                if (callback != null)
                    callback.getAllDataProduct(check);
            });
            ui.name_product_category.setOnClickListener(v -> {
                if (callback != null)
                    callback.getAllDataProduct(check);
            });

            ui.layout_update.setOnClickListener(new View.OnClickListener() {
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
                    listModel.setImport_price(ui.edtGiaNhap.getText().toString());
                    listModel.setManufacturer_name(nha_cung_ung);
                    listModel.setManufacturer_phone_number(phone_ncu);

                    if (callback!=null)
                        callback.updateDataPackage(listModel,id_product);
                }
            });
        }

    }

    @Override
    public void sentDataProductToView(ProductListModel model) {
        if (model != null) {
            id_product = model.getId();
            ui.name_product_category.setText(model.getName());
        }
    }

    @Override
    public void hideRootView() {
        setGone(ui.layoutRootView);
    }

    @Override
    public void showRootView() {
        setVisible(ui.layoutRootView);
    }

    @Override
    public void sendDataToViewTwo(SupplierModel supplierModel) {
        if (supplierModel != null) {
            ui.nhacungung.setText(supplierModel.getName());
            id_nha_cung_ung = supplierModel.getId();
            id_code_ncu = supplierModel.getId_code();
            nha_cung_ung = supplierModel.getName();
            phone_ncu = supplierModel.getPhone_number();
            ui.nhacungung.setText(supplierModel.getName());
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentChiTietLoHangView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_chi_tiet_lo_hang;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutRootView)
        public View layoutRootView;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.layout_id)
        public LinearLayout layout_id;

        @UiElement(R.id.malohang)
        public TextView malohang;

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

        @UiElement(R.id.tvTitleUpdate)
        public TextView tvTitleUpdate;


        @UiElement(R.id.edtGiaNhap)
        public CurrencyEditText edtGiaNhap;

        @UiElement(R.id.gia_ban)
        public CurrencyEditText gia_ban;

        @UiElement(R.id.mota)
        public EditText mota;

        @UiElement(R.id.soluong_nhapvao)
        public CurrencyEditText soluong_nhapvao;

        @UiElement(R.id.nguoi_tao_don)
        public TextView nguoi_tao_don;

        @UiElement(R.id.layout_update)
        public LinearLayout layout_update;

        @UiElement(R.id.layout_delete)
        public LinearLayout layout_trahang;

        @UiElement(R.id.layout_nhacungung)
        public LinearLayout layout_nhacungung;

        @UiElement(R.id.date_ngay_nhap)
        public ImageView date_ngay_nhap;

        @UiElement(R.id.date_ngay_sx)
        public ImageView date_ngay_sx;

        @UiElement(R.id.date_han_su_dung)
        public ImageView date_han_su_dung;

        @UiElement(R.id.imvChooseProduct)
        public ImageView imvChooseProduct;

        @UiElement(R.id.tonkho)
        public TextView tonkho;

        @UiElement(R.id.layout_tonkho)
        public LinearLayout layout_tonkho;

    }
}
