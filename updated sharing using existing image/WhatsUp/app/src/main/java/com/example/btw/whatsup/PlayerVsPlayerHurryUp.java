package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by BTW on 5/24/2017.
 */

public class PlayerVsPlayerHurryUp extends Activity {
    public static final String KEY = "KEY";
    public static final String KEY_DEFAULT = "hello";
    String key;
    public static final String IDENTITY = "IDENTITY";
    public static final String IDENTITY_DEFAULT = "null";
    String identity;
    private boolean vsScreen;
    protected boolean continueMusic = true;
    DatabaseReference currentGameDb;
    int upDigit;
    private ValueEventListener listener;
    private ArrayList<Integer> sharedArrInitialise = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart1 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart2 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart3 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart4 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart5 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart6 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart7 = new ArrayList<Integer>();
    private ArrayList<Integer> sharedArrPart8 = new ArrayList<Integer>();

    private ValueEventListener listenerIni, listener1, listener2, listener3, listener4;
    private ValueEventListener listener5, listener6, listener7, listener8;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playervsplayer);
        key = getIntent().getExtras().getString(KEY, KEY_DEFAULT);
        identity = getIntent().getExtras().getString(IDENTITY, IDENTITY_DEFAULT);
        currentGameDb = FirebaseDatabase.getInstance().getReference("hurryup_games").child(key);

        final TextView creator = (TextView) findViewById(R.id.creator_name);
        final TextView joiner = (TextView) findViewById(R.id.joiner_name);
        final TextView up = (TextView) findViewById(R.id.vs);
        vsScreen = true;

        listener = currentGameDb.addValueEventListener(new ValueEventListener() {
            MultiplayerGame game;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                game = dataSnapshot.getValue(MultiplayerGame.class);

                if (game == null) {
                    return;
                }

                if(game.getState().equals("END_ROUND")){
                    currentGameDb.removeEventListener(listener);
                    currentGameDb.removeEventListener(listenerIni);
                    currentGameDb.removeEventListener(listener1);
                    currentGameDb.removeEventListener(listener2);
                    currentGameDb.removeEventListener(listener3);
                    currentGameDb.removeEventListener(listener4);
                    currentGameDb.removeEventListener(listener5);
                    currentGameDb.removeEventListener(listener6);
                    currentGameDb.removeEventListener(listener7);
                    currentGameDb.removeEventListener(listener8);
                    return;
                }

                upDigit = game.getUpDigit();
                if(vsScreen) {
                    creator.setText(game.getCreator());
                    joiner.setText(game.getJoiner());
                    vsScreen = false;
                }
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

    private void startGame() {
        currentGameDb.child("state").setValue("PLAY");

        Intent i = new Intent(this, MainHurryUp.class);
        i.putExtra(MainHurryUp.KEY, key);
        i.putExtra(MainHurryUp.IDENTITY, identity);
        i.putExtra(MainHurryUp.ARR_INITIALISE, sharedArrInitialise);
        i.putExtra(MainHurryUp.ARR_PART1, sharedArrPart1);
        i.putExtra(MainHurryUp.ARR_PART2, sharedArrPart2);
        i.putExtra(MainHurryUp.ARR_PART3, sharedArrPart3);
        i.putExtra(MainHurryUp.ARR_PART4, sharedArrPart4);
        i.putExtra(MainHurryUp.ARR_PART5, sharedArrPart5);
        i.putExtra(MainHurryUp.ARR_PART6, sharedArrPart6);
        i.putExtra(MainHurryUp.ARR_PART7, sharedArrPart7);
        i.putExtra(MainHurryUp.ARR_PART8, sharedArrPart8);
        this.startActivity(i);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onStart(){
        super.onStart();

        listenerIni = currentGameDb.child("shared_arr_initialise").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("identity", identity);
                sharedArrInitialise.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrInitialise.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener1 = currentGameDb.child("shared_arr_part1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart1.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart1.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener2 = currentGameDb.child("shared_arr_part2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart2.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart2.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener3 = currentGameDb.child("shared_arr_part3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart3.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart3.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener4 = currentGameDb.child("shared_arr_part4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart4.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart4.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener5 = currentGameDb.child("shared_arr_part5").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart5.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart5.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener6 = currentGameDb.child("shared_arr_part6").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart6.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart6.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener7 = currentGameDb.child("shared_arr_part7").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart7.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart7.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listener8 = currentGameDb.child("shared_arr_part8").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("identity", identity);
                sharedArrPart8.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Integer num = postSnapshot.getValue(Integer.class);
                    sharedArrPart8.add(num);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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