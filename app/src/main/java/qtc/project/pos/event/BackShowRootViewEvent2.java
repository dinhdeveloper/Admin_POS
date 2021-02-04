package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class BackShowRootViewEvent2  {
    public static void post()
    {
        BusHelper.post(new BackShowRootViewEvent2());
    }
}
