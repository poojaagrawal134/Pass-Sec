package com.example.pass_sec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class Home extends AppCompatActivity {

    Button details;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ImageView img;
    StorageReference storageReference;
    FirebaseUser user;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        details=(Button)findViewById(R.id.details);
        img=findViewById(R.id.barcode);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        fstore = FirebaseFirestore.getInstance();
        StorageReference profileRef=storageReference.child("barcode/"+fAuth.getCurrentUser().getUid());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,User.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.log:
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
            break;

        }
        return super.onOptionsItemSelected(item);
    }
}
