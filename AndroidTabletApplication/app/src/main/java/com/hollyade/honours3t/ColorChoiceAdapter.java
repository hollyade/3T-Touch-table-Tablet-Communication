package com.hollyade.honours3t;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Collection;

public class ColorChoiceAdapter extends BaseAdapter {
    private Context context;
    private String[] colorsAvailable;

    public ColorChoiceAdapter(Context c, Collection<String> colors) {
        context = c;
        this.colorsAvailable = colors.toArray(new String[colors.size()]);
    }

    public int getCount() {
        return colorsAvailable.length;
    }

    public Object getItem(int position) { return null;}

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }

        Resources resources = context.getResources();

        imageView.setBackgroundColor(resources.getColor(Integer.parseInt(colorsAvailable[position])));

        imageView.setScaleX(0.75f);
        imageView.setScaleY(0.75f);

        return imageView;
    }


}
