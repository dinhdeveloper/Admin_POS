//package qtc.project.pos.helper.fcm;
//
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.FirebaseInstanceIdService;
//
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.model.UserResponseModel;
//
//public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
//    private static final String TAG = "MyFirebaseIIDService";
//
//    @Override
//    public void onTokenRefresh() {
//
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        AppProvider.getPreferences().saveToken(refreshedToken);
//        Log.e(TAG, "Refreshed token FCM: " + refreshedToken);
//
//        // TODO: Implement this method to send any registration to your app's servers.
//        sendRegistrationToServer(refreshedToken);
//    }
//
//    private void sendRegistrationToServer(String token) {
//        // Add custom implementation, as needed.
//
//        if (!TextUtils.isEmpty(token)) {
//            if (AppProvider.getConnectivityHelper().hasInternetConnection()) {
//                UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
//                if (userResponseModel != null)
//                    requestUpdateDeviceToken(token, userResponseModel.getId());
//            }
//        }
//    }
//
//
//    private void requestUpdateDeviceToken(String token, String userId) {
//
////        RequestUpdateDiviceImei.ApiParams params = new RequestUpdateDiviceImei.ApiParams();
////
////        params.fcm_token = token;
////        params.id_user = userId;
////
////        AppProvider.getApiManagement().call(RequestUpdateDiviceImei.class, params, null);
//
//
//    }
//}
