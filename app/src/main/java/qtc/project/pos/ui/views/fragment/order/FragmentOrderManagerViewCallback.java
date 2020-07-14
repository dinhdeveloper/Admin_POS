package qtc.project.pos.ui.views.fragment.order;

import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentOrderManagerViewCallback {
    void callDataOrder();

    void onBackProgress();

    void goToOrderDetail(OrderCustomerModel model);

    void goToFilter();
}
