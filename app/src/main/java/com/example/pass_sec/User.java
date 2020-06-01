package com.example.pass_sec;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class User extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ImageView imageView;
    StorageReference storageReference;
    FirebaseUser user;
    TextView name,email,contact,loactionfrom,locationto,age;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
         name=(TextView)findViewById(R.id.uuname);
         imageView=(ImageView)findViewById(R.id.imageView);
        age=(TextView)findViewById(R.id.uuage);
        email=(TextView)findViewById(R.id.uuemail);
        loactionfrom=(TextView)findViewById(R.id.uulocationfrom);
        locationto=(TextView)findViewById(R.id.uulocationto);
        contact=(TextView)findViewById(R.id.uucontact);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        final FirebaseUser user = fAuth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        fstore = FirebaseFirestore.getInstance();
        StorageReference profileRef=storageReference.child("images/"+fAuth.getCurrentUser().getUid());
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageView);
            }
        });
        final DocumentReference documentReference = fstore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("Name"));
                age.setText(documentSnapshot.getString("Age"));
                email.setText(documentSnapshot.getString("Email"));
                contact.setText(documentSnapshot.getString("ContactNo"));
                loactionfrom.setText(documentSnapshot.getString("LocationFrom"));
                locationto.setText(documentSnapshot.getString("LocationTo"));
            }
        });
    }
}
