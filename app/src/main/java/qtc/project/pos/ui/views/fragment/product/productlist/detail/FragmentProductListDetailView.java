package qtc.project.pos.ui.views.fragment.product.productlist.detail;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.widgets.popupmenu.ActionItem;
import b.laixuantam.myaarlibrary.widgets.popupmenu.MyCustomPopupMenu;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.category.ProductItemCategoryAdapter;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.model.ProductListModel;

public class FragmentProductListDetailView extends BaseView<FragmentProductListDetailView.UIContainer> implements FragmentProductListDetailViewInterface, ZXingScannerView.ResultHandler {

    HomeActivity activity;
    FragmentProductListDetailViewCallback callback;
    String image_pro;
    String id_category = null;

    int TYLE_CODE_GEN = 0;

    private ZXingScannerView mScannerView;

    @Override
    public void init(HomeActivity activity, FragmentProductListDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();

    }

    public void initScanBarcode() {
        mScannerView = new ZXingScannerView(activity);
        ui.content_frame.addView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.setAutoFocus(true);
        mScannerView.startCamera();
    }

    public void genarateScanBarcode(String resultCode){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix;
            bitMatrix = multiFormatWriter.encode(resultCode, BarcodeFormat.CODE_128, 300, 150);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ui.imv_barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void genarateScanQrcode(String resultCode){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix;
            bitMatrix = multiFormatWriter.encode(resultCode, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ui.imv_qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendDataToView(ProductListModel model) {
        if (model != null) {
            AppProvider.getImageHelper().displayImage(Consts.HOST_API + model.getImage(), ui.image_product, null, R.drawable.imageloading);
            ui.name_product.setText(model.getName());
            ui.id_product.setText(model.getId_code());
            ui.description_product.setText(model.getDescription());
            ui.tonkho.setText(model.getQuantity_safetystock());
            ui.qrcode.setText(model.getQr_code());
            ui.barcode.setText(model.getBarcode());
            try {
                genarateScanBarcode(model.getBarcode());
                genarateScanQrcode(model.getQr_code());
            }catch (Exception e){
                Log.e("Ex",e.getMessage());
            }
            ui.name_product_category.setText(model.getCategory_name());
        }
        //cap nhat
        ui.layout_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductListModel listModel = new ProductListModel();
                listModel.setId(model.getId());
                listModel.setId_code(ui.id_product.getText().toString());
                listModel.setName(ui.name_product.getText().toString());
                listModel.setDescription(ui.description_product.getText().toString());
                listModel.setCategory_id(id_category);
                listModel.setQuantity_safetystock(ui.tonkho.getText().toString());
                listModel.setImage(image_pro);
                listModel.setBarcode(ui.barcode.getText().toString());
                listModel.setQr_code(ui.qrcode.getText().toString());
                if (callback != null) {
                    callback.undateData(listModel);
                }
            }
        });

        //xoa sp
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
                        if (callback != null)
                            callback.deleteProduct(model);
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void generateCode(String resultCode) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix;
            switch (TYLE_CODE_GEN) {
                case 1:
                    bitMatrix = multiFormatWriter.encode(resultCode, BarcodeFormat.CODE_128, 300, 150);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    ui.imv_barcode.setImageBitmap(bitmap);
                    ui.barcode.setText(resultCode);
                    break;

                case 2:
                    bitMatrix = multiFormatWriter.encode(resultCode, BarcodeFormat.QR_CODE, 250, 250);
                    BarcodeEncoder barcodeQr = new BarcodeEncoder();
                    Bitmap bitmapQr = barcodeQr.createBitmap(bitMatrix);
                    ui.imv_qrcode.setImageBitmap(bitmapQr);
                    ui.qrcode.setText(resultCode);
                    break;
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataProductImage(String outfile) {
        image_pro = outfile;
        AppProvider.getImageHelper().displayImage(outfile, ui.image_product, null, R.drawable.imageloading, false);
    }

    @Override
    public void initViewPopup(ArrayList<ProductCategoryModel> list) {
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

        ProductItemCategoryAdapter chooseAdapter = new ProductItemCategoryAdapter(activity, list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view_list.setAdapter(chooseAdapter);

        chooseAdapter.setOnItemClickListener(new ProductItemCategoryAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(ProductCategoryModel model) {
                choose_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_category = model.getId();
                        ui.name_product_category.setText(model.getName());
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
    public void showConfirm() {
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
                if (callback != null) {
                    callback.onBackprogress();
                    dialog.dismiss();
                }

            }
        });
    }

    private void onClick() {
        //quet barcode
        ui.image_barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TYLE_CODE_GEN = 1;
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CAMERA}, 1101);
                } else {
                    ui.layout_scanbar_code.setVisibility(View.VISIBLE);
                    initScanBarcode();
                }
            }
        });

        //quet qr code
        ui.image_qrcode.setOnClickListener(v -> {
            TYLE_CODE_GEN = 2;
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA}, 1101);
            } else {
                ui.layout_scanbar_code.setVisibility(View.VISIBLE);
                initScanBarcode();
            }
        });
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.onBackprogress();
            }
        });

        //chon file
        ui.choose_file_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (callback != null) {
//                    callback.showDialogSelecteImage();
//                }
                showPopupMenu(v);
            }
        });

        //chon danh muc
        ui.choose_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.getAllProductCategory();
            }
        });

        //dong layout scanbar code
        ui.image_close_layout_scan.setOnClickListener(v -> {
            mScannerView.stopCamera();
            ui.layout_scanbar_code.setVisibility(View.GONE);
        });
    }

    private void showPopupMenu(View view) {
        ActionItem change_password = new ActionItem(1, "Chọn ảnh từ camera", null);
        ActionItem employee_tracking = new ActionItem(2, "Chọn hình từ thư viện", null);
//        int backgroundCustom = ContextCompat.getColor(getContext(), R.color.red);
//        int arrowColorCustom = ContextCompat.getColor(getContext(), R.color.red);

        MyCustomPopupMenu quickAction = new MyCustomPopupMenu(getContext()/*, backgroundCustom, arrowColorCustom*/);
        quickAction.addActionItem(change_password);
        quickAction.addActionItem(employee_tracking);

        quickAction.setOnActionItemClickListener(new MyCustomPopupMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(MyCustomPopupMenu source, int pos, int actionId) {
                switch (actionId) {
                    case 1:
                        if (callback != null)
                            callback.onClickOptionSelectImageFromCamera();
                        break;

                    case 2:
                        if (callback != null)
                            callback.onClickOptionSelectImageFromGallery();
                        break;
                }
            }
        });

        quickAction.show(view);
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductListDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_product_detail;
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult != null) {
            Vibrator v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            // SET RUNG 400 MILLISECONDS
            v.vibrate(400);
            mScannerView.stopCamera();
            mScannerView = null;
            ui.layout_scanbar_code.setVisibility(View.GONE);
            generateCode(rawResult.getText());
        }
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.name_product)
        public EditText name_product;

        @UiElement(R.id.id_product)
        public EditText id_product;

        @UiElement(R.id.tonkho)
        public EditText tonkho;

        @UiElement(R.id.barcode)
        public EditText barcode;

        @UiElement(R.id.qrcode)
        public EditText qrcode;

        @UiElement(R.id.description_product)
        public EditText description_product;

        @UiElement(R.id.choose_file_image)
        public LinearLayout choose_file_image;

        @UiElement(R.id.image_product)
        public ImageView image_product;

        @UiElement(R.id.image_barcode)
        public ImageView image_barcode;

        @UiElement(R.id.image_qrcode)
        public ImageView image_qrcode;


        @UiElement(R.id.layout_update)
        public LinearLayout layout_update;

        @UiElement(R.id.layout_delete)
        public LinearLayout layout_delete;

        @UiElement(R.id.choose_category)
        public LinearLayout choose_category;

        @UiElement(R.id.name_product_category)
        public TextView name_product_category;

        @UiElement(R.id.content_frame)
        public FrameLayout content_frame;

        @UiElement(R.id.layout_scanbar_code)
        public RelativeLayout layout_scanbar_code;

        @UiElement(R.id.image_close_layout_scan)
        public View image_close_layout_scan;

        @UiElement(R.id.imv_qrcode)
        public ImageView imv_qrcode;

        @UiElement(R.id.imv_barcode)
        public ImageView imv_barcode;


    }
}
