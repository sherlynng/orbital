package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/**
 * Created by BTW on 6/11/2017.
 */

public class ChooseLevel extends Activity implements OnClickListener, View.OnLongClickListener {
    public static final String UPDIGIT = "UPDIGIT";
    protected boolean continueMusic = true;

    public int gridSize;
    public int upDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oneplayer);

        View lame = this.findViewById(R.id.lame);
        lame.setBackgroundResource(R.drawable.lame_btn_state);
        lame.setOnClickListener(this);
        lame.setOnLongClickListener(this);
        View easy = this.findViewById(R.id.easy);
        easy.setBackgroundResource(R.drawable.easy_btn_state);
        easy.setOnClickListener(this);
        easy.setOnLongClickListener(this);
        View medium = this.findViewById(R.id.medium);
        medium.setBackgroundResource(R.drawable.medium_btn_state);
        medium.setOnClickListener(this);
        medium.setOnLongClickListener(this);
        View hard = this.findViewById(R.id.hard);
        hard.setBackgroundResource(R.drawable.hard_btn_state);
        hard.setOnClickListener(this);
        hard.setOnLongClickListener(this);
        View extreme = this.findViewById(R.id.extreme);
        extreme.setBackgroundResource(R.drawable.extreme_btn_state);
        extreme.setOnClickListener(this);
        extreme.setOnLongClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.lame:
                Intent next = new Intent(this, YourUpDigit.class);
             //   next.putExtra(YourUpDigit.UPDIGIT, upDigit);
             //   next.putExtra(YourUpDigit.GRIDSIZE, gridSize);
                next.putExtra(YourUpDigit.DIFFICULTY, 1);
                startActivity(next);
                break;

            case R.id.easy:
                Intent next2 = new Intent(this, YourUpDigit.class);
             //   next2.putExtra(YourUpDigit.UPDIGIT, upDigit);
             //   next2.putExtra(YourUpDigit.GRIDSIZE, gridSize);
                next2.putExtra(YourUpDigit.DIFFICULTY, 2);
                startActivity(next2);
                break;

            case R.id.medium:
                Intent next3 = new Intent(this, YourUpDigit.class);
             //   next3.putExtra(YourUpDigit.UPDIGIT, upDigit);
             //   next3.putExtra(YourUpDigit.GRIDSIZE, gridSize);
                next3.putExtra(YourUpDigit.DIFFICULTY, 3);
                startActivity(next3);
                break;

            case R.id.hard:
                Intent next4 = new Intent(this, YourUpDigit.class);
             //   next4.putExtra(YourUpDigit.UPDIGIT, upDigit);
             //   next4.putExtra(YourUpDigit.GRIDSIZE, gridSize);
                next4.putExtra(YourUpDigit.DIFFICULTY, 4);
                startActivity(next4);
                break;

            case R.id.extreme:
                Intent next5 = new Intent(this, YourUpDigit.class);
             //   next5.putExtra(YourUpDigit.UPDIGIT, upDigit);
             //   next5.putExtra(YourUpDigit.GRIDSIZE, gridSize);
                next5.putExtra(YourUpDigit.DIFFICULTY, 5);
                startActivity(next5);
                break;
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.lame:
                Intent lame = new Intent(this, LevelInfo.class);
                lame.putExtra(LevelInfo.DIFFICULTY, 1);
                this.startActivity(lame);
                break;
            case R.id.easy:
                Intent easy = new Intent(this, LevelInfo.class);
                easy.putExtra(LevelInfo.DIFFICULTY, 2);
                this.startActivity(easy);
                break;
            case R.id.medium:
                Intent medium = new Intent(this, LevelInfo.class);
                medium.putExtra(LevelInfo.DIFFICULTY, 3);
                this.startActivity(medium);
                break;
            case R.id.hard:
                Intent hard = new Intent(this, LevelInfo.class);
                hard.putExtra(LevelInfo.DIFFICULTY, 4);
                this.startActivity(hard);
                break;
            case R.id.extreme:
                Intent extreme = new Intent(this, LevelInfo.class);
                extreme.putExtra(LevelInfo.DIFFICULTY, 5);
                this.startActivity(extreme);
                break;
        }

        return true;
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