package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class StatusEmployeeEvent {
    public static void post()
    {
        BusHelper.post(new StatusEmployeeEvent());
    }
}
