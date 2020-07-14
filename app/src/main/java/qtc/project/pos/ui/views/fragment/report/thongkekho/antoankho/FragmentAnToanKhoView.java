package qtc.project.pos.ui.views.fragment.report.thongkekho.antoankho;

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
import qtc.project.pos.adapter.report.antoankho.BaoCaoAnToanKhoAdapter;
import qtc.project.pos.model.AnToanKhoModel;

public class FragmentAnToanKhoView extends BaseView<FragmentAnToanKhoView.UIContainer> implements FragmentAnToanKhoViewInterface {

    HomeActivity activity;
    FragmentAnToanKhoViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentAnToanKhoViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
        getDataAnToanKho();
    }

    @Override
    public void sendDataToView(ArrayList<AnToanKhoModel> list) {
        BaoCaoAnToanKhoAdapter khoAdapter = new BaoCaoAnToanKhoAdapter(activity, list);
        ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ui.recycler_view_list.setAdapter(khoAdapter);
        khoAdapter.notifyDataSetChanged();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
    }

    private void getDataAnToanKho() {
        if (callback != null)
            callback.getAllDataAnToanKho();
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentAnToanKhoView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_an_toan_kho;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.search_button)
        public ImageView search_button;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

    }
}
