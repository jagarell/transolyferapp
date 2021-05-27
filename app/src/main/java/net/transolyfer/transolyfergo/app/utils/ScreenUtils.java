package net.transolyfer.transolyfergo.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 21/3/17.
 *
 * Clase utilitaria del manejo de tamanios de elementos y eventos en la pantalla
 */
public class ScreenUtils {

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static Point getDeviceDimention(Activity context)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;

        float density = metrics.density;
        int densityDpi = metrics.densityDpi;

        Log.v("ScreenUtils", "widthPixels " + widthPixels + " heightPixels " + heightPixels + " density " + density + " densityDpi " + densityDpi);
        Point point = new Point(widthPixels,heightPixels);

        return point;
    }

    public  static Point getMidPoint(Point p1, Point p2)
    {
        return new Point(((p1.x+p2.x)/2),((p1.y+p2.y)/2));
    }

    public static float getCurrentDIP(Context context)
    {
        float d = context.getResources().getDisplayMetrics().density;
        return d;
    }

    public static DisplayInfo getCurrentInfo(Context context)
    {
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        return new DisplayInfo(getDip(getCurrentDIP(context),d.widthPixels),
                getDip(getCurrentDIP(context),d.heightPixels));
    }


    public static  int getDip(float scale, int pixel)
    {
        return (int) (pixel * scale + 0.5f);
    }

    public  static  class DisplayInfo implements Serializable
    {
        private int width;
        private int height;


        public DisplayInfo(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    public static void hideKeyboard(Context context, View view){
        ((InputMethodManager) context.getSystemService(Context
                .INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static List<Integer> imageSize(Context context, int typeLoad){
        int density= context.getResources().getDisplayMetrics().densityDpi;
        int width = 0;
        int height = 0;
        ArrayList<Integer> imageSize = new ArrayList<>();
        switch(typeLoad){
            case 1:
                if(density < DisplayMetrics.DENSITY_HIGH){
                    width = 348;
                    height = 182;
                } else if(density >= DisplayMetrics.DENSITY_HIGH && density < DisplayMetrics.DENSITY_XHIGH){
                    width = 522;
                    height = 273;
                } else if(density >= DisplayMetrics.DENSITY_XHIGH && density < DisplayMetrics.DENSITY_XXHIGH){
                    width = 696;
                    height = 364;
                } else if(density >= DisplayMetrics.DENSITY_XXHIGH && density < DisplayMetrics.DENSITY_XXXHIGH){
                    width = 1044;
                    height = 546;
                } else if(density >= DisplayMetrics.DENSITY_XXXHIGH){
                    width = 1392;
                    height = 728;
                }
                break;
            case 2:
                if(density < DisplayMetrics.DENSITY_HIGH){
                    width = 201;
                    height = 252;
                } else if(density >= DisplayMetrics.DENSITY_HIGH && density < DisplayMetrics.DENSITY_XHIGH){
                    width = 252;
                    height = 328;
                } else if(density >= DisplayMetrics.DENSITY_XHIGH && density < DisplayMetrics.DENSITY_XXHIGH){
                    width = 302;
                    height = 404;
                } else if(density >= DisplayMetrics.DENSITY_XXHIGH && density < DisplayMetrics.DENSITY_XXXHIGH){
                    width = 403;
                    height = 556;
                } else if(density >= DisplayMetrics.DENSITY_XXXHIGH){
                    width = 504;
                    height = 708;
                }
                break;
            case 3:
                if(density < DisplayMetrics.DENSITY_HIGH){
                    width = 124;
                    height = 124;
                } else if(density >= DisplayMetrics.DENSITY_HIGH && density < DisplayMetrics.DENSITY_XHIGH){
                    width = 186;
                    height = 186;
                } else if(density >= DisplayMetrics.DENSITY_XHIGH && density < DisplayMetrics.DENSITY_XXHIGH){
                    width = 248;
                    height = 248;
                } else if(density >= DisplayMetrics.DENSITY_XXHIGH && density < DisplayMetrics.DENSITY_XXXHIGH){
                    width = 372;
                    height = 372;
                } else if(density >= DisplayMetrics.DENSITY_XXXHIGH){
                    width = 496;
                    height = 496;
                }
                break;
        }
        imageSize.add(height);
        return imageSize;
    }

    public static void changeEditextColorLine(EditText editText, int color){
        editText.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}
