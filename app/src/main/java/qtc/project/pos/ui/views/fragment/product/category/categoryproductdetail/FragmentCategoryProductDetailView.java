package qtc.project.pos.ui.views.fragment.product.category.categoryproductdetail;

import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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

public class FragmentCategoryProductDetailView extends BaseView<FragmentCategoryProductDetailView.UIContainer> implements FragmentCategoryProductDetailViewInterface {

    HomeActivity activity;
    FragmentCategoryProductDetailViewCallback callback;
    private String user_avata;

    @Override
    public void init(HomeActivity activity, FragmentCategoryProductDetailViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(),activity);
        onClickItem();
    }

    @Override
    public void setDataCategoryDetail(ProductCategoryModel model) {
        if (model!=null){
            ui.id_product_category.setText(model.getId_code());
            if (!TextUtils.isEmpty(model.getName())){
                ui.title_header.setText(model.getName());
                ui.name_product_category.setText(model.getName());
            }
            ui.description_product.setText(model.getDescription());
            AppProvider.getImageHelper().displayImage(Consts.HOST_API+model.getImage(),ui.image_product,null,R.drawable.no_image_full);

            ui.choose_file_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view);
                }
            });

            ui.layout_update.setOnClickListener(view -> {
                ProductCategoryModel categoryModel = new ProductCategoryModel();
                categoryModel.setId(model.getId());
                categoryModel.setName(ui.name_product_category.getText().toString());
                categoryModel.setDescription(ui.description_product.getText().toString());
                categoryModel.setImage(user_avata);
                categoryModel.setId_code(ui.id_product_category.getText().toString());
                if (callback !=null){
                    callback.updateCategoryDetail(categoryModel);
                }
            });
            ui.layout_delete.setOnClickListener(v -> {
                if (callback!=null)
                    callback.deleteProductCategoryModel(model.getId());
            });

        }else {
            ui.layout_delete.setVisibility(View.GONE);
            ui.title_header.setText("Tạo mới danh mục");
            ui.tvTitleUpdate.setText("Tạo mới");
            ui.layout_update.setOnClickListener(v -> {
                ProductCategoryModel categoryModel = new ProductCategoryModel();
                categoryModel.setName(ui.name_product_category.getText().toString());
                categoryModel.setDescription(ui.description_product.getText().toString());
                categoryModel.setImage(user_avata);
                categoryModel.setId_code(ui.id_product_category.getText().toString());
                if (callback !=null){
                    callback.updateCategoryDetail(categoryModel);
                }
            });
        }
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
    }

    @Override
    public void confirm() {

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

        @UiElement(R.id.title_header)
        public TextView title_header;

        @UiElement(R.id.name_product_category)
        public EditText name_product_category;

        @UiElement(R.id.id_product_category)
        public EditText id_product_category;

        @UiElement(R.id.description_product)
        public EditText description_product;

        @UiElement(R.id.choose_file_image)
        public LinearLayout choose_file_image;

        @UiElement(R.id.image_product)
        public ImageView image_product;

        @UiElement(R.id.layout_update)
        public LinearLayout layout_update;

        @UiElement(R.id.tvTitleUpdate)
        public TextView tvTitleUpdate;


        @UiElement(R.id.layout_delete)
        public LinearLayout layout_delete;


    }
}
