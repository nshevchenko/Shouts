package com.nik.shouts.autocompletion;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Mykola Shevchenko on 18/02/2015.
 */
public class GMaps {

    private GoogleMap googleMap;

    public GMaps(int mapId)
    {
        LoadGMapsTask loadMap = new LoadGMapsTask();
        loadMap.execute(mapId);
    }

    public void updateLoadedMap(GoogleMap map)
    {
        googleMap = map;
    }

    public void cameraUpdatePos(LatLng pos)
    {
        if(googleMap == null)
        {
            System.out.println("Google Map view is null");
            return;
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,2000));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(pos));
    }

    public LatLng getCurrentLocation()
    {
//        Activity act = TouchManager.getActivity();
//        Location lastLoc = AppManager.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        double lat = lastLoc.getLatitude();
//        double lng = lastLoc.getLongitude();
//        LatLng lastLatLng = new LatLng(lat, lng);
        return null;
    }


    private void setUpMap()
    {
        if(googleMap == null )
            return;
        googleMap.setMyLocationEnabled(true);
        LatLng loc = getCurrentLocation();
        cameraUpdatePos(loc);
    }

    private void addMarker()
    {
        if(googleMap == null )
            return;
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(10.02, 2.03))
                .title("Football Match")
                .draggable(true)
        );
    }
}

