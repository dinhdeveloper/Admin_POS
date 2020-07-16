package qtc.project.pos.ui.views.fragment.history.history;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;

import qtc.project.pos.adapter.history.HistoryOderCustomerAdapter;
import qtc.project.pos.model.OrderCustomerModel;

public class FragmentHistoryOrderCustomerView extends BaseView<FragmentHistoryOrderCustomerView.UIContainer> implements FragmentHistoryOrderCustomerViewInterface {
    HomeActivity activity;
    FragmentHistoryOrderCustomerViewCallback callback;
    String customer_id;

    @Override
    public void init(HomeActivity activity, FragmentHistoryOrderCustomerViewCallback callback) {
        this.callback = callback;
        this.activity = activity;
        onClick();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });

        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString()!=null){
                        searchHistoryOrder(ui.edit_filter.getText().toString());
                        return true;
                    }
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null) {
                    searchHistoryOrder(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //close
        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
            }
        });
    }

    private void searchHistoryOrder(String order_id) {
        if (callback!=null){
            callback.searchHistoryOrderCustomer(order_id,customer_id);
        }
    }

    @Override
    public void sendDataToView(ArrayList<OrderCustomerModel> model,String id_customer) {
        if (model != null) {
            customer_id = id_customer;
            HistoryOderCustomerAdapter adapter = new HistoryOderCustomerAdapter(activity, model);
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.setListener(new HistoryOderCustomerAdapter.HistoryOderCustomerAdapterListener() {
                @Override
                public void onClickItem(OrderCustomerModel model) {
                    if (callback!=null){
                        callback.sentDataToDetail(model);
                    }
                }
            });
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentHistoryOrderCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_history_order_customer;
    }

    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.image_close)
        public ImageView image_close;


        @UiElement(R.id.edit_filter)
        public EditText edit_filter;


    }
}
