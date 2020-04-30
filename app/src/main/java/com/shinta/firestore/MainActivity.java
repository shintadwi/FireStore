package com.shinta.firestore;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button mSaveBtn;
    private EditText mMainText, mMainText1;
    private TextView mListText;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        mFirestore = FirebaseFirestore.getInstance();

        mMainText = findViewById(R.id.mainText);
        mMainText1 = findViewById(R.id.mainText2);
        mSaveBtn = findViewById(R.id.saveBtn);
        mListText = findViewById(R.id.textView1);
        mListText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent registerIntent = new Intent(MainActivity.this, ListDataActivity.class);
//                startActivity(registerIntent);
                Toast.makeText(MainActivity.this, "Buka Acitiviy yang menampilkan ListView", Toast.LENGTH_SHORT).show();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mMainText.getText().toString();
                String stambuk = mMainText1.getText().toString();

                Map<String, String> userMap = new HashMap<>();

                userMap.put("nama", username);
                userMap.put("stambuk", stambuk);

                mFirestore.collection("pengguna").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Pengguna Ditambahakan di Firestore", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String error = e.getMessage();
                        Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
