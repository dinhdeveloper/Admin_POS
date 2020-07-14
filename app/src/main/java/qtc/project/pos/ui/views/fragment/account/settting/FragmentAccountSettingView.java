package qtc.project.pos.ui.views.fragment.account.settting;

import android.view.View;
import android.widget.TextView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.widgets.scaletouchlistener.ScaleTouchListener;
import qtc.project.pos.R;

public class FragmentAccountSettingView extends BaseView<FragmentAccountSettingView.UIContainer> implements FragmentAccountSettingViewInterface {

    @Override
    public void init(FragmentAccountSettingViewCallback callback) {

        ui.tvTitleHeader.setText("Thiết lập tài khoản");

        ui.btnBackHeader.setOnTouchListener(new ScaleTouchListener(confScaleTouch) {
            @Override
            public void onClick(View v) {
                callback.onClickBackHeader();
            }
        });

        ui.btnProfileManager.setOnClickListener(v -> callback.onClickProfileManager());

        ui.btnLogout.setOnClickListener(v -> callback.onClickLogout());

        ui.btnPasswordManager.setOnClickListener(v -> callback.onClickShowPasswordManager());
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentAccountSettingView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_account_settings;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.btnBackHeader)
        public View btnBackHeader;

        @UiElement(R.id.tvTitleHeader)
        public TextView tvTitleHeader;

        @UiElement(R.id.btnProfileManager)
        public View btnProfileManager;

        @UiElement(R.id.btnPasswordManager)
        public View btnPasswordManager;

        @UiElement(R.id.btnLogout)
        public View btnLogout;


    }
}
