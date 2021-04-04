package com.shankaram.lokashankaram.adishankaracharya.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.shankaram.lokashankaram.adishankaracharya.BuildConfig;
import com.shankaram.lokashankaram.adishankaracharya.Constants.ActivityScreenTransition;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppConstants;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppUtils;
import com.shankaram.lokashankaram.adishankaracharya.R;

public class WebviewActivity extends AppCompatActivity {
    private final String TAG = AppConstants.TAG + WebviewActivity.class.getSimpleName();

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        makeFullScreenActivity();

        webView = (WebView) findViewById(R.id.wv_website);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.loadUrl("http://www.shrishankaracharya.com");
        webView.setWebViewClient(new MyWebViewClient(progressBar));
    }


    private void makeFullScreenActivity() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "making FullScreen activity");
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        if (!AppUtils.isInternetConnected(this)) {
            AppUtils.showOfflineDialog(this);
        }
    }


    private class MyWebViewClient extends WebViewClient {
        private ProgressBar progressBar;

        public MyWebViewClient(ProgressBar bar) {
            progressBar = bar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        ActivityScreenTransition.animateScreen(this,
                ActivityScreenTransition.ANIM_TYPE.EXIT);
    }

}
