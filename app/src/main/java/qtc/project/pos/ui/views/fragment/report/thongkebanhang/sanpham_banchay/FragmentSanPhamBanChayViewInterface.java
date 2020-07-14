package qtc.project.pos.ui.views.fragment.report.thongkebanhang.sanpham_banchay;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.TopProductModel;

public interface FragmentSanPhamBanChayViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentSanPhamBanChayViewCallback callback);

    void mappingRecyclerView(ArrayList<TopProductModel> list);
}
