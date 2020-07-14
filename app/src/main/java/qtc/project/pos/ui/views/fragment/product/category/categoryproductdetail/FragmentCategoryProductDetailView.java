package qtc.project.pos.ui.views.fragment.product.category.categoryproductdetail;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.ProductCategoryModel;

public class FragmentCategoryProductDetailView extends BaseView<FragmentCategoryProductDetailView.UIContainer> implements FragmentCategoryProductDetailViewInterface {

    HomeActivity activity;
    FragmentCategoryProductDetailViewCallback callback;
    private String user_avata;

    @Override
    public void init(HomeActivity activity, FragmentCategoryProductDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClickItem();
    }

    @Override
    public void sendDataToView(ProductCategoryModel model) {
        ui.name_product_category_header.setText(model.getName());
        ui.name_product_category.setText(model.getName());
        ui.description_product.setText(model.getDescription());
        ui.choose_file_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.showDialogSelecteImage();
                }
            }
        });

        ui.layout_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductCategoryModel categoryModel = new ProductCategoryModel();
                categoryModel.setId(model.getId());
                categoryModel.setName(ui.name_product_category.getText().toString());
                categoryModel.setDescription(ui.description_product.getText().toString());
                categoryModel.setImage(user_avata);
                categoryModel.setId_code(ui.id_product_category.getText().toString());
                if (callback !=null){
                    callback.updateData(categoryModel);
                }
            }
        });

        ui.layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.alert_dialog_waiting, null);
                TextView title_text = popupView.findViewById(R.id.title_text);
                TextView content_text = popupView.findViewById(R.id.content_text);
                Button cancel_button = popupView.findViewById(R.id.cancel_button);
                Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

                title_text.setText("Cảnh báo");
                content_text.setText("Bạn có muốn xóa danh mục này không?");

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
                            callback.deleteProductCategoryModel(model.getId());
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public void setDataProductImage(String outfile) {
        user_avata = outfile;
        AppProvider.getImageHelper().displayImage(outfile, ui.image_product, null, R.drawable.imageloading, false);
        changeStateBtnSubmitUpdate(true);
    }

    @Override
    public void onBack() {
        if (callback != null)
            callback.onBackProgress();
    }

    @Override
    public void confirmDialog() {
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
    public void confirm() {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.alert_dialog_success, null);
        TextView title_text = popupView.findViewById(R.id.title_text);
        TextView content_text = popupView.findViewById(R.id.content_text);
        Button custom_confirm_button = popupView.findViewById(R.id.custom_confirm_button);

        title_text.setText("Xác nhận");
        content_text.setText("Bạn đã xóa danh mục này thành công!");

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

    public void changeStateBtnSubmitUpdate(boolean active) {
        if (active) {
            ui.layout_update.setEnabled(true);
            ui.layout_update.setVisibility(View.VISIBLE);
            ui.layout_update.setBackgroundResource(R.drawable.custom_background_button_login);
        } else {
            ui.layout_update.setEnabled(false);
            ui.layout_update.setBackgroundResource(R.drawable.button_login_background_disable);
        }
    }


    private void onClickItem() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCategoryProductDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_category_product_detail;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.name_product_category_header)
        public TextView name_product_category_header;

        @UiElement(R.id.name_product_category)
        public EditText name_product_category;

        @UiElement(R.id.id_product_category)
        public TextView id_product_category;

        @UiElement(R.id.description_product)
        public EditText description_product;

        @UiElement(R.id.choose_file_image)
        public LinearLayout choose_file_image;

        @UiElement(R.id.image_product)
        public ImageView image_product;

        @UiElement(R.id.layout_update)
        public LinearLayout layout_update;

        @UiElement(R.id.layout_delete)
        public LinearLayout layout_delete;


    }
}
