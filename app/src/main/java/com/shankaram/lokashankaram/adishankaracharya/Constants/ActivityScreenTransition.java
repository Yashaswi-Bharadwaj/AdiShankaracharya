package com.shankaram.lokashankaram.adishankaracharya.Constants;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.shankaram.lokashankaram.adishankaracharya.R;

public class ActivityScreenTransition {
    private static final String TAG = AppConstants.TAG +
            ActivityScreenTransition.class.getSimpleName();

    public enum ANIM_TYPE {
        ENTER,      // Starting of an activity
        EXIT,       // Closing of an activity
        NO_ANIM     // No animations
    }

    public static void animateScreen(Context context, ANIM_TYPE animType) {
        if (animType != null) {
            if (context instanceof Activity) {
                switch (animType) {
                    case ENTER:
                        ((Activity) context).overridePendingTransition(
                                R.anim.screen_enter, 0);
                        break;

                    case EXIT:
                        ((Activity) context).overridePendingTransition(
                                0, R.anim.screen_exit);
                        break;

                    case NO_ANIM:
                    default:
                        ((Activity) context).overridePendingTransition(0, 0);
                }
            }
        } else{
            Log.d(TAG, "Screen transition is only for Activities");
        }
    }
}
