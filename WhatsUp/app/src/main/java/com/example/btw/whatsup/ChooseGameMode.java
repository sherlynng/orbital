package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by BTW on 6/23/2017.
 */

public class ChooseGameMode extends Activity implements View.OnClickListener {

    private View whatsUpBtn;
    private View teamUpBtn ;
    private View hurryUpBtn;
    private View upAndDownBtn;
    public int up;
    protected boolean continueMusic = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosegamemode);
        whatsUpBtn= this.findViewById(R.id.WhatsUp_button);
        whatsUpBtn.setOnClickListener(this);
        teamUpBtn = this.findViewById(R.id.TeamUp_button);
        teamUpBtn.setOnClickListener(this);
        hurryUpBtn= this.findViewById(R.id.hurryUp_button);
        hurryUpBtn.setOnClickListener(this);
        upAndDownBtn = this.findViewById(R.id.UpAndDown_button);
        upAndDownBtn.setOnClickListener(this);
    }

    public void onClick(View v) {
        if(v==whatsUpBtn) {
            Intent game= new Intent(this, ChooseLevel.class);
            this.startActivity(game);
        } else if(v==teamUpBtn) {

            Intent game= new Intent(this, MainTeamUp.class);
            this.startActivity(game);
        }
        // TODO: other multiplayer mode HERE
    /*    else if(v==hurryUpBtn) {
            Intent game = new Intent(this, ChooseUpManual.class);
            this.startActivity(game);
        }
    */
        else if(v==upAndDownBtn){
                Intent game = new Intent(this, MultiplayerGameStates.class);
                this.startActivity(game);
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
