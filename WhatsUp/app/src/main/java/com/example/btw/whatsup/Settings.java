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
import android.support.annotation.NonNull;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Settings extends PreferenceActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 0;
    FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //option names and default values
    private static final String OPT_LOGIN = "music";
    private static final boolean OPT_LOGIN_DEF = true;
    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;
    private static final String OPT_HINTS = "hints";
    private static final boolean OPT_HINTS_DEF = true;
    private static final String OPT_VIBRATION = "vibration";
    private static final boolean OPT_VIBRATION_DEF = true;
    private static final String OPT_GRID = "gridSize";
    private static final String OPT_GRID_DEF = "4";
    private static final String OPT_UP = "upDigit";
    private static final String OPT_UP_DEF = "0";
    protected boolean continueMusic = true;


    Preference authButton;
    Preference addToDababaseButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("AUTH", "enter setting");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        auth = FirebaseAuth.getInstance();


        authButton = findPreference("signin");
        if (auth.getCurrentUser() == null) {
            Log.d("AUTH", "user not logged in");
            authButton.setTitle("Log in");
        } else {
            Log.d("AUTH", "user logged in");
            authButton.setTitle("Log Out");
        }
        authButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                if (auth.getCurrentUser() != null) {
                    // User is currently signed in
                    AuthUI.getInstance()
                            .signOut(Settings.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("AUTH", "user logged out");
                                }
                            });

                } else {
                    // User is currently signed out
                    Intent i = new Intent(Settings.this, SignIn.class);
                    startActivity(i);
                }
                if (auth.getCurrentUser() != null) {
                    authButton.setTitle("Log Out");
                } else {
                    authButton.setTitle("Log In");
                }
                return true;
            }
        });

        addToDababaseButton = findPreference("myplayerprofile");
        addToDababaseButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if(auth.getCurrentUser()==null){
                    toastMessage("Directing to sign in page");
                    Intent i = new Intent(Settings.this, SignIn.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(Settings.this, MyPlayerProfile.class);
                    startActivity(i);
                }
                return true;
            }
        });
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

    public static int getGridSize(Context context) {
        return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_GRID, OPT_GRID_DEF));
    }

    public static int getUpDigit(Context context) {
        return Integer.valueOf(PreferenceManager.getDefaultSharedPreferences(context).getString(OPT_UP, OPT_UP_DEF));
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

    @Override
    public void onClick(View v) {
        Log.d("switch", "directed to login/out");
        Intent i = new Intent(this, SignIn.class);
        this.startActivity(i);
    }
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
