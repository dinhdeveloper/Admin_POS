//package qtc.project.pos.fragment.list_base;
//
//import b.laixuantam.myaarlibrary.base.BaseFragment;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import b.laixuantam.myaarlibrary.helper.MyLog;
//import qtc.project.pos.R;
//import qtc.project.pos.dialog.option.OptionModel;
//import qtc.project.pos.helper.Consts;
//import qtc.project.pos.ui.views.fragment.list_base.FragmentAdminManagerListBaseView;
//import qtc.project.pos.ui.views.fragment.list_base.FragmentAdminManagerListBaseViewCallback;
//import qtc.project.pos.ui.views.fragment.list_base.FragmentAdminManagerListBaseViewInterface;
//
//public class FragmentListBase extends BaseFragment<FragmentAdminManagerListBaseViewInterface, BaseParameters> implements FragmentAdminManagerListBaseViewCallback {
//
//    private int page = 1;
//    private int totalPage = 0;
//
//    @Override
//    protected void initialize() {
//
//        handler.postDelayed(loopCheckViewInit, 300);
//
//    }
//
//    private final Runnable loopCheckViewInit = new Runnable() {
//        public void run() {
//            if (getView() != null && getView().isShown()) {
//                MyLog.e(Consts.TAG_FRAGMENT_CHECK, " is on screen");
//
//                handler.removeCallbacks(this);
//
//                //todo request list data
//
//                return;
//            }
//
//            MyLog.e(Consts.TAG_FRAGMENT_CHECK, " is NOT on screen");
//            handler.postDelayed(this, 500);
//        }
//    };
//
//    @Override
//    protected FragmentAdminManagerListBaseViewInterface getViewInstance() {
//        return new FragmentAdminManagerListBaseView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//    @Override
//    public void onClickBackHeader() {
//
//    }
//
//    @Override
//    public void refreshLoadingList() {
//        view.resetListData();
//        page = 1;
//        totalPage = 0;
//        //todo request list data
//    }
//
//    @Override
//    public void onRequestLoadMoreList() {
//        ++page;
//
//        if (totalPage > 0 && page <= totalPage) {
//
//            //todo request list data
//        } else {
//            view.setNoMoreLoading();
//            showToast(getString(R.string.error_loadmore));
//        }
//    }
//
//    private String status, key_filter;
//
//    @Override
//    public void onRequestSearchWithFilter(String status, String key) {
//        this.key_filter = key;
//
//        page = 1;
//        totalPage = 0;
//
//        //todo request list data
//    }
//
//    @Override
//    public void onItemListSelected(OptionModel item) {
//
//    }
//
//    @Override
//    public void onClickAddItem() {
//
//    }
//
//    @Override
//    public void onDeleteItemSelected(OptionModel item) {
//
//    }
//}
