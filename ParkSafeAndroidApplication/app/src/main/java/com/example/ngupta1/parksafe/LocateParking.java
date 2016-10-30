package com.example.ngupta1.parksafe;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class LocateParking extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    ArrayList<MapMarker> mapMarkers = new ArrayList();
    Marker current_marker=null;
    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    UiSettings mUiSettings;
    Marker currLocationMarker;
    static final String USER_ID="admin";
    public final static String EXTRA_MESSAGE = "com.example.ngupta1.parksafe.MESSAGE";
    String s;
    RelativeLayout ll;
    private static final LatLng SYDNEY = new LatLng(-37.0,145.0);


    boolean firstLoad = true;
    boolean popup=false;
    static String server="http://172.16.56.173:8888";
    String uid;

    public LocateParking(String s) {
        // Required empty public constructor
        this.s=s;
    }

    public LocateParking(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        int a = getResources().getIdentifier(s, "layout",container.getContext().getPackageName());
        ll = (RelativeLayout) inflater.inflate(a, container, false);

        ll.findViewById(R.id.view_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ParkingSpotViewPager.class);
                intent.putExtra(EXTRA_MESSAGE, current_marker.getTitle());
                startActivity(intent);
            }
        });

        View v = ll.findViewById(R.id.popup);
        ScaleAnimation scale = new ScaleAnimation((float) 1.0, (float) 1.0, (float) 1, (float) 0.0067, Animation.ABSOLUTE, 0,
                Animation.RELATIVE_TO_SELF, 1);

        //scale.setInterpolator(new AccelerateDecelerateInterpolator());
        scale.setFillAfter(true);
        scale.setFillBefore(true);
        scale.setFillEnabled(true);
        scale.setDuration(1);
        System.out.println("CHeck: "+ll.findViewById(R.id.map));
        v.startAnimation(scale);

        return ll;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapSetup();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapSetup();
    }

public void mapSetup(){
    View v= ll.findViewById(R.id.map);
    System.out.println("CHeck: "+v.getId());
        mFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(v.getId()));
        mFragment.getMapAsync(new OnMapReadyCallback() {
                                  @Override
                                  public void onMapReady(GoogleMap gMap) {

                                      popup = false;
                                      mGoogleMap = gMap;
                                      mUiSettings = mGoogleMap.getUiSettings();
                                      mUiSettings.setZoomControlsEnabled(true);
                                      mGoogleMap.setMyLocationEnabled(true);
                                      buildGoogleApiClient();
                                      System.out.println("Asking to connect");
                                      mGoogleApiClient.connect();
                                      View v = ll.findViewById(R.id.popup);
                                      v.setVisibility(View.GONE);



                                      System.out.println("Next listener");

                                      mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                          @Override
                                          public void onMapClick(LatLng latLng) {
                                              if (popup) {
                                                  current_marker = null;
                                                  View v = ll.findViewById(R.id.popup);
                                                  ScaleAnimation scale = new ScaleAnimation((float) 1.0, (float) 1.0, (float) 1, (float) 0.0067, Animation.ABSOLUTE, 0,
                                                          Animation.RELATIVE_TO_SELF, 1);

                                                  //scale.setInterpolator(new AccelerateDecelerateInterpolator());
                                                  scale.setFillAfter(true);
                                                  scale.setFillBefore(true);
                                                  scale.setFillEnabled(true);
                                                  scale.setDuration(200);

                                                  v.startAnimation(scale);
                                                  popup = false;
                                                  v.setVisibility(View.GONE);

                                              }

                                          }
                                      });


                                      mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()

                                      {

                                          @Override
                                          public boolean onMarkerClick(Marker arg0) {
                                              System.out.println("Hello");

                                              uid=arg0.getTitle();
                                                      System.out.println("Clicked: "+uid);
                                              if (popup == false) {
                                                  View v1 = ll.findViewById(R.id.popup);
                                                  v1.setVisibility(1);
                                                  current_marker = arg0;
                                                  //                                                  View background = findViewById(R.id.transparentOverlay);
                                                  //                                                  background.setVisibility(1);
                                                  getLocationDetail();
                                                  View v = ll.findViewById(R.id.popup);

                                                  ScaleAnimation scale = new ScaleAnimation((float) 1.0, (float) 1.0, (float) 0.0067, (float) 1, Animation.ABSOLUTE, 0,
                                                          Animation.RELATIVE_TO_SELF, 1);

                                                  //scale.setInterpolator(new AccelerateDecelerateInterpolator());
                                                  scale.setFillEnabled(true);
                                                  scale.setDuration(200);

                                                  v.startAnimation(scale);
                                                  popup = true;

                                                  return true;
                                              }
                                              else{
                                                  current_marker=arg0;
                                                  getLocationDetail();
                                              }
                                              return true;
                                          }

                                      });

//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(10, 10))
//                .title("Hello world"));


                                      mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                                          @Override
                                          public void onCameraIdle() {
                                              // Cleaning all the markers.
                                              if (mGoogleMap != null) {
                                                  mGoogleMap.clear();
                                                  LatLngBounds bounds = mGoogleMap.getProjection().getVisibleRegion().latLngBounds;
                                                  System.out.println("Bounds1: " + bounds);

                                                  findMarkers(bounds);
                                              }

                                              //LatLng mPosition = mGoogleMap.getCameraPosition().target;
                                              //mZoom = mGoogleMap.getCameraPosition().zoom;

                                          }
                                      });

                                      CameraPosition cameraPosition = new CameraPosition.Builder()
                                              .target(SYDNEY)      // Sets the center of the map to Mountain View
                                              .zoom(6)                   // Sets the zoom
                                              .bearing(0)                // Sets the orientation of the camera to east
                                              .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                                              .build();                   // Creates a CameraPosition from the builder
                                      mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                  }



        }


        );





    }




    protected void findMarkers(final LatLngBounds bounds) {
        //mGoogleMap.clear();
        mapMarkers.clear();
        final Double a = bounds.southwest.latitude;
        final Double b = bounds.southwest.longitude;
        final Double c = bounds.northeast.latitude;
        final Double d = bounds.northeast.longitude;
        System.out.println(a+" "+b+" "+c+" "+d);
        System.out.println("Bounds: "+bounds);

        class GetData extends AsyncTask<String, Void, String> {
            String myJSON;
            JSONArray peoples = null;


            @Override
            protected String doInBackground(String... strings) {
                return getDataOKHTTP();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                myJSON = s;
                System.out.println("This is myJSON " + myJSON);
                displayData();

            }

            protected void displayData() {
                try {
                    JSONObject jsonObj = new JSONObject(myJSON);

                    peoples = jsonObj.getJSONArray("messages");
                    System.out.println(peoples);


                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject json_data = peoples.getJSONObject(i);
                        uid = json_data.getString("uid");
                        String lat = json_data.getString("lat");
                        String lon = json_data.getString("lng");
                        MapMarker m = new MapMarker();
                        m.setUid(uid);
                        System.out.println("Uid: "+uid);
                        m.setLat(lat);
                        m.setLon(lon);
                        mapMarkers.add(m);
                    }
                    for(int i=0;i<mapMarkers.size();i++)
                    {
                        mGoogleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(mapMarkers.get(i).getLat()),Double.parseDouble(mapMarkers.get(i).getLon())))
                                .title(mapMarkers.get(i).getUid()));

                    }
//                    if (mMessages.addAll(messages)) {
//                        System.out.println("Trying to delete");
//                        deleteMessages();
//                        mAdapter.notifyDataSetChanged();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            protected String getDataOKHTTP() {
                String returnString = null;
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("a", a.toString()).addFormDataPart("b", b.toString()).addFormDataPart("c", c.toString()).addFormDataPart("d", d.toString()).build();
//                System.out.println(requestBody);
                Request request = new Request.Builder().url(server+"/findMarkersOnScreen.php").post(requestBody).build();
                //System.out.println("@ register a new token ");
                try {
                    Response response = client.newCall(request).execute();
                    //response.header("Content-Type: application/json");
                    returnString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return returnString;
            }

            // When send button is clicked, create message object on Parse

        }
        GetData get = new GetData();
        get.execute();
    }

    protected synchronized void buildGoogleApiClient() {
//        Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        System.out.println("Connected");

        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//                //zoom to current position:
//                if(firstLoad) {
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(latLng).zoom(14).build();
//
//                    mGoogleMap.animateCamera(CameraUpdateFactory
//                            .newCameraPosition(cameraPosition));
//
//                    firstLoad = false;
//                }
//                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//
//            }
//        });
//        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter


    }

    @Override
    public void onConnectionSuspended(int i) {
//        Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    public void getLocationDetail() {

        class GetData extends AsyncTask<String, Void, String> {
            String myJSON;
            JSONArray peoples = null;


            @Override
            protected String doInBackground(String... strings) {
                return getDataOKHTTP();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                myJSON = s;
                System.out.println("This is myJSON " + myJSON);
                locationData();

            }

            protected void locationData() {
                try {
                    JSONObject jsonObj = new JSONObject(myJSON);

                    peoples = jsonObj.getJSONArray("messages");
                    System.out.println(peoples);


                    for (int i = 0; i < peoples.length(); i++) {
                        JSONObject json_data = peoples.getJSONObject(i);
                        uid = json_data.getString("uid");
                        String address = json_data.getString("short_address");
                        String available = json_data.getString("available_space");
                        String total = json_data.getString("total_space");
                        String rent = json_data.getString("rent");

                        ((TextView)ll.findViewById(R.id.locate_location)).setText(address);
                        ((TextView)ll.findViewById(R.id.locate_available)).setText(available+"/"+total);
                        ((TextView)ll.findViewById(R.id.locate_rent)).setText(rent);

                    }

//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            protected String getDataOKHTTP() {
                String returnString = null;
                OkHttpClient client = new OkHttpClient();
                System.out.println("Sending uid: "+uid);
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("uid",uid).build();
//                System.out.println(requestBody);
                Request request = new Request.Builder().url(server+"/ParkSafe/getLocationData.php").post(requestBody).build();
                //System.out.println("@ register a new token ");
                try {
                    Response response = client.newCall(request).execute();
                    //response.header("Content-Type: application/json");
                    returnString = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return returnString;
            }

            // When send button is clicked, create message object on Parse

        }
        GetData get = new GetData();
        get.execute();


    }

//    @Override
//    public void onLocationChanged(Location location) {
//
//        latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        //zoom to current position:
//        if(firstLoad) {
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(latLng).zoom(14).build();
//
//            mGoogleMap.animateCamera(CameraUpdateFactory
//                    .newCameraPosition(cameraPosition));
//
//            firstLoad = false;
//        }
//        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//
//    }


}