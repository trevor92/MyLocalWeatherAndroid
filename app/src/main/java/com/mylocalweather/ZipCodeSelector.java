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
 * Created by Trevor on 8/20/2016.
 */
public class ZipCodeSelector {
    static ArrayList<String> zipCode = new ArrayList<>();
    int index = 0;
    Context wContext;
    SharedPreferences sharedPreferences;


    public ZipCodeSelector (Context context){
        wContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(wContext);
        //index = sharedPreferences.getInt("Current Index", 0);
    }
    public void loadJSONFromAsset() {

        String json = null;
        try {
            InputStream is = wContext.getAssets().open("zips.json");
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
            JSONArray arr = obj.getJSONArray("zipcodes");
            JSONObject listObj;
            String zip;

            for (int i = 0; i < arr.length(); i++) {
                listObj = arr.getJSONObject(i);
                zip = listObj.getString("zipcode");
                zipCode.add(zip);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String checkZip(String id){

        Integer stateIndex = zipCode.indexOf(id);
        System.out.println(stateIndex);
        String validZip;

        if(stateIndex == -1){
            validZip = "Invalid";
        }
        else{
            validZip = "Zip Code";
        }

        return validZip;

    }

}
