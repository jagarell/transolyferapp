package net.transolyfer.transolyfergo.app.utils;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by garymontengro on 22/03/17.
 */

public class FragmentUtils {

    public static void changeFragment(AppCompatActivity actionBarActivity, Fragment fragment, Bundle bundle, int
            contentLayout){
        FragmentTransaction ft = actionBarActivity.getSupportFragmentManager().beginTransaction();
        if(bundle!=null)fragment.setArguments(bundle);
        ft.replace(contentLayout, fragment);
        ft.commitAllowingStateLoss();
    }
}
