package com.nik.shouts.utils;

import android.app.Activity;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by nik on 12/12/15.
 */

public class MapUtils {

    public static LatLng getLatLngFromLocation(Location location){
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        return new LatLng(lat, lng);
    }

    public static GoogleMap initGoogleMap(MapView mapView, Activity activity) {
        GoogleMap googleMap = mapView.getMap();
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);

        MapsInitializer.initialize(activity);

        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
//        googleMap.moveCamera(cameraUpdate);
        return googleMap;
    }

    public static void animateMapViewToCoordinates(GoogleMap googleMap, LatLng coordinates){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordinates, 15);
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(coordinates)      // Sets the center of the map to Mountain View
                .zoom(14)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
