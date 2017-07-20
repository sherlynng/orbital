package com.example.btw.whatsup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

/**
 * Created by BTW on 6/11/2017.
 */

public class ChooseMultiplayerGame extends Activity implements OnClickListener {
    protected boolean continueMusic = true;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twoplayer);

        auth = FirebaseAuth.getInstance();

        View hurryUpBtn = this.findViewById(R.id.hurryUp_button);
        hurryUpBtn.setBackgroundResource(R.drawable.hurryup_btn_state);
        hurryUpBtn.setOnClickListener(this);
        View upAndDownBtn = this.findViewById(R.id.UpAndDown_button);
        upAndDownBtn.setBackgroundResource(R.drawable.updown_btn_state);
        upAndDownBtn.setOnClickListener(this);
        View teamUpBtn = this.findViewById(R.id.TeamUp_button);
        teamUpBtn.setBackgroundResource(R.drawable.teamup_btn_state);
        teamUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.hurryUp_button:
                /*
                if (auth.getCurrentUser() != null) {
                    Intent game = new Intent(this, HurryUpGameStates.class);
                    this.startActivity(game);
                } else {
                    openAuthDialog();
                } */
                if (isOnline()) {
                    Intent game = new Intent(this, HurryUpGameStates.class);
                    this.startActivity(game);
                } else {
                    openConnectInternetDialog();
                }

                break;

            case R.id.UpAndDown_button:
                /*
                if (auth.getCurrentUser() != null) {
                    Intent game2 = new Intent(this, UpDownGameStates.class);
                    this.startActivity(game2);
                } else {
                    openAuthDialog();
                }
                */
                if (isOnline()) {
                    Intent game2 = new Intent(this, UpDownGameStates.class);
                    this.startActivity(game2);
                } else {
                    openConnectInternetDialog();
                }
                break;

            case R.id.TeamUp_button:
                Intent game3 = new Intent(this, MainTeamUp.class);
                this.startActivity(game3);
                break;
        }
    }

    public void openAuthDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Unable to play without logging in.");
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

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void openConnectInternetDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Unable to play without Internet connection.");
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