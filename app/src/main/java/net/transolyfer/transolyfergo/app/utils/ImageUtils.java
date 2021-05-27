package net.transolyfer.transolyfergo.app.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import net.transolyfer.transolyfergo.BuildConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by garymontengro on 22/03/17.
 */

public class ImageUtils {

    public static File getImageFile(Context context, byte[] pdfAsBytesbyte){
        File f = new File(context.getCacheDir(), "");
        try {
            File filePath = new File(Environment.getExternalStorageDirectory()+"/image1.png");
            FileOutputStream os = new FileOutputStream(filePath, true);
            os.write(pdfAsBytesbyte);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    public static String bitmapToString(Bitmap in){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        in.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        return Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap stringToBitmap(String in){
        byte[] bytes = Base64.decode(in, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String convertBytesToString(byte[] bytes){
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static byte[] convertStringToBytes(String in){
        byte[] bytes = Base64.decode(in, Base64.DEFAULT);
        return bytes;
    }

    public static String convertUriToBase64(Uri imageUri, Context context){
        InputStream iStream = null;
        byte[] inputData = null;
        try {
            iStream = context.getContentResolver().openInputStream(imageUri);
            inputData = getBytes(iStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String base64Uri = convertBytesToString(inputData);
        return base64Uri;
    }

    public static byte[] convertUriToBytes(Uri imageUri, Context context){
        InputStream iStream = null;
        byte[] inputData = null;
        try {
            iStream = context.getContentResolver().openInputStream(imageUri);
            inputData = getBytes(iStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputData;
    }

    public static byte[] convertBytesToUri(String in){
        byte[] bytes = Base64.decode(in, Base64.DEFAULT);
        return bytes;
    }

    public static String convertBase64UritoString(String base64Uri){
        byte[] data = Base64.decode(base64Uri, Base64.DEFAULT);
        String text = null;
        try {
            text = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static byte[] convertBitmapToBytes(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static Uri getUrlFileFromBytes(Context context, byte[] imageBytes, String name){
        File imagesDirectory = new File(context.getFilesDir(), "photos");
        if(!imagesDirectory.exists()){
            imagesDirectory.mkdirs();
        }
        File file = new File(imagesDirectory, name);
        writeFile(file, imageBytes);
        Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", file);
        return uri;
    }

    private static void writeFile(File file, byte[] content) {
        FileOutputStream stream = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            stream = new FileOutputStream(file);
            stream.write(content);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            Log.e("provider", "IOException writing file: ", e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                Log.e("provider", "IOException closing stream: ", e);
            }
        }
    }

    public static void setBlurToView(Context context, View viewFromBlur, View viewToBlur){
        viewFromBlur.buildDrawingCache();
        Bitmap bmp = viewFromBlur.getDrawingCache();
        blur(context, bmp, viewToBlur);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static void blur(Context context, Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();

        float radius = 20;

        Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
                (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);

        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bkg, 0, 0, null);

        RenderScript rs = RenderScript.create(context);

        Allocation overlayAlloc = Allocation.createFromBitmap(
                rs, overlay);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
                rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);

        blur.setRadius(radius);

        blur.forEach(overlayAlloc);

        overlayAlloc.copyTo(overlay);

        view.setBackground(new BitmapDrawable(
                context.getResources(), overlay));

        rs.destroy();
    }

    public static void setTintAdapter(ImageView imageView, int color, Context context){
        imageView.setColorFilter(color);
    }

    public static void setTint(ImageView imageView, int color, Context context){
        imageView.setColorFilter(ContextCompat.getColor(context, color));
    }

    public static Bitmap blur(Context context, Bitmap image) {
        final float BITMAP_SCALE = 0.4f;
        final float BLUR_RADIUS = 7.5f;
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    public static Bitmap getBitmapFromUri(Uri uri, Context context){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
