package com.example.ngupta1.parksafe;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViewPayments extends Fragment
{

    ArrayList<Payment> mPayments;
    PaymentListAdapter mAdapter;
    ListView lvPayments;
    boolean mFirstLoad;
    String s;
    RelativeLayout ll;
    EditText editsearch;
    ArrayList<Payment> arraylist;
    ArrayList<Payment> worldpopulationlist;


    public ViewPayments(String s) {
        arraylist = new ArrayList<Payment>();
        worldpopulationlist = new ArrayList<Payment>();
        // Required empty public constructor
        this.s=s;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("View Payments");
        // super.onCreate(savedInstanceState);
        int a = getResources().getIdentifier(s, "layout",container.getContext().getPackageName());
        ll = (RelativeLayout) inflater.inflate(a, container, false);


        mPayments = new ArrayList<>();

        lvPayments = (ListView)ll.findViewById(R.id.lvPayments);
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvPayments.setTranscriptMode(1);
        mFirstLoad = true;

        mAdapter = new PaymentListAdapter(getContext(), LocateParking.USER_ID, mPayments);
        lvPayments.setAdapter(mAdapter);

        Payment b=new Payment();
        updatePayments();


        editsearch = (EditText) ll.findViewById(R.id.filter);

        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

//        b.location_name="105/21 B";
//        mAdapter.add(b);
        return ll;
    }

    protected void updatePayments() {

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
                if (mFirstLoad) {
                    lvPayments.setSelection(mAdapter.getCount() - 1);
                    mFirstLoad = false;
                }
            }

            protected void displayData() {
                try {

                    ArrayList<Payment> payments = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(myJSON);

                    peoples = jsonObj.getJSONArray("payments");
                    System.out.println(peoples);


                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject json_data = peoples.getJSONObject(i);
                        String payment_reference = json_data.getString("payment_id");
                        String reference = json_data.getString("booking_reference");
                        String location = json_data.getString("location_name");
                        String amount= json_data.getString("amount");
                        String start_date= json_data.getString("start_date");
                        String end_date= json_data.getString("end_date");
                        String time = json_data.getString("time");
                        Payment b = new Payment();
                        b.setPaymentReference(payment_reference);
                        b.setBookingReference(reference);
                        b.setLocation(location);
                        b.setAmount(amount);
                        b.setStartDate(start_date);
                        b.setEndDate(end_date);
                        b.setPaymentTime(time);
                        payments.add(b);
                    }
                    if (mPayments.addAll(payments)) {
                        System.out.println("Fetched all payments");
                        arraylist.addAll(payments);
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            protected String getDataOKHTTP() {
                String returnString = null;
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("User", LocateParking.USER_ID).build();
//                System.out.println(requestBody);
                Request request = new Request.Builder().url(LocateParking.server+"/ParkSafe/getPayments.php").post(requestBody).build();
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

    public void filter(String charText) {
        System.out.println("Ye hai:"+charText+" "+arraylist.toString());
        charText = charText.toLowerCase(Locale.getDefault());
        mPayments.clear();
        if (charText.length() == 0) {
            mPayments.addAll(arraylist);
        }
        else
        {
            for (Payment wp : arraylist)
            {
                if (wp.getLocationName().toLowerCase(Locale.getDefault()).contains(charText)||wp.getPaymentReference().toLowerCase(Locale.getDefault()).contains(charText)||wp.getBookingReference().toLowerCase(Locale.getDefault()).contains(charText)||wp.getAmount().toLowerCase(Locale.getDefault()).contains(charText)||wp.getStartDate().toLowerCase(Locale.getDefault()).contains(charText)||wp.getEndDate().toLowerCase(Locale.getDefault()).contains(charText)||wp.getPaymentTime().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    mPayments.add(wp);
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }


//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        }  else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
