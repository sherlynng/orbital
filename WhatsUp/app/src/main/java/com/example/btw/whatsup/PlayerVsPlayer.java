package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by BTW on 5/24/2017.
 */

public class PlayerVsPlayer extends Activity {
    public static final String KEY = "KEY";
    public static final String KEY_DEFAULT = "hello";
    String key;
    public static final String IDENTITY = "IDENTITY";
    public static final String IDENTITY_DEFAULT = "null";
    String identity;
    protected boolean continueMusic = true;
    DatabaseReference currentGameDb;
    int upDigit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playervsplayer);
        key = getIntent().getExtras().getString(KEY, KEY_DEFAULT);
        identity = getIntent().getExtras().getString(IDENTITY, IDENTITY_DEFAULT);
        currentGameDb = FirebaseDatabase.getInstance().getReference("games").child(key);

        final TextView creator = (TextView) findViewById(R.id.creator_name);
        final TextView joiner = (TextView) findViewById(R.id.joiner_name);
        final TextView up = (TextView) findViewById(R.id.vs);


        currentGameDb.addValueEventListener(new ValueEventListener() {
            MultiplayerGame game;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                game = dataSnapshot.getValue(MultiplayerGame.class);
                upDigit = game.getUpDigit();
                creator.setText(game.getCreator());
                joiner.setText(game.getJoiner());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Handler handler = new Handler();
        final Runnable counter = new Runnable() {
            @Override
            public void run() {
                creator.setText("UP Digit is");
                up.setText(upDigit + "");
                joiner.setText("");
            }
        };
        handler.postDelayed(counter, 1500);

        final Handler handler2 = new Handler();
        final Runnable counter2 = new Runnable() {
            @Override
            public void run() {
                startGame();
            }
        };
        handler2.postDelayed(counter2, 3000);
    }

    private void startGame(){
        currentGameDb.child("status").setValue("PLAY1");

        if(identity.equals("creator")) {
            Intent i = new Intent(this, MainUp.class);
            i.putExtra(MainUp.KEY, key);
            this.startActivity(i);
        }
        else{ //joiner
            Intent i = new Intent(this, MainDown.class);
            i.putExtra(MainDown.KEY, key);
            this.startActivity(i);
        }
    }

    @Override
    public void onBackPressed(){

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
        MusicManager.start(this, MusicManager.MUSIC_GAME);
    }

}