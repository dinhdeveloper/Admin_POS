package qtc.project.pos.ui.views.fragment.levelcustomer.create;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.model.LevelCustomerModel;

public class FragmentCreateLevelCustomerView extends BaseView<FragmentCreateLevelCustomerView.UIContainer> implements FragmentCreateLevelCustomerViewInterface {

    HomeActivity activity;
    FragmentCreateLevelCustomerViewCallback callback;

    String image_level;

    @Override
    public void init(HomeActivity activity, FragmentCreateLevelCustomerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    @Override
    public void setDataProductImage(String filePath) {
        image_level = filePath;
        AppProvider.getImageHelper().displayImage(filePath, ui.image_level, null, R.drawable.imageloading, false);
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.onBackProgress();
                }
            }
        });

        ui.choose_file_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null) {
                    callback.showDialogSelecteImage();
                }
            }
        });

        ui.layout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LevelCustomerModel levelCustomerModel = new LevelCustomerModel();
                levelCustomerModel.setId_code(ui.id_level.getText().toString());
                levelCustomerModel.setName(ui.name_level_customer.getText().toString());
                levelCustomerModel.setDiscount(ui.discount_level.getText().toString());
                levelCustomerModel.setDescription(ui.description_level.getText().toString());
                levelCustomerModel.setImage(image_level);

                if (callback!=null)
                    callback.createLevelCustomer(levelCustomerModel);
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCreateLevelCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_create_level_customer;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.choose_file_image)
        public LinearLayout choose_file_image;

        @UiElement(R.id.image_level)
        public ImageView image_level;

        @UiElement(R.id.id_level)
        public EditText id_level;

        @UiElement(R.id.name_level_customer)
        public EditText name_level_customer;

        @UiElement(R.id.discount_level)
        public EditText discount_level;

        @UiElement(R.id.description_level)
        public EditText description_level;

        @UiElement(R.id.layout_create)
        public LinearLayout layout_create;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
