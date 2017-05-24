package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.btw.whatsup.R.id.btn1;

/**
 * Created by BTW on 5/24/2017.
 */

public class Main extends Activity implements OnClickListener {

    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPDIGIT_DEFAULT = 7;
    private ArrayList<Integer> fillArr = new ArrayList<Integer>();
    private int pressedNum;
    private int pressedID;
    private boolean notClicked = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //initialise the grid
        initialiseGrid();
        //populate the fillArr
        changeNo(26);
        //start 321 timer
        int digit = getIntent().getIntExtra(UPDIGIT, UPDIGIT_DEFAULT);
        game()//start the game

    }

    public void onClick(View v) {
        Button temp;
        switch (v.getId()) {
            case R.id.btn1:
                temp = (Button) v;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                notClicked = false;
                break;
//copy 25 times!
        }
    }

    public void game(){
        

    }


        protected void changeNo ( int cur){
            if (fillArr.isEmpty()) {
                for (int i = 0; i <= 9; i++) {
                    fillArr.add(cur++);
                }
                Collections.shuffle(fillArr);
            }
        }


        protected void changeNo ( int cur, Button btn){
            if (fillArr.isEmpty()) {
                for (int i = 0; i <= 9; i++) {
                    fillArr.add(cur++);
                }
                Collections.shuffle(fillArr);
            }
            btn.setText(fillArr.remove(0) + "");
        }

        protected void initialiseGrid () {
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

    }
