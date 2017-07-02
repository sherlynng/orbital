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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by BTW on 5/24/2017.
 */

public class MainExtreme4 extends Activity implements OnClickListener, View.OnTouchListener {

    private boolean continueFromLast;
    protected SharedPreferences gameData;
    protected SharedPreferences.Editor editor;
    protected boolean continueMusic = true;

    public static final String UPDIGIT = "UPDIGIT";
    private int UP;
    private int secondUP;
    private boolean secondUP_start;
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
    private ImageView life1, life2, life3;

    private int[] freezeArr = new int[37]; //Does not use freezeArr[0]
    private int[] beforeFreezeArr = new int[]{8, 9, 10, 11, 14, 15, 16, 17, 20, 21, 22, 23, 26, 27, 28, 29}; //For randomising frozen grid
    private int[] afterFreezeArr1 = new int[]{8, 9, 10, 11, 14, 15, 16, 17, 20, 21, 22, 23, 26, 27, 28, 29, 2, 3, 4, 5, 32, 33, 34, 35}; //For randomising frozen grid

    private long timeLeft;
    private long changeUpTimeLeft;
    private long increasedGridTimeLeft1;
    private long increasedGridTimeLeft2;
    private long freezeTimeLeft1;
    private long freezeTimeLeft2;
    private long freezeTimeLeft3;
    private long freezeTimeLeft4;
    public CountDownTimerPausable cdt;
    public CountDownTimerPausable changeUpTimer;
    public CountDownTimerPausable increasedGridTimer1;
    public CountDownTimerPausable increasedGridTimer2;
    public CountDownTimerPausable freezeTimer1;
    public CountDownTimerPausable freezeTimer2;
    public CountDownTimerPausable freezeTimer3;
    public CountDownTimerPausable freezeTimer4;

    private LinearLayout bg;
    private boolean hasIncreasedGrid1;
    private boolean hasIncreasedGrid2;

    protected TextView currentScore;
    //   protected TextView best;
    protected TextView currentNumText;
    protected TextView upDisplayText;
    protected Button upBtn;
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

    //    private MediaPlayer explode_sound;
    //   private MediaPlayer up_sound;
    //  private MediaPlayer tap_sound;
    public static boolean playMusic;
    public static boolean vibrationOn;

    private SoundPoolHelper mSoundPoolHelper;
    private int explodeId, tapId, upId, rotateId, freezeId, unfreezeId, gridIncreaseId;

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
        setContentView(R.layout.mainextreme4);

        checkVibration(this);
        checkMusic(this);
        secondUP_start = false;
        secondUP = -1;
        hasIncreasedGrid1 = false;
        hasIncreasedGrid2 = false;

        /* First initialisation of frozen grids.
            0: Unfrozen; 1:Frozen once; 2:Frozen twice */
        for (int i = 1; i <= 36; i++) {
            freezeArr[i] = 0;
        }

        //      explode_sound = MediaPlayer.create(this, R.raw.explode);
        //     up_sound = MediaPlayer.create(this, R.raw.up);
        //      tap_sound = MediaPlayer.create(this, R.raw.tap);

        mSoundPoolHelper = new SoundPoolHelper(1, this);
        explodeId = mSoundPoolHelper.load(this, R.raw.explode, 1);
        upId = mSoundPoolHelper.load(this, R.raw.up, 1);
        tapId = mSoundPoolHelper.load(this, R.raw.tap, 1);
        rotateId = mSoundPoolHelper.load(this, R.raw.grid_rotate, 1);
        freezeId = mSoundPoolHelper.load(this, R.raw.freeze, 1);
        unfreezeId = mSoundPoolHelper.load(this, R.raw.zap, 1);
        gridIncreaseId = mSoundPoolHelper.load(this, R.raw.grid_increase, 1);

        bg = (LinearLayout) this.findViewById(R.id.main);

        upBtn = (Button) this.findViewById(R.id.up_button);
        btn1 = (Button) this.findViewById(R.id.btn1);
        btn1.setBackgroundResource(R.drawable.yellow_spark);
        btn2 = (Button) this.findViewById(R.id.btn2);
        btn2.setBackgroundResource(R.drawable.orange_spark);
        btn3 = (Button) this.findViewById(R.id.btn3);
        btn3.setBackgroundResource(R.drawable.pink_spark);
        btn4 = (Button) this.findViewById(R.id.btn4);
        btn4.setBackgroundResource(R.drawable.blue_spark);
        btn5 = (Button) this.findViewById(R.id.btn5);
        btn5.setBackgroundResource(R.drawable.green_spark);
        btn6 = (Button) this.findViewById(R.id.btn6);
        btn6.setBackgroundResource(R.drawable.purple_spark);
        btn7 = (Button) this.findViewById(R.id.btn7);
        btn7.setBackgroundResource(R.drawable.purple_spark);
        btn8 = (Button) this.findViewById(R.id.btn8);
        btn8.setBackgroundResource(R.drawable.yellow_spark);
        btn9 = (Button) this.findViewById(R.id.btn9);
        btn9.setBackgroundResource(R.drawable.orange_spark);
        btn10 = (Button) this.findViewById(R.id.btn10);
        btn10.setBackgroundResource(R.drawable.pink_spark);
        btn11 = (Button) this.findViewById(R.id.btn11);
        btn11.setBackgroundResource(R.drawable.blue_spark);
        btn12 = (Button) this.findViewById(R.id.btn12);
        btn12.setBackgroundResource(R.drawable.green_spark);
        btn13 = (Button) this.findViewById(R.id.btn13);
        btn13.setBackgroundResource(R.drawable.green_spark);
        btn14 = (Button) this.findViewById(R.id.btn14);
        btn14.setBackgroundResource(R.drawable.purple_spark);
        btn15 = (Button) this.findViewById(R.id.btn15);
        btn15.setBackgroundResource(R.drawable.yellow_spark);
        btn16 = (Button) this.findViewById(R.id.btn16);
        btn16.setBackgroundResource(R.drawable.orange_spark);
        btn17 = (Button) this.findViewById(R.id.btn17);
        btn17.setBackgroundResource(R.drawable.pink_spark);
        btn18 = (Button) this.findViewById(R.id.btn18);
        btn18.setBackgroundResource(R.drawable.blue_spark);
        btn19 = (Button) this.findViewById(R.id.btn19);
        btn19.setBackgroundResource(R.drawable.blue_spark);
        btn20 = (Button) this.findViewById(R.id.btn20);
        btn20.setBackgroundResource(R.drawable.green_spark);
        btn21 = (Button) this.findViewById(R.id.btn21);
        btn21.setBackgroundResource(R.drawable.purple_spark);
        btn22 = (Button) this.findViewById(R.id.btn22);
        btn22.setBackgroundResource(R.drawable.yellow_spark);
        btn23 = (Button) this.findViewById(R.id.btn23);
        btn23.setBackgroundResource(R.drawable.orange_spark);
        btn24 = (Button) this.findViewById(R.id.btn24);
        btn24.setBackgroundResource(R.drawable.pink_spark);
        btn25 = (Button) this.findViewById(R.id.btn25);
        btn25.setBackgroundResource(R.drawable.pink_spark);
        btn26 = (Button) this.findViewById(R.id.btn26);
        btn26.setBackgroundResource(R.drawable.blue_spark);
        btn27 = (Button) this.findViewById(R.id.btn27);
        btn27.setBackgroundResource(R.drawable.green_spark);
        btn28 = (Button) this.findViewById(R.id.btn28);
        btn28.setBackgroundResource(R.drawable.purple_spark);
        btn29 = (Button) this.findViewById(R.id.btn29);
        btn29.setBackgroundResource(R.drawable.yellow_spark);
        btn30 = (Button) this.findViewById(R.id.btn30);
        btn30.setBackgroundResource(R.drawable.orange_spark);
        btn31 = (Button) this.findViewById(R.id.btn31);
        btn31.setBackgroundResource(R.drawable.orange_spark);
        btn32 = (Button) this.findViewById(R.id.btn32);
        btn32.setBackgroundResource(R.drawable.pink_spark);
        btn33 = (Button) this.findViewById(R.id.btn33);
        btn33.setBackgroundResource(R.drawable.blue_spark);
        btn34 = (Button) this.findViewById(R.id.btn34);
        btn34.setBackgroundResource(R.drawable.green_spark);
        btn35 = (Button) this.findViewById(R.id.btn35);
        btn35.setBackgroundResource(R.drawable.purple_spark);
        btn36 = (Button) this.findViewById(R.id.btn36);
        btn36.setBackgroundResource(R.drawable.yellow_spark);

        gameData = getSharedPreferences("GameData", Context.MODE_PRIVATE);
        editor = gameData.edit();

        continueFromLast = gameData.getBoolean("CONTINUE_FROM_LAST", false);
        if (continueFromLast) {
            //   Log.d("Debug", "cotinue from last=true");
            UP = gameData.getInt("UPdigit", 1);
            score = gameData.getInt("score", 0);
            life = gameData.getInt("life", 3);
            current = gameData.getInt("current", 1);
            timeLeft = gameData.getLong("timeLeft", 120000);
            changeUpTimeLeft = gameData.getLong("changeUpTimeLeft", 60000);
            increasedGridTimeLeft1 = gameData.getLong("increasedGridtimeLeft1", 30000);
            increasedGridTimeLeft2 = gameData.getLong("increasedGridimeLeft2", 90000);
            freezeTimeLeft1 = gameData.getLong("freezetimeLeft1", 15000);
            freezeTimeLeft2 = gameData.getLong("freezetimeLeft2", 45000);
            freezeTimeLeft3 = gameData.getLong("freezetimeLeft3", 75000);
            freezeTimeLeft4 = gameData.getLong("freezetimeLeft4", 105000);
            fillArrContent = gameData.getString("FILLARR_CONTENT", "");
            String[] strArray = fillArrContent.split(",");
            fillArr.clear();
            for (int i = 0; i < strArray.length; i++) {
                fillArr.add(0, Integer.parseInt(strArray[i]));
            }
            TextView t;
            t = (TextView) findViewById(R.id.btn1);
            if (gameData.getString("btn1", "").equals("-1")) {
                btn1.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn1", ""));
            t = (TextView) findViewById(R.id.btn2);
            if (gameData.getString("btn2", "").equals("-1")) {
                btn2.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn2", ""));
            t = (TextView) findViewById(R.id.btn3);
            if (gameData.getString("btn3", "").equals("-1")) {
                btn3.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn3", ""));
            t = (TextView) findViewById(R.id.btn4);
            if (gameData.getString("btn4", "").equals("-1")) {
                btn4.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn4", ""));
            t = (TextView) findViewById(R.id.btn5);
            if (gameData.getString("btn5", "").equals("-1")) {
                btn5.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn5", ""));
            t = (TextView) findViewById(R.id.btn6);
            if (gameData.getString("btn6", "").equals("-1")) {
                btn6.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn6", ""));
            t = (TextView) findViewById(R.id.btn7);
            if (gameData.getString("btn7", "").equals("-1")) {
                btn7.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn7", ""));
            t = (TextView) findViewById(R.id.btn8);
            t.setText(gameData.getString("btn8", ""));
            t = (TextView) findViewById(R.id.btn9);
            t.setText(gameData.getString("btn9", ""));
            t = (TextView) findViewById(R.id.btn10);
            t.setText(gameData.getString("btn10", ""));
            t = (TextView) findViewById(R.id.btn11);
            t.setText(gameData.getString("btn11", ""));
            t = (TextView) findViewById(R.id.btn12);
            if (gameData.getString("btn12", "").equals("-1")) {
                btn12.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn12", ""));
            t = (TextView) findViewById(R.id.btn13);
            if (gameData.getString("btn13", "").equals("-1")) {
                btn13.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn13", ""));
            t = (TextView) findViewById(R.id.btn14);
            t.setText(gameData.getString("btn14", ""));
            t = (TextView) findViewById(R.id.btn15);
            t.setText(gameData.getString("btn15", ""));
            t = (TextView) findViewById(R.id.btn16);
            t.setText(gameData.getString("btn16", ""));
            t = (TextView) findViewById(R.id.btn17);
            t.setText(gameData.getString("btn17", ""));
            t = (TextView) findViewById(R.id.btn18);
            if (gameData.getString("btn18", "").equals("-1")) {
                btn18.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn18", ""));
            t = (TextView) findViewById(R.id.btn19);
            if (gameData.getString("btn19", "").equals("-1")) {
                btn19.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn19", ""));
            t = (TextView) findViewById(R.id.btn20);
            t.setText(gameData.getString("btn20", ""));
            t = (TextView) findViewById(R.id.btn21);
            t.setText(gameData.getString("btn21", ""));
            t = (TextView) findViewById(R.id.btn22);
            t.setText(gameData.getString("btn22", ""));
            t = (TextView) findViewById(R.id.btn23);
            t.setText(gameData.getString("btn23", ""));
            t = (TextView) findViewById(R.id.btn24);
            if (gameData.getString("btn24", "").equals("-1")) {
                btn24.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn24", ""));
            t = (TextView) findViewById(R.id.btn25);
            if (gameData.getString("btn25", "").equals("-1")) {
                btn25.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn25", ""));
            t = (TextView) findViewById(R.id.btn26);
            t.setText(gameData.getString("btn26", ""));
            t = (TextView) findViewById(R.id.btn27);
            t.setText(gameData.getString("btn27", ""));
            t = (TextView) findViewById(R.id.btn28);
            t.setText(gameData.getString("btn28", ""));
            t = (TextView) findViewById(R.id.btn29);
            t.setText(gameData.getString("btn29", ""));
            t = (TextView) findViewById(R.id.btn30);
            if (gameData.getString("btn30", "").equals("-1")) {
                btn30.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn30", ""));
            t = (TextView) findViewById(R.id.btn31);
            if (gameData.getString("btn31", "").equals("-1")) {
                btn31.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn31", ""));
            t = (TextView) findViewById(R.id.btn32);
            if (gameData.getString("btn32", "").equals("-1")) {
                btn32.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn32", ""));
            t = (TextView) findViewById(R.id.btn33);
            if (gameData.getString("btn33", "").equals("-1")) {
                btn33.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn33", ""));
            t = (TextView) findViewById(R.id.btn34);
            if (gameData.getString("btn34", "").equals("-1")) {
                btn34.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn34", ""));
            t = (TextView) findViewById(R.id.btn35);
            if (gameData.getString("btn35", "").equals("-1")) {
                btn35.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn35", ""));
            t = (TextView) findViewById(R.id.btn36);
            if (gameData.getString("btn36", "").equals("-1")) {
                btn36.setVisibility(View.GONE);
            }
            t.setText(gameData.getString("btn36", ""));

            populateArr();
        } else {
            //   Log.d("Debug", "cotinue from last=false");
            UP = getIntent().getIntExtra("UPDIGIT", 1);
            score = 0;
            life = 3;
            current = 1;
            timeLeft = 120000;
            changeUpTimeLeft = 60000;
            increasedGridTimeLeft1 = 30000;
            increasedGridTimeLeft2 = 90000;

            Random rand = new Random();
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            freezeTimeLeft1 = rand.nextInt((25000 - 10000) + 1) + 10000; //Between 10 to 25 seconds
            freezeTimeLeft2 = rand.nextInt((55000 - 40000) + 1) + 40000; //Between 40 to 55 seconds
            freezeTimeLeft3 = rand.nextInt((85000 - 70000) + 1) + 70000; //Between 70 to 85 seconds
            freezeTimeLeft4 = rand.nextInt((110000 - 100000) + 1) + 100000; //Between 100 to 110 seconds

            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            btn5.setVisibility(View.GONE);
            btn6.setVisibility(View.GONE);
            btn7.setVisibility(View.GONE);
            btn12.setVisibility(View.GONE);
            btn13.setVisibility(View.GONE);
            btn18.setVisibility(View.GONE);
            btn19.setVisibility(View.GONE);
            btn24.setVisibility(View.GONE);
            btn25.setVisibility(View.GONE);
            btn30.setVisibility(View.GONE);
            btn31.setVisibility(View.GONE);
            btn32.setVisibility(View.INVISIBLE);
            btn33.setVisibility(View.INVISIBLE);
            btn34.setVisibility(View.INVISIBLE);
            btn35.setVisibility(View.INVISIBLE);
            btn36.setVisibility(View.GONE);

            initialiseGrid(1);
            lastGeneratedNum = 16;
            populateArr();
        }

        currentScore = (TextView) findViewById(R.id.currentScore);
        currentScore.setText(score + "");
        //  best = (TextView) findViewById(R.id.bestScore);
        //  bestScore = gameData.getInt("bestScore", 0);
        //    best.setText(bestScore + "");
        currentNumText = (TextView) findViewById(R.id.currentNumText);
        currentNumText.setText(current + "");
        upDisplayText = (TextView) findViewById(R.id.UPDisplayText);
        upDisplayText.setText(UP + "");


        //321 animation
        Intent intent = new Intent(this, Onetwothree.class);
        intent.putExtra(Onetwothree.UPDIGIT, UP);
        startActivity(intent);

        //Countdown Timer
        final TextView timer = (TextView) findViewById(R.id.countdown);
        timer.setText("-- : --");
        cdt = new CountDownTimerPausable(timeLeft, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                timer.setText("00 : 00");
                cdt.cancel();
                editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                editor.putInt("score", score).commit();
                GameOver(1);
            }
        };

        //Change UP Timer
        changeUpTimer = new CountDownTimerPausable(changeUpTimeLeft, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                changeUpTimer.cancel();

                int newUpDigit;
                Random rand = new Random();

                do {
                    newUpDigit = rand.nextInt((9 - 3) + 1) + 3;
                } while (newUpDigit == UP);

                secondUP = newUpDigit;

                //   upDisplayText = (TextView) findViewById(R.id.UPDisplayText);
                upDisplayText.setText(UP + ", " + secondUP);

                cdt.pause();
                increasedGridTimer2.pause();
                freezeTimer3.pause();
                freezeTimer4.pause();

                startChangeUpDigit();
                secondUP_start = true;

                final Handler handler = new Handler();
                final Runnable counter = new Runnable() {
                    @Override
                    public void run() {
                        cdt.resume();
                        freezeTimer3.resume();
                        freezeTimer4.resume();
                    }
                };
                handler.postDelayed(counter, 1500);
            }
        };

        //First Increase Grid Timer
        increasedGridTimer1 = new CountDownTimerPausable(increasedGridTimeLeft1, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                increasedGridTimer1.cancel();
                hasIncreasedGrid1 = true;

                cdt.pause();
                changeUpTimer.pause();
                increasedGridTimer2.pause();
                freezeTimer2.pause();
                freezeTimer3.pause();
                freezeTimer4.pause();

                startIncreaseGrid1();

                final Handler handler = new Handler();
                final Runnable counter = new Runnable() {
                    @Override
                    public void run() {
                        cdt.resume();
                        changeUpTimer.resume();
                        increasedGridTimer2.resume();
                        freezeTimer2.resume();
                        freezeTimer3.resume();
                        freezeTimer4.resume();
                    }
                };
                handler.postDelayed(counter, 2000);
            }
        };

        //Second Increase Grid Timer
        increasedGridTimer2 = new CountDownTimerPausable(increasedGridTimeLeft2, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                increasedGridTimer2.cancel();
                hasIncreasedGrid2 = true;

                cdt.pause();
                freezeTimer4.pause();

                startIncreaseGrid2();

                final Handler handler = new Handler();
                final Runnable counter = new Runnable() {
                    @Override
                    public void run() {
                        cdt.resume();
                        freezeTimer4.resume();
                    }
                };
                handler.postDelayed(counter, 2000);
            }
        };

        //First Freeze Timer
        freezeTimer1 = new CountDownTimerPausable(freezeTimeLeft1, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                freezeTimer1.cancel();

                startFreeze();
            }
        };

        //Second Freeze Timer
        freezeTimer2 = new CountDownTimerPausable(freezeTimeLeft2, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                freezeTimer2.cancel();

                startFreeze();
            }
        };

        //Third Freeze Timer
        freezeTimer3 = new CountDownTimerPausable(freezeTimeLeft3, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                freezeTimer3.cancel();

                startFreeze();
            }
        };

        //Fourth Freeze Timer
        freezeTimer4 = new CountDownTimerPausable(freezeTimeLeft4, 100, true) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                freezeTimer4.cancel();

                startFreeze();
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

        final Handler handler3 = new Handler();
        final Runnable counter3 = new Runnable() {
            @Override
            public void run() {
                changeUpTimer.create();
            }
        };
        handler3.postDelayed(counter3, 5000);

        final Handler handler4 = new Handler();
        final Runnable counter4 = new Runnable() {
            @Override
            public void run() {
                increasedGridTimer1.create();
            }
        };
        handler4.postDelayed(counter4, 5000);

        final Handler handler5 = new Handler();
        final Runnable counter5 = new Runnable() {
            @Override
            public void run() {
                increasedGridTimer2.create();
            }
        };
        handler5.postDelayed(counter5, 5000);

        final Handler handler6 = new Handler();
        final Runnable counter6 = new Runnable() {
            @Override
            public void run() {
                freezeTimer1.create();
            }
        };
        handler6.postDelayed(counter6, 5000);

        final Handler handler7 = new Handler();
        final Runnable counter7 = new Runnable() {
            @Override
            public void run() {
                freezeTimer2.create();
            }
        };
        handler7.postDelayed(counter7, 5000);

        final Handler handler8 = new Handler();
        final Runnable counter8 = new Runnable() {
            @Override
            public void run() {
                freezeTimer3.create();
            }
        };
        handler8.postDelayed(counter8, 5000);

        final Handler handler9 = new Handler();
        final Runnable counter9 = new Runnable() {
            @Override
            public void run() {
                freezeTimer4.create();
            }
        };
        handler9.postDelayed(counter9, 5000);


        final Handler handler2 = new Handler();
        final Runnable counter2 = new Runnable() {
            @Override
            public void run() {
                startPause();
            }
        };
        handler2.postDelayed(counter2, 5001);

    }

    private void startFreeze() {

        if (playMusic) {
            playSound(freezeId);
        }

        if (vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
        }

        Random rand = new Random();
        int temp;
        for (int i = 0; i < 4; i++) { //Freeze 4 grids

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            if (hasIncreasedGrid1) {
                do {
                    int gridNo = rand.nextInt((23 - 0) + 1) + 0;
                    temp = afterFreezeArr1[gridNo];
                }
                while (freezeArr[temp] == 2 || freezeArr[temp] == 1); //Make sure its not frozen already
            } else if (hasIncreasedGrid2) {
                do {
                    temp = rand.nextInt((36 - 1) + 1) + 1;
                }
                while (freezeArr[temp] == 2 || freezeArr[temp] == 1); //Make sure its not frozen already
            } else {
                do {
                    int gridNo = rand.nextInt((15 - 0) + 1) + 0;
                    temp = beforeFreezeArr[gridNo];
                }
                while (freezeArr[temp] == 2 || freezeArr[temp] == 1); //Make sure its not frozen already
            }

            freezeArr[temp] = 2; //Frozen

            switch (temp) {
                case 1:
                    btn1.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
                case 2:
                    btn2.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 3:
                    btn3.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 4:
                    btn4.setBackgroundResource(R.drawable.blue_ice1);
                    break;
                case 5:
                    btn5.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 6:
                    btn6.setBackgroundResource(R.drawable.purple_ice1);
                    break;
                case 7:
                    btn7.setBackgroundResource(R.drawable.purple_ice1);
                    break;
                case 8:
                    btn8.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
                case 9:
                    btn9.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 10:
                    btn10.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 11:
                    btn11.setBackgroundResource(R.drawable.blue_ice1);
                    break;
                case 12:
                    btn12.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 13:
                    btn13.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 14:
                    btn14.setBackgroundResource(R.drawable.purple_ice1);
                    break;
                case 15:
                    btn15.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
                case 16:
                    btn16.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 17:
                    btn17.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 18:
                    btn18.setBackgroundResource(R.drawable.blue_ice1);
                    break;
                case 19:
                    btn19.setBackgroundResource(R.drawable.blue_ice1);
                    break;
                case 20:
                    btn20.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 21:
                    btn21.setBackgroundResource(R.drawable.purple_ice1);
                    break;
                case 22:
                    btn22.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
                case 23:
                    btn23.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 24:
                    btn24.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 25:
                    btn25.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 26:
                    btn26.setBackgroundResource(R.drawable.blue_ice1);
                    break;
                case 27:
                    btn27.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 28:
                    btn28.setBackgroundResource(R.drawable.purple_ice1);
                    break;
                case 29:
                    btn29.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
                case 30:
                    btn30.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 31:
                    btn31.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 32:
                    btn32.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 33:
                    btn33.setBackgroundResource(R.drawable.blue_ice1);
                    break;
                case 34:
                    btn34.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 35:
                    btn35.setBackgroundResource(R.drawable.purple_ice1);
                    break;
                case 36:
                    btn36.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
            }
        }
    }

    private void startIncreaseGrid1() {

        //Bounce animation
        final Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.bounce2);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounceAnim.setInterpolator(interpolator);

        btn8.startAnimation(scaleAnim);
        btn9.startAnimation(scaleAnim);
        btn10.startAnimation(scaleAnim);
        btn11.startAnimation(scaleAnim);
        btn14.startAnimation(scaleAnim);
        btn15.startAnimation(scaleAnim);
        btn16.startAnimation(scaleAnim);
        btn17.startAnimation(scaleAnim);
        btn20.startAnimation(scaleAnim);
        btn21.startAnimation(scaleAnim);
        btn22.startAnimation(scaleAnim);
        btn23.startAnimation(scaleAnim);
        btn26.startAnimation(scaleAnim);
        btn27.startAnimation(scaleAnim);
        btn28.startAnimation(scaleAnim);
        btn29.startAnimation(scaleAnim);

        btn2.setVisibility(View.VISIBLE);
        btn3.setVisibility(View.VISIBLE);
        btn4.setVisibility(View.VISIBLE);
        btn5.setVisibility(View.VISIBLE);
        btn32.setVisibility(View.VISIBLE);
        btn33.setVisibility(View.VISIBLE);
        btn34.setVisibility(View.VISIBLE);
        btn35.setVisibility(View.VISIBLE);
        btn2.startAnimation(bounceAnim);
        btn3.startAnimation(bounceAnim);
        btn4.startAnimation(bounceAnim);
        btn5.startAnimation(bounceAnim);
        btn32.startAnimation(bounceAnim);
        btn33.startAnimation(bounceAnim);
        btn34.startAnimation(bounceAnim);
        btn35.startAnimation(bounceAnim);

        changeNo(btn2);
        changeNo(btn3);
        changeNo(btn4);
        changeNo(btn5);
        changeNo(btn32);
        changeNo(btn33);
        changeNo(btn34);
        changeNo(btn35);


        //Sound effect
        if (playMusic) {
            playSound(gridIncreaseId);
        }

        if (vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(2000);
        }

        Intent i = new Intent(this, IncreaseGrid.class);
        this.startActivity(i);
    }

    private void startIncreaseGrid2() {

        //Bounce animation
        final Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        final Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.bounce2);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
        bounceAnim.setInterpolator(interpolator);

        btn8.startAnimation(scaleAnim);
        btn9.startAnimation(scaleAnim);
        btn10.startAnimation(scaleAnim);
        btn11.startAnimation(scaleAnim);
        btn14.startAnimation(scaleAnim);
        btn15.startAnimation(scaleAnim);
        btn16.startAnimation(scaleAnim);
        btn17.startAnimation(scaleAnim);
        btn20.startAnimation(scaleAnim);
        btn21.startAnimation(scaleAnim);
        btn22.startAnimation(scaleAnim);
        btn23.startAnimation(scaleAnim);
        btn26.startAnimation(scaleAnim);
        btn27.startAnimation(scaleAnim);
        btn28.startAnimation(scaleAnim);
        btn29.startAnimation(scaleAnim);
        btn2.startAnimation(scaleAnim);
        btn3.startAnimation(scaleAnim);
        btn4.startAnimation(scaleAnim);
        btn5.startAnimation(scaleAnim);
        btn32.startAnimation(scaleAnim);
        btn33.startAnimation(scaleAnim);
        btn34.startAnimation(scaleAnim);
        btn35.startAnimation(scaleAnim);

        btn1.setVisibility(View.VISIBLE);
        btn6.setVisibility(View.VISIBLE);
        btn7.setVisibility(View.VISIBLE);
        btn12.setVisibility(View.VISIBLE);
        btn13.setVisibility(View.VISIBLE);
        btn18.setVisibility(View.VISIBLE);
        btn19.setVisibility(View.VISIBLE);
        btn24.setVisibility(View.VISIBLE);
        btn25.setVisibility(View.VISIBLE);
        btn30.setVisibility(View.VISIBLE);
        btn31.setVisibility(View.VISIBLE);
        btn36.setVisibility(View.VISIBLE);
        btn1.startAnimation(bounceAnim);
        btn6.startAnimation(bounceAnim);
        btn7.startAnimation(bounceAnim);
        btn12.startAnimation(bounceAnim);
        btn13.startAnimation(bounceAnim);
        btn18.startAnimation(bounceAnim);
        btn19.startAnimation(bounceAnim);
        btn24.startAnimation(bounceAnim);
        btn25.startAnimation(bounceAnim);
        btn30.startAnimation(bounceAnim);
        btn31.startAnimation(bounceAnim);
        btn36.startAnimation(bounceAnim);

        changeNo(btn1);
        changeNo(btn6);
        changeNo(btn7);
        changeNo(btn12);
        changeNo(btn13);
        changeNo(btn18);
        changeNo(btn19);
        changeNo(btn24);
        changeNo(btn25);
        changeNo(btn30);
        changeNo(btn31);
        changeNo(btn36);

        //Sound effect
        if (playMusic) {
            playSound(gridIncreaseId);
        }

        if (vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(2000);
        }

        Intent i = new Intent(this, IncreaseGrid.class);
        this.startActivity(i);
    }


    private void startChangeUpDigit() {
        Intent i = new Intent(this, SecondUpDigit.class);
        i.putExtra(SecondUpDigit.UPDIGIT, UP);
        i.putExtra(SecondUpDigit.UPDIGIT2, secondUP);
        this.startActivity(i);
    }

    private void startPause() {
        if (isApplicationSentToBackground(getApplicationContext())) {
            // Do what you want to do on detecting Home Key being Pressed
            cdt.pause();
            changeUpTimer.pause();
            increasedGridTimer1.pause();
            increasedGridTimer2.pause();
            freezeTimer1.pause();
            freezeTimer2.pause();
            freezeTimer3.pause();
            freezeTimer4.pause();
            Intent pause = new Intent(this, Pause.class);
            pause.putExtra(Pause.UPDIGIT, UP);
            pause.putExtra(Pause.SCORE, score);
            pause.putExtra(Pause.TIME, cdt.timeLeft());
            pause.putExtra(Pause.CHANGEUPTIME, changeUpTimer.timeLeft());
            pause.putExtra(Pause.ONETWOTHREE_PAUSED, true);
            pause.putExtra(Pause.CURRENT, current);
            this.startActivity(pause);

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
            changeUpTimer.pause();
            increasedGridTimer1.pause();
            increasedGridTimer2.pause();
            freezeTimer1.pause();
            freezeTimer2.pause();
            freezeTimer3.pause();
            freezeTimer4.pause();
            Intent pause = new Intent(this, Pause.class);
            pause.putExtra(Pause.UPDIGIT, UP);
            pause.putExtra(Pause.SCORE, score);
            pause.putExtra(Pause.TIME, cdt.timeLeft());
            pause.putExtra(Pause.CHANGEUPTIME, changeUpTimer.timeLeft());
            pause.putExtra(Pause.CURRENT, current);
            //    this.startActivity(pause);
        }

    }


    @Override
    public void onBackPressed() {
        cdt.pause();
        changeUpTimer.pause();
        increasedGridTimer1.pause();
        increasedGridTimer2.pause();
        freezeTimer1.pause();
        freezeTimer2.pause();
        freezeTimer3.pause();
        freezeTimer4.pause();

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
        editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
        editor.putInt("life", life).commit();
        editor.putInt("current", current).commit();
        editor.putLong("timeLeft", cdt.timeLeft()).commit();
        editor.putLong("changeUpTimeLeft", changeUpTimer.timeLeft()).commit();
        editor.putLong("increasedGridtimeLeft1", increasedGridTimer1.timeLeft()).commit();
        editor.putLong("increasedGridtimeLeft2", increasedGridTimer2.timeLeft()).commit();
        editor.putLong("freezetimeLeft1", freezeTimer1.timeLeft()).commit();
        editor.putLong("freezetimeLeft2", freezeTimer2.timeLeft()).commit();
        editor.putLong("freezetimeLeft3", freezeTimer3.timeLeft()).commit();
        editor.putLong("freezetimeLeft4", freezeTimer4.timeLeft()).commit();
        for (Integer i : fillArr) {
            fillArrContent += i + ",";
        }
        editor.putString("FILLARR_CONTENT", fillArrContent).commit();
        Intent i = new Intent(this, Pause.class);
        i.putExtra(Pause.UPDIGIT, UP);
        i.putExtra(Pause.TIME, cdt.timeLeft());
        i.putExtra(Pause.CHANGEUPTIME, changeUpTimer.timeLeft());
        i.putExtra(Pause.SCORE, score);
        i.putExtra(Pause.CURRENT, current);
        this.startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);

        cdt.resume();
        changeUpTimer.resume();
        increasedGridTimer1.resume();
        increasedGridTimer2.resume();
        freezeTimer1.resume();
        freezeTimer2.resume();
        freezeTimer3.resume();
        freezeTimer4.resume();

        ImageButton pause = (ImageButton) findViewById(R.id.pause_btn);
        pause.setImageResource(R.drawable.pause_button);
        pause.setOnClickListener(this);

        life3 = (ImageView) findViewById(R.id.life3);
        life3.setImageResource(R.drawable.life3);
        life2 = (ImageView) findViewById(R.id.life2);
        life2.setImageResource(R.drawable.life2);
        life1 = (ImageView) findViewById(R.id.life1);
        life1.setImageResource(R.drawable.life1);

        //set onClickListeners for all buttons

        upBtn.setOnClickListener(this);
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
        changeUpTimer.pause();
        increasedGridTimer1.pause();
        increasedGridTimer2.pause();
        freezeTimer1.pause();
        freezeTimer2.pause();
        freezeTimer3.pause();
        freezeTimer4.pause();

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
        //Log.d("checkstate","UP saved as "+UP);
        outState.putInt("pressedNum", pressedNum);
        //Log.d("checkstate","pressedNum saved as "+pressedNum);
        outState.putInt("score", score);
        //Log.d("checkstate","score saved as "+score);
        outState.putInt("life", life);
        //Log.d("checkstate","life saved as "+life);
        outState.putInt("current", current);
        //Log.d("checkstate","current saved as "+current);

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

        boolean isUp;
        isUp = checkUp();
        if (isUp) {   //UP num
            if (pressedID == R.id.up_button) {  //Correct

                //Background animation
                bg.setBackgroundResource(R.drawable.up_animation);
                AnimationDrawable anim = (AnimationDrawable) bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

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
                findCurrent();
                currentScore.setText(score + "");
                current++;
                currentNumText.setText(current + "");


            } else {//Wrong
               /*cdt.cancel();
               editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                editor.putInt("score", score).commit();
                GameOver(2);
                */

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
                    life3.setVisibility(View.GONE);
                } else if (life == 1) {
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                } else if (life <= 0) {
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                    life1.setVisibility(View.GONE);

                    cdt.cancel();
                    changeUpTimer.cancel();
                    increasedGridTimer1.cancel();
                    increasedGridTimer2.cancel();
                    freezeTimer1.cancel();
                    freezeTimer2.cancel();
                    freezeTimer3.cancel();
                    freezeTimer4.cancel();
                    editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(2);
                    return;
                }
                //Shake screen
                //  pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                if (life > 1) {
                    final Toast toast1 = Toast.makeText(MainExtreme4.this, "You have " + life + " more lives!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast1.show();
                        }

                        public void onFinish() {
                            toast1.cancel();
                        }
                    }.start();
                } else {
                    final Toast toast2 = Toast.makeText(MainExtreme4.this, "You have " + life + " more life!", Toast.LENGTH_SHORT);
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
                currentScore.setText(score + "");
                current++;
                currentNumText.setText(current + "");


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
                    life3.setVisibility(View.GONE);
                } else if (life == 1) {
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                } else if (life <= 0) {
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                    life1.setVisibility(View.GONE);

                    cdt.cancel();
                    changeUpTimer.cancel();
                    increasedGridTimer1.cancel();
                    increasedGridTimer2.cancel();
                    freezeTimer1.cancel();
                    freezeTimer2.cancel();
                    freezeTimer3.cancel();
                    freezeTimer4.cancel();
                    editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(3);
                    return;
                }
                //Shake screen
                //    pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                if (life > 1) {
                    final Toast toast1 = Toast.makeText(MainExtreme4.this, "You have " + life + " more lives!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast1.show();
                        }

                        public void onFinish() {
                            toast1.cancel();
                        }
                    }.start();
                } else {
                    final Toast toast2 = Toast.makeText(MainExtreme4.this, "You have " + life + " more life!", Toast.LENGTH_SHORT);
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

    public boolean checkUp() {
        if (secondUP_start) {
            return (current % UP == 0) || (current % secondUP == 0) || checkDigit();
        } else {
            return (current % UP == 0) || checkDigit();
        }
    }

    public boolean checkDigit() {
        String cur = Integer.toString(current);
        String upString = Integer.toString(UP);

        if (secondUP_start) {
            String upString2 = Integer.toString(secondUP);
            return cur.contains(upString) || cur.contains(upString2);
        } else {
            return cur.contains(upString);
        }
    }

    //Brute force
    //Finds and change the digit for UP num
    public void findCurrent() {
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

    public void GameOver(int r) {
        Intent i = new Intent(this, GameOver.class);
        i.putExtra(GameOver.REASON, r);
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

        for (int i = start; i < start + 16; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);

        //First 4x4 grid
        btn8.setText(arr.remove(0) + "");
        btn9.setText(arr.remove(0) + "");
        btn10.setText(arr.remove(0) + "");
        btn11.setText(arr.remove(0) + "");
        btn14.setText(arr.remove(0) + "");
        btn15.setText(arr.remove(0) + "");
        btn16.setText(arr.remove(0) + "");
        btn17.setText(arr.remove(0) + "");
        btn20.setText(arr.remove(0) + "");
        btn21.setText(arr.remove(0) + "");
        btn22.setText(arr.remove(0) + "");
        btn23.setText(arr.remove(0) + "");
        btn26.setText(arr.remove(0) + "");
        btn27.setText(arr.remove(0) + "");
        btn28.setText(arr.remove(0) + "");
        btn29.setText(arr.remove(0) + "");

        //Rest of the grid
        btn1.setText("-1");
        btn2.setText("-1");
        btn3.setText("-1");
        btn4.setText("-1");
        btn5.setText("-1");
        btn6.setText("-1");
        btn7.setText("-1");
        btn12.setText("-1");
        btn13.setText("-1");
        btn18.setText("-1");
        btn19.setText("-1");
        btn24.setText("-1");
        btn25.setText("-1");
        btn30.setText("-1");
        btn31.setText("-1");
        btn32.setText("-1");
        btn33.setText("-1");
        btn34.setText("-1");
        btn35.setText("-1");
        btn36.setText("-1");
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

    private void freezeEffect() {
        if (playMusic) {
            playSound(unfreezeId);
        }

        if (vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
        }
    }

    public void onClick(View v) {

        Button temp;

        switch (v.getId()) {
            case R.id.pause_btn:
                cdt.pause();
                changeUpTimer.pause();
                increasedGridTimer1.pause();
                increasedGridTimer2.pause();
                freezeTimer1.pause();
                freezeTimer2.pause();
                freezeTimer3.pause();
                freezeTimer4.pause();

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
                editor.putInt("UPdigit", UP).commit();
                editor.putInt("UPdigit2", secondUP).commit();
                editor.putBoolean("UPdigit2_start", secondUP_start).commit();
                editor.putInt("score", score).commit();
                editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                editor.putInt("life", life).commit();
                editor.putInt("current", current).commit();
                editor.putLong("timeLeft", cdt.timeLeft()).commit();
                editor.putLong("changeUpTimeLeft", changeUpTimer.timeLeft()).commit();
                editor.putLong("increasedGridtimeLeft1", increasedGridTimer1.timeLeft()).commit();
                editor.putLong("increasedGridtimeLeft2", increasedGridTimer2.timeLeft()).commit();
                editor.putLong("freezetimeLeft1", freezeTimer1.timeLeft()).commit();
                editor.putLong("freezetimeLeft2", freezeTimer2.timeLeft()).commit();
                editor.putLong("freezetimeLeft3", freezeTimer3.timeLeft()).commit();
                editor.putLong("freezetimeLeft4", freezeTimer4.timeLeft()).commit();
                for (Integer i : fillArr) {
                    fillArrContent += i + ",";
                }
                editor.putString("FILLARR_CONTENT", fillArrContent).commit();
                Intent i = new Intent(this, Pause.class);
                i.putExtra(Pause.UPDIGIT, UP);
                i.putExtra(Pause.UPDIGIT2, secondUP);
                i.putExtra(Pause.TIME, cdt.timeLeft());
                i.putExtra(Pause.CHANGEUPTIME, changeUpTimer.timeLeft());
                i.putExtra(Pause.SCORE, score);
                i.putExtra(Pause.CURRENT, current);
                this.startActivity(i);
                break;
            case R.id.up_button:
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

                if (freezeArr[1] == 2) {
                    freezeArr[1] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                } else if (freezeArr[1] == 1) {
                    freezeArr[1] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                    Game();
                }
                break;
            case R.id.btn2:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[2] == 2) {
                    freezeArr[2] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                } else if (freezeArr[2] == 1) {
                    freezeArr[2] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.orange_spark);
                    Game();
                }
                break;
            case R.id.btn3:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[3] == 2) {
                    freezeArr[3] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                } else if (freezeArr[3] == 1) {
                    freezeArr[3] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.pink_spark);
                    Game();
                }
                break;
            case R.id.btn4:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[4] == 2) {
                    freezeArr[4] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_ice2);
                } else if (freezeArr[4] == 1) {
                    freezeArr[4] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.blue_spark);
                    Game();
                }
                break;
            case R.id.btn5:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[5] == 2) {
                    freezeArr[5] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_ice2);
                } else if (freezeArr[5] == 1) {
                    freezeArr[5] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.green_spark);
                    Game();
                }
                break;
            case R.id.btn6:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[6] == 2) {
                    freezeArr[6] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_ice2);
                } else if (freezeArr[6] == 1) {
                    freezeArr[6] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.purple_spark);
                    Game();
                }
                break;
            case R.id.btn7:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[7] == 2) {
                    freezeArr[7] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_ice2);
                } else if (freezeArr[7] == 1) {
                    freezeArr[7] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.purple_spark);
                    Game();
                }
                break;
            case R.id.btn8:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[8] == 2) {
                    freezeArr[8] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                } else if (freezeArr[8] == 1) {
                    freezeArr[8] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                    Game();
                }
                break;
            case R.id.btn9:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[9] == 2) {
                    freezeArr[9] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                } else if (freezeArr[9] == 1) {
                    freezeArr[9] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.orange_spark);
                    Game();
                }
                break;
            case R.id.btn10:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[10] == 2) {
                    freezeArr[10] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                } else if (freezeArr[10] == 1) {
                    freezeArr[10] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.pink_spark);
                    Game();
                }
                break;
            case R.id.btn11:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[11] == 2) {
                    freezeArr[11] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_ice2);
                } else if (freezeArr[11] == 1) {
                    freezeArr[11] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.blue_spark);
                    Game();
                }
                break;
            case R.id.btn12:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[12] == 2) {
                    freezeArr[12] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_ice2);
                } else if (freezeArr[12] == 1) {
                    freezeArr[12] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.green_spark);
                    Game();
                }
                break;
            case R.id.btn13:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[13] == 2) {
                    freezeArr[13] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_ice2);
                } else if (freezeArr[13] == 1) {
                    freezeArr[13] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.green_spark);
                    Game();
                }
                break;
            case R.id.btn14:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[14] == 2) {
                    freezeArr[14] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_ice2);
                } else if (freezeArr[14] == 1) {
                    freezeArr[14] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.purple_spark);
                    Game();
                }
                break;
            case R.id.btn15:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[15] == 2) {
                    freezeArr[15] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                } else if (freezeArr[15] == 1) {
                    freezeArr[15] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                    Game();
                }
                break;
            case R.id.btn16:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[16] == 2) {
                    freezeArr[16] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                } else if (freezeArr[16] == 1) {
                    freezeArr[16] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.orange_spark);
                    Game();
                }
                break;
            case R.id.btn17:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[17] == 2) {
                    freezeArr[17] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                } else if (freezeArr[17] == 1) {
                    freezeArr[17] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.pink_spark);
                    Game();
                }
                break;
            case R.id.btn18:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[18] == 2) {
                    freezeArr[18] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_ice2);
                } else if (freezeArr[18] == 1) {
                    freezeArr[18] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.blue_spark);
                    Game();
                }
                break;
            case R.id.btn19:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[19] == 2) {
                    freezeArr[19] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_ice2);
                } else if (freezeArr[19] == 1) {
                    freezeArr[19] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.blue_spark);
                    Game();
                }
                break;
            case R.id.btn20:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[20] == 2) {
                    freezeArr[20] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_ice2);
                } else if (freezeArr[20] == 1) {
                    freezeArr[20] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.green_spark);
                    Game();
                }
                break;
            case R.id.btn21:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[21] == 2) {
                    freezeArr[21] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_ice2);
                } else if (freezeArr[21] == 1) {
                    freezeArr[21] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.purple_spark);
                    Game();
                }
                break;
            case R.id.btn22:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[22] == 2) {
                    freezeArr[22] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                } else if (freezeArr[22] == 1) {
                    freezeArr[22] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                    Game();
                }
                break;
            case R.id.btn23:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[23] == 2) {
                    freezeArr[23] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                } else if (freezeArr[23] == 1) {
                    freezeArr[23] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.orange_spark);
                    Game();
                }
                break;
            case R.id.btn24:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[24] == 2) {
                    freezeArr[24] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                } else if (freezeArr[24] == 1) {
                    freezeArr[24] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.pink_spark);
                    Game();
                }
                break;
            case R.id.btn25:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[25] == 2) {
                    freezeArr[25] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                } else if (freezeArr[25] == 1) {
                    freezeArr[25] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.pink_spark);
                    Game();
                }
                break;
            case R.id.btn26:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[26] == 2) {
                    freezeArr[26] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_ice2);
                } else if (freezeArr[26] == 1) {
                    freezeArr[26] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.blue_spark);
                    Game();
                }
                break;
            case R.id.btn27:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[27] == 2) {
                    freezeArr[27] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_ice2);
                } else if (freezeArr[27] == 1) {
                    freezeArr[27] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.green_spark);
                    Game();
                }
                break;
            case R.id.btn28:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[28] == 2) {
                    freezeArr[28] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_ice2);
                } else if (freezeArr[28] == 1) {
                    freezeArr[28] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.purple_spark);
                    Game();
                }
                break;
            case R.id.btn29:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[29] == 2) {
                    freezeArr[29] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                } else if (freezeArr[29] == 1) {
                    freezeArr[29] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                    Game();
                }
                break;
            case R.id.btn30:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[30] == 2) {
                    freezeArr[30] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                } else if (freezeArr[30] == 1) {
                    freezeArr[30] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.orange_spark);
                    Game();
                }
                break;
            case R.id.btn31:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[31] == 2) {
                    freezeArr[31] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                } else if (freezeArr[31] == 1) {
                    freezeArr[31] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.orange_spark);
                    Game();
                }
                break;
            case R.id.btn32:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[32] == 2) {
                    freezeArr[32] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                } else if (freezeArr[32] == 1) {
                    freezeArr[32] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.pink_spark);
                    Game();
                }
                break;
            case R.id.btn33:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[33] == 2) {
                    freezeArr[33] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_ice2);
                } else if (freezeArr[33] == 1) {
                    freezeArr[33] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.blue_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.blue_spark);
                    Game();
                }
                break;
            case R.id.btn34:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[34] == 2) {
                    freezeArr[34] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_ice2);
                } else if (freezeArr[34] == 1) {
                    freezeArr[34] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.green_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.green_spark);
                    Game();
                }
                break;
            case R.id.btn35:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[35] == 2) {
                    freezeArr[35] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_ice2);
                } else if (freezeArr[35] == 1) {
                    freezeArr[35] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.purple_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.purple_spark);
                    Game();
                }
                break;
            case R.id.btn36:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();

                if (freezeArr[36] == 2) {
                    freezeArr[36] = 1;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                } else if (freezeArr[36] == 1) {
                    freezeArr[36] = 0;
                    freezeEffect();
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                } else {
                    pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                    Game();
                }
                break;
        }
    }
}

