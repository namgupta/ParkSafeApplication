package com.example.ngupta1.parksafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ngupta1 on 16/10/16.
 */
public class LocationListAdapter extends ArrayAdapter<Location> {

    private String mUserId;
    Context context;
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";


    public LocationListAdapter(Context context, String userId, ArrayList<Location> messages) {
        super(context, 0, messages);
        this.context=context;
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owners_hub_choose_location_item, parent, false);
        }
        final Location location = getItem(position);
        ((TextView)convertView.findViewById(R.id.owners_hub_choose_location)).setText(location.getAddress());
//        convertView.findViewById(R.id.owners_hub_choose_location).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ViewBookingDetails.class);
//                String message = location.getBookingReference();
//                intent.putExtra(EXTRA_MESSAGE, message);
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }
}
