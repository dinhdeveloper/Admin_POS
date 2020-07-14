package qtc.project.pos.ui.views.fragment.supplier.list;

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
import qtc.project.pos.adapter.supplier.SupplierAdapter;
import qtc.project.pos.model.SupplierModel;

public class FragmentSupplierView extends BaseView<FragmentSupplierView.UIContainer> implements FragmentSupplierViewInterface {

    HomeActivity activity;
    FragmentSupplierViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentSupplierViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        backHome();
    }

    @Override
    public void mappingRecyclerView(ArrayList<SupplierModel> list) {
        SupplierAdapter supplierAdapter = new SupplierAdapter(activity, list);
        ui.recycler_view_supplier.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ui.recycler_view_supplier.setAdapter(supplierAdapter);
        supplierAdapter.notifyDataSetChanged();

        supplierAdapter.setListener(new SupplierAdapter.SupplierAdapterListener() {
            @Override
            public void setOnClick(SupplierModel model) {
                if (callback!=null)
                    callback.cancelNhaCungUng(model);
            }
        });
    }

    private void backHome() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentSupplierView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_supplier_list;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_supplier)
        public RecyclerView recycler_view_supplier;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
