package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.btw.whatsup.R.id.btn1;

/**
 * Created by BTW on 5/24/2017.
 */

public class Onetwothree extends Activity {
    public static final String UPDIGIT = "UPDIGIT";
    public static final int UPDIGIT_DEFAULT = 7;
    int UP;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onetwothree);
        UP = getIntent().getIntExtra(UPDIGIT, UPDIGIT_DEFAULT);
        final Handler handler = new Handler();
        final TextView textView = (TextView) findViewById(R.id.textView123);
        final AtomicInteger n = new AtomicInteger(3);
        final Runnable counter = new Runnable() {
            @Override
            public void run() {

                textView.setText(Integer.toString(n.get()));
                if (n.getAndDecrement() >=1)
                    handler.postDelayed(this, 1000);
                else {
                    TextView v= (TextView) findViewById(R.id.textView123);
                    v.setText("Start!");
                    final Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                           // startGame();
                        }
                    }, 500);
                }
            }
        };
        handler.postDelayed(counter, 1000);
    }

    protected void startGame(){

        Intent intent= new Intent(this,Main.class);
        intent.putExtra(Main.UPDIGIT,UP);
        finish();
        startActivity(intent);

    }

}