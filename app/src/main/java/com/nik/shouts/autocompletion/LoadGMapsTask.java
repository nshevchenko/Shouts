package com.nik.shouts.autocompletion;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by nik on 21/02/15.
 */
class LoadGMapsTask extends AsyncTask<Integer, Void, GoogleMap> {

        private GoogleMap googleMap;

        @Override
        protected GoogleMap doInBackground(Integer... mapId) {
            // For storing data from web service
//            try
//            {
//                if(googleMap == null)
//                    googleMap = ((MapFragment) TouchManager.getActivity().getFragmentManager().findFragmentById(
//                            mapId[0])).getMap();
//            } catch (NullPointerException exception){
//                Log.e("mapApp", exception.toString());
//            }
//            return googleMap;
//        }
//
//        private void addMarker(){
//            /** Make sure that the map has been initialised **/
//            if(null != googleMap){
//                googleMap.addMarker(new MarkerOptions()
//                                .position(new LatLng(10.02, 2.03))
//                                .title("Football Match")
//                                .draggable(true)
//                );
//            }
            return null;
        }

        @Override
        protected void onPostExecute(GoogleMap map)
        {
            super.onPostExecute(map);

            if (map == null)
                return;
        }
    }

