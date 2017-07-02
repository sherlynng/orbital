package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Random;

/**
 * Created by BTW on 6/11/2017.
 */

public class ChooseLevel extends Activity implements OnClickListener {
    public static final String UPDIGIT = "UPDIGIT";
    protected boolean continueMusic = true;

    public int gridSize;
    public int upDigit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooselevel);
/*
        gridSize = Settings.getGridSize(this);
        upDigit = Settings.getUpDigit(this);

        if(upDigit == 0) {
            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            upDigit = rand.nextInt((9 - 3) + 1) + 3;
        }
*/
   //     UP = getIntent().getIntExtra("UPDIGIT", 1);

        View lame = this.findViewById(R.id.lame);
        lame.setOnClickListener(this);
        View easy = this.findViewById(R.id.easy);
        easy.setOnClickListener(this);
        View medium = this.findViewById(R.id.medium);
        medium.setOnClickListener(this);
        View hard = this.findViewById(R.id.hard);
        hard.setOnClickListener(this);
        View extreme = this.findViewById(R.id.extreme);
        extreme.setOnClickListener(this);
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
/*
    private void chooseGridLame() {
        switch (gridSize) {
            case 4:
                Intent fourbyfour = new Intent(this, MainLame4.class);
                fourbyfour.putExtra(MainLame4.UPDIGIT, upDigit);
                startActivity(fourbyfour);
                break;
            case 5:
                Intent fivebyfive = new Intent(this, MainLame4.class);
                fivebyfive.putExtra(MainLame4.UPDIGIT, upDigit);
                startActivity(fivebyfive);
                break;
            case 6:
                Intent sixbysix = new Intent(this, MainLame4.class);
                sixbysix.putExtra(MainLame4.UPDIGIT, upDigit);
                startActivity(sixbysix);
                break;
        }
    }

    private void chooseGridEasy() {
        switch (gridSize) {
            case 4:
                Intent fourbyfour = new Intent(this, Main4.class);
                fourbyfour.putExtra(Main4.UPDIGIT, upDigit);
                startActivity(fourbyfour);
                break;
            case 5:
                Intent fivebyfive = new Intent(this, Main5.class);
                fivebyfive.putExtra(Main5.UPDIGIT, upDigit);
                startActivity(fivebyfive);
                break;
            case 6:
                Intent sixbysix = new Intent(this, Main6.class);
                sixbysix.putExtra(Main6.UPDIGIT, upDigit);
                startActivity(sixbysix);
                break;
        }
    }
    */

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