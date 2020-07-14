package qtc.project.pos.ui.views.fragment.levelcustomer;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.levelcustomer.LevelCustomerAdapter;
import qtc.project.pos.fragment.levelcustomer.FragmentCreateLevelCustomer;
import qtc.project.pos.fragment.product.productcategory.FragmentCreateProductCategory;
import qtc.project.pos.model.LevelCustomerModel;
import qtc.project.pos.ui.views.fragment.customer.FragmentCustomerView;
import qtc.project.pos.ui.views.fragment.customer.FragmentCustomerViewInterface;

public class FragmentLevelCustomerView extends BaseView<FragmentLevelCustomerView.UIContainer> implements FragmentLevelCustomerViewInterface {

    HomeActivity activity;
    FragmentLevelCustomerViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentLevelCustomerViewCallback callback) {
        this.callback = callback;
        this.activity = activity;

        callDataLevelCustomer();
        onClick();
    }

    private void onClick() {
        //back
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });
        ui.image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(new FragmentCreateLevelCustomer(),true,null);
            }
        });
    }

    @Override
    public void initRecyclerView(ArrayList<LevelCustomerModel> list) {
        LevelCustomerAdapter adapter = new LevelCustomerAdapter(activity, list);
        ui.recycler_view_level_customer.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ui.recycler_view_level_customer.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setListener(new LevelCustomerAdapter.CustomerLevelAdapterListener() {
            @Override
            public void onItemClick(LevelCustomerModel model) {
                callback.sendDataToDetail(model);
            }
        });
    }

    //lay du lieu level customer ra recyclerview
    private void callDataLevelCustomer() {
        if (callback != null)
            callback.callDataLevelCustomer();
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentLevelCustomerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_level_customer;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_level_customer)
        public RecyclerView recycler_view_level_customer;

        @UiElement(R.id.image_add)
        public ImageView image_add;

         @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
