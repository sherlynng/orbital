package com.example.btw.whatsup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by BTW on 5/11/2017.
 */

public class GameOver extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        View resBtn = this.findViewById(R.id.restart_btn);
        resBtn.setOnClickListener(this);
        View mainBtn = this.findViewById(R.id.mainMenu_btn);
        mainBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.restart_btn:
                Intent i = new Intent(this, ChooseUpMode.class);
                this.startActivity(i);
                break;
            case R.id.mainMenu_btn:
                Intent j = new Intent(this, WhatsUp.class);
                this.startActivity(j);
                break;
        }
    }
}