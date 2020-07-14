package qtc.project.pos.ui.views.fragment.notification;

public interface FragmentNotificationViewCallback {
    void onClickBackHeader();

    void onRequestRefreshListNotification();

    void onRequestLoadMoreListNotification();

    void onRequestCheckViewNotify(String id);

    void onClickShowShoppingCart();

    void onClickFilterProduct();
}
