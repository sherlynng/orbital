package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.btw.whatsup.R.id.easy;
import static com.example.btw.whatsup.R.id.up;

/**
 * Created by BTW on 5/24/2017.
 */

public class YourUpDigit extends Activity implements View.OnClickListener {

    public static final String UPDIGIT = "UPDIGIT";
    public static final String GRIDSIZE = "GRIDSIZE";
    public static final String DIFFICULTY = "DIFFICULTY";
    public int upDigit, gridSize, difficulty;
    protected boolean continueMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yourupdigit);

        // upDigit = getIntent().getIntExtra("UPDIGIT", 1);
        // gridSize = getIntent().getIntExtra("GRIDSIZE", 1);
        difficulty = getIntent().getIntExtra("DIFFICULTY", 1);

        gridSize = Settings.getGridSize(this);
        upDigit = Settings.getUpDigit(this);

        if (upDigit == 0) {
            Random rand = new Random();

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            upDigit = rand.nextInt((9 - 3) + 1) + 3;
        }

        TextView v = (TextView) findViewById(R.id.up_display);
        v.setText(upDigit + "");
        View button = this.findViewById(R.id.start_btn);
        button.setBackgroundResource(R.drawable.start_btn_state);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (difficulty) {
            case 1:
                chooseGridLame();
                break;
            case 2:
                chooseGridEasy();
                break;
            case 3:
                chooseGridMedium();
                break;
            case 4:
                chooseGridHard();
                break;
            case 5:
                chooseGridExtreme();
                break;
        }
    }

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

    private void chooseGridMedium() {
        switch (gridSize) {
            case 4:
                Intent fourbyfour = new Intent(this, MainMedium4.class);
                fourbyfour.putExtra(MainMedium4.UPDIGIT, upDigit);
                startActivity(fourbyfour);
                break;
            case 5:
                Intent fivebyfive = new Intent(this, MainMedium4.class);
                fivebyfive.putExtra(MainMedium4.UPDIGIT, upDigit);
                startActivity(fivebyfive);
                break;
            case 6:
                Intent sixbysix = new Intent(this, MainMedium4.class);
                sixbysix.putExtra(MainMedium4.UPDIGIT, upDigit);
                startActivity(sixbysix);
                break;
        }
    }

    private void chooseGridHard() {
        switch (gridSize) {
            case 4:
                Intent fourbyfour = new Intent(this, MainHard4.class);
                fourbyfour.putExtra(MainHard4.UPDIGIT, upDigit);
                startActivity(fourbyfour);
                break;
            case 5:
                Intent fivebyfive = new Intent(this, MainHard4.class);
                fivebyfive.putExtra(MainHard4.UPDIGIT, upDigit);
                startActivity(fivebyfive);
                break;
            case 6:
                Intent sixbysix = new Intent(this, MainHard4.class);
                sixbysix.putExtra(MainHard4.UPDIGIT, upDigit);
                startActivity(sixbysix);
                break;
        }
    }

    private void chooseGridExtreme() {
        Intent fourbyfour = new Intent(this, MainExtreme4.class);
        fourbyfour.putExtra(MainExtreme4.UPDIGIT, upDigit);
        startActivity(fourbyfour);
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