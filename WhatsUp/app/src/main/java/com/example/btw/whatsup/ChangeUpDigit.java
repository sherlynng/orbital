package com.example.btw.whatsup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by BTW on 5/24/2017.
 */

public class ChangeUpDigit extends Activity {
    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPDIGIT_DEFAULT = 7;
    int UP;
    protected boolean continueMusic = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeupdigit);
        UP = getIntent().getIntExtra(UPDIGIT, UPDIGIT_DEFAULT);
        final Handler handler = new Handler();
        final TextView textView = (TextView) findViewById(R.id.newUpDigit);
        textView.setText(Integer.toString(UP));

        final Runnable counter = new Runnable() {
            @Override
            public void run() {
               finish();
            }
        };
        handler.postDelayed(counter, 1500);
    }

    @Override
    public void onBackPressed(){
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);
    }

/*
    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPause() {
        if (isApplicationSentToBackground(this)){
            // Do what you want to do on detecting Home Key being Pressed
            Intent i = new Intent(this, ChooseUpMode.class);
            this.startActivity(i);
        }
        super.onPause();
    }
*/
}