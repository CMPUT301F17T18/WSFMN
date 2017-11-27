package com.wsfmn.habit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wsfmn.habitcontroller.OnlineController;
import com.wsfmn.habittracker.R;

import java.util.ArrayList;

/**
 * Created by Fredric on 2017-11-11.
 */

public class RequestAdapter extends BaseAdapter implements ListAdapter {
    private RequestList list = new RequestList();
    private Context context;
    private OnlineController online = new OnlineController();



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
        typeOfRequest.setText(list.get(position).getRequestType());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.decline);
        Button addBtn = (Button)view.findViewById(R.id.accept);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                online.deleteRequest( list.get(position).getId());
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                online.deleteRequest(list.get(position).getId());
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}