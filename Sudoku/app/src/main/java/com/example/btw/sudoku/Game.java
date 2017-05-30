package com.example.btw.sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import static android.content.Intent.getIntent;

/**
 * Created by BTW on 5/12/2017.
 */


public class Game extends Activity{
    public static final String TAG = "Soduku";

    public static final String KEY_DIFFICULTY = "difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_MEDIUM = 1;
    public static final int DIFFICULTY_HARD = 2;

    private static final String PREF_PUZZLE = "puzzle";
    protected static final int DIFFICULTY_CONTINUE = -1;

    private final String easyPuzzle =
            "360000000004230800000004200" +
                    "070460003820000014500013020" +
                    "001900000007048300000000045";
    private final String mediumPuzzle =
            "650000070000506000014000005" +
                    "007009000002314700000700800" +
                    "500000630000201000030000097";
    private final String hardPuzzle =
            "009000000080605020501078000" +
                    "000000700706040102004000000" +
                    "000720903090301080000000600";

    private int puzzle[] = new int[9 * 9];

    private PuzzleView puzzleView;


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        Log.d(TAG, "onCreate");

        //diff contains the user selected difficulty level(0/1/2), default is easy (0)
        int diff = getIntent().getIntExtra(KEY_DIFFICULTY, DIFFICULTY_EASY);
        puzzle = getPuzzle(diff);
        calculateUsedTiles();

        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();

        //if activity is restarted, do a continue next time
        getIntent().putExtra(KEY_DIFFICULTY,DIFFICULTY_CONTINUE);
    }

    @Override
    protected void  onResume(){
        super.onResume();
        Music.play(this,R.raw.game);
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause");
        Music.stop(this);

        //save the current puzzle
        getPreferences(MODE_PRIVATE).edit().putString(PREF_PUZZLE,toPuzzleString(puzzle)).commit();
    }

    //given a diffuclty, return a new puzzle
    private int[] getPuzzle(int diff) {
        String puz;
        switch (diff) {
            case DIFFICULTY_CONTINUE:
                puz=getPreferences(MODE_PRIVATE).getString(PREF_PUZZLE,easyPuzzle);
                break;
            case DIFFICULTY_HARD:
                puz = hardPuzzle;
                break;
            case DIFFICULTY_MEDIUM:
                puz = mediumPuzzle;
                break;
            case DIFFICULTY_EASY:
            default:
                puz = easyPuzzle;
                break;
        }
        return fromPuzzleString(puz);
    }

    //convert array into a puzzle string
    static private String toPuzzleString(int[] puz) {
        StringBuilder buf = new StringBuilder();
        for (int element : puz) {
            buf.append(element);
        }
        return buf.toString();
    }

    //convert a puzzle string into an array
    static protected int[] fromPuzzleString(String str) {
        int[] puz = new int[str.length()];
        for (int i = 0; i < puz.length; i++) {
            puz[i] = str.charAt(i) - '0';
        }
        return puz;
    }

    //return tile at given coords
    private int getTile(int x, int y) {
        return puzzle[y * 9 + x];
    }

    //change tile at given coords
    private void setTile(int x, int y, int value) {
        puzzle[y * 9 + x] = value;
    }

    //return a string for the tile at given coords
    //for display purpose
    protected String getTileString(int x, int y){
        int v=getTile(x,y);
        if(v==0)
            return "";
        else return String.valueOf(v);
    }

    //change the tile only if it's a valid move
    protected boolean setTileIfValid(int x, int y, int value) {
        int tiles[] = getUsedTiles(x, y); //list of alreay filled-in tiles
        if (value != 0) {
            for (int tile : tiles) {
                if (tile == value)
                    return false;
            }
        }
        setTile(x, y, value);
        calculateUsedTiles();
        return true;
    }



    //cache of used tiles
    //[9][9][], an array of size 9 with each cell contains an array of size 9( each inner cell contain an array of different size)
    private final int used[][][] = new int[9][9][];

    //return cached used tiles visible from current coords
    protected int[] getUsedTiles(int x, int y) {
        return used[x][y];
    }

    //compute 2D array of used tiles, recauculate only when necessay
    //calls calculatedUsedTiles(x,y) in turn on every position in the 9 by 9 grid
    //find out which numbers are not valid for the tile based on Sudoky rules
    private void calculateUsedTiles() {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                used[x][y] = calculateUsedTiles(x, y);
                //Log.d(TAG,"used["+x+"]["+y+"]="
                //+toPuzzleString(used[x][y]);
            }
        }
    }

    protected int[] calculateUsedTiles(int x, int y) {
        int c[] = new int[9];

        // /horizontal
        for (int i = 0; i < 9; i++) {
            if (i == y)
                continue;
            int t = getTile(x, i);
            if (t != 0)
                c[t - 1] = t;
        }

        //vertical
        for (int i = 0; i < 9; i++) {
            if (i == x)
                continue;
            int t = getTile(i, y);
            if (t != 0)
                c[t - 1] = t;
        }

        //same cell block
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y)
                    continue;
                int t = getTile(i, j);
                if (t != 0)
                    c[t - 1] = t;
            }
        }

        //compress
        int nused = 0;
        for (int t : c) {
            if (t != 0)
                nused++;
        }
        int c1[] = new int[nused];
        nused = 0;
        for (int t : c) {
            if (t != 0)
                c1[nused++] = t;
        }
        return c1;
    }

    //open the keyboard if there are any valid moves
    protected void showKeypadOrError(int x, int y) {
        // in order to show only valid inputs on Keypad, pass in extraData area an array containing all numbers already used
        int tiles[] = getUsedTiles(x, y);
        if (tiles.length == 9) {
            Toast toast = Toast.makeText(this, R.string.no_moves_label, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Log.d(TAG, "showKeypad:used=" + toPuzzleString(tiles));
            Dialog v = new Keypad(this, tiles, puzzleView);
            v.show();
        }
    }

}
