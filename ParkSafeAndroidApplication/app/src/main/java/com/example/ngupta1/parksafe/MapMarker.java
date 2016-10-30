package com.example.ngupta1.parksafe;

/**
 * Created by ngupta1 on 11/10/16.
 */
public class MapMarker {

    String lat;
    String lon;
    String uid;


    public String getLat(){
        return lat;
    }

    public String getLon() { return lon;  }

    public String getUid() { return uid;  }

    public void setLat(String lat){
        this.lat = lat;
    }

    public void setLon(String lon){
        this.lon = lon;
    }

    public void setUid(String uid){
        this.uid = uid;
    }
}
