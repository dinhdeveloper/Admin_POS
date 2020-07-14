package qtc.project.pos.ui.views.fragment.list_base;

import qtc.project.pos.dialog.option.OptionModel;

public interface FragmentAdminManagerListBaseViewCallback {
    void onClickBackHeader();

    void refreshLoadingList();

    void onRequestLoadMoreList();

    void onRequestSearchWithFilter(String status, String key);

    void onItemListSelected(OptionModel item);

    void onClickAddItem();

    void onDeleteItemSelected(OptionModel item);
}
