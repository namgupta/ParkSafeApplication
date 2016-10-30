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
public class PaymentListAdapter extends ArrayAdapter<Payment> {

    private String mUserId;
    Context context;
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";


    public PaymentListAdapter(Context context, String userId, ArrayList<Payment> messages) {
        super(context, 0, messages);
        this.context=context;
        this.mUserId = userId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.payment_item, parent, false);
        }
        final Payment payment = getItem(position);
        ((TextView)convertView.findViewById(R.id.payment_id)).setText(payment.getPaymentReference());
        ((TextView)convertView.findViewById(R.id.payment_booking_id)).setText(payment.getBookingReference());
        ((TextView)convertView.findViewById(R.id.payment_location)).setText(payment.getLocationName());
        ((TextView)convertView.findViewById(R.id.payment_amount)).setText(payment.getAmount());
        ((TextView)convertView.findViewById(R.id.payment_from)).setText(payment.getStartDate());
        ((TextView)convertView.findViewById(R.id.payment_till)).setText(payment.getEndDate());
        ((TextView)convertView.findViewById(R.id.payment_time)).setText(payment.getPaymentTime());

        return convertView;
    }


}
