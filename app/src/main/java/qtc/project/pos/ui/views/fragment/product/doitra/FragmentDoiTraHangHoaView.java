package qtc.project.pos.ui.views.fragment.product.doitra;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseFragment;
import b.laixuantam.myaarlibrary.base.BaseParameters;
import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.doitra.ProductListDTHHAdapter;
import qtc.project.pos.model.PackageReturnModel;

public class FragmentDoiTraHangHoaView extends BaseView<FragmentDoiTraHangHoaView.UIContainer> implements FragmentDoiTraHangHoaViewInterface {

    HomeActivity activity;
    FragmentDoiTraHangHoaViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentDoiTraHangHoaViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        callBack();

        getData();
    }

    @Override
    public void mappingRecyclerView(ArrayList<PackageReturnModel> list) {
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


    }
}
