package com.example.user.activityfinder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class UserViewHolder extends RecyclerView.ViewHolder{
    public TextView txt_Name;
    public TextView txt_Gender;
    public TextView txt_Age;
    public String name;
    public String gender;
    public String age;
    View mView;

    public UserViewHolder(View itemView)
    {
        super(itemView);
        mView=itemView;
        txt_Name=itemView.findViewById(R.id.Name);
        txt_Age=itemView.findViewById(R.id.Age);
        txt_Gender=itemView.findViewById(R.id.Gender);
    }

    public void setName(String name) {
        this.name = name;
        txt_Name.setText(name);
    }

    public void setAge(String age) {
        this.age = age;
        txt_Age.setText(age);
    }

    public void setGender(String gender) {
        this.gender = gender;
        txt_Gender.setText(gender);
    }



}
