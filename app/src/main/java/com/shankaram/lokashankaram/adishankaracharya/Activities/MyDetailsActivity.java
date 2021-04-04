package com.shankaram.lokashankaram.adishankaracharya.Activities;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.shankaram.lokashankaram.adishankaracharya.Constants.ActivityScreenTransition;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppConstants;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppUtils;
import com.shankaram.lokashankaram.adishankaracharya.R;

public class MyDetailsActivity extends AppCompatActivity {
    private final String TAG = AppConstants.TAG + MyDetailsActivity.class.getSimpleName();

    private EditText mName, mPhone, mAddress;
    private Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //makeFullScreenActivity();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_my_details);

        mName = (EditText) findViewById(R.id.et_name);
        mPhone = (EditText) findViewById(R.id.et_phone);
        mAddress = (EditText) findViewById(R.id.et_address);
        mSend = (Button) findViewById(R.id.btn_details);

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mName.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mPhone.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mAddress.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.sendEmail(MyDetailsActivity.this, AppConstants.DETAILS_SUBJECT,
                        getEmailBody());
                AppUtils.saveDetailsSentStatus(MyDetailsActivity.this);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        if (!AppUtils.isInternetConnected(this)) {
            AppUtils.showOfflineDialog(this);
        } else if (AppUtils.isDetailsSent(this)) {
            showDetailsSentDialog();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppUtils.alertDialog = null;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        ActivityScreenTransition.animateScreen(this,
                ActivityScreenTransition.ANIM_TYPE.EXIT);
    }


    private void makeFullScreenActivity() {
        Log.d(TAG, "making Full screen activity");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /*private void sendEmail() {
        Intent detailsIntent = new Intent(Intent.ACTION_SEND);
        detailsIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {AppConstants.MAIL_TO});
        detailsIntent.putExtra(Intent.EXTRA_SUBJECT, AppConstants.DETAILS_SUBJECT);
        detailsIntent.putExtra(Intent.EXTRA_TEXT, getEmailBody());
        detailsIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(detailsIntent, "Choose an Email app..."));
        finish();
        ActivityScreenTransition.animateScreen(this,
                ActivityScreenTransition.ANIM_TYPE.ENTER);
    }*/


    private String getEmailBody() {
        String bodyText;

        if (TextUtils.isEmpty(mName.getText().toString())) {
            bodyText = "Name : <not specified>";
        } else {
            bodyText = "Name : " + mName.getText().toString();
        }

        bodyText = bodyText + "\n\n";

        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            bodyText = bodyText + "Phone : <not specified>";
        } else {
            bodyText = bodyText + "Phone : " + mPhone.getText().toString();
        }

        bodyText = bodyText + "\n\n";

        if (TextUtils.isEmpty(mAddress.getText().toString())) {
            bodyText = bodyText + "Address : <not specified>";
        } else {
            bodyText = bodyText + "Address : " + mAddress.getText().toString();
        }

        return bodyText;
    }


    private void showDetailsSentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Details have already been sent")
                .setMessage("Your details have already sent and recorded successfully. " +
                        "Do you still want to send again?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                ActivityScreenTransition.animateScreen(MyDetailsActivity.this,
                        ActivityScreenTransition.ANIM_TYPE.EXIT);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }
}
