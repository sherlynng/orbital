package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by BTW on 5/24/2017.
 */

public class ChooseUpMode extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public int up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseupmode);
        View button = this.findViewById(R.id.manual_button);
        button.setOnClickListener(this);
        View button2 = this.findViewById(R.id.random_button);
        button2.setOnClickListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.gridsize_spinner);
        spinner.setOnItemSelectedListener(this);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gridsize_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
      //  up = Integer.valueOf((String)parent.getItemAtPosition(pos));
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manual_button:
                Intent i = new Intent(this, ChooseUpManual.class);
                this.startActivity(i);
                break;
            case R.id.random_button:
                Intent j = new Intent(this, ChooseUpRandom.class);
                this.startActivity(j);
                break;
        }
    }
}