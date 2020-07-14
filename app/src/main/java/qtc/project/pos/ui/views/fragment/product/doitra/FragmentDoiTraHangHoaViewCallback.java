package qtc.project.pos.ui.views.fragment.product.doitra;

import qtc.project.pos.model.PackageReturnModel;

public interface FragmentDoiTraHangHoaViewCallback {
    void onBackProgress();

    void getDataDoiTraHangHoa();

    void sentDataToDetailDTHH(PackageReturnModel model);
}
