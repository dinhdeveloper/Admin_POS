package qtc.project.pos.ui.views.fragment.levelcustomer.detail;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.widgets.popupmenu.ActionItem;
import b.laixuantam.myaarlibrary.widgets.popupmenu.MyCustomPopupMenu;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.customer.CustomerAdapter;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.helper.Consts;
import qtc.project.pos.model.CustomerModel;
import qtc.project.pos.model.LevelCustomerModel;

public class FragmentLevelCustomerDetailView extends BaseView<FragmentLevelCustomerDetailView.UIContainer> implements FragmentLevelCustomerDetailViewInterface {
    HomeActivity activity;
    FragmentLevelCustomerDetailViewCallback callback;
    String image_url;
    String level_id;

    @Override
    public void init(HomeActivity activity, FragmentLevelCustomerDetailViewCallback callback) {
        this.callback = callback;
        this.activity = activity;

        backHome();
        xulySearch();
    }

    private void xulySearch() {
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCustomer(ui.edit_filter.getText().toString(), level_id);
                    return true;
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null) {
                    searchCustomer(ui.edit_filter.getText().toString(), level_id);
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchCustomer(String toString, String id) {
        if (callback != null) {
            if (toString != null) {
                callback.callDataSearchCus(toString, id);
            }
        }
    }

    private void backHome() {
        //back
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
    }

    @Override
    public void sendDataToView(LevelCustomerModel model) {
        level_id = model.getId();
        ui.name_level_customer_header.setText(model.getName());
        ui.id_level_customer.setText(model.getId_code());
        ui.name_level_customer.setText(model.getName());
        ui.discount_level.setText(model.getDiscount());
        ui.description_level.setText(model.getDescription());
        ui.so_nguoi_nhan_duoc_cap_do.setText("6. Số người nhận được cấp độ "+model.getName());
        AppProvider.getImageHelper().displayImage(Consts.HOST_API + model.getImage(), ui.image_level, null, R.drawable.imageloading);
        ui.tongkhachhang.setText("Có tất cả " + model.getTotal_customer() + " khách hàng.");

        ui.choose_file_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);
            }
        });
        //xu ly man hinh xem chi tiet
        ui.xemchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callback != null)
                    callback.xemSoNguoiCoCapDo(model.getId());

                ui.layout_detail_all.setVisibility(View.GONE);
                ui.layout_detail_persion.setVisibility(View.VISIBLE);
            }
        });

        ui.txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.layout_detail_all.setVisibility(View.VISIBLE);
                ui.layout_detail_persion.setVisibility(View.GONE);
            }
        });

        ui.layout_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LevelCustomerModel levelCustomerModel = new LevelCustomerModel();
                levelCustomerModel.setId(model.getId());
                levelCustomerModel.setId_code(model.getId_code());
                levelCustomerModel.setName(ui.name_level_customer.getText().toString());
                levelCustomerModel.setDiscount(ui.discount_level.getText().toString());
                levelCustomerModel.setDescription(ui.description_level.getText().toString());
                levelCustomerModel.setImage(image_url);

                if (callback != null) {
                    callback.updateData(levelCustomerModel);
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
                content_text.setText("Bạn có muốn xóa cấp độ này không?");

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
                            callback.deleteLevelCustomer(level_id);
                        dialog.dismiss();
                    }
                });
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

    @Override
    public void setDataLevelImage(String outfile) {
        image_url = outfile;
        AppProvider.getImageHelper().displayImage(outfile, ui.image_level, null, R.drawable.imageloading, false);
        changeStateBtnSubmitUpdate(true);
    }

    @Override
    public void initCustomer(ArrayList<CustomerModel> list) {

        CustomerAdapter customerAdapter = new CustomerAdapter(activity, list);
        ui.recycler_view_list_customer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ui.recycler_view_list_customer.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
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

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentLevelCustomerDetailView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_level_customer_detail;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.name_level_customer_header)
        public TextView name_level_customer_header;

        @UiElement(R.id.recycler_view_list_customer)
        public RecyclerView recycler_view_list_customer;

        @UiElement(R.id.tongkhachhang)
        public TextView tongkhachhang;

        @UiElement(R.id.choose_file_image)
        public LinearLayout choose_file_image;

        @UiElement(R.id.id_level_customer)
        public TextView id_level_customer;

        @UiElement(R.id.so_nguoi_nhan_duoc_cap_do)
        public TextView so_nguoi_nhan_duoc_cap_do;



        @UiElement(R.id.name_level_customer)
        public EditText name_level_customer;

        @UiElement(R.id.discount_level)
        public EditText discount_level;

        @UiElement(R.id.description_level)
        public EditText description_level;

        @UiElement(R.id.xemchitiet)
        public LinearLayout xemchitiet;

        @UiElement(R.id.layout_update)
        public LinearLayout layout_update;

        @UiElement(R.id.layout_delete)
        public LinearLayout layout_delete;

        @UiElement(R.id.image_level)
        public ImageView image_level;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.layout_detail_persion)
        public LinearLayout layout_detail_persion;

        @UiElement(R.id.layout_detail_all)
        public NestedScrollView layout_detail_all;

        @UiElement(R.id.txtBack)
        public TextView txtBack;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_search)
        public ImageView image_search;


    }
}
