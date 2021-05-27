package net.transolyfer.transolyfergo.repository.datasource.preferences;

import android.content.Context;
import android.net.Uri;

import net.transolyfer.transolyfergo.repository.RepositoryCallback;


/**
 * Created by Admin on 21/03/2017.
 */

public class ConfigurationsPreferencesDataStoreImpl implements ConfigurationsPreferencesDataStore {

    private PreferenceHelper preferencesHelper;
    private Context context;

    public ConfigurationsPreferencesDataStoreImpl(Context context) {
        this.preferencesHelper = new PreferenceHelper();
        this.context = context;
    }

    @Override
    public void getCanReceiveNotifications(RepositoryCallback repositoryCallback) {
    }

    @Override
    public void saveCanReceiveNotification(boolean haveNotification, RepositoryCallback repositoryCallback) {
    }

    @Override
    public void saveUriNotificationSound(Uri uriNotificationSound) {
    }

    @Override
    public void getCanVibrate(RepositoryCallback repositoryCallback) {
    }

    @Override
    public void saveCanVibrate(boolean canVibrate) {
    }
}
