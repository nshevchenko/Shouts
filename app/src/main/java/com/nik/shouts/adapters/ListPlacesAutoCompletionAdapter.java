package com.nik.shouts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nik.shouts.R;
import com.nik.shouts.models.Place;

import java.util.ArrayList;

/**
 * Created by Mykola Shevchenko on 20/02/2015.
 */
public class ListPlacesAutoCompletionAdapter extends ArrayAdapter<Place> {

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Place> places;

    public ListPlacesAutoCompletionAdapter ( Context ctx, int resourceId, ArrayList<Place> places)
    {
        super( ctx, resourceId, places );
        this.places = places;
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent )
    {
        convertView = (LinearLayout) inflater.inflate( resource, null );
        Place place = places.get(position);

//        TextView txtTimeDate = (TextView)convertView.findViewById(R.id.street);
//        System.out.println(position + " " + place.street);
//        txtTimeDate.setText(place.street);
//
//        TextView detail = (TextView)convertView.findViewById(R.id.detail);
//        detail.setText(place.addressDetail);
//        convertView.invalidate();
        return convertView;
    }
}
