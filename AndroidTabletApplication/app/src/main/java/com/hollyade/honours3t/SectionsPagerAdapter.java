package com.hollyade.honours3t;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private TableFragment tableFragment;
    private TabletFragment tabletFragment;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (tableFragment == null) {
                tableFragment = TableFragment.newInstance(position + 1);
            }
            return tableFragment;
        } else {
            if (tabletFragment == null) {
                tabletFragment = TabletFragment.newInstance(position + 1);
            }
            return tabletFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TABLE VIEW";
            case 1:
                return "TABLET VIEW";
        }
        return null;
    }
}