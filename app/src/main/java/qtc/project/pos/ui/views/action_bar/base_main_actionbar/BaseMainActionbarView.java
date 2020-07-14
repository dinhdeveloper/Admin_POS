package qtc.project.pos.ui.views.action_bar.base_main_actionbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.AppUtils;
import qtc.project.pos.R;

/**
 * Created by laixuantam on 23/7/18.
 */

public class BaseMainActionbarView extends BaseView<BaseMainActionbarView.UIContainer> implements BaseMainActionbarViewInterface, View.OnClickListener {

    private BaseMainActionbarViewCallback callback;

    private boolean showFilter = false;

    @Override
    public void initialize(String title, final BaseMainActionbarViewCallback callback) {
        this.callback = callback;

        setTitle(title);

        ui.btnLeftActionBar.setOnClickListener(this);
        ui.btnRightActionBar.setOnClickListener(this);
        ui.buttonBackFilter.setOnClickListener(this);

        ui.buttonClear.setOnClickListener(this);

        ui.editFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                callback.onFiltering(s.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLeftActionBar:
                callback.onClickButtonLeftActionbar();
                break;

            case R.id.btnRightActionBar:
                callback.onClickButtonRightActionbar();
                break;

            case R.id.button_clear:
                ui.editFilter.setText("");
                break;

            case R.id.button_back_filter:
                showFilter = false;
                toggleFilter();

                break;
        }

    }

    @Override
    public void configButtonLeftActionBar(int iconLeft, int tintColor) {

        ui.btnLeftActionBar.setImageResource(iconLeft);
        ui.btnLeftActionBar.setColorFilter(getContext().getResources().getColor(tintColor));
    }

    @Override
    public void configButtonRightActionBar(int iconRight, int tintColor) {

        ui.btnRightActionBar.setImageResource(iconRight);
        ui.btnRightActionBar.setColorFilter(getContext().getResources().getColor(tintColor));
    }

    @Override
    public void showButtonBackFilter() {
        setVisible(ui.buttonBackFilter);
    }

    @Override
    public void hideButtonBackFilter() {
        setGone(ui.buttonBackFilter);
    }

    @Override
    public void setSubtitle(String subtitle) {

        setVisible(ui.subtitle);
        ui.subtitle.setText(subtitle);

    }

    @Override
    public void showLayoutFilter() {
        showFilter = true;
        toggleFilter();
    }

    @Override
    public void hideLayoutFilter() {
        setGone(ui.layoutFilter);
    }

    @Override
    public void configBackgroundLayoutFilter(int backgroundCorlor) {
        ui.layoutFilter.setBackgroundColor(getContext().getResources().getColor(backgroundCorlor));
    }

    @Override
    public void configBackgroundLayoutFilterContainer(int drawableBackground) {
        ui.layout_filter_container.setBackground(getContext().getResources().getDrawable(drawableBackground));
    }

    @Override
    public void configButtonBackLayoutFilter(int tintCorlor) {
        ui.buttonBackFilter.setColorFilter(getContext().getResources().getColor(tintCorlor));
    }

    @Override
    public void configButtonCancelSearchLayoutFilter(int tintCorlor) {
        ui.buttonClear.setColorFilter(getContext().getResources().getColor(tintCorlor));
    }

    @Override
    public void configEdtSearchLayoutFilter(int textColor, int backgroundColor) {
        ui.editFilter.setTextColor(getContext().getResources().getColor(textColor));
        ui.editFilter.setBackgroundColor(getContext().getResources().getColor(backgroundColor));
    }

    private void toggleFilter() {
        ui.editFilter.setText("");
        if (showFilter) {
            setGone(ui.layout_actionbar);
            setVisible(ui.layoutFilter);
            ui.editFilter.requestFocus();
            AppUtils.showKeyboard(ui.editFilter);
        } else {
            setVisible(ui.layout_actionbar);
            setGone(ui.layoutFilter);
            ui.editFilter.clearFocus();
            AppUtils.hideKeyBoard(ui.editFilter);
        }
        callback.onFilterToggle(showFilter);
    }


    @Override
    public void setActionbarColor(int resId) {
        ui.layout_actionbar.setBackgroundColor(getColor(resId));
    }

    @Override
    public void showActionbar() {
        setVisible(ui.layout_actionbar);
    }

    @Override
    public void hideActionbar() {
        setGone(ui.layout_actionbar);
    }

    @Override
    public void setTitle(String title) {
        ui.title.setText(title);
    }

    @Override
    public void showButtonLeftActionBar() {
        setVisible(ui.btnLeftActionBar);
    }

    @Override
    public void hideButtonLeftActionBar() {
        setGone(ui.btnLeftActionBar);
    }

    @Override
    public void showButtonRightActionBar() {
        setVisible(ui.btnRightActionBar);
    }

    @Override
    public void hideButtonRightActionBar() {
        setGone(ui.btnRightActionBar);
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new BaseMainActionbarView.UIContainer();
    }

    @Override
    public int getViewId() {
        return R.layout.layout_base_main_actionbar_view;
    }

    public static class UIContainer extends BaseUiContainer {

        @UiElement(R.id.layout_actionbar)
        public RelativeLayout layout_actionbar;

        @UiElement(R.id.title)
        public TextView title;

        @UiElement(R.id.subtitle)
        public TextView subtitle;

        @UiElement(R.id.btnLeftActionBar)
        public ImageView btnLeftActionBar;

        @UiElement(R.id.btnRightActionBar)
        public ImageView btnRightActionBar;

        @UiElement(R.id.layout_filter)
        public View layoutFilter;

        @UiElement(R.id.layout_filter_container)
        public View layout_filter_container;

        @UiElement(R.id.button_back_filter)
        public ImageView buttonBackFilter;

        @UiElement(R.id.button_clear)
        public ImageView buttonClear;

        @UiElement(R.id.edit_filter)
        public EditText editFilter;

    }


}
