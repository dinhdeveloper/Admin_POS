package qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho.detail;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ProductListModel;

public interface FragmentDetailXuatNhapKhoViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentDetailXuatNhapKhoViewCallback callback);

    void sendDataToViewDetail(ArrayList<ProductListModel> listModels);
}
