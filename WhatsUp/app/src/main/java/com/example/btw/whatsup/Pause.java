package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.os.Process;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by BTW on 5/24/2017.
 */

public class Pause extends Activity implements View.OnClickListener {

    public static final String UPD = "UPDIGIT";
    public static final int UPD_DEFAULT = 7;
    private int UP;
    public static final String TIME = "TIME";
    private long timeLeft;
    public static final String ONETWOTHREE_PAUSED = "PAUSED_123";
    private boolean paused_123;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause);

        UP = getIntent().getIntExtra(UPD, UPD_DEFAULT);
        timeLeft = getIntent().getLongExtra(TIME, 123);
        paused_123 = getIntent().getBooleanExtra(ONETWOTHREE_PAUSED, false);

        View resumeButton = this.findViewById(R.id.resume_btn);
        resumeButton.setOnClickListener(this);
        View contButton = this.findViewById(R.id.continue_btn);
        contButton.setOnClickListener(this);
        View endButton = this.findViewById(R.id.endgame_btn);
        endButton.setOnClickListener(this);
        TextView v= (TextView) findViewById(R.id.UPD);
        v.setText(UP + "");
        TextView v2= (TextView) findViewById(R.id.timeleft_clock);
        v2.setText("" + String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(timeLeft),
                TimeUnit.MILLISECONDS.toSeconds(timeLeft)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeLeft))));


        if(paused_123)
            moveTaskToBack(true);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resume_btn:
                finish();
                break;
            case R.id.continue_btn:
                //must create an instance of Intent before running the activity
                Intent i = new Intent(this, About.class);
                this.startActivity(i);
                break;
            case R.id.endgame_btn:
                finishAffinity();
                break;
        }
    }

}