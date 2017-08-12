package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by sherl on 10/7/2017.
 */

public class MultiplayerGameStates extends Activity {

    ListView listViewGames;
    View createGameBtn;
    String identity;

    List<MultiplayerGame> games;

    DatabaseReference databaseGames;
    ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_create_game);

//getting the reference of artists node
        databaseGames = FirebaseDatabase.getInstance().getReference("games");

//getting views
        listViewGames = (ListView) findViewById(R.id.game_list);

        createGameBtn = this.findViewById(R.id.create_game);
        createGameBtn.setBackgroundResource(R.drawable.creategame_btn_state);

//list to store games
        games = new ArrayList<>();


//adding an onclicklistener to button
        createGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//calling the method addArtist()
//the method is defined below
//this method is actually performing the write operation
                createGame();
            }
        });



        listViewGames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MultiplayerGame game = games.get(position);
                identity = "joiner";
         //       Intent start_game = new Intent(this, JoinGame.class);
                databaseGames.child(game.getGameId()).child("state").setValue("JOINED");

                final Handler handler = new Handler();
                final Runnable counter = new Runnable() {
                    @Override
                    public void run() {
                        watchGame(game.getGameId());
                    }
                };
                handler.postDelayed(counter, 350);
            }
        });
    }

    private void createGame(){
        String gameID = databaseGames.push().getKey();
        identity = "creator";
        //Game state open
        MultiplayerGame game = new MultiplayerGame(gameID, "matcha", "latte");
        databaseGames.child(gameID).setValue(game);
        databaseGames.child(gameID).child("state").setValue("OPEN");
        watchGame(gameID);

    }

    @Override
    protected void onStart(){
        super.onStart();

        databaseGames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                games.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    MultiplayerGame game = postSnapshot.getValue(MultiplayerGame.class);
                    Log.d("game", "is = " + game);
                    Log.d("game state", "is = " + game.getState());
                    if(game.getState() == null){
                        return;
                    }
                    else if(game.getState().equals("OPEN")) {
                        games.add(game);
                    }
                }
                GameList gameAdapter = new GameList(MultiplayerGameStates.this, games);
                listViewGames.setAdapter(gameAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void watchGame(final String key){
        final DatabaseReference game_status = databaseGames.child(key).child("state");
        listener = game_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String state = dataSnapshot.getValue(String.class);
                Log.d("status", "game status = " + state);

                if(state == null){
                    databaseGames.child(key).removeEventListener(listener);
                    return;
                }

                switch (state){
                    case "OPEN":
                        waitingForPlayer(key);
                        break;
                    case "JOINED":
                        Random rand = new Random();
                        int upDigit = rand.nextInt((9 - 3) + 1) + 3;
                        databaseGames.child(key).child("upDigit").setValue(upDigit);
                        playerVsPlayer(key);
                /*        final Handler handler = new Handler();
                        final Runnable counter = new Runnable() {
                            @Override
                            public void run() {
                                databaseGames.child(key).child("status").setValue("PLAY");
                            }
                        };
                        handler.postDelayed(counter, 3000); */
                        break;
                    case "END_ROUND1":
                        endRoundOne(key);
                        break;
                    case "PLAY2":
                        if(identity.equals("creator")) {
                            startDown(key);
                        }
                        else{ //joiner
                            startUp(key);
                        }
                        break;
                    case "END_ROUND2":
                        endRoundTwo(key);
                        break;
                    case "ENDGAME":
                        removeGame(key);
                        databaseGames.child(key).removeEventListener(listener);
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void waitingForPlayer(String key){
        Intent i = new Intent(this, WaitingForPlayer.class);
        i.putExtra(WaitingForPlayer.KEY, key);
        this.startActivity(i);
    }

    private void playerVsPlayer(String key){
        Intent i = new Intent(this, PlayerVsPlayer.class);
        i.putExtra(PlayerVsPlayer.KEY, key);
        i.putExtra(PlayerVsPlayer.IDENTITY, identity);
        this.startActivity(i);
    }

    private void endRoundOne(String key){
        Intent i = new Intent(this, EndRoundOne.class);
        i.putExtra(EndRoundOne.KEY, key);
        i.putExtra(EndRoundOne.IDENTITY, identity);
        this.startActivity(i);
    }

    private void startUp(String key){
        Intent i = new Intent(this, MainUp.class);
        i.putExtra(MainUp.KEY, key);
        this.startActivity(i);
        databaseGames.child(key).child("creator_current").setValue(100);
        databaseGames.child(key).child("joiner_current").setValue(1);
    }

    private void startDown(String key){
        Intent j = new Intent(this, MainDown.class);
        j.putExtra(MainDown.KEY, key);
        this.startActivity(j);
    }

    private void endRoundTwo(String key){
        Intent i = new Intent(this, EndRoundTwo.class);
        i.putExtra(EndRoundTwo.KEY, key);
        i.putExtra(EndRoundTwo.IDENTITY, identity);
        this.startActivity(i);
    }

    private void removeGame(final String key){
        final Handler handler = new Handler();
        final Runnable counter = new Runnable() {
            @Override
            public void run() {
                databaseGames.child(key).removeValue();
            }
        };
        handler.postDelayed(counter, 10000);

    }
}
