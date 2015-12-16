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
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.App;
import com.nik.shouts.utils.ShoutsUtils;
import com.nik.shouts.utils.UserUtils;

public class UserDetailsLastShoutsListAdapter extends ArrayAdapter<Shout>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public UserDetailsLastShoutsListAdapter(Context ctx, int resourceId, String userId)
    {
        super(ctx, resourceId, ShoutsUtils.getLastUserShouts(userId));
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = (RelativeLayout) inflater.inflate( resource, null );
        Shout shout = App.data.getShouts().get(position);
        System.out.println(position + " " + shout.getContent());
        if(position % 2 == 1)
        {
            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.shoutMainContent);
        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        View tempShoutViewObject = (TextView)convertView.findViewById(R.id.username);
        User creator = UserUtils.getUserById(shout.getCreatorId());
        ((TextView)tempShoutViewObject).setText(creator.getNameAndSurname());

        tempShoutViewObject = (TextView)convertView.findViewById(R.id.title);
        ((TextView)tempShoutViewObject).setText(shout.getContent());

        tempShoutViewObject  = (TextView)convertView.findViewById(R.id.date);
        ((TextView)tempShoutViewObject).setText(shout.getDate().toString());

        tempShoutViewObject  = (TextView)convertView.findViewById(R.id.location);
        ((TextView)tempShoutViewObject).setText(shout.getLocationName());

        return convertView;
    }
}
