package com.example.btw.sudoku;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by BTW on 5/19/2017.
 */

public class Music {
    private static MediaPlayer mp = null;

    //Stop old song and start new one
    public static void play(Context context, int resource) {
        stop(context);

        //start music only if not disabled in settings
        if (Settings.getMusic(context)) {
            mp = MediaPlayer.create(context, resource);
            mp.setLooping(true);
            mp.start();
        }
    }

    //Stop the music
    public static void stop(Context context) {
        if (mp != null) {  //defensive check
            mp.stop();
            mp.release();  //release resource, prevent crashing
            mp = null;
        }
    }
}
