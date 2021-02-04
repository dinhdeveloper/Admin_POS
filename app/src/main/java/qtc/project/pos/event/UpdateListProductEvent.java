package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class UpdateListProductEvent {
    public static void post()
    {
        BusHelper.post(new UpdateListProductEvent());
    }
}

