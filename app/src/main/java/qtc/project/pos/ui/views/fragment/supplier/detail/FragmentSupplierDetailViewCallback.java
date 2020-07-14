package qtc.project.pos.ui.views.fragment.supplier.detail;

import qtc.project.pos.model.SupplierModel;

public interface FragmentSupplierDetailViewCallback {
    void onBackProgress();

    void updateSupplier(SupplierModel supplierModel);

    void deleteSupplier(String id);
}
