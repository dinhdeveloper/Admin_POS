package qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay;

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
import qtc.project.pos.adapter.report.hangbanchay.HangBanChayAdapter;
import qtc.project.pos.model.TopProductModel;

public class FragmentSanPhamBanChayView extends BaseView<FragmentSanPhamBanChayView.UIContainer> implements FragmentSanPhamBanChayViewInterface {
    HomeActivity activity;
    FragmentSanPhamBanChayViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentSanPhamBanChayViewCallback callback) {
        this.activity = activity;
        this.callback = callback;
        
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
        //filter
        ui.image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.goToChooseDate();
            }
        });
    }

    @Override
    public void mappingRecyclerView(ArrayList<TopProductModel> list) {
        if (list != null) {
            HangBanChayAdapter adapter = new HangBanChayAdapter(activity, list);
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(adapter);
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentSanPhamBanChayView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_sanpham_banchay;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.search_button)
        public ImageView search_button;

        @UiElement(R.id.image_filter)
        public ImageView image_filter;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;


    }
}
