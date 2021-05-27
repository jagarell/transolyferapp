package net.transolyfer.transolyfergo.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import net.transolyfer.transolyfergo.R;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;

/**
 * Created by garymontengro on 12/06/17.
 */

public class NetworkUtils {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private static CloudBlobContainer getContainer(Context context,String containerName){
        CloudStorageAccount storageAccount = null;
        CloudBlobContainer container = null;
        try {
            storageAccount = CloudStorageAccount
                    .parse(context.getString(R.string.storageConnectionString));
            CloudBlobClient cloudBlobClient = storageAccount.createCloudBlobClient();

            // Create the blob client.
            container = cloudBlobClient.getContainerReference(containerName);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (StorageException e) {
            e.printStackTrace();
        }

        return container;
    }

    public static String uploadImage(Context context, String containerName, String imageName, byte[] image) throws Exception {
        CloudBlobContainer container = getContainer(context,containerName);
        String newImageName = imageName.replace(":","").replace(" ","_") + ".jpg";
        CloudBlockBlob blobImage = container.getBlockBlobReference(newImageName);

        blobImage.uploadFromByteArray(image, 0, image.length);
        return newImageName;
    }
}
