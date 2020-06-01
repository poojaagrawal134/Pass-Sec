package com.example.pass_sec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScannedBarcodeActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    TextView txtBarcodeValue,txtName,txtContact,txtLocationfrom,txtLocationto;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    String intentData = "",urldata="";
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    ImageView img;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        initViews();
    }

    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        txtName=findViewById(R.id.txtName);
        txtContact=findViewById(R.id.txtContact);
        txtLocationfrom=findViewById(R.id.txtLocationFrom);
        txtLocationto=findViewById(R.id.txtLocationTo);
        img=findViewById(R.id.img);
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0) != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText("Barcode Detected");
                                final DocumentReference documentReference = fstore.collection("users").document(intentData);
                                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                        txtName.setText("Name:"+documentSnapshot.getString("Name"));
                                        txtContact.setText("Contact No:"+documentSnapshot.getString("ContactNo"));
                                        txtLocationfrom.setText("Location From:"+documentSnapshot.getString("LocationFrom"));
                                        txtLocationto.setText("Location To:"+documentSnapshot.getString("LocationTo"));
                                        try
                                        {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                            urldata=documentSnapshot.getString("Image");
                                            URL url = new URL(urldata);
                                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                            conn.setReadTimeout(60000 /* milliseconds */);
                                            conn.setConnectTimeout(65000 /* milliseconds */);
                                            conn.setRequestMethod("GET");
                                            conn.setDoInput(true);
                                            conn.connect();
                                            InputStream is;
                                            is = conn.getInputStream();
                                            BufferedInputStream bufferedInputStream = new BufferedInputStream(is);

                                            Bitmap b = BitmapFactory.decodeStream(bufferedInputStream);
                                            img.setImageBitmap(b);

                                        } catch(Exception ex){Log.d(TAG, "Error"+String.valueOf(ex));}
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }
}

