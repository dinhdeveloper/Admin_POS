package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class RequestLoginWithGoogleEvent
{
    public static void post()
    {
        BusHelper.post(new RequestLoginWithGoogleEvent());
    }
}
