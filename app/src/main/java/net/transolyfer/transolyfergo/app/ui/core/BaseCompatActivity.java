package net.transolyfer.transolyfergo.app.ui.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import net.transolyfer.transolyfergo.app.utils.ScreenUtils;

/**
 * Created by sergio on 26/6/16.
 *
 * Clase que se utiliza para manejar todos los eventos de la actividad
 */
public class BaseCompatActivity extends AppCompatActivity {


    private static final String TAG = "BaseCompatActivity";

    public void initStatusBarTranslucent(View vKitkat) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        validateVersion(vKitkat);
    }

    private void validateVersion(View vKitkat){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            vKitkat.setVisibility(View.VISIBLE);
            vKitkat.setPadding(0, 0, 0, ScreenUtils.dpToPx(24));
        }else {
            vKitkat.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // Set paddingTop of toolbar to height of status bar.
            // Fixes statusbar covers toolbar issue
            vKitkat.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    protected void nextActivity(Class<?> activity, boolean notDestroy) {
        this.startActivity(new Intent(this, activity));
        if(!notDestroy) {
            this.finish();
        }
    }

    protected void nextData(Class<?> activity, Bundle bundle, boolean notDestroy) {
        Intent intent = new Intent(this, activity);
        if(bundle!=null){    intent.putExtras(bundle);}
        this.startActivity(intent);
        if(!notDestroy) {
            this.finish();
        }
    }

    public int getHeightScreen(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int height = display.getHeight();
        return  height;
    }

    public int getWidthScreen(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        return  width;
    }

    protected void nextDestroyAllActivities(Bundle bundle, Intent intent, boolean destroy ) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(bundle!=null){intent.putExtras(bundle);}
        startActivity(intent);
        if(destroy)finish();
    }

}
