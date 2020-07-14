package qtc.project.pos.ui.views.fragment.history.history;

import qtc.project.pos.model.OrderCustomerModel;

public interface FragmentHistoryOrderCustomerViewCallback {
    void onBackProgress();

    void sentDataToDetail(OrderCustomerModel model);
}
