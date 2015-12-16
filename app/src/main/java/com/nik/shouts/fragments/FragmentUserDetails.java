package com.nik.shouts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nik.shouts.R;
import com.nik.shouts.adapters.FeedListAdapter;
import com.nik.shouts.adapters.UserDetailsLastShoutsListAdapter;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.UserUtils;

import org.w3c.dom.Text;

/**
 * Created by nik on 13/12/15.
 */

public class FragmentUserDetails extends Fragment {

    public static FragmentUserDetails newInstance() {
        FragmentUserDetails fragmentUserDetails = new FragmentUserDetails();
        return fragmentUserDetails;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // read userID
        String userId = getArguments().getString("userID");
        User user = UserUtils.getUserById(userId);

        // read main rootView containing all elements
        View rootView = inflater.inflate(R.layout.fragment_user_detail, container, false);
        setUpListViews(rootView, userId); // set up listview
        findFragmentElements(rootView, user); // find elements in fragment
        return rootView;
    }
    /*
    * Set up the two lists view in this fragmetn (Last Activities and Last Shouts)
    * */
    private void setUpListViews(View rootView, String userId){
        ListView lastShoutsListView = (ListView) rootView.findViewById(R.id.lastShoutsListView);
        lastShoutsListView.setAdapter(new UserDetailsLastShoutsListAdapter(this.getContext(), R.layout.feed_row, userId));

        ListView lastActivitiesListView  = (ListView) rootView.findViewById(R.id.lastActivitiesListView);
        lastShoutsListView.setAdapter(new UserDetailsLastShoutsListAdapter(this.getContext(), R.layout.feed_row, userId));
    }

    /*
    *   Find all fragment elements regarding user info
    * */
    private void findFragmentElements(View rootView, User user) {

        TextView tempTextView = (TextView)rootView.findViewById(R.id.nameTextView);
        tempTextView.setText(user.getName());

        tempTextView = (TextView)rootView.findViewById(R.id.surnameTextView);
        tempTextView.setText(user.getSurname());

        EditText tempEditText = (EditText)rootView.findViewById(R.id.interestsEditText);
        tempEditText.setSelected(false);
        tempTextView.setText(user.getInterestsAsString());
    }
}
