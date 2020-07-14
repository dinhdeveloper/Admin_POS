//package qtc.project.pos.activity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Rect;
//import android.location.Address;
//import android.location.Location;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.Looper;
//import android.os.StrictMode;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.util.TypedValue;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewTreeObserver;
//import android.widget.Toast;
//
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.tasks.Task;
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.io.File;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//
//import b.laixuantam.myaarlibrary.base.BaseFragment;
//import b.laixuantam.myaarlibrary.base.BaseFragmentActivity;
//import b.laixuantam.myaarlibrary.base.BaseParameters;
//import b.laixuantam.myaarlibrary.event.GetCurrentPositionSuccess;
//import b.laixuantam.myaarlibrary.helper.MyLog;
//import b.laixuantam.myaarlibrary.helper.OnKeyboardVisibilityListener;
//import b.laixuantam.myaarlibrary.helper.map.location.LocationHelper;
//import b.laixuantam.myaarlibrary.helper.map.location.PermissionUtils;
//import b.laixuantam.myaarlibrary.widgets.dialog.alert.KAlertDialog;
//import b.laixuantam.myaarlibrary.widgets.multiple_media_picker.Gallery;
//import qtc.project.pos.R;
//import qtc.project.pos.dependency.AppProvider;
//import qtc.project.pos.dialog.FeedbackRatingDialog;
//import qtc.project.pos.event.AleartSignedEvent;
//import qtc.project.pos.event.AlertCheckRolePermissionEvent;
//import qtc.project.pos.event.AlertCheckTaskEvent;
//import qtc.project.pos.event.AlertCheckUserRolePermissionEvent;
//import qtc.project.pos.event.KeyboardInEvent;
//import qtc.project.pos.event.KeyboardOutEvent;
//import qtc.project.pos.event.ReloadMenuBottomEvent;
//import qtc.project.pos.event.RequestLoginWithGoogleEvent;
//import qtc.project.pos.fragment.account.forgot_password.FragmentForgotPassword;
//import qtc.project.pos.fragment.account.password_manager.FragmentPasswordManager;
//import qtc.project.pos.fragment.account.profile_manager.FragmentProfileManager;
//import qtc.project.pos.fragment.account.register.FragmentRegister;
//import qtc.project.pos.helper.Consts;
//import qtc.project.pos.model.UserResponseModel;
//import qtc.project.pos.ui.views.action_bar.base_main_actionbar.BaseMainActionbarView;
//import qtc.project.pos.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewCallback;
//import qtc.project.pos.ui.views.action_bar.base_main_actionbar.BaseMainActionbarViewInterface;
//import qtc.project.pos.ui.views.activity.base_main_activity.BaseMainActivityView;
//import qtc.project.pos.ui.views.activity.base_main_activity.BaseMainActivityViewInterface;
//import qtc.project.pos.ui.views.activity.home_activity.HomeActivityViewCallback;
//
//public class HomeActivityBackup extends BaseFragmentActivity<BaseMainActivityViewInterface, BaseMainActionbarViewInterface, BaseParameters> implements BaseMainActionbarViewCallback, HomeActivityViewCallback, ActivityCompat.OnRequestPermissionsResultCallback, OnKeyboardVisibilityListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
//
//    private static final String TAG = HomeActivityBackup.class.getName();
//    private LocationHelper locationHelper;
//    private GoogleApiClient mGoogleApiClient;
//    GoogleSignInClient mGoogleSignInClient;
//
//    public static final int REQUEST_PHONE_CALL = 101;
//
//    @Override
//    protected void initialize(Bundle bundle) {
//
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this/* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        FullScreencall();
//
//        view.init(this, this);
//        view.configLayoutBaseMainFragmentActivity();
//
//        view.setDrawerEnabled(false);
//
//        view.hideToolBar();
//
//        view.showBottomMenuBar();
//
//        actionbar.initialize("", this);
//
//        actionbar.hideActionbar();
//
//        setKeyboardVisibilityListener(this);
//
////        mFusedProviderClient = LocationServices.getFusedLocationProviderClient(this);
////        mLocationRequest = LocationRequest.create();
////        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
////        locationHelper = new LocationHelper(this);
//
////        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
////        if (userResponseModel != null) {
////            if (userResponseModel.getAccount_level().equalsIgnoreCase("normal")) {
////                changeToFragmentDashboard();
////            } else {
////                if (checkLoginStatus()) {
//////                    checkRolePermission();
//////                    changeToFragmentMainMenu();
////                } else {
//////                    changeToFragmentLogin();
////                }
////            }
////        } else {
////            changeToFragmentDashboard();
////        }
//
//        changeToFragmentLogin();
//
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if (getIntent().getExtras() != null) {
//            String action = getIntent().getExtras().getString("action", "");
//            if (!TextUtils.isEmpty(action) && action.equalsIgnoreCase("check_order")) {
////                changeToFragmentAccountMenu();
//                view.setBottomMenuBarPossitionSelected(4);
//                getIntent().removeExtra("action");
//            }
//        }
//    }
//
//    private void setKeyboardVisibilityListener(final OnKeyboardVisibilityListener onKeyboardVisibilityListener) {
//        final View parentView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
//        parentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            boolean alreadyOpen;
//            final int defaultKeyboardHeightDP = 100;
//            final int EstimatedKeyboardDP = defaultKeyboardHeightDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
//            final Rect rect = new Rect();
//
//            @Override
//            public void onGlobalLayout() {
//                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, parentView.getResources().getDisplayMetrics());
//                parentView.getWindowVisibleDisplayFrame(rect);
//                int heightDiff = parentView.getRootView().getHeight() - (rect.bottom - rect.top);
//                boolean isShown = heightDiff >= estimatedKeyboardHeight;
//
//                if (isShown == alreadyOpen) {
//                    return;
//                }
//                alreadyOpen = isShown;
//                onKeyboardVisibilityListener.onVisibilityChanged(isShown);
//            }
//        });
//    }
//
//    @Override
//    public void onVisibilityChanged(boolean visible) {
//        if (visible) {
//            KeyboardInEvent.post();
//        } else {
//            KeyboardOutEvent.post();
//        }
//    }
//
//
//    public void FullScreencall() {
//        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//            View v = this.getWindow().getDecorView();
//            v.setSystemUiVisibility(View.GONE);
//        } else if (Build.VERSION.SDK_INT >= 19) {
//            //for new api versions.
//            View decorView = getWindow().getDecorView();
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            decorView.setSystemUiVisibility(uiOptions);
//        }
//    }
//
//    //Override custom toolbar when set @style/AppTheme.NoActionBar in manifest
//    @Override
//    protected void setupActionbar(ViewGroup container) {
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.layoutToolBar);
//        this.actionbar = getActionbarInstance();
//
//        if ((toolbar != null) && (this.actionbar != null)) {
//            setSupportActionBar(toolbar);
//
//            View actionbarView = this.actionbar.inflate(getLayoutInflater(), container);
//            toolbar.addView(actionbarView);
//        }
//    }
//
//    @Override
//    protected BaseMainActivityViewInterface getViewInstance() {
//        return new BaseMainActivityView();
//    }
//
//
//    @Override
//    protected BaseMainActionbarViewInterface getActionbarInstance() {
//        return new BaseMainActionbarView();
//    }
//
//    @Override
//    protected BaseParameters getParametersContainer() {
//        return null;
//    }
//
//    @Override
//    protected int getFragmentContainerId() {
//        return R.id.LayoutBaseMainFragmentActivity;
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//
//    @Override
//    public void onBackPressed() {
////        if (isShowContainer == 0) {
////            super.onBackPressed();
////        } else {
////
////
////        }
//    }
//
//    private int isShowContainer = 0;
//
//    public void checkBack() {
//        FullScreencall();
//
//        if (isShowContainer > 0) {
//            isShowContainer--;
//            FragmentManager fm = getSupportFragmentManager();
//            if (fm.getBackStackEntryCount() > 0) {
//                fm.popBackStack();
//            }
//
//            if (isShowContainer == 0) {
////                if (listProductOrder != null) {
////                    int countItemOrder = countOrderProduct();
////                    if (countItemOrder > 0) {
////                        view.setBadgeCart(countItemOrder);
////                    }
////
////
////                }
//                view.showBottomMenuBar();
//            }
//
//        } else {
//            view.showBottomMenuBar();
//            checkFragment();
//        }
//        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
//        if (userResponseModel != null && !userResponseModel.getAccount_level().equalsIgnoreCase("normal")) {
//            hideBottomMenuBar();
//        }
//    }
//
//    private void checkFragment() {
//
//        FragmentManager fm = getSupportFragmentManager();
//
//        if (fm.getBackStackEntryCount() > 0) {
//            fm.popBackStack();
//
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    public void checkLocationPermission() {
//        showProgress();
//
//        if (locationHelper == null)
//            locationHelper = new LocationHelper(this);
//
//        locationHelper.checkpermission();
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onGetCurrentLocationSuccess(GetCurrentPositionSuccess event) {
//        if (view != null) {
//            dismissProgress();
//            if (locationHelper != null && locationHelper.getmLastLocation() != null) {
//                try {
//                    locationHelper.getAddress(locationHelper.getmLastLocation().getLatitude(), locationHelper.getmLastLocation().getLongitude());
//                    Address userAddress = locationHelper.getUserAddress();
//
//                    BaseFragment fragment = getCurrentFragment();
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                    showAlert("Không thể xác định được vị trí của bạn, xin hãy thử lại sau.", KAlertDialog.ERROR_TYPE);
//                }
//            } else {
//
//                showAlert("Không thể xác định được vị trí của bạn, xin hãy thử lại sau.", KAlertDialog.ERROR_TYPE);
//            }
//
//
//        }
//    }
//
//    ///////////////////////////////////////////////////////////////////////////
//    // interface implement
//    ///////////////////////////////////////////////////////////////////////////
//
//
//    //============ view callback =====================
//
//
//    @Override
//    public void onClickBottomBarMenuHome() {
//        changeToFragmentDashboard();
//    }
//
//    @Override
//    public void onClickBottomBarMenuCategory() {
//    }
//
//    @Override
//    public void onClickBottomBarMenuShoppingCart() {
//    }
//
//
//    @Override
//    public void onClickBottomBarMenuTransaction() {
//    }
//
//
//    @Override
//    public void onClickBottomBarMenuOrder() {
//    }
//
//    @Override
//    public void onClickItemNav(Fragment fragment) {
//
//    }
//
//    @Override
//    public void onClickBottomBarMenuAccount() {
//    }
//
//    //============== actionbar view callback===========
//
//    @Override
//    public void onFilterToggle(boolean showFilter) {
//
//    }
//
//    @Override
//    public void onFiltering(String keyword) {
//
//    }
//
//    @Override
//    public void onClickButtonLeftActionbar() {
//        view.toggleMenuNavigation();
//    }
//
//    @Override
//    public void onClickButtonRightActionbar() {
//
//    }
//
//    public LocationHelper getLocationHelper() {
//        return locationHelper;
//    }
//
//    @Override
//    protected void onDestroy() {
//
//        super.onDestroy();
//    }
//
//    ///////////////////////////////////////////////////////////////////////////
//    // handle image select
//    ///////////////////////////////////////////////////////////////////////////
//
//    private final int OPEN_MEDIA_PICKER = 10101;
//    private final int OPEN_MEDIA_PICKER_PERMISSION = 10102;
//    private static final int CAMERA_REQUEST = 10103;
//    private final int MY_CAMERA_PERMISSION_CODE = 10104;
//    private ArrayList<String> permissions = new ArrayList<>();
//    private PermissionUtils permissionUtils;
//
//    public void changeToActivitySelectImage() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getApplicationContext()), Manifest.permission.READ_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, OPEN_MEDIA_PICKER_PERMISSION);
//            } else {
//
//                intentGallerySelectImage();
//            }
//
//        } else {
//            intentGallerySelectImage();
//        }
//    }
//
//    private File photoFile = null;
//
//    public void captureImageFromCamera() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getApplicationContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_CAMERA_PERMISSION_CODE);
//            } else {
//
//                intentCameraCaptureImage();
//            }
//
//        } else {
//            intentCameraCaptureImage();
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (locationHelper != null) {
//            // redirects to utils
//            locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }
//
//        if (requestCode == Consts.REQUEST_CODE_GPS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            reloadDataCurrentLocation();
//        }
//
//        if (requestCode == OPEN_MEDIA_PICKER_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            intentGallerySelectImage();
//        } else if (requestCode == MY_CAMERA_PERMISSION_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
////            Create the File where the photo should go
//                try {
//                    photoFile = createMediaFile();
//
//                } catch (IOException ex) {
//                    // Error occurred while creating the File
//                    ex.printStackTrace();
//
//                }
//                handler.postDelayed(() -> {
//                    if (photoFile != null) {
//
//                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                        StrictMode.setVmPolicy(builder.build());
//
//                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                    } else {
//                        showAlert(getString(R.string.error_load_file_image), KAlertDialog.ERROR_TYPE);
//                    }
//                }, 300);
//                // Continue only if the File was successfully created
//
//            }
//        }
//    }
//
//    private PermissionUtils.PermissionResultCallback permissionResultCallback = new PermissionUtils.PermissionResultCallback() {
//
//        @Override
//        public void PermissionGranted(int request_code) {
//
//            Intent intent = new Intent(HomeActivityBackup.this, Gallery.class);
//            // Set the title
//            intent.putExtra("title", "Chọn hình ảnh");
//            // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
//            intent.putExtra("mode", 2);
//            intent.putExtra("maxSelection", 1); // Optional
//            startActivityForResult(intent, OPEN_MEDIA_PICKER);
//
//        }
//
//        @Override
//        public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
//
//        }
//
//        @Override
//        public void PermissionDenied(int request_code) {
//
//        }
//
//        @Override
//        public void NeverAskAgain(int request_code) {
//
//        }
//    };
//
//    private void intentGallerySelectImage() {
//        Intent intent = new Intent(HomeActivityBackup.this, Gallery.class);
//        // Set the title
//        intent.putExtra("title", "Chọn hình ảnh");
//        // Mode 1 for both images and videos selection, 2 for images only and 3 for videos!
//        intent.putExtra("mode", 2);
//        intent.putExtra("maxSelection", 1); // Optional
//        startActivityForResult(intent, OPEN_MEDIA_PICKER);
//    }
//
//    private void intentCameraCaptureImage() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
////            Create the File where the photo should go
//            try {
//                photoFile = createMediaFile();
//
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                ex.printStackTrace();
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//
//                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                StrictMode.setVmPolicy(builder.build());
//
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            } else {
//                showAlert(getString(R.string.error_load_file_image), KAlertDialog.ERROR_TYPE);
//            }
//        } else {
//            showAlert("Camera không khả dụng.", KAlertDialog.ERROR_TYPE);
//        }
//    }
//
//    private File createMediaFile() throws IOException {
//        // Create an image file name
//        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//        File mediaFile;
//
//        String rootPath = Environment.getExternalStorageDirectory()
//                .getAbsolutePath() + "/529FTN_Images/";
//        File root = new File(rootPath);
//        if (!root.exists()) {
//            root.mkdirs();
//        }
////        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        String fileName = "JPEG_" + timeStamp + "_";
//        mediaFile = File.createTempFile(fileName,  // prefix
//                ".jpg",         // suffix
//                root      // directory
//        );
//
//        return mediaFile;
//    }
//
//    public void deleteTempMedia() {
//        String rootPath = Environment.getExternalStorageDirectory()
//                .getAbsolutePath() + "/529FTN_Images/";
//        File root = new File(rootPath);
//        if (root.exists()) {
//            String[] children = root.list();
//            if (children != null && children.length > 0) {
//                for (String aChildren : children) {
//                    new File(root, aChildren).delete();
//                }
//            }
//        }
//    }
//
//    private static final int RC_SIGN_IN = 10001;
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        BaseFragment fragment = getCurrentFragment();
//
//        if (locationHelper != null) {
//            locationHelper.onActivityResult(requestCode, resultCode, data);
//        }
//
//        if (requestCode == REQUEST_PHONE_CALL) {
//            if (resultCode == RESULT_OK) {
////                if (fragment instanceof FragmentContactInfo) {
////                    ((FragmentContactInfo) fragment).callPhone();
////                }
//                return;
//            }
//        }
//
////        if (requestCode == FragmentEmployeeTaskDetail.REQUEST_CHECK_SETTINGS) {
////            if (resultCode == RESULT_OK) {
//////                if (fragment instanceof FragmentEmployeeTaskDetail) {
//////                    ((FragmentEmployeeTaskDetail) fragment).startStep1();
//////                }
////                return;
////            } else {
////                return;
////            }
////        }
//
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//
////            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
////            handleSignInResult(task);
//            return;
//        }
//        if (requestCode == Consts.REQUEST_CODE_AUTOCOMPLETE) {
//
//            if (resultCode == RESULT_OK) {
////                com.google.android.libraries.places.api.model.Place place = Autocomplete.getPlaceFromIntent(data);
////
////                String placeAddress = Objects.requireNonNull(place.getAddress());
////                double mLat = Objects.requireNonNull(place.getLatLng()).latitude;
////                double mLong = place.getLatLng().longitude;
//
////                MyLog.e("PlaceAutocomplete", "Place: " + place.getName() + ", " + place.getId());
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // The user canceled the operation.
//            }
//
//            return;
//        }
//
//        if (requestCode == OPEN_MEDIA_PICKER) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK && data != null) {
//                ArrayList<String> selectionResult = data.getStringArrayListExtra("result");
//                if (selectionResult != null && selectionResult.size() > 0) {
////                    if (fragment instanceof FragmentProfileManager) {
////                        ((FragmentProfileManager) fragment).setImageSelected(selectionResult.get(0));
////                    }
//                }
//            }
//        } else if (requestCode == CAMERA_REQUEST) {
//            if (resultCode == RESULT_OK) {
//
////                if (fragment instanceof FragmentProfileManager) {
////                    if (photoFile != null)
////                        ((FragmentProfileManager) fragment).setImageSelected(photoFile.getAbsolutePath());
////                }
//
//            }
//        }
//    }
//
//    public void signInGoogle() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//
////        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
////        startActivityForResult(signInIntent, RC_SIGN_IN);
//
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        locationHelper.requestGetLocation();
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        locationHelper.connectApiClient();
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//        try {
//            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//
//            String userId = account.getId();
//
//            String userEmail = account.getEmail();
//
//            String lastname = account.getFamilyName();
//
//            String firstname = account.getGivenName();
//
//            String userName = account.getDisplayName();
//
//            String userImage = "";
//
//            if (account.getPhotoUrl() != null && !account.getPhotoUrl().toString().isEmpty()) {
//                userImage = account.getPhotoUrl().toString() + "?sz=150";
//            }
//
//            AppProvider.getPreferences().saveUserGGId(userId);
//            if (!userImage.isEmpty()) {
//                AppProvider.getPreferences().saveUserImage(userImage);
//            }
//
//            AppProvider.getPreferences().saveUserEmail(userEmail);
//            AppProvider.getPreferences().saveUsername(userName);
//            AppProvider.getPreferences().saveUserFirstName(firstname);
//            AppProvider.getPreferences().saveUserLastName(lastname);
//
//            RequestLoginWithGoogleEvent.post();
//        } catch (ApiException e) {
//            // The ApiException status code indicates the detailed failure reason.
//            // Please refer to the GoogleSignInStatusCodes class reference for more information.
//            MyLog.e("", "signInResult:failed code=" + e.getStatusCode());
//        }
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        MyLog.e("GGSignIn", "handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount account = result.getSignInAccount();
//            //            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//
//            String userId = account.getId();
//
//            String userEmail = account.getEmail();
//
//            String lastname = account.getFamilyName();
//
//            String firstname = account.getGivenName();
//
//            String userName = account.getDisplayName();
//
//            String userImage = "";
//
//            if (account.getPhotoUrl() != null && !account.getPhotoUrl().toString().isEmpty()) {
//                userImage = account.getPhotoUrl().toString() + "?sz=150";
//            }
//
//            AppProvider.getPreferences().saveUserGGId(userId);
//            if (!userImage.isEmpty()) {
//                AppProvider.getPreferences().saveUserImage(userImage);
//            }
//
//            AppProvider.getPreferences().saveUserEmail(userEmail);
//            AppProvider.getPreferences().saveUsername(userName);
//            AppProvider.getPreferences().saveUserFirstName(firstname);
//            AppProvider.getPreferences().saveUserLastName(lastname);
//
//            RequestLoginWithGoogleEvent.post();
//
//        } else {
//            MyLog.e("GGSignIn", "signInResult:failed code=" + result.getSignInAccount());
//        }
//
//    }
//
//    private void showDialog() {
//        final FeedbackRatingDialog ratingDialog = new FeedbackRatingDialog.Builder(HomeActivityBackup.this)
////                .setIcon(R.drawable.ic_rating_dialog_default)
//                .title("Bạn có hài lòng với đơn hàng bạn mua?")
//                .feedbackTextTitle("title feedback")
//                .titleTextColor(R.color.color_text_default)
//                .setCancelable(false)
////                .setVisibleNeverButton(true)
//                .positiveButtonBackgroundColor(R.color.color_text_default)
//                .positiveButtonTextColor(R.color.white)
//                .negativeButtonText("Để sau")
//                .setTypeDialog(FeedbackRatingDialog.Builder.Type.BOTH)
//                .setPositiveButtonListener((rating, feedback) -> Toast.makeText(HomeActivityBackup.this, "Feedback: " + feedback + " | rating: " + rating, Toast.LENGTH_SHORT).show())
//                .setNegativeButtonListener(() -> Toast.makeText(HomeActivityBackup.this, "ClickNegativeButton", Toast.LENGTH_SHORT).show())
//                .setNeverButtonListener(() -> Toast.makeText(HomeActivityBackup.this, "ClickNeverButton", Toast.LENGTH_SHORT).show())
//                .build();
//        ratingDialog.show();
//
//    }
//
//
//    ///////////////////////////////////////////////////////////////////////////
//    // Main function
//    ///////////////////////////////////////////////////////////////////////////
//
//    public boolean checkLoginStatus() {
//
//        boolean userHasLogin = AppProvider.getPreferences().checkLoginStatus();
//
//        if (!userHasLogin) {
//
//            showConfirmAlert(getString(R.string.title_login), getString(R.string.txt_message_sign), kAlertDialog -> {
//                changeToFragmentLogin();
//
//                kAlertDialog.dismiss();
//            }, kAlertDialog -> {
//                kAlertDialog.dismiss();
//                setPositionBottomMenu(0);
//            }, -1);
//        }
//
//        return userHasLogin;
////        return true;
//    }
//
//    public void doLogout() {
//
//        showConfirmAlert("Đăng xuất", "Bạn có muốn đăng xuất tài khoản?", kAlertDialog -> {
//            kAlertDialog.dismiss();
//            AppProvider.getPreferences().saveStatusLogin(false);
//            changeToFragmentDashboard();
//            showBottomMenuBar();
//            view.setBottomMenuBarPossitionSelected(0);
//
//            UserResponseModel userModel = AppProvider.getPreferences().getUserModel();
//            String topic = "user" + userModel.getId();
////            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
////
////            String notifycation_529 = "nino_notifycation";
////
////            if (userModel != null) {
////                String notifycation_529_user = "nino_notifycation_user_" + userModel.getId();
////                FirebaseMessaging.getInstance().unsubscribeFromTopic(notifycation_529_user);
////            }
////
////            FirebaseMessaging.getInstance().unsubscribeFromTopic(notifycation_529);
////
////            if (userModel.getAccount_level().equalsIgnoreCase("admin")) {
////                String notifycation_nino_admin = "nino_notifycation_admin" + userModel.getIDManager();
////                FirebaseMessaging.getInstance().unsubscribeFromTopic(notifycation_nino_admin);
////            }
////
////            if (userModel.getAccount_level().equalsIgnoreCase("employee")) {
////
////                String notifycation_nino_employee = "nino_notifycation_employee_" + userModel.getIDManager();
////                FirebaseMessaging.getInstance().unsubscribeFromTopic(notifycation_nino_employee);
////
////                FirebaseMessaging.getInstance().unsubscribeFromTopic("update_role_permission");
////                FirebaseMessaging.getInstance().unsubscribeFromTopic("update_role_permission_" + userModel.getIDManager());
////            }
////
////            AppProvider.getPreferences().saveUserModel(null);
////
////            String deviceImei = AppProvider.getPreferences().getDeviceImei();
////            String topicCheckAuth = "channel_" + deviceImei;
////            FirebaseMessaging.getInstance().unsubscribeFromTopic(topicCheckAuth);
////
////            AppProvider.getPreferences().saveDeviceImei("");
////            ReloadMenuBottomEvent.post();
////                String deviceUniqueIdentifier = String.valueOf(System.currentTimeMillis());
////                AppProvider.getPreferences().saveDeviceImei(deviceUniqueIdentifier);
//
//        }, Dialog::dismiss, -1);
//
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onReloadMenuBottomEvent(ReloadMenuBottomEvent event) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                if (view != null) {
//                    view.reloadMenuNavigation();
//                }
//            }
//        });
//    }
//
//    public void setPositionBottomMenu(int pos) {
//        view.setBottomMenuBarPossitionSelected(pos);
//
//    }
//
//    public void hideBottomMenuBar() {
//        if (view != null)
//            view.hideBottomMenuBar();
//    }
//
//    public void showBottomMenuBar() {
//        if (view != null)
//            view.showBottomMenuBar();
//    }
//
//    public void changeToFragmentDashboard() {
//        showToast("changeToFragmentDashboard");
////        FullScreencall();
////        isShowContainer = 0;
////        showBottomMenuBar();
////        replaceFragment(new FragmentDashboard(), false);
//    }
//
//    public void changeToFragmentLogin() {
//        view.hideBottomMenuBar();
//        isShowContainer++;
//        FullScreencall();
////        replaceFragment(new FragmentLogin(), true, Animation.SLIDE_IN_OUT);
//    }
//
//    public void changeToFragmentForgotPassword() {
//        FullScreencall();
//        isShowContainer++;
//        view.hideBottomMenuBar();
//
//        replaceFragment(new FragmentForgotPassword(), true, Animation.SLIDE_IN_OUT);
//    }
//
//    public void changeToFragmentRegister() {
//        view.hideBottomMenuBar();
//        FullScreencall();
//        addFragment(new FragmentRegister(), true, Animation.SLIDE_IN_OUT);
//    }
//
//
//    public void changeToFragmentMainMenu() {
//        showToast("changeToFragmentMainMenu");
////        view.hideBottomMenuBar();
////        FullScreencall();
////        replaceFragment(new FragmentMainMenu(), false, Animation.SLIDE_IN_OUT);
//    }
//
//    public void changeToFragmentProfileManager() {
//        FullScreencall();
//        view.hideBottomMenuBar();
//        isShowContainer++;
//
//        replaceFragment(new FragmentProfileManager(), true, Animation.SLIDE_IN_OUT);
//
//    }
//
//    public void changeToFragmentProfileManager(String type_change) {
//        FullScreencall();
//        view.hideBottomMenuBar();
//        addFragment(FragmentProfileManager.newInstance(type_change), true, Animation.SLIDE_IN_OUT);
//
//    }
//
//    public void changeToFragmentPasswordManager() {
//        FullScreencall();
//        view.hideBottomMenuBar();
//        isShowContainer++;
//
//        replaceFragment(new FragmentPasswordManager(), true, Animation.SLIDE_IN_OUT);
//    }
//
//
//    //    private void showMenuTutorial() {
////
////        TutorialModel model = new TutorialModel(R.id.btnMenu, R.string.tutorial_menu, R.layout.view_tutorial_home_menu);
////        model.setArrowBottom(false);
////        view.showTutorial(model, new TutorialView.TutorialListener() {
////            @Override
////            public void onClose() {
////                showHomeListTutorial();
////            }
////
////            @Override
////            public void onAction() {
////            }
////        });
////    }
//
//
//    private LocationRequest mLocationRequest;
//    private FusedLocationProviderClient mFusedProviderClient;
//
//    @RequiresApi(api = Build.VERSION_CODES.M)
//    private void reloadDataCurrentLocation() {
//
//        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
//                PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
//                        PackageManager.PERMISSION_GRANTED) {
//            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
//            requestPermissions(permissions, Consts.REQUEST_CODE_GPS);
//        } else {
//
//            LocationCallback mLocationCallback = new LocationCallback() {
//                @Override
//                public void onLocationResult(LocationResult locationResult) {
//                    super.onLocationResult(locationResult);
//                    BaseFragment fragment = getCurrentFragment();
//
//                    List<Location> locations = locationResult.getLocations();
//
//                    if (locations.size() > 0) {
//
//                    } else {
//                    }
//                }
//            };
//
//            mFusedProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//        }
//    }
//
//    public void checkAuthUser() {
//
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            return;
//        }
//
//        boolean userHasLogin = AppProvider.getPreferences().checkLoginStatus();
//        if (!userHasLogin) {
//            return;
//        }
//
//        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
//
//        String deviceImei = AppProvider.getPreferences().getDeviceImei();
//
//        if (TextUtils.isEmpty(deviceImei))
//            return;
//
////        RequestCheckAuthUser.ApiParams params = new RequestCheckAuthUser.ApiParams();
////
////        params.id_user = userResponseModel.getId();
////        params.device_imei = deviceImei;
////
////        AppProvider.getApiManagement().call(RequestCheckAuthUser.class, params, new ApiRequest.ApiCallback<BaseResponseModel>() {
////            @Override
////            public void onSuccess(BaseResponseModel result) {
////                if (!TextUtils.isEmpty(result.getSuccess()) && result.getSuccess().equalsIgnoreCase("true")) {
////
////                } else {
////                    if (!TextUtils.isEmpty(result.getError_code()) && result.getError_code().equalsIgnoreCase("1502")) {
////                        String mess = result.getMessage();
////                        showAlertLoginedAccount(mess);
////                    }
////                }
////            }
////
////            @Override
////            public void onError(ErrorApiResponse error) {
////
////            }
////
////            @Override
////            public void onFail(ApiRequest.RequestError error) {
////
////            }
////        });
//
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAleartSigned(AleartSignedEvent event) {
//        showAlertLoginedAccount("Tài khoản của bạn đã đăng nhập ở nơi khác!");
//    }
//
//    private void showAlertLoginedAccount(String mess) {
//
//        showConfirmAlert("Cảnh báo đăng nhập!!!", mess, kAlertDialog -> {
//            kAlertDialog.dismiss();
//            //doLogout
//            AppProvider.getPreferences().saveStatusLogin(false);
//            changeToFragmentDashboard();
//            showBottomMenuBar();
//            view.setBottomMenuBarPossitionSelected(0);
//
////            UserResponseModel userModel = AppProvider.getPreferences().getUserModel();
////            String topic = "user" + userModel.getId();
////            FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
////            AppProvider.getPreferences().saveUserModel(null);
////
////            String deviceImei = AppProvider.getPreferences().getDeviceImei();
////            String topicCheckAuth = "channel_" + deviceImei;
////            FirebaseMessaging.getInstance().unsubscribeFromTopic(topicCheckAuth);
////
////            AppProvider.getPreferences().saveDeviceImei("");
////            view.reloadMenuNavigation();
//        }, KAlertDialog.WARNING_TYPE);
//    }
//
//
//    public void checkRolePermission() {
//
//        if (!AppProvider.getConnectivityHelper().hasInternetConnection()) {
//            return;
//        }
//
//        if (!AppProvider.getPreferences().checkLoginStatus()) {
//            return;
//        }
//
////        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
////
////        RequestCheckRole.ApiParams params = new RequestCheckRole.ApiParams();
////        params.id_user = userResponseModel.getIDManager();
////
////        AppProvider.getApiManagement().call(RequestCheckRole.class, params, new ApiRequest.ApiCallback<BaseResponseModel<UserResponseModel>>() {
////            @Override
////            public void onSuccess(BaseResponseModel<UserResponseModel> result) {
////                if (!TextUtils.isEmpty(result.getSuccess()) && Objects.requireNonNull(result.getSuccess()).equalsIgnoreCase("true")) {
////                    AppProvider.getPreferences().saveUserModel(result.getData()[0]);
////                    ReloadRolePermissionEvent.post();
////                }
////            }
////
////            @Override
////            public void onError(ErrorApiResponse error) {
////
////            }
////
////            @Override
////            public void onFail(ApiRequest.RequestError error) {
////
////            }
////        });
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAlertCheckRolePermissionEvent(AlertCheckRolePermissionEvent event) {
//        checkRolePermission();
//    }
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAlertCheckUserRolePermissionEvent(AlertCheckUserRolePermissionEvent event) {
//        UserResponseModel userResponseModel = AppProvider.getPreferences().getUserModel();
//        if (userResponseModel != null && userResponseModel.getAccount_level().equalsIgnoreCase("employee")) {
//
//            showConfirmAlert("Cập nhật phân quyền", "Tài khoản đã được cập nhật phân quyền, vui lòng đăng nhập lại.", new KAlertDialog.KAlertClickListener() {
//                @Override
//                public void onClick(KAlertDialog kAlertDialog) {
//                    kAlertDialog.dismiss();
//                    AppProvider.getPreferences().saveStatusLogin(false);
//
////                    FirebaseMessaging.getInstance().unsubscribeFromTopic("update_role_permission");
////                    FirebaseMessaging.getInstance().unsubscribeFromTopic("update_role_permission_" + userResponseModel.getId());
////
////                    String topic = "user" + userResponseModel.getId();
////                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
////
////                    String notifycation_529 = "nino_notifycation";
////
////                    if (userResponseModel != null) {
////                        String notifycation_529_user = "nino_notifycation_user_" + userResponseModel.getId();
////                        FirebaseMessaging.getInstance().unsubscribeFromTopic(notifycation_529_user);
////                    }
////
////                    FirebaseMessaging.getInstance().unsubscribeFromTopic(notifycation_529);
////
////                    AppProvider.getPreferences().saveUserModel(null);
////
////                    String deviceImei = AppProvider.getPreferences().getDeviceImei();
////                    String topicCheckAuth = "channel_" + deviceImei;
////                    FirebaseMessaging.getInstance().unsubscribeFromTopic(topicCheckAuth);
////
////                    AppProvider.getPreferences().saveUserModel(null);
////                    changeToFragmentLogin();
//
//                }
//            }, KAlertDialog.WARNING_TYPE);
//        }
//    }
//
//
//    @SuppressWarnings("unused")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onAlertCheckTaskEvent(AlertCheckTaskEvent event) {
//        UserResponseModel userModel = AppProvider.getPreferences().getUserModel();
//        if (userModel == null) {
//            return;
//        }
//        if (userModel.getAccount_level().equalsIgnoreCase("employee")) {
//            showConfirmAlert("Thông báo công việc!!!", "Bạn nhận được công việc từ phía quản lý.", new KAlertDialog.KAlertClickListener() {
//                @Override
//                public void onClick(KAlertDialog kAlertDialog) {
//                    kAlertDialog.dismiss();
////                    changeToFragmentListTask();
//                }
//            }, KAlertDialog.WARNING_TYPE);
//        }
//    }
//}
