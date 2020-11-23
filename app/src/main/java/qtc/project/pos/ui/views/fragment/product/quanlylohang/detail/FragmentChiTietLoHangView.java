package qtc.project.pos.ui.views.fragment.product.quanlylohang.detail;

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
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.SupplierModel;

public class FragmentChiTietLoHangView extends BaseView<FragmentChiTietLoHangView.UIContainer> implements FragmentChiTietLoHangViewInterface {
    HomeActivity activity;
    FragmentChiTietLoHangViewCallback callback;
    DatePickerDialog picker;
    String id_product;

    @Override
    public void init(HomeActivity activity, FragmentChiTietLoHangViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(),activity);
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


    }
    String ma_cungung;
    @Override
    public void sendDataToView(PackageInfoModel infoModel, String name, String id) {
        if (infoModel != null) {
            id_product = id;
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
            if (!Float.valueOf(infoModel.getQuantity_order()).equals(Float.valueOf(infoModel.getQuantity_storage()))){
                ui.soluong_nhapvao.setEnabled(false);
            }
            ui.nguoi_tao_don.setText(infoModel.getEmployee_fullname());
            ui.tonkho.setText(infoModel.getQuantity_storage());

            if (Float.valueOf(infoModel.getQuantity_storage())>0){
                ui.layout_trahang.setVisibility(View.VISIBLE);
            }
        }
        //cap nhat
        ui.layout_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    PackageInfoModel info = new PackageInfoModel();
                    info.setPack_id(infoModel.getPack_id());
                    info.setManufacturer_id(infoModel.getManufacturer_id());
                    info.setManufacturer_name(ui.nhacungung.getText().toString());
                    info.setManufacturing_date(ui.ngay_sx.getText().toString());
                    info.setImport_date(ui.ngay_nhap.getText().toString());
                    info.setExpiry_date(ui.han_su_dung.getText().toString());
                    info.setImport_price(ui.edtGiaNhap.getText().toString());
                    info.setSale_price(ui.gia_ban.getText().toString());
                    info.setDescription(ui.mota.getText().toString());
                    info.setQuantity_order(ui.soluong_nhapvao.getText().toString());
                    info.setEmployee_fullname(ui.nguoi_tao_don.getText().toString());
                    callback.updateDataPackage(info, id_product);
                }
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

    }

    @Override
    public void sendDataToViewTwo(SupplierModel supplierModel) {
        if (supplierModel != null) {
            ui.nhacungung.setText(supplierModel.getName());
        }
    }

    @Override
    public void showPopSuccess() {
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
        custom_confirm_button.setOnClickListener(v -> {
            dialog.dismiss();
        });
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
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

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

        @UiElement(R.id.edtGiaNhap)
        public EditText edtGiaNhap;

        @UiElement(R.id.gia_ban)
        public EditText gia_ban;

        @UiElement(R.id.mota)
        public EditText mota;

        @UiElement(R.id.soluong_nhapvao)
        public EditText soluong_nhapvao;

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

        @UiElement(R.id.tonkho)
        public TextView tonkho;

    }
}
