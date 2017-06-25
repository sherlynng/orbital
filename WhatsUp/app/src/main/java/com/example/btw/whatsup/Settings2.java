package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Switch;


public class Settings2 extends PreferenceActivity {

    //option names and default values
    private static final String OPT_MUSIC ="music";
    private static final boolean OPT_MUSIC_DEF =true;
    private static final String OPT_HINTS="hints";
    private static final boolean OPT_HINTS_DEF =true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings2);

        // initiate a Switch
        Switch simpleSwitch = (Switch) findViewById(R.id.music_switch);

        //set the current state of a Switch
      //  simpleSwitch.setChecked(true);
    }

    //get current value of music option
    public static boolean getMusic(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(OPT_MUSIC,OPT_MUSIC_DEF);
    }
/*
    //get current value of hints option
    public static boolean getHints(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(OPT_HINTS,OPT_HINTS_DEF);
    }
*/
}
