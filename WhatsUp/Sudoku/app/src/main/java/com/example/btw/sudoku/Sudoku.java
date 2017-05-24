package com.example.btw.sudoku;

//NEVER use import wildcards
//buttons

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;

public class Sudoku extends Activity implements OnClickListener {

    @Override
    //when app is first launched
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Set up click listeners for all buttons
        View continueButton = this.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        View newButton = this.findViewById(R.id.new_button);
        newButton.setOnClickListener(this);
        View aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View exitButton = this.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

    @Override
    protected void  onResume(){
        super.onResume();
        Music.play(this,R.raw.main);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Music.stop(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_button:
                //must create an instance of Intent before running the activity
                Intent i = new Intent(this, About.class);
                this.startActivity(i);
                break;
            case R.id.continue_button:
                startGame(Game.DIFFICULTY_CONTINUE);
                break;
            case R.id.new_button:
                openNewGameDialog();
                break;
            case R.id.exit_button:
                finish();
                break;

            //More buttons to go
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        //MenuInflater read the menu definitions from XML and turns it into real view
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                this.startActivity(new Intent(this, Settings.class));
                //Settings is a class that dispalys all preferences and allows users to change them
                return true;
            //More items to go here
        }
        return false;
    }

    private static final String TAG = "Sudoku";

    /**
     * Ask the user which difficulty to play
     */
    private void openNewGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.new_game_title)
                .setItems(R.array.difficulty,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startGame(i);
                            }
                        })
                .show();
    }

    /**
     * Start a new game with given difficulty
     */
    private void startGame(int i) {
        Log.d(TAG, "clicked on " + i);
        //Start game here
        Intent intent= new Intent(Sudoku.this,Game.class);
        intent.putExtra(Game.KEY_DIFFICULTY,i); //extraData is a map of key(string)/value(in this case int) pairs
        startActivity(intent);
    }

}


