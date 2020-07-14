package qtc.project.pos.ui.views.fragment.report.thongkekho.xuatnhapkho;

import qtc.project.pos.model.ReportXuatNhapKhoModel;

public interface FragmentBaoCaoXuatNhapKhoViewCallback {
    void onBackProgress();

    void filterData();

    void goToDetailXuatNhapKho(ReportXuatNhapKhoModel model);
}
