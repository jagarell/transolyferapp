package net.transolyfer.transolyfergo.repository.datasource.preferences;


import android.net.Uri;

import net.transolyfer.transolyfergo.repository.RepositoryCallback;

/**
 * Created by Admin on 21/03/2017.
 */

public interface ConfigurationsPreferencesDataStore {

    void getCanReceiveNotifications(RepositoryCallback repositoryCallback);
    void saveCanReceiveNotification(boolean haveNotification, RepositoryCallback repositoryCallback);
    void saveUriNotificationSound(Uri uriNotificationSound);
    void getCanVibrate(RepositoryCallback repositoryCallback);
    void saveCanVibrate(boolean canVibrate);
}
