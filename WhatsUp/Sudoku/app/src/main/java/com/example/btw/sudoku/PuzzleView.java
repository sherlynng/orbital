package com.example.btw.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by BTW on 5/12/2017.
 */

//completely customized view, easier to do in java than in XML

public class PuzzleView extends View {
    private static final String TAG = "Sudoku";
    private final Game game;

    private static final String SELX="selX";
    private static final String SELY="selY";
    private static final String VIEW_STATE ="viewState";
    private static final int ID=42;

    private float width; //width of one tile
    private float height; //height of one tile
    private int selX;  //X index of selection (0-8)
    private int selY;  //Y index of selection (0-8)
    private final Rect selRect = new Rect(); //rectangle to keep track of selection cursor

    public PuzzleView(Context context) {
        super(context);
        game = (Game) context;
        //For a key press to affect a component, the component must have the keyboard focus.
        setFocusable(true);
        setFocusableInTouchMode(true);
        //set ID for PuzzleView. Android will walk down view hierarchy and call view.onSaveInstanceState() on every view it finds that has an ID
        setId(ID);
        /*do not set width/height of the view in constructor, used onSizeChanged method
        real sizes are caculated during layout stage, after consturction and before anything is drawn
        */

    }

    @Override
    protected Parcelable onSaveInstanceState(){
        Parcelable p = super.onSaveInstanceState();
        Log.d(TAG,"onSavedInstanceState");
        Bundle bundle = new Bundle();
        bundle.putInt(SELX,selX);
        bundle.putInt(SELY,selY);
        bundle.putParcelable(VIEW_STATE,p);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state){
        Log.d(TAG,"onRestoreInstanceState");
        Bundle bundle =(Bundle)state;
        select(bundle.getInt(SELX),bundle.getInt(SELY));
        super.onRestoreInstanceState(bundle.getParcelable(VIEW_STATE));
        return;
    }

    @Override
    //calculate the size of each tile on the screen
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w / 9f;
        height = h / 9f;
        getRect(selX, selY, selRect);
        Log.d(TAG, "onSizeChanged:width" + width + ",height" + height);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //called everytime part of the view needs to be updated
    //pretend complete recreation of entire screen, actual clipping done by Android
    protected void onDraw(Canvas canvas) {
        //draw background
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), background);

        //draw the board
        //define color for grid lines
        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark));
        Paint hilite = new Paint();
        hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.puzzle_light));

        //draw the minor grid lines
        for (int i = 0; i < 9; i++) {
            //horizontal(bottom-up)
            canvas.drawLine(0, i * height, getWidth(), i * height, light);
            //horizontal shade
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
            //vertical (left-right)
            canvas.drawLine(i * width, 0, i * width, getHeight(), light);
            //vertical shade
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
        }

        //draw the major grid line(lines drawn later will OVERLAY on top)
        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0)
                continue;
            //horizontal
            canvas.drawLine(0, i * height, getWidth(), i * height, dark);
            //horizontal shade
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
            //vertical
            canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
            //vertical shade
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
        }

        //draw the numbers
        //define color and style for numbers
        Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
        foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
        foreground.setStyle(Style.FILL);
        foreground.setTextSize(height * 0.75f);
        foreground.setTextScaleX(width / height);
        foreground.setTextAlign(Paint.Align.CENTER); // reference point is center of baseline(default:top left hand corner

        //draw numbers in center of tile
        FontMetrics fm = foreground.getFontMetrics();
        //Centering in X: use alignment ( and X at midpoint)
        float x = width / 2;
        //Centering in Y: measure ascent/descent first as
        float y = height / 2 - (fm.ascent + fm.descent) / 2; //(fm.ascent + fm.descent) is actual height of the text
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(this.game.getTileString(i, j), i * width + x, j * height + y, foreground);
            }
        }


        //draw selection
        Log.d(TAG, "selRect=" + selRect);
        Paint selected = new Paint();
        selected.setColor(getResources().getColor(R.color.puzzle_selected));
        canvas.drawRect(selRect, selected);

        //draw the hints: pick a hint color based on #moves left
        if (Settings.getHints(getContext())) {
            Paint hint = new Paint();

            int c[] = {getResources().getColor(R.color.puzzle_hint_0),
                    getResources().getColor(R.color.puzzle_hint_1),
                    getResources().getColor(R.color.puzzle_hint_2),};
            Rect r = new Rect();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int movesLeft = 9 - game.getUsedTiles(i, j).length;
                    if (movesLeft < c.length) {  //only draw if there are 0/1/2 moves, do not draw if there are >2
                        getRect(i, j, r);
                        hint.setColor(c[movesLeft]);
                        canvas.drawRect(r, hint);
                    }
                }
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);
        select((int) (event.getX() / width), (int) (event.getY() / height));
        game.showKeypadOrError(selX, selY);
        Log.d(TAG, "onTouchEvent:x " + selX + ", y " + selY);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "onKeyDown:keycode=" + keyCode + ",event=" + event);
        switch (keyCode) {
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                setSelectedTile(0);
                break; //0 or space means erase the number
            case KeyEvent.KEYCODE_1:
                setSelectedTile(1);
                break;
            case KeyEvent.KEYCODE_2:
                setSelectedTile(2);
                break;
            case KeyEvent.KEYCODE_3:
                setSelectedTile(3);
                break;
            case KeyEvent.KEYCODE_4:
                setSelectedTile(4);
                break;
            case KeyEvent.KEYCODE_5:
                setSelectedTile(5);
                break;
            case KeyEvent.KEYCODE_6:
                setSelectedTile(6);
                break;
            case KeyEvent.KEYCODE_7:
                setSelectedTile(7);
                break;
            case KeyEvent.KEYCODE_8:
                setSelectedTile(8);
                break;
            case KeyEvent.KEYCODE_9:
                setSelectedTile(9);
                break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                game.showKeypadOrError(selX, selY);
                break; //pop out keypad for D pad
            case KeyEvent.KEYCODE_DPAD_UP:
                if (selY - 1 < 0)
                    select(selX, 8);
                else
                    select(selX, (selY - 1) % 9);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                select(selX, (selY + 1) % 9);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (selX - 1 < 0)
                    select(8, selY);
                else select((selX - 1) % 9, selY);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                select((selX + 1) % 9, selY);
                break;
            default:
                return super.onKeyDown(keyCode, event);
        }
        return true;
    }


    /*pre: x, y are coordinates of selected minor grid boxes
    no actual drawing done here. NEVER call any drawing function except in onDraw() method.
    use invalidate() to mark things needs to be redrawn as dirty (clip region)
    */
    private void select(int x, int y) {
        //old selection rect needs to be redrawn
        invalidate(selRect);
        selX = x;
        selY = y;
        getRect(selX, selY, selRect);
        //new selection rect needs to be redrawn
        invalidate(selRect);
    }

    private void getRect(int x, int y, Rect rect) {
        //rect.set(left, top, right, bottom)
        rect.set((int) (x * width), (int) (y * height), (int) (x * width + width), (int) (y * height + height));
    }

    /*change the number on a tile to input
     */
    public void setSelectedTile(int tile) {
        if (game.setTileIfValid(selX, selY, tile)) {
            //actuall setting (if valid) done in setTileIfValid
            invalidate(); //mark whole screen as dirty(will be redrawn later) asnew number added or removed may change hints given next
        } else {
            //number is not valid for this tile, shake screen
            Log.d(TAG, "setSelectedTile:invalid: " + tile);
            startAnimation(AnimationUtils.loadAnimation(game, R.anim.shake));
        }
    }

}
