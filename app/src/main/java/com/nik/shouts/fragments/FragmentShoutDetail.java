package com.nik.shouts.fragments;

import android.support.v4.app.DialogFragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.nik.shouts.R;
import com.nik.shouts.adapters.FeedListAdapter;
import com.nik.shouts.models.Shout;
import com.nik.shouts.utils.Configurations;

/**
 * Created by nik on 26/10/15.
 */

public class FragmentShoutDetail extends DialogFragment {

    // Fragments icons to be read and validated
    private int[] fragments_icons = {
            R.id.clockDateImg,
            R.id.limitPeopleImg,
            R.id.inviteFriendsImg,
            R.id.hashtagsImg
    };
    private Shout shoutDetail;

    public void setShoutDetail(Shout shout){
        this.shoutDetail = shout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shout_detail, container, false);

        findFragmentElements(rootView);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }

    private void findFragmentElements(View rootView){

        // set up icons
        for (int i = 0; i < fragments_icons.length; i++) {
            ImageView tempImageView = (ImageView) rootView.findViewById(fragments_icons[i]);
            tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        }

        // set up data
        ((EditText) rootView.findViewById(R.id.titleEditText)).setText(shoutDetail.getTitle());
        ((EditText) rootView.findViewById(R.id.dateEditText)).setText(shoutDetail.getDateAsStringInList());
        ((EditText) rootView.findViewById(R.id.descriptionEditText)).setText(shoutDetail.getContent());
        ((EditText) rootView.findViewById(R.id.timeEditText)).setText(shoutDetail.getTimeAsString());
        ((EditText) rootView.findViewById(R.id.limitPeopleEditText)).setText(shoutDetail.getParticipationLimit()+"");
        ((EditText) rootView.findViewById(R.id.inviteFriendsEditText)).setText(shoutDetail.getParticipationsAsString());
    }
}

