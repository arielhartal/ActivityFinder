package com.example.user.activityfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    public static FirebaseDatabase nFirebaseDatabase = FirebaseDatabase.getInstance();
    public static DatabaseReference UserRef = nFirebaseDatabase.getReference().child("Users");
    public static DatabaseReference EventRef = nFirebaseDatabase.getReference().child("Event");
    TextView mActivityFinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityFinder = findViewById(R.id.textViewActivityFinder);
        mActivityFinder = (TextView) findViewById(R.id.textViewActivityFinder);
        Button register = (Button) findViewById(R.id.registerbutton);
        Button login = (Button) findViewById(R.id.loginbutton);
        //The staring activity, choose whether to login or register by clicking one of the buttons
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });




    }
}











