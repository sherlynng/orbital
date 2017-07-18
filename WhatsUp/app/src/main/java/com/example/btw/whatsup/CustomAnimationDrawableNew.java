package com.example.btw.whatsup;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;

/**
 * Created by sherl on 17/7/2017.
 */

public class CustomAnimationDrawableNew extends AnimationDrawable {

    /** Handles the animation callback. */
    Handler mAnimationHandler;
    AnimationDrawable anim;

    public CustomAnimationDrawableNew(AnimationDrawable aniDrawable) {
        /* Add each frame to our animation drawable */
     //   anim = aniDrawable;
        for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++) {
            this.addFrame(aniDrawable.getFrame(i), aniDrawable.getDuration(i));
        }
    }


    public void start(AnimationDrawable aniDrawable) {
        super.start();
        /*
         * Call super.start() to call the base class start animation method.
         * Then add a handler to call onAnimationFinish() when the total
         * duration for the animation has passed
         */
        anim = aniDrawable;
        mAnimationHandler = new Handler();
        mAnimationHandler.postDelayed(new Runnable() {

            public void run() {
                onAnimationFinish();
            }
        }, getTotalDuration());

    }

    /**
     * Gets the total duration of all frames.
     *
     * @return The total duration.
     */
    public int getTotalDuration() {

        int iDuration = 0;

        for (int i = 0; i < this.getNumberOfFrames(); i++) {
            iDuration += this.getDuration(i);
        }

        return iDuration;
    }

    /**
     * Called when the animation finishes.
     */
    public void onAnimationFinish(){
        anim.stop();
        anim.setCallback(null);
        anim = null;
        setCallback(null);
      //  System.gc();
        Log.d("garbage", "cleared");
    };
}