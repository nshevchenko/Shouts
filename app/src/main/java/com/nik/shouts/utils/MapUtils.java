package com.nik.shouts.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.models.Place;

import java.io.ByteArrayOutputStream;

/**
 * Created by nik on 12/12/15.
 */

public class MapUtils {

    private static Place lastUserKnownPlace;

//    public static

    /**
     * Get Latitude and Longitude Objecvt from a location object
     * @param location
     * @return
     */
    public static LatLng getLatLngFromLocation(Location location){
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        return new LatLng(lat, lng);
    }

    /**
     * initialize google map view
     * @param mapView
     * @param activity
     * @return
     */

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

    /**
     * Animate a google map object towards a specific location in LatLng
     * @param googleMap
     * @param coordinates
     */
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


    public static String getStaticMapFromLatLng(LatLng latLng) {
        String marker = "&markers=color:red%7C" + latLng.latitude + "," + latLng.longitude;
        return "?center=" + latLng.latitude + "," + latLng.longitude + "&zoom=16&size=800x800&sensor=false";

    }
    /**
     * Request permission location for the new Marshamallow version
     * @param fromActivity
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkLocationPermissionActive(Activity fromActivity) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (ActivityCompat.checkSelfPermission(fromActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(fromActivity.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    /**
     * Get static map bitmap from encoded string
     * @param encodedString
     * @return
     */
    public static Bitmap stringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

}
