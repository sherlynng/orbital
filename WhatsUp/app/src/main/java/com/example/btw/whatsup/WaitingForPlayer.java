package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by BTW on 5/24/2017.
 */

public class WaitingForPlayer extends Activity {
    public static final String KEY = "KEY";
    public static final String KEY_DEFAULT = "hello";
    String key;
    protected boolean continueMusic = true;
    DatabaseReference currentGameDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_for_player);
        key = getIntent().getExtras().getString(KEY, KEY_DEFAULT);
        currentGameDb = FirebaseDatabase.getInstance().getReference("updown_games").child(key);
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
        MusicManager.start(this, MusicManager.MUSIC_END_GAME);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit multiplayer game?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(WaitingForPlayer.this, "Multiplayer game exited", Toast.LENGTH_SHORT).show();
                        currentGameDb.child("state").setValue("ENDGAME");
                        backToMainMenu();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void backToMainMenu(){
        Intent i = new Intent(this, MainMenu.class);
        this.startActivity(i);
    }
}