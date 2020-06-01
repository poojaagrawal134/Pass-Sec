package com.example.pass_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Police extends AppCompatActivity {
   EditText e,p;
   Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        e=(EditText)findViewById(R.id.ppemail);
        p=(EditText)findViewById(R.id.pppas);
        log=(Button)findViewById(R.id.pplogin);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.getText().toString().equals("policehu@gmail.com") && p.getText().toString().equals("policehu"))
                {
                    startActivity(new Intent(Police.this,PoliceEntry.class));
                }
                else
                    Toast.makeText(Police.this, "Invalid Email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
