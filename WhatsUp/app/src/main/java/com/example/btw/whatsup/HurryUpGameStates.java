package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by sherl on 10/7/2017.
 */

public class HurryUpGameStates extends Activity {

    ListView listViewGames;
    View createGameBtn;
    String identity;
    protected SharedPreferences settings;

    List<MultiplayerGame> games;

    DatabaseReference databaseGames;
    ValueEventListener listener;

    TextView game_type;

    protected boolean continueMusic = true;

    private ArrayList<Integer> sharedArrInitialise = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart1 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart2 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart3 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart4 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart5 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart6 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart7 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrMPart8 = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_create_game);

        final String PREFS_NAME = "MyPrefsFile";
        settings = getSharedPreferences(PREFS_NAME, 0);

        game_type = (TextView) findViewById(R.id.game_type);
        game_type.setText("Hurry Up");

//getting the reference of artists node
        databaseGames = FirebaseDatabase.getInstance().getReference("hurryup_games");

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
                String joiner_game_id = settings.getString("game_id", "");
                if(joiner_game_id != ""){
                    databaseGames.child(game.getGameId()).child("joiner").setValue(joiner_game_id);
                }
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
        MultiplayerGame game;
        String gameID = databaseGames.push().getKey();
        identity = "creator";
        //Game state open
        String creator_game_id = settings.getString("game_id", "");
        Log.d("Creator game ID", creator_game_id);
        if(creator_game_id != "") {
            game = new MultiplayerGame(gameID, creator_game_id, "Joiner");
        }
        else{
            game = new MultiplayerGame(gameID, "Creator", "Joiner");
        }
        databaseGames.child(gameID).setValue(game);
        databaseGames.child(gameID).child("state").setValue("OPEN");
        databaseGames.child(gameID).child("joiner_current").setValue(1);
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

                    if(game.getState() == null){
                        return;
                    }
                    else if(game.getState().equals("OPEN")) {
                        games.add(game);
                    }
                }
                GameList gameAdapter = new GameList(HurryUpGameStates.this, games);
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
                        //Generate up num
                        Random rand = new Random();
                        int upDigit = rand.nextInt((9 - 3) + 1) + 3;
                        databaseGames.child(key).child("upDigit").setValue(upDigit);

                        //Generate shared array for new digits
                        generateArray(key);

                        playerVsPlayer(key);
                        break;
                    case "END_ROUND":
                        endRound(key);
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
        Intent i = new Intent(this, WaitingForPlayerHurryUp.class);
        i.putExtra(WaitingForPlayerHurryUp.KEY, key);
        this.startActivity(i);
    }

    private void generateArray(String key){
        //Initialise
        for(int i=1; i<=16; i++){
            sharedArrInitialise.add(i);
        }
        Collections.shuffle(sharedArrInitialise);
        databaseGames.child(key).child("shared_arr_initialise").setValue(sharedArrInitialise);

        //Part 1
        for(int i=17; i<=26; i++){
            sharedArrMPart1.add(i);
        }
        Collections.shuffle(sharedArrMPart1);
        databaseGames.child(key).child("shared_arr_part1").setValue(sharedArrMPart1);

        //Part 2
        for(int i=27; i<=36; i++){
            sharedArrMPart2.add(i);
        }
        Collections.shuffle(sharedArrMPart2);
        databaseGames.child(key).child("shared_arr_part2").setValue(sharedArrMPart2);

        //Part 3
        for(int i=37; i<=46; i++){
            sharedArrMPart3.add(i);
        }
        Collections.shuffle(sharedArrMPart3);
        databaseGames.child(key).child("shared_arr_part3").setValue(sharedArrMPart3);

        //Part 4
        for(int i=47; i<=56; i++){
            sharedArrMPart4.add(i);
        }
        Collections.shuffle(sharedArrMPart4);
        databaseGames.child(key).child("shared_arr_part4").setValue(sharedArrMPart4);

        //Part 5
        for(int i=57; i<=66; i++){
            sharedArrMPart5.add(i);
        }
        Collections.shuffle(sharedArrMPart5);
        databaseGames.child(key).child("shared_arr_part5").setValue(sharedArrMPart5);

        //Part 6
        for(int i=67; i<=76; i++){
            sharedArrMPart6.add(i);
        }
        Collections.shuffle(sharedArrMPart6);
        databaseGames.child(key).child("shared_arr_part6").setValue(sharedArrMPart6);

        //Part 7
        for(int i=77; i<=86; i++){
            sharedArrMPart7.add(i);
        }
        Collections.shuffle(sharedArrMPart7);
        databaseGames.child(key).child("shared_arr_part7").setValue(sharedArrMPart7);

        //Part 8
        for(int i=87; i<=100; i++){
            sharedArrMPart8.add(i);
        }
        Collections.shuffle(sharedArrMPart8);
        databaseGames.child(key).child("shared_arr_part8").setValue(sharedArrMPart8);
    }

    private void playerVsPlayer(String key){
        Intent i = new Intent(this, PlayerVsPlayerHurryUp.class);
        i.putExtra(PlayerVsPlayerHurryUp.KEY, key);
        i.putExtra(PlayerVsPlayerHurryUp.IDENTITY, identity);
        this.startActivity(i);
    }

    private void endRound(String key){
        Intent i = new Intent(this, EndRoundHurryUp.class);
        i.putExtra(EndRoundHurryUp.KEY, key);
        i.putExtra(EndRoundHurryUp.IDENTITY, identity);
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
