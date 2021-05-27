package net.transolyfer.transolyfergo.app.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by sergio on 21/3/17.
 *
 * Clase utilitaria json
 */
public class JSONUtils {

    public static JSONObject generateJSONObject(Object obj) {
        Gson gson = new Gson();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(obj));//JsonObject(gson.toJson(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONArray generateJSONArray(Object obj) {
        Gson gson = new Gson();
        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(gson.toJson(obj));//JsonObject(gson.toJson(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Object jsonStringToObject(String jsonString, Class<?> objectClass) {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        Object nObj = gson.fromJson(jsonString, objectClass);

        return nObj;
    }

    public static Object jsonStringToArray(String jsonString, Type type) {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        Object nObj = gson.fromJson(jsonString, type);

        return nObj;
    }
}
