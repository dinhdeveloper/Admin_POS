package qtc.project.pos.ui.views.fragment.product.doitra.detail;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.PackageReturnModel;

public class FragmentChiTietDonTraHangHoaView extends BaseView<FragmentChiTietDonTraHangHoaView.UIContainer> implements FragmentChiTietDonTraHangHoaViewInterface {
    HomeActivity activity;
    FragmentChiTietDonTraHangHoaViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentChiTietDonTraHangHoaViewCallback callback) {
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
    }

    @Override
    public void sendDataToView(PackageReturnModel infoModel) {
        if (infoModel != null) {
            ui.ma_tra_hang.setText(infoModel.getProduct_return_id());
            ui.ten_sp.setText(infoModel.getProduct_name());
            ui.nhacungung.setText(infoModel.getManufacturer_name());
            ui.ma_nha_cung_ung.setText(infoModel.getManufacturer_id());
            ui.soluong_tra.setText(infoModel.getProduct_return_quantity());
            ui.han_su_dung.setText(infoModel.getExpiry_date());
            ui.ngay_nhap.setText(infoModel.getImport_date());
            ui.ngay_tra.setText(infoModel.getProduct_return_return_date());
            ui.nguoi_tao_don.setText(infoModel.getEmployee_fullname());
            ui.lydo_tra.setText(infoModel.getProduct_return_description());
            if (infoModel.getProduct_return_status().equals("Y")) {
                ui.layout_button.setVisibility(View.GONE);
            } else if (infoModel.getProduct_return_status().equals("N")) {
                ui.layout_button.setVisibility(View.VISIBLE);

                ui.layout_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback!=null)
                            callback.updateStatus(infoModel);
                    }
                });

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
                        content_text.setText("Bạn có muốn xóa sản phẩm này không?");

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
                                    callback.deleteStatus(infoModel);
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        }
    }

    @Override
    public void showConfirmDelete() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã xóa sản phẩm thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.onBackProgress();
                    dialog.dismiss();
                }

            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentChiTietDonTraHangHoaView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_chi_tiet_don_tra_hang;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.ma_tra_hang)
        public TextView ma_tra_hang;

        @UiElement(R.id.ten_sp)
        public TextView ten_sp;

        @UiElement(R.id.nhacungung)
        public TextView nhacungung;

        @UiElement(R.id.ma_nha_cung_ung)
        public TextView ma_nha_cung_ung;

        @UiElement(R.id.soluong_tra)
        public TextView soluong_tra;

        @UiElement(R.id.han_su_dung)
        public TextView han_su_dung;

        @UiElement(R.id.ngay_nhap)
        public TextView ngay_nhap;

        @UiElement(R.id.ngay_tra)
        public TextView ngay_tra;

        @UiElement(R.id.nguoi_tao_don)
        public TextView nguoi_tao_don;

        @UiElement(R.id.lydo_tra)
        public TextView lydo_tra;

        @UiElement(R.id.layout_button)
        public LinearLayout layout_button;

        @UiElement(R.id.layout_done)
        public LinearLayout layout_done;

        @UiElement(R.id.layout_delete)
        public LinearLayout layout_delete;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
