package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class EndRoundTwo extends Activity implements View.OnClickListener {

    public static final String KEY = "KEY";
    public static final String KEY_DEFAULT = "hello";
    String key;
    public static final String IDENTITY = "IDENTITY";
    public static final String IDENTITY_DEFAULT = "null";
    String identity;
    private RelativeLayout bg;
    String state, creator_ready, joiner_ready;
    private int my_score, opponent_score;
    protected DatabaseReference currentGameDb;
    private ValueEventListener listener;
    protected boolean continueMusic = true;
    private TextView waiting_text;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endroundtwo);

        bg = (RelativeLayout)this.findViewById(R.id.background);

        key = getIntent().getExtras().getString(KEY, KEY_DEFAULT);
        identity = getIntent().getExtras().getString(IDENTITY, IDENTITY_DEFAULT);
        currentGameDb = FirebaseDatabase.getInstance().getReference("updown_games").child(key);

        View exitBtn = this.findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(this);

        waiting_text = (TextView) findViewById(R.id.waiting_text);
        waiting_text.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart(){
        super.onStart();
        final TextView gameOver_text = (TextView)findViewById(R.id.gameover_message);

        listener = currentGameDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MultiplayerGame game = dataSnapshot.getValue(MultiplayerGame.class);

                if(game.getState().equals("ENDGAME")){
                    currentGameDb.removeEventListener(listener);
                    Toast.makeText(EndRoundTwo.this, "Multiplayer game exited", Toast.LENGTH_SHORT).show();
                    backToMainMenu();
                    return;
                }

                creator_ready = game.getCreator_ready();
                joiner_ready = game.getJoiner_ready();

                if (creator_ready.equals("EXITED") && joiner_ready.equals("EXITED")) {
                   // currentGameDb.child("creator_ready").setValue("NO");
                   // currentGameDb.child("joiner_ready").setValue("NO");
              //      backToMainMenu();
                    currentGameDb.child("state").setValue("ENDGAME");
                    return;
                }

                int creator_current = game.getCreator_current();
                int joiner_current = game.getJoiner_current();
                int creator_score = game.getCreator_score();
                int joiner_score = game.getJoiner_score();

                int joinerTotalScore = joiner_score + joiner_current - 1;
                int creatorTotalScore = creator_score + 100 - creator_current;

                currentGameDb.child("joiner_total_score").setValue(joinerTotalScore);
                currentGameDb.child("creator_total_score").setValue(creatorTotalScore);


                if (identity.equals("creator")) {
                    opponent_score = game.getJoiner_total_score();
                    my_score = game.getCreator_total_score();

                    if (my_score > opponent_score) {
                        bg.setBackgroundResource(R.drawable.background2);
                        gameOver_text.setText("You Win!");
                    } else if (my_score < opponent_score) {
                        bg.setBackgroundResource(R.drawable.background3);
                        gameOver_text.setText("You Lose!");
                    } else {
                        bg.setBackgroundResource(R.drawable.background4);
                        gameOver_text.setText("Draw Game!");
                    }

                    if(creator_ready.equals("EXITED") && !joiner_ready.equals("EXITED")){
                        waiting_text.setVisibility(View.VISIBLE);
                    }

                } else { //joiner
                    opponent_score = game.getCreator_total_score();
                    my_score = game.getJoiner_total_score();

                    if (my_score > opponent_score) {
                        bg.setBackgroundResource(R.drawable.background2);
                        gameOver_text.setText("You Win!");
                    } else if (my_score < opponent_score) {
                        bg.setBackgroundResource(R.drawable.background3);
                        gameOver_text.setText("You Lose!");
                    } else {
                        bg.setBackgroundResource(R.drawable.background4);
                        gameOver_text.setText("Draw Game!");
                    }

                    if(joiner_ready.equals("EXITED") && !creator_ready.equals("EXITED")){
                        waiting_text.setVisibility(View.VISIBLE);
                    }
                }

                TextView myScore_text = (TextView) findViewById(R.id.myScore_text);
                TextView opponentScore_text = (TextView) findViewById(R.id.opponentScore_text);
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

        if(identity.equals("creator")) {
            currentGameDb.child("creator_ready").setValue("EXITED");
        }
        else{
            currentGameDb.child("joiner_ready").setValue("EXITED");
        }
    }

    private void backToMainMenu(){
        Intent i = new Intent(this, MainMenu.class);
        this.startActivity(i);
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
                        Toast.makeText(EndRoundTwo.this, "Multiplayer game exited", Toast.LENGTH_SHORT).show();
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
}