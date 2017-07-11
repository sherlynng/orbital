package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

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
    Button createGameBtn;
    String identity;

    List<MultiplayerGame> games;

    DatabaseReference databaseGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_create_game);

//getting the reference of artists node
        databaseGames = FirebaseDatabase.getInstance().getReference("games");

//getting views
        listViewGames = (ListView) findViewById(R.id.game_list);
        createGameBtn = (Button) findViewById(R.id.create_game);

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
                    games.add(game);
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
        game_status.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String state = dataSnapshot.getValue(String.class);
                Log.d("status", "game status = " + state);

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
                    case "PLAY":
                        startUpDown(key);
                        break;
                    case "GAMEOVER":
                        gameOver(key);
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

    private void startUpDown(String key){
        Intent i = new Intent(this, MainLame4.class);
   //     i.putExtra(WaitingForPlayer.KEY, key);
        this.startActivity(i);
    }

    private void gameOver(String key){

    }
}
