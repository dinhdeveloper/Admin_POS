package qtc.project.pos.ui.views.fragment.product.category.createcategoryproduct;

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
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import b.laixuantam.myaarlibrary.widgets.popupmenu.ActionItem;
import b.laixuantam.myaarlibrary.widgets.popupmenu.MyCustomPopupMenu;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.ProductCategoryModel;

public class FragmentCreateProductCategoryView extends BaseView<FragmentCreateProductCategoryView.UIContainer> implements FragmentCreateProductCategoryViewInterface {

    HomeActivity activity;
    FragmentCreateProductCategoryViewCallback callback;
    ProductCategoryModel categoryModel;

    String image_pro;

    @Override
    public void init(HomeActivity activity, FragmentCreateProductCategoryViewCallback callback) {
        this.callback = callback;
        this.activity = activity;
        KeyboardUtils.setupUI(getView(),activity);
        onClick();
        createProductCategory();
    }

    @Override
    public void setDataProductImage(String outfile) {
        image_pro = outfile;
        AppProvider.getImageHelper().displayImage(outfile, ui.image_product, null, R.drawable.imageloading, false);
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback!=null){
                    callback.onBackProgress();
                }
            }
        });

        ui.choose_file_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
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

    private void createProductCategory() {

        ui.layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppProvider.getImageHelper().displayImage(image_pro,ui.image_product,null,R.drawable.imageloading);
                categoryModel = new ProductCategoryModel();
                categoryModel.setImage(image_pro);
                categoryModel.setDescription(ui.description_product.getText().toString());
                categoryModel.setName(ui.name_product_category.getText().toString());
                categoryModel.setId_code(ui.id_product_category.getText().toString());
                if (callback != null) {
                    callback.createCategoryProduct(categoryModel);
                }
            }
        });

    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCreateProductCategoryView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_create_product_category_detail;
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
        content_text.setText("Bạn đã tạo mới thành công!");

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        custom_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.id_product_category.setText(null);
                ui.name_product_category.setText(null);
                ui.description_product.setText(null);
                ui.image_product.setImageResource(R.drawable.imageloading);
                dialog.dismiss();
            }
        });
    }


    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.name_product_category)
        public EditText name_product_category;

        @UiElement(R.id.description_product)
        public EditText description_product;

        @UiElement(R.id.id_product_category)
        public EditText id_product_category;

        @UiElement(R.id.choose_file_image)
        public LinearLayout choose_file_image;

        @UiElement(R.id.image_product)
        public ImageView image_product;

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
