package com.example.user.activityfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.user.activityfinder.MainActivity.UserRef;

public class LoginActivity extends AppCompatActivity {
    EditText edtemail;
    EditText edtPassword;
    Button btnSign;
    public static User loggeduser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtemail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSign = (Button) findViewById(R.id.btnSign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edtemail.getText().toString();
                final String userID=email.replace('.',',');
                final String password = edtPassword.getText().toString();
                //Takes the values of the logged user and checking if the user exists in the firebase database with datasnapshot
               UserRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if(dataSnapshot.child(userID).exists())
                       {
                           User login=dataSnapshot.child(userID).getValue(User.class);
                           if(login.getPassword().equals(password))
                           {
                               Toast.makeText(LoginActivity.this,"Logged In",Toast.LENGTH_LONG).show();
                               loggeduser=login;
                               startActivity(new Intent(LoginActivity.this, ChooseActivity.class));
                           }
                           else {
                               Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_LONG).show();
                           }
                       }
                       else {
                           Toast.makeText(LoginActivity.this,"Failed",Toast.LENGTH_LONG).show();
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {
                       Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                   }
               });

            }
        });

    }
}
