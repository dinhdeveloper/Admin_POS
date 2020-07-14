package qtc.project.pos.event;


import b.laixuantam.myaarlibrary.helper.BusHelper;

/**
 * Created by laixuantam on 7/26/18.
 *
 * @SuppressWarnings("unused")
 @Subscribe(threadMode = ThreadMode.MAIN)
 public void onKeyboardDissmiss(KeyboardOutEvent event)
 {
 if (view != null)
 {
 view.hideKeyboardFakeLayout();
 }
 }
 */

public class KeyboardOutEvent
{
    public static void post()
    {
        BusHelper.post(new KeyboardOutEvent());
    }
}
