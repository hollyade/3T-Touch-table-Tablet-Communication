package com.hollyade.honours3t.CommunicationProviders;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.hollyade.honours3t.Constants;
import com.hollyade.honours3t.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FirebaseCommunicationProvider implements CommunicationProvider {

    private static final String LOG = "FirebaseCommunication";
    private static final String firebaseKey = "https://hollyadehonours.firebaseio.com/";
    private Firebase firebaseConnection;
    private Firebase firebaseExport;
    private Firebase firebaseTableScreenshot;
    private Map<String, String> usernamesColors = new HashMap<String, String>();
    private boolean tableConnected;
    private String username;
    private Context context;

    public FirebaseCommunicationProvider(Context context) {
        connectToFirebase(context);
    }

    private void connectToFirebase(Context context){
        Firebase.setAndroidContext(context);
        Firebase firebase = new Firebase(firebaseKey);
        firebaseConnection = firebase.child(context.getResources().getString(R.string.connection));
        firebaseExport = firebase.child("Upload");
        firebaseTableScreenshot = firebase.child("Screenshot");
    }

    public void checkTableConnection(final Context context, final CommunicationChannelListener communicationChannelListener) {
        this.context = context;
        final Resources resources = context.getResources();
        firebaseConnection.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String username = dataSnapshot.getKey();
                Log.v(LOG, username);
                if (username.equals(resources.getString(R.string.tableConnection))) {
                    Log.v(LOG, resources.getString(R.string.tableConnection));
                    communicationChannelListener.tableConnected();
                    tableConnected = true;
                } else {
                    String[] usernameColorSplit = username.split("_");
                    Integer color = Constants.namesColors.get(usernameColorSplit[1]);
                    usernamesColors.put(usernameColorSplit[0], color.toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Cannot Occur. Do Nothing
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.getKey();
                if (username.equals(resources.getString(R.string.tableConnection))) {
                    tableConnected = false;
                } else {
                    usernamesColors.remove(username);
                }
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

    @Override
    public void removeUser(String username) {
        firebaseConnection.child(username).removeValue();
    }

    @Override
    public void establishUserConnection(String key, String value) {
        //TODO remove table fake
        if (key.equals("table")) {
            Log.v(LOG, firebaseConnection.toString());
            firebaseConnection.child("TableConnected").setValue("connection");
            return;
        }
        this.username = key.split("_")[0];
        setUpCommentListener(context);
        firebaseConnection.child(key).setValue(value);
    }

    @Override
    public void sendCommunication(String key, Object value) {
        firebaseExport.child(key).setValue(value);
    }

    public Pair<Boolean, Boolean> checkUsernameColor(String username, String color) {
        return new Pair<>(!usernamesColors.containsKey(username), !usernamesColors.containsValue(color));
    }

    public Collection<String> getColorsTaken(){
        return usernamesColors.values();
    }

    public Firebase getTableConnect(){
        return firebaseTableScreenshot;
    }

    public String getUsername(){
        return username;
    }

    public void setUpCommentListener(final Context context) {
        Log.v(LOG, "setup");

        new Firebase(firebaseKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String key = dataSnapshot.getKey();
                Log.v(LOG, key);
                if (!key.contains(username)) {
                    return;
                }
                Toast.makeText(context, dataSnapshot.getValue().toString(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //Cannot Occur. Do Nothing
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Cannot Occur. Do Nothing
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