package qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho;

import java.util.ArrayList;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.ReportXuatNhapKhoModel;

public interface FragmentBaoCaoXuatNhapKhoViewInterface extends BaseViewInterface {
    void init(HomeActivity activity,FragmentBaoCaoXuatNhapKhoViewCallback callback);

    void sendDataToView(ArrayList<ReportXuatNhapKhoModel> list);
}
