package com.nik.shouts.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nik.shouts.R;
import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;

public class FeedListAdapter extends ArrayAdapter<Shout>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    
    public FeedListAdapter(Context ctx, int resourceId)
    {
        super(ctx, resourceId, App.shoutsCollections.getShouts());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = (RelativeLayout) inflater.inflate( resource, null );
        Shout shout = App.shoutsCollections.getShouts().get(position);
        System.out.println(position + " " + shout.getContent());
        if(position % 2 == 1)
        {
            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.feedsLayoutBg);
        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        View tempShoutViewObject = (TextView)convertView.findViewById(R.id.username);
//        ((TextView)tempShoutViewObject).setText(shout.getUser().getNameAndSurname());

        tempShoutViewObject = (TextView)convertView.findViewById(R.id.title);
        ((TextView)tempShoutViewObject).setText(shout.getTitle());

        tempShoutViewObject = (TextView)convertView.findViewById(R.id.content);
        ((TextView)tempShoutViewObject).setText(shout.getContent());

        tempShoutViewObject  = (TextView)convertView.findViewById(R.id.date);
        if(shout.getDate() != null)
            ((TextView)tempShoutViewObject).setText(shout.getDate().toString());

        tempShoutViewObject  = (TextView)convertView.findViewById(R.id.location);
        ((TextView)tempShoutViewObject).setText(shout.getLocationName());

//        AppManager.fontTextView(nameLabel, 23);

//        TriggerButtonUI ten = new TriggerButtonUI( (Button)convertView.findViewById(R.id.ten), activityName.addPurchase );
//        ten.setId(position);
//        TriggerButtonUI twenty = new TriggerButtonUI( (Button)convertView.findViewById(R.id.twenty), activityName.addPurchase);
//        twenty.setId(position);
        return convertView;
    }
}
