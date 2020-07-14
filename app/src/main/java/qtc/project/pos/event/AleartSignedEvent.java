package qtc.project.pos.event;


import b.laixuantam.myaarlibrary.helper.BusHelper;

public class AleartSignedEvent {

    public static void post() {
        BusHelper.post(new AleartSignedEvent());
    }
}
