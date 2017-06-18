package com.example.btw.whatsup;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class Settings extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    //option names and default values
    private static final String OPT_MUSIC = "music";
    private static final boolean OPT_MUSIC_DEF = true;
    private static final String OPT_HINTS = "hints";
    private static final boolean OPT_HINTS_DEF = true;
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

    //get current value of hints option
    public static boolean getHints(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(OPT_HINTS, OPT_HINTS_DEF);
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

    /*   @Override
       public boolean onPreferenceClick(Preference preference) {
           CheckBoxPreference pref = (CheckBoxPreference) findPreference("music");

           if (pref.isChecked()) {
               pref.setChecked(false);
               MusicManager.start(this, MusicManager.MUSIC_MENU);
           }else if (!pref.isChecked()){
               pref.setChecked(true);
               MusicManager.start(this, MusicManager.MUSIC_MENU);
           }
           return false;
       }
   */
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

}
