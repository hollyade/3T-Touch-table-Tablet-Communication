package com.hollyade.honours3t;

import java.util.HashMap;

public class Constants {
    // references to all colors
    public static final Integer[] colors = new Integer[] {
            R.color.userColorYellow,
            R.color.userColorRed,
            R.color.userColorBlue,
            R.color.userColorGreen,
            R.color.userColorOrange,
            R.color.userColorPink,
            R.color.userColorBlack,
            R.color.userColorPurple
    };

    public static HashMap<Integer, String> colorsNames = new HashMap<Integer, String>();
    static {
        colorsNames.put(R.color.userColorYellow, "Yellow");
        colorsNames.put(R.color.userColorRed, "Red");
        colorsNames.put(R.color.userColorBlue, "Blue");
        colorsNames.put(R.color.userColorGreen, "Green");
        colorsNames.put(R.color.userColorOrange, "Orange");
        colorsNames.put(R.color.userColorPink, "Pink");
        colorsNames.put(R.color.userColorBlack, "Black");
        colorsNames.put(R.color.userColorPurple, "Purple");
    }

    public static HashMap<String, Integer> namesColors = new HashMap<String, Integer>();
    static {
        namesColors.put("Yellow", R.color.userColorYellow);
        namesColors.put("Red", R.color.userColorRed);
        namesColors.put("Blue", R.color.userColorBlue);
        namesColors.put("Green", R.color.userColorGreen);
        namesColors.put("Orange", R.color.userColorOrange);
        namesColors.put("Pink", R.color.userColorPink);
        namesColors.put("Black", R.color.userColorBlack);
        namesColors.put("Purple", R.color.userColorPurple);
    }
}
