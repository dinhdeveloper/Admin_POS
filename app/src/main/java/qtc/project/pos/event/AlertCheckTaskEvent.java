package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class AlertCheckTaskEvent {

    public static void post() {
        BusHelper.post(new AlertCheckTaskEvent());
    }
}
