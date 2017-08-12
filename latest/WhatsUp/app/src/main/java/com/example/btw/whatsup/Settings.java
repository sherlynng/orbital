package com.example.btw.whatsup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class Settings extends PreferenceActivity {

    FirebaseAuth auth;
    //option names and default values
    private static final String OPT_LOGIN = "music";
    private static final boolean OPT_LOGIN_DEF = true;
    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;
    private static final String OPT_HINTS = "hints";
    private static final boolean OPT_HINTS_DEF = true;
    private static final String OPT_VIBRATION = "vibration";
    private static final boolean OPT_VIBRATION_DEF = true;
 //   private static final String OPT_GRID = "gridSize";
 //   private static final String OPT_GRID_DEF = "4";
    private static final String OPT_UP = "upDigit";
    private static final String OPT_UP_DEF = "0";
 //   private static final String OPT_GAMEID = "gameID";
  //  private static final String OPT_GAMEID_DEF = "Gamer1";
    protected boolean continueMusic = true;
    ViewGroup main;
    View loginlogoutButtonView;
    Button loginlogoutButton;

    Preference button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("AUTH", "enter setting");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    /*    auth = FirebaseAuth.getInstance();

        button = findPreference("loginlogout");
        if (auth.getCurrentUser() == null) {
            Log.d("AUTH", "user not logged in");
            button.setTitle("Log in");
        } else {
            Log.d("AUTH", "user logged in");
            button.setTitle("Log Out");
        }
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //code for what you want it to do
                Log.d("switch", "directed to login/out");
                Intent i = new Intent(Settings.this, loginlogout.class);
                Settings.this.startActivity(i);
                if(button.getTitle().equals("Log Out")){
                    button.setTitle("Log In");
                    Toast.makeText(Settings.this, "You are logged out", Toast.LENGTH_SHORT).show();
                } else{
                    button.setTitle("Log Out");
                }
                return true;
            }
        }); */

    }

    //get current value of music option
    public static boolean getMusic(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(OPT_MUSIC, OPT_MUSIC_DEF);
    }

    //get current value of vibration option
    public static boolean getVibration(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(OPT_VIBRATION, OPT_VIBRATION_DEF);
    }

    //get current value of hints option
    public static boolean getHints(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(OPT_HINTS, OPT_HINTS_DEF);
    }

    //get current login status
    public static boolean getLogin(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(OPT_LOGIN, OPT_LOGIN_DEF);
    }
/*
    public static int getGridSize(Context context) {
        return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_GRID, OPT_GRID_DEF));
    }
*/
    public static int getUpDigit(Context context) {
        return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_UP, OPT_UP_DEF));
    }
/*
    public static String getGameID(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_GAMEID, OPT_GAMEID_DEF);
    }
*/
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
/*
    @Override
    public void onClick(View v) {
        Log.d("switch", "directed to login/out");
        Intent i = new Intent(this, loginlogout.class);
        this.startActivity(i);
    } */
}
