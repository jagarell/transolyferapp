package net.transolyfer.transolyfergo.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 21/03/2017.
 */

public class PreferenceHelper {

    protected static final String TRANSOLYFER = "net.transolyfer.transolyfergo";

    protected static SharedPreferences mSettings;
    protected static SharedPreferences.Editor mEditor;

    private static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(TRANSOLYFER, Context.MODE_PRIVATE);
    }
}
