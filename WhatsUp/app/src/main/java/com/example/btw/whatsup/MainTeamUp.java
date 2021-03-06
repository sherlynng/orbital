package com.example.btw.whatsup;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by BTW on 6/23/2017.
 */


public class MainTeamUp extends Activity implements View.OnClickListener, View.OnTouchListener {

    private boolean continueFromLast;
    protected SharedPreferences gameDataTeamUp;
    protected SharedPreferences.Editor editor;
    protected boolean continueMusic = true;

    public static final String UPDIGIT = "UPDIGIT";
    private int UP;
    private long timeLeft;
    private int score;
    //  private int bestScore;
    private int current;
    private int lastGeneratedNum;
    private int life;
    private ArrayList<Integer> fillArr = new ArrayList<Integer>();  //arraylist that contains new numbers to replace tapped numbers
    private String fillArrContent = "";
    private int pressedNum;
    private int pressedID;
    private Button pressedButton;
    public CountDownTimerPausable cdt;

    private LinearLayout bg;
    protected TextView currentScoreUp;
    protected TextView currentScoreBottom;
    protected TextView currentNumTextUp;
    protected TextView currentNumTextBottom;
    protected TextView upDisplayTextUp;
    protected TextView upDisplayTextBottom;
    protected Button UpButtonPlayerUp;
    protected Button UpButtonPlayerBottom;
    private ImageView life1, life2, life3;
    private String upButtonSide;

    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected Button btn4;
    protected Button btn5;
    protected Button btn6;
    protected Button btn7;
    protected Button btn8;
    protected Button btn9;
    protected Button btn10;
    protected Button btn11;
    protected Button btn12;
    protected Button btn13;
    protected Button btn14;
    protected Button btn15;
    protected Button btn16;
    protected Button btn17;
    protected Button btn18;
    protected Button btn19;
    protected Button btn20;
    protected Button btn21;
    protected Button btn22;
    protected Button btn23;
    protected Button btn24;
    protected Button btn25;
    protected Button btn26;
    protected Button btn27;
    protected Button btn28;
    protected Button btn29;
    protected Button btn30;
    protected Button btn31;
    protected Button btn32;
    protected Button btn33;
    protected Button btn34;
    protected Button btn35;
    protected Button btn36;

    public static boolean playMusic;
    public static boolean vibrationOn;

    private SoundPoolHelper mSoundPoolHelper;
    private int explodeId, tapId, upId;

    public static void checkMusic(Context context) {
        if (Settings.getMusic(context)) {
            playMusic = true;
        } else {
            playMusic = false;
        }

    }

    public static void checkVibration(Context context) {
        if (Settings.getVibration(context)) {
            vibrationOn = true;
        } else {
            vibrationOn = false;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainteamup);

        checkVibration(this);
        checkMusic(this);

        mSoundPoolHelper = new SoundPoolHelper(1, this);
        explodeId = mSoundPoolHelper.load(this, R.raw.explode, 1);
        upId = mSoundPoolHelper.load(this, R.raw.up, 1);
        tapId = mSoundPoolHelper.load(this, R.raw.tap, 1);

        bg = (LinearLayout) this.findViewById(R.id.main);


        UpButtonPlayerUp = (Button) this.findViewById(R.id.up_button_playerUp);
        UpButtonPlayerBottom = (Button) this.findViewById(R.id.up_button_playerBottom);
        btn1 = (Button) this.findViewById(R.id.btn1);
        //    btn1.setBackgroundResource(R.drawable.yellow_spark);
        btn2 = (Button) this.findViewById(R.id.btn2);
        //   btn2.setBackgroundResource(R.drawable.orange_spark);
        btn3 = (Button) this.findViewById(R.id.btn3);
        //     btn3.setBackgroundResource(R.drawable.pink_spark);
        btn4 = (Button) this.findViewById(R.id.btn4);
        //    btn4.setBackgroundResource(R.drawable.blue_spark);
        btn5 = (Button) this.findViewById(R.id.btn5);
        //    btn5.setBackgroundResource(R.drawable.green_spark);
        btn6 = (Button) this.findViewById(R.id.btn6);
        //    btn6.setBackgroundResource(R.drawable.purple_spark);
        btn7 = (Button) this.findViewById(R.id.btn7);
        //    btn7.setBackgroundResource(R.drawable.purple_spark);
        btn8 = (Button) this.findViewById(R.id.btn8);
        //    btn8.setBackgroundResource(R.drawable.yellow_spark);
        btn9 = (Button) this.findViewById(R.id.btn9);
        //     btn9.setBackgroundResource(R.drawable.orange_spark);
        btn10 = (Button) this.findViewById(R.id.btn10);
        //    btn10.setBackgroundResource(R.drawable.pink_spark);
        btn11 = (Button) this.findViewById(R.id.btn11);
        //     btn11.setBackgroundResource(R.drawable.blue_spark);
        btn12 = (Button) this.findViewById(R.id.btn12);
        //    btn12.setBackgroundResource(R.drawable.green_spark);
        btn13 = (Button) this.findViewById(R.id.btn13);
        //     btn13.setBackgroundResource(R.drawable.green_spark);
        btn14 = (Button) this.findViewById(R.id.btn14);
        //     btn14.setBackgroundResource(R.drawable.purple_spark);
        btn15 = (Button) this.findViewById(R.id.btn15);
        //    btn15.setBackgroundResource(R.drawable.yellow_spark);
        btn16 = (Button) this.findViewById(R.id.btn16);
        //    btn16.setBackgroundResource(R.drawable.orange_spark);
        btn17 = (Button) this.findViewById(R.id.btn17);
        //    btn17.setBackgroundResource(R.drawable.pink_spark);
        btn18 = (Button) this.findViewById(R.id.btn18);
        //      btn18.setBackgroundResource(R.drawable.blue_spark);
        btn19 = (Button) this.findViewById(R.id.btn19);
        //     btn19.setBackgroundResource(R.drawable.blue_spark);
        btn20 = (Button) this.findViewById(R.id.btn20);
        //     btn20.setBackgroundResource(R.drawable.green_spark);
        btn21 = (Button) this.findViewById(R.id.btn21);
        //     btn21.setBackgroundResource(R.drawable.purple_spark);
        btn22 = (Button) this.findViewById(R.id.btn22);
        //     btn22.setBackgroundResource(R.drawable.yellow_spark);
        btn23 = (Button) this.findViewById(R.id.btn23);
        //    btn23.setBackgroundResource(R.drawable.orange_spark);
        btn24 = (Button) this.findViewById(R.id.btn24);
        //     btn24.setBackgroundResource(R.drawable.pink_spark);
        btn25 = (Button) this.findViewById(R.id.btn25);
        //      btn25.setBackgroundResource(R.drawable.pink_spark);
        btn26 = (Button) this.findViewById(R.id.btn26);
        //      btn26.setBackgroundResource(R.drawable.blue_spark);
        btn27 = (Button) this.findViewById(R.id.btn27);
        //     btn27.setBackgroundResource(R.drawable.green_spark);
        btn28 = (Button) this.findViewById(R.id.btn28);
        //    btn28.setBackgroundResource(R.drawable.purple_spark);
        btn29 = (Button) this.findViewById(R.id.btn29);
        //     btn29.setBackgroundResource(R.drawable.yellow_spark);
        btn30 = (Button) this.findViewById(R.id.btn30);
        //     btn30.setBackgroundResource(R.drawable.orange_spark);
        btn31 = (Button) this.findViewById(R.id.btn31);
        //     btn31.setBackgroundResource(R.drawable.orange_spark);
        btn32 = (Button) this.findViewById(R.id.btn32);
        //     btn32.setBackgroundResource(R.drawable.pink_spark);
        btn33 = (Button) this.findViewById(R.id.btn33);
        //     btn33.setBackgroundResource(R.drawable.blue_spark);
        btn34 = (Button) this.findViewById(R.id.btn34);
        //     btn34.setBackgroundResource(R.drawable.green_spark);
        btn35 = (Button) this.findViewById(R.id.btn35);
        //     btn35.setBackgroundResource(R.drawable.purple_spark);
        btn36 = (Button) this.findViewById(R.id.btn36);
        //    btn36.setBackgroundResource(R.drawable.yellow_spark);

        gameDataTeamUp = getSharedPreferences("gameDataTeamUp", Context.MODE_PRIVATE);
        editor = gameDataTeamUp.edit();

        continueFromLast = gameDataTeamUp.getBoolean("continuefromlastTeamUp", false);
        if (continueFromLast) {
            Log.d("SAVE", "retrieved and restarted TeamUp");
            UP = gameDataTeamUp.getInt("UPdigit", 1);
            score = gameDataTeamUp.getInt("score", 0);
            life = gameDataTeamUp.getInt("life", 3);
            current = gameDataTeamUp.getInt("current", 1);
            lastGeneratedNum = gameDataTeamUp.getInt("lastGeneratedNum", 1);
            timeLeft = gameDataTeamUp.getLong("timeLeft", 120000);
            fillArrContent = gameDataTeamUp.getString("FILLARR_CONTENT", "");
            if (!fillArrContent.equals("")) {
                String[] strArray = fillArrContent.split(",");
                fillArr.clear();

                for (int i = 0; i < strArray.length; i++) {
                    fillArr.add(0, Integer.parseInt(strArray[i]));
                }
            }
            life3 = (ImageView) findViewById(R.id.life3);
            life3.setImageResource(R.drawable.life3);
            life2 = (ImageView) findViewById(R.id.life2);
            life2.setImageResource(R.drawable.life2);
            life1 = (ImageView) findViewById(R.id.life1);
            life1.setImageResource(R.drawable.life1);

            if (life == 2) {
                life3.setVisibility(View.INVISIBLE);
            }
            else if(life == 1) {
                life3.setVisibility(View.INVISIBLE);
                life2.setVisibility(View.INVISIBLE);
            }

            TextView t;
            t = (TextView) findViewById(R.id.btn1);
            t.setText(gameDataTeamUp.getString("btn1", ""));
            t = (TextView) findViewById(R.id.btn2);
            t.setText(gameDataTeamUp.getString("btn2", ""));
            t = (TextView) findViewById(R.id.btn3);
            t.setText(gameDataTeamUp.getString("btn3", ""));
            t = (TextView) findViewById(R.id.btn4);
            t.setText(gameDataTeamUp.getString("btn4", ""));
            t = (TextView) findViewById(R.id.btn5);
            t.setText(gameDataTeamUp.getString("btn5", ""));
            t = (TextView) findViewById(R.id.btn6);
            t.setText(gameDataTeamUp.getString("btn6", ""));
            t = (TextView) findViewById(R.id.btn7);
            t.setText(gameDataTeamUp.getString("btn7", ""));
            t = (TextView) findViewById(R.id.btn8);
            t.setText(gameDataTeamUp.getString("btn8", ""));
            t = (TextView) findViewById(R.id.btn9);
            t.setText(gameDataTeamUp.getString("btn9", ""));
            t = (TextView) findViewById(R.id.btn10);
            t.setText(gameDataTeamUp.getString("btn10", ""));
            t = (TextView) findViewById(R.id.btn11);
            t.setText(gameDataTeamUp.getString("btn11", ""));
            t = (TextView) findViewById(R.id.btn12);
            t.setText(gameDataTeamUp.getString("btn12", ""));
            t = (TextView) findViewById(R.id.btn13);
            t.setText(gameDataTeamUp.getString("btn13", ""));
            t = (TextView) findViewById(R.id.btn14);
            t.setText(gameDataTeamUp.getString("btn14", ""));
            t = (TextView) findViewById(R.id.btn15);
            t.setText(gameDataTeamUp.getString("btn15", ""));
            t = (TextView) findViewById(R.id.btn16);
            t.setText(gameDataTeamUp.getString("btn16", ""));
            t = (TextView) findViewById(R.id.btn17);
            t.setText(gameDataTeamUp.getString("btn17", ""));
            t = (TextView) findViewById(R.id.btn18);
            t.setText(gameDataTeamUp.getString("btn18", ""));
            t = (TextView) findViewById(R.id.btn19);
            t.setText(gameDataTeamUp.getString("btn19", ""));
            t = (TextView) findViewById(R.id.btn20);
            t.setText(gameDataTeamUp.getString("btn20", ""));
            t = (TextView) findViewById(R.id.btn21);
            t.setText(gameDataTeamUp.getString("btn21", ""));
            t = (TextView) findViewById(R.id.btn22);
            t.setText(gameDataTeamUp.getString("btn22", ""));
            t = (TextView) findViewById(R.id.btn23);
            t.setText(gameDataTeamUp.getString("btn23", ""));
            t = (TextView) findViewById(R.id.btn24);
            t.setText(gameDataTeamUp.getString("btn24", ""));
            t = (TextView) findViewById(R.id.btn25);
            t.setText(gameDataTeamUp.getString("btn25", ""));
            t = (TextView) findViewById(R.id.btn26);
            t.setText(gameDataTeamUp.getString("btn26", ""));
            t = (TextView) findViewById(R.id.btn27);
            t.setText(gameDataTeamUp.getString("btn27", ""));
            t = (TextView) findViewById(R.id.btn28);
            t.setText(gameDataTeamUp.getString("btn28", ""));
            t = (TextView) findViewById(R.id.btn29);
            t.setText(gameDataTeamUp.getString("btn29", ""));
            t = (TextView) findViewById(R.id.btn30);
            t.setText(gameDataTeamUp.getString("btn30", ""));
            t = (TextView) findViewById(R.id.btn31);
            t.setText(gameDataTeamUp.getString("btn31", ""));
            t = (TextView) findViewById(R.id.btn32);
            t.setText(gameDataTeamUp.getString("btn32", ""));
            t = (TextView) findViewById(R.id.btn33);
            t.setText(gameDataTeamUp.getString("btn33", ""));
            t = (TextView) findViewById(R.id.btn34);
            t.setText(gameDataTeamUp.getString("btn34", ""));
            t = (TextView) findViewById(R.id.btn35);
            t.setText(gameDataTeamUp.getString("btn35", ""));
            t = (TextView) findViewById(R.id.btn36);
            t.setText(gameDataTeamUp.getString("btn36", ""));

        } else {
            Log.d("SAVE", "new game of TeamUp");
            Random rand = new Random();
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            UP = rand.nextInt((9 - 3) + 1) + 3;
            //  UP= ThreadLocalRandom.current().nextInt(3, 9 + 1);
            score = 0;
            life = 3;
            current = 1;
            timeLeft = 120000;
            initialiseGrid(1);
            lastGeneratedNum = 36;
            populateArr();
        }

        currentScoreUp = (TextView) findViewById(R.id.currentScoreUp);
        currentScoreUp.setText(score + "");
        currentNumTextUp = (TextView) findViewById(R.id.currentNumTextUp);
        currentNumTextUp.setText(current + "");
        upDisplayTextUp = (TextView) findViewById(R.id.UPDisplayTextUp);
        upDisplayTextUp.setText(UP + "");

        currentScoreBottom = (TextView) findViewById(R.id.currentScoreBottom);
        currentScoreBottom.setText(score + "");
        currentNumTextBottom = (TextView) findViewById(R.id.currentNumTextBottom);
        currentNumTextBottom.setText(current + "");
        upDisplayTextBottom = (TextView) findViewById(R.id.UPDisplayTextBottom);
        upDisplayTextBottom.setText(UP + "");


        //321 animation
        Intent intent = new Intent(this, Onetwothree.class);
        intent.putExtra(Onetwothree.UPDIGIT, UP);
        startActivity(intent);


        final TextView timerLeft = (TextView) findViewById(R.id.countdownLeft);
    //    final TextView timerRight = (TextView) findViewById(R.id.countdownRight);

        timerLeft.setText("-- : --");
    //    timerRight.setText("-- : --");

        cdt = new CountDownTimerPausable(timeLeft, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerLeft.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            /*    timerRight.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))); */
            }

            @Override
            public void onFinish() {
                timerLeft.setText("00 : 00");
            //    timerRight.setText("00 : 00");
                cdt.cancel();
                editor.putInt("bestScore", Math.max(score, gameDataTeamUp.getInt("bestScore", -1))).commit();
                editor.putInt("score", score).commit();
                GameOver(1);
            }
        };


        final Handler handler = new Handler();
        final Runnable counter = new Runnable() {
            @Override
            public void run() {
                cdt.create();
            }
        };
        handler.postDelayed(counter, 5000);

        final Handler handler2 = new Handler();
        final Runnable counter2 = new Runnable() {
            @Override
            public void run() {
                startPause();
            }
        };
        handler2.postDelayed(counter2, 5001);


    }

    @Override
    protected void onStart(){
        super.onStart();

        UpButtonPlayerBottom.setBackgroundResource(R.drawable.up_bear);
        UpButtonPlayerUp.setBackgroundResource(R.drawable.up_bear);
        btn1.setBackgroundResource(R.drawable.yellow_spark);
        btn2.setBackgroundResource(R.drawable.orange_spark);
        btn3.setBackgroundResource(R.drawable.pink_spark);
        btn4.setBackgroundResource(R.drawable.blue_spark);
        btn5.setBackgroundResource(R.drawable.green_spark);
        btn6.setBackgroundResource(R.drawable.purple_spark);
        btn7.setBackgroundResource(R.drawable.purple_spark);
        btn8.setBackgroundResource(R.drawable.yellow_spark);
        btn9.setBackgroundResource(R.drawable.orange_spark);
        btn10.setBackgroundResource(R.drawable.pink_spark);
        btn11.setBackgroundResource(R.drawable.blue_spark);
        btn12.setBackgroundResource(R.drawable.green_spark);
        btn13.setBackgroundResource(R.drawable.green_spark);
        btn14.setBackgroundResource(R.drawable.purple_spark);
        btn15.setBackgroundResource(R.drawable.yellow_spark);
        btn16.setBackgroundResource(R.drawable.orange_spark);
        btn17.setBackgroundResource(R.drawable.pink_spark);
        btn18.setBackgroundResource(R.drawable.blue_spark);
        btn19.setBackgroundResource(R.drawable.blue_spark);
        btn20.setBackgroundResource(R.drawable.green_spark);
        btn21.setBackgroundResource(R.drawable.purple_spark);
        btn22.setBackgroundResource(R.drawable.yellow_spark);
        btn23.setBackgroundResource(R.drawable.orange_spark);
        btn24.setBackgroundResource(R.drawable.pink_spark);
        btn25.setBackgroundResource(R.drawable.pink_spark);
        btn26.setBackgroundResource(R.drawable.blue_spark);
        btn27.setBackgroundResource(R.drawable.green_spark);
        btn28.setBackgroundResource(R.drawable.purple_spark);
        btn29.setBackgroundResource(R.drawable.yellow_spark);
        btn30.setBackgroundResource(R.drawable.orange_spark);
        btn31.setBackgroundResource(R.drawable.orange_spark);
        btn32.setBackgroundResource(R.drawable.pink_spark);
        btn33.setBackgroundResource(R.drawable.blue_spark);
        btn34.setBackgroundResource(R.drawable.green_spark);
        btn35.setBackgroundResource(R.drawable.purple_spark);
        btn36.setBackgroundResource(R.drawable.yellow_spark);

        life3 = (ImageView) findViewById(R.id.life3);
        life3.setImageResource(R.drawable.life3);
        life2 = (ImageView) findViewById(R.id.life2);
        life2.setImageResource(R.drawable.life2);
        life1 = (ImageView) findViewById(R.id.life1);
        life1.setImageResource(R.drawable.life1);

        if (life == 2) {
            life3.setVisibility(View.INVISIBLE);
        }
        else if(life == 1) {
            life3.setVisibility(View.INVISIBLE);
            life2.setVisibility(View.INVISIBLE);
        }

        bg.setBackgroundResource(R.drawable.background);
    }

    @Override
    protected void onStop(){
        super.onStop();

        UpButtonPlayerBottom.setBackgroundResource(0);
        UpButtonPlayerUp.setBackgroundResource(0);
        btn1.setBackgroundResource(0);
        btn2.setBackgroundResource(0);
        btn3.setBackgroundResource(0);
        btn4.setBackgroundResource(0);
        btn5.setBackgroundResource(0);
        btn6.setBackgroundResource(0);
        btn7.setBackgroundResource(0);
        btn8.setBackgroundResource(0);
        btn9.setBackgroundResource(0);
        btn10.setBackgroundResource(0);
        btn11.setBackgroundResource(0);
        btn12.setBackgroundResource(0);
        btn13.setBackgroundResource(0);
        btn14.setBackgroundResource(0);
        btn15.setBackgroundResource(0);
        btn16.setBackgroundResource(0);
        btn17.setBackgroundResource(0);
        btn18.setBackgroundResource(0);
        btn19.setBackgroundResource(0);
        btn20.setBackgroundResource(0);
        btn21.setBackgroundResource(0);
        btn22.setBackgroundResource(0);
        btn23.setBackgroundResource(0);
        btn24.setBackgroundResource(0);
        btn25.setBackgroundResource(0);
        btn26.setBackgroundResource(0);
        btn27.setBackgroundResource(0);
        btn28.setBackgroundResource(0);
        btn29.setBackgroundResource(0);
        btn30.setBackgroundResource(0);
        btn31.setBackgroundResource(0);
        btn32.setBackgroundResource(0);
        btn33.setBackgroundResource(0);
        btn34.setBackgroundResource(0);
        btn35.setBackgroundResource(0);
        btn36.setBackgroundResource(0);

        life3.setImageResource(0);
        life2.setImageResource(0);
        life1.setImageResource(0);

        bg.setBackgroundResource(0);
    }


    private void startPause() {
        if (isApplicationSentToBackground(getApplicationContext())) {
            // Do what you want to do on detecting Home Key being Pressed
            cdt.pause();
            Intent pause = new Intent(this, Pause.class);
            pause.putExtra(Pause.UPDIGIT, UP);
            pause.putExtra(Pause.SCORE, score);
            pause.putExtra(Pause.CURRENT, current);
            pause.putExtra(Pause.ONETWOTHREE_PAUSED, true);
            pause.putExtra(Pause.CALLEE, 6);
        //    this.startActivity(pause); //hereeee

        }
    }


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
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }

        if (isApplicationSentToBackground(this)) {
            // Do what you want to do on detecting Home Key being Pressed
            cdt.pause();
            Intent i = new Intent(this, Pause.class);
            i.putExtra(Pause.UPDIGIT, UP);
            i.putExtra(Pause.SCORE, score);
            i.putExtra(Pause.CURRENT, current);
            i.putExtra(Pause.TIME, cdt.timeLeft());
            i.putExtra(Pause.CALLEE, 6);
         //   this.startActivity(i);
        }

    }

    @Override
    public void onBackPressed() {
        cdt.pause();

        TextView t;
        t = (TextView) findViewById(R.id.btn1);
        editor.putString("btn1", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn2);
        editor.putString("btn2", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn3);
        editor.putString("btn3", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn4);
        editor.putString("btn4", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn5);
        editor.putString("btn5", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn6);
        editor.putString("btn6", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn7);
        editor.putString("btn7", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn8);
        editor.putString("btn8", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn9);
        editor.putString("btn9", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn10);
        editor.putString("btn10", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn11);
        editor.putString("btn11", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn12);
        editor.putString("btn12", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn13);
        editor.putString("btn13", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn14);
        editor.putString("btn14", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn15);
        editor.putString("btn15", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn16);
        editor.putString("btn16", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn17);
        editor.putString("btn17", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn18);
        editor.putString("btn18", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn19);
        editor.putString("btn19", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn20);
        editor.putString("btn20", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn21);
        editor.putString("btn21", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn22);
        editor.putString("btn22", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn23);
        editor.putString("btn23", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn24);
        editor.putString("btn24", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn25);
        editor.putString("btn25", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn26);
        editor.putString("btn26", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn27);
        editor.putString("btn27", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn28);
        editor.putString("btn28", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn29);
        editor.putString("btn29", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn30);
        editor.putString("btn30", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn31);
        editor.putString("btn31", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn32);
        editor.putString("btn32", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn33);
        editor.putString("btn33", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn34);
        editor.putString("btn34", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn35);
        editor.putString("btn35", t.getText().toString()).commit();
        t = (TextView) findViewById(R.id.btn36);
        editor.putString("btn36", t.getText().toString()).commit();
        editor.putInt("UPdigit", UP).commit();
        editor.putInt("score", score).commit();
        editor.putInt("bestScore", Math.max(score, gameDataTeamUp.getInt("bestScore", -1))).commit();
        editor.putInt("life", life).commit();
        editor.putInt("current", current).commit();
        editor.putInt("lastGeneratedNum", lastGeneratedNum).commit();
        editor.putLong("timeLeft", cdt.timeLeft()).commit();
        fillArrContent = "";
        for (Integer i : fillArr) {
            fillArrContent += i + ",";
        }
        editor.putString("FILLARR_CONTENT", fillArrContent).commit();
        Intent i = new Intent(this, Pause.class);
        i.putExtra(Pause.UPDIGIT, UP);
        i.putExtra(Pause.TIME, cdt.timeLeft());
        i.putExtra(Pause.SCORE, score);
        i.putExtra(Pause.CURRENT, current);
        i.putExtra(Pause.CURRENT, current);
        i.putExtra(Pause.CALLEE, 6);
        this.startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);

        cdt.resume();

        Button pause = (Button) findViewById(R.id.pause_btn);
        pause.setBackgroundResource(R.drawable.pause_btn_state);
        pause.setOnClickListener(this);

        life3 = (ImageView) findViewById(R.id.life3);
        life3.setImageResource(R.drawable.life3);
        life2 = (ImageView) findViewById(R.id.life2);
        life2.setImageResource(R.drawable.life2);
        life1 = (ImageView) findViewById(R.id.life1);
        life1.setImageResource(R.drawable.life1);

        UpButtonPlayerUp.setOnClickListener(this);
        UpButtonPlayerBottom.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn1.setOnTouchListener(this);
        btn2.setOnClickListener(this);
        btn2.setOnTouchListener(this);
        btn3.setOnClickListener(this);
        btn3.setOnTouchListener(this);
        btn4.setOnClickListener(this);
        btn4.setOnTouchListener(this);
        btn5.setOnClickListener(this);
        btn5.setOnTouchListener(this);
        btn6.setOnClickListener(this);
        btn6.setOnTouchListener(this);
        btn7.setOnClickListener(this);
        btn7.setOnTouchListener(this);
        btn8.setOnClickListener(this);
        btn8.setOnTouchListener(this);
        btn9.setOnClickListener(this);
        btn9.setOnTouchListener(this);
        btn10.setOnClickListener(this);
        btn10.setOnTouchListener(this);
        btn11.setOnClickListener(this);
        btn11.setOnTouchListener(this);
        btn12.setOnClickListener(this);
        btn12.setOnTouchListener(this);
        btn13.setOnClickListener(this);
        btn13.setOnTouchListener(this);
        btn14.setOnClickListener(this);
        btn14.setOnTouchListener(this);
        btn15.setOnClickListener(this);
        btn15.setOnTouchListener(this);
        btn16.setOnClickListener(this);
        btn16.setOnTouchListener(this);
        btn17.setOnClickListener(this);
        btn17.setOnTouchListener(this);
        btn18.setOnClickListener(this);
        btn18.setOnTouchListener(this);
        btn19.setOnClickListener(this);
        btn19.setOnTouchListener(this);
        btn20.setOnClickListener(this);
        btn20.setOnTouchListener(this);
        btn21.setOnClickListener(this);
        btn21.setOnTouchListener(this);
        btn22.setOnClickListener(this);
        btn22.setOnTouchListener(this);
        btn23.setOnClickListener(this);
        btn23.setOnTouchListener(this);
        btn24.setOnClickListener(this);
        btn24.setOnTouchListener(this);
        btn25.setOnClickListener(this);
        btn25.setOnTouchListener(this);
        btn26.setOnClickListener(this);
        btn26.setOnTouchListener(this);
        btn27.setOnClickListener(this);
        btn27.setOnTouchListener(this);
        btn28.setOnClickListener(this);
        btn28.setOnTouchListener(this);
        btn29.setOnClickListener(this);
        btn29.setOnTouchListener(this);
        btn30.setOnClickListener(this);
        btn30.setOnTouchListener(this);
        btn31.setOnClickListener(this);
        btn31.setOnTouchListener(this);
        btn32.setOnClickListener(this);
        btn32.setOnTouchListener(this);
        btn33.setOnClickListener(this);
        btn33.setOnTouchListener(this);
        btn34.setOnClickListener(this);
        btn34.setOnTouchListener(this);
        btn35.setOnClickListener(this);
        btn35.setOnTouchListener(this);
        btn36.setOnClickListener(this);
        btn36.setOnTouchListener(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        cdt.pause();
        TextView t;
        t = (TextView) findViewById(R.id.btn1);
        outState.putCharSequence("btn1", t.getText());
        t = (TextView) findViewById(R.id.btn2);
        outState.putCharSequence("btn2", t.getText());
        t = (TextView) findViewById(R.id.btn3);
        outState.putCharSequence("btn3", t.getText());
        t = (TextView) findViewById(R.id.btn4);
        outState.putCharSequence("btn4", t.getText());
        t = (TextView) findViewById(R.id.btn5);
        outState.putCharSequence("btn5", t.getText());
        t = (TextView) findViewById(R.id.btn6);
        outState.putCharSequence("btn6", t.getText());
        t = (TextView) findViewById(R.id.btn7);
        outState.putCharSequence("btn7", t.getText());
        t = (TextView) findViewById(R.id.btn8);
        outState.putCharSequence("btn8", t.getText());
        t = (TextView) findViewById(R.id.btn9);
        outState.putCharSequence("btn9", t.getText());
        t = (TextView) findViewById(R.id.btn10);
        outState.putCharSequence("btn10", t.getText());
        t = (TextView) findViewById(R.id.btn11);
        outState.putCharSequence("btn11", t.getText());
        t = (TextView) findViewById(R.id.btn12);
        outState.putCharSequence("btn12", t.getText().toString());
        t = (TextView) findViewById(R.id.btn13);
        outState.putCharSequence("btn13", t.getText().toString());
        t = (TextView) findViewById(R.id.btn14);
        outState.putCharSequence("btn14", t.getText());
        t = (TextView) findViewById(R.id.btn15);
        outState.putCharSequence("btn15", t.getText());
        t = (TextView) findViewById(R.id.btn16);
        outState.putCharSequence("btn16", t.getText());
        t = (TextView) findViewById(R.id.btn17);
        outState.putCharSequence("btn17", t.getText());
        t = (TextView) findViewById(R.id.btn18);
        outState.putCharSequence("btn18", t.getText());
        t = (TextView) findViewById(R.id.btn19);
        outState.putCharSequence("btn19", t.getText());
        t = (TextView) findViewById(R.id.btn20);
        outState.putCharSequence("btn20", t.getText());
        t = (TextView) findViewById(R.id.btn21);
        outState.putCharSequence("btn21", t.getText());
        t = (TextView) findViewById(R.id.btn22);
        outState.putCharSequence("btn22", t.getText());
        t = (TextView) findViewById(R.id.btn23);
        outState.putCharSequence("btn23", t.getText());
        t = (TextView) findViewById(R.id.btn24);
        outState.putCharSequence("btn24", t.getText());
        t = (TextView) findViewById(R.id.btn25);
        outState.putCharSequence("btn25", t.getText());
        t = (TextView) findViewById(R.id.btn26);
        outState.putCharSequence("btn26", t.getText());
        t = (TextView) findViewById(R.id.btn27);
        outState.putCharSequence("btn27", t.getText());
        t = (TextView) findViewById(R.id.btn28);
        outState.putCharSequence("btn28", t.getText());
        t = (TextView) findViewById(R.id.btn29);
        outState.putCharSequence("btn29", t.getText());
        t = (TextView) findViewById(R.id.btn30);
        outState.putCharSequence("btn30", t.getText());
        t = (TextView) findViewById(R.id.btn31);
        outState.putCharSequence("btn31", t.getText());
        t = (TextView) findViewById(R.id.btn32);
        outState.putCharSequence("btn32", t.getText());
        t = (TextView) findViewById(R.id.btn33);
        outState.putCharSequence("btn33", t.getText());
        t = (TextView) findViewById(R.id.btn34);
        outState.putCharSequence("btn34", t.getText());
        t = (TextView) findViewById(R.id.btn35);
        outState.putCharSequence("btn35", t.getText());
        t = (TextView) findViewById(R.id.btn36);
        outState.putCharSequence("btn36", t.getText());

        outState.putIntegerArrayList("fillArr", fillArr);
        outState.putInt("UP", UP);
        outState.putInt("pressedNum", pressedNum);
        outState.putInt("score", score);
        outState.putInt("life", life);
        outState.putInt("current", current);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView t;
        t = (TextView) findViewById(R.id.btn1);
        t.setText(savedInstanceState.getCharSequence("btn1"));
        t = (TextView) findViewById(R.id.btn2);
        t.setText(savedInstanceState.getCharSequence("btn2"));
        t = (TextView) findViewById(R.id.btn3);
        t.setText(savedInstanceState.getCharSequence("btn3"));
        t = (TextView) findViewById(R.id.btn4);
        t.setText(savedInstanceState.getCharSequence("btn4"));
        t = (TextView) findViewById(R.id.btn5);
        t.setText(savedInstanceState.getCharSequence("btn5"));
        t = (TextView) findViewById(R.id.btn6);
        t.setText(savedInstanceState.getCharSequence("btn6"));
        t = (TextView) findViewById(R.id.btn7);
        t.setText(savedInstanceState.getCharSequence("btn7"));
        t = (TextView) findViewById(R.id.btn8);
        t.setText(savedInstanceState.getCharSequence("btn8"));
        t = (TextView) findViewById(R.id.btn9);
        t.setText(savedInstanceState.getCharSequence("btn9"));
        t = (TextView) findViewById(R.id.btn10);
        t.setText(savedInstanceState.getCharSequence("btn10"));
        t = (TextView) findViewById(R.id.btn11);
        t.setText(savedInstanceState.getCharSequence("btn11"));
        t = (TextView) findViewById(R.id.btn12);
        t.setText(savedInstanceState.getCharSequence("btn12"));
        t = (TextView) findViewById(R.id.btn13);
        t.setText(savedInstanceState.getCharSequence("btn13"));
        t = (TextView) findViewById(R.id.btn14);
        t.setText(savedInstanceState.getCharSequence("btn14"));
        t = (TextView) findViewById(R.id.btn15);
        t.setText(savedInstanceState.getCharSequence("btn15"));
        t = (TextView) findViewById(R.id.btn16);
        t.setText(savedInstanceState.getCharSequence("btn16"));
        t = (TextView) findViewById(R.id.btn17);
        t.setText(savedInstanceState.getCharSequence("btn17"));
        t = (TextView) findViewById(R.id.btn18);
        t.setText(savedInstanceState.getCharSequence("btn18"));
        t = (TextView) findViewById(R.id.btn19);
        t.setText(savedInstanceState.getCharSequence("btn19"));
        t = (TextView) findViewById(R.id.btn20);
        t.setText(savedInstanceState.getCharSequence("btn20"));
        t = (TextView) findViewById(R.id.btn21);
        t.setText(savedInstanceState.getCharSequence("btn21"));
        t = (TextView) findViewById(R.id.btn22);
        t.setText(savedInstanceState.getCharSequence("btn22"));
        t = (TextView) findViewById(R.id.btn23);
        t.setText(savedInstanceState.getCharSequence("btn23"));
        t = (TextView) findViewById(R.id.btn24);
        t.setText(savedInstanceState.getCharSequence("btn24"));
        t = (TextView) findViewById(R.id.btn25);
        t.setText(savedInstanceState.getCharSequence("btn25"));
        t = (TextView) findViewById(R.id.btn26);
        t.setText(savedInstanceState.getCharSequence("btn26"));
        t = (TextView) findViewById(R.id.btn27);
        t.setText(savedInstanceState.getCharSequence("btn27"));
        t = (TextView) findViewById(R.id.btn28);
        t.setText(savedInstanceState.getCharSequence("btn28"));
        t = (TextView) findViewById(R.id.btn29);
        t.setText(savedInstanceState.getCharSequence("btn29"));
        t = (TextView) findViewById(R.id.btn30);
        t.setText(savedInstanceState.getCharSequence("btn30"));
        t = (TextView) findViewById(R.id.btn31);
        t.setText(savedInstanceState.getCharSequence("btn31"));
        t = (TextView) findViewById(R.id.btn32);
        t.setText(savedInstanceState.getCharSequence("btn32"));
        t = (TextView) findViewById(R.id.btn33);
        t.setText(savedInstanceState.getCharSequence("btn33"));
        t = (TextView) findViewById(R.id.btn34);
        t.setText(savedInstanceState.getCharSequence("btn34"));
        t = (TextView) findViewById(R.id.btn35);
        t.setText(savedInstanceState.getCharSequence("btn35"));
        t = (TextView) findViewById(R.id.btn36);
        t.setText(savedInstanceState.getCharSequence("btn36"));

        fillArr = savedInstanceState.getIntegerArrayList("fillArr");
        UP = savedInstanceState.getInt("UP");
        //Log.d("checkstate","UP restored as "+UP);
        pressedNum = savedInstanceState.getInt("pressedNum");
        //Log.d("checkstate","pressedNum restored as "+pressedNum);
        score = savedInstanceState.getInt("score");
        //Log.d("checkstate","score restored as "+score);
        life = savedInstanceState.getInt("life");
        //Log.d("checkstate","life restored as "+life);
        current = savedInstanceState.getInt("current");
        //Log.d("checkstate","current restored as "+current);

    }

    private void playSound(int soundId) {
        mSoundPoolHelper.play(soundId);
    }

    public void Game() {

        boolean isUp = checkUp();
        boolean rightUpButton;
        /*
        for TeamUp mode:
        correct situations are:
        1)press number in order
        2)press the UP button on the correct side
         */
        if (isUp) {   //UP num
            rightUpButton = checkRightUpButton();
            if (rightUpButton) {  //Correct

                //Background animation
                bg.setBackgroundResource(R.drawable.up_animation);
                AnimationDrawable anim = (AnimationDrawable) bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                if(upButtonSide.equals("up")){
                    //Up bear animation
                    UpButtonPlayerUp.setBackgroundResource(R.drawable.up_bear);
                    AnimationDrawable anim2 = (AnimationDrawable)UpButtonPlayerUp.getBackground();

                    if (anim2.isRunning()) {
                        anim2.stop();
                    }
                    anim2.start();
                }
                else{
                    //Up bear animation
                    UpButtonPlayerBottom.setBackgroundResource(R.drawable.up_bear);
                    AnimationDrawable anim3 = (AnimationDrawable)UpButtonPlayerBottom.getBackground();

                    if (anim3.isRunning()) {
                        anim3.stop();
                    }
                    anim3.start();
                }


                //Sound effect
                if (playMusic) {
                    //            up_sound.start();
                    playSound(upId);
                }

                if (vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(200);
                }

                score += current;
                findCurrentAndUpdate();
                currentScoreUp.setText(score + "");
                currentScoreBottom.setText(score + "");

                current++;
                currentNumTextUp.setText(current + "");
                currentNumTextBottom.setText(current + "");

            } else {//Wrong
                //Background effect
                bg.setBackgroundResource(R.drawable.bomb);
                AnimationDrawable anim = (AnimationDrawable) bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                //Sound effect
                if (playMusic) {
                    //            explode_sound.start();
                    playSound(explodeId);
                }

                if (vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(1000);
                }

                life--;
                if (life == 2) {
                    life3.setVisibility(View.INVISIBLE);
                }
                else if(life == 1){
                    life3.setVisibility(View.INVISIBLE);
                    life2.setVisibility(View.INVISIBLE);
                }

                else if (life <= 0) {
                    life3.setVisibility(View.INVISIBLE);
                    life2.setVisibility(View.INVISIBLE);
                    life1.setVisibility(View.INVISIBLE);

                    cdt.cancel();
                    editor.putInt("bestScore", Math.max(score, gameDataTeamUp.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(2);
                    return;
                }
                //Shake screen
            //    pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                if (life > 1) {
                    final Toast toast1 = Toast.makeText(com.example.btw.whatsup.MainTeamUp.this, "You have " + life + " more lives!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast1.show();
                        }

                        public void onFinish() {
                            toast1.cancel();
                        }
                    }.start();
                } else {
                    final Toast toast2 = Toast.makeText(com.example.btw.whatsup.MainTeamUp.this, "You have " + life + " more life!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast2.show();
                        }

                        public void onFinish() {
                            toast2.cancel();
                        }
                    }.start();
                }

            }
        } else {   //Not UP num
            if (pressedNum == current) {      //Correct
                //Spark animation
                AnimationDrawable anim = (AnimationDrawable) pressedButton.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                //Sound effect
                if (playMusic) {
                    //          tap_sound.start();
                    playSound(tapId);
                }

                if (vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(200);
                }
                score += pressedNum;
                changeNo(pressedButton);
                currentScoreUp.setText(score + "");
                currentScoreBottom.setText(score + "");

                current++;
                currentNumTextUp.setText(current + "");
                currentNumTextBottom.setText(current + "");

            } else {                            //Wrong
                //Background effect
                bg.setBackgroundResource(R.drawable.bomb);
                AnimationDrawable anim = (AnimationDrawable) bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                //Sound effect
                if (playMusic) {
                    //             explode_sound.start();
                    playSound(explodeId);
                }

                if (vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(1000);
                }

                life--;
                if (life == 2) {
                    life3.setVisibility(View.INVISIBLE);
                }
                else if(life == 1){
                    life3.setVisibility(View.INVISIBLE);
                    life2.setVisibility(View.INVISIBLE);
                }

                else if (life <= 0) {
                    life3.setVisibility(View.INVISIBLE);
                    life2.setVisibility(View.INVISIBLE);
                    life1.setVisibility(View.INVISIBLE);
                    cdt.cancel();
                    editor.putInt("bestScore", Math.max(score, gameDataTeamUp.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(3);
                    return;
                }
                //Shake screen
             //  pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                if (life > 1) {
                    final Toast toast1 = Toast.makeText(com.example.btw.whatsup.MainTeamUp.this, "You have " + life + " more lives!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast1.show();
                        }

                        public void onFinish() {
                            toast1.cancel();
                        }
                    }.start();
                } else {
                    final Toast toast2 = Toast.makeText(com.example.btw.whatsup.MainTeamUp.this, "You have " + life + " more life!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast2.show();
                        }

                        public void onFinish() {
                            toast2.cancel();
                        }
                    }.start();
                }
            }
        }
    }

    private boolean checkUp() {
        return (current % UP == 0) || checkDigit();
    }

    private boolean checkRightUpButton() {
        Button upNumber = findCurrent();
        if (pressedButton == UpButtonPlayerUp) {
            if (upNumber == btn1 ||
                    upNumber == btn2 ||
                    upNumber == btn3 ||
                    upNumber == btn4 ||
                    upNumber == btn5 ||
                    upNumber == btn6 ||
                    upNumber == btn7 ||
                    upNumber == btn8 ||
                    upNumber == btn9 ||
                    upNumber == btn10 ||
                    upNumber == btn11 ||
                    upNumber == btn12 ||
                    upNumber == btn13 ||
                    upNumber == btn14 ||
                    upNumber == btn15 ||
                    upNumber == btn16 ||
                    upNumber == btn17 ||
                    upNumber == btn18) {
                upButtonSide = "up";
                return true;
            } else {
                return false;
            }
        } else if (pressedButton == UpButtonPlayerBottom) {
            if (
                            upNumber == btn19 ||
                            upNumber == btn20 ||
                            upNumber == btn21 ||
                            upNumber == btn22 ||
                            upNumber == btn23 ||
                            upNumber == btn24 ||
                            upNumber == btn25 ||
                            upNumber == btn26 ||
                            upNumber == btn27 ||
                            upNumber == btn28 ||
                            upNumber == btn29 ||
                            upNumber == btn30 ||
                            upNumber == btn31 ||
                            upNumber == btn32 ||
                            upNumber == btn33 ||
                            upNumber == btn34 ||
                            upNumber == btn35 ||
                            upNumber == btn36
                    ) {
                upButtonSide = "bottom";
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean checkDigit() {
        String cur = Integer.toString(current);
        String upString = Integer.toString(UP);

        return cur.contains(upString);
    }

    //Brute force
    //Finds and change the digit for UP num
    public void findCurrentAndUpdate() {
        if (Integer.parseInt(btn1.getText().toString()) == current) {
            changeNo(btn1);
            return;
        }
        if (Integer.parseInt(btn2.getText().toString()) == current) {
            changeNo(btn2);
            return;
        }
        if (Integer.parseInt(btn3.getText().toString()) == current) {
            changeNo(btn3);
            return;
        }
        if (Integer.parseInt(btn4.getText().toString()) == current) {
            changeNo(btn4);
            return;
        }
        if (Integer.parseInt(btn5.getText().toString()) == current) {
            changeNo(btn5);
            return;
        }
        if (Integer.parseInt(btn6.getText().toString()) == current) {
            changeNo(btn6);
            return;
        }
        if (Integer.parseInt(btn7.getText().toString()) == current) {
            changeNo(btn7);
            return;
        }
        if (Integer.parseInt(btn8.getText().toString()) == current) {
            changeNo(btn8);
            return;
        }
        if (Integer.parseInt(btn9.getText().toString()) == current) {
            changeNo(btn9);
            return;
        }
        if (Integer.parseInt(btn10.getText().toString()) == current) {
            changeNo(btn10);
            return;
        }
        if (Integer.parseInt(btn11.getText().toString()) == current) {
            changeNo(btn11);
            return;
        }
        if (Integer.parseInt(btn12.getText().toString()) == current) {
            changeNo(btn12);
            return;
        }
        if (Integer.parseInt(btn13.getText().toString()) == current) {
            changeNo(btn13);
            return;
        }
        if (Integer.parseInt(btn14.getText().toString()) == current) {
            changeNo(btn14);
            return;
        }
        if (Integer.parseInt(btn15.getText().toString()) == current) {
            changeNo(btn15);
            return;
        }
        if (Integer.parseInt(btn16.getText().toString()) == current) {
            changeNo(btn16);
            return;
        }
        if (Integer.parseInt(btn17.getText().toString()) == current) {
            changeNo(btn17);
            return;
        }
        if (Integer.parseInt(btn18.getText().toString()) == current) {
            changeNo(btn18);
            return;
        }
        if (Integer.parseInt(btn19.getText().toString()) == current) {
            changeNo(btn19);
            return;
        }
        if (Integer.parseInt(btn20.getText().toString()) == current) {
            changeNo(btn20);
            return;
        }
        if (Integer.parseInt(btn21.getText().toString()) == current) {
            changeNo(btn21);
            return;
        }
        if (Integer.parseInt(btn22.getText().toString()) == current) {
            changeNo(btn22);
            return;
        }
        if (Integer.parseInt(btn23.getText().toString()) == current) {
            changeNo(btn23);
            return;
        }
        if (Integer.parseInt(btn24.getText().toString()) == current) {
            changeNo(btn24);
            return;
        }
        if (Integer.parseInt(btn25.getText().toString()) == current) {
            changeNo(btn25);
            return;
        }
        if (Integer.parseInt(btn26.getText().toString()) == current) {
            changeNo(btn26);
            return;
        }
        if (Integer.parseInt(btn27.getText().toString()) == current) {
            changeNo(btn27);
            return;
        }
        if (Integer.parseInt(btn28.getText().toString()) == current) {
            changeNo(btn28);
            return;
        }
        if (Integer.parseInt(btn29.getText().toString()) == current) {
            changeNo(btn29);
            return;
        }
        if (Integer.parseInt(btn30.getText().toString()) == current) {
            changeNo(btn30);
            return;
        }
        if (Integer.parseInt(btn31.getText().toString()) == current) {
            changeNo(btn31);
            return;
        }
        if (Integer.parseInt(btn32.getText().toString()) == current) {
            changeNo(btn32);
            return;
        }
        if (Integer.parseInt(btn33.getText().toString()) == current) {
            changeNo(btn33);
            return;
        }
        if (Integer.parseInt(btn34.getText().toString()) == current) {
            changeNo(btn34);
            return;
        }
        if (Integer.parseInt(btn35.getText().toString()) == current) {
            changeNo(btn35);
            return;
        }
        if (Integer.parseInt(btn36.getText().toString()) == current) {
            changeNo(btn36);
            return;
        }
    }

    public Button findCurrent() {
        if (Integer.parseInt(btn1.getText().toString()) == current) {
            return btn1;
        }
        if (Integer.parseInt(btn2.getText().toString()) == current) {
            return btn2;
        }
        if (Integer.parseInt(btn3.getText().toString()) == current) {
            return btn3;
        }
        if (Integer.parseInt(btn4.getText().toString()) == current) {
            return btn4;
        }
        if (Integer.parseInt(btn5.getText().toString()) == current) {
            return btn5;
        }
        if (Integer.parseInt(btn6.getText().toString()) == current) {
            return btn6;
        }
        if (Integer.parseInt(btn7.getText().toString()) == current) {
            return btn7;
        }
        if (Integer.parseInt(btn8.getText().toString()) == current) {
            return btn8;
        }
        if (Integer.parseInt(btn9.getText().toString()) == current) {
            return btn9;
        }
        if (Integer.parseInt(btn10.getText().toString()) == current) {
            return btn10;
        }
        if (Integer.parseInt(btn11.getText().toString()) == current) {
            return btn11;
        }
        if (Integer.parseInt(btn12.getText().toString()) == current) {
            return btn12;
        }
        if (Integer.parseInt(btn13.getText().toString()) == current) {
            return btn13;
        }
        if (Integer.parseInt(btn14.getText().toString()) == current) {
            return btn14;
        }
        if (Integer.parseInt(btn15.getText().toString()) == current) {
            return btn15;
        }
        if (Integer.parseInt(btn16.getText().toString()) == current) {
            return btn16;
        }
        if (Integer.parseInt(btn17.getText().toString()) == current) {
            return btn17;
        }
        if (Integer.parseInt(btn18.getText().toString()) == current) {
            return btn18;
        }
        if (Integer.parseInt(btn19.getText().toString()) == current) {
            return btn19;
        }
        if (Integer.parseInt(btn20.getText().toString()) == current) {
            return btn20;
        }
        if (Integer.parseInt(btn21.getText().toString()) == current) {
            return btn21;
        }
        if (Integer.parseInt(btn22.getText().toString()) == current) {
            return btn22;
        }
        if (Integer.parseInt(btn23.getText().toString()) == current) {
            return btn23;
        }
        if (Integer.parseInt(btn24.getText().toString()) == current) {
            return btn24;
        }
        if (Integer.parseInt(btn25.getText().toString()) == current) {
            return btn25;
        }
        if (Integer.parseInt(btn26.getText().toString()) == current) {
            return btn26;
        }
        if (Integer.parseInt(btn27.getText().toString()) == current) {
            return btn27;
        }
        if (Integer.parseInt(btn28.getText().toString()) == current) {
            return btn28;
        }
        if (Integer.parseInt(btn29.getText().toString()) == current) {
            return btn29;
        }
        if (Integer.parseInt(btn30.getText().toString()) == current) {
            return btn30;
        }
        if (Integer.parseInt(btn31.getText().toString()) == current) {
            return btn31;
        }
        if (Integer.parseInt(btn32.getText().toString()) == current) {
            return btn32;
        }
        if (Integer.parseInt(btn33.getText().toString()) == current) {
            return btn33;
        }
        if (Integer.parseInt(btn34.getText().toString()) == current) {
            return btn34;
        }
        if (Integer.parseInt(btn35.getText().toString()) == current) {
            return btn35;
        } else if (Integer.parseInt(btn36.getText().toString()) == current) {
            return btn36;
        }
        return null;
    }

    public void GameOver(int r) {
        Intent i = new Intent(this, GameOver.class);
        i.putExtra(GameOver.REASON, r);
        i.putExtra(GameOver.CALLEE, 6);
        this.startActivity(i);
    }


    protected void populateArr() {
        if (fillArr.isEmpty()) {
            for (int i = 0; i <= 9; i++) {
                fillArr.add(++lastGeneratedNum);
            }
            Collections.shuffle(fillArr);
        }
    }

    protected void changeNo(Button btn) {
        if (fillArr.isEmpty()) {
            for (int i = 0; i <= 9; i++) {
                fillArr.add(++lastGeneratedNum);
            }
            Collections.shuffle(fillArr);
        }
        btn.setText(fillArr.remove(0) + "");
    }


    //fill grid with numbers from start to start+24 in random order
    protected void initialiseGrid(int start) {
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = start; i < start + 36; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);

        btn1.setText(arr.remove(0) + "");
        btn2.setText(arr.remove(0) + "");
        btn3.setText(arr.remove(0) + "");
        btn4.setText(arr.remove(0) + "");
        btn5.setText(arr.remove(0) + "");
        btn6.setText(arr.remove(0) + "");
        btn7.setText(arr.remove(0) + "");
        btn8.setText(arr.remove(0) + "");
        btn9.setText(arr.remove(0) + "");
        btn10.setText(arr.remove(0) + "");
        btn11.setText(arr.remove(0) + "");
        btn12.setText(arr.remove(0) + "");
        btn13.setText(arr.remove(0) + "");
        btn14.setText(arr.remove(0) + "");
        btn15.setText(arr.remove(0) + "");
        btn16.setText(arr.remove(0) + "");
        btn17.setText(arr.remove(0) + "");
        btn18.setText(arr.remove(0) + "");
        btn19.setText(arr.remove(0) + "");
        btn20.setText(arr.remove(0) + "");
        btn21.setText(arr.remove(0) + "");
        btn22.setText(arr.remove(0) + "");
        btn23.setText(arr.remove(0) + "");
        btn24.setText(arr.remove(0) + "");
        btn25.setText(arr.remove(0) + "");
        btn26.setText(arr.remove(0) + "");
        btn27.setText(arr.remove(0) + "");
        btn28.setText(arr.remove(0) + "");
        btn29.setText(arr.remove(0) + "");
        btn30.setText(arr.remove(0) + "");
        btn31.setText(arr.remove(0) + "");
        btn32.setText(arr.remove(0) + "");
        btn33.setText(arr.remove(0) + "");
        btn34.setText(arr.remove(0) + "");
        btn35.setText(arr.remove(0) + "");
        btn36.setText(arr.remove(0) + "");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (view.getId()) {
            case R.id.btn1:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn1.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
            case R.id.btn2:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn2.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn3:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn3.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn4:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn4.setBackgroundResource(R.drawable.blue_btn_pressed);
                break;
            case R.id.btn5:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn5.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn6:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn6.setBackgroundResource(R.drawable.purple_btn_pressed);
                break;
            case R.id.btn7:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn7.setBackgroundResource(R.drawable.purple_btn_pressed);
                break;
            case R.id.btn8:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn8.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
            case R.id.btn9:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn9.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn10:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn10.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn11:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn11.setBackgroundResource(R.drawable.blue_btn_pressed);
                break;
            case R.id.btn12:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn12.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn13:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn13.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn14:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn14.setBackgroundResource(R.drawable.purple_btn_pressed);
                break;
            case R.id.btn15:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn15.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
            case R.id.btn16:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn16.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn17:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn17.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn18:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn18.setBackgroundResource(R.drawable.blue_btn_pressed);
                break;
            case R.id.btn19:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn19.setBackgroundResource(R.drawable.blue_btn_pressed);
                break;
            case R.id.btn20:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn20.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn21:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn21.setBackgroundResource(R.drawable.purple_btn_pressed);
                break;
            case R.id.btn22:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn22.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
            case R.id.btn23:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn23.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn24:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn24.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn25:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn25.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn26:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn26.setBackgroundResource(R.drawable.blue_btn_pressed);
                break;
            case R.id.btn27:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn27.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn28:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn28.setBackgroundResource(R.drawable.purple_btn_pressed);
                break;
            case R.id.btn29:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn29.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
            case R.id.btn30:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn30.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn31:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn31.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn32:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn32.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn33:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn33.setBackgroundResource(R.drawable.blue_btn_pressed);
                break;
            case R.id.btn34:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn34.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn35:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn35.setBackgroundResource(R.drawable.purple_btn_pressed);
                break;
            case R.id.btn36:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn36.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
        }
        return false;
    }

    public void onClick(View v) {

        Button temp;
        switch (v.getId()) {
            case R.id.pause_btn:
                cdt.pause();

                TextView t;
                t = (TextView) findViewById(R.id.btn1);
                editor.putString("btn1", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn2);
                editor.putString("btn2", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn3);
                editor.putString("btn3", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn4);
                editor.putString("btn4", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn5);
                editor.putString("btn5", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn6);
                editor.putString("btn6", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn7);
                editor.putString("btn7", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn8);
                editor.putString("btn8", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn9);
                editor.putString("btn9", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn10);
                editor.putString("btn10", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn11);
                editor.putString("btn11", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn12);
                editor.putString("btn12", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn13);
                editor.putString("btn13", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn14);
                editor.putString("btn14", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn15);
                editor.putString("btn15", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn16);
                editor.putString("btn16", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn17);
                editor.putString("btn17", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn18);
                editor.putString("btn18", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn19);
                editor.putString("btn19", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn20);
                editor.putString("btn20", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn21);
                editor.putString("btn21", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn22);
                editor.putString("btn22", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn23);
                editor.putString("btn23", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn24);
                editor.putString("btn24", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn25);
                editor.putString("btn25", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn26);
                editor.putString("btn26", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn27);
                editor.putString("btn27", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn28);
                editor.putString("btn28", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn29);
                editor.putString("btn29", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn30);
                editor.putString("btn30", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn31);
                editor.putString("btn31", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn32);
                editor.putString("btn32", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn33);
                editor.putString("btn33", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn34);
                editor.putString("btn34", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn35);
                editor.putString("btn35", t.getText().toString()).commit();
                t = (TextView) findViewById(R.id.btn36);
                editor.putString("btn36", t.getText().toString()).commit();
                editor.putInt("UPdigit", UP).commit();
                editor.putInt("score", score).commit();
                editor.putInt("bestScore", Math.max(score, gameDataTeamUp.getInt("bestScore", -1))).commit();
                editor.putInt("life", life).commit();
                editor.putInt("current", current).commit();
                editor.putInt("lastGeneratedNum", lastGeneratedNum).commit();
                editor.putLong("timeLeft", cdt.timeLeft()).commit();
                fillArrContent = "";
                for (Integer i : fillArr) {
                    fillArrContent += i + ",";
                }
                editor.putString("FILLARR_CONTENT", fillArrContent).commit();
                Intent i = new Intent(this, Pause.class);
                i.putExtra(Pause.UPDIGIT, UP);
                i.putExtra(Pause.TIME, cdt.timeLeft());
                i.putExtra(Pause.SCORE, score);
                i.putExtra(Pause.CURRENT, current);
                i.putExtra(Pause.CALLEE, 6);
                this.startActivity(i);
                break;
            case R.id.up_button_playerBottom:
                temp = (Button) v;
                pressedButton = temp;
                pressedID = temp.getId();
                Game();
                break;
            case R.id.up_button_playerUp:
                temp = (Button) v;
                pressedButton = temp;
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn1:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn2:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn3:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn4:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.blue_spark);
                Game();
                break;
            case R.id.btn5:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn6:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.purple_spark);
                Game();
                break;
            case R.id.btn7:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.purple_spark);
                Game();
                break;
            case R.id.btn8:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn9:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn10:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn11:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.blue_spark);
                Game();
                break;
            case R.id.btn12:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn13:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn14:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.purple_spark);
                Game();
                break;
            case R.id.btn15:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn16:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn17:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn18:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.blue_spark);
                Game();
                break;
            case R.id.btn19:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.blue_spark);
                Game();
                break;
            case R.id.btn20:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn21:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.purple_spark);
                Game();
                break;
            case R.id.btn22:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn23:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn24:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn25:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn26:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.blue_spark);
                Game();
                break;
            case R.id.btn27:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn28:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.purple_spark);
                Game();
                break;
            case R.id.btn29:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn30:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn31:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn32:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn33:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.blue_spark);
                Game();
                break;
            case R.id.btn34:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn35:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.purple_spark);
                Game();
                break;
            case R.id.btn36:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;

        }
    }
}

