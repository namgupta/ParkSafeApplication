package com.example.ngupta1.parksafe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OwnerHubDetailView extends Baseactivity
{

    ArrayList<OwnersHubDetail> mOwnerHubDetail;
    OwnerHubDetailListAdapter mAdapter;
    ListView lvOwnerHubDetail;
    boolean mFirstLoad;
    static final String server = "http://10.4.52.173:8888";
    String s;
    RelativeLayout ll;
    String ref,start,end,rent;
    String reference;
    protected View view;
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        setReference();


        Intent intent = getIntent();
        ref = intent.getStringExtra(ViewLocations.EXTRA_MESSAGE);
        start = intent.getStringExtra(ViewLocations.EXTRA_MESSAGE1);
        end = intent.getStringExtra(ViewLocations.EXTRA_MESSAGE2);
        rent = intent.getStringExtra(ViewLocations.EXTRA_MESSAGE3);
         System.out.println(ref+start+end+rent);



    }

    @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.owners_hub_location_detail,container);
        mOwnerHubDetail = new ArrayList<>();

        lvOwnerHubDetail = (ListView)view.findViewById(R.id.lvOwnersHubDetail);
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvOwnerHubDetail.setTranscriptMode(1);
        mFirstLoad = true;

        mAdapter = new OwnerHubDetailListAdapter(this, LocateParking.USER_ID, mOwnerHubDetail);
        lvOwnerHubDetail.setAdapter(mAdapter);

        Location b=new Location();
        updateDetails();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        System.out.println("View Locations");
//        // super.onCreate(savedInstanceState);
//        int a = getResources().getIdentifier(s, "layout",container.getContext().getPackageName());
//        ll = (RelativeLayout) inflater.inflate(a, container, false);
//
//
//        mOwnerHubDetail = new ArrayList<>();
//
//        lvOwnerHubDetail = (ListView)ll.findViewById(R.id.lvOwnersHubDetail);
//        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
//        lvOwnerHubDetail.setTranscriptMode(1);
//        mFirstLoad = true;
//
//        mAdapter = new LocationListAdapter(getContext(), LocateParking.USER_ID, mOwnerHubDetail);
//        lvOwnerHubDetail.setAdapter(mAdapter);
//
//        Location b=new Location();
//        updateDetails();
//
////        b.location_name="105/21 B";
////        mAdapter.add(b);
//        return ll;
//    }

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
                if (mFirstLoad) {
                    lvOwnerHubDetail.setSelection(mAdapter.getCount() - 1);
                    mFirstLoad = false;
                }
            }

            protected void displayData() {
                try {

                    ArrayList<OwnersHubDetail> ownerHubDetails = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(myJSON);

                    peoples = jsonObj.getJSONArray("hubdetail");
                    System.out.println(peoples);
                    int from=10, till=12;
                    int amount=0;
                    int spaces=0;
                    int from_day = Integer.parseInt(start.substring(8,10));
                    int from_month = Integer.parseInt(start.substring(5,7));
                    int from_year = Integer.parseInt(start.substring(0,4));
                    int till_day = Integer.parseInt(end.substring(8,10));
                    int till_month = Integer.parseInt(end.substring(5,7));
                    int till_year = Integer.parseInt(start.substring(0,4));

//                    SimpleDateFormat sdf = new SimpleDateFormat("MM");
//                    int currentMonth = Integer.parseInt(sdf.format(new Date()));
//                    sdf = new SimpleDateFormat("MM");
//                    int currentYear = Integer.parseInt(sdf.format(new Date()));
//
//                    if(till_year>currentYear)
//                    {
//                        till_day=31;
//                        till_month=currentMonth;
//                        till_year=currentYear;
//                        if(till_month>=12){
//                            till_month=till_month-11;
//                            till_year++;
//                        }
//                    }
//                    else if(till_month>currentMonth && till_year>=currentYear){
//                        till_day=31;
//                        till_month=currentMonth;
//                        till_year=currentYear;
//                        if(till_month>=12){
//                            till_month=till_month-11;
//                            till_year++;
//                        }
//                    }

                    System.out.println("From day "+ from_day+" month "+from_month+ "year "+from_year);
                    System.out.println("From day "+ till_day+" month "+till_month+ "year "+till_year);
                   while((from_month<=till_month && from_year<=till_year) || (from_month>till_month && from_year<till_year)) {
                       System.out.println("Inside loop month year" +from_month+" "+from_year);
                       amount=0;
                       spaces=0;



                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject json_data = peoples.getJSONObject(i);
                        String client_start = json_data.getString("start_date");
                        String client_end = json_data.getString("end_date");
                        int client_start_month = Integer.parseInt(client_start.substring(5,7));
                        int client_end_month = Integer.parseInt(client_end.substring(5,7));
                        int client_start_year = Integer.parseInt(client_start.substring(0,4));
                        int client_end_year = Integer.parseInt(client_end.substring(0,4));
                        if(((from_month > client_start_month && from_year>=client_start_year ) || (from_month < client_start_month && from_year>client_start_year )) && ((from_month < client_end_month && from_year <= client_end_year)||(from_month > client_end_month && from_year < client_end_year))) {
                            amount += Integer.parseInt(rent);
                            spaces++;
                        }
                        else if(from_month == client_start_month && from_year==client_start_year) {
                            amount = amount + ((31 - from_day) * Integer.parseInt(rent)/31);
                            spaces++;
                        }
                        else if(from_month == client_end_month && from_year==client_end_year) {
                            amount = amount + (till_day * Integer.parseInt(rent)/31);
                            spaces++;
                        }

                    }
                        OwnersHubDetail od = new OwnersHubDetail();
                        od.setMonth(setMonth(from_month)+" "+ from_year);
                        od.setAmount(Integer.toString(amount));
                        od.setSpaces(Integer.toString(spaces));
                        ownerHubDetails.add(od);
                       if(from_month==12) {
                           from_month=0;
                           from_year++;
                       }
                       from_month++;
                    }
                    if (mOwnerHubDetail.addAll(ownerHubDetails)) {
                        System.out.println("Fetched all locations");
                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private String setMonth(int month) {
                if(month==1)
                    return "January";
                else if (month==2)
                    return "February";
                else if (month==3)
                    return "March";
                else if (month==4)
                    return "April";
                else if (month==5)
                    return "May";
                else if (month==6)
                    return "June";
                else if (month==7)
                    return "July";
                else if (month==8)
                    return "August";
                else if (month==9)
                    return "September";
                else if (month==10)
                    return "October";
                else if (month==11)
                    return "November";
                else if (month==12)
                    return "December";

                return "true";
            }

            protected String getDataOKHTTP() {
                String returnString = null;
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("Location_id", ref).build();
//                System.out.println(requestBody);
                Request request = new Request.Builder().url(LocateParking.server+"/ParkSafe/getOwnerLocationBookings.php").post(requestBody).build();
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
