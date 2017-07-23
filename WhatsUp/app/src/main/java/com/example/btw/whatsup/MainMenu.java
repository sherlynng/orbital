package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
/**
 * Created by BTW on 5/21/2017.
 */

public class MainMenu extends Activity implements OnClickListener {

    protected SharedPreferences gameDataLame;
    protected SharedPreferences gameDataEasy;
    protected SharedPreferences gameDataMedium;
    protected SharedPreferences gameDataHard;
    protected SharedPreferences gameDataExtreme;
    protected SharedPreferences gameDataTeamUp;
    protected SharedPreferences settings;
    protected SharedPreferences.Editor editorLame;
    protected SharedPreferences.Editor editorEasy;
    protected SharedPreferences.Editor editorMedium;
    protected SharedPreferences.Editor editorHard;
    protected SharedPreferences.Editor editorExtreme;
    protected SharedPreferences.Editor editorTeamUp;

    boolean hasOldGameToContinueLame;
    boolean hasOldGameToContinueEasy;
    boolean hasOldGameToContinueMedium;
    boolean hasOldGameToContinueHard;
    boolean hasOldGameToContinueExtreme;
    boolean hasOldGameToContinueTeamUp;

    protected boolean continueMusic = true;
    private boolean isMoreButton;
    private String gameID;

//    boolean hasOldGameToContinue;
    //   int oldGameType;

    private static final int RC_SIGN_IN = 0;
    FirebaseAuth auth;

    View continueButton;
    View newButton;
    View aboutButton;
    View moreButton;
    View leadershipButton;
    View howToPlayButton;
    View settingsButton;
    View userButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        auth = FirebaseAuth.getInstance();

        //prompt the user to login if first time running the app or not already logged in

        final String PREFS_NAME = "MyPrefsFile";
        settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            Intent i = new Intent(this, loginlogout.class);
            this.startActivity(i);
            openGameIdDialog();
        }

        isMoreButton = settings.getBoolean("isMoreButton", true);

        gameDataLame = getSharedPreferences("gameDataLame", Context.MODE_PRIVATE);
        gameDataEasy = getSharedPreferences("gameDataEasy", Context.MODE_PRIVATE);
        gameDataMedium = getSharedPreferences("gameDataMedium", Context.MODE_PRIVATE);
        gameDataHard = getSharedPreferences("gameDataHard", Context.MODE_PRIVATE);
        gameDataExtreme = getSharedPreferences("gameDataExtreme", Context.MODE_PRIVATE);
        gameDataTeamUp = getSharedPreferences("gameDataTeamUp", Context.MODE_PRIVATE);
        editorLame = gameDataLame.edit();
        editorEasy = gameDataEasy.edit();
        editorMedium = gameDataMedium.edit();
        editorHard = gameDataHard.edit();
        editorExtreme = gameDataExtreme.edit();
        editorTeamUp = gameDataTeamUp.edit();


        //Set up click listeners for all buttons
        hasOldGameToContinueLame = gameDataLame.getBoolean("hasoldgametocontinueLame", false);
        hasOldGameToContinueEasy = gameDataEasy.getBoolean("hasoldgametocontinueEasy", false);
        hasOldGameToContinueMedium = gameDataMedium.getBoolean("hasoldgametocontinueMedium", false);
        hasOldGameToContinueHard = gameDataHard.getBoolean("hasoldgametocontinueHard", false);
        hasOldGameToContinueExtreme = gameDataExtreme.getBoolean("hasoldgametocontinueExtreme", false);
        hasOldGameToContinueTeamUp = gameDataTeamUp.getBoolean("hasoldgametocontinueTeamUp", false);
        Log.d("SAVE", "lame+ " + Boolean.toString(hasOldGameToContinueLame));
        Log.d("SAVE", "easy+ " + Boolean.toString(hasOldGameToContinueEasy));
        Log.d("SAVE", "medium+ " + Boolean.toString(hasOldGameToContinueMedium));
        Log.d("SAVE", "hard+ " + Boolean.toString(hasOldGameToContinueHard));
        Log.d("SAVE", "extreme+ " + Boolean.toString(hasOldGameToContinueExtreme));
        Log.d("SAVE", "teamUp+ " + Boolean.toString(hasOldGameToContinueTeamUp));


        //    hasOldGameToContinue = gameData.getBoolean("hasoldgametocontinue", false);
        //  oldGameType = gameData.getInt("oldGameType", -1);

        //Continue button not visible if user not logged in or there's no old game
        continueButton = this.findViewById(R.id.continue_button);
        if (auth.getCurrentUser() == null ||
                (!hasOldGameToContinueLame &&
                        !hasOldGameToContinueEasy &&
                        !hasOldGameToContinueMedium &&
                        !hasOldGameToContinueHard &&
                        !hasOldGameToContinueExtreme &&
                        !hasOldGameToContinueTeamUp)) {
            continueButton.setVisibility(View.GONE);
        } else {
            continueButton.setBackgroundResource(R.drawable.continue_btn_state);
            continueButton.setOnClickListener(this);
        }

        newButton = this.findViewById(R.id.new_button);
        newButton.setBackgroundResource(R.drawable.new_game_btn_state);
        newButton.setOnClickListener(this);
        userButton = this.findViewById(R.id.user_button);
        userButton.setBackgroundResource(R.drawable.user_btn_state);
        userButton.setOnClickListener(this);
        aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setBackgroundResource(R.drawable.about_btn_state);
        aboutButton.setOnClickListener(this);
        howToPlayButton = this.findViewById(R.id.howtoplay_button);
        howToPlayButton.setBackgroundResource(R.drawable.howtoplay_btn_state);
        howToPlayButton.setOnClickListener(this);
        settingsButton = this.findViewById(R.id.settings_button);
        settingsButton.setBackgroundResource(R.drawable.settings_btn_state);
        settingsButton.setOnClickListener(this);
        leadershipButton = this.findViewById(R.id.leadership_button);
        leadershipButton.setBackgroundResource(R.drawable.leadership_btn_state);
        leadershipButton.setOnClickListener(this);
        moreButton = this.findViewById(R.id.more_button);
        if(isMoreButton) {
            moreButton.setBackgroundResource(R.drawable.more_btn_state);
            userButton.setVisibility(View.INVISIBLE);
            aboutButton.setVisibility(View.INVISIBLE);
            settingsButton.setVisibility(View.INVISIBLE);
            howToPlayButton.setVisibility(View.INVISIBLE);
            leadershipButton.setVisibility(View.INVISIBLE);
        }
        else{
            moreButton.setBackgroundResource(R.drawable.cancel_btn_state);
            userButton.setVisibility(View.VISIBLE);
            aboutButton.setVisibility(View.VISIBLE);
            settingsButton.setVisibility(View.VISIBLE);
            howToPlayButton.setVisibility(View.VISIBLE);
            leadershipButton.setVisibility(View.VISIBLE);
        }
        moreButton.setOnClickListener(this);




        ImageView logo = (ImageView) findViewById(R.id.whatsup_logo);
        logo.setImageResource(R.mipmap.whatsup_logo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Music.play(this,R.raw.bgm);
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //   Music.stop(this);
        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    private void startDemo(){
        Intent demo = new Intent(this, PlayDemo.class);
        this.startActivity(demo);
    }

    @Override
    public void onBackPressed() {
        openExitDialog();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.more_button:
                if(isMoreButton){
                    isMoreButton = false;
                    settings.edit().putBoolean("isMoreButton", isMoreButton).commit();
                //    moreButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                    moreButton.setBackgroundResource(R.drawable.cancel_btn_state);
                    userButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                    aboutButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                    settingsButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                    howToPlayButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                    leadershipButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                }
                else{
                    isMoreButton = true;
                    settings.edit().putBoolean("isMoreButton", isMoreButton).commit();
             //       moreButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
                    moreButton.setBackgroundResource(R.drawable.more_btn_state);
                    userButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                    aboutButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                    settingsButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                    howToPlayButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                    leadershipButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
                }
                break;
            case R.id.leadership_button:
                gameID = getGameID();
                if(isOnline()) {
                    if(gameID.equals("")){
                        openGameIdDialog();
                        openSetGameIdDialog();
                    }
                    else {
                        Intent leadership = new Intent(this, LeadershipChooseLevel.class);
                        this.startActivity(leadership);
                    }
                }
                else{
                    openConnectInternetDialog();
                }
                break;
            case R.id.settings_button:
                Intent settings = new Intent(this, Settings.class);
                this.startActivity(settings);
                break;
            case R.id.user_button:
                openGameIdDialog();
                break;
            case R.id.about_button:
                Intent about = new Intent(this, About.class);
                editorLame.putBoolean("continuefromlastLame", false).commit();
                editorEasy.putBoolean("continuefromlastEasy", false).commit();
                editorMedium.putBoolean("continuefromlastMedium", false).commit();
                editorHard.putBoolean("continuefromlastHard", false).commit();
                editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                this.startActivity(about);
                break;
            case R.id.howtoplay_button:
                Intent demo = new Intent(this, PlayDemo.class);
                editorLame.putBoolean("continuefromlastLame", false).commit();
                editorEasy.putBoolean("continuefromlastEasy", false).commit();
                editorMedium.putBoolean("continuefromlastMedium", false).commit();
                editorHard.putBoolean("continuefromlastHard", false).commit();
                editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                this.startActivity(demo);
                break;
            case R.id.continue_button:
                if (hasOldGameToContinueLame) {
                    Intent continuefromlast = new Intent(this, ContinueScreen.class);
                    continuefromlast.putExtra(ContinueScreen.MODE, 1);
                    editorLame.putBoolean("continuefromlastLame", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Lame");

                } else if (hasOldGameToContinueEasy) {
                    Intent continuefromlast = new Intent(this, ContinueScreen.class);
                    continuefromlast.putExtra(ContinueScreen.MODE, 2);
                    editorEasy.putBoolean("continuefromlastEasy", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Easy");

                } else if (hasOldGameToContinueMedium) {
                    Intent continuefromlast = new Intent(this, ContinueScreen.class);
                    continuefromlast.putExtra(ContinueScreen.MODE, 3);
                    editorMedium.putBoolean("continuefromlastMedium", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Medium");

                } else if (hasOldGameToContinueHard) {
                    Intent continuefromlast = new Intent(this, ContinueScreen.class);
                    continuefromlast.putExtra(ContinueScreen.MODE, 4);
                    editorHard.putBoolean("continuefromlastHard", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Hard");
                } else if (hasOldGameToContinueExtreme) {
                    Intent continuefromlast = new Intent(this, ContinueScreen.class);
                    continuefromlast.putExtra(ContinueScreen.MODE, 5);
                    editorExtreme.putBoolean("continuefromlastExtreme", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume Extreme");
                } else if (hasOldGameToContinueTeamUp) {
                    Intent continuefromlast = new Intent(this, ContinueScreen.class);
                    continuefromlast.putExtra(ContinueScreen.MODE, 6);
                    editorTeamUp.putBoolean("continuefromlastTeamUp", true).commit();
                    this.startActivity(continuefromlast);
                    Log.d("SAVE", "attempt to resume TEAMUP");
                }
                break;
            case R.id.new_button:
                gameID = getGameID();
                if(gameID.equals("")){
                    openGameIdDialog();
                    openSetGameIdDialog();
                }
                else{
                    if(auth.getCurrentUser() != null){
                        Intent new_Game = new Intent(this, ChoosePlayerMode.class);
                        editorLame.putBoolean("continuefromlastLame", false).commit();
                        editorEasy.putBoolean("continuefromlastEasy", false).commit();
                        editorMedium.putBoolean("continuefromlastMedium", false).commit();
                        editorHard.putBoolean("continuefromlastHard", false).commit();
                        editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                        editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                        this.startActivity(new_Game);
                    }
                    else{
                        openAuthDialog();
                    }
                }
                break;
        }
    }

    private String getGameID(){
       return settings.getString("game_id", "");
    }

    private void openSetGameIdDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please set your Game ID.");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void openGameIdDialog(){
    //    AlertDialog.Builder builder = new AlertDialog.Builder(this);
     //   LayoutInflater inflater = this.getLayoutInflater();
     //   final AlertDialog.Builder builder1 = builder.setView(inflater.inflate(game_id), null);

        final LayoutInflater layoutInflater = this.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.game_id, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Game ID");
     //   alertDialog.setIcon("Icon id here");
        alertDialog.setCancelable(false);
     //   Constant.alertDialog.setMessage("Your Message Here");

        final EditText gameID_text = (EditText) view.findViewById(R.id.game_id);
        String prev_game_id = settings.getString("game_id", null);
        if(prev_game_id != null){
            gameID_text.setText(prev_game_id);
        }

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String game_id = gameID_text.getText().toString();
                settings.edit().putString("game_id", game_id).commit();
                if (settings.getBoolean("my_first_time", true)){
                    settings.edit().putBoolean("my_first_time", false).commit();
                    startDemo();
                }
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (settings.getBoolean("my_first_time", true)){
                    settings.edit().putBoolean("my_first_time", false).commit();
                    startDemo();
                }
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }

    public void openConnectInternetDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please turn on Internet connection.");
        alertDialogBuilder.setPositiveButton("Got it!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    protected boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public void openExitDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to exit?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainMenu.this, "Hope you enjoyed playing What's Up!", Toast.LENGTH_SHORT).show();
                        finishAffinity();
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

    public void openAuthDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("You are not logged in yet. Game progress cannot be saved.");
        alertDialogBuilder.setPositiveButton("Log in ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        startActivityForResult(AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setProviders(
                                        AuthUI.FACEBOOK_PROVIDER,
                                        AuthUI.EMAIL_PROVIDER,
                                        AuthUI.GOOGLE_PROVIDER)
                                .build(), RC_SIGN_IN);
                    }
                });

        alertDialogBuilder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent new_Game = new Intent(MainMenu.this, ChoosePlayerMode.class);
                editorLame.putBoolean("continuefromlastLame", false).commit();
                editorEasy.putBoolean("continuefromlastEasy", false).commit();
                editorMedium.putBoolean("continuefromlastMedium", false).commit();
                editorHard.putBoolean("continuefromlastHard", false).commit();
                editorExtreme.putBoolean("continuefromlastExtreme", false).commit();
                editorTeamUp.putBoolean("continuefromlastTeamUp", false).commit();
                MainMenu.this.startActivity(new_Game);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
