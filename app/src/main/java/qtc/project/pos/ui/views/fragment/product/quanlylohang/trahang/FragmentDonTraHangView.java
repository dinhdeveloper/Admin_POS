package qtc.project.pos.ui.views.fragment.product.quanlylohang.trahang;

import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
import b.laixuantam.myaarlibrary.widgets.slidedatetimepicker.SlideDateTimeListener;
import b.laixuantam.myaarlibrary.widgets.slidedatetimepicker.SlideDateTimePicker;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.PackageReturnModel;

public class FragmentDonTraHangView extends BaseView<FragmentDonTraHangView.UIContainer> implements FragmentDonTraHangViewInterface {

    HomeActivity activity;
    FragmentDonTraHangViewCallback callback;
    DatePickerDialog picker;

    @Override
    public void init(HomeActivity activity, FragmentDonTraHangViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(), activity);
        ui.title_header.setText("Tạo đơn trả hàng");
        onClick();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(v -> {
            if (callback != null)
                callback.onBackProgress();
        });
    }

    @Override
    public void sendDataToView(PackageInfoModel infoModel, String name, String id) {
        if (infoModel != null) {
            ui.ngay_nhap.setText(infoModel.getImport_date());
            ui.ma_nha_cung_ung.setText(infoModel.getManufacturer_id());
            ui.nhacungung.setText(infoModel.getManufacturer_name());
            ui.nguoi_tao_don.setText(AppProvider.getPreferences().getUserModel().getFull_name());
        }

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        ui.date_ngay_tra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(activity.getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        .setTypeShowDialog(1)
                        .build()
                        .show();
            }
        });

        ui.layout_create.setOnClickListener(v -> {
            try {
                PackageReturnModel returnModel = new PackageReturnModel();
                returnModel.setProduct_return_id_code(ui.ma_tra_hang.getText().toString());
                returnModel.setEmployee_id(infoModel.getEmployee_id());

                if (!TextUtils.isEmpty(ui.soluong_tra.getText().toString())) {
                    int tonkho = Integer.parseInt(infoModel.getQuantity_storage());
                    int nhapvao = Integer.parseInt(ui.soluong_tra.getText().toString());
                    if (nhapvao > tonkho) {
                        activity.showAlert("Số lượng trả vượt quá số lượng còn lại.", KAlertDialog.ERROR_TYPE);
                    } else {
                        returnModel.setProduct_return_description(ui.lydo_tra.getText().toString());
                    }
                } else {
                    activity.showAlert("Không được để trống.", KAlertDialog.ERROR_TYPE);

                }
                returnModel.setManufacturer_id(infoModel.getManufacturer_id());
                returnModel.setProduct_return_quantity(ui.soluong_tra.getText().toString());
                returnModel.setProduct_return_return_date(ui.ngay_tra.getText().toString());
                returnModel.setProduct_return_id(infoModel.getPack_id());

                if (callback != null) {
                    callback.setDataDoiTraHang(returnModel);
                }
            } catch (NumberFormatException ex) {
                Log.e("NumberFormatException", ex.getMessage());
            }
        });

        ui.layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.setOnBack();
                }
            }
        });
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat sdfTimeServer = new SimpleDateFormat("yyyy-MM-dd");
            String niceFormatDate = sdf.format(date);
            String scheduleDateNhap = sdfTimeServer.format(date);

            ui.ngay_tra.setText(niceFormatDate);

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
        }
    };

    @Override
    public void setOnBack() {
        callback.onBackProgress();
    }

    @Override
    public void showSuccess() {
        ui.ngay_tra.setText(null);
        ui.ma_tra_hang.setText(null);
        ui.soluong_tra.setText(null);
        ui.lydo_tra.setText(null);
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentDonTraHangView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_tra_hang;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.ngay_nhap)
        public TextView ngay_nhap;

        @UiElement(R.id.ma_nha_cung_ung)
        public TextView ma_nha_cung_ung;

        @UiElement(R.id.nhacungung)
        public TextView nhacungung;

        @UiElement(R.id.ngay_tra)
        public TextView ngay_tra;

        @UiElement(R.id.ma_tra_hang)
        public EditText ma_tra_hang;

        @UiElement(R.id.soluong_tra)
        public EditText soluong_tra;

        @UiElement(R.id.nguoi_tao_don)
        public TextView nguoi_tao_don;

        @UiElement(R.id.lydo_tra)
        public EditText lydo_tra;

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

        @UiElement(R.id.layout_cancel)
        public LinearLayout layout_cancel;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.date_ngay_tra)
        public ImageView date_ngay_tra;


    }
}
