package com.example.pass_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PoliceEntry extends AppCompatActivity {
     Button scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_entry);
        scan=(Button)findViewById(R.id.qr);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PoliceEntry.this,ScannedBarcodeActivity.class));
            }
        });

    }
}
