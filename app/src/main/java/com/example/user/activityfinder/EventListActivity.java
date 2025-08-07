package com.example.user.activityfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.ArrayList;

import static com.example.user.activityfinder.LoginActivity.loggeduser;
import static com.example.user.activityfinder.MainActivity.UserRef;
import static com.example.user.activityfinder.MainActivity.EventRef;
public class EventListActivity extends AppCompatActivity {

    public RecyclerView eventRecycler;
    public Query eventQuery;
    public static FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    public static ArrayList<Event> eventList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        eventRecycler=findViewById(R.id.eventRecycler);
        eventRecycler.setHasFixedSize(true);
        eventRecycler.setLayoutManager(new LinearLayoutManager(EventListActivity.this));
        insertEvents();
//        startActivity(new Intent(EventListActivity.this,MapsActivity.class));
    }
    private void insertEvents(){

//Adding the events to the list if the event has the same category that was chose
        eventQuery=EventRef.orderByChild("category").equalTo(SearchActivity.category);
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Event,EventViewHolder>(Event.class,R.layout.card_view_event,
                EventViewHolder.class,eventQuery) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {
                Log.i("AAA",model.getName());
                viewHolder.setName(model.getName());
                eventList.add(model);
            }

        };
        eventRecycler.setAdapter(firebaseRecyclerAdapter);
}}
