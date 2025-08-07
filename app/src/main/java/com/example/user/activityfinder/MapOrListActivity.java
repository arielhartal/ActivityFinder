package com.example.user.activityfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapOrListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_or_list);
        Button btnMap = (Button) findViewById(R.id.btnMap);
        Button btnList = (Button) findViewById(R.id.btnList);
        Button btnChat = (Button) findViewById(R.id.btnChat) ;

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapOrListActivity.this,MapsActivity.class));
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapOrListActivity.this,EventListActivity.class));
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapOrListActivity.this,GroupsActivity.class));
            }
        });
    }
}
