package qtc.project.pos.ui.views.fragment.employee.lichsubanhang;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.employee.HistoryOderEmployeeAdapter;
import qtc.project.pos.adapter.history.HistoryOderCustomerAdapter;
import qtc.project.pos.model.OrderCustomerModel;

public class FragmentHistorySaleEmployeeView extends BaseView<FragmentHistorySaleEmployeeView.UIContainer> implements FragmentHistorySaleEmployeeViewInterface {

    HomeActivity activity;
    FragmentHistorySaleEmployeeViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentHistorySaleEmployeeViewCallback callback) {
        this.callback = callback;
        this.activity = activity;
        onClick();
        
        getData();
    }

    @Override
    public void sendDataToView(ArrayList<OrderCustomerModel> model) {
        if (model != null) {
            HistoryOderEmployeeAdapter adapter = new HistoryOderEmployeeAdapter(activity, model);
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.setListener(new HistoryOderEmployeeAdapter.HistoryOderEmployeeAdapterListener() {
                @Override
                public void onClickItem(OrderCustomerModel model) {
                    if (callback!=null)
                        callback.goToDetailOrder(model);
                }
            });
        }
    }

    private void getData() {
        if (callback!=null)
            callback.getAllDataHistory();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentHistorySaleEmployeeView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_history_sale_employee;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


        @UiElement(R.id.image_search)
        public ImageView image_search;


        @UiElement(R.id.edit_filter)
        public EditText edit_filter;


        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;
        
    }
}
