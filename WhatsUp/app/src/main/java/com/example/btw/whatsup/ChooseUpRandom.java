package com.example.btw.whatsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by BTW on 5/24/2017.
 */

public class ChooseUpRandom extends Activity implements View.OnClickListener {

    public int up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        up = ThreadLocalRandom.current().nextInt(3, 9 + 1);
        setContentView(R.layout.chooseuprandom);
        TextView v= (TextView) findViewById(R.id.UPdigit_display);
        v.setText(up + "");
        View button = this.findViewById(R.id.next_button_random);
        button.setOnClickListener(this);

    }

    public void onClick(View v) {
        Intent intent= new Intent(ChooseUpRandom.this,Main.class);
        intent.putExtra(Main.UPDIGIT,up);
        startActivity(intent);
    }
}