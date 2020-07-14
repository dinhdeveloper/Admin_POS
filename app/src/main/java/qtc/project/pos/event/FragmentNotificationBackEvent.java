package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class FragmentNotificationBackEvent {

    public static void post() {
        BusHelper.post(new FragmentNotificationBackEvent());
    }
}
