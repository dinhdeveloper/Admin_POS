//package qtc.project.pos.activity;
//
//import android.widget.Toast;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import b.laixuantam.myaarlibrary.base.BaseActivity;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import b.laixuantam.myaarlibrary.widgets.ultils.ConvertDate;
//import qtc.project.pos.R;
//import qtc.project.pos.dialog.slidedatetimepicker.SlideDateTimeListener;
//import qtc.project.pos.dialog.slidedatetimepicker.SlideDateTimePicker;
//import qtc.project.pos.ui.views.action_bar.base_main_actionbar.BaseMainActionbarView;
//import qtc.project.pos.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewCallback;
//import qtc.project.pos.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewInterface;
//import qtc.project.pos.ui.views.activity.base_main_activity.BaseMainActivityView;
//import qtc.project.pos.ui.views.activity.base_main_activity.BaseMainActivityViewCallback;
//import qtc.project.pos.ui.views.activity.base_main_activity.BaseMainActivityViewInterface;
//
//public class DemoSingleActivity extends BaseActivity<BaseMainActivityViewInterface, BaseMainActionbarViewInterface, BaseParameters> implements BaseMainActivityViewCallback, BaseMainActionbarViewCallback {
//
//    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//    private SimpleDateFormat mFormatterNice = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//
//    @Override
//    protected void initialize() {
//        view.init(DemoSingleActivity.this);
//        actionbar.initialize("Main", this);
//        actionbar.hideLayoutFilter();
//
//        actionbar.showButtonRightActionBar();
//
//        actionbar.configButtonLeftActionBar(R.drawable.ic_keyboard_arrow_left_black_24dp, R.color.blue_light);
//
//        actionbar.configButtonRightActionBar(R.drawable.ic_filter2, R.color.blue_light);
//
//        actionbar.configBackgroundLayoutFilter(R.color.white);
//
//        actionbar.configBackgroundLayoutFilterContainer(R.drawable.border_shape_primary_layout_search_contact);
//
//        actionbar.configButtonBackLayoutFilter(R.color.black);
//
//        actionbar.configEdtSearchLayoutFilter(R.color.white, R.color.color_primary);
//
//    }
//
//    private SlideDateTimeListener listener = new SlideDateTimeListener() {
//
//        @Override
//        public void onDateTimeSet(Date date) {
//
//            Toast.makeText(DemoSingleActivity.this,
//                    mFormatterNice.format(date), Toast.LENGTH_SHORT).show();
//        }
//
//        // Optional cancel listener
//        @Override
//        public void onDateTimeCancel() {
//        }
//    };
//
//    private void showDateTimePickerDialog() {
//        new SlideDateTimePicker.Builder(getSupportFragmentManager())
//                .setContext(getApplicationContext())
//                .setListener(listener)
//                .setInitialDate(ConvertDate.getTimeMoment())
//                //.setMinDate(minDate)
//                //.setMaxDate(maxDate)
//                .setIs24HourTime(true)
//                //.setTheme(SlideDateTimePicker.HOLO_DARK)
//                //.setIndicatorColor(Color.parseColor("#990000"))
//                .setTypeShowDialog(0)
//                .build()
//                .show();
//    }
//
//    @Override
//    public void onClickButtonAlert() {
//
//    }
//
////    @Override
////    protected void setupActionbar(ViewGroup container) {
////        Toolbar toolbar = (Toolbar) view.findViewById(R.id.layoutToolBar);
////        this.actionbar = getActionbarInstance();
////
////        if ((toolbar != null) && (this.actionbar != null)) {
////            setSupportActionBar(toolbar);
////
////            View actionbarView = this.actionbar.inflate(getLayoutInflater(), container);
////            toolbar.addView(actionbarView);
////        }
////    }
//
//    @Override
//    protected BaseMainActivityViewInterface getViewInstance() {
//        return new BaseMainActivityView();
//    }
//
//    @Override
//    protected BaseMainActionbarViewInterface getActionbarInstance() {
//        return new BaseMainActionbarView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//    @Override
//    public void onFilterToggle(boolean showFilter) {
//
//    }
//
//    @Override
//    public void onFiltering(String keyword) {
//
//    }
//
//    @Override
//    public void onClickButtonLeftActionbar() {
//
//    }
//
//    @Override
//    public void onClickButtonRightActionbar() {
////        actionbar.showLayoutFilter();
//
//        showDateTimePickerDialog();
//
//    }
//}
