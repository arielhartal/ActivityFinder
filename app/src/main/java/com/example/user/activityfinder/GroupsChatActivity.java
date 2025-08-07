package com.example.user.activityfinder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.user.activityfinder.GroupsActivity.ifClickedHost;
import static com.example.user.activityfinder.MainActivity.EventRef;
import static com.example.user.activityfinder.GroupsActivity.n;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import android.content.Context;

import static com.example.user.activityfinder.LoginActivity.loggeduser;

public class GroupsChatActivity extends AppCompatActivity {

    private Button SendMessageToAll;
    private Button SendMessageToPerson;
    private Button SendMessageToHost;
    private EditText userMessageInput;
    private ScrollView mScrollView;
    private TextView displayTextMessages;
    private String currentGroupName ,currentDate, currentTime;
    private DatabaseReference groupNameReference, groupMessageKeyReference;
    Spinner spinnerUsers;
    Context context;
    Boolean ifAllClicked=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        addItemsOnSpinner();

        context=this;

        currentGroupName = getIntent().getExtras().get("groupName").toString();



        groupNameReference = FirebaseDatabase.getInstance().getReference().child("Event").child(currentGroupName);
        groupNameReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                InitializeFields();
                //Checking which user is entered and checking if he wants to chat the whole group or the host if hes not host
                Event e = dataSnapshot.getValue(Event.class);
                if (!(loggeduser.getUserid().equals(e.getHost())) && ifClickedHost == true) {
                    //if the user isnt host and he chose host it opens the chat to host window
                    SendMessageToPerson.setVisibility(View.GONE);
                    SendMessageToAll.setVisibility(View.GONE);
                    spinnerUsers.setVisibility(View.GONE);
                    currentGroupName = getIntent().getExtras().get("groupName").toString();
                    Toast.makeText(GroupsChatActivity.this, currentGroupName + ": " + "Host", Toast.LENGTH_SHORT).show();

                }
                if (!(loggeduser.getUserid().equals(e.getHost())) && ifClickedHost == false) {
                    //if the user isnt host and he chose all it opens the chat to group window
                    SendMessageToPerson.setVisibility(View.GONE);
                    SendMessageToHost.setVisibility(View.GONE);
                    spinnerUsers.setVisibility(View.GONE);
                    currentGroupName = getIntent().getExtras().get("groupName").toString();
                    Toast.makeText(GroupsChatActivity.this, currentGroupName + ": " + "Group", Toast.LENGTH_SHORT).show();

                }

                if (loggeduser.getUserid().equals(e.getHost())) {
                    //if the user is host it opens the host chat window
                    SendMessageToHost.setVisibility(View.GONE);
                    currentGroupName = getIntent().getExtras().get("groupName").toString();
                    Toast.makeText(GroupsChatActivity.this, currentGroupName ,Toast.LENGTH_SHORT).show();


                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


   protected void onStart()
   {
       super.onStart();

      
       groupNameReference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               String key = dataSnapshot.getKey();
               if (dataSnapshot.exists() && !key.equals("visitorList")) { // prevent getting inside data of a key with children (visitorList)
                   //calling the display messages method
                displayMessages(dataSnapshot);
               }
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               if (dataSnapshot.exists()) {
                   displayMessages(dataSnapshot);
               }

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

   }


   //Adding the visitorlist to the spinner
    public void addItemsOnSpinner() {

        spinnerUsers = (Spinner) findViewById(R.id.spinnerUsers);
        EventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(currentGroupName).exists()) {
                    Event myEvent = dataSnapshot.child(currentGroupName).getValue(Event.class);
                    ArrayList<String> usersList=new ArrayList<>();
                    usersList=myEvent.getVisitorList();
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_item,usersList);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUsers.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }






//initializing the fields
    private void InitializeFields()
    {
        getSupportActionBar().setTitle(currentGroupName);
        SendMessageToAll =(Button) findViewById(R.id.send_message_everyone);
        userMessageInput = (EditText) findViewById(R.id.input_group_message);
        SendMessageToPerson=(Button) findViewById(R.id.send_message_person);
        SendMessageToHost=(Button) findViewById(R.id.send_message_host);
        displayTextMessages = (TextView) findViewById(R.id.group_chat_text_display);
        mScrollView = (ScrollView) findViewById(R.id.my_scroll_view);
        SendMessageToAll.setVisibility(View.VISIBLE);
        SendMessageToPerson.setVisibility(View.VISIBLE);
        SendMessageToHost.setVisibility(View.VISIBLE);
        userMessageInput.setVisibility(View.VISIBLE);
        spinnerUsers= (Spinner) findViewById(R.id.spinnerUsers);





//checking which button was clicked to know which case (sending message to all,person or to host)
        SendMessageToAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifAllClicked=true;
                SaveMessageInfoToDatabase();
                userMessageInput.setText("");
            }
        });

        SendMessageToPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifAllClicked=false;
                SaveMessageInfoToDatabase();
                userMessageInput.setText("");
            }
        });

        SendMessageToHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMessageInfoToDatabase();
                userMessageInput.setText("");
            }
        });



    }

    //Saving the message info to the database method
    private void SaveMessageInfoToDatabase()
    {
      final   String message = userMessageInput.getText().toString();
      final   String messageKey = groupNameReference.push().getKey();
      final   String selectedUser=spinnerUsers.getSelectedItem().toString();
           EventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    //checking what case is it (host to all, host to person , person to all , person to host) and then saving the message info to the database
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Event myEvent = dataSnapshot.child(currentGroupName).getValue(Event.class);

                        if (!loggeduser.getUserid().equals(myEvent.getHost()) && ifClickedHost == true)//add selectsiza
                        {
                            String to = "To: Host: " + (myEvent.getHost().toString());

                            if(message.isEmpty())
                            {
                                Toast.makeText(GroupsChatActivity.this, "Please Write A Message First...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Calendar calForDate = Calendar.getInstance();
                                SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                                currentDate = currentDateFormat.format(calForDate.getTime());

                                Calendar calForTime = Calendar.getInstance();
                                SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");
                                currentTime = currentTimeFormat.format(calForTime.getTime());

                                HashMap<String, Object> groupMessageKey = new HashMap<>();
                                groupNameReference.updateChildren(groupMessageKey);
                                groupMessageKeyReference = groupNameReference.child(messageKey);

                                HashMap<String, Object> messageInfo = new HashMap<>();
                                messageInfo.put("name", loggeduser.getFirstName()+" "+ loggeduser.getLastName());
                                messageInfo.put("date", currentDate);
                                messageInfo.put("time", currentTime);
                                messageInfo.put("message", message);
                                messageInfo.put("To", to);

                                groupMessageKeyReference.updateChildren(messageInfo);



                            }


                        }

                        if (!loggeduser.getUserid().equals(myEvent.getHost()) && ifClickedHost == false)//add selectsiza
                        {
                            String to = "To: All " ;

                            if(message.isEmpty())
                            {
                                Toast.makeText(GroupsChatActivity.this, "Please Write A Message First...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Calendar calForDate = Calendar.getInstance();
                                SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                                currentDate = currentDateFormat.format(calForDate.getTime());

                                Calendar calForTime = Calendar.getInstance();
                                SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");
                                currentTime = currentTimeFormat.format(calForTime.getTime());

                                HashMap<String, Object> groupMessageKey = new HashMap<>();
                                groupNameReference.updateChildren(groupMessageKey);
                                groupMessageKeyReference = groupNameReference.child(messageKey);

                                HashMap<String, Object> messageInfo = new HashMap<>();
                                messageInfo.put("name", loggeduser.getFirstName()+" "+ loggeduser.getLastName());
                                messageInfo.put("date", currentDate);
                                messageInfo.put("time", currentTime);
                                messageInfo.put("message", message);
                                messageInfo.put("To", to);

                                groupMessageKeyReference.updateChildren(messageInfo);



                            }


                        }

                        if (loggeduser.getUserid().equals(myEvent.getHost())&&ifAllClicked==false)
                        {
                            String to = "To:    "+selectedUser;

                            if(message.isEmpty())
                            {
                                Toast.makeText(GroupsChatActivity.this, "Please Write A Message First...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Calendar calForDate = Calendar.getInstance();
                                SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                                currentDate = currentDateFormat.format(calForDate.getTime());

                                Calendar calForTime = Calendar.getInstance();
                                SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");
                                currentTime = currentTimeFormat.format(calForTime.getTime());

                                HashMap<String, Object> groupMessageKey = new HashMap<>();
                                groupNameReference.updateChildren(groupMessageKey);
                                groupMessageKeyReference = groupNameReference.child(messageKey);

                                HashMap<String, Object> messageInfo = new HashMap<>();
                                messageInfo.put("name", loggeduser.getFirstName()+" "+ loggeduser.getLastName());
                                messageInfo.put("date", currentDate);
                                messageInfo.put("time", currentTime);
                                messageInfo.put("message", message);
                                messageInfo.put("To", to);

                                groupMessageKeyReference.updateChildren(messageInfo);



                            }


                        }


                        if (loggeduser.getUserid().equals(myEvent.getHost())&&ifAllClicked==true)
                        {
                            String to = "To: All " ;

                            if(message.isEmpty())
                            {
                                Toast.makeText(GroupsChatActivity.this, "Please Write A Message First...", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Calendar calForDate = Calendar.getInstance();
                                SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy");
                                currentDate = currentDateFormat.format(calForDate.getTime());

                                Calendar calForTime = Calendar.getInstance();
                                SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");
                                currentTime = currentTimeFormat.format(calForTime.getTime());

                                HashMap<String, Object> groupMessageKey = new HashMap<>();
                                groupNameReference.updateChildren(groupMessageKey);
                                groupMessageKeyReference = groupNameReference.child(messageKey);

                                HashMap<String, Object> messageInfo = new HashMap<>();
                                messageInfo.put("name", loggeduser.getFirstName()+" "+ loggeduser.getLastName());
                                messageInfo.put("date", currentDate);
                                messageInfo.put("time", currentTime);
                                messageInfo.put("message", message);
                                messageInfo.put("To", to);

                                groupMessageKeyReference.updateChildren(messageInfo);



                            }


                        }



                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }


 //displaying the messages method
    public void displayMessages(DataSnapshot dataSnapshot)
    {
        Iterator iterator = dataSnapshot.getChildren().iterator();

            while (iterator.hasNext()) {  //change order




                final String chatTo = (String) ((DataSnapshot) iterator.next()).getValue();
                final String chatDate = (String) ((DataSnapshot) iterator.next()).getValue();
                final String chatMessage = (String) ((DataSnapshot) iterator.next()).getValue();
                final String chatName = (String) ((DataSnapshot) iterator.next()).getValue();
                final String chatTime = (String) ((DataSnapshot) iterator.next()).getValue();

                displayTextMessages.append(chatTo+"\n"+chatDate+"    "+chatTime+"    "+"\n" + chatName+":" +"\n" +chatMessage + "\n\n\n");




                mScrollView.fullScroll(View.FOCUS_DOWN);

            }
            }
     }






