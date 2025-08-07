package com.example.user.activityfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static com.example.user.activityfinder.MainActivity.UserRef;

public class RegisterActivity extends AppCompatActivity {
    EditText edtemail;
    EditText edtpassword;
    EditText edtconfirm;
    EditText edtfirstname;
    EditText edtlastname;
    EditText edtgender;
    EditText edtage;
    EditText edtaddress;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtemail = (EditText) findViewById(R.id.edtEmail);
        edtpassword = (EditText) findViewById(R.id.edtPassword);
        edtconfirm = (EditText) findViewById(R.id.edtConfirmPassword);
        edtfirstname = (EditText) findViewById(R.id.edtFirstName);
        edtlastname = (EditText) findViewById(R.id.edtLastName);
        edtgender = (EditText) findViewById(R.id.edtGender);
        edtage = (EditText) findViewById(R.id.edtAge);
        edtaddress=(EditText) findViewById(R.id.edtAddress);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  //The confirm button takes the values that have been entered
                  String email = edtemail.getText().toString();
                  String password = edtpassword.getText().toString();
                  String confirmpassword = edtconfirm.getText().toString();
                  String firstname = edtfirstname.getText().toString();
                  String lastname = edtlastname.getText().toString();
                  String gender = edtgender.getText().toString();
                  int age = Integer.valueOf(edtage.getText().toString());
                  String address=edtaddress.getText().toString();
                  //Checking if the password field is equal to the confirm password field, if it is it creates the user with the entered values and the created user is created in the firebase database
                  if (password.equals(confirmpassword)) {
                      final User user1 = new User(email, password, firstname, lastname, age, gender,"","",address);
                      UserRef.child(user1.getUserid()).setValue(user1);

                  }

                  startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
              }

          }
        );
    }
}


