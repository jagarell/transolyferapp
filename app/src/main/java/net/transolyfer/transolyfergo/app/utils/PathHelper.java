package net.transolyfer.transolyfergo.app.utils;

import android.net.Uri;

/**
 * Created by garymontengro on 22/03/17.
 */

public class PathHelper {

    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

    public static String processURL(String path) {
        if(path==null)return "";
        String urlEncoded = Uri.encode(path, ALLOWED_URI_CHARS);
        return urlEncoded;
    }

    public static String validateHttp(String url){
        if(!url.contains("http")){
            url = "http://" + url;
        }
        return url;
    }
}
