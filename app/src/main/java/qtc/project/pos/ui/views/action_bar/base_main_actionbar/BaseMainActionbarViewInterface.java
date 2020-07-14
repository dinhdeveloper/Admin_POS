package qtc.project.pos.ui.views.action_bar.base_main_actionbar;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import b.laixuantam.myaarlibrary.base.BaseActionbarView;


/**
 * Created by laixuantam on 23/7/18.
 */

public interface BaseMainActionbarViewInterface extends BaseActionbarView {
    void initialize(String title, BaseMainActionbarViewCallback callback);

    void setActionbarColor(@ColorRes int resId);

    void setTitle(String title);

    void showActionbar();

    void hideActionbar();

    void configButtonLeftActionBar(@DrawableRes int iconLeft, @ColorRes int tintCorlor);

    void showButtonLeftActionBar();

    void hideButtonLeftActionBar();

    void configButtonRightActionBar(@DrawableRes int iconRight, @ColorRes int tintCorlor);

    void showButtonRightActionBar();

    void hideButtonRightActionBar();

    void showLayoutFilter();

    void hideLayoutFilter();

    void showButtonBackFilter();

    void hideButtonBackFilter();

    void configBackgroundLayoutFilter(@ColorRes int backgroundCorlor);

    void configBackgroundLayoutFilterContainer(@DrawableRes int drawableBackground);

    void configButtonBackLayoutFilter(@ColorRes int tintCorlor);

    void configButtonCancelSearchLayoutFilter(@ColorRes int tintCorlor);

    void configEdtSearchLayoutFilter(@ColorRes int textColor, @ColorRes int backgroundColor);


}
