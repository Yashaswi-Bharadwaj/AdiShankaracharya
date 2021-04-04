package com.shankaram.lokashankaram.adishankaracharya.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.shankaram.lokashankaram.adishankaracharya.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class AppUtils {
    private static final String TAG = AppConstants.TAG + AppUtils.class.getSimpleName();

    public static AlertDialog alertDialog = null;

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }


    public static void showOfflineDialog(final Context context) {
        Log.d(TAG, "user is OFFLINE. Hence, showing dialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.offline_title))
                .setMessage(context.getString(R.string.offline_msg))
                .setPositiveButton(context.getString(R.string.offline_button),
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        alertDialog = null;
                        ((Activity) context).finish();
                        ActivityScreenTransition.animateScreen(context,
                                ActivityScreenTransition.ANIM_TYPE.EXIT);
                    }
                });

        if (alertDialog == null) {
            alertDialog = builder.create();
            alertDialog.show();
        } else {
            Log.d(TAG, "previous dialog is still active");
        }
    }


    public static void sendEmail(Context context, String subject, String bodyMsg) {
        Intent detailsIntent = new Intent(Intent.ACTION_SEND);
        detailsIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {AppConstants.MAIL_TO});
        detailsIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        detailsIntent.putExtra(Intent.EXTRA_TEXT, bodyMsg);
        detailsIntent.setType("message/rfc822");
        context.startActivity(Intent.createChooser(detailsIntent, "Choose an Email app..."));
        ((Activity) context).finish();
        ActivityScreenTransition.animateScreen(context,
                ActivityScreenTransition.ANIM_TYPE.ENTER);


        /*List<Intent> emailAppLauncherIntents = new ArrayList<>();

        //Intent that only email apps can handle:
        Intent emailAppIntent = new Intent(Intent.ACTION_SENDTO);
        emailAppIntent.setData(Uri.parse("mailto:"));
        emailAppIntent.putExtra(Intent.EXTRA_EMAIL, "");
        emailAppIntent.putExtra(Intent.EXTRA_SUBJECT, "");

        PackageManager packageManager = context.getPackageManager();

        //All installed apps that can handle email intent:
        List<ResolveInfo> emailApps = packageManager.queryIntentActivities(emailAppIntent, PackageManager.MATCH_ALL);

        for (ResolveInfo resolveInfo : emailApps) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
            emailAppLauncherIntents.add(launchIntent);
        }

        //Create chooser
        Intent chooserIntent = Intent.createChooser(new Intent(), "Select email app:");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, emailAppLauncherIntents.toArray(new Parcelable[emailAppLauncherIntents.size()]));
        context.startActivity(chooserIntent);*/
    }


    public static void saveDetailsSentStatus (Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AppConstants.KEY_DETAILS_SENT, true);
        editor.apply();
    }


    public static boolean isDetailsSent (Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(AppConstants.KEY_DETAILS_SENT, false);
    }


    public static void saveTotalCount (Context context, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(AppConstants.KEY_TOTAL_COUNT, value);
        editor.apply();
    }


    public static int getTotalCount (Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(AppConstants.KEY_TOTAL_COUNT, 0);
    }
}
