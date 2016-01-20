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
    ArrayList<User> users; // list of users for autocompletion


    public UserSearchAutoCompleteAdapter(Context ctx, int resourceId, ArrayList<User> users)
    {
        super(ctx, resourceId, users);
        resource = resourceId;
        inflater = LayoutInflater.from( ctx );
        context  = ctx;
        this.users = new ArrayList<User>();
//        this.users.addAll(users);

    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent ) 
    {
    	convertView = (RelativeLayout) inflater.inflate( resource, null );
        if(position < users.size()){
            User user = users.get(position);
            if (position % 2 == 1) {
                RelativeLayout bg = (RelativeLayout) convertView.findViewById(R.id.rowContentLayout);
                bg.setBackgroundColor(Color.parseColor("#F3F3F3"));
            }

            View tempUserViewObject = (TextView) convertView.findViewById(R.id.nameAndSurnameTextView);
            ((TextView) tempUserViewObject).setText(user.getNameAndSurname());

//        tempUserViewObject = (ImageView)convertView.findViewById(R.id.userImg);
//        ((ImageView)tempUserViewObject).setImageBitmap(user.getImageProfile());
        }
        return convertView;
    }

    @Override
    public Filter getFilter(){
        return filter;
    }

    @Override
    public int getCount(){
        return users.size();
    }


    @Override
    public User getItem(int position){
        return users.get(position);
    }

    private Filter filter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((User)resultValue).getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            System.out.println("filter results");
            if (constraint != null) {
                ArrayList<User> suggestions = new ArrayList<User>();
                System.out.println("change on :" + App.usersCollections.getCurrentlyLoggedInUser().getFriends().size());

                for (User user: App.usersCollections.getCurrentlyLoggedInUser().getFriends()) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (user.getNameAndSurname().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        System.out.println("result = " +  user.getNameAndSurname());
                        suggestions.add(user);
                    }
                }
                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            users.clear();
            System.out.println("publish results");
            if (results != null && results.count > 0) {
                // we have filtered results
                System.out.println("positive results");
//                System.out.println("result values" result.values);
                users.addAll((ArrayList<User>) results.values);
            } else {
                // no filter, add entire original list back in
//                addAll(App.usersCollections.getUsers());
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
