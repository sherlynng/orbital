package com.example.btw.whatsup;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by BTW on 5/11/2017.
 */

public class GameOver extends Activity implements View.OnClickListener {


    protected SharedPreferences.Editor editor;
    public static final String REASON = "REASON";
    public static final int REASON_DEFAULT = 0;
    private int reason;
    protected boolean continueMusic = true;
    public static final String CALLEE = "CALLEE";
    private int callee;
    public static final int CALLEE_DEFAULT = 0;

    protected SharedPreferences gameDataLame;
    protected SharedPreferences gameDataEasy;
    protected SharedPreferences gameDataMedium;
    protected SharedPreferences gameDataHard;
    protected SharedPreferences gameDataExtreme;
    protected SharedPreferences gameDataTeamUp;
    protected SharedPreferences.Editor editorLame;
    protected SharedPreferences.Editor editorEasy;
    protected SharedPreferences.Editor editorMedium;
    protected SharedPreferences.Editor editorHard;
    protected SharedPreferences.Editor editorExtreme;
    protected SharedPreferences.Editor editorTeamUp;

    boolean hasOldGameToContinueLame;
    boolean hasOldGameToContinueEasy;
    boolean hasOldGameToContinueMedium;
    boolean hasOldGameToContinueHard;
    boolean hasOldGameToContinueExtreme;
    boolean hasOldGameToContinueTeamUp;

    TextView message;
    TextView yourScore;
    TextView yourBestScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        reason = getIntent().getIntExtra(REASON, REASON_DEFAULT);
        callee = getIntent().getIntExtra(CALLEE, CALLEE_DEFAULT);

        setContentView(R.layout.gameover);

        gameDataLame = getSharedPreferences("gameDataLame", Context.MODE_PRIVATE);
        gameDataEasy = getSharedPreferences("gameDataEasy", Context.MODE_PRIVATE);
        gameDataMedium = getSharedPreferences("gameDataMedium", Context.MODE_PRIVATE);
        gameDataHard = getSharedPreferences("gameDataHard", Context.MODE_PRIVATE);
        gameDataExtreme = getSharedPreferences("gameDataExtreme", Context.MODE_PRIVATE);
        gameDataTeamUp = getSharedPreferences("gameDataTeamUp", Context.MODE_PRIVATE);
        editorLame = gameDataLame.edit();
        editorEasy = gameDataEasy.edit();
        editorMedium = gameDataMedium.edit();
        editorHard = gameDataHard.edit();
        editorExtreme = gameDataExtreme.edit();
        editorTeamUp = gameDataTeamUp.edit();


        //Set up click listeners for all buttons
        hasOldGameToContinueLame = gameDataLame.getBoolean("hasoldgametocontinueLame", false);
        hasOldGameToContinueEasy = gameDataEasy.getBoolean("hasoldgametocontinueEasy", false);
        hasOldGameToContinueMedium = gameDataMedium.getBoolean("hasoldgametocontinueMedium", false);
        hasOldGameToContinueHard = gameDataHard.getBoolean("hasoldgametocontinueHard", false);
        hasOldGameToContinueExtreme = gameDataExtreme.getBoolean("hasoldgametocontinueExtreme", false);
        hasOldGameToContinueTeamUp = gameDataTeamUp.getBoolean("hasoldgametocontinueTeamUp", false);


        message = (TextView) findViewById(R.id.gameover_message);
        yourScore = (TextView) findViewById(R.id.yourScore_text);
        yourBestScore = (TextView) findViewById(R.id.yourBestScore_text);

        switch (callee) {
            case 1:
                yourScore.setText(gameDataLame.getInt("score", 0) + "");
                yourBestScore.setText(gameDataLame.getInt("bestScore", 0) + "");
                if (Integer.parseInt(yourBestScore.getText().toString()) == Integer.valueOf(yourScore.getText().toString())) {
                    shareScore(1);
                }
                break;
            case 2:
                yourScore.setText(gameDataEasy.getInt("score", 0) + "");
                yourBestScore.setText(gameDataEasy.getInt("bestScore", 0) + "");
                if (Integer.parseInt(yourBestScore.getText().toString()) == Integer.valueOf(yourScore.getText().toString())) {
                    shareScore(2);
                }
                break;
            case 3:
                yourScore.setText(gameDataMedium.getInt("score", 0) + "");
                yourBestScore.setText(gameDataMedium.getInt("bestScore", 0) + "");
                if (Integer.parseInt(yourBestScore.getText().toString()) == Integer.valueOf(yourScore.getText().toString())) {
                    shareScore(3);
                }
                break;
            case 4:
                yourScore.setText(gameDataHard.getInt("score", 0) + "");
                yourBestScore.setText(gameDataHard.getInt("bestScore", 0) + "");
                if (Integer.parseInt(yourBestScore.getText().toString()) == Integer.valueOf(yourScore.getText().toString())) {
                    shareScore(4);
                }
                break;
            case 5:
                yourScore.setText(gameDataExtreme.getInt("score", 0) + "");
                yourBestScore.setText(gameDataExtreme.getInt("bestScore", 0) + "");
                if (Integer.parseInt(yourBestScore.getText().toString()) == Integer.valueOf(yourScore.getText().toString())) {
                    shareScore(5);
                }
                break;
            case 6:
                yourScore.setText(gameDataTeamUp.getInt("score", 0) + "");
                yourBestScore.setText(gameDataTeamUp.getInt("bestScore", 0) + "");
                if (Integer.parseInt(yourBestScore.getText().toString()) == Integer.valueOf(yourScore.getText().toString())) {
                    shareScore(6);
                }

        }

        switch (reason) {
             /* REASON for ending game:
      1: Time is up
      2: did not press up
      3: no more lives
      */
            case 1:
                message.setText(getResources().getString(R.string.GameOver_Message1));
                break;
            case 2:
            case 3:
                message.setText(getResources().getString(R.string.GameOver_Message3));
        }
        View resBtn = this.findViewById(R.id.restart_btn);
        resBtn.setOnClickListener(this);
        View mainBtn = this.findViewById(R.id.mainMenu_btn);
        mainBtn.setOnClickListener(this);
        if (callee == 1) {
            editorLame.putBoolean("hasoldgametocontinueLame", false);
        } else if (callee == 2) {
            editorEasy.putBoolean("hasoldgametocontinueEasy", false);
        } else if (callee == 3) {
            editorMedium.putBoolean("hasoldgametocontinueMedium", false);
        } else if (callee == 4) {
            editorHard.putBoolean("hasoldgametocontinueHard", false);
        } else if (callee == 5) {
            editorExtreme.putBoolean("hasoldgametocontinueExtreme", false);
        } else if (callee == 6) {
            editorTeamUp.putBoolean("hasoldgametocontinueTeamUp", false);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.restart_btn:
                Intent i = new Intent(this, ChooseLevel.class);
                this.startActivity(i);
                break;
            case R.id.mainMenu_btn:
                Intent j = new Intent(this, MainMenu.class);
                this.startActivity(j);
        }
    }

    protected void shareScore(int type) {
        String game_type = "";

        switch (type) {
            case 1:
                game_type = "Lame";
                break;
            case 2:
                game_type = "Easy";
                break;
            case 3:
                game_type = "Medium";
                break;
            case 4:
                game_type = "Hard";
                break;
            case 5:
                game_type = "Extreme";
                break;
            case 6:
                game_type = "TeamUP";
                break;
        }

        //method 1: sharing text
       /*
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"I just scored "+yourScore.getText()+" for " + game_type + " game in What's UP! See if you can do better!");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Excellent!");
            startActivity(Intent.createChooser(shareIntent, "Broke my own record! Share my score to"));
*/

        //method 2: sharing screen capture
     /*   View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        shareImage(store(getScreenShot(rootView), "screencapture"), game_type);
*/
        //method 3: sharing existing image
        Uri imageUri;
        imageUri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + "up_bear2");
//imageUri = Uri.parse("android.resource://" + getPackageName() + R.drawable.image1);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//sharingIntent.putExtra(Intent.EXTRA_TEXT, "Ha Ha! Check out this picure I got with todays date! Download the app: http://www.google.com");
        sharingIntent.setType("image/jpg");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Bitmap imgBitmap = BitmapFactory.decodeFile("android.resource://" + getPackageName() + "/drawable/" + "up_bear2");
        String imgBitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), imgBitmap, "title", null);
        Uri imgBitmapUri = Uri.parse("android.resource://" + getPackageName() + "/drawable/" + "up_bear2");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
        sharingIntent.putExtra(Intent.EXTRA_TEXT,"I just scored "+yourScore.getText()+" for " + game_type + " game in What's UP! See if you can do better!");
        startActivity(Intent.createChooser(sharingIntent, "Broke my own record! Share my score to"));
    }

    public Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        screenView.layout(0, 0, screenView.getMeasuredWidth(), screenView.getMeasuredHeight());
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public File store(Bitmap bm, String fileName) {
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void shareImage(File file, String game_type) {
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_TEXT, "I just scored " + yourScore.getText() + " for " + game_type + " game in What's UP! See if you can do better!");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Excellent!");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Broke your own record! Share your score to"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
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
        MusicManager.start(this, MusicManager.MUSIC_END_GAME);
    }
}