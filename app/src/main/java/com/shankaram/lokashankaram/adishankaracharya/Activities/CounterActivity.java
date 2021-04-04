package com.shankaram.lokashankaram.adishankaracharya.Activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shankaram.lokashankaram.adishankaracharya.Constants.ActivityScreenTransition;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppConstants;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppUtils;
import com.shankaram.lokashankaram.adishankaracharya.R;

public class CounterActivity extends AppCompatActivity {
    private final String TAG = AppConstants.TAG + CounterActivity.class.getSimpleName();

    private EditText mCounterValue;
    private Button mUpdateCount;
    private TextView mMyTotalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        //makeFullScreenActivity();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_counter);

        mCounterValue = (EditText) findViewById(R.id.et_counter);
        mUpdateCount = (Button) findViewById(R.id.btn_counter);
        mMyTotalCount = (TextView) findViewById(R.id.tv_latest_count);

        mCounterValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mCounterValue.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUpdateCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mCounterValue.getText().toString())) {
                    AppUtils.sendEmail(CounterActivity.this, AppConstants.COUNTER_SUBJECT,
                            getEmailBody());
                } else {
                    Toast.makeText(CounterActivity.this, "Please enter the value...",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        if (!AppUtils.isInternetConnected(this)) {
            AppUtils.showOfflineDialog(this);
        }

        mMyTotalCount.setText("Your total count (till today) : " +
                AppUtils.getTotalCount(this));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        ActivityScreenTransition.animateScreen(this,
                ActivityScreenTransition.ANIM_TYPE.EXIT);
    }


    private String getEmailBody() {
        int currentValue = Integer.parseInt(mCounterValue.getText().toString());
        int totalValue = currentValue + AppUtils.getTotalCount(this);
        String bodyText = "My TOTAL japa count : " + totalValue;

        AppUtils.saveTotalCount(this, totalValue);

        Log.d(TAG, "sending Email BODY as => " + bodyText);
        return bodyText;
    }
}
