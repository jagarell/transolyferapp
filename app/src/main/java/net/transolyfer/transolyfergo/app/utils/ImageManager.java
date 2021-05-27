package net.transolyfer.transolyfergo.app.utils;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.LinkedList;

public class ImageManager {
    public static final String storageConnectionString = "DefaultEndpointsProtocol=https;"
            + "AccountName=transolyferstorage;"
            + "AccountKey=xW4XtC+PW5yIzf7BBaEBdAxo8HsmayMY4aBxBI8C1EivZ+XAfe5DisZ8b0xP/Fdws/D8j7q6Kkeu6VbHjJayvQ==";

    private static CloudBlobContainer getContainer(String containerName) throws Exception {
        // Retrieve storage account from connection-string.

        CloudStorageAccount storageAccount = CloudStorageAccount
                .parse(storageConnectionString);

        // Create the blob client.
        CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

        // Get a reference to a container.
        // The container name must be lower case

        return blobClient.getContainerReference(containerName);
    }

    public static String UploadImage(InputStream image,String containerName, int imageLength,String imageName) throws Exception {
        CloudBlobContainer container = getContainer(containerName);

        container.createIfNotExists();

        CloudBlockBlob imageBlob = container.getBlockBlobReference(imageName);
        imageBlob.upload(image, imageLength);

        return imageName;

    }

    public static String[] ListImages(String containerName) throws Exception{
        CloudBlobContainer container = getContainer(containerName);

        Iterable<ListBlobItem> blobs = container.listBlobs();

        LinkedList<String> blobNames = new LinkedList<>();
        for(ListBlobItem blob: blobs) {
            blobNames.add(((CloudBlockBlob) blob).getName());
        }

        return blobNames.toArray(new String[blobNames.size()]);
    }

    public static void GetImage(String name,String containerName, OutputStream imageStream, long imageLength) throws Exception {
        CloudBlobContainer container = getContainer(containerName);

        CloudBlockBlob blob = container.getBlockBlobReference(name);

        if(blob.exists()){
            blob.downloadAttributes();
            imageLength = blob.getProperties().getLength();
            blob.download(imageStream);
        }
    }

    static final String validChars = "abcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( validChars.charAt( rnd.nextInt(validChars.length()) ) );
        return sb.toString();
    }
}
