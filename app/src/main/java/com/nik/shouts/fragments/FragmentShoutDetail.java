package com.nik.shouts.fragments;

import android.support.v4.app.DialogFragment;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nik.shouts.R;
import com.nik.shouts.adapters.FeedListAdapter;
import com.nik.shouts.interfaces.RequestCallback;
import com.nik.shouts.models.App;
import com.nik.shouts.models.Shout;
import com.nik.shouts.models.User;
import com.nik.shouts.utils.ApiUtils;
import com.nik.shouts.utils.Configurations;
import com.nik.shouts.utils.Messages;

/**
 * Created by nik on 26/10/15.
 */

public class FragmentShoutDetail extends DialogFragment implements View.OnClickListener {

    // Fragments icons to be read and validated
    private int[] fragments_icons = {
            R.id.clockDateImg,
            R.id.limitPeopleImg,
            R.id.participationsImg,
            R.id.hashtagsImg
    };

    private int[] fragment_buttons = {
            R.id.participateTextView,
            R.id.interestedTextView,
            R.id.creatorTextView
    };

    // shout to show in the window
    private Shout shoutDetail;
    // fragment view
    private View fragmentView;

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

        fragmentView = rootView;
        findFragmentElements(rootView);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return rootView;
    }

    /**
     * Find views in the fragment to be customised
     * @param rootView
     */
    private void findFragmentElements(View rootView){

        // set up icons
        for (int i = 0; i < fragments_icons.length; i++) {
            ImageView tempImageView = (ImageView) rootView.findViewById(fragments_icons[i]);
            if(tempImageView != null)
                tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.colorPrimaryLight), PorterDuff.Mode.SRC_ATOP);
        }

        for (int i = 0; i < fragment_buttons.length; i++){
            ((TextView)rootView.findViewById(fragment_buttons[i])).setOnClickListener(this);
        }

        // set up data
        ((EditText) rootView.findViewById(R.id.titleEditText)).setText(shoutDetail.getTitle());
        ((EditText) rootView.findViewById(R.id.dateEditText)).setText(shoutDetail.getDateAsStringInList());
        ((EditText) rootView.findViewById(R.id.descriptionEditText)).setText(shoutDetail.getContent());
        ((EditText) rootView.findViewById(R.id.timeEditText)).setText(shoutDetail.getTimeAsString());
        ((EditText) rootView.findViewById(R.id.limitPeopleEditText)).setText(shoutDetail.getParticipationLimit() + "");
        ((EditText) rootView.findViewById(R.id.participationsEditText)).setText(shoutDetail.getParticipationsAsString());

        // set up textviews
        ((TextView) rootView.findViewById(R.id.creatorTextView)).setText("by " + shoutDetail.getCreator().getNameAndSurname());

        System.out.println("shout " + shoutDetail.isCurrentUserAParticipant(App.usersCollections.getCurrentlyLoggedInUser()));
        if( shoutDetail.isCurrentUserAParticipant(App.usersCollections.getCurrentlyLoggedInUser())) {
            ((TextView) rootView.findViewById(R.id.participationsTextView)).setText(R.string.unparticipate);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        final User currentUser = App.usersCollections.getCurrentlyLoggedInUser();
        switch (viewId) {
            case R.id.interestedTextView:

//                if( currentUser != null )
//                    currentUser.addI(currentUser);
                break;
            case R.id.participateTextView:
                RequestCallback requestCallback = null;
                if (currentUser != null){
                    if( ! shoutDetail.isCurrentUserAParticipant(currentUser)){
                        Log.d("MESSAGE", "adding");
                        shoutDetail.addNewParticipant(currentUser);

                        ApiUtils.uploadNewJsonObject(new RequestCallback() {
                            @Override
                            public void onRequestComplete(String result) {
                                System.out.println(result + " " + Configurations.SUCCESS_STATUS_CODE);
                                if (result.equals(Configurations.SUCCESS_STATUS_CODE)) {
                                    ((EditText) fragmentView.findViewById(R.id.participationsEditText)).setText(shoutDetail.getParticipationsAsString());
                                    ((TextView) fragmentView.findViewById(R.id.participateTextView)).setText(R.string.unparticipate);
                                    fragmentView.invalidate();
                                } else
                                    Log.d("MESSAGES", "FAILED ADDING PARTICIPANT");
                            }
                        }, shoutDetail.toJSON());

                    }
                    else {
                        Log.d("MESSAGE" , "removing");
                        shoutDetail.removeParticipant(currentUser);
                        requestCallback = new RequestCallback() {
                            @Override
                            public void onRequestComplete(String result) {
                                if(result.equals(Configurations.SUCCESS_STATUS_CODE)){
                                    ((EditText) fragmentView.findViewById(R.id.participationsEditText)).setText(shoutDetail.getParticipationsAsString());
                                    ((TextView) fragmentView.findViewById(R.id.participateTextView)).setText(R.string.participate);
                                }
                                else
                                    Log.d("MESSAGE" , "fail");
                            }
                        };
                        ApiUtils.uploadNewJsonObject(requestCallback, shoutDetail.toJSON());
                    }

                }
                break;
            case R.id.creatorTextView:
                User userClicked = shoutDetail.getCreator();
                FragmentUserDetails fragmentUserDetail = new FragmentUserDetails();
                fragmentUserDetail.setUserDetail(userClicked);
                fragmentUserDetail.show(getFragmentManager(), "User detail");
                break;
        }
    }
}

