package com.wsfmn.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wsfmn.view.R;

import java.util.ArrayList;

/**
 * Created by Fredric on 2017-11-30.
 */

public class LeaderBoardAdapter extends ArrayAdapter<ProfileName> {

    private ArrayList<ProfileName> objects;  // declaring our ArrayList of items

    // Constructor for the CounterAdapter.
    public LeaderBoardAdapter(Context context, int textViewResourceId, ArrayList<ProfileName> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    // Override the getView method, and make the look of each item on the list.
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView; // assign view to a variable v

        // if view is null, then show view with inflate.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listfriend_item, null);
        }

        ProfileName i = objects.get(position); // custom item object and get it's position in list

        if (i != null) {
            // obtain a reference to the TextViews.
            TextView owner = (TextView) v.findViewById(R.id.owner);
            TextView ownerHabit = (TextView) v.findViewById(R.id.ownerHabit);

            // check to see if each individual textview is null.
            // if not, assign some text
            if (owner != null) {
                owner.setText(i.getName() + "      ");
            }
            if (ownerHabit != null) {
                ownerHabit.setText(i.getScore()); // set the name
            }

            // view is returned to activity

        }
        return v;
    }
}
