package com.example.user.activityfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.activityfinder.LoginActivity.loggeduser;
import static com.example.user.activityfinder.MainActivity.UserRef;


public class UserListActivity extends AppCompatActivity {
   public RecyclerView userRecycler;
    public Query userQuery;
   public static FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    public static ArrayList<User> userList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userRecycler=findViewById(R.id.userRecycler);
        userRecycler.setHasFixedSize(true);
        userRecycler.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
        insertUsers();
    }

    private void insertUsers(){


        userQuery=UserRef.orderByChild("category").equalTo(loggeduser.getCategory());
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<User,UserViewHolder>(User.class,R.layout.card_view_user,
                UserViewHolder.class,userQuery) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {
                Log.i("AAA",model.getFirstName());
                viewHolder.setName(model.getFirstName()+" "+model.getLastName());
                viewHolder.setAge(String.valueOf(model.getAge()));
                viewHolder.setGender(model.getGender());
                userList.add(model);
            }

        };
        userRecycler.setAdapter(firebaseRecyclerAdapter);

    }

    

}
