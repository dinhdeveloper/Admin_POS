package qtc.project.pos.ui.views.fragment.product.quanlylohang;

import qtc.project.pos.model.PackageInfoModel;
import qtc.project.pos.model.ProductListModel;

public interface FragmentQuanLyLoHangViewCallback {
    void onBackprogress();
    void callDataSearch(String toString);

    void sentDataToDetail(PackageInfoModel model,String  name_product,String id_product);
}
