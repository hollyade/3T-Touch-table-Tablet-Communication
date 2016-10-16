package com.hollyade.honours3t.CommunicationProviders;


import android.content.Context;
import android.util.Pair;

import com.firebase.client.Firebase;

import java.util.Collection;


public interface CommunicationProvider {

    void checkTableConnection(Context context, CommunicationChannelListener communicationChannelListener);
    void establishUserConnection(String key, String value);
    void sendCommunication(String key, Object value);
    Pair<Boolean, Boolean> checkUsernameColor(String username, String color);
    Collection<String> getColorsTaken();
    void removeUser(String username);
    Firebase getTableConnect();
    String getUsername();

    interface CommunicationChannelListener {
        void tableConnected();
    }

}
