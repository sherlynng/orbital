package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by BTW on 5/11/2017.
 */

public class EndRoundOne extends Activity implements View.OnClickListener {

    public static final String KEY = "KEY";
    public static final String KEY_DEFAULT = "hello";
    String key;
    public static final String IDENTITY = "IDENTITY";
    public static final String IDENTITY_DEFAULT = "null";
    String identity;
    String state, creator_ready, joiner_ready;
    private int my_score, opponent_score;
    protected DatabaseReference currentGameDb;
    private ValueEventListener listener;
    protected boolean continueMusic = true;
    private TextView waiting_text;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endroundone);

        key = getIntent().getExtras().getString(KEY, KEY_DEFAULT);
        identity = getIntent().getExtras().getString(IDENTITY, IDENTITY_DEFAULT);
        currentGameDb = FirebaseDatabase.getInstance().getReference("updown_games").child(key);

        View readyBtn = this.findViewById(R.id.ready_btn);
        readyBtn.setOnClickListener(this);

        waiting_text = (TextView) findViewById(R.id.waiting_text);
        waiting_text.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart(){
        super.onStart();

        listener = currentGameDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MultiplayerGame game = dataSnapshot.getValue(MultiplayerGame.class);

                if(game.getState().equals("ENDGAME")){
                    currentGameDb.removeEventListener(listener);
                    Toast.makeText(EndRoundOne.this, "Multiplayer game exited", Toast.LENGTH_SHORT).show();
                    backToMainMenu();
                    return;
                }

                creator_ready = game.getCreator_ready();
                joiner_ready = game.getJoiner_ready();

                if(creator_ready.equals("READY") && joiner_ready.equals("READY")){
                    currentGameDb.child("state").setValue("PLAY2");
                    currentGameDb.child("creator_ready").setValue("NO");
                    currentGameDb.child("joiner_ready").setValue("NO");
                    return;
                }

                state = game.getState();

                if(identity.equals("creator")) {
                    opponent_score = game.getJoiner_score();
                    my_score = game.getCreator_score();

                    if(creator_ready.equals("READY") && !joiner_ready.equals("READY")){
                        waiting_text.setVisibility(View.VISIBLE);
                    }
                }
                else{ //joiner
                    opponent_score = game.getCreator_score();
                    my_score = game.getJoiner_score();

                    if(joiner_ready.equals("READY") && !creator_ready.equals("READY")){
                        waiting_text.setVisibility(View.VISIBLE);
                    }
                }

                TextView myScore_text = (TextView)findViewById(R.id.myScore_text);
                TextView opponentScore_text = (TextView)findViewById(R.id.opponentScore_text);
                myScore_text.setText(my_score + "");
                opponentScore_text.setText(opponent_score + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

 //       currentGameDb.child("creator_current").setValue(0);
  //      currentGameDb.child("joiner_current").setValue(0);
        if(identity.equals("creator")) {
            currentGameDb.child("creator_ready").setValue("READY");
        }
        else{
            currentGameDb.child("joiner_ready").setValue("READY");
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
                        Toast.makeText(EndRoundOne.this, "Multiplayer game exited", Toast.LENGTH_SHORT).show();
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