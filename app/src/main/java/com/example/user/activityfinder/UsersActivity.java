package com.example.user.activityfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.example.user.activityfinder.MainActivity.UserRef;

public class UsersActivity extends AppCompatActivity {
    private View usersActivityView;
    private ListView listviewUsers;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_users = new ArrayList<>();
    private DatabaseReference UserRef;
    public Query usersQuery;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        InitalizeFields();
        RetriveAndDisplayUsers();
        listviewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String currentUserName = adapterView.getItemAtPosition(position).toString();

                Intent groupChatIntent = new Intent(UsersActivity.this, GroupsChatActivity.class);
                groupChatIntent.putExtra("groupName" , currentUserName);
                startActivity(groupChatIntent);
            }
        });


    }
    private void InitalizeFields()
    {
        listviewUsers = (ListView) findViewById(R.id.listviewUsers);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list_of_users);
        listviewUsers.setAdapter(arrayAdapter);
    }


    private void RetriveAndDisplayUsers()
    {
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext())
                {
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }

             /*   for (DataSnapshot snap :dataSnapshot.getChildren()) {


                    String eventFromFB=snap.child("category").getValue(String.class);
                    String nameFromFB = snap.child("name").getValue(String.class);
                    if (categoryFromFB.equals(SearchActivity.category))
                    {
                        set.add(nameFromFB);
                    }
                }
*/

                list_of_users.clear();
                list_of_users.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;
}}
