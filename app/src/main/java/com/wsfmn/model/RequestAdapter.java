/*
 * Copyright © 2017 Team 18 (WSFMN), CMPUT301, University of Alberta – All Rights Reserved.
 * You may use, distribute, or modify this code under terms and conditions of the Code of Student Behavior at University of Alberta.
 * You can find a copy of the license in this project. Otherwise please contact nmayne@ualberta.ca.
 *
 *  Team 18 is: Musaed Alsobaie, Siddhant Khanna, Wei Li, Nicholas Mayne, Fredric Mendi.
 */

package com.wsfmn.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wsfmn.controller.OnlineController;
import com.wsfmn.view.R;

/**
 * Created by Fredric on 2017-11-11.
 */

/**
 * Adapter for requests in profileActivity, will be able show the
 * the name of the person who would like to follow and a little message "would like to follow!"
 */
public class RequestAdapter extends BaseAdapter implements ListAdapter {
    private RequestList list = new RequestList();
    private Context context;
    private OnlineController online = new OnlineController();


    // Set the requestlist and context for requestAdapter.
    public RequestAdapter(RequestList list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    public String getType(int pos) {
        return list.get(pos).getRequestType();
        //return list.get(pos).getRId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_request, null);
        }

        //Handle TextView and display string from your list
        TextView personWhoRequest = (TextView)view.findViewById(R.id.item_request);
        TextView typeOfRequest = (TextView)view.findViewById(R.id.request_type);
        personWhoRequest.setText(list.get(position).getName());
        typeOfRequest.setText("wants to " +list.get(position).getRequestType() + " you!");

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.decline);
        Button addBtn = (Button)view.findViewById(R.id.accept);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //delete request of the person who asked to follow, delete on elastic search as well.
                online.deleteRequest( list.get(position).getId());
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //accept request of the person. delete request and add the friend to friend list.
                online.deleteRequest(list.get(position).getId());
                online.addFriend(list.get(position).getName());


                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}