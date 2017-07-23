package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sherl on 10/7/2017.
 */

public class LeadershipMediumGame extends Activity implements View.OnClickListener {

    ListView listViewGames;
    View createGameBtn;
    String identity;

    LeadershipComparator leaderComp;
    protected SharedPreferences gameDataMedium;
    protected SharedPreferences settings;
    private int best_score;
    private String game_id;
    private boolean nameExist;
    private boolean boardChanged;
    private String old_key;

    DatabaseReference databaseGames;

    View sync_btn;
    TextView game_type;
    TextView first_name, second_name, third_name, fourth_name, fifth_name, sixth_name, seventh_name, eighth_name,
            ninth_name, tenth_name;
    TextView first_score, second_score, third_score, fourth_score, fifth_score, sixth_score, seventh_score, eighth_score,
            ninth_score, tenth_score;
    ImageView first_trophy, second_trophy, third_trophy, fourth_badge, fifth_badge;

    protected boolean continueMusic = true;
    private boolean setData;
    private String sync_status;

    private ArrayList<User> leadership_arr = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leadership_board);

        final String PREFS_NAME = "MyPrefsFile";
        settings = getSharedPreferences(PREFS_NAME, 0);
        game_id = settings.getString("game_id", "");

        gameDataMedium = getSharedPreferences("gameDataMedium", Context.MODE_PRIVATE);
        best_score = gameDataMedium.getInt("bestScore", 0);
        Log.d("best score = ", best_score + "");

        nameExist = false;
        boardChanged = false;
        setData = false;
        sync_status = "not_syncing";

        game_type = (TextView) findViewById(R.id.game_type);
        game_type.setText("Medium Game");

        sync_btn = this.findViewById(R.id.sync_btn);
        sync_btn.setBackgroundResource(R.drawable.sync_btn_state);
        sync_btn.setOnClickListener(this);
        first_name = (TextView) findViewById(R.id.first_name);
        first_score = (TextView) findViewById(R.id.first_score);
        second_name = (TextView) findViewById(R.id.second_name);
        second_score = (TextView) findViewById(R.id.second_score);
        third_name = (TextView) findViewById(R.id.third_name);
        third_score = (TextView) findViewById(R.id.third_score);
        fourth_name = (TextView) findViewById(R.id.fourth_name);
        fourth_score = (TextView) findViewById(R.id.fourth_score);
        fifth_name = (TextView) findViewById(R.id.fifth_name);
        fifth_score = (TextView) findViewById(R.id.fifth_score);
        sixth_name = (TextView) findViewById(R.id.sixth_name);
        sixth_score = (TextView) findViewById(R.id.sixth_score);
        seventh_name = (TextView) findViewById(R.id.seventh_name);
        seventh_score = (TextView) findViewById(R.id.seventh_score);
        eighth_name = (TextView) findViewById(R.id.eighth_name);
        eighth_score = (TextView) findViewById(R.id.eighth_score);
        ninth_name = (TextView) findViewById(R.id.ninth_name);
        ninth_score = (TextView) findViewById(R.id.ninth_score);
        tenth_name = (TextView) findViewById(R.id.tenth_name);
        tenth_score = (TextView) findViewById(R.id.tenth_score);

        first_trophy = (ImageView) findViewById(R.id.first_trophy);
        first_trophy.setImageResource(R.drawable.first_trophy);
        second_trophy = (ImageView) findViewById(R.id.second_trophy);
        second_trophy.setImageResource(R.drawable.second_trophy);
        third_trophy = (ImageView) findViewById(R.id.third_trophy);
        third_trophy.setImageResource(R.drawable.third_trophy);
        fourth_badge = (ImageView) findViewById(R.id.fourth_badge);
        fourth_badge.setImageResource(R.drawable.fourth_badge);
        fifth_badge = (ImageView) findViewById(R.id.fifth_badge);
        fifth_badge.setImageResource(R.drawable.fifth_badge);

        leaderComp = new LeadershipComparator();

//getting the reference of artists node
        databaseGames = FirebaseDatabase.getInstance().getReference("leadership_board").child("medium");

        databaseGames.child("old_key").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                old_key = dataSnapshot.getValue(String.class);
                Log.d("old key", "hi= " + old_key);

                leadership_arr.clear();
                Log.d("old key2", "hi= " + old_key);
                if(old_key != null) {
                    databaseGames.child(old_key).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                User user = postSnapshot.getValue(User.class);
                                leadership_arr.add(user);
                            }
                            Collections.sort(leadership_arr, leaderComp);
                            Log.d("leadership_arr size", leadership_arr.size() + "");
                            setData();
                            setData = true;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    setData();
                    setData = true;
                }
                Log.d("boolean set data1", setData + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();

        Log.d("onStart", "hi= " + old_key);

    }

    @Override
    public void onClick(View v) {  //Sync data
        Log.d("boolean set data2", setData + "");

        databaseGames.child("sync_status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sync_status = dataSnapshot.getValue(String.class);
                if(sync_status != null) {
                    if (sync_status.equals("not_syncing")) { //other players not syncing

                        if (setData) { //data is set on user's phone
                            databaseGames.child("sync_status").setValue("syncing");
                            setData = false;
                            databaseGames.child("old_key").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    leadership_arr.clear();
                                    old_key = dataSnapshot.getValue(String.class);
//                    Log.d("old key", old_key);
                                    if (old_key != null) {

                                        databaseGames.child(old_key).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                    User user = postSnapshot.getValue(User.class);
                                                    leadership_arr.add(user);
                                                }
                                                Collections.sort(leadership_arr, leaderComp);
                                                Log.d("add arr", "hello");

                                                if (old_key != null) {
                                                    databaseGames.child(old_key).removeValue();
                                                    Log.d("remove data", "hello");
                                                }

                                                Log.d("arr size", leadership_arr.size() + "");
                                                addNewData();
                                                setData = true;
                                                databaseGames.child("sync_status").setValue("not_syncing");
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        addNewData();
                                        setData = true;
                                        databaseGames.child("sync_status").setValue("not_syncing");
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
                else{
                    if (setData) { //data is set on user's phone
                        databaseGames.child("sync_status").setValue("syncing");
                        setData = false;
                        databaseGames.child("old_key").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                leadership_arr.clear();
                                old_key = dataSnapshot.getValue(String.class);
//                    Log.d("old key", old_key);
                                if (old_key != null) {

                                    databaseGames.child(old_key).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                User user = postSnapshot.getValue(User.class);
                                                leadership_arr.add(user);
                                            }
                                            Collections.sort(leadership_arr, leaderComp);
                                            Log.d("add arr", "hello");

                                            if (old_key != null) {
                                                databaseGames.child(old_key).removeValue();
                                                Log.d("remove data", "hello");
                                            }

                                            Log.d("arr size", leadership_arr.size() + "");
                                            addNewData();
                                            setData = true;
                                            databaseGames.child("sync_status").setValue("not_syncing");
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                } else {
                                    addNewData();
                                    setData = true;
                                    databaseGames.child("sync_status").setValue("not_syncing");
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addNewData() {
        Log.d("add new data", "hello");

        nameExist = false;
        //Check if name is already in leadership board
        for (int i = 0; i < leadership_arr.size(); i++) {
            //Name exists
            if (game_id.equals(leadership_arr.get(i).getName())) {
                nameExist = true;
                Log.d("name exist", "hello");
                if (best_score > leadership_arr.get(i).getScore()) {
                    leadership_arr.get(i).setScore(best_score);
                }
            }
        }

        //If name does not exist in leadership board, check if user can enter leadership board
        if (!nameExist) {
            if (leadership_arr.size() == 0) { //Handle unique case when there's no entry in leadership board
                User user = new User(game_id, best_score);
                leadership_arr.add(user);
            } else {
                Log.d("name does not exist", "hello");
                for (int i = 0; i < leadership_arr.size(); i++) {
                    if (best_score >= leadership_arr.get(i).getScore()) {
                        Log.d("add user1", "hello");
                        User user = new User(game_id, best_score);
                        leadership_arr.add(user);
                        break;
                    } else { //Add user if leadership board less than 10
                        if (leadership_arr.size() <= 10) {
                            Log.d("add user2", "hello");
                            User user = new User(game_id, best_score);
                            leadership_arr.add(user);
                            break;
                        }
                    }
                }
            }
        }

        String key = databaseGames.push().getKey();
        databaseGames.child("old_key").setValue(key); //Board key
        Log.d("arr size", leadership_arr.size() + "");
        Collections.sort(leadership_arr, leaderComp);
        ArrayList<User> temp = new ArrayList<User>();
        if (leadership_arr.size() > 10) {
            for (int i = 0; i < 10; i++) {
                temp.add(leadership_arr.get(i)); //Get only the first 10 players
            }
            leadership_arr = temp;
            databaseGames.child(key).child("list").setValue(leadership_arr);
        } else{
            for (int i = 0; i < leadership_arr.size(); i++) {
                databaseGames.child(key).child("list").setValue(leadership_arr);
            }
        }
        setData();
    }

    private void setData() {
        boolean set0, set1, set2, set3, set4, set5, set6, set7, set8, set9;
        set0 = set1 = set2 = set3 = set4 = set5 = set6 = set7 = set8 = set9 = false;

        Log.d("set data", "hi");

        for (int i = 0; i < leadership_arr.size(); i++) {
            switch (i) {
                case 0:
                    first_name.setText(leadership_arr.get(0).getName());
                    first_score.setText(leadership_arr.get(0).getScore() + "");
                    set0 = true;
                    break;
                case 1:
                    second_name.setText(leadership_arr.get(1).getName());
                    second_score.setText(leadership_arr.get(1).getScore() + "");
                    set1 = true;
                    break;
                case 2:
                    third_name.setText(leadership_arr.get(2).getName());
                    third_score.setText(leadership_arr.get(2).getScore() + "");
                    set2 = true;
                    break;
                case 3:
                    fourth_name.setText(leadership_arr.get(3).getName());
                    fourth_score.setText(leadership_arr.get(3).getScore() + "");
                    set3 = true;
                    break;
                case 4:
                    fifth_name.setText(leadership_arr.get(4).getName());
                    fifth_score.setText(leadership_arr.get(4).getScore() + "");
                    set4 = true;
                    break;
                case 5:
                    sixth_name.setText(leadership_arr.get(5).getName());
                    sixth_score.setText(leadership_arr.get(5).getScore() + "");
                    set5 = true;
                    break;
                case 6:
                    seventh_name.setText(leadership_arr.get(6).getName());
                    seventh_score.setText(leadership_arr.get(6).getScore() + "");
                    set6 = true;
                    break;
                case 7:
                    eighth_name.setText(leadership_arr.get(7).getName());
                    eighth_score.setText(leadership_arr.get(7).getScore() + "");
                    set7 = true;
                    break;
                case 8:
                    ninth_name.setText(leadership_arr.get(8).getName());
                    ninth_score.setText(leadership_arr.get(8).getScore() + "");
                    set8 = true;
                    break;
                case 9:
                    tenth_name.setText(leadership_arr.get(9).getName());
                    tenth_score.setText(leadership_arr.get(9).getScore() + "");
                    set9 = true;
                    break;
            }
        }

            Log.d("set0 = ", set0 + "");
            if (!set0) {
                first_name.setText("");
                first_score.setText("");
            }
            Log.d("set1 = ", set1 + "");
            if (!set1) {
                second_name.setText("");
                second_score.setText("");
            }
            if (!set2) {
                third_name.setText("");
                third_score.setText("");
            }
            if (!set3) {
                fourth_name.setText("");
                fourth_score.setText("");
            }
            if (!set4) {
                fifth_name.setText("");
                fifth_score.setText("");
            }
            if (!set5) {
                sixth_name.setText("");
                sixth_score.setText("");
            }
            if (!set6) {
                seventh_name.setText("");
                seventh_score.setText("");
            }
            if (!set7) {
                eighth_name.setText("");
                eighth_score.setText("");
            }
            if (!set8) {
                ninth_name.setText("");
                ninth_score.setText("");
            }
            if (!set9) {
                tenth_name.setText("");
                tenth_score.setText("");
            }
        }
/*
        //First place
        if(leadership_arr.get(0) != null){
            first_name.setText(leadership_arr.get(0).getName());
            first_score.setText(leadership_arr.get(0).getScore() + "");
        }
        else{
            first_name.setText("");
            first_score.setText("");
        }

        //Second place
        if(leadership_arr.get(1) != null){
            second_name.setText(leadership_arr.get(1).getName());
            second_score.setText(leadership_arr.get(1).getScore() + "");
        }
        else{
            second_name.setText("");
            second_score.setText("");
        }

        //Third place
        if(leadership_arr.get(2) != null){
            third_name.setText(leadership_arr.get(2).getName());
            third_score.setText(leadership_arr.get(2).getScore() + "");
        }
        else{
            third_name.setText("");
            third_score.setText("");
        }

        //Fourth place
        if(leadership_arr.get(3) != null){
            fourth_name.setText(leadership_arr.get(3).getName());
            fourth_score.setText(leadership_arr.get(3).getScore() + "");
        }
        else{
            fourth_name.setText("");
            fourth_score.setText("");
        }

        //Fifth place
        if(leadership_arr.get(4) != null){
            fifth_name.setText(leadership_arr.get(4).getName());
            fifth_score.setText(leadership_arr.get(4).getScore() + "");
        }
        else{
            fifth_name.setText("");
            fifth_score.setText("");
        }

        //Sixth place
        if(leadership_arr.get(5) != null){
            sixth_name.setText(leadership_arr.get(5).getName());
            sixth_score.setText(leadership_arr.get(5).getScore() + "");
        }
        else{
            sixth_name.setText("");
            sixth_score.setText("");
        }

        //Seventh place
        if(leadership_arr.get(6) != null){
            seventh_name.setText(leadership_arr.get(6).getName());
            seventh_score.setText(leadership_arr.get(6).getScore() + "");
        }
        else{
            seventh_name.setText("");
            seventh_score.setText("");
        }

        //Eighth place
        if(leadership_arr.get(7) != null){
            eighth_name.setText(leadership_arr.get(7).getName());
            eighth_score.setText(leadership_arr.get(7).getScore() + "");
        }
        else{
            eighth_name.setText("");
            eighth_score.setText("");
        }

        //Ninth place
        if(leadership_arr.get(8) != null){
            ninth_name.setText(leadership_arr.get(8).getName());
            ninth_score.setText(leadership_arr.get(8).getScore() + "");
        }
        else{
            ninth_name.setText("");
            ninth_score.setText("");
        }

        //Tenth place
        if(leadership_arr.get(9) != null){
            tenth_name.setText(leadership_arr.get(9).getName());
            tenth_score.setText(leadership_arr.get(9).getScore() + "");
        }
        else{
            tenth_name.setText("");
            tenth_score.setText("");
        } */




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
