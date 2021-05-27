package net.transolyfer.transolyfergo.app.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;

import net.transolyfer.transolyfergo.R;

public class LocationHelper {

    public static final int REQUEST_CODE_LOCATION = 54312;

    public static final int MIN_TIME = 1000;
    public static final int MIN_DISTANCE = 0;

    public static String getProviderName(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM); // Chose your desired power consumption level.
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setSpeedRequired(false);

        return locationManager.getBestProvider(criteria, true);
    }
    public static void showAlertActivateLocation(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setMessage(context.getResources().getString(R.string.location_message_text))
                .setCancelable(false)
                .setPositiveButton(context.getResources().getString(R.string.location_option_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.location_option_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
