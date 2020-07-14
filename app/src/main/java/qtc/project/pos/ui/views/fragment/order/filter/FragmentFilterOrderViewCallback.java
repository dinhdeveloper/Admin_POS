package qtc.project.pos.ui.views.fragment.order.filter;

public interface FragmentFilterOrderViewCallback {
    void onBackProgress();

    void filterOnDay(String dateStartSelected, String dateEndSelected);
}
