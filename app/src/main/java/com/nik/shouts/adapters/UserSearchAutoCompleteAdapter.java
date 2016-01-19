package com.nik.shouts.adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nik.shouts.R;
import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserSearchAutoCompleteAdapter extends ArrayAdapter<User>{

    private int resource;
    private LayoutInflater inflater;
    private Context context;
    List<User> users; // list of users for autocompletion


    public UserSearchAutoCompleteAdapter(Context ctx, int resourceId)
    {
        super(ctx, resourceId, App.usersCollections.getUsers());
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
        users.addAll(App.usersCollections.getUsers());

    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = (RelativeLayout) inflater.inflate( resource, null );
        User user = App.usersCollections.getUsers().get(position);

        if(position % 2 == 1)
        {
            RelativeLayout bg = (RelativeLayout)convertView.findViewById(R.id.rowContentLayout);
        	bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
        }

        View tempUserViewObject = (TextView)convertView.findViewById(R.id.nameAndSurnameTextView);
        ((TextView)tempUserViewObject).setText(user.getNameAndSurname());

//        tempUserViewObject = (ImageView)convertView.findViewById(R.id.userImg);
//        ((ImageView)tempUserViewObject).setImageBitmap(user.getImageProfile());

        return convertView;
    }

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((User)resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<User> suggestions = new ArrayList<User>();
                for (User customer : users) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }
                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<User>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(App.usersCollections.getUsers());
            }
            notifyDataSetChanged();
        }
    };
//
//    public CustomerAdapter(Context context, int textViewResourceId, List<Customer> customers) {
//        super(context, textViewResourceId, customers);
//        // copy all the customers into a master list
//        mCustomers = new ArrayList<Customer>(customers.size());
//        mCustomers.addAll(customers);
//        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }

}
