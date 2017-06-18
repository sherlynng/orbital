package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by BTW on 6/11/2017.
 */

public class ChooseGridSize extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public static final String UPDIGIT = "UPDIGIT";
    private int UP;
    public String gridsize;
    public Spinner spinner;
    protected boolean continueMusic = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosegridsize);

        UP = getIntent().getIntExtra("UPDIGIT", 1);

        spinner = (Spinner) findViewById(R.id.gridsize_spinner);
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gridsize_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        View button = this.findViewById(R.id.LetsPlay_button);
        button.setOnClickListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
     //   gridsize = Integer.valueOf((String) parent.getItemAtPosition(pos));
        gridsize = (String) parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    //TODO: create new graphcs for StartGame btn
    @Override
    public void onClick(View v) {
        switch (gridsize) {
            case "4x4":
                Intent fourbyfour = new Intent(ChooseGridSize.this, Main4.class);
                fourbyfour.putExtra(Main4.UPDIGIT, UP);
                startActivity(fourbyfour);
                break;
            case "5x5":
                Intent fivebyfive = new Intent(ChooseGridSize.this, Main5.class);
                fivebyfive.putExtra(Main5.UPDIGIT, UP);
                startActivity(fivebyfive);
                break;
            case "6x6":
                Intent sixbysix = new Intent(ChooseGridSize.this, Main6.class);
                sixbysix.putExtra(Main6.UPDIGIT, UP);
                startActivity(sixbysix);
                break;
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
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }
}
