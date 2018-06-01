package com.mylocalweather;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Window;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by Trevor on 11/22/2015.
 * Class that runs asynctasks to get weather data
 */
public class WeatherTask extends AsyncTask<String, Void, JSONObject> {
    private Context wContext;
    private WeatherUpdates listener;
    private String weatherFormat;
    private ProgressDialog dialog;
    private LocalWeatherMain activity;

    public WeatherTask(Context context, LocalWeatherMain listener, LocalWeatherMain activity) {
        wContext = context;
        this.listener = listener;
        dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setMessage("Loading Data!");
        dialog.setCancelable(false);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute(){
        dialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        weatherFormat = params[0];

        JSONObject jsonObject;

        if (weatherFormat.equals("Current Weather")) {
            FetchWeather fetchCurrentWeather = new FetchWeather();
            jsonObject = fetchCurrentWeather.getCurrentWeatherJSON(wContext, params[1], params[2]);

            return jsonObject;
        }
        if(weatherFormat.equals("Home Current Weather")){
            FetchWeather fetchCurrentWeather = new FetchWeather();
            jsonObject = fetchCurrentWeather.getCurrentWeatherJSON(wContext, params[1], params[2]);

            return jsonObject;
        }
        if (weatherFormat.equals("Forecast")) {
            FetchWeather fetchWeather = new FetchWeather();
            jsonObject = fetchWeather.getForecast(wContext, params[1], params[2]);
            return jsonObject;
        }
        if (weatherFormat.equals("Hour")) {

            FetchWeather fetchHour = new FetchWeather();
            jsonObject = fetchHour.getHourForecast(wContext, params[1], params[2]);
            return jsonObject;
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        if(jsonObject == null){
            dialog.dismiss();
            cancel(true);
            Toast.makeText(wContext ,"Error getting data, please try again", Toast.LENGTH_LONG).show();
//            if (weatherFormat.equals("Current Weather")) {
//
//            }
        }
        else{

            if (weatherFormat.equals("Current Weather")) {
                dialog.dismiss();
                listener.findCurrentWeather(jsonObject);
            }
            if(weatherFormat.equals("Home Current Weather")){
                dialog.dismiss();
                listener.findHomeCurrentWeather(jsonObject);
            }
            if (weatherFormat.equals("Forecast")) {
                dialog.dismiss();
                listener.findForecast(jsonObject);
            }
            if (weatherFormat.equals("Hour")) {
                dialog.dismiss();
                listener.findHourForecast(jsonObject);
            }
        }
    }

    public interface WeatherUpdates{
        void findCurrentWeather(JSONObject jsonObject);
        void findForecast(JSONObject jsonObject);
        void findHourForecast(JSONObject jsonObject);
        void findHomeCurrentWeather(JSONObject jsonObject);

    }

}

