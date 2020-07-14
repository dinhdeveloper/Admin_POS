package qtc.project.pos.ui.views.fragment.order.filter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.libraries.places.internal.ne;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import b.laixuantam.myaarlibrary.base.BaseUiContainer;
import b.laixuantam.myaarlibrary.base.BaseView;
import b.laixuantam.myaarlibrary.helper.MyLog;
import b.laixuantam.myaarlibrary.widgets.calendardaterangepicker.customviews.CalendarListener;
import b.laixuantam.myaarlibrary.widgets.calendardaterangepicker.customviews.DateRangeCalendarView;
import b.laixuantam.myaarlibrary.widgets.custompicker.MyCustomPicker;
import b.laixuantam.myaarlibrary.widgets.ultils.ConvertDate;
import qtc.project.pos.R;
import qtc.project.pos.activity.HomeActivity;

public class FragmentFilterOrderView extends BaseView<FragmentFilterOrderView.UIContainer> implements FragmentFilterOrderViewInterface {

    HomeActivity activity;
    FragmentFilterOrderViewCallback callback;
    String old_value_date_end;

    private static String TAG = "MyCustomPicker";
    private Timer timer = new Timer();
    private final long DELAY = 500; // in ms
    private int positionItemMonthSelected = 1;
    private int positionItemYearSelected  = 1;
    private int positionOldYearSelected  = 1;
    String[] arr_year = new String[10];
    String[] arr_month = new String[12];
    private String dateStartSelected, dateEndSelected;

    @Override
    public void init(HomeActivity activity, FragmentFilterOrderViewCallback callback) {
        this.activity = activity;
        this.callback = callback;

        onClick();

        initScheduleFilter();

        configPickerYear();
        configPickerMonth();
        configCalendar();
    }

    private void onClick() {
        ui.imageNavLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onBackProgress();
            }
        });
        //remove
        ui.btnClearCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ui.calendar.resetAllSelectedViews();
                ui.btnClearCalendar.setVisibility(View.GONE);
            }
        });

        //search
        ui.layout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null){
                    callback.filterOnDay(dateStartSelected,dateEndSelected);
                }
            }
        });

        //cancel
        ui.layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                    callback.onBackProgress();
            }
        });
    }

    @Override
    public BaseUiContainer getUiContainer() {
        return new FragmentFilterOrderView.UIContainer();
    }

    private void configCalendar() {
//        final Calendar startMonth = Calendar.getInstance();
//        startMonth.add(Calendar.MONTH, -2);
//        final Calendar endMonth = (Calendar) startMonth.clone();
//        endMonth.add(Calendar.MONTH, 5);
//        MyLog.e(TAG, "Start month: " + startMonth.getTime().toString() + " :: End month: " + endMonth.getTime().toString());
//        ui.calendar.setVisibleMonthRange(startMonth, endMonth);

//        final Calendar startDateSelectable = (Calendar) startMonth.clone();
//        startDateSelectable.add(Calendar.DATE,20);
//        final Calendar endDateSelectable = (Calendar) endMonth.clone();
//        endDateSelectable.add(Calendar.DATE, -20);
//        MyLog.e(TAG, "startDateSelectable: " + startDateSelectable.getTime().toString() + " :: endDateSelectable: " + endDateSelectable.getTime().toString());
//        ui.calendar.setSelectableDateRange(startDateSelectable, endDateSelectable);

//        final Calendar startSelectedDate = (Calendar) startDateSelectable.clone();
//        startSelectedDate.add(Calendar.DATE, 10);
//        final Calendar endSelectedDate = (Calendar) endDateSelectable.clone();
//        endSelectedDate.add(Calendar.DATE, -10);
//        MyLog.e(TAG, "startSelectedDate: " + startSelectedDate.getTime().toString() + " :: endSelectedDate: " + endSelectedDate.getTime().toString());
//        ui.calendar.setSelectedDateRange(startSelectedDate, endSelectedDate);

        Calendar current = Calendar.getInstance();

        ui.calendar.setCurrentMonth(current);

        ui.calendar.setCalendarListener(calendarListener);

        ui.calendar.resetAllSelectedViews();

        ui.calendar.setShowHeader(false);
        ui.calendar.setSwipeable(false);
    }

    private final CalendarListener calendarListener = new CalendarListener() {
        @Override
        public void onFirstDateSelected(@NonNull final Calendar startDate) {
            //            Toast.makeText(getContext(), "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            setVisible(ui.btnClearCalendar);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateStartSelected = sdf.format(startDate.getTime());
            dateEndSelected = dateStartSelected;
            old_value_date_end = "";

        }

        @Override
        public void onDateRangeSelected(@NonNull final Calendar startDate, @NonNull final Calendar endDate) {

            setVisible(ui.btnClearCalendar);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateStartSelected = sdf.format(startDate.getTime());
            dateEndSelected = sdf.format(endDate.getTime());

        }
    };

    private void configPickerMonth() {

        ui.custom_picker_month.setDividerColor(ContextCompat.getColor(getContext(), R.color.transparent));
        ui.custom_picker_month.setSelectedTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        ui.custom_picker_month.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));

        String[] data = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"};
        ui.custom_picker_month.setMinValue(1);
        ui.custom_picker_month.setMaxValue(data.length);
        ui.custom_picker_month.setDisplayedValues(data);

        final Calendar now = Calendar.getInstance();
        int currentMonth = now.get(Calendar.MONTH);
        positionItemMonthSelected = currentMonth + 1;
        ui.custom_picker_month.setValue(positionItemMonthSelected);

        // Set fading edge enabled
        ui.custom_picker_month.setFadingEdgeEnabled(true);

        // Set scroller enabled
        ui.custom_picker_month.setScrollerEnabled(true);

        // Set wrap selector wheel
        ui.custom_picker_month.setWrapSelectorWheel(false);

        // Set accessibility description enabled
        ui.custom_picker_month.setAccessibilityDescriptionEnabled(true);

        // OnClickListener
        ui.custom_picker_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.e(TAG, "Click on current value");
            }
        });

        // OnValueChangeListener
        ui.custom_picker_month.setOnValueChangedListener(new MyCustomPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(MyCustomPicker picker, int oldVal, int newVal) {
                MyLog.e(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                positionItemMonthSelected = newVal;

                if (ui.layoutPickerCalendar.getVisibility() != View.VISIBLE)
                    return;
                if (timer != null)
                    timer.cancel();

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                reloadCalendarView();
                            }
                        });
                    }

                }, DELAY);
            }
        });

        // OnScrollListener
        ui.custom_picker_month.setOnScrollListener(new MyCustomPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(MyCustomPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    MyLog.e(TAG, String.format(Locale.US, "newVal: %d", picker.getValue()));
                }
            }
        });
    }

    private void configPickerYear() {

        ui.number_picker_year.setDividerColor(ContextCompat.getColor(getContext(), R.color.transparent));
        ui.number_picker_year.setSelectedTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        ui.number_picker_year.setTextColor(ContextCompat.getColor(getContext(), R.color.gray));

        ui.number_picker_year.setMinValue(1);
        ui.number_picker_year.setMaxValue(arr_year.length);
        ui.number_picker_year.setDisplayedValues(arr_year);
        ui.number_picker_year.setValue(positionOldYearSelected + 1);

        // Set fading edge enabled
        ui.number_picker_year.setFadingEdgeEnabled(true);

        // Set scroller enabled
        ui.number_picker_year.setScrollerEnabled(true);

        // Set wrap selector wheel
        ui.number_picker_year.setWrapSelectorWheel(false);

        // Set accessibility description enabled
        ui.number_picker_year.setAccessibilityDescriptionEnabled(true);

        // OnClickListener
        ui.number_picker_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyLog.e(TAG, "Click on current value");
            }
        });

        // OnValueChangeListener
        ui.number_picker_year.setOnValueChangedListener(new MyCustomPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(MyCustomPicker picker, int oldVal, int newVal) {
                MyLog.e(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
                positionItemYearSelected = newVal - 1;

                if (ui.layoutPickerCalendar.getVisibility() != View.VISIBLE)
                    return;

                if (timer != null)
                    timer.cancel();

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                reloadCalendarView();
                            }
                        });
                    }

                }, DELAY);
            }
        });

        // OnScrollListener
        ui.number_picker_year.setOnScrollListener(new MyCustomPicker.OnScrollListener() {
            @Override
            public void onScrollStateChange(MyCustomPicker picker, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    MyLog.e(TAG, String.format(Locale.US, "newVal: %d", picker.getValue()));
                }
            }
        });
    }

    private void initScheduleFilter() {

        for (int i = 1; i < 13; i++) {
            arr_month[i - 1] = "Tháng " + i;
        }

        final Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int nextYear = currentYear + 1;

        int pass1Year = currentYear - 1;
        int pass2Year = currentYear - 2;
        int pass3Year = currentYear - 3;
        int pass4Year = currentYear - 4;
        int pass5Year = currentYear - 5;
        int pass6Year = currentYear - 6;
        int pass7Year = currentYear - 7;
        int pass8Year = currentYear - 8;
        int pass9Year = currentYear - 9;

        arr_year[0] = "" + (nextYear);
        arr_year[1] = "" + (currentYear);
        arr_year[2] = "" + (pass1Year);
        arr_year[3] = "" + (pass2Year);
        arr_year[4] = "" + (pass3Year);
        arr_year[5] = "" + (pass4Year);
        arr_year[6] = "" + (pass5Year);
        arr_year[7] = "" + (pass6Year);
        arr_year[8] = "" + (pass7Year);
        arr_year[9] = "" + (pass8Year);
        positionItemYearSelected = 1;
        positionOldYearSelected = 1;
    }

    private void reloadCalendarView() {
        String valueYearSelected = arr_year[positionItemYearSelected];
        String valueMonthSelected = String.valueOf(positionItemMonthSelected);

        StringBuilder valueDate = new StringBuilder();
        String dateSelected = valueDate.append(valueYearSelected).append("-").append(valueMonthSelected).append("-01").toString();
        Calendar calendarFromDate = convertDateToCalendar(ConvertDate.getDateFromString(dateSelected, "yyyy-MM-dd"));
        ui.calendar.setCurrentMonth(calendarFromDate);
    }

    private Calendar convertDateToCalendar(Date dateFromString) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFromString);
        return cal;
    }

    @Override
    public int getViewId() {
        return R.layout.layout_fragment_filter_order;
    }

    public class UIContainer extends BaseUiContainer {
        @UiElement(R.id.layoutPickerCalendar)
        public View layoutPickerCalendar;

        @UiElement(R.id.btnClearCalendar)
        public TextView btnClearCalendar;

        @UiElement(R.id.calendar)
        public DateRangeCalendarView calendar;

        @UiElement(R.id.layoutPickerMonth)
        public View layoutPickerMonth;

        @UiElement(R.id.custom_picker_month)
        public MyCustomPicker custom_picker_month;

        @UiElement(R.id.layoutPickerYear)
        public View layoutPickerYear;

        @UiElement(R.id.custom_picker_year)
        public MyCustomPicker number_picker_year;

        @UiElement(R.id.layout_cancel)
        public LinearLayout layout_cancel;

        @UiElement(R.id.layout_update)
        public LinearLayout layout_search;

        @UiElement(R.id.imageNavLeft)
        public ImageView imageNavLeft;


    }
}
