package com.example.ngupta1.parksafe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ngupta1 on 16/10/16.
 */
public class BookingListAdapter extends ArrayAdapter<Booking> {

    private String mUserId;
    Context context;
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";


    public BookingListAdapter(Context context, String userId, ArrayList<Booking> messages) {
        super(context, 0, messages);
        this.context=context;
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.booking_item, parent, false);
        }
        final Booking booking = getItem(position);
        ((TextView)convertView.findViewById(R.id.location)).setText(booking.getLocationName());
        ((TextView)convertView.findViewById(R.id.end_date)).setText(booking.getEndDate());
        ((TextView)convertView.findViewById(R.id.booking_status)).setText(booking.getBookingStatus());
        convertView.findViewById(R.id.view_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewBookingDetails.class);
                String message = booking.getBookingReference();
                intent.putExtra(EXTRA_MESSAGE, message);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
