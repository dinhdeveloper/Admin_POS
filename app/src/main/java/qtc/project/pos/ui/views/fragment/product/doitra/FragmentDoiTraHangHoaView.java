package qtc.project.pos.ui.views.fragment.product.doitra;

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
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.doitra.ProductListDTHHAdapter;
import qtc.project.pos.fragment.product.doitra.FragmentFilterDoiTraHangHoa;
import qtc.project.pos.model.PackageReturnModel;

public class FragmentDoiTraHangHoaView extends BaseView<FragmentDoiTraHangHoaView.UIContainer> implements FragmentDoiTraHangHoaViewInterface {

    HomeActivity activity;
    FragmentDoiTraHangHoaViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentDoiTraHangHoaViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        KeyboardUtils.setupUI(getView(),activity);
        callBack();

        getData();
    }

    @Override
    public void mappingRecyclerView(ArrayList<PackageReturnModel> list) {
        if (list!=null){
            ProductListDTHHAdapter adapter = new ProductListDTHHAdapter(activity, list);
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            adapter.setListener(new ProductListDTHHAdapter.ProductListDTHHAdapterListener() {
                @Override
                public void onClickItem(PackageReturnModel model) {
                    if (callback!=null)
                        callback.sentDataToDetailDTHH(model);
                }
            });
        }
        else {
            Toast.makeText(activity, "Không có kết quả tìm kiếm", Toast.LENGTH_SHORT).show();
        }
    }

    private void getData() {
        if (callback != null) {
            callback.getDataDoiTraHangHoa();
        }
    }

    private void callBack() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
        //fillter
        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity!=null)
                    activity.replaceFragment(new FragmentFilterDoiTraHangHoa(),true,null);
            }
        });

        //search customer
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString()!=null&& !ui.edit_filter.getText().toString().isEmpty()){
                        searchPackInfo(ui.edit_filter.getText().toString());
                        return true;
                    }
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null&& !ui.edit_filter.getText().toString().isEmpty()) {
                    searchPackInfo(ui.edit_filter.getText().toString());
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

    private void searchPackInfo(String search) {
        if (callback!=null)
            callback.searchData(search);
    }


    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentDoiTraHangHoaView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_doi_tra_hang_hoa;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.search_button)
        public ImageView search_button;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;

        @UiElement(R.id.image_close)
        public ImageView image_close;


    }
}
