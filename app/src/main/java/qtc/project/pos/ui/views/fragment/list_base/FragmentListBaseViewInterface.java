package qtc.project.pos.ui.views.fragment.list_base;

import b.laixuantam.myaarlibrary.base.BaseViewInterface;
import qtc.project.pos.activity.HomeActivity;
import qtc.project.pos.model.BaseResponseModel;

public interface FragmentListBaseViewInterface extends BaseViewInterface {

    void init(HomeActivity activity, FragmentListBaseViewCallback callback);

    void showEmptyList();

    void hideEmptyList();

    void setDataList(BaseResponseModel dataList);

    void setNoMoreLoading();

    void resetListData();

    void hideRootView();

    void showRootView();

    void clearListData();
}
