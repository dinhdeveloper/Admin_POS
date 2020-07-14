package qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.detail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.adapter.report.xuatnhapkho.BaoCaoNhapKhoAdapter;
import qtc.project.pos.adapter.report.xuatnhapkho.BaoCaoXuatKhoAdapter;
import qtc.project.pos.adapter.report.xuatnhapkho.BaoCaoXuatNhapKhoDetailAdapter;
import qtc.project.pos.model.ProductListModel;

public class FragmentDetailXuatNhapKhoView extends BaseView<FragmentDetailXuatNhapKhoView.UIContainer> implements FragmentDetailXuatNhapKhoViewInterface {
    HomeActivity activity;
    FragmentDetailXuatNhapKhoViewCallback callback;

    @Override
    public void init(HomeActivity activity, FragmentDetailXuatNhapKhoViewCallback callback) {
        this.callback = callback;
        this.activity = activity;

        onClick();
    }

    @Override
    public void sendDataToViewDetail(ArrayList<ProductListModel> listModels) {
        if (listModels != null) {
            ui.name_product.setText(listModels.get(0).getName() + " ");

            int quantity_total = 0;

            int sizeListDataProduct = listModels.get(0).getListDataProduct().size();
            int sizeListDataStockOut = listModels.get(0).getListDataStockOutModel().size();

            for (int i = 0; i < sizeListDataProduct; i++) {
                quantity_total += Integer.parseInt(listModels.get(0).getListDataProduct().get(i).getQuantity_storage());
            }
            ui.quantity_product.setText("(" + String.valueOf(quantity_total) + ")");

            BaoCaoXuatNhapKhoDetailAdapter detailAdapter = new BaoCaoXuatNhapKhoDetailAdapter(activity, listModels.get(0).getListDataProduct());
            ui.recycler_view_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            ui.recycler_view_list.setAdapter(detailAdapter);

            ui.quantity_order_nhap.setText(sizeListDataProduct + " đơn");
            ui.quantity_order_xuat.setText(sizeListDataStockOut + " đơn");

            //nhap
            ui.layout_nhap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ui.layout_status.setVisibility(View.VISIBLE);
                    ui.name_status.setText("Nhập kho:");

                    BaoCaoNhapKhoAdapter nhapKhoAdapter = new BaoCaoNhapKhoAdapter(activity, listModels.get(0).getListDataProduct());
                    ui.recycler_view_list_detail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    ui.recycler_view_list_detail.setAdapter(nhapKhoAdapter);
                    detailAdapter.notifyDataSetChanged();
                }
            });
            //xuat
            ui.layout_xuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ui.layout_status.setVisibility(View.VISIBLE);
                    ui.name_status.setText("Xuất kho:");

                    BaoCaoXuatKhoAdapter xuatKhoAdapter = new BaoCaoXuatKhoAdapter(activity, listModels.get(0).getListDataStockOutModel());
                    ui.recycler_view_list_detail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    ui.recycler_view_list_detail.setAdapter(xuatKhoAdapter);
                    xuatKhoAdapter.notifyDataSetChanged();
                }
            });
        }
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

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentDetailXuatNhapKhoView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_bao_cao_xuat_nhap_kho_detail;
    }


    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;

        @UiElement(R.id.name_product)
        public TextView name_product;
        @UiElement(R.id.quantity_product)
        public TextView quantity_product;
        @UiElement(R.id.recycler_view_list)
        public RecyclerView recycler_view_list;

        @UiElement(R.id.quantity_order_xuat)
        public TextView quantity_order_xuat;
        @UiElement(R.id.quantity_order_nhap)
        public TextView quantity_order_nhap;

        @UiElement(R.id.layout_status)
        public LinearLayout layout_status;

        @UiElement(R.id.layout_xuat)
        public LinearLayout layout_xuat;

        @UiElement(R.id.layout_nhap)
        public LinearLayout layout_nhap;


        @UiElement(R.id.name_status)
        public TextView name_status;

        @UiElement(R.id.recycler_view_list_detail)
        public RecyclerView recycler_view_list_detail;


    }
}
