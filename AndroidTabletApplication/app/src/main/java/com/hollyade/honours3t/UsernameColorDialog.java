package com.hollyade.honours3t;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.hollyade.honours3t.ColorChoiceAdapter;
import com.hollyade.honours3t.Constants;
import com.hollyade.honours3t.MainActivity;
import com.hollyade.honours3t.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UsernameColorDialog {

    private String userName = "";
    private String colorChosen = "";
    private static final String LOG = "UsernameColorDialog";
    private MainActivity activity;
    private AlertDialog alertDialog;

    public UsernameColorDialog(Activity activity) {
        this.activity = (MainActivity) activity;
        createDialog();
    }

    public void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Get the layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        alertDialog = builder.create();
        alertDialog.setView(inflater.inflate(R.layout.dialog_login, null));
        // Add action buttons
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Due to auto dismiss, onClick functionality must be done after alert is shown.
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Cancel the login
                dialog.cancel();
            }
        });
    }

    public void show() {
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userName.length() == 0 || colorChosen.length() == 0) {
                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Please enter a name and choose a color.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Pair<Boolean, Boolean> usernameColorAvailable = checkUsernameColor();
                    if (usernameColorAvailable.first && usernameColorAvailable.second) {
                        sendUsernameColor();
                        alertDialog.dismiss();
                    } else {
                        String text = "";
                        if (!usernameColorAvailable.first && !usernameColorAvailable.second) {
                            text = "Sorry, your username and color is taken!";
                        } else if (!usernameColorAvailable.first) {
                            text = "Sorry, your username is taken!";
                        } else {
                            text = "Sorry, your color is taken!";
                        }
                        Toast toast = Toast.makeText(activity.getApplicationContext(), text, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });
        usernameSelection(alertDialog.findViewById(R.id.username));
        setupColorSelection(alertDialog.findViewById(R.id.userColorSelectionGrid));
    }

    private void usernameSelection(View editText) {
        EditText editTextUsername = (EditText) editText;
        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do Nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                userName = editable.toString();
                Log.v(LOG, "Username: " + userName);
            }
        });
    }

    private void setupColorSelection(View gridView) {
        GridView gridview = (GridView) gridView;
        Collection<String> colorsTaken = activity.getCommunicationProvider().getColorsTaken();
        final List<String> colorsAvailable = new ArrayList<String>();
        for (Integer color : Constants.colors) {
            if (!colorsTaken.contains(color.toString())) {
                colorsAvailable.add(color.toString());
            }
        }
        gridview.setNumColumns(colorsAvailable.size());

        final ColorChoiceAdapter colorChoiceAdapter = new ColorChoiceAdapter(activity.getApplicationContext(), colorsAvailable);
        gridview.setAdapter(colorChoiceAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private View previousView = null;
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (previousView != null) {
                    previousView.setScaleX(0.75f);
                    previousView.setScaleY(0.75f);
                }
                v.setScaleY(1f);
                v.setScaleX(1f);
                previousView = v;
                colorChosen = colorsAvailable.get(position);
                Log.v(LOG, "ColorChosen: " + colorChosen);
            }
        });
    }

    public void sendUsernameColor() {
        String color = Constants.colorsNames.get(Integer.parseInt(colorChosen));
        activity.sendUsernameColorData(userName+"_"+color, "ConnectionAttempt");
    }

    private Pair<Boolean, Boolean> checkUsernameColor() {
        return activity.getCommunicationProvider().checkUsernameColor(userName, colorChosen.toString());
    }
}
