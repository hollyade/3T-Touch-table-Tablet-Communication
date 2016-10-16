package com.hollyade.honours3t;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hollyade.honours3t.CommunicationProviders.CommunicationProvider;
import com.hollyade.honours3t.CommunicationProviders.FirebaseCommunicationProvider;

/**
 * Start screen. Where the application enters.
 * Establishes that the table is connected.
 * Allows the tablet user to connect.
 */
public class MainActivity extends AppCompatActivity {

    private static final String LOG = "MainActivity";
    private CommunicationProvider communicationProvider;
    private CommunicationProvider.CommunicationChannelListener communicationChannelListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        communicationProvider = new FirebaseCommunicationProvider(getApplicationContext());
        communicationChannelListener = new CommunicationProvider.CommunicationChannelListener() {
            @Override
            public void tableConnected() {
                undullStartOption();
            }
        };
        communicationProvider.checkTableConnection(getApplicationContext(), communicationChannelListener);

//        //TODO remove table fake
//        Button testButton = (Button) findViewById(R.id.tableConnectionTestButton);
//        if (testButton != null) {
//            testButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    communicationProvider.establishUserConnection("table", null);
//                }
//            });
//        }
    }

    /**
     * Adds color to the start button and removes note to wait for Firebase Connection.
     */
    private void undullStartOption() {
        final TextView tableConnectionNote = (TextView) findViewById(R.id.tableConnectionNote);
        //TODO Remove Strings
        if (tableConnectionNote == null) {
            return;
        }
        tableConnectionNote.setText("Let's Begin!");

        final Button startButton = (Button) findViewById(R.id.startButton);
        if (startButton != null) {
            startButton.setVisibility(View.VISIBLE);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (communicationProvider.getColorsTaken().size() == 8) {
                        tableConnectionNote.setText("Sorry, the table is full.");
                        startButton.setText("Retry");
                        return;
                    }
                    createDialog();
                }
            });
        }
    }

    public void createDialog() {
        UsernameColorDialog usernameColorDialog = new UsernameColorDialog(this);
        usernameColorDialog.show();
    }

    /**
     * Username and color chosen has gone through checks for individuality.
     * The username and color should be sent to the table through the
     * {@link com.hollyade.honours3t.CommunicationProviders.CommunicationProvider}.
     * The tablet activity can therefore be started.
     * @param userName Username chosen by the user.
     * @param colorChosen Color chosen by the user. Is represented as an int.
     */
    public void sendUsernameColorData(String userName, String colorChosen) {
        communicationProvider.establishUserConnection(userName, colorChosen);
        Intent intent = new Intent(this, TableTabletActivity.class);
        intent.putExtra("Login", userName + "_" + colorChosen);
        startActivity(intent);
    }

    public CommunicationProvider getCommunicationProvider() {
        return communicationProvider;
    }
}


