package com.example.btw.sudoku;

/**
 * Created by BTW on 5/12/2017.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class Keypad extends Dialog{
    protected static final String TAG = "Sudoku";

    private final View keys[] = new View[9];
    private View keypad;

    private final int useds[];
    private final PuzzleView puzzleView;

    public Keypad(Context context, int useds[], PuzzleView puzzleView) {
        super(context);
        this.useds = useds;
        this.puzzleView = puzzleView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keypad);
        findViews(); //helper 1
        for (int element : useds) {
            if (element != 0)
                keys[element - 1].setVisibility(View.INVISIBLE);
        }
        setListeners(); //helper 2
    }

    //fetches and saves the views for all keypad keys and main keypad window
    private void findViews() {
        keypad = findViewById(R.id.keypad); //keypad.xml
        keys[0] = findViewById(R.id.keypad_1);  //button views
        keys[1] = findViewById(R.id.keypad_2);
        keys[2] = findViewById(R.id.keypad_3);
        keys[3] = findViewById(R.id.keypad_4);
        keys[4] = findViewById(R.id.keypad_5);
        keys[5] = findViewById(R.id.keypad_6);
        keys[6] = findViewById(R.id.keypad_7);
        keys[7] = findViewById(R.id.keypad_8);
        keys[8] = findViewById(R.id.keypad_9);
    }

    //loop through all keypad keys and sets listener for each one, also for main keypad window
    private void setListeners() {
        for (int i = 0; i < keys.length; i++) {
            final int t = i + 1;
            keys[i].setOnClickListener(new View.OnClickListener() {
                //when player selects one of the buttons with number, call returnResult() with that number
                public void onClick(View v) {
                    returnResult(t);
                }
            });
        }

        keypad.setOnClickListener(new View.OnClickListener() {
            //when player selects a place with no button, call returenResult with zero, indicating the tile should be erased
            public void onClick(View v) {
                returnResult(0);
            }
        });
    }

    //called when player uses keyboard to enter a number
    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event){
        int tile =0;
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                tile = 0;
                break;
            case KeyEvent.KEYCODE_1:
                tile = 1;
                break;
            case KeyEvent.KEYCODE_2:
                tile = 2;
                break;
            case KeyEvent.KEYCODE_3:
                tile = 3;
                break;
            case KeyEvent.KEYCODE_4:
                tile = 4;
                break;
            case KeyEvent.KEYCODE_5:
                tile = 5;
                break;
            case KeyEvent.KEYCODE_6:
                tile = 6;
                break;
            case KeyEvent.KEYCODE_7:
                tile = 7;
                break;
            case KeyEvent.KEYCODE_8:
                tile = 8;
                break;
            case KeyEvent.KEYCODE_9:
                tile = 9;
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }
        //if number is valid for current tile, calls returnResult, else keystroke is ignored
        if(isValid(tile)){
            returnResult(tile);
        }
        return true;
    }

    //if number already appeared in useds, it is invalid
    private boolean isValid(int tile){
        for(int t:useds){
            if(tile==t){
                return false;
            }
        }
        return true;
    }

    //return the chosen tile to caller
    private void returnResult(int tile){
        puzzleView.setSelectedTile(tile); //change puzzle's current tile
        dismiss(); //terminates Keypad dialog box
    }


}
