package com.example.user.activityfinder;

import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

public class EventViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_Name;
    public String name;

    View mView;

    public EventViewHolder(View itemView)
    {
        super(itemView);
        mView=itemView;
        txt_Name=itemView.findViewById(R.id.Name);
    }

    public void setName(String name) {
        this.name = name;
        txt_Name.setText(name);
    }


}
