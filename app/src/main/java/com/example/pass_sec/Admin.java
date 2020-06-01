package com.example.pass_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin extends AppCompatActivity {
    EditText e,p;
    Button log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        e=(EditText)findViewById(R.id.aaemail);
        p=(EditText)findViewById(R.id.aapas);
        log=(Button)findViewById(R.id.aalogin);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.getText().toString().equals("adminhu@gmail.com") && p.getText().toString().equals("adminhu"))
                {
                    startActivity(new Intent(Admin.this,AdminEntry.class));
                }
                else
                    Toast.makeText(Admin.this, "Invalid Email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
