package qtc.project.pos.ui.views.fragment.order;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
                if (callback!=null)
                    callback.onBackProgress();
            }
        });

        //loc
        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.goToFilter();
            }
        });
    }

    @Override
    public void initRecyclerViewOrder(ArrayList<OrderCustomerModel> list,String dateStart, String dateEnd) {
        ListOrderManagerAdapter adapter = new ListOrderManagerAdapter(activity,list);
        ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ui.recycler_view_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new ListOrderManagerAdapter.ListOrderManagerAdapterListener() {
            @Override
            public void onItemClick(OrderCustomerModel model) {
                if (callback!=null)
                    callback.goToOrderDetail(model);
            }
        });

        if (dateStart !=null && dateEnd!=null){

            ui.layout_show_filter.setVisibility(View.VISIBLE);
            ui.date_start.setText("Ngày bắt đầu: "+dateStart);
            ui.date_end.setText("Ngày kết thúc: "+dateEnd);

            //close filter
            ui.image_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ui.layout_show_filter.setVisibility(View.GONE);
                }
            });
        }
        else {
            ui.layout_show_filter.setVisibility(View.GONE);
        }
    }

    private void getAllData() {
        if (callback!=null)
            callback.callDataOrder();
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
