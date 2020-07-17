package qtc.project.pos.ui.views.fragment.product.productlist.create;

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

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.category.ProductItemCategoryAdapter;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.ProductCategoryModel;
import qtc.project.pos.model.ProductListModel;

public class FragmentCreateProductView extends BaseView<FragmentCreateProductView.UIContainer> implements FragmentCreateProductViewInterface{
    HomeActivity activity;
    FragmentCreateProductViewCallback callback;

    String image_pro;
    String  id_category;
    String category_name;
    @Override
    public void init(HomeActivity activity, FragmentCreateProductViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(),activity);
        onClick();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback !=null)
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
                if (callback!=null)
                    callback.getAllProductCategory();
            }
        });

        //tao san pham
        ui.layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListModel listModel = new ProductListModel();
                listModel.setImage(image_pro);
                listModel.setCategory_id(id_category);
                listModel.setCategory_name(category_name);
                listModel.setName(ui.name_product.getText().toString());
                listModel.setId_code(ui.id_product.getText().toString());
                listModel.setDescription(ui.description_product.getText().toString());
                listModel.setQuantity_safetystock(ui.tonkho.getText().toString());
                listModel.setBarcode(ui.barcode.getText().toString());
                listModel.setQr_code(ui.qrcode.getText().toString());

                if (callback!=null)
                    callback.createProduct(listModel);
            }
        });
    }

    @Override
    public void setDataProductImage(String image) {
        image_pro = image;
        AppProvider.getImageHelper().displayImage(image, ui.image_product, null, R.drawable.imageloading, false);

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

        ProductItemCategoryAdapter chooseAdapter = new ProductItemCategoryAdapter(activity,list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recycler_view_list.setAdapter(chooseAdapter);

        chooseAdapter.setOnItemClickListener(new ProductItemCategoryAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClickListener(ProductCategoryModel model) {
                choose_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        id_category = model.getId();
                        category_name = model.getName();
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
    public void showPopupSuccess() {
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
                ui.name_product_category.setText("Chọn");
                ui.name_product.setText(null);
                ui.id_product.setText(null);
                ui.description_product.setText(null);
                ui.tonkho.setText(null);
                ui.barcode.setText(null);
                ui.qrcode.setText(null);
                ui.image_product.setImageResource(R.drawable.imageloading);
                dialog.dismiss();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCreateProductView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_create_product;
    }



    public class UIContainer extends BaseUiContainer {
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

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

        @UiElement(R.id.choose_category)
        public LinearLayout choose_category;

        @UiElement(R.id.name_product_category)
        public TextView name_product_category;

    }
}
