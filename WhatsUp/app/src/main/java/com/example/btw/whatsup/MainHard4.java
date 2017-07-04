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

public class MainHard4 extends Activity implements OnClickListener, View.OnTouchListener {

    private boolean continueFromLast;
    protected SharedPreferences gameDataHard;
    protected SharedPreferences.Editor editor;
    protected boolean continueMusic = true;

    public static final String UPDIGIT = "UPDIGIT";
    private int UP;
    private int secondUP;
    private boolean secondUP_start;
    private int score;
    //  private int bestScore;
    private int current;
    private int life;
    private ArrayList<Integer> fillArr = new ArrayList<Integer>();  //arraylist that contains new numbers to replace tapped numbers
    private String fillArrContent = "";
    private int pressedNum;
    private int pressedID;
    private Button pressedButton;
    private ImageView life1, life2, life3;

    private int[] freezeArr = new int[17]; //Does not use freezeArr[0]
    private int[] freezeArr_rotate = new int[17]; //Does not use freezeArr_rotate[0]

    private long timeLeft;
    private long changeUpTimeLeft;
    private long rotateTimeLeft1;
    private long rotateTimeLeft2;
    private long freezeTimeLeft1;
    private long freezeTimeLeft2;
    private long freezeTimeLeft3;
    private long freezeTimeLeft4;
    public CountDownTimerPausable cdt;
    public CountDownTimerPausable changeUpTimer;
    public CountDownTimerPausable rotateTimer1;
    public CountDownTimerPausable rotateTimer2;
    public CountDownTimerPausable freezeTimer1;
    public CountDownTimerPausable freezeTimer2;
    public CountDownTimerPausable freezeTimer3;
    public CountDownTimerPausable freezeTimer4;

    private LinearLayout bg;
    private boolean hasRotated;
    private boolean hasRotated1;
    private boolean hasRotated2;
    private boolean hasChangedUp;

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

//    private MediaPlayer explode_sound;
 //   private MediaPlayer up_sound;
  //  private MediaPlayer tap_sound;
    public static boolean playMusic;
    public static boolean vibrationOn;

    private SoundPoolHelper mSoundPoolHelper;
    private int explodeId, tapId, upId, rotateId, freezeId, unfreezeId;

    public static void checkMusic(Context context) {
        if (Settings.getMusic(context)) {
            playMusic = true;
        }
        else{
            playMusic = false;
        }

    }

    public static void checkVibration(Context context) {
        if (Settings.getVibration(context)) {
            vibrationOn = true;
        }
        else{
            vibrationOn = false;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainhard4);

        checkVibration(this);
        checkMusic(this);
        hasRotated = false;
        hasRotated1 = false;
        hasRotated2 = false;
        hasChangedUp= false;
        secondUP_start = false;
        secondUP = -1;

        /* First initialisation of frozen grids.
            0: Unfrozen; 1:Frozen once; 2:Frozen twice */
        for(int i=1; i<=16; i++){
            freezeArr[i] = 0;
            freezeArr_rotate[i] = 0;
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

        bg = (LinearLayout)this.findViewById(R.id.main);

        upBtn = (Button) this.findViewById(R.id.up_button);
        btn1 = (Button) this.findViewById(R.id.btn1);
        btn2 = (Button) this.findViewById(R.id.btn2);
        btn2.setBackgroundResource(R.drawable.orange_spark);
        btn3 = (Button) this.findViewById(R.id.btn3);
        btn3.setBackgroundResource(R.drawable.pink_spark);
        btn4 = (Button) this.findViewById(R.id.btn4);
        btn4.setBackgroundResource(R.drawable.green_spark);
        btn5 = (Button) this.findViewById(R.id.btn5);
        btn5.setBackgroundResource(R.drawable.green_spark);
        btn6 = (Button) this.findViewById(R.id.btn6);
        btn6.setBackgroundResource(R.drawable.yellow_spark);
        btn7 = (Button) this.findViewById(R.id.btn7);
        btn7.setBackgroundResource(R.drawable.orange_spark);
        btn8 = (Button) this.findViewById(R.id.btn8);
        btn8.setBackgroundResource(R.drawable.pink_spark);
        btn9 = (Button) this.findViewById(R.id.btn9);
        btn9.setBackgroundResource(R.drawable.pink_spark);
        btn10 = (Button) this.findViewById(R.id.btn10);
        btn10.setBackgroundResource(R.drawable.green_spark);
        btn11 = (Button) this.findViewById(R.id.btn11);
        btn11.setBackgroundResource(R.drawable.yellow_spark);
        btn12 = (Button) this.findViewById(R.id.btn12);
        btn12.setBackgroundResource(R.drawable.orange_spark);
        btn13 = (Button) this.findViewById(R.id.btn13);
        btn13.setBackgroundResource(R.drawable.orange_spark);
        btn14 = (Button) this.findViewById(R.id.btn14);
        btn14.setBackgroundResource(R.drawable.pink_spark);
        btn15 = (Button) this.findViewById(R.id.btn15);
        btn15.setBackgroundResource(R.drawable.green_spark);
        btn16 = (Button) this.findViewById(R.id.btn16);
        btn16.setBackgroundResource(R.drawable.yellow_spark);


        gameDataHard = getSharedPreferences("gameDataHard", Context.MODE_PRIVATE);
        editor = gameDataHard.edit();

        continueFromLast = gameDataHard.getBoolean("continuefromlastHard", false);
        if (continueFromLast) {
            //   Log.d("Debug", "cotinue from last=true");
            UP = gameDataHard.getInt("UPdigit", 1);
            secondUP = gameDataHard.getInt("UPdigit2", -1);
            secondUP_start = gameDataHard.getBoolean("UPdigit2_start", false);
            hasRotated1 = gameDataHard.getBoolean("hasRotated1", false);
            hasRotated2 = gameDataHard.getBoolean("hasRotated2", false);
            hasChangedUp = gameDataHard.getBoolean("hasChangedUp", false);
            score = gameDataHard.getInt("score", 0);
            life = gameDataHard.getInt("life", 3);
            current = gameDataHard.getInt("current", 1);
            timeLeft = gameDataHard.getLong("timeLeft", 120000);
            changeUpTimeLeft = gameDataHard.getLong("changeUpTimeLeft", 60000);
            rotateTimeLeft1 = gameDataHard.getLong("rotatetimeLeft1", 30000);
            rotateTimeLeft2 = gameDataHard.getLong("rotatetimeLeft2", 90000);
            freezeTimeLeft1 = gameDataHard.getLong("freezetimeLeft1", 15000);
            freezeTimeLeft2 = gameDataHard.getLong("freezetimeLeft2", 45000);
            freezeTimeLeft3 = gameDataHard.getLong("freezetimeLeft3", 75000);
            freezeTimeLeft4 = gameDataHard.getLong("freezetimeLeft4", 105000);
            fillArrContent = gameDataHard.getString("FILLARR_CONTENT", "");
            String[] strArray = fillArrContent.split(",");
            fillArr.clear();
            for (int i = 0; i < strArray.length; i++) {
                fillArr.add(0, Integer.parseInt(strArray[i]));
            }
            TextView t;
            t = (TextView) findViewById(R.id.btn1);
            t.setText(gameDataHard.getString("btn1", ""));
            t = (TextView) findViewById(R.id.btn2);
            t.setText(gameDataHard.getString("btn2", ""));
            t = (TextView) findViewById(R.id.btn3);
            t.setText(gameDataHard.getString("btn3", ""));
            t = (TextView) findViewById(R.id.btn4);
            t.setText(gameDataHard.getString("btn4", ""));
            t = (TextView) findViewById(R.id.btn5);
            t.setText(gameDataHard.getString("btn5", ""));
            t = (TextView) findViewById(R.id.btn6);
            t.setText(gameDataHard.getString("btn6", ""));
            t = (TextView) findViewById(R.id.btn7);
            t.setText(gameDataHard.getString("btn7", ""));
            t = (TextView) findViewById(R.id.btn8);
            t.setText(gameDataHard.getString("btn8", ""));
            t = (TextView) findViewById(R.id.btn9);
            t.setText(gameDataHard.getString("btn9", ""));
            t = (TextView) findViewById(R.id.btn10);
            t.setText(gameDataHard.getString("btn10", ""));
            t = (TextView) findViewById(R.id.btn11);
            t.setText(gameDataHard.getString("btn11", ""));
            t = (TextView) findViewById(R.id.btn12);
            t.setText(gameDataHard.getString("btn12", ""));
            t = (TextView) findViewById(R.id.btn13);
            t.setText(gameDataHard.getString("btn13", ""));
            t = (TextView) findViewById(R.id.btn14);
            t.setText(gameDataHard.getString("btn14", ""));
            t = (TextView) findViewById(R.id.btn15);
            t.setText(gameDataHard.getString("btn15", ""));
            t = (TextView) findViewById(R.id.btn16);
            t.setText(gameDataHard.getString("btn16", ""));
            populateArr(current + 16);
        } else {
            //   Log.d("Debug", "cotinue from last=false");
            UP = getIntent().getIntExtra("UPDIGIT", 1);
            score = 0;
            life = 3;
            current = 1;
            timeLeft = 120000;
            changeUpTimeLeft = 60000;
            rotateTimeLeft1 = 30000;
            rotateTimeLeft2 = 90000;

            Random rand = new Random();
            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            freezeTimeLeft1 = rand.nextInt((25000 - 10000) + 1) + 10000; //Between 10 to 25 seconds
            freezeTimeLeft2 = rand.nextInt((55000 - 40000) + 1) + 40000; //Between 40 to 55 seconds
            freezeTimeLeft3 = rand.nextInt((85000 - 70000) + 1) + 70000; //Between 70 to 85 seconds
            freezeTimeLeft4 = rand.nextInt((110000 - 100000) + 1) + 100000; //Between 100 to 110 seconds

            initialiseGrid(1);
            populateArr(17);
        }

        currentScore = (TextView) findViewById(R.id.currentScore);
        currentScore.setText(score + "");
        //  best = (TextView) findViewById(R.id.bestScore);
        //  bestScore = gameDataHard.getInt("bestScore", 0);
        //    best.setText(bestScore + "");
        currentNumText = (TextView) findViewById(R.id.currentNumText);
        currentNumText.setText(current + "");
        upDisplayText = (TextView) findViewById(R.id.UPDisplayText);
        if (secondUP == -1) { //SecondUP has not started
            upDisplayText.setText(UP + "");
        } else {
            upDisplayText.setText(UP + ", " + secondUP);
        }


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
                editor.putInt("bestScore", Math.max(score, gameDataHard.getInt("bestScore", -1))).commit();
                editor.putInt("score", score).commit();
                GameOver(1);
            }
        };

        //Change UP Timer
        if(changeUpTimeLeft != 0) {
            changeUpTimer = new CountDownTimerPausable(changeUpTimeLeft, 100, true) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {

                    hasChangedUp = true;
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
                    rotateTimer2.pause();
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
        }

        //First Rotate Timer
        if(rotateTimeLeft1 != 0) {
            rotateTimer1 = new CountDownTimerPausable(rotateTimeLeft1, 100, true) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {

                    hasRotated1 = true;
                    rotateTimer1.cancel();

                    cdt.pause();
                    changeUpTimer.pause();
                    rotateTimer2.pause();
                    freezeTimer2.pause();
                    freezeTimer3.pause();
                    freezeTimer4.pause();

                    startRotate1();

                    final Handler handler = new Handler();
                    final Runnable counter = new Runnable() {
                        @Override
                        public void run() {
                            cdt.resume();
                            if(!hasChangedUp) {
                                changeUpTimer.resume();
                            }
                            if(!hasRotated2) {
                                rotateTimer2.resume();
                            }
                            freezeTimer2.resume();
                            freezeTimer3.resume();
                            freezeTimer4.resume();
                        }
                    };
                    handler.postDelayed(counter, 2500);
                }
            };
        }

        //Second Rotate Timer
        if(rotateTimeLeft2 != 0) {
            rotateTimer2 = new CountDownTimerPausable(rotateTimeLeft2, 100, true) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {

                    hasRotated2 = true;
                    rotateTimer2.cancel();

                    cdt.pause();
                    freezeTimer4.pause();

                    startRotate2();

                    final Handler handler = new Handler();
                    final Runnable counter = new Runnable() {
                        @Override
                        public void run() {
                            cdt.resume();
                            freezeTimer4.resume();
                        }
                    };
                    handler.postDelayed(counter, 2500);
                }
            };
        }

        //First Freeze Timer
        freezeTimer1 = new CountDownTimerPausable(freezeTimeLeft1, 100, true){
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
                freezeTimer2 = new CountDownTimerPausable(freezeTimeLeft2, 100, true){
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
                freezeTimer3 = new CountDownTimerPausable(freezeTimeLeft3, 100, true){
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
                freezeTimer4 = new CountDownTimerPausable(freezeTimeLeft4, 100, true){
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
                if(!hasChangedUp) {
                    changeUpTimer.create();
                }
            }
        };
        handler3.postDelayed(counter3, 5000);

        final Handler handler4 = new Handler();
        final Runnable counter4 = new Runnable() {
            @Override
            public void run() {
                if(!hasRotated1) {
                    rotateTimer1.create();
                }
            }
        };
        handler4.postDelayed(counter4, 5000);

        final Handler handler5 = new Handler();
        final Runnable counter5 = new Runnable() {
            @Override
            public void run() {
                if(!hasRotated2) {
                    rotateTimer2.create();
                }
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



     /*
        final AtomicInteger n = new AtomicInteger(15);

        final Handler handler3 = new Handler();
         final TextView textView = (TextView) findViewById(R.id.currentScore);
         final AtomicInteger n = new AtomicInteger(3);
        final Runnable check = new Runnable() {

            @Override
            public void run() {
                boolean isBackPressed = getIntent().getBooleanExtra(BACKPRESSED, false);
                int test = getIntent().getIntExtra(TEST, 100);

                if (isBackPressed){
                    //      cdt.cancel();
                    //     finish();

                }
                else if(test < 0){
                    n.getAndIncrement();
                         TextView v = (TextView) findViewById(R.id.currentScore);
                        v.setText(Integer.toString(test));
                    handler3.postDelayed(this, 500);
                }
                else if(n.get() > 0){
                    n.getAndDecrement();
                        TextView v = (TextView) findViewById(R.id.currentScore);
                      v.setText(Integer.toString(n.get()));
                    handler3.postDelayed(this, 500);
                }
            }
        };
        handler3.postDelayed(check, 1);
    }

 */
    }

    private void startFreeze(){

        if(playMusic) {
            playSound(freezeId);
        }

        if(vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
        }

        Random rand = new Random();
        int temp;
        for(int i=0; i<4; i++){ //Freeze 4 grids

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            do{
                temp = rand.nextInt((16 - 1) + 1) + 1;
            }while(freezeArr[temp]==2 || freezeArr[temp]==1); //Make sure its not frozen already

            freezeArr[temp] = 2; //Frozen

            switch(temp){
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
                    btn4.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 5:
                    btn5.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 6:
                    btn6.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
                case 7:
                    btn7.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 8:
                    btn8.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 9:
                    btn9.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 10:
                    btn10.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 11:
                    btn11.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
                case 12:
                    btn12.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 13:
                    btn13.setBackgroundResource(R.drawable.orange_ice1);
                    break;
                case 14:
                    btn14.setBackgroundResource(R.drawable.pink_ice1);
                    break;
                case 15:
                    btn15.setBackgroundResource(R.drawable.green_ice1);
                    break;
                case 16:
                    btn16.setBackgroundResource(R.drawable.yellow_ice1);
                    break;
            }
        }
    }

    private void rotateFreezeArr(){

        freezeArr_rotate[16] = freezeArr[1];
        freezeArr_rotate[15] = freezeArr[2];
        freezeArr_rotate[14] = freezeArr[3];
        freezeArr_rotate[13] = freezeArr[4];
        freezeArr_rotate[12] = freezeArr[5];
        freezeArr_rotate[11] = freezeArr[6];
        freezeArr_rotate[10] = freezeArr[7];
        freezeArr_rotate[9] = freezeArr[8];
        freezeArr_rotate[8] = freezeArr[9];
        freezeArr_rotate[7] = freezeArr[10];
        freezeArr_rotate[6] = freezeArr[11];
        freezeArr_rotate[5] = freezeArr[12];
        freezeArr_rotate[4] = freezeArr[13];
        freezeArr_rotate[3] = freezeArr[14];
        freezeArr_rotate[2] = freezeArr[15];
        freezeArr_rotate[1] = freezeArr[16];

        if(freezeArr_rotate[16] == 2){
            btn16.setBackgroundResource(R.drawable.yellow_ice1);
        }
        else if(freezeArr_rotate[16] == 1){
            btn16.setBackgroundResource(R.drawable.yellow_ice2);
        }
        else if(freezeArr_rotate[16] == 0){
            btn16.setBackgroundResource(R.drawable.yellow_spark0);
        }

        if(freezeArr_rotate[15] == 2){
            btn16.setBackgroundResource(R.drawable.orange_ice1);
        }
        else if(freezeArr_rotate[15] == 1){
            btn16.setBackgroundResource(R.drawable.orange_ice2);
        }
        else if(freezeArr_rotate[15] == 0){
            btn16.setBackgroundResource(R.drawable.orange_spark0);
        }

        if(freezeArr_rotate[14] == 2){
            btn16.setBackgroundResource(R.drawable.pink_ice1);
        }
        else if(freezeArr_rotate[14] == 1){
            btn16.setBackgroundResource(R.drawable.pink_ice2);
        }
        else if(freezeArr_rotate[14] == 0){
            btn16.setBackgroundResource(R.drawable.pink_spark0);
        }

        if(freezeArr_rotate[13] == 2){
            btn16.setBackgroundResource(R.drawable.green_ice1);
        }
        else if(freezeArr_rotate[13] == 1){
            btn16.setBackgroundResource(R.drawable.green_ice2);
        }
        else if(freezeArr_rotate[13] == 0){
            btn16.setBackgroundResource(R.drawable.green_spark0);
        }

        if(freezeArr_rotate[12] == 2){
            btn16.setBackgroundResource(R.drawable.green_ice1);
        }
        else if(freezeArr_rotate[12] == 1){
            btn16.setBackgroundResource(R.drawable.green_ice2);
        }
        else if(freezeArr_rotate[12] == 0){
            btn16.setBackgroundResource(R.drawable.green_spark0);
        }

        if(freezeArr_rotate[11] == 2){
            btn16.setBackgroundResource(R.drawable.yellow_ice1);
        }
        else if(freezeArr_rotate[11] == 1){
            btn16.setBackgroundResource(R.drawable.yellow_ice2);
        }
        else if(freezeArr_rotate[11] == 0){
            btn16.setBackgroundResource(R.drawable.yellow_spark0);
        }

        if(freezeArr_rotate[10] == 2){
            btn16.setBackgroundResource(R.drawable.orange_ice1);
        }
        else if(freezeArr_rotate[10] == 1){
            btn16.setBackgroundResource(R.drawable.orange_ice2);
        }
        else if(freezeArr_rotate[10] == 0){
            btn16.setBackgroundResource(R.drawable.orange_spark0);
        }

        if(freezeArr_rotate[9] == 2){
            btn16.setBackgroundResource(R.drawable.pink_ice1);
        }
        else if(freezeArr_rotate[9] == 1){
            btn16.setBackgroundResource(R.drawable.pink_ice2);
        }
        else if(freezeArr_rotate[9] == 0){
            btn16.setBackgroundResource(R.drawable.pink_spark0);
        }

        if(freezeArr_rotate[8] == 2){
            btn16.setBackgroundResource(R.drawable.pink_ice1);
        }
        else if(freezeArr_rotate[8] == 1){
            btn16.setBackgroundResource(R.drawable.pink_ice2);
        }
        else if(freezeArr_rotate[8] == 0){
            btn16.setBackgroundResource(R.drawable.pink_spark0);
        }

        if(freezeArr_rotate[7] == 2){
            btn16.setBackgroundResource(R.drawable.green_ice1);
        }
        else if(freezeArr_rotate[7] == 1){
            btn16.setBackgroundResource(R.drawable.green_ice2);
        }
        else if(freezeArr_rotate[7] == 0){
            btn16.setBackgroundResource(R.drawable.green_spark0);
        }

        if(freezeArr_rotate[6] == 2){
            btn16.setBackgroundResource(R.drawable.yellow_ice1);
        }
        else if(freezeArr_rotate[6] == 1){
            btn16.setBackgroundResource(R.drawable.yellow_ice2);
        }
        else if(freezeArr_rotate[6] == 0){
            btn16.setBackgroundResource(R.drawable.yellow_spark0);
        }

        if(freezeArr_rotate[5] == 2){
            btn16.setBackgroundResource(R.drawable.orange_ice1);
        }
        else if(freezeArr_rotate[5] == 1){
            btn16.setBackgroundResource(R.drawable.orange_ice2);
        }
        else if(freezeArr_rotate[5] == 0){
            btn16.setBackgroundResource(R.drawable.orange_spark0);
        }

        if(freezeArr_rotate[4] == 2){
            btn16.setBackgroundResource(R.drawable.orange_ice1);
        }
        else if(freezeArr_rotate[4] == 1){
            btn16.setBackgroundResource(R.drawable.orange_ice2);
        }
        else if(freezeArr_rotate[4] == 0){
            btn16.setBackgroundResource(R.drawable.orange_spark0);
        }

        if(freezeArr_rotate[3] == 2){
            btn16.setBackgroundResource(R.drawable.pink_ice1);
        }
        else if(freezeArr_rotate[3] == 1){
            btn16.setBackgroundResource(R.drawable.pink_ice2);
        }
        else if(freezeArr_rotate[3] == 0){
            btn16.setBackgroundResource(R.drawable.pink_spark0);
        }

        if(freezeArr_rotate[2] == 2){
            btn16.setBackgroundResource(R.drawable.green_ice1);
        }
        else if(freezeArr_rotate[2] == 1){
            btn16.setBackgroundResource(R.drawable.green_ice2);
        }
        else if(freezeArr_rotate[2] == 0){
            btn16.setBackgroundResource(R.drawable.green_spark0);
        }

        if(freezeArr_rotate[1] == 2){
            btn16.setBackgroundResource(R.drawable.yellow_ice1);
        }
        else if(freezeArr_rotate[1] == 1){
            btn16.setBackgroundResource(R.drawable.yellow_ice2);
        }
        else if(freezeArr_rotate[1] == 0){
            btn16.setBackgroundResource(R.drawable.yellow_spark0);
        }
    }

    private void startRotate1(){

        //Grid rotate
        LinearLayout grid = (LinearLayout) this.findViewById(R.id.mainGrid);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate180_firsthalf);
        grid.startAnimation(animation);

        //Button rotate
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(2500);
        rotateAnimation.setFillAfter(true);
        btn1.startAnimation(rotateAnimation);
        btn2.startAnimation(rotateAnimation);
        btn3.startAnimation(rotateAnimation);
        btn4.startAnimation(rotateAnimation);
        btn5.startAnimation(rotateAnimation);
        btn6.startAnimation(rotateAnimation);
        btn7.startAnimation(rotateAnimation);
        btn8.startAnimation(rotateAnimation);
        btn9.startAnimation(rotateAnimation);
        btn10.startAnimation(rotateAnimation);
        btn11.startAnimation(rotateAnimation);
        btn12.startAnimation(rotateAnimation);
        btn13.startAnimation(rotateAnimation);
        btn14.startAnimation(rotateAnimation);
        btn15.startAnimation(rotateAnimation);
        btn16.startAnimation(rotateAnimation);

        hasRotated = true;

        //Sound effect
        if(playMusic) {
            playSound(rotateId);
        }

        if(vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(2500);
        }

        Intent i = new Intent(this, Rotate.class);
        this.startActivity(i);
    }

    private void startRotate2(){

        //Grid rotate
        LinearLayout grid = (LinearLayout) this.findViewById(R.id.mainGrid);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate180_secondhalf);
        grid.startAnimation(animation);

        //Button rotate
        RotateAnimation rotateAnimation = new RotateAnimation(180f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(2500);
        rotateAnimation.setFillAfter(true);
        btn1.startAnimation(rotateAnimation);
        btn2.startAnimation(rotateAnimation);
        btn3.startAnimation(rotateAnimation);
        btn4.startAnimation(rotateAnimation);
        btn5.startAnimation(rotateAnimation);
        btn6.startAnimation(rotateAnimation);
        btn7.startAnimation(rotateAnimation);
        btn8.startAnimation(rotateAnimation);
        btn9.startAnimation(rotateAnimation);
        btn10.startAnimation(rotateAnimation);
        btn11.startAnimation(rotateAnimation);
        btn12.startAnimation(rotateAnimation);
        btn13.startAnimation(rotateAnimation);
        btn14.startAnimation(rotateAnimation);
        btn15.startAnimation(rotateAnimation);
        btn16.startAnimation(rotateAnimation);

        hasRotated = false;

        //Sound effect
        if(playMusic) {
            playSound(rotateId);
        }

        if(vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(2500);
        }

        Intent i = new Intent(this, Rotate.class);
        this.startActivity(i);
    }


    private void startChangeUpDigit(){
        Intent i = new Intent(this, SecondUpDigit.class);
        i.putExtra(SecondUpDigit.UPDIGIT, UP);
        i.putExtra(SecondUpDigit.UPDIGIT2, secondUP);
        this.startActivity(i);
    }

    private void startPause() {
        if (isApplicationSentToBackground(getApplicationContext())) {
            // Do what you want to do on detecting Home Key being Pressed
            cdt.pause();
            if(!hasChangedUp) {
                changeUpTimer.pause();
            }
            if(!hasRotated1) {
                rotateTimer1.pause();
            }
            if(!hasRotated2) {
                rotateTimer2.pause();
            }
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
            pause.putExtra(Pause.CALLEE, 4);
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
            if(!hasChangedUp) {
                changeUpTimer.pause();
            }
            if(!hasRotated1) {
                rotateTimer1.pause();
            }
            if(!hasRotated2) {
                rotateTimer2.pause();
            }
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
            pause.putExtra(Pause.CALLEE, 4);
        //    this.startActivity(pause);
        }

    }


    @Override
    public void onBackPressed() {
        cdt.pause();
        if(!hasChangedUp) {
            changeUpTimer.pause();
        }
        if(!hasRotated1) {
            rotateTimer1.pause();
        }
        if(!hasRotated2) {
            rotateTimer2.pause();
        }
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
        editor.putInt("score", score).commit();
        editor.putInt("bestScore", Math.max(score, gameDataHard.getInt("bestScore", -1))).commit();
        editor.putInt("life", life).commit();
        editor.putInt("current", current).commit();
        editor.putBoolean("hasRotated1", hasRotated1).commit();
        editor.putBoolean("hasRotated2", hasRotated2).commit();
        editor.putBoolean("hasChangedUp", hasChangedUp).commit();
        editor.putLong("timeLeft", cdt.timeLeft()).commit();
        if(!hasChangedUp) {
            editor.putLong("changeUpTimeLeft", changeUpTimer.timeLeft()).commit();
        }
        if (!hasRotated1) {
            editor.putLong("rotatetimeLeft1", rotateTimer1.timeLeft()).commit();
        }
        if (!hasRotated2) {
            editor.putLong("rotatetimeLeft2", rotateTimer2.timeLeft()).commit();
        }
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
        i.putExtra(Pause.CALLEE, 4);
        this.startActivity(i);
    }



    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);

        cdt.resume();
        if(!hasChangedUp) {
            changeUpTimer.resume();
        }
        if(!hasRotated1) {
            rotateTimer1.resume();
        }
        if(!hasRotated2) {
            rotateTimer2.resume();
        }
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
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        cdt.pause();
        if(!hasChangedUp) {
            changeUpTimer.pause();
        }
        if(!hasRotated1) {
            rotateTimer1.pause();
        }
        if(!hasRotated2) {
            rotateTimer2.pause();
        }
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
                AnimationDrawable anim = (AnimationDrawable)bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                //Sound effect
                if(playMusic) {
        //            up_sound.start();
                    playSound(upId);
                }

                if(vibrationOn) {
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
               editor.putInt("bestScore", Math.max(score, gameDataHard.getInt("bestScore", -1))).commit();
                editor.putInt("score", score).commit();
                GameOver(2);
                */

               //Background effect
                bg.setBackgroundResource(R.drawable.bomb);
                AnimationDrawable anim = (AnimationDrawable)bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                //Sound effect
                if(playMusic){
         //            explode_sound.start();
                    playSound(explodeId);
                }

                if(vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(1000);
                }

                life--;

                if (life == 2) {
                    life3.setVisibility(View.GONE);
                }
                else if(life==1){
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                }

                else if (life <= 0) {
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                    life1.setVisibility(View.GONE);

                    cdt.cancel();
                    changeUpTimer.cancel();
                    rotateTimer1.cancel();
                    rotateTimer2.cancel();
                    freezeTimer1.cancel();
                    freezeTimer2.cancel();
                    freezeTimer3.cancel();
                    freezeTimer4.cancel();
                    editor.putInt("bestScore", Math.max(score, gameDataHard.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(2);
                    return;
                }
                //Shake screen
              //  pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                if (life > 1) {
                    final Toast toast1 = Toast.makeText(MainHard4.this, "You have " + life + " more lives!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast1.show();
                        }

                        public void onFinish() {
                            toast1.cancel();
                        }
                    }.start();
                } else {
                    final Toast toast2 = Toast.makeText(MainHard4.this, "You have " + life + " more life!", Toast.LENGTH_SHORT);
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
                AnimationDrawable anim = (AnimationDrawable)pressedButton.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                //Sound effect
                if(playMusic) {
          //          tap_sound.start();
                    playSound(tapId);
                }

                if(vibrationOn) {
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
                AnimationDrawable anim = (AnimationDrawable)bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();

                //Sound effect
                if(playMusic) {
       //             explode_sound.start();
                    playSound(explodeId);
                }

                if(vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(1000);
                }

                life--;

                if (life == 2) {
                    life3.setVisibility(View.GONE);
                }
                else if(life==1){
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                }

                else if (life <= 0) {
                    life3.setVisibility(View.GONE);
                    life2.setVisibility(View.GONE);
                    life1.setVisibility(View.GONE);

                    cdt.cancel();
                    changeUpTimer.cancel();
                    rotateTimer1.cancel();
                    rotateTimer2.cancel();
                    freezeTimer1.cancel();
                    freezeTimer2.cancel();
                    freezeTimer3.cancel();
                    freezeTimer4.cancel();
                    editor.putInt("bestScore", Math.max(score, gameDataHard.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(3);
                    return;
                }
                //Shake screen
            //    pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                if (life > 1) {
                    final Toast toast1 = Toast.makeText(MainHard4.this, "You have " + life + " more lives!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000) {
                        public void onTick(long millisUntilFinished) {
                            toast1.show();
                        }

                        public void onFinish() {
                            toast1.cancel();
                        }
                    }.start();
                } else {
                    final Toast toast2 = Toast.makeText(MainHard4.this, "You have " + life + " more life!", Toast.LENGTH_SHORT);
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
        if(secondUP_start){
            return (current % UP == 0) ||(current % secondUP == 0) || checkDigit();
        }
        else {
            return (current % UP == 0) || checkDigit();
        }
    }

    public boolean checkDigit() {
        String cur = Integer.toString(current);
        String upString = Integer.toString(UP);

        if(secondUP_start){
            String upString2 = Integer.toString(secondUP);
            return cur.contains(upString) || cur.contains(upString2);
        }
        else{
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
    }

    public void GameOver(int r) {
        Intent i = new Intent(this, GameOver.class);
        i.putExtra(GameOver.REASON, r);
        i.putExtra(Pause.CALLEE, 4);
        this.startActivity(i);
    }


    protected void populateArr(int cur) {
        if (fillArr.isEmpty()) {
            for (int i = 0; i <= 9; i++) {
                fillArr.add(cur++);
            }
            Collections.shuffle(fillArr);
        }
    }

    protected void changeNo(Button btn) {
        if (fillArr.isEmpty()) {
            for (int i = current + 16; i <= current + 25; i++) {
                fillArr.add(i);
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
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        if(hasRotated){
            switch (view.getId())
            {
                case R.id.btn1:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        btn16.setBackgroundResource(R.drawable.yellow_pressed);
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        btn16.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    break;
                case R.id.btn2:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn15.setBackgroundResource(R.drawable.green_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn15.setBackgroundResource(R.drawable.green_spark0);
                    break;
                case R.id.btn3:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn14.setBackgroundResource(R.drawable.pink_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn14.setBackgroundResource(R.drawable.pink_spark0);
                    break;
                case R.id.btn4:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn13.setBackgroundResource(R.drawable.orange_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn13.setBackgroundResource(R.drawable.orange_spark0);
                    break;
                case R.id.btn5:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn12.setBackgroundResource(R.drawable.orange_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn12.setBackgroundResource(R.drawable.orange_spark0);
                    break;
                case R.id.btn6:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn11.setBackgroundResource(R.drawable.yellow_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn11.setBackgroundResource(R.drawable.yellow_spark0);
                    break;
                case R.id.btn7:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn10.setBackgroundResource(R.drawable.green_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn10.setBackgroundResource(R.drawable.green_spark0);
                    break;
                case R.id.btn8:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn9.setBackgroundResource(R.drawable.pink_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn9.setBackgroundResource(R.drawable.pink_spark0);
                    break;
                case R.id.btn9:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn8.setBackgroundResource(R.drawable.pink_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn8.setBackgroundResource(R.drawable.pink_spark0);
                    break;
                case R.id.btn10:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn7.setBackgroundResource(R.drawable.orange_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn7.setBackgroundResource(R.drawable.orange_spark0);
                    break;
                case R.id.btn11:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn6.setBackgroundResource(R.drawable.yellow_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn6.setBackgroundResource(R.drawable.yellow_spark0);
                    break;
                case R.id.btn12:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn5.setBackgroundResource(R.drawable.green_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn5.setBackgroundResource(R.drawable.green_spark0);
                    break;
                case R.id.btn13:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn4.setBackgroundResource(R.drawable.green_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn4.setBackgroundResource(R.drawable.green_spark0);
                    break;
                case R.id.btn14:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn3.setBackgroundResource(R.drawable.pink_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn3.setBackgroundResource(R.drawable.pink_spark0);
                    break;
                case R.id.btn15:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn2.setBackgroundResource(R.drawable.orange_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn2.setBackgroundResource(R.drawable.orange_spark0);
                    break;
                case R.id.btn16:
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn1.setBackgroundResource(R.drawable.yellow_pressed);
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                        btn1.setBackgroundResource(R.drawable.yellow_spark0);
                    break;
            }
        }
        else {
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
                        btn4.setBackgroundResource(R.drawable.green_btn_pressed);
                    break;
                case R.id.btn5:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn5.setBackgroundResource(R.drawable.green_btn_pressed);
                    break;
                case R.id.btn6:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn6.setBackgroundResource(R.drawable.yellow_btn_pressed);
                    break;
                case R.id.btn7:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn7.setBackgroundResource(R.drawable.orange_btn_pressed);
                    break;
                case R.id.btn8:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn8.setBackgroundResource(R.drawable.pink_btn_pressed);
                    break;
                case R.id.btn9:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn9.setBackgroundResource(R.drawable.pink_btn_pressed);
                    break;
                case R.id.btn10:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn10.setBackgroundResource(R.drawable.green_btn_pressed);
                    break;
                case R.id.btn11:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn11.setBackgroundResource(R.drawable.yellow_btn_pressed);
                    break;
                case R.id.btn12:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn12.setBackgroundResource(R.drawable.orange_btn_pressed);
                    break;
                case R.id.btn13:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn13.setBackgroundResource(R.drawable.orange_btn_pressed);
                    break;
                case R.id.btn14:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn14.setBackgroundResource(R.drawable.pink_btn_pressed);
                    break;
                case R.id.btn15:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn15.setBackgroundResource(R.drawable.green_btn_pressed);
                    break;
                case R.id.btn16:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                        btn16.setBackgroundResource(R.drawable.yellow_btn_pressed);
                    break;
            }
        }
        return false;
    }

    private void freezeEffect(){
        if(playMusic) {
            playSound(unfreezeId);
        }

        if(vibrationOn) {
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(100);
        }
    }

    public void onClick(View v) {

        Button temp;

        if(hasRotated){
            switch (v.getId()) {

                case R.id.pause_btn:
                    cdt.pause();
                    if(!hasChangedUp) {
                        changeUpTimer.pause();
                    }
                    if(!hasRotated1) {
                        rotateTimer1.pause();
                    }
                    if(!hasRotated2) {
                        rotateTimer2.pause();
                    }
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
                    editor.putInt("bestScore", Math.max(score, gameDataHard.getInt("bestScore", -1))).commit();
                    editor.putInt("life", life).commit();
                    editor.putInt("current", current).commit();
                    editor.putBoolean("hasRotated1", hasRotated1).commit();
                    editor.putBoolean("hasRotated2", hasRotated2).commit();
                    editor.putBoolean("hasChangedUp", hasChangedUp).commit();
                    editor.putLong("timeLeft", cdt.timeLeft()).commit();
                    if(!hasChangedUp) {
                        editor.putLong("changeUpTimeLeft", changeUpTimer.timeLeft()).commit();
                    }
                    if (!hasRotated1) {
                        editor.putLong("rotatetimeLeft1", rotateTimer1.timeLeft()).commit();
                    }
                    if (!hasRotated2) {
                        editor.putLong("rotatetimeLeft2", rotateTimer2.timeLeft()).commit();
                    }
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
                    i.putExtra(Pause.CALLEE, 4);
                    this.startActivity(i);
                    break;
                case R.id.up_button:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedID = temp.getId();
                    Game();
                    break;
                case R.id.btn1:
                    temp = btn16;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[16] == 2){
                        freezeArr[16] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[16] == 1){
                        freezeArr[16] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
                case R.id.btn2:
                    temp = btn15;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[15] == 2){
                        freezeArr[15] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[15] == 1){
                        freezeArr[15] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn3:
                    temp = btn14;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[14] == 2){
                        freezeArr[14] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[14] == 1){
                        freezeArr[14] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn4:
                    temp = btn13;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[13] == 2){
                        freezeArr[13] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[13] == 1){
                        freezeArr[13] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn5:
                    temp = btn12;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[12] == 2){
                        freezeArr[12] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[12] == 1){
                        freezeArr[12] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn6:
                    temp = btn11;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[11] == 2){
                        freezeArr[11] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[11] == 1){
                        freezeArr[11] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
                case R.id.btn7:
                    temp = btn10;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[10] == 2){
                        freezeArr[10] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[10] == 1){
                        freezeArr[10] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn8:
                    temp = btn9;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[9] == 2){
                        freezeArr[9] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[9] == 1){
                        freezeArr[9] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn9:
                    temp = btn8;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[8] == 2){
                        freezeArr[8] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[8] == 1){
                        freezeArr[8] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn10:
                    temp = btn7;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[7] == 2){
                        freezeArr[7] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[7] == 1){
                        freezeArr[7] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn11:
                    temp = btn6;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[6] == 2){
                        freezeArr[6] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[6] == 1){
                        freezeArr[6] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
                case R.id.btn12:
                    temp = btn5;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[5] == 2){
                        freezeArr[5] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[5] == 1){
                        freezeArr[5] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn13:
                    temp = btn4;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[4] == 2){
                        freezeArr[4] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[4] == 1){
                        freezeArr[4] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn14:
                    temp = btn3;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[3] == 2){
                        freezeArr[3] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[3] == 1){
                        freezeArr[3] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn15:
                    temp = btn2;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[2] == 2){
                        freezeArr[2] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[2] == 1){
                        freezeArr[2] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn16:
                    temp = btn1;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[1] == 2){
                        freezeArr[1] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[1] == 1){
                        freezeArr[1] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
            }
        }
        else {
            switch (v.getId()) {
                case R.id.pause_btn:
                    cdt.pause();
                    if(!hasChangedUp) {
                        changeUpTimer.pause();
                    }
                    if(!hasRotated1) {
                        rotateTimer1.pause();
                    }
                    if(!hasRotated2) {
                        rotateTimer2.pause();
                    }
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
                    editor.putInt("bestScore", Math.max(score, gameDataHard.getInt("bestScore", -1))).commit();
                    editor.putInt("life", life).commit();
                    editor.putInt("current", current).commit();
                    editor.putBoolean("hasRotated1", hasRotated1).commit();
                    editor.putBoolean("hasRotated2", hasRotated2).commit();
                    editor.putBoolean("hasChangedUp", hasChangedUp).commit();
                    editor.putLong("timeLeft", cdt.timeLeft()).commit();
                    if(!hasChangedUp) {
                        editor.putLong("changeUpTimeLeft", changeUpTimer.timeLeft()).commit();
                    }
                    if (!hasRotated1) {
                        editor.putLong("rotatetimeLeft1", rotateTimer1.timeLeft()).commit();
                    }
                    if (!hasRotated2) {
                        editor.putLong("rotatetimeLeft2", rotateTimer2.timeLeft()).commit();
                    }
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
                    i.putExtra(Pause.CALLEE, 4);
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

                    if(freezeArr[1] == 2){
                        freezeArr[1] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[1] == 1){
                        freezeArr[1] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
                case R.id.btn2:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[2] == 2){
                        freezeArr[2] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[2] == 1){
                        freezeArr[2] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn3:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[3] == 2){
                        freezeArr[3] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[3] == 1){
                        freezeArr[3] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn4:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[4] == 2){
                        freezeArr[4] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[4] == 1){
                        freezeArr[4] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn5:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[5] == 2){
                        freezeArr[5] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[5] == 1){
                        freezeArr[5] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn6:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[6] == 2){
                        freezeArr[6] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[6] == 1){
                        freezeArr[6] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
                case R.id.btn7:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[7] == 2){
                        freezeArr[7] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[7] == 1){
                        freezeArr[7] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn8:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[8] == 2){
                        freezeArr[8] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[8] == 1){
                        freezeArr[8] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn9:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[9] == 2){
                        freezeArr[9] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[9] == 1){
                        freezeArr[9] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn10:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[10] == 2){
                        freezeArr[10] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[10] == 1){
                        freezeArr[10] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn11:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[11] == 2){
                        freezeArr[11] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[11] == 1){
                        freezeArr[11] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
                case R.id.btn12:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[12] == 2){
                        freezeArr[12] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[12] == 1){
                        freezeArr[12] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn13:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[13] == 2){
                        freezeArr[13] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_ice2);
                    }
                    else if(freezeArr[13] == 1){
                        freezeArr[13] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.orange_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.orange_spark);
                        Game();
                    }
                    break;
                case R.id.btn14:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[14] == 2){
                        freezeArr[14] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_ice2);
                    }
                    else if(freezeArr[14] == 1){
                        freezeArr[14] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.pink_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.pink_spark);
                        Game();
                    }
                    break;
                case R.id.btn15:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[15] == 2){
                        freezeArr[15] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_ice2);
                    }
                    else if(freezeArr[15] == 1){
                        freezeArr[15] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.green_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.green_spark);
                        Game();
                    }
                    break;
                case R.id.btn16:
                    temp = (Button) v;
                    pressedButton = temp;
                    pressedNum = Integer.valueOf(temp.getText().toString());
                    pressedID = temp.getId();

                    if(freezeArr[16] == 2){
                        freezeArr[16] = 1;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_ice2);
                    }
                    else if(freezeArr[16] == 1){
                        freezeArr[16] = 0;
                        freezeEffect();
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark0);
                    }
                    else{
                        pressedButton.setBackgroundResource(R.drawable.yellow_spark);
                        Game();
                    }
                    break;
            }
        }
    }
}