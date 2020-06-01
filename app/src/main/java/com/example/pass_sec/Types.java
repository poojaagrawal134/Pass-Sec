package com.example.pass_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Types extends AppCompatActivity {
   Button emaile,police,admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
        emaile=(Button)findViewById(R.id.email);
        police=(Button)findViewById(R.id.google);
        admin=(Button)findViewById(R.id.fb);
        emaile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Types.this,Login.class));
            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Types.this,Police.class));
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Types.this,Admin.class));
            }
        });
    }
}
