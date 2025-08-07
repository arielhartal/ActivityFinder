package com.example.user.activityfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.SignInButton;

import java.util.ArrayList;

import static com.example.user.activityfinder.LoginActivity.loggeduser;
import static com.example.user.activityfinder.MainActivity.UserRef;
import static com.example.user.activityfinder.MainActivity.EventRef;

public class CreateEventActivity extends AppCompatActivity {
    EditText edtname;
    EditText edtcategory;
    EditText edtTime;
    EditText edtplace;
    EditText edtMax;
    Button btnConfirm;
    Event event1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        edtname=(EditText) findViewById(R.id.edtname);
        edtcategory=(EditText) findViewById(R.id.edtcategory);
        edtTime=(EditText) findViewById(R.id.edtTime);
        edtplace=(EditText) findViewById(R.id.edtplace);
        edtMax=(EditText) findViewById(R.id.edtmax);

        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //The confirm button takes the values that have been entered
                String name = edtname.getText().toString();
                String date= edtTime.getText().toString();
                String host=loggeduser.getFirstName()+" "+loggeduser.getLastName();
                String category = edtcategory.getText().toString();
                String time = edtTime.getText().toString();
                String place = edtplace.getText().toString();
                //boolean ifDistance=false;
                int max = Integer.parseInt(edtMax.getText().toString());
                ArrayList<String> users=new ArrayList<String>();
                users.add(0,host);
                int amount=0;
                //Creates the event with the entered values and the created user is created in the firebase database
                final Event event1 = new Event(name,host,category,date,place,time,max,users,amount/*,ifDistance*/);
                EventRef.child(event1.getName()).setValue(event1);
                startActivity(new Intent(CreateEventActivity.this,ChooseActivity.class));

            }
        });
    }

}
