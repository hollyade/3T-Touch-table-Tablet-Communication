package com.hollyade.honours3t.CommunicationProviders;


import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Collection;

public class MockCommunicationProvider implements CommunicationProvider {

    private static final String LOG = "MockCommunication";
    private boolean tableConnected = false;

    public void checkTableConnection(Context context, final CommunicationChannelListener communicationChannelListener) {
        Log.v(LOG, "Table connection confirmed.");
        if (tableConnected) {
            communicationChannelListener.tableConnected();
        }
        tableConnected = true;
    }

    public void establishUserConnection(String key, String value) {
    }

    public Pair<Boolean, Boolean> checkUsernameColor(String username, String color) {
        return new Pair<>(true, true);
    }

    public Collection<String> getColorsTaken() {
        return new ArrayList<String>();
    }

    @Override
    public void removeUser(String username) {

    }

    @Override
    public void sendCommunication(String key, Object value) {}

    public Firebase getTableConnect() {
        return null;
    }

    public String getUsername() {
        return null;
    }
}
