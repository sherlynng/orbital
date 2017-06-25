package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by BTW on 5/24/2017.
 */

public class ChooseUpMode extends Activity implements View.OnClickListener {

    public int up;
    protected boolean continueMusic = true;


    AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseupmode);
        View button = this.findViewById(R.id.manual_button);
        button.setOnClickListener(this);
        View button2 = this.findViewById(R.id.random_button);
        button2.setOnClickListener(this);

        Button test = (Button) findViewById(R.id.test);
        test.setBackgroundResource(R.drawable.blue_spark);
        test.setOnClickListener(this);


}

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manual_button:
                Intent i = new Intent(this, ChooseUpManual.class);
                this.startActivity(i);
                break;
            case R.id.random_button:
                Intent j = new Intent(this, ChooseUpRandom.class);
                this.startActivity(j);
                break;

            case R.id.test:
                Button temp = (Button) v;
                anim = (AnimationDrawable)temp.getBackground();
               // anim.start();
                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();


                //   Button temp = (Button) v;
               // temp.setBackgroundDrawable(getResources().getDrawable(R.drawable.orange_spark));

                break;
        }
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
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }
}