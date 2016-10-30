package com.example.ngupta1.parksafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ngupta1 on 31/10/16.
 */
public class OwnerHubDetailListAdapter extends ArrayAdapter<OwnersHubDetail> {

    private String mUserId;
    Context context;
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";


    public OwnerHubDetailListAdapter(Context context, String userId, ArrayList<OwnersHubDetail> messages) {
        super(context, 0, messages);
        this.context=context;
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.owners_hub_location_detail_item, parent, false);
        }
        final OwnersHubDetail ohd = getItem(position);
        ((TextView)convertView.findViewById(R.id.owners_hub_detail_month)).setText(ohd.getMonth());
        ((TextView)convertView.findViewById(R.id.owners_hub_detail_spaces)).setText(ohd.getSpaces());
        ((TextView)convertView.findViewById(R.id.owners_hub_detail_amount)).setText(ohd.getAmount());


        return convertView;
    }
}
