package com.pasha.efebudak.popularmovies.util;

import android.app.Activity;
import android.view.Display;

/**
 * Created by efebudak on 30/09/15.
 */
public class Utils {

    public static int calculateNumberOfColumns(Activity context) {

        Display display = context.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated

        return 2;

    }

}
