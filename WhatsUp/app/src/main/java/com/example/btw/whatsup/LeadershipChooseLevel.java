package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by BTW on 6/11/2017.
 */

public class LeadershipChooseLevel extends Activity implements OnClickListener {
    protected boolean continueMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneplayer);

        View lame = this.findViewById(R.id.lame);
        lame.setBackgroundResource(R.drawable.lame_btn_state);
        lame.setOnClickListener(this);
        View easy = this.findViewById(R.id.easy);
        easy.setBackgroundResource(R.drawable.easy_btn_state);
        easy.setOnClickListener(this);
        View medium = this.findViewById(R.id.medium);
        medium.setBackgroundResource(R.drawable.medium_btn_state);
        medium.setOnClickListener(this);
        View hard = this.findViewById(R.id.hard);
        hard.setBackgroundResource(R.drawable.hard_btn_state);
        hard.setOnClickListener(this);
        View extreme = this.findViewById(R.id.extreme);
        extreme.setBackgroundResource(R.drawable.extreme_btn_state);
        extreme.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.lame:
                Intent next = new Intent(this, LeadershipLameGame.class);
                startActivity(next);
                break;

            case R.id.easy:
                Intent next2 = new Intent(this, LeadershipEasyGame.class);
                startActivity(next2);
                break;

            case R.id.medium:
                Intent next3 = new Intent(this, LeadershipMediumGame.class);
                startActivity(next3);
                break;

            case R.id.hard:
                Intent next4 = new Intent(this, LeadershipHardGame.class);
                startActivity(next4);
                break;

            case R.id.extreme:
                Intent next5 = new Intent(this, LeadershipExtremeGame.class);
                startActivity(next5);
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