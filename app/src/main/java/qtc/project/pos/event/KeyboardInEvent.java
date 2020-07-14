package qtc.project.pos.event;


import b.laixuantam.myaarlibrary.helper.BusHelper;

/**
 * Created by laixuantam on 7/26/18.
 *
    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
     public void onKeyboardShowing(KeyboardInEvent event)
     {
         if (view != null)
         {
             view.showKeyboardFakeLayout();
         }
     }
 */

public class KeyboardInEvent
{
    public static void post()
    {
        BusHelper.post(new KeyboardInEvent());
    }

}
