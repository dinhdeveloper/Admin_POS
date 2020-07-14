package qtc.project.pos.ui.views.fragment.supplier.list;

import qtc.project.pos.model.SupplierModel;

public interface FragmentSupplierViewCallback {
    void onBackProgress();

    void cancelNhaCungUng(SupplierModel model);
}
