package qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.KeyboardUtils;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.report.xuatnhapkho.BaoCaoXuatNhapKhoAdapter;
import qtc.project.pos.model.ReportXuatNhapKhoModel;

public class FragmentBaoCaoXuatNhapKhoView extends BaseView<FragmentBaoCaoXuatNhapKhoView.UIContainer> implements FragmentBaoCaoXuatNhapKhoViewInterface {

    HomeActivity activity;
    FragmentBaoCaoXuatNhapKhoViewCallback callback;
    BaoCaoXuatNhapKhoAdapter adapter;
    @Override
    public void init(HomeActivity activity, FragmentBaoCaoXuatNhapKhoViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        KeyboardUtils.setupUI(getView(),activity);

        ui.edit_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter!= null)
                    adapter.getFilter().filter(s);
            }
        });

        onClick();
        
        getAllData();
    }

    private void getAllData() {
        if (callback!=null){
            callback.getAllData();
        }
    }

    @Override
    public void sendDataToView(ArrayList<ReportXuatNhapKhoModel> list) {
        adapter = new BaoCaoXuatNhapKhoAdapter(activity,list);
        adapter.getListData().addAll(list);
        adapter.getListDataBackup().addAll(list);
        ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ui.recycler_view_list.setAdapter(adapter);

        adapter.setListener(new BaoCaoXuatNhapKhoAdapter.BaoCaoXuatNhapKhoAdapterListener() {
            @Override
            public void onClickItem(ReportXuatNhapKhoModel model) {
                if (callback!=null)
                    callback.goToDetailXuatNhapKho(model);
            }
        });
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });

        //search
        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.filterData();
            }
        });

        //search customer
        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ui.edit_filter.getText().toString()!=null && !ui.edit_filter.getText().toString().isEmpty()){
                        searchKho(ui.edit_filter.getText().toString());
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
                if (ui.edit_filter.getText().toString() != null && !ui.edit_filter.getText().toString().isEmpty()) {
                    searchKho(ui.edit_filter.getText().toString());
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

    private void searchKho(String search) {
        if (callback!=null)
            callback.goToSearch(search);
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentBaoCaoXuatNhapKhoView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_bao_cao_xuat_nhap_kho;
    }



    public class UIContainer extends BaseUiContainer {

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;

        @UiElement(R.id.image_close)
        public ImageView image_close;


        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

    }
}
