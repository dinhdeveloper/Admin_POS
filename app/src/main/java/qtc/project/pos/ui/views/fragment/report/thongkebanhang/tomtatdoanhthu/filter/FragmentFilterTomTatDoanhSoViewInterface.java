package qtc.project.pos.ui.views.fragment.report.thongkebanhang.tomtatdoanhthu.filter;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;

public interface FragmentFilterTomTatDoanhSoViewInterface extends BaseViewInterface {

    void init(HomeActivity activity,FragmentFilterTomTatDoanhSoViewCallback callback);

    void sentDataDayChoose(int date);
}
