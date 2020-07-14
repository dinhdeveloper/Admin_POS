package qtc.project.pos.ui.views.fragment.supplier.create;

import qtc.project.pos.model.SupplierModel;

public interface FragmentCreateSupplierViewCallback {
    void onBackProgress();

    void createSupplier(SupplierModel supplierModel);
}
