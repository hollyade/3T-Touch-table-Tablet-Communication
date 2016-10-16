package com.hollyade.honours3t;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.Firebase;
import com.hollyade.honours3t.CommunicationProviders.CommunicationProvider;
import com.hollyade.honours3t.CommunicationProviders.FirebaseCommunicationProvider;


public class TableTabletActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private CommunicationProvider communicationProvider;
    private String username;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
        Firebase.setAndroidContext(getApplicationContext());
        establishCommunicationProvider();

        Intent intent = getIntent();
        String[] usernameColor = intent.getStringExtra("Login").split("_");
        this.username = usernameColor[0];
        this.color = usernameColor[1];

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void establishCommunicationProvider() {
        this.communicationProvider = new FirebaseCommunicationProvider(getApplicationContext());
    }

    public CommunicationProvider getCommunicationProvider() {
        return this.communicationProvider;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tablet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        communicationProvider.removeUser(username);
        super.onBackPressed();
    }

    public String getUsername(){
        return this.username;
    }

    public String getColor(){
        return this.color;
    }
}
