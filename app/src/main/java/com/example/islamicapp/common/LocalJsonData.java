package com.example.islamicapp.common;

import android.content.Context;
import android.util.Log;

import com.example.islamicapp.model.Azkar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LocalJsonData {
    private static final String TAG = "localJsonData";

    public static String loadJSONFromAsset(String fileName, Context context) {
        String json = null;
        try {
            InputStream is;
            int size;
            if (context.getAssets().open(fileName) != null) {

                is = context.getAssets().open(fileName);
                size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, StandardCharsets.UTF_8);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<Azkar> getDataFromJson(String fileName, Context context) {
        List<Azkar> list = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            if (loadJSONFromAsset(fileName, context) != null) {
                jsonObject = new JSONObject(loadJSONFromAsset(fileName, context));
                jsonArray = jsonObject.getJSONArray("content");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userData = jsonArray.getJSONObject(i);
                    list.add(new Azkar(userData.get("start").toString(), userData.get("zekr").toString(),
                            Integer.parseInt(userData.get("repeat").toString()), userData.get("bless").toString()));
                }
            }else {
                Log.d(TAG, "getDataFromJson: "+ "problem in file");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
