package com.mylocalweather;



import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Trevor on 10/5/2015.
 * This class fetches various weather data
 */
public class FetchWeather{

    private static final String OWM_KEY = BuildConfig.OWM_API_KEY;
    private static final String OWM_URL_CW_ZC_BEG = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String OWM_URL_CW_CI_BEG = "http://api.openweathermap.org/data/2.5/weather?id=";
    private static final String OWM_URL_CW_END = "&units=imperial";
    private static final String OPEN_WEATHER_MAP_URL_ID_FOR_BEG = "http://api.openweathermap.org/data/2.5/forecast/daily?id=";
    private static final String OPEN_WEATHER_MAP_URL_ZC_FOR_BEG = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static final String OPEN_WEATHER_MAP_URL_FOR_END = "&mode=json&units=imperial&cnt=5";
    private static final String OPEN_WEATHER_MAP_URL_ID_HOUR_BEG = "http://api.openweathermap.org/data/2.5/forecast?id=";
    private static final String OPEN_WEATHER_MAP_URL_ZC_HOUR_BEG = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String OPEN_WEATHER_MAP_URL_HOUR_END = "&mode=json&units=imperial&cnt=8";
    

    public JSONObject getCurrentWeatherJSON(Context context, String searchBy, String searchType) {
        JSONObject data = null;
        String urlString = "";

        if(searchType == "City ID"){

            urlString = OWM_URL_CW_CI_BEG+searchBy+OWM_URL_CW_END+"&appid="+OWM_KEY;
        }
        if(searchType == "Zip Code"){
            urlString = OWM_URL_CW_ZC_BEG+searchBy+",us"+OWM_URL_CW_END+"&appid="+OWM_KEY;
        }

        try {

            URL url = new URL(urlString);
            System.out.println(url);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());

            // If value is not 200 request was not successful
            if (data.getInt("cod") != 200) {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return data;
    }
    public JSONObject getForecast(Context context, String searchBy, String searchType){

        JSONObject data = null;
        String urlString = "";

        try {
            if(searchType == "City ID"){
                urlString = OPEN_WEATHER_MAP_URL_ID_FOR_BEG+searchBy+OPEN_WEATHER_MAP_URL_FOR_END+"&appid="+OWM_KEY;
            }
            if(searchType == "Zip Code"){
                urlString = OPEN_WEATHER_MAP_URL_ZC_FOR_BEG+searchBy+",us"+OPEN_WEATHER_MAP_URL_FOR_END+"&appid="+OWM_KEY;
            }

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        StringBuffer json = new StringBuffer(1024);
        String tmp;
        while ((tmp = reader.readLine()) != null)
            json.append(tmp).append("\n");
        reader.close();

        data = new JSONObject(json.toString());

        // If value is not 200 request was not successful
        if (data.getInt("cod") != 200) {
            return null;
        }

    } catch (Exception e) {
            e.printStackTrace();
    }
    return data;
    }


    public JSONObject getHourForecast(Context context, String searchBy, String searchType){

        JSONObject data = null;
        String urlString = "";

        try {
            if(searchType == "City ID"){
                urlString = OPEN_WEATHER_MAP_URL_ID_HOUR_BEG+searchBy+OPEN_WEATHER_MAP_URL_HOUR_END+"&appid="+OWM_KEY;
            }
            if(searchType == "Zip Code"){
                urlString = OPEN_WEATHER_MAP_URL_ZC_HOUR_BEG+searchBy+",us"+OPEN_WEATHER_MAP_URL_HOUR_END+"&appid="+OWM_KEY;
            }

            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());

            // If value is not 200 request was not successful
            if (data.getInt("cod") != 200) {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

}

