package com.example.btw.whatsup;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


import static com.example.btw.whatsup.R.id.UPDisplayText;
import static com.example.btw.whatsup.R.id.btn1;
import static com.example.btw.whatsup.R.id.countdown;
import static com.example.btw.whatsup.R.id.time;

/**
 * Created by BTW on 5/24/2017.
 */


public class Main5 extends Activity implements OnClickListener {

    private boolean continueFromLast;
    protected SharedPreferences gameData;
    protected SharedPreferences.Editor editor;

    public static final String UPDIGIT = "UPDIGIT";
    private int UP;
    private long timeLeft;
    private int score;
    //  private int bestScore;
    private int current;
    private int life;
    private ArrayList<Integer> fillArr = new ArrayList<Integer>();  //arraylist that contains new numbers to replace tapped numbers
    private String fillArrContent = "";
    private int pressedNum;
    private int pressedID;
    private Button pressedButton;
    public CountDownTimerPausable cdt;


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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        upBtn = (Button) this.findViewById(R.id.up_button);
        btn1 = (Button) this.findViewById(R.id.btn1);
        btn2 = (Button) this.findViewById(R.id.btn2);
        btn3 = (Button) this.findViewById(R.id.btn3);
        btn4 = (Button) this.findViewById(R.id.btn4);
        btn5 = (Button) this.findViewById(R.id.btn5);
        btn6 = (Button) this.findViewById(R.id.btn6);
        btn7 = (Button) this.findViewById(R.id.btn7);
        btn8 = (Button) this.findViewById(R.id.btn8);
        btn9 = (Button) this.findViewById(R.id.btn9);
        btn10 = (Button) this.findViewById(R.id.btn10);
        btn11 = (Button) this.findViewById(R.id.btn11);
        btn12 = (Button) this.findViewById(R.id.btn12);
        btn13 = (Button) this.findViewById(R.id.btn13);
        btn14 = (Button) this.findViewById(R.id.btn14);
        btn15 = (Button) this.findViewById(R.id.btn15);
        btn16 = (Button) this.findViewById(R.id.btn16);
        btn17 = (Button) this.findViewById(R.id.btn17);
        btn18 = (Button) this.findViewById(R.id.btn18);
        btn19 = (Button) this.findViewById(R.id.btn19);
        btn20 = (Button) this.findViewById(R.id.btn20);
        btn21 = (Button) this.findViewById(R.id.btn21);
        btn22 = (Button) this.findViewById(R.id.btn22);
        btn23 = (Button) this.findViewById(R.id.btn23);
        btn24 = (Button) this.findViewById(R.id.btn24);
        btn25 = (Button) this.findViewById(R.id.btn25);

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
            fillArrContent = gameData.getString("FILLARR_CONTENT", "");
            String[] strArray = fillArrContent.split(",");
            fillArr.clear();
            for (int i = 0; i < strArray.length; i++) {
                fillArr.add(0, Integer.parseInt(strArray[i]));
            }
            TextView t;
            t = (TextView) findViewById(R.id.btn1);
            t.setText(gameData.getString("btn1", ""));
            t = (TextView) findViewById(R.id.btn2);
            t.setText(gameData.getString("btn2", ""));
            t = (TextView) findViewById(R.id.btn3);
            t.setText(gameData.getString("btn3", ""));
            t = (TextView) findViewById(R.id.btn4);
            t.setText(gameData.getString("btn4", ""));
            t = (TextView) findViewById(R.id.btn5);
            t.setText(gameData.getString("btn5", ""));
            t = (TextView) findViewById(R.id.btn6);
            t.setText(gameData.getString("btn6", ""));
            t = (TextView) findViewById(R.id.btn7);
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
            t.setText(gameData.getString("btn12", ""));
            t = (TextView) findViewById(R.id.btn13);
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
            t.setText(gameData.getString("btn18", ""));
            t = (TextView) findViewById(R.id.btn19);
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
            t.setText(gameData.getString("btn24", ""));
            t = (TextView) findViewById(R.id.btn25);
            t.setText(gameData.getString("btn25", ""));
            populateArr(current + 25);
        } else {
            //   Log.d("Debug", "cotinue from last=false");
            UP = getIntent().getIntExtra("UPDIGIT", 1);
            score = 0;
            life = 3;
            current = 1;
            timeLeft = 120000;
            initialiseGrid(1);
            populateArr(26);
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



        /* TODO: Back press from 321 not working!!! :((((((
        final AtomicInteger n = new AtomicInteger(15);
        final Handler handler3 = new Handler();
        //   final TextView textView = (TextView) findViewById(R.id.currentScore);
        // final AtomicInteger n = new AtomicInteger(3);
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
                    //         TextView v = (TextView) findViewById(R.id.currentScore);
                    //       v.setText(Integer.toString(test));
                    handler3.postDelayed(this, 500);
                }
                else if(n.get() > 0){
                    n.getAndDecrement();
                    //     TextView v = (TextView) findViewById(R.id.currentScore);
                    //   v.setText(Integer.toString(n.get()));
                    handler3.postDelayed(this, 500);
                }
            }
        };
        handler3.postDelayed(check, 1);
    }
    */

    }

    private void startPause() {
        if (isApplicationSentToBackground(getApplicationContext())) {
            // Do what you want to do on detecting Home Key being Pressed
            cdt.pause();
            Intent pause = new Intent(this, Pause.class);
            pause.putExtra(Pause.UPDIGIT, UP);
            pause.putExtra(Pause.SCORE, score);
            pause.putExtra(Pause.TIME, cdt.timeLeft());
            pause.putExtra(Pause.ONETWOTHREE_PAUSED, true);
            this.startActivity(pause); //hereeee

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

        if (isApplicationSentToBackground(this)) {
            // Do what you want to do on detecting Home Key being Pressed
            cdt.pause();
            Intent i = new Intent(this, Pause.class);
            //i.putExtra(Pause.UPDIGIT, UP);
            //i.putExtra(Pause.TIME, cdt.timeLeft());
            this.startActivity(i);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        cdt.resume();

        ImageButton pause = (ImageButton) findViewById(R.id.pause_btn);
        pause.setImageResource(R.drawable.pause_button);
        pause.setOnClickListener(this);

        //set onClickListeners for all buttons

        upBtn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn15.setOnClickListener(this);
        btn16.setOnClickListener(this);
        btn17.setOnClickListener(this);
        btn18.setOnClickListener(this);
        btn19.setOnClickListener(this);
        btn20.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);
        btn24.setOnClickListener(this);
        btn25.setOnClickListener(this);
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

    public void Game() {

        boolean isUp;
        isUp = checkUp();
        if (isUp) {   //UP num
            if (pressedID == R.id.up_button) {  //Correct
                score += current;
                findCurrent();
                currentScore.setText(score + "");
                current++;
                currentNumText.setText(current + "");
            } else {//Wrong
                cdt.cancel();
                editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                editor.putInt("score", score).commit();
                GameOver(2);
            }
        } else {   //Not UP num
            if (pressedNum == current) {      //Correct
                score += pressedNum;
                changeNo(pressedButton);
                currentScore.setText(score + "");
                current++;
                currentNumText.setText(current + "");
            } else {                            //Wrong


                life--;
                if (life <= 0) {
                    cdt.cancel();
                    editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(3);
                    return;
                }
                //Shake screen
                pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                if (life > 1) {
                    final Toast toast1=Toast.makeText(Main5.this, "You have " + life + " more lives!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000)
                    {
                        public void onTick(long millisUntilFinished) {toast1.show();}
                        public void onFinish() {toast1.cancel();}
                    }.start();
                } else {
                    final Toast toast2=Toast.makeText(Main5.this, "You have " + life + " more life!", Toast.LENGTH_SHORT);
                    new CountDownTimer(1200, 1000)
                    {
                        public void onTick(long millisUntilFinished) {toast2.show();}
                        public void onFinish() {toast2.cancel();}
                    }.start();
                }
            }
        }
    }


    public boolean checkUp() {
        return (current % UP == 0) || checkDigit();
    }

    public boolean checkDigit() {
        String cur = Integer.toString(current);
        String upString = Integer.toString(UP);

        return cur.contains(upString);
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
    }

    public void GameOver(int r) {
        Intent i = new Intent(this, GameOver.class);
        i.putExtra(GameOver.REASON, r);
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
            for (int i = current + 25; i <= current + 34; i++) {
                fillArr.add(i);
            }
            Collections.shuffle(fillArr);
        }
        btn.setText(fillArr.remove(0) + "");
    }

    //fill grid with numbers from start to start+24 in random order
    protected void initialiseGrid(int start) {
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = start; i < start + 25; i++) {
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
                editor.putInt("UPdigit", UP).commit();
                editor.putInt("score", score).commit();
                editor.putInt("bestScore", Math.max(score, gameData.getInt("bestScore", -1))).commit();
                editor.putInt("life", life).commit();
                editor.putInt("current", current).commit();
                editor.putLong("timeLeft", cdt.timeLeft()).commit();
                for (Integer i : fillArr) {
                    fillArrContent += i + ",";
                }
                editor.putString("FILLARR_CONTENT", fillArrContent).commit();
                Intent i = new Intent(this, Pause.class);
                i.putExtra(Pause.UPDIGIT, UP);
                i.putExtra(Pause.TIME, cdt.timeLeft());
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
                Game();
                break;
            case R.id.btn2:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn3:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn4:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn5:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn6:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn7:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn8:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn9:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn10:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn11:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn12:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn13:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn14:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn15:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn16:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn17:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn18:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn19:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn20:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn21:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn22:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn23:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn24:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn25:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                Game();
                break;
        }
    }
}

