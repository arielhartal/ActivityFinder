package com.example.user.activityfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewEventsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);
        Button btn3Months = (Button) findViewById(R.id.btn3Months);
        Button btn6Months = (Button) findViewById(R.id.btn6Months);
        Button btnYear    = (Button) findViewById(R.id.btnYear);
        //showEvents();

    }

/*
    private void showEvents() {

        btn3Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        btn6Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }*/
}


