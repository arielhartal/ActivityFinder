package com.example.user.activityfinder;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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

//import static com.example.user.activityfinder.CreateEventActivity.event1;
import static com.example.user.activityfinder.MapsActivity.ifDistance;
import static com.example.user.activityfinder.SearchActivity.category;
import static com.example.user.activityfinder.EventListActivity.eventList;
import static com.example.user.activityfinder.LoginActivity.loggeduser;
import static com.example.user.activityfinder.MainActivity.nFirebaseDatabase;
import static com.example.user.activityfinder.MainActivity.UserRef;
import static com.example.user.activityfinder.MainActivity.EventRef;
import static com.example.user.activityfinder.SearchActivity.distanceFrom;

public class GroupsActivity extends AppCompatActivity {

    private View groupActivityView;
    public static ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    public static ArrayList<String> list_of_groups = new ArrayList<>();
    private DatabaseReference EventRef;
    private int c=0;
    private  ArrayList<String> users=new ArrayList<>();
    private boolean ok=false;
    public static int n;
    public static boolean ifClickedHost;
    Button btnMap;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        EventRef = FirebaseDatabase.getInstance().getReference().child("Event");
        InitalizeFields();
        RetriveAndDisplayGroups();
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Click on the button opens the EventListActivity and instantly the maps activity
                startActivity(new Intent(GroupsActivity.this,EventListActivity.class));
            }
        });
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //When the event is clicked it opens the GroupChatActivity and calls the setVisitiorsList method
                final String currentGroupName = adapterView.getItemAtPosition(position).toString();
                setVisitorsList(currentGroupName);
            }});

    }



//if the user isnt host it opens dialog so he can choose if he wants to chat the whole group or just the host
    public void openDialog(final String thisEvent) {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Choose");
        dialog.show();
        Button bt_host = (Button)dialog.findViewById(R.id.dialog_host);
        Button bt_group = (Button)dialog.findViewById(R.id.dialog_group);
        bt_host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if he chose host it opens the chat to the host window
                ifClickedHost=true;
                Intent groupChatIntent = new Intent(GroupsActivity.this, GroupsChatActivity.class);
                groupChatIntent.putExtra("groupName", thisEvent);
                startActivity(groupChatIntent);

            }
        });
        bt_group.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if he chose all it opens the chat to the whole group window
                ifClickedHost=false;
                Intent groupChatIntent = new Intent(GroupsActivity.this, GroupsChatActivity.class);
                groupChatIntent.putExtra("groupName", thisEvent);
                startActivity(groupChatIntent);
                dialog.dismiss();
            }
        });
        dialog.show();

    }


//Initalize the fields method
    private void InitalizeFields()
    {
        list_view = (ListView) findViewById(R.id.list_view1);
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);
        btnMap=(Button) findViewById(R.id.btnMap);

    }

//Retriving and displaying groups method
    private void RetriveAndDisplayGroups()
    {
        EventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Set<String> set = new HashSet<>();
                /*
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext())
                {
                        set.add(((DataSnapshot) iterator.next()).getKey());
                }
*/
                for (DataSnapshot snap :dataSnapshot.getChildren()) {
                    //Takes each event in the firebase via snap and takes it's category and name
                    String categoryFromFB=snap.child("category").getValue(String.class);
                    String nameFromFB = snap.child("name").getValue(String.class);
                    if (categoryFromFB.equals(SearchActivity.category))
                    {
                        //adding the event name to the Arraylist if the category equals to the chose category
                        set.add(nameFromFB);


                    }
                }

                list_of_groups.clear();
                list_of_groups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;
    }

    //Setting the visitors list of each event and the amount of people of each event
    public void setVisitorsList(final String eventname) {

        EventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(eventname).exists()) {
                    //Getting the visitorlist of each event and changing the amount of people
                    Event myEvent = dataSnapshot.child(eventname).getValue(Event.class);
                    ArrayList<String> visitorList=new ArrayList<>();
                    visitorList=myEvent.getVisitorList();
                    EventRef.child(eventname).child("amount").setValue(visitorList.size());
                    c=visitorList.size();
                    boolean ok=true;
                    //the boolean ok is used in order to know if the logged user is host or not and if he can enter the event chat true=cant enter false=can enter
                    for(int i=0;i<visitorList.size();i++) {
                        if (visitorList.get(i).equals(loggeduser.getUserid())&&!loggeduser.getUserid().equals(myEvent.getHost())) {
                            //if the user is already in the event and hes not host he can enter the event chat
                            ok = false;
                        }
                        else{
                            //if hes not in the event hes added
                            visitorList.add(loggeduser.getFirstName()+" "+loggeduser.getLastName());
                            openDialog(eventname);
                        }

                    }
                        if (c <= myEvent.getMax() && !loggeduser.getUserid().equals(myEvent.getHost())&& loggeduser.getEvent().equals(eventname) && ok==true) {
                        // if the amount is less the max and the logged user isnt host and isnt in the event he enters the event, also it updates the amount of pepole in the event
                            visitorList.add(loggeduser.getUserid());
                            c++;
                            myEvent.setAmount(c);
                            EventRef.child(eventname).child("amount").setValue(c);
                            myEvent.setVisitorList(users);
                            EventRef.child(eventname).child("visitorList").setValue(visitorList);
                            openDialog(eventname);

                        }
                        else if(loggeduser.getUserid().equals(myEvent.getHost())) {
                        //if the user is host it opens the groupchat for the host
                            Intent groupChatIntent = new Intent(GroupsActivity.this, GroupsChatActivity.class);
                            groupChatIntent.putExtra("groupName", eventname);
                            startActivity(groupChatIntent);
                        }

                        else if(ok==false) {
                        //if the user is already in the event and hes not host it opens the dialog in which he can choose whether to chat to the host or the whole group
                            openDialog(eventname);

                        }

                        else if(!loggeduser.getEvent().equals(eventname))
                        {
                            //if the logged user is in another event he cant enter the chat of this event
                            Toast.makeText(GroupsActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }





                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }
}
