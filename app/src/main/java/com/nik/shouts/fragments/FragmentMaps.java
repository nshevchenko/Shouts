package com.nik.shouts.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.nik.shouts.R;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.MapUtils;

/**
 * Created by nik on 26/10/15.
 */

public class FragmentMaps extends Fragment implements View.OnClickListener, LocationListener {

    private LocationManager locationManager;
    private MapView mapView;
    private GoogleMap googleMap;

    public static FragmentMaps newInstance(int page, String title) {
        FragmentMaps fragmentMaps = new FragmentMaps();
        return fragmentMaps;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        setUpGoogleMap(rootView, savedInstanceState);
        findElements(rootView);
        return rootView;
    }

    //
    // set up google map view
    //
    private void setUpGoogleMap(View rootView, Bundle savedInstanceState){
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        googleMap = MapUtils.initGoogleMap(mapView, getActivity());
    }

    //
    // Activate location button
    //
    private void findElements(View rootView){
        FloatingActionButton myLocationButton = (FloatingActionButton) rootView.findViewById(R.id.mylocation);
        myLocationButton.setOnClickListener(this);
    }

    //
    // Update map view on my location coordinates. Set call back to this fragment
    //
    private void updateMapOnMyLocation() {
        locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        try{
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }catch (SecurityException se) {
            Log.d("TAG", "SE CAUGHT");
            se.printStackTrace();
        }
    }

    //
    // Animate camera towards coordinates
    //


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause(){
        System.out.println("paused, map off");
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroy() {
        System.out.println("destroy");
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    // LISTENERS

    @Override
    public void onClick(View view) {
        int id = view.getId();
        System.out.println(id);
        switch (id){
            case R.id.mylocation:
                if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // call request permission popup in order to access the location
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                            Configurations.REQUEST_CODE_ASK_PERMISSIONS);
                } else {
                    //permission granted bro, get dat location!!
                    updateMapOnMyLocation();
                }
                break;
        }
    }

    //
    // Listener for requesting permissions
    //
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Configurations.REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    updateMapOnMyLocation();
                } else {
                    System.out.println("permission denined, do nothing yet.. :(");
                    // Permission Denied
//                    Toast.makeText(MainActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
//                            .show();
                }

                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /*
    * ON LOCATION LISTENERS
    */

    @Override
    public void onLocationChanged(Location location) {
        Log.d("CHANGED", "LOCATION UPDATED");
        System.out.println(location.getLatitude());
        LatLng coordinates = MapUtils.getLatLngFromLocation(location);
        MapUtils.animateMapViewToCoordinates(googleMap, coordinates);
        try {
            locationManager.removeUpdates(this);
        } catch(SecurityException ex){
            System.out.println("Permission Denied");
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        System.out.println(s);

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
