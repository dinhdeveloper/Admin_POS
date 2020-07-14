package qtc.project.pos.ui.views.fragment.customer;

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
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.customer.ListCustomerAdapter;
import qtc.project.pos.model.CustomerModel;

public class FragmentCustomerView extends BaseView<FragmentCustomerView.UIContainer> implements FragmentCustomerViewInterface {
    HomeActivity activity;
    FragmentCustomerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentCustomerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();

        callData();
    }

    @Override
    public void mappingRecyclerView(ArrayList<CustomerModel> list) {
        ListCustomerAdapter adapter = new ListCustomerAdapter(activity, list);
        ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ui.recycler_view_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new ListCustomerAdapter.ListCustomerAdapterListener() {
            @Override
            public void onClickItem(CustomerModel model) {
                LayoutInflater layoutInflater = activity.getLayoutInflater();
                View popupView = layoutInflater.inflate(R.layout.custom_popup_choose_customer, null);
                LinearLayout item_history_order = popupView.findViewById(R.id.item_history_order);
                LinearLayout item_detail = popupView.findViewById(R.id.item_detail);

                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setView(popupView);
                AlertDialog dialog = alert.create();
                //dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                item_history_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback!=null){
                            callback.getHistoryOrderCustomer(model);
                            dialog.dismiss();
                        }
                    }
                });

                item_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (callback!=null){
                            callback.getCustomerDetail(model);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void callData() {
        if (callback != null)
            callback.getDataCustomer();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackprogress();
            }
        });

        //create customer
        ui.image_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.createCustomer();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_customer_manager;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.image_create)
        public ImageView image_create;

        @UiElement(R.id.ic_search)
        public ImageView ic_search;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;


    }
}
