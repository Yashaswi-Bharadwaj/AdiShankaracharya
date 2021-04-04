package com.shankaram.lokashankaram.adishankaracharya.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.shankaram.lokashankaram.adishankaracharya.BuildConfig;
import com.shankaram.lokashankaram.adishankaracharya.Constants.ActivityScreenTransition;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppConstants;
import com.shankaram.lokashankaram.adishankaracharya.R;

public class SplashScreenActivity extends AppCompatActivity {
    private final String TAG = AppConstants.TAG + SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        makeFullScreenActivity();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this,
                        HomeScreenActivity.class);
                startActivity(intent);
                finish();
                ActivityScreenTransition.animateScreen(SplashScreenActivity.this,
                        ActivityScreenTransition.ANIM_TYPE.ENTER);
            }
        }, 1000);
    }


    private void makeFullScreenActivity() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "making FullScreen activity");
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
