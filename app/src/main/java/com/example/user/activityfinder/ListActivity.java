package com.example.user.activityfinder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;
    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    User user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        user = new User();
        listView=(ListView) findViewById(R.id.listView);
        database= FirebaseDatabase.getInstance();
        ref= database.getReference("Users");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.user_info,R.id.userInfo, list);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {

                    user = ds.getValue(User.class);
                    list.add(user.getFirstName().toString()+ " "+user.getLastName().toString()+" "+user.getGender().toString());
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
