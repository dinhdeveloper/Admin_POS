package qtc.project.pos.ui.views.fragment.product.quanlylohang;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.product.lohang.ProductListItemQLLHAdapter;
import qtc.project.pos.adapter.product.lohang.ProductListQLLHAdapter;
import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;

public class FragmentQuanLyLoHangView extends BaseView<FragmentQuanLyLoHangView.UIContainer> implements FragmentQuanLyLoHangViewInterface {

    HomeActivity activity;
    FragmentQuanLyLoHangViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentQuanLyLoHangViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();
    }

    @Override
    public void mappingRecyclerView(ArrayList<ProductListModel> list) {
        ProductListQLLHAdapter adapter = new ProductListQLLHAdapter(activity, list);
        ui.recycler_view_qllh.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ui.recycler_view_qllh.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setListener(new ProductListQLLHAdapter.ProductListQLLHAdapterListener() {
            @Override
            public void setOnClick(ProductListModel model) {
                if (model.getListDataProduct().size()>0){
                    ui.recycler_view_qllh_detail.setVisibility(View.VISIBLE);
                    ProductListItemQLLHAdapter qllhAdapter = new ProductListItemQLLHAdapter(activity,model.getListDataProduct(),model.getName(),model.getId());
                    ui.recycler_view_qllh_detail.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    ui.recycler_view_qllh_detail.setAdapter(qllhAdapter);
                    qllhAdapter.notifyDataSetChanged();

                    qllhAdapter.setListener(new ProductListItemQLLHAdapter.ProductListItemQLLHAdapterListener() {
                        @Override
                        public void setOnClick(PackageInfoModel model) {

                        }

                        @Override
                        public void sentDataOnClick(PackageInfoModel infoModel, String name,String id) {
                            if (callback!=null)
                                callback.sentDataToDetail(infoModel,name,id);
                        }
                    });
                }
                else {
                    ui.recycler_view_qllh_detail.setVisibility(View.GONE);
                }
            }
        });
    }

    private void onClick() {

        ui.imageNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackprogress();
            }
        });

        ui.edit_filter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchQLLH(ui.edit_filter.getText().toString());
                    return true;
                }
                Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ui.image_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ui.edit_filter.getText().toString() != null) {
                    searchQLLH(ui.edit_filter.getText().toString());
                } else {
                    Toast.makeText(activity, "Không có kết quả tìm kiếm!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ui.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.edit_filter.setText(null);
                ui.layout_qldh_data.setVisibility(View.GONE);
                ui.layout_qldh_nodata.setVisibility(View.VISIBLE);
            }
        });
    }

    private void searchQLLH(String toString) {
        if (callback != null) {
            if (toString != null) {
                callback.callDataSearch(toString);
                ui.layout_qldh_data.setVisibility(View.VISIBLE);
                ui.layout_qldh_nodata.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentQuanLyLoHangView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_quan_ly_lo_hang;
    }

    public static class UIContainer extends BaseUiContainer {
        @UiElement(R.id.recycler_view_qllh)
        public RecyclerView recycler_view_qllh;

        @UiElement(R.id.recycler_view_qllh_detail)
        public RecyclerView recycler_view_qllh_detail;

        @UiElement(R.id.layout_qldh_data)
        public LinearLayout layout_qldh_data;

        @UiElement(R.id.layout_qldh_nodata)
        public LinearLayout layout_qldh_nodata;

        @UiElement(R.id.edit_filter)
        public EditText edit_filter;

        @UiElement(R.id.image_search)
        public ImageView image_search;

        @UiElement(R.id.imageNav)
        public ImageView imageNav;

        @UiElement(R.id.addLoHang)
        public ImageView addLoHang;

        @UiElement(R.id.image_close)
        public ImageView image_close;


    }
}
