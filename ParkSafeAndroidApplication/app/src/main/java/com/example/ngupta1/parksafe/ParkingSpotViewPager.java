package com.example.ngupta1.parksafe;

/**
 * Created by ngupta1 on 8/10/16.
 */

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


public class ParkingSpotViewPager extends Baseactivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    protected View view;
    private ImageButton btnNext, btnFinish;
    private ViewPager intro_images;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";
    private int[] mImageResources = {
            R.drawable.abc1,
            R.drawable.abc2,
            R.drawable.abc3,

    };
    private int uid=0;
    private int pos=0;
    private String reference="";

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
        onPageSelected(pos);
        updateDetails();
        toolbar.setVisibility(View.GONE);

    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_parkingspot,container);

        intro_images = (ViewPager) view.findViewById(R.id.pager_introduction);
        btnNext = (ImageButton) view.findViewById(R.id.btn_next);
        btnFinish = (ImageButton) view.findViewById(R.id.btn_finish);

        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);

        btnNext.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

        mAdapter = new ViewPagerAdapter(ParkingSpotViewPager.this, mImageResources);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(pos);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();

    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }

    public void openImage(View view){
        Intent intent = new Intent(this, ParkingSpotImageViewPager.class);
        String message = Integer.toString(intro_images.getCurrentItem());
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                intro_images.setCurrentItem((intro_images.getCurrentItem() < dotsCount)
                        ? intro_images.getCurrentItem() + 1 : 0);
                break;

            case R.id.btn_finish:
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

        if (position + 1 == dotsCount) {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    protected void updateDetails() {

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

                    peoples = jsonObj.getJSONArray("parkingSpots");
                    System.out.println(peoples);


                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject json_data = peoples.getJSONObject(i);
                        String address_short = json_data.getString("short_address");
                        String rent = json_data.getString("rent");
                        String available = json_data.getString("available_space");
                        String total = json_data.getString("total_space");
                        String address = json_data.getString("address");
                        String available_till= json_data.getString("available_till");
                        String special = json_data.getString("special");

                        ((TextView)findViewById(R.id.location_address_short)).setText(address_short);
                        ((TextView)findViewById(R.id.location_rent)).setText(rent);
                        ((TextView)findViewById(R.id.location_available)).setText(available);
                        ((TextView)findViewById(R.id.location_total)).setText(total);
                        ((TextView)findViewById(R.id.location_address)).setText(address);
                        ((TextView)findViewById(R.id.location_available_till)).setText(available_till);
                        ((TextView)findViewById(R.id.location_special)).setText(special);

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
                System.out.println(EXTRA_MESSAGE);
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("LocationReference", reference).build();
//                System.out.println(requestBody);
                Request request = new Request.Builder().url(LocateParking.server+"/ParkSafe/getParkingSpotDetail.php").post(requestBody).build();
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