package com.nik.shouts.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nik.shouts.R;
import com.nik.shouts.models.App;
import com.nik.shouts.models.Place;
import com.nik.shouts.models.PlaceResult;
import com.nik.shouts.models.SearchResult;
import com.nik.shouts.models.Shout;

import java.util.ArrayList;

public class SearchListAdapter extends ArrayAdapter<SearchResult>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<SearchResult> results;

    public SearchListAdapter(Context ctx, int resourceId, ArrayList<SearchResult> results)
    {
        super(ctx, resourceId, results);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
        this.results = results;
    }

    public void updateSearchResults(ArrayList<SearchResult> results){
        clear();
        this.results = results;
        addAll(results);
    }


    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = (RelativeLayout) inflater.inflate( resource, null );
        SearchResult search = results.get(position);
        System.out.println(position + " " + search.getTitle());
        if(position % 2 == 1)
        {
            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.search_row_background);
        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        ((ImageView)convertView.findViewById(R.id.imageView)).setImageBitmap(search.getImageView());
        ((TextView)convertView.findViewById(R.id.title)).setText(search.getTitle());
        if(search.getSubTitle().length() > 0)
            ((TextView)convertView.findViewById(R.id.subTitle)).setText(search.getSubTitle());
        return convertView;
    }
}
