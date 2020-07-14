package qtc.project.pos.ui.views.activity.notify_manager_activity;

import android.widget.FrameLayout;
import android.widget.TextView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import qtc.project.pos.R;

public class NotificationManagerActivityView extends BaseView<NotificationManagerActivityView.UIContainer> implements NotificationManagerActivityViewInterface {

    @Override
    public void init(NotificationManagerActivityViewCallback callback) {
    }

    @Override
    public void setTitleHeader(String title) {
        ui.tvTitleHeader.setText(title);
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new NotificationManagerActivityView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.activity_notifycation_manager;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.tvTitleHeader)
        public TextView tvTitleHeader;

        @UiElement(R.id.LayoutBaseMainFragmentActivity)
        public FrameLayout LayoutBaseMainFragmentActivity;
    }
}
