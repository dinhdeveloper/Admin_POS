package qtc.project.pos.ui.views.fragment.product.productlist.detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.activity.Qr_BarcodeActivity;
import qtc.project.pos.adapter.product.category.ProductItemCategoryAdapter;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.model.ProductListModel;

public class FragmentProductListDetailView extends BaseView<FragmentProductListDetailView.UIContainer> implements FragmentProductListDetailViewInterface {

    HomeActivity activity;
    FragmentProductListDetailViewCallback callback;
    String image_pro;
    String id_category = null;
    ZXingScannerView scannerView;

    @Override
    public void init(HomeActivity activity, FragmentProductListDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(activity), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, activity.MY_BARCODE_PERMISSION_CODE);
                    } else {
                        if (activity != null)
                            activity.startActivity(new Intent(activity, Qr_BarcodeActivity.class));

                    }

                } else {
                    if (activity != null)
                        activity.startActivity(new Intent(activity, Qr_BarcodeActivity.class));
                }

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
                if (callback != null) {
                    callback.showDialogSelecteImage();
                }
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
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentProductListDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_product_detail;
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

    }
}
