package com.hollyade.honours3t;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SendButtonTutorialActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_button);
        FloatingActionButton floatingActionSendButton = (FloatingActionButton) findViewById(R.id.buttonSendPictureTutorial);
        Bundle extra = getIntent().getExtras();
        float x = extra.getFloat("X");
        float y = extra.getFloat("Y");

        floatingActionSendButton.setX(x);
        floatingActionSendButton.setY(y);
        floatingActionSendButton.setVisibility(View.VISIBLE);
    }

    public void finish(View view) {
        super.finish();
    }
}
