package qtc.project.pos.ui.views.fragment.supplier;

import qtc.project.pos.model.SupplierModel;

public interface FragmentSupplierManagerViewCallback {
    void onBackProgress();

    void goToDetail(SupplierModel model);

    void createSupplier();
}
