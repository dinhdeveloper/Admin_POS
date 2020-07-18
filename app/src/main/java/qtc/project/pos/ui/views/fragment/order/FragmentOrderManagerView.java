package qtc.project.pos.ui.views.fragment.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.order.ListOrderManagerAdapter;
import qtc.project.pos.model.OrderCustomerModel;

public class FragmentOrderManagerView extends BaseView<FragmentOrderManagerView.UIContainer> implements FragmentOrderManagerViewInterface {
    HomeActivity activity;
    FragmentOrderManagerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentOrderManagerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
        getAllData();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });

        //loc
        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.goToFilter();
            }
        });

        //clearn
        ui.ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
                if (callback!=null)
                    callback.getAllData();
            }
        });

        //search customer
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString()!=null && !ui.edit_filter.getText().toString().isEmpty()){
                        searchOrder(ui.edit_filter.getText().toString());
                        return true;
                    }
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.ic_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null && !ui.edit_filter.getText().toString().isEmpty()) {
                    searchOrder(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchOrder(String search) {
        if (callback!=null)
            callback.searchOrder(search);
    }

    @Override
    public void initRecyclerViewOrder(ArrayList<OrderCustomerModel> list, String dateStart, String dateEnd) {
        if (dateStart != null && dateEnd != null) {

            ui.layout_show_filter.setVisibility(View.VISIBLE);
            ui.date_start.setText("Ngày bắt đầu: " + dateStart);
            ui.date_end.setText("Ngày kết thúc: " + dateEnd);

            ListOrderManagerAdapter adapter = new ListOrderManagerAdapter(activity, list);
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.setListener(new ListOrderManagerAdapter.ListOrderManagerAdapterListener() {
                @Override
                public void onItemClick(OrderCustomerModel model) {
                    if (callback != null)
                        callback.goToOrderDetail(model);
                }
            });

            //close filter
            ui.image_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ui.layout_show_filter.setVisibility(View.GONE);
                    if (callback != null)
                        callback.getAllData();
                }
            });
        } else {
            ListOrderManagerAdapter adapter = new ListOrderManagerAdapter(activity, list);
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.setListener(new ListOrderManagerAdapter.ListOrderManagerAdapterListener() {
                @Override
                public void onItemClick(OrderCustomerModel model) {
                    if (callback != null)
                        callback.goToOrderDetail(model);
                }
            });
        }
    }

    private void getAllData() {
        if (callback != null)
            callback.getAllData();
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentOrderManagerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_order_manager;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.ic_search)
        public ImageView ic_search;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;

        @UiElement(R.id.ic_close)
        public ImageView ic_close;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.date_start)
        public TextView date_start;

        @UiElement(R.id.date_end)
        public TextView date_end;

        @UiElement(R.id.image_close)
        public ImageView image_close;

        @UiElement(R.id.layout_show_filter)
        public RelativeLayout layout_show_filter;


    }
}
