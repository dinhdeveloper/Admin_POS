package qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.report.xuatnhapkho.BaoCaoXuatNhapKhoAdapter;
import qtc.project.pos.model.ReportXuatNhapKhoModel;

public class FragmentBaoCaoXuatNhapKhoView extends BaseView<FragmentBaoCaoXuatNhapKhoView.UIContainer> implements FragmentBaoCaoXuatNhapKhoViewInterface {

    HomeActivity activity;
    FragmentBaoCaoXuatNhapKhoViewCallback callback;
    @Override
    public void init(HomeActivity activity, FragmentBaoCaoXuatNhapKhoViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    @Override
    public void sendDataToView(ArrayList<ReportXuatNhapKhoModel> list) {

        ui.recycler_view_list.setVisibility(View.VISIBLE);
        ui.layout_nodata.setVisibility(View.GONE);

        BaoCaoXuatNhapKhoAdapter adapter = new BaoCaoXuatNhapKhoAdapter(activity,list);
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

        @UiElement(R.id.search_button)
        public ImageView search_button;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.layout_nodata)
        public LinearLayout layout_nodata;


    }
}
