package com.hollyade.honours3t;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.hollyade.honours3t.CommunicationProviders.CommunicationProvider;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class TableFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    Firebase firebaseTableScreenshot;
    private String username;
    private TableTabletActivity tableTabletActivity;

    public TableFragment() {
    }

    public static TableFragment newInstance(int sectionNumber) {
        TableFragment fragment = new TableFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_table, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText("Table View");
        this.tableTabletActivity = (TableTabletActivity) getActivity();
        CommunicationProvider communicationProvider = tableTabletActivity.getCommunicationProvider();
        firebaseTableScreenshot = communicationProvider.getTableConnect();
        this.username = tableTabletActivity.getUsername();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        final FloatingActionButton loadImageButton = (FloatingActionButton) getView().findViewById(R.id.buttonLoadPicture);
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseTableScreenshot.child(username).setValue("Request");
                refreshImage(getContext(), firebaseTableScreenshot);
            }
        });
    }

    public void refreshImage(final Context context, final Firebase communicationChannelListener) {
        final Resources resources = context.getResources();
        firebaseTableScreenshot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Do Nothing
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getValue().toString().equals("Request")) {
                    return;
                }
                if(!dataSnapshot.getKey().toString().equals(username)) {
                    return;
                }
                Log.v("lOGGING", dataSnapshot.getValue().toString() );
                ImageView uploadedImage = (ImageView) tableTabletActivity.findViewById(R.id.tableViewImage);
                String rawImage = dataSnapshot.getValue().toString();

                //Convert String data to binary image file
                byte[] bytesImg = Base64.decode(rawImage, Base64.NO_WRAP);


                Log.v("lOGGING", bytesImg.toString());

                Bitmap bmp = BitmapFactory.decodeByteArray(bytesImg, 0, bytesImg.length);

                uploadedImage.setImageBitmap(bmp);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //Cannot Occur. Do Nothing
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //Cannot Occur. Do Nothing
            }
        });
    }
}
