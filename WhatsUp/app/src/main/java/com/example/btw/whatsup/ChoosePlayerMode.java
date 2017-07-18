package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by BTW on 6/23/2017.
 */

public class ChoosePlayerMode extends Activity implements View.OnClickListener {

    private View onePlayerBtn;
    private View twoPlayerBtn;
    protected boolean continueMusic = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseplayer);

        onePlayerBtn = this.findViewById(R.id.oneplayer_btn);
        onePlayerBtn.setBackgroundResource(R.drawable.oneplayer_btn_state);
        onePlayerBtn.setOnClickListener(this);
        twoPlayerBtn = this.findViewById(R.id.twoplayer_btn);
        twoPlayerBtn.setBackgroundResource(R.drawable.twoplayer_btn_state);
        twoPlayerBtn.setOnClickListener(this);

        ImageView logo = (ImageView) findViewById(R.id.whatsup_logo);
        logo.setImageResource(R.mipmap.whatsup_logo);

    }

    public void onClick(View v) {
        if(v==onePlayerBtn) {
            Intent game= new Intent(this, ChooseLevel.class);
            this.startActivity(game);
        } else if(v==twoPlayerBtn) {

            Intent game= new Intent(this, ChooseMultiplayerGame.class);
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
