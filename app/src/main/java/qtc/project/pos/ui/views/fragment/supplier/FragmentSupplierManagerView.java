package qtc.project.pos.ui.views.fragment.supplier;

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
import qtc.project.pos.adapter.supplier.SupplierAdapter;
import qtc.project.pos.model.SupplierModel;

public class FragmentSupplierManagerView extends BaseView<FragmentSupplierManagerView.UIContainer> implements FragmentSupplierManagerViewInterface{
    HomeActivity activity;
    FragmentSupplierManagerViewCallback callback;
    ArrayList<SupplierModel> listAll = new ArrayList<>();
    @Override
    public void init(HomeActivity activity, FragmentSupplierManagerViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        backHome();
    }

    @Override
    public void mappingRecyclerView(ArrayList<SupplierModel> list) {
        listAll = list;
        getData(listAll);
    }

    private void getData(ArrayList<SupplierModel> list) {
        SupplierAdapter supplierAdapter = new SupplierAdapter(activity, list);
        ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ui.recycler_view_list.setAdapter(supplierAdapter);
        supplierAdapter.notifyDataSetChanged();

        supplierAdapter.setListener(new SupplierAdapter.SupplierAdapterListener() {
            @Override
            public void setOnClick(SupplierModel model) {
                if (callback!=null)
                    callback.goToDetail(model);
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

        //tao sup new
        ui.image_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.createSupplier();
            }
        });

        //search
        //search customer
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString()!=null){
                        searchSuplier(ui.edit_filter.getText().toString());
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
                if (ui.edit_filter.getText().toString() != null) {
                    searchSuplier(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //xos search
        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
                if (callback!=null)
                    callback.getAllData();
            }
        });
    }

    private void searchSuplier(String filter) {
        if (filter!=null){
            callback.searchSupplier(filter);
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentSupplierManagerView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_supplier_manager;
    }



    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.ic_search)
        public ImageView ic_search;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_create)
        public ImageView image_create;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_close)
        public ImageView image_close;

    }
}
