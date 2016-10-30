package com.example.ngupta1.parksafe;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class ViewLocations extends Fragment
{

    ArrayList<Location> mLocations;
    LocationListAdapter mAdapter;
    ListView lvLocations;
    boolean mFirstLoad;
    static final String server = "http://10.4.52.173:8888";
    String s;
    RelativeLayout ll;
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";
    public final static String EXTRA_MESSAGE1 = "com.example.ngupta1.parksafe.MESSAGE1";
    public final static String EXTRA_MESSAGE2 = "com.example.ngupta1.parksafe.MESSAGE2";
    public final static String EXTRA_MESSAGE3 = "com.example.ngupta1.parksafe.MESSAGE3";


    public ViewLocations(String s) {
        // Required empty public constructor
        this.s=s;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("View Locations");
        // super.onCreate(savedInstanceState);
        int a = getResources().getIdentifier(s, "layout",container.getContext().getPackageName());
        ll = (RelativeLayout) inflater.inflate(a, container, false);


        mLocations = new ArrayList<>();

        lvLocations = (ListView)ll.findViewById(R.id.lvOwnersHubChooseLocation);
        // Automatically scroll to the bottom when a data set change notification is received and only if the last item is already visible on screen. Don't scroll to the bottom otherwise.
        lvLocations.setTranscriptMode(1);
        mFirstLoad = true;

        mAdapter = new LocationListAdapter(getContext(), LocateParking.USER_ID, mLocations);
        lvLocations.setAdapter(mAdapter);




        lvLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String selected = ((TextView) view.findViewById(R.id.owners_hub_choose_location)).getText().toString();
                Intent intent = new Intent(getContext(), OwnerHubDetailView.class);
                intent.putExtra(EXTRA_MESSAGE, mAdapter.getItem(position).getReference());
                intent.putExtra(EXTRA_MESSAGE1, mAdapter.getItem(position).getStartdDate());
                intent.putExtra(EXTRA_MESSAGE2, mAdapter.getItem(position).getEndDate());
                intent.putExtra(EXTRA_MESSAGE3, mAdapter.getItem(position).getRent());
                getContext().startActivity(intent);
                System.out.println("dekho "+selected+ " "+s);
            }
        });






        Location b=new Location();
        updateLocations();

//        b.location_name="105/21 B";
//        mAdapter.add(b);
        return ll;
    }

    protected void updateLocations() {

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
                    lvLocations.setSelection(mAdapter.getCount() - 1);
                    mFirstLoad = false;
                }
            }

            protected void displayData() {
                try {

                    ArrayList<Location> locations = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(myJSON);

                    peoples = jsonObj.getJSONArray("locations");
                    System.out.println(peoples);


                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject json_data = peoples.getJSONObject(i);
                        String address = json_data.getString("short_address");
                        String reference = json_data.getString("uid");
                        String from = json_data.getString("available_from");
                        String till = json_data.getString("available_till");
                        String rent = json_data.getString("rent");
                        Location b = new Location();
                        b.setAddress(address);
                        b.setReference(reference);
                        b.setStartDate(from);
                        b.setEndDate(till);
                        b.setRent(rent);
                        locations.add(b);
                    }
                    if (mLocations.addAll(locations)) {
                        System.out.println("Fetched all locations");
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
                Request request = new Request.Builder().url(LocateParking.server+"/ParkSafe/getOwnerLocations.php").post(requestBody).build();
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
