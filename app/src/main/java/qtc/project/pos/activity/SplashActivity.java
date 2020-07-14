//package qtc.project.pos.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
//import qtc.project.pos.R;
//import qtc.project.pos.adapter.SlidingImage_Adapter;
//import qtc.project.pos.dependency.AppProvider;
//
//
//public class SplashActivity extends Activity {
//
//    private View layoutSplash1;
//    private View layoutSplash2;
//    private View layoutSplash3;
//    private View loadingView;
//
//    private final Integer[] IMAGES = {R.drawable.no_image_full, R.drawable.no_image_full, R.drawable.no_image_full};
//    private final ArrayList<Integer> ImagesArray = new ArrayList<>();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        checkLogin();
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.activity_splash);
//
//        layoutSplash1 = findViewById(R.id.layoutSplash1);
//        layoutSplash2 = findViewById(R.id.layoutSplash2);
//        layoutSplash3 = findViewById(R.id.layoutSplash3);
//        View btnBegin = findViewById(R.id.btnBegin);
//        loadingView = findViewById(R.id.loadingView);
//        loadingView.setVisibility(View.GONE);
//
//        Collections.addAll(ImagesArray, IMAGES);
//
//        ViewPager viewPager = findViewById(R.id.viewPager);
//
//        SlidingImage_Adapter adapter = new SlidingImage_Adapter(SplashActivity.this, SlidingImage_Adapter.SliderImageType.RESOURCE);
//        adapter.setShowLoading(false);
//        adapter.setIMAGE_RESOURCE(ImagesArray);
//
//        viewPager.setAdapter(adapter);
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//                switch (i) {
//                    case 0:
//                        layoutSplash1.setVisibility(View.VISIBLE);
//                        layoutSplash2.setVisibility(View.GONE);
//                        layoutSplash3.setVisibility(View.GONE);
//
//                        break;
//                    case 1:
//                        layoutSplash1.setVisibility(View.GONE);
//                        layoutSplash2.setVisibility(View.VISIBLE);
//                        layoutSplash3.setVisibility(View.GONE);
//
//                        break;
//                    case 2:
//                        layoutSplash1.setVisibility(View.GONE);
//                        layoutSplash2.setVisibility(View.GONE);
//                        layoutSplash3.setVisibility(View.VISIBLE);
//
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });
//
//        btnBegin.setOnClickListener(v -> {
//
//            loadingView.setVisibility(View.VISIBLE);
//
//            Intent intent = new Intent(SplashActivity.this, HomeActivityBackup.class);
//
//            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_right_in);
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            startActivity(intent);
//            finish();
//
//        });
//    }
//
//    private void checkLogin() {
//        if (AppProvider.getPreferences().checkLoginStatus()) {
//            Intent intent = new Intent(SplashActivity.this, HomeActivityBackup.class);
//
//            overridePendingTransition(R.anim.slide_left_out, R.anim.slide_right_in);
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            startActivity(intent);
//            finish();
//        }
//    }
//}
