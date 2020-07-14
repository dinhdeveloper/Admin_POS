package qtc.project.pos.ui.views.action_bar.base_main_actionbar;

/**
 * Created by laixuantam on 23/7/18.
 */

public interface BaseMainActionbarViewCallback {

    void onFilterToggle(boolean showFilter);

    void onFiltering(String keyword);

    void onClickButtonLeftActionbar();

    void onClickButtonRightActionbar();

}
