package com.example.user.activityfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Button btnCreate = (Button) findViewById(R.id.btnCreateEvent);
        Button btnLook = (Button) findViewById(R.id.btnLookEvents);
        Button btnViewEvents = (Button) findViewById(R.id.btnViewEvents);
        //Choose whether you want to create an event or search for an event by clicking the choosen button or if you want to view the events you have been to
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseActivity.this,CreateEventActivity.class));
            }
        });
        btnLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseActivity.this,SearchActivity.class));
            }
        });
        btnViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseActivity.this,ViewEventsActivity.class));
            }
        });
    }


}
