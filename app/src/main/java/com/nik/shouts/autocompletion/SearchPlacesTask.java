//package com.nik.shouts.autocompletion;
//
//import android.os.AsyncTask;
//import android.util.Log;
//
//import com.google.android.gms.maps.model.LatLng;
//import com.heygames.footiefinder.Adapters.ListPlacesAutoCompletionAdapter;
//import com.heygames.footiefinder.Elements.AutoCompleteSearch;
//import com.heygames.footiefinder.Elements.Place;
//import com.heygames.footiefinder.Managers.AppManager;
//import com.heygames.footiefinder.Managers.TouchManager;
//import com.heygames.footiefinder.R;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//
///**
// * Created by Mykola Shevchenko on 20/02/2015.
// */
//public class SearchPlacesTask extends AsyncTask<String, Void, ArrayList<Place>>
//{
//
////    private static AutoCompleteSearch parentEditView;
//    private static final String urlApi ="https://maps.googleapis.com/maps/api/geocode/json?address=";
//    private static final String api = "AIzaSyDctGsoNiqUQWer9nFcBQr78xdnXgb-lzU";
//    private static final String TAG_RESULTS="results";
//    private static final String TAG_GEOMETRY="geometry";
//    private static final String TAG_FRORMATTED_ADDRESS="formatted_address";
//    private static final String TAG_LOCATION="location";
//    private static final String TAG_LAT="lat";
//    private static final String TAG_LNG="lng";
//    private static final String TAG_STATUS = "status";
//    private static final String TAG_STATUS_OK = "OK";
//
//    JSONObject jObject;
//
//    public SearchPlacesTask(AutoCompleteSearch editView)
//    {
//        parentEditView = editView;
//    }
//
//    @Override
//    protected ArrayList<Place> doInBackground(String... place) {
//        // For storing data from web service
//        String data = "";
//        String address = place[0];
//        if(address.length() == 0)
//            return AppManager.dataManager.getLastSearched();
//        else
//            address = address.replace(' ', '+');
//
//        ArrayList<Place> returnPlaces = new ArrayList<Place>();
//        String url = urlApi + address + "&sensor=false&key=" + api;
//        try{
//            System.out.println("Url to download : " + url);
//            data = downloadUrl(url);
//            returnPlaces = parsePlaces(data);
//        }catch(Exception e){
//            Log.d("Background Task", e.toString());
//        }
//        return returnPlaces;
//    }
//
//    @Override
//    protected void onPostExecute(ArrayList<Place> places) {
//        super.onPostExecute(places);
//        if(places == null)
//            return;
//
//        if(places.size() == 0)
//            return;
//
//        parentEditView.updateAdapterCompletionText(places);
//    }
//
//    private String downloadUrl(String strUrl) throws IOException {
//        String data = "";
//        InputStream iStream = null;
//        HttpURLConnection urlConnection = null;
//        try{
//            URL url = new URL(strUrl);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.connect();
//            iStream = urlConnection.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
//            StringBuffer sb = new StringBuffer();
//
//            String line = "";
//            while( ( line = br.readLine()) != null){
//                sb.append(line);
//            }
//            data = sb.toString();
//            br.close();
//
//        }catch(Exception e)
//        {
//            System.out.println("Exception while downloading url: " + e.toString());
//        }finally
//        {
//            iStream.close();
//            urlConnection.disconnect();
//        }
//        return data;
//    }
//
//    private ArrayList<Place> parsePlaces(String jsonData)
//    {
//        ArrayList<Place> places = new ArrayList<Place>();
//        try
//        {
//            JSONObject data = new JSONObject(jsonData);
//
//            if (!data.getString(TAG_STATUS).equals(TAG_STATUS_OK))
//                return null;
//            JSONArray res = data.getJSONArray(TAG_RESULTS);
//            for (int i = 0; i < res.length(); i++)
//            {
//                JSONObject obj = res.getJSONObject(i);
//                JSONObject loc = obj.getJSONObject(TAG_GEOMETRY).getJSONObject(TAG_LOCATION);
//                LatLng latLng = new LatLng(loc.getDouble(TAG_LAT), loc.getDouble(TAG_LNG));
//                String formattedAddress = obj.getString(TAG_FRORMATTED_ADDRESS);
//                Place place = new Place(latLng, formattedAddress);
//                places.add(place);
//            }
//        }
//        catch (JSONException ex)
//        {
//            Log.i("Error: parsing .. ", String.valueOf(ex));
//        }
//        return places;
//    }
//}
