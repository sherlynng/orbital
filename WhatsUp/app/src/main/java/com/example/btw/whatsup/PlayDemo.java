package com.example.btw.whatsup;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
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


import static com.example.btw.whatsup.R.id.btn1;
import static com.example.btw.whatsup.R.id.countdown;
import static com.example.btw.whatsup.R.id.instruct_display;
import static com.example.btw.whatsup.R.id.time;

/**
 * Created by BTW on 5/24/2017.
 */

public class PlayDemo extends Activity implements OnClickListener {
    private int UP;

    // private long timeLeft;
    private int score;
    private int bestScore;
    private int current;
    private int life;


    private ArrayList<Integer> fillArr = new ArrayList<Integer>();  //arraylist that contains new numbers to replace tapped numbers
    private int pressedNum;
    private int pressedID;
    private Button pressedButton;


    protected TextView currentScore;
    protected TextView instruct;

    protected Button upBtn;
    protected Button backToHomeBtn;
    protected Button retrieveInstBtn;
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
        setContentView(R.layout.playdemo);

        UP = 4;
        score = 0;
        life = 3;
        current = 1;

        instruct = (TextView) findViewById(R.id.instruct_display);

        upBtn = (Button) this.findViewById(R.id.up_button);
        retrieveInstBtn= (Button) this.findViewById(R.id.retrieve_inst_btn);
        backToHomeBtn= (Button) this.findViewById(R.id.back_to_home_btn);
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

        initialiseGrid(1);
        changeNo(26);

        final Handler handler2 = new Handler();
        final Runnable counter2 = new Runnable() {
            @Override
            public void run() {
                backToStartMenu();
            }
        };
        handler2.postDelayed(counter2, 5001);



        AlertDialog.Builder builder1 = new AlertDialog.Builder(PlayDemo.this);
        builder1.setMessage(R.string.PlayDemo_message1)
                .setTitle(R.string.PlayDemo_title);
        builder1.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(PlayDemo.this);
                builder2.setMessage(R.string.PlayDemo_message2);
                builder2.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(PlayDemo.this);
                        builder3.setMessage(R.string.PlayDemo_message3);
                        builder3.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                AlertDialog.Builder builder4 = new AlertDialog.Builder(PlayDemo.this);
                                builder4.setMessage(R.string.PlayDemo_message4);
                                builder4.setPositiveButton(R.string.start, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                }).setCancelable(false);
                                AlertDialog dialog4 = builder4.create();
                                dialog4.show();
                            }
                        }).setCancelable(false);
                        AlertDialog dialog3 = builder3.create();
                        dialog3.show();
                    }
                }).setCancelable(false);
                AlertDialog dialog2 = builder2.create();
                dialog2.show();
            }
        }).setCancelable(false);
        AlertDialog dialog1 = builder1.create();
        dialog1.show();
    }


    private void backToStartMenu() {
        if (isApplicationSentToBackground(getApplicationContext())) {
            Intent backToStartMenu = new Intent(this, WhatsUp.class);
            this.startActivity(backToStartMenu); //hereeee
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
            Intent i = new Intent(this, Pause.class);
            //i.putExtra(Pause.UPDIGIT, UP);
            //i.putExtra(Pause.TIME, cdt.timeLeft());
            this.startActivity(i);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // cdt.resume();
/*
      ImageButton backToStartMenu = (ImageButton) findViewById(R.id.back_to_home_btn);
       backToStartMenu.setImageResource(R.drawable.pause_button);
        backToStartMenu.setOnClickListener(this);
        retrieveInstBtn.setImageResource(R.drawable.pause_button);
        retrieveInstBtn.setOnClickListener(this);
*/
        backToHomeBtn.setOnClickListener(this);
        retrieveInstBtn.setOnClickListener(this);
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
                //score += current;
                findCurrent();
                // currentScore.setText(score + "");
                current++;
                instruct.setText("Correct!");
                instruct.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_mild));
            } else {//Wrong
                pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                instruct.setText("Wrong!Press the UP button");
                instruct.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_mild));
            }
        } else {   //Not UP num
            if (pressedNum == current) {      //Correct
                // score += pressedNum;
                changeNo(pressedButton);
                // currentScore.setText(score + "");
                current++;
                instruct.setText("Correct!");
                instruct.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_mild));
            } else {      //Wrong
                pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
                instruct.setText("Press the numbers in order!\nGet it wrong three times and you are OUT!");
                instruct.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_mild));
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
        Button btn1 = (Button) findViewById(R.id.btn1);
        if (Integer.parseInt(btn1.getText().toString()) == current) {
            changeNo(btn1);
            return;
        }
        Button btn2 = (Button) findViewById(R.id.btn2);
        if (Integer.parseInt(btn2.getText().toString()) == current) {
            changeNo(btn2);
            return;
        }
        Button btn3 = (Button) findViewById(R.id.btn3);
        if (Integer.parseInt(btn3.getText().toString()) == current) {
            changeNo(btn3);
            return;
        }
        Button btn4 = (Button) findViewById(R.id.btn4);
        if (Integer.parseInt(btn4.getText().toString()) == current) {
            changeNo(btn4);
            return;
        }
        Button btn5 = (Button) findViewById(R.id.btn5);
        if (Integer.parseInt(btn5.getText().toString()) == current) {
            changeNo(btn5);
            return;
        }
        Button btn6 = (Button) findViewById(R.id.btn6);
        if (Integer.parseInt(btn6.getText().toString()) == current) {
            changeNo(btn6);
            return;
        }
        Button btn7 = (Button) findViewById(R.id.btn7);
        if (Integer.parseInt(btn7.getText().toString()) == current) {
            changeNo(btn7);
            return;
        }
        Button btn8 = (Button) findViewById(R.id.btn8);
        if (Integer.parseInt(btn8.getText().toString()) == current) {
            changeNo(btn8);
            return;
        }
        Button btn9 = (Button) findViewById(R.id.btn9);
        if (Integer.parseInt(btn9.getText().toString()) == current) {
            changeNo(btn9);
            return;
        }
        Button btn10 = (Button) findViewById(R.id.btn10);
        if (Integer.parseInt(btn10.getText().toString()) == current) {
            changeNo(btn10);
            return;
        }
        Button btn11 = (Button) findViewById(R.id.btn11);
        if (Integer.parseInt(btn11.getText().toString()) == current) {
            changeNo(btn11);
            return;
        }
        Button btn12 = (Button) findViewById(R.id.btn12);
        if (Integer.parseInt(btn12.getText().toString()) == current) {
            changeNo(btn12);
            return;
        }
        Button btn13 = (Button) findViewById(R.id.btn13);
        if (Integer.parseInt(btn13.getText().toString()) == current) {
            changeNo(btn13);
            return;
        }
        Button btn14 = (Button) findViewById(R.id.btn14);
        if (Integer.parseInt(btn14.getText().toString()) == current) {
            changeNo(btn14);
            return;
        }
        Button btn15 = (Button) findViewById(R.id.btn15);
        if (Integer.parseInt(btn15.getText().toString()) == current) {
            changeNo(btn15);
            return;
        }
        Button btn16 = (Button) findViewById(R.id.btn16);
        if (Integer.parseInt(btn16.getText().toString()) == current) {
            changeNo(btn16);
            return;
        }
        Button btn17 = (Button) findViewById(R.id.btn17);
        if (Integer.parseInt(btn17.getText().toString()) == current) {
            changeNo(btn17);
            return;
        }
        Button btn18 = (Button) findViewById(R.id.btn18);
        if (Integer.parseInt(btn18.getText().toString()) == current) {
            changeNo(btn18);
            return;
        }
        Button btn19 = (Button) findViewById(R.id.btn19);
        if (Integer.parseInt(btn19.getText().toString()) == current) {
            changeNo(btn19);
            return;
        }
        Button btn20 = (Button) findViewById(R.id.btn20);
        if (Integer.parseInt(btn20.getText().toString()) == current) {
            changeNo(btn20);
            return;
        }
        Button btn21 = (Button) findViewById(R.id.btn21);
        if (Integer.parseInt(btn21.getText().toString()) == current) {
            changeNo(btn21);
            return;
        }
        Button btn22 = (Button) findViewById(R.id.btn22);
        if (Integer.parseInt(btn22.getText().toString()) == current) {
            changeNo(btn22);
            return;
        }
        Button btn23 = (Button) findViewById(R.id.btn23);
        if (Integer.parseInt(btn23.getText().toString()) == current) {
            changeNo(btn23);
            return;
        }
        Button btn24 = (Button) findViewById(R.id.btn24);
        if (Integer.parseInt(btn24.getText().toString()) == current) {
            changeNo(btn24);
            return;
        }
        Button btn25 = (Button) findViewById(R.id.btn25);
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


    protected void changeNo(int cur) {
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

    //fill grid with numbers from 1 to 25 in random order
    protected void initialiseGrid(int start) {
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = start; i <= start + 25; i++) {
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
            case R.id.back_to_home_btn:
                Intent i = new Intent(this, WhatsUp.class);
                this.startActivity(i);
                break;
            case R.id.retrieve_inst_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(PlayDemo.this);
                builder.setMessage(R.string.PlayDemo_messageFull);
                builder.setPositiveButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
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
