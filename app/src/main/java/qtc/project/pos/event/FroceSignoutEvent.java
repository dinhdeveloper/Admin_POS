package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class FroceSignoutEvent {
    public static void post()
    {
        BusHelper.post(new FroceSignoutEvent());
    }
}
