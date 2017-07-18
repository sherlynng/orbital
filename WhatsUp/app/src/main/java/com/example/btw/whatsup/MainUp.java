package com.example.btw.whatsup;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by BTW on 5/24/2017.
 */

public class MainUp extends Activity implements OnClickListener, View.OnTouchListener {

    public static final String KEY = "KEY";
    public static final String KEY_DEFAULT = "hello";
    String key;
    protected boolean continueMusic = true;
    private int UP;
    private int current;
    private ArrayList<Integer> fillArr = new ArrayList<Integer>();  //arraylist that contains new numbers to replace tapped numbers
    private String fillArrContent = "";
    private int pressedNum;
    private int pressedID;
    private Button pressedButton;
    private LinearLayout bg;

    DatabaseReference currentGameDb;
    private int opponent_score;
    private int my_score;
    private int opponent_current;
    private int my_current;
    private String state;
    private ValueEventListener listener;

    protected TextView myScoreText;
    protected TextView opponentScoreText;
    protected TextView upDisplayText;
    protected Button upBtn;
    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected Button btn4;
    protected Button btn5;
    protected Button btn6;
    protected Button btn7;
    protected Button btn8;
    protected Button btn9;
    protected Button btn10;
    protected Button btn11;
    protected Button btn12;
    protected Button btn13;
    protected Button btn14;
    protected Button btn15;
    protected Button btn16;

    public static boolean playMusic;
    public static boolean vibrationOn;

    private SoundPoolHelper mSoundPoolHelper;
    private int explodeId, tapId, upId;

    private Handler hint_handler;
    private Runnable hint_counter = new Runnable() {
        @Override
        public void run() {
            shakeHintTile();
        }
    };

    public static void checkMusic(Context context) {
        if (Settings.getMusic(context)) {
            playMusic = true;
        }
        else{
            playMusic = false;
        }
    }

    public static void checkVibration(Context context) {
        if (Settings.getVibration(context)) {
            vibrationOn = true;
        }
        else{
            vibrationOn = false;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainup);

        checkVibration(this);
        checkMusic(this);

        key = getIntent().getExtras().getString(KEY, KEY_DEFAULT);
        currentGameDb = FirebaseDatabase.getInstance().getReference("updown_games").child(key);

        mSoundPoolHelper = new SoundPoolHelper(1, this);
        explodeId = mSoundPoolHelper.load(this, R.raw.explode, 1);
        upId = mSoundPoolHelper.load(this, R.raw.up, 1);
        tapId = mSoundPoolHelper.load(this, R.raw.tap, 1);

        bg = (LinearLayout)this.findViewById(R.id.main);

        upBtn = (Button) this.findViewById(R.id.up_button);
        btn1 = (Button) this.findViewById(R.id.btn1);
        btn2 = (Button) this.findViewById(R.id.btn2);
        btn2.setBackgroundResource(R.drawable.orange_spark);
        btn3 = (Button) this.findViewById(R.id.btn3);
        btn3.setBackgroundResource(R.drawable.pink_spark);
        btn4 = (Button) this.findViewById(R.id.btn4);
        btn4.setBackgroundResource(R.drawable.green_spark);
        btn5 = (Button) this.findViewById(R.id.btn5);
        btn5.setBackgroundResource(R.drawable.green_spark);
        btn6 = (Button) this.findViewById(R.id.btn6);
        btn6.setBackgroundResource(R.drawable.yellow_spark);
        btn7 = (Button) this.findViewById(R.id.btn7);
        btn7.setBackgroundResource(R.drawable.orange_spark);
        btn8 = (Button) this.findViewById(R.id.btn8);
        btn8.setBackgroundResource(R.drawable.pink_spark);
        btn9 = (Button) this.findViewById(R.id.btn9);
        btn9.setBackgroundResource(R.drawable.pink_spark);
        btn10 = (Button) this.findViewById(R.id.btn10);
        btn10.setBackgroundResource(R.drawable.green_spark);
        btn11 = (Button) this.findViewById(R.id.btn11);
        btn11.setBackgroundResource(R.drawable.yellow_spark);
        btn12 = (Button) this.findViewById(R.id.btn12);
        btn12.setBackgroundResource(R.drawable.orange_spark);
        btn13 = (Button) this.findViewById(R.id.btn13);
        btn13.setBackgroundResource(R.drawable.orange_spark);
        btn14 = (Button) this.findViewById(R.id.btn14);
        btn14.setBackgroundResource(R.drawable.pink_spark);
        btn15 = (Button) this.findViewById(R.id.btn15);
        btn15.setBackgroundResource(R.drawable.green_spark);
        btn16 = (Button) this.findViewById(R.id.btn16);
        btn16.setBackgroundResource(R.drawable.yellow_spark);

        current = 1;
        initialiseGrid(1);
        populateArr(17);



        //321 animation
        Intent intent = new Intent(this, Onetwothree.class);
        intent.putExtra(Onetwothree.UPDIGIT, UP);
        startActivity(intent);
    }


    public boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart(){
        super.onStart();

        listener = currentGameDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MultiplayerGame game = dataSnapshot.getValue(MultiplayerGame.class);

                state = game.getState();

                if(state.equals("ENDGAME")){
                    currentGameDb.removeEventListener(listener);
                    Toast.makeText(MainUp.this, "Multiplayer game exited", Toast.LENGTH_SHORT).show();
                    backToMainMenu();
                    return;
                }

                UP = game.getUpDigit();
                if(state.equals("PLAY1")) {
                    opponent_current = game.getJoiner_current();
                    my_current = game.getCreator_current();

                    int creatorScore = my_current-1;
                    int joinerScore = 100 - opponent_current;

                    if(my_current > opponent_current){
                        currentGameDb.child("creator_score").setValue(creatorScore);
                        currentGameDb.child("joiner_score").setValue(joinerScore);
                        currentGameDb.child("state").setValue("END_ROUND1");
                    }
                }
                else{ //PLAY2
                    opponent_current = game.getCreator_current();
                    my_current = game.getJoiner_current();

                    if(my_current > opponent_current){
                        currentGameDb.child("state").setValue("END_ROUND2");
                    }
                }
                myScoreText = (TextView) findViewById(R.id.myScore);
                myScoreText.setText(my_current + "");
                opponentScoreText = (TextView) findViewById(R.id.opponentScore);
                opponentScoreText.setText(opponent_current + "");
                upDisplayText = (TextView) findViewById(R.id.UPDisplayText);
                upDisplayText.setText(UP + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit multiplayer game?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainUp.this, "Multiplayer game exited", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_GAME);

        //set onClickListeners for all buttons

        upBtn.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn1.setOnTouchListener(this);
        btn2.setOnClickListener(this);
        btn2.setOnTouchListener(this);
        btn3.setOnClickListener(this);
        btn3.setOnTouchListener(this);
        btn4.setOnClickListener(this);
        btn4.setOnTouchListener(this);
        btn5.setOnClickListener(this);
        btn5.setOnTouchListener(this);
        btn6.setOnClickListener(this);
        btn6.setOnTouchListener(this);
        btn7.setOnClickListener(this);
        btn7.setOnTouchListener(this);
        btn8.setOnClickListener(this);
        btn8.setOnTouchListener(this);
        btn9.setOnClickListener(this);
        btn9.setOnTouchListener(this);
        btn10.setOnClickListener(this);
        btn10.setOnTouchListener(this);
        btn11.setOnClickListener(this);
        btn11.setOnTouchListener(this);
        btn12.setOnClickListener(this);
        btn12.setOnTouchListener(this);
        btn13.setOnClickListener(this);
        btn13.setOnTouchListener(this);
        btn14.setOnClickListener(this);
        btn14.setOnTouchListener(this);
        btn15.setOnClickListener(this);
        btn15.setOnTouchListener(this);
        btn16.setOnClickListener(this);
        btn16.setOnTouchListener(this);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        TextView t;
        t = (TextView) findViewById(R.id.btn1);
        t.setText(savedInstanceState.getCharSequence("btn1"));
        t = (TextView) findViewById(R.id.btn2);
        t.setText(savedInstanceState.getCharSequence("btn2"));
        t = (TextView) findViewById(R.id.btn3);
        t.setText(savedInstanceState.getCharSequence("btn3"));
        t = (TextView) findViewById(R.id.btn4);
        t.setText(savedInstanceState.getCharSequence("btn4"));
        t = (TextView) findViewById(R.id.btn5);
        t.setText(savedInstanceState.getCharSequence("btn5"));
        t = (TextView) findViewById(R.id.btn6);
        t.setText(savedInstanceState.getCharSequence("btn6"));
        t = (TextView) findViewById(R.id.btn7);
        t.setText(savedInstanceState.getCharSequence("btn7"));
        t = (TextView) findViewById(R.id.btn8);
        t.setText(savedInstanceState.getCharSequence("btn8"));
        t = (TextView) findViewById(R.id.btn9);
        t.setText(savedInstanceState.getCharSequence("btn9"));
        t = (TextView) findViewById(R.id.btn10);
        t.setText(savedInstanceState.getCharSequence("btn10"));
        t = (TextView) findViewById(R.id.btn11);
        t.setText(savedInstanceState.getCharSequence("btn11"));
        t = (TextView) findViewById(R.id.btn12);
        t.setText(savedInstanceState.getCharSequence("btn12"));
        t = (TextView) findViewById(R.id.btn13);
        t.setText(savedInstanceState.getCharSequence("btn13"));
        t = (TextView) findViewById(R.id.btn14);
        t.setText(savedInstanceState.getCharSequence("btn14"));
        t = (TextView) findViewById(R.id.btn15);
        t.setText(savedInstanceState.getCharSequence("btn15"));
        t = (TextView) findViewById(R.id.btn16);
        t.setText(savedInstanceState.getCharSequence("btn16"));

        fillArr = savedInstanceState.getIntegerArrayList("fillArr");
        UP = savedInstanceState.getInt("UP");
        //Log.d("checkstate","UP restored as "+UP);
        pressedNum = savedInstanceState.getInt("pressedNum");
        //Log.d("checkstate","pressedNum restored as "+pressedNum);
        //Log.d("checkstate","score restored as "+score);
     //   life = savedInstanceState.getInt("life");
        //Log.d("checkstate","life restored as "+life);
        current = savedInstanceState.getInt("current");
        //Log.d("checkstate","current restored as "+current);

    }

    private void playSound(int soundId) {
        mSoundPoolHelper.play(soundId);
    }

    private void hintTimer() {

        if(current != 2){
            hint_handler.removeCallbacks(hint_counter);
        }

        hint_handler = new Handler();

        hint_handler.postDelayed(hint_counter, 3000);
    }

    private void shakeHintTile() {

        boolean isUp = checkUp();

        if(isUp){
            upBtn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }

        if (Integer.parseInt(btn1.getText().toString()) == current) {
            btn1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn2.getText().toString()) == current) {
            btn2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn3.getText().toString()) == current) {
            btn3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn4.getText().toString()) == current) {
            btn4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn5.getText().toString()) == current) {
            btn5.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn6.getText().toString()) == current) {
            btn6.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn7.getText().toString()) == current) {
            btn7.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn8.getText().toString()) == current) {
            btn8.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn9.getText().toString()) == current) {
            btn9.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn10.getText().toString()) == current) {
            btn10.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn11.getText().toString()) == current) {
            btn11.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn12.getText().toString()) == current) {
            btn12.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn13.getText().toString()) == current) {
            btn13.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn14.getText().toString()) == current) {
            btn14.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn15.getText().toString()) == current) {
            btn15.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
        if (Integer.parseInt(btn16.getText().toString()) == current) {
            btn16.startAnimation(AnimationUtils.loadAnimation(this, R.anim.hint_shake));
            return;
        }
    }

    public void Game() {

        boolean isUp;
        isUp = checkUp();
        if (isUp) {   //UP num
            if (pressedID == R.id.up_button) {  //Correct

                // Pass our animation drawable to our custom drawable class
                CustomAnimationDrawableNew cad = new CustomAnimationDrawableNew(
                        (AnimationDrawable) getResources().getDrawable(
                                R.drawable.up_animation));

                // Set the views drawable to our custom drawable
                bg.setBackgroundDrawable(cad);

                // Start the animation
                cad.start(cad);

            /*
                //Background animation
                bg.setBackgroundResource(R.drawable.up_animation);
                AnimationDrawable anim = (AnimationDrawable)bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();
*/
                //Sound effect
                if(playMusic) {
              //      up_sound.start();
                    playSound(upId);
                }


                if(vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(200);
                }

                findCurrent();
                current++;
                if(state.equals("PLAY1")) {
                    currentGameDb.child("creator_current").setValue(current);
                }
                else{
                    currentGameDb.child("joiner_current").setValue(current);
                }

                hintTimer();


            } else {//Wrong
               /*cdt.cancel();
               editor.putInt("bestScore", Math.max(score, gameDataLame.getInt("bestScore", -1))).commit();
                editor.putInt("score", score).commit();
                GameOver(2);
                */

                // Pass our animation drawable to our custom drawable class
                CustomAnimationDrawableNew cad = new CustomAnimationDrawableNew(
                        (AnimationDrawable) getResources().getDrawable(
                                R.drawable.bomb));

                // Set the views drawable to our custom drawable
                bg.setBackgroundDrawable(cad);

                // Start the animation
                cad.start(cad);
             /*
               //Background effect
                bg.setBackgroundResource(R.drawable.bomb);
                AnimationDrawable anim = (AnimationDrawable)bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                }
                anim.start();
                anim.setCallback(null);
*/
                //Sound effect
                if(playMusic){
        //             explode_sound.start();
                    playSound(explodeId);
                }

                if(vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(1000);
                }
/*
                life--;
                if (life <= 0) {
                    cdt.cancel();
                    editor.putInt("bestScore", Math.max(score, gameDataLame.getInt("bestScore", -1))).commit();
                    editor.putInt("score", score).commit();
                    GameOver(2);
                    return;
                }
 */               //Shake screen
                pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
            }
        } else {   //Not UP num
            if (pressedNum == current) {      //Correct

                //Spark animation
                AnimationDrawable anim = (AnimationDrawable)pressedButton.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                    anim.setCallback(null);
                }
                anim.start();


                //Sound effect
                if(playMusic) {
        //            tap_sound.start();
                    playSound(tapId);
                }

                if(vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(200);
                }

                changeNo(pressedButton);
                current++;
                if(state.equals("PLAY1")) {
                    currentGameDb.child("creator_current").setValue(current);
                }
                else{
                    currentGameDb.child("joiner_current").setValue(current);
                }

                hintTimer();


            } else {                            //Wrong

                // Pass our animation drawable to our custom drawable class
                CustomAnimationDrawableNew cad = new CustomAnimationDrawableNew(
                        (AnimationDrawable) getResources().getDrawable(
                                R.drawable.bomb));

                // Set the views drawable to our custom drawable
                bg.setBackgroundDrawable(cad);

                // Start the animation
                cad.start(cad);
             /*
                //Background effect
                bg.setBackgroundResource(R.drawable.bomb);
                AnimationDrawable anim = (AnimationDrawable)bg.getBackground();

                if (anim.isRunning()) {
                    anim.stop();
                    anim.setCallback(null);
                }
                anim.start();
          */

                //Sound effect
                if(playMusic) {
          //          explode_sound.start();
                    playSound(explodeId);
                }

                if(vibrationOn) {
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(1000);
                }

               //Shake screen
                pressedButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_strong));
            }
        }
    }

    public boolean checkUp() {
        return (current % UP == 0) || checkDigit();
    }

    public boolean checkDigit() {
        String cur = Integer.toString(current);
        String upString = Integer.toString(UP);

        return cur.contains(upString);
    }

    //Brute force
    //Finds and change the digit for UP num
    public void findCurrent() {
        if (Integer.parseInt(btn1.getText().toString()) == current) {
            changeNo(btn1);
            return;
        }
        if (Integer.parseInt(btn2.getText().toString()) == current) {
            changeNo(btn2);
            return;
        }
        if (Integer.parseInt(btn3.getText().toString()) == current) {
            changeNo(btn3);
            return;
        }
        if (Integer.parseInt(btn4.getText().toString()) == current) {
            changeNo(btn4);
            return;
        }
        if (Integer.parseInt(btn5.getText().toString()) == current) {
            changeNo(btn5);
            return;
        }
        if (Integer.parseInt(btn6.getText().toString()) == current) {
            changeNo(btn6);
            return;
        }
        if (Integer.parseInt(btn7.getText().toString()) == current) {
            changeNo(btn7);
            return;
        }
        if (Integer.parseInt(btn8.getText().toString()) == current) {
            changeNo(btn8);
            return;
        }
        if (Integer.parseInt(btn9.getText().toString()) == current) {
            changeNo(btn9);
            return;
        }
        if (Integer.parseInt(btn10.getText().toString()) == current) {
            changeNo(btn10);
            return;
        }
        if (Integer.parseInt(btn11.getText().toString()) == current) {
            changeNo(btn11);
            return;
        }
        if (Integer.parseInt(btn12.getText().toString()) == current) {
            changeNo(btn12);
            return;
        }
        if (Integer.parseInt(btn13.getText().toString()) == current) {
            changeNo(btn13);
            return;
        }
        if (Integer.parseInt(btn14.getText().toString()) == current) {
            changeNo(btn14);
            return;
        }
        if (Integer.parseInt(btn15.getText().toString()) == current) {
            changeNo(btn15);
            return;
        }
        if (Integer.parseInt(btn16.getText().toString()) == current) {
            changeNo(btn16);
            return;
        }
    }

    public void GameOver(int r) {
        Intent i = new Intent(this, GameOver.class);
        i.putExtra(GameOver.REASON, r);
        i.putExtra(GameOver.CALLEE, 1);
        this.startActivity(i);
    }


    protected void populateArr(int cur) {
        if (fillArr.isEmpty()) {
            for (int i = 0; i <= 9; i++) {
                fillArr.add(cur++);
            }
            Collections.shuffle(fillArr);
        }
    }

    protected void changeNo(Button btn) {
        if (fillArr.isEmpty()) {
            for (int i = current + 16; i <= current + 25; i++) {
                fillArr.add(i);
            }
            Collections.shuffle(fillArr);
        }
        btn.setText(fillArr.remove(0) + "");
    }

    //fill grid with numbers from start to start+24 in random order
    protected void initialiseGrid(int start) {
        ArrayList<Integer> arr = new ArrayList<Integer>();

        for (int i = start; i < start + 16; i++) {
            arr.add(i);
        }
        Collections.shuffle(arr);

        btn1.setText(arr.remove(0) + "");
        btn2.setText(arr.remove(0) + "");
        btn3.setText(arr.remove(0) + "");
        btn4.setText(arr.remove(0) + "");
        btn5.setText(arr.remove(0) + "");
        btn6.setText(arr.remove(0) + "");
        btn7.setText(arr.remove(0) + "");
        btn8.setText(arr.remove(0) + "");
        btn9.setText(arr.remove(0) + "");
        btn10.setText(arr.remove(0) + "");
        btn11.setText(arr.remove(0) + "");
        btn12.setText(arr.remove(0) + "");
        btn13.setText(arr.remove(0) + "");
        btn14.setText(arr.remove(0) + "");
        btn15.setText(arr.remove(0) + "");
        btn16.setText(arr.remove(0) + "");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch (view.getId())
        {
            case R.id.btn1:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn1.setBackgroundResource(R.drawable.yellow_btn_pressed);
                    break;
            case R.id.btn2:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn2.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn3:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn3.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn4:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn4.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn5:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn5.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn6:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn6.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
            case R.id.btn7:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn7.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn8:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn8.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn9:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn9.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn10:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn10.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn11:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn11.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
            case R.id.btn12:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn12.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn13:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn13.setBackgroundResource(R.drawable.orange_btn_pressed);
                break;
            case R.id.btn14:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn14.setBackgroundResource(R.drawable.pink_btn_pressed);
                break;
            case R.id.btn15:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn15.setBackgroundResource(R.drawable.green_btn_pressed);
                break;
            case R.id.btn16:
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    btn16.setBackgroundResource(R.drawable.yellow_btn_pressed);
                break;
        }
        return false;
    }

    public void onClick(View v) {

        Button temp;
        switch (v.getId()) {
            case R.id.up_button:
                temp = (Button) v;
                pressedButton = temp;
                pressedID = temp.getId();
                Game();
                break;
            case R.id.btn1:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn1.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn2:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn2.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn3:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn3.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn4:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn4.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn5:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn5.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn6:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn6.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn7:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn7.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn8:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn8.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn9:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn9.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn10:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn10.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn11:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn11.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
            case R.id.btn12:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn12.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn13:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn13.setBackgroundResource(R.drawable.orange_spark);
                Game();
                break;
            case R.id.btn14:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn14.setBackgroundResource(R.drawable.pink_spark);
                Game();
                break;
            case R.id.btn15:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn15.setBackgroundResource(R.drawable.green_spark);
                Game();
                break;
            case R.id.btn16:
                temp = (Button) v;
                pressedButton = temp;
                pressedNum = Integer.valueOf(temp.getText().toString());
                pressedID = temp.getId();
                btn16.setBackgroundResource(R.drawable.yellow_spark);
                Game();
                break;
        }
    }
}
