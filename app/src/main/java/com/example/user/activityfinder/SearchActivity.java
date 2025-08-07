package com.example.user.activityfinder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;

import static com.example.user.activityfinder.MainActivity.UserRef;
import static com.example.user.activityfinder.LoginActivity.loggeduser;
import static com.example.user.activityfinder.MainActivity.EventRef;

public class SearchActivity extends AppCompatActivity {
    Spinner spinner;
    Button search;
    EditText dFrom;
    public static int distanceFrom;
    public static String category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spinner = (Spinner) findViewById(R.id.spinner);
        search = (Button) findViewById(R.id.search);
        dFrom = (EditText) findViewById(R.id.edtDistance);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Takes the chosen category and the entered distance
                category = spinner.getSelectedItem().toString();
                distanceFrom = Integer.parseInt(dFrom.getText().toString());
                addCategory();
                startActivity(new Intent(SearchActivity.this, GroupsActivity.class));
            }

        });

    }

    //Setting the user's category to be the chosen category from the spinner
    private void addCategory() {
        UserRef.child(loggeduser.getUserid()).child("category").setValue(category);
    }

    public void checkIfExist()
    {
        EventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
