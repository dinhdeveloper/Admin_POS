package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class UpdateCreateProductEvent {
    public static void post()
    {
        BusHelper.post(new UpdateCreateProductEvent());
    }
}

