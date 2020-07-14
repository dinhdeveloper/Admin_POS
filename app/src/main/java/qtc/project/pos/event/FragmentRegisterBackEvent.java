package qtc.project.pos.event;

import b.laixuantam.myaarlibrary.helper.BusHelper;

public class FragmentRegisterBackEvent {

    public static void post() {
        BusHelper.post(new FragmentRegisterBackEvent());
    }
}
