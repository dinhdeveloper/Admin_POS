package qtc.project.pos;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import b.laixuantam.myaarlibrary.helper.MyLifecycleHandler;
import b.laixuantam.myaarlibrary.helper.MyLog;
import b.laixuantam.myaarlibrary.widgets.ACRA.ACRA;
import b.laixuantam.myaarlibrary.widgets.ACRA.ReportingInteractionMode;
import b.laixuantam.myaarlibrary.widgets.ACRA.annotation.ReportsCrashes;
import qtc.project.pos.dependency.AppObjectProvider;
import qtc.project.pos.dependency.AppProvider;
import qtc.project.pos.dependency.ObjectProviderInterface;
import qtc.project.pos.helper.Consts;

@ReportsCrashes(

        mailTo = "qtctek@gmail.com",
        mode = ReportingInteractionMode.DIALOG,
        resNotifTickerText = R.string.crash_notif_ticker_text,
        resNotifTitle = R.string.crash_notif_title,
        resNotifText = R.string.crash_notif_text,
        resNotifIcon = android.R.drawable.stat_notify_error,
        resDialogText = R.string.crash_dialog_text,
        resDialogIcon = android.R.drawable.ic_dialog_info,
        resDialogTitle = R.string.crash_dialog_title,
        resDialogOkToast = R.string.crash_dialog_ok_toast

)
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Đăng ký ActivityLifecycleCallbacks để kiểm tra application có background running hay visible on screen
        registerActivityLifecycleCallbacks(new MyLifecycleHandler());

        MultiDex.install(this);
        if (Consts.MODE.equalsIgnoreCase("debug"))
            MyLog.isEnableLog = true;
        else
            MyLog.isEnableLog = false;

        ObjectProviderInterface objectProviderInterface = new AppObjectProvider(this);
        AppProvider.init(objectProviderInterface);


        ACRA.init(this);

    }


}
