package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


import static com.example.btw.whatsup.R.id.btn1;
import static com.example.btw.whatsup.R.id.countdown;

/**
 * Created by BTW on 5/24/2017.
 */

public class Main extends Activity implements OnClickListener {

    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPDIGIT_DEFAULT = 7;
    private ArrayList<Integer> fillArr = new ArrayList<Integer>();  //arraylist that contains new numbers to replace tapped numbers
    private int UP;
    private int pressedNum;
    private int pressedID;
    private Button pressedButton;
    private int score;
    private int life;
    private int current;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //initialise the grid
        initialiseGrid();
        //populate fillArr
        changeNo(26);
        //initialise UP
        UP = getIntent().getIntExtra(UPDIGIT, UPDIGIT_DEFAULT);
        score = 0;
        life = 3;
        current = 1;

        //321 animation
        Intent intent= new Intent(this,Onetwothree.class);
        intent.putExtra(Onetwothree.UPDIGIT,UP);
        startActivity(intent);


        //set onClickListeners for all buttons
        View upBtn = this.findViewById(R.id.up_button);
        upBtn.setOnClickListener(this);
        View btn1 = this.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        View btn2 = this.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        View btn3 = this.findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        View btn4 = this.findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        View btn5 = this.findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        View btn6 = this.findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        View btn7 = this.findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        View btn8 = this.findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        View btn9 = this.findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        View btn10 = this.findViewById(R.id.btn10);
        btn10.setOnClickListener(this);
        View btn11 = this.findViewById(R.id.btn11);
        btn11.setOnClickListener(this);
        View btn12 = this.findViewById(R.id.btn12);
        btn12.setOnClickListener(this);
        View btn13 = this.findViewById(R.id.btn13);
        btn13.setOnClickListener(this);
        View btn14 = this.findViewById(R.id.btn14);
        btn14.setOnClickListener(this);
        View btn15 = this.findViewById(R.id.btn15);
        btn15.setOnClickListener(this);
        View btn16 = this.findViewById(R.id.btn16);
        btn16.setOnClickListener(this);
        View btn17 = this.findViewById(R.id.btn17);
        btn17.setOnClickListener(this);
        View btn18 = this.findViewById(R.id.btn18);
        btn18.setOnClickListener(this);
        View btn19 = this.findViewById(R.id.btn19);
        btn19.setOnClickListener(this);
        View btn20 = this.findViewById(R.id.btn20);
        btn20.setOnClickListener(this);
        View btn21 = this.findViewById(R.id.btn21);
        btn21.setOnClickListener(this);
        View btn22 = this.findViewById(R.id.btn22);
        btn22.setOnClickListener(this);
        View btn23 = this.findViewById(R.id.btn23);
        btn23.setOnClickListener(this);
        View btn24 = this.findViewById(R.id.btn24);
        btn24.setOnClickListener(this);
        View btn25 = this.findViewById(R.id.btn25);
        btn25.setOnClickListener(this);

        TextView v = (TextView) findViewById(R.id.countdown);
        v.setText("" + String.format("%02d : %02d", 2, 0));

        final Handler handler = new Handler();
        final Runnable counter = new Runnable() {
            @Override
            public void run() {
                new CountDownTimer(119000, 1000) {
                    TextView v = (TextView) findViewById(R.id.countdown);

                    public void onTick(long millisUntilFinished) {
                        v.setText("" + String.format("%02d : %02d",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    }

                    public void onFinish() {
                        TimesUp();
                    }
                }.start();

                }
        };
        handler.postDelayed(counter, 5000);
    }


    public void Game() {

        boolean isUp;
        isUp = checkUp();
        if (isUp) {   //UP num
            if (pressedID == R.id.up_button) {  //Correct
                score += current;
                findCurrent();

                TextView scoreBoard = (TextView) findViewById(R.id.currentScore);
                scoreBoard.setText(score + "");
                current++;
            } else {                            //Wrong
                GameOver();
            }
        } else {   //Not UP num
            if (pressedNum == current) {      //Correct
                score += pressedNum;
                changeNo(pressedButton);

                TextView scoreBoard = (TextView) findViewById(R.id.currentScore);
                scoreBoard.setText(score + "");
                current++;
            } else {                            //Wrong
                //Shake screen
                life--;
                if (life <= 0)
                    GameOver();
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

    public void GameOver() {
        Intent i = new Intent(this, GameOver.class);
        this.startActivity(i);
    }

    public void TimesUp() {
        Intent i = new Intent(this, TimesUp.class);
        this.startActivity(i);
    }

    public void timerAnimation() {

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
    protected void initialiseGrid() {
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = 1; i <= 25; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);

        Button p1_button = (Button) findViewById(btn1);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn2);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn3);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn4);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn5);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn6);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn7);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn8);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn9);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn10);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn11);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn12);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn13);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn14);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn15);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn16);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn17);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn18);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn19);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn20);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn21);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn22);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn23);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn24);
        p1_button.setText(arr.remove(0) + "");

        p1_button = (Button) findViewById(R.id.btn25);
        p1_button.setText(arr.remove(0) + "");

    }

    public void onClick(View v) {
        Button temp;
        switch (v.getId()) {
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
