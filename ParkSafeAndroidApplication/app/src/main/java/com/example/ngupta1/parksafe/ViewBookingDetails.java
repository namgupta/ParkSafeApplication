package com.example.ngupta1.parksafe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ngupta1 on 20/10/16.
 */
public class ViewBookingDetails extends Baseactivity {


        protected View view;
        private ImageButton btnNext, btnFinish;
        private ViewPager intro_images;
        private LinearLayout pager_indicator;
        private int dotsCount;
        private ImageView[] dots;
        private ViewPagerAdapter mAdapter;
        public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";

        private int uid=0;
        private int pos=0;
    String reference;



    @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            // To make activity full screen.
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            super.onCreate(savedInstanceState);

            Intent intent = getIntent();
             reference = intent.getStringExtra(ParkingSpotViewPager.EXTRA_MESSAGE);

//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            finish();
//            return;
//        }

            setReference();
            updateBookings();

        }

        @Override
        public void setReference() {
            view = LayoutInflater.from(this).inflate(R.layout.view_detail_booking,container);
        }


    protected void updateBookings() {

        class GetData extends AsyncTask<String, Void, String> {
            String myJSON;
            JSONArray peoples = null;
            @Override
            protected String doInBackground(String... strings) {
                return getDataOKHTTP();
            }

            @Override
            protected void onPostExecute(String s) {
                System.out.println("onpostexecute");

                super.onPostExecute(s);
                myJSON = s;
                System.out.println("This is myJSON " + myJSON);
                displayData();
                System.out.println("Done with display data");
//                mMessages.clear();
//                Collections.reverse(m);
//                mMessages.addAll(m);
//               mAdapter.notifyDataSetChanged(); // update adapter

                // Scroll to the bottom of the list on initial load

            }

            protected void displayData() {
                try {

                    JSONObject jsonObj = new JSONObject(myJSON);

                    peoples = jsonObj.getJSONArray("bookings");
                    System.out.println(peoples);


                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject json_data = peoples.getJSONObject(i);
                        String booking_reference = json_data.getString("booking_reference");
                        String booking_time = json_data.getString("booking_time");
                        String location = json_data.getString("location_name");
                        String start_date = json_data.getString("start_date");
                        String end_date= json_data.getString("end_date");
                        String pending_amount = json_data.getString("pending_amount");
                        String booking_status = json_data.getString("booking_status");
                        String paid_till = json_data.getString("paid_till");
                        String due_date = json_data.getString("due_date");
                        ((TextView)findViewById(R.id.booking_reference_detail)).setText("Booking Reference - "+booking_reference);
                        ((TextView)findViewById(R.id.booking_date_detail)).setText(booking_time);
                        ((TextView)findViewById(R.id.booking_location_detail)).setText(location);
                        ((TextView)findViewById(R.id.booking_startdate_detail)).setText(start_date);
                        ((TextView)findViewById(R.id.booking_enddate_detail)).setText(end_date);
                        ((TextView)findViewById(R.id.booking_pendingamount_detail)).setText(pending_amount);
                        ((TextView)findViewById(R.id.booking_status_detail)).setText(booking_status);
                        ((TextView)findViewById(R.id.booking_paidtill_detail)).setText(paid_till);
                        ((TextView)findViewById(R.id.booking_duedate_detail)).setText(due_date);
//
//                        b.setBookingReference(reference);
//                        b.setBookingTime(booking_time);
//                        b.setLocationName(location);
//                        b.setStartDate(start_date);
//                        b.setEndDate(end_date);
//                        b.setBookingStatus(booking_status);
//                        b.setPendingAmount(pending_amount);
//                        b.setDueDate(due_date);
//                        b.setPaidTill(paid_till);
//                        bookings.add(b);
                    }
//                    if (mBookings.addAll(bookings)) {
//                        System.out.println("Fetched all bookings");
//                        mAdapter.notifyDataSetChanged();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            protected String getDataOKHTTP() {
                String returnString = null;
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("BookingReference", reference).build();
//                System.out.println(requestBody);
                Request request = new Request.Builder().url(LocateParking.server+"/ParkSafe/getBookingDetail.php").post(requestBody).build();
                try {
                    Response response = client.newCall(request).execute();
                    //response.header("Content-Type: application/json");
                    returnString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Done with okhttp");
                return returnString;
            }

            // When send button is clicked, create message object on Parse

        }
        GetData get = new GetData();
        get.execute();
    }

}
