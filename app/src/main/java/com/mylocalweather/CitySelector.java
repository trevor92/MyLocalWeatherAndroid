package com.mylocalweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Trevor on 2/21/2016.
 * Class for US City Data
 */
public class CitySelector {

    static ArrayList<String> cityName = new ArrayList<>();
    static ArrayList<String> cityState = new ArrayList<>();
    static ArrayList<String> state = new ArrayList<>();
    static ArrayList<String> cityID = new ArrayList<>();
    static ArrayList<String> latitude = new ArrayList<>();
    static ArrayList<String> longitude = new ArrayList<>();
    int index = 0;
    Context wContext;
    SharedPreferences sharedPreferences;

    public CitySelector (Context context){
        wContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(wContext);
        index = sharedPreferences.getInt("Current Index", 0);
    }
    public void loadJSONFromAsset() {

        String json = null;
        try {
            InputStream is = wContext.getAssets().open("city.list.us_1.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        try {

            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray("list");
            JSONObject coords;
            JSONObject listObj;
            String combo;
            String id;
            String listState;
            String listName;

                for (int i = 0; i < arr.length(); i++) {
                    listObj = arr.getJSONObject(i);
                    listState = listObj.getString("state");
                    listName = listObj.getString("name");
                    combo = listObj.getString("name")+", "+listObj.getString("state");
                    id = listObj.getString("_id");
                    coords = listObj.getJSONObject("coord");
                    String lon = coords.getString("lon");
                    String lat = coords.getString("lat");


                    state.add(listState);
                    cityState.add(combo);
                    cityID.add(id);
                    cityName.add(listName);
                    longitude.add(lon);
                    latitude.add(lat);

                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLat(String id){
        Integer latIndex = cityID.indexOf(id);
        return latitude.get(latIndex);
    }

    public String getLong(String id){
        Integer lonIndex = cityID.indexOf(id);
        return longitude.get(lonIndex);
    }
}
