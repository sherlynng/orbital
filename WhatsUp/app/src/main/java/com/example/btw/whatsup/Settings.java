package com.example.btw.whatsup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class Settings extends PreferenceActivity {
    //implements Preference.OnPreferenceClickListener {

    //option names and default values
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
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
/*
       @Override
       public boolean onPreferenceClick(Preference preference) {
        //   CheckBoxPreference pref = (CheckBoxPreference) findPreference("music");
          // CheckBoxPreference pref2 = (CheckBoxPreference) findPreference("vibration");

           String key = preference.getKey();
/*
           if(key.equals("music")) {
               CheckBoxPreference pref = (CheckBoxPreference) findPreference("music");
               if (pref.isChecked()) {
                   pref.setChecked(false);
                   MusicManager.start(this, MusicManager.MUSIC_MENU);
                   Main4.playMusic = false;
               } else if (!pref.isChecked()) {
                   pref.setChecked(true);
                   MusicManager.start(this, MusicManager.MUSIC_MENU);
                   Main4.playMusic = true;
               }
           }
           else if(key.equals("vibration")) {
               CheckBoxPreference pref2 = (CheckBoxPreference) findPreference("vibration");
               if (pref2.isChecked()) {
                   pref2.setChecked(false);
                   Main4.vibrationOn = false;
               } else if (!pref2.isChecked()) {
                   pref2.setChecked(true);
                   Main4.vibrationOn = true;
               }
           }

          if(key.equals("gridSize")) {
               ListPreference gridPreference = (ListPreference) findPreference("gridSize");
             //  CharSequence gridEntry = gridPreference.getEntry();
               String gridValue = gridPreference.getValue();
               ChooseLevel.gridSize = Integer.valueOf(gridValue);
           }
           else if(key.equals("upDigit")) {
               ListPreference upPreference = (ListPreference) findPreference("upDigit");
            //   CharSequence upEntry = upPreference.getEntry();
               String upValue = upPreference.getValue();
               ChooseLevel.upDigit = Integer.valueOf(upValue);
           }
           return false;
       }


    /*
    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals("music")) {
            MusicManager.start(this, MusicManager.MUSIC_MENU);
            return true;
        }
        else{
            MusicManager.start(this, MusicManager.MUSIC_MENU);
            return false;
        }

    }
*/
}
