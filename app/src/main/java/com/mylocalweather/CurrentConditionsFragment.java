package com.mylocalweather;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Trevor on 11/12/2015.
 * Current Conditions Fragment class
 */
public class
CurrentConditionsFragment extends Fragment{

    TextView currentConditions;
    TextView weatherDesc;
    TextView cityLabel;
    TextView windLabel;
    TextView windValue;
    TextView humidityLabel;
    TextView humidityValue;
    TextView pressureLabel;
    TextView pressureValue;
    TextView icon;
    Integer iconID;
    TextView sunriseLabel;
    TextView sunriseValue;
    TextView sunsetLabel;
    TextView sunsetValue;
    Typeface weather_font;
    String id;
    String city;
    Button hourButton;
    Button mapButton;
    WeatherTask weatherTask;
    WeatherTask.WeatherUpdates listener;
    WeatherMapFragment.Radar radarListener;
    Bundle savedData;
    Network network;
    WeatherUtils wu;
    CitySelector cs;
    long startTime;
    long timeNow;
    long baseTime;

    SharedPreferences sharedPreferences;

    static final String CITY_NAME = "City";
    static final String WEA_DESC = "Weather Desc";
    static final String ICON = "Icon";
    static final String CUR_CONDS = "Current Conditions";
    static final String PRESSURE = "Pressure";
    static final String WIND_VAL = "Wind";
    static final String HUM_VAL = "Humidity";
    static final String SUNRISE_VAL = "Sunrise";
    static final String SUNSET_VAL = "Sunset";
    static final String CITY_ID = "City ID";

    long hourBaseTime;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LocalWeatherMain) {
            listener = (LocalWeatherMain) activity;
            radarListener = (LocalWeatherMain) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement LocalWeatherMain");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        wu = new WeatherUtils(this.getActivity());
        cs = new CitySelector(this.getActivity());
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.current_weather_result, container, false);
        currentConditions = (TextView) view.findViewById(R.id.current_weather);
        cityLabel = (TextView) view.findViewById(R.id.city_label);
        weatherDesc = (TextView) view.findViewById(R.id.weather_description);
        humidityLabel = (TextView) view.findViewById(R.id.humidity_label);
        humidityValue = (TextView) view.findViewById(R.id.humidity_value);
        windLabel = (TextView) view.findViewById(R.id.wind_label);
        windValue = (TextView) view.findViewById(R.id.wind_value);
        pressureLabel = (TextView) view.findViewById(R.id.pressure_label);
        pressureValue = (TextView) view.findViewById(R.id.pressure_value);
        sunriseLabel = (TextView) view.findViewById(R.id.sunrise_label);
        sunriseValue = (TextView) view.findViewById(R.id.sunrise_value);
        sunsetLabel = (TextView) view.findViewById(R.id.sunset_label);
        sunsetValue = (TextView) view.findViewById(R.id.sunset_value);
        icon = (TextView) view.findViewById(R.id.weather_icon);
        weather_font = Typeface.createFromAsset(getActivity().getBaseContext().getAssets(),"weathericons.ttf");
        icon.setTypeface(weather_font);


        hourButton = (Button) view.findViewById(R.id.hour_button);
        hourButton.setOnClickListener(v -> {
            network = new Network();
            boolean isConnected = network.checkNetworkConnection(getActivity().getBaseContext());
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();

            hourBaseTime = sharedPreferences.getLong("Hour Base Time", System.currentTimeMillis());
            System.out.println(hourBaseTime);
            String savedID = sharedPreferences.getString("City ID Hour", "");

            if (id.equals(savedID)) {

                if (wu.hasTenMinsElapsed(hourBaseTime) == false){

                        System.out.println("City ID in Hour is the same");
                        HourlyFragment hourFragment = new HourlyFragment();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, hourFragment);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        getFragmentManager().executePendingTransactions();
                        hourFragment.getSavedData();
                        editor.putString("City ID Hour", id);
                        editor.putLong("Hour Base Time", System.currentTimeMillis());
                }
                else{
                    if(isConnected == true) {

                        weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());;
                        weatherTask.execute("Hour", id, "City ID");
                        editor.putString("City ID Hour", id);
                        editor.putLong("Hour Base Time", System.currentTimeMillis());
                        editor.apply();
                    }
                    else{
                        network.showNetworkError(getActivity().getBaseContext());
                    }
                }
            }
            else{
                if(isConnected == true) {

                    weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                    weatherTask.execute("Hour", id, "City ID");
                    editor.putString("City ID Hour", id);
                    editor.putLong("Hour Base Time", System.currentTimeMillis());
                    editor.apply();

                }
                else{
                    network.showNetworkError(getActivity().getBaseContext());
                }
            }
        });

        mapButton = (Button) view.findViewById(R.id.map_button);
        mapButton.setOnClickListener(v -> {

            network = new Network();
            boolean isConnected = network.checkNetworkConnection(getActivity().getBaseContext());

            if(isConnected){

                String searchString = wu.convertSpacesToUnderscores(city);
                String lon = cs.getLong(id);
                String lat = cs.getLat(id);
                radarListener.findMaps(searchString, lon, lat);
            }
            else{
                network.showNetworkError(getActivity().getBaseContext());
            }

        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            String cityName = savedInstanceState.getString(CITY_NAME);
            String weaDesc = savedData.getString(WEA_DESC);
            String pic = savedData.getString(ICON);
            String curConds = savedData.getString(CUR_CONDS);
            String pressure = savedData.getString(PRESSURE);
            String windVal = savedData.getString(WIND_VAL);
            String humVal = savedData.getString(HUM_VAL);
            String sunrise = savedData.getString(SUNRISE_VAL);
            String sunset = savedData.getString(SUNSET_VAL);

            cityLabel.setText(cityName);
            weatherDesc.setText(weaDesc);
            icon.setText(pic);
            currentConditions.setText(curConds);
            pressureValue.setText(pressure);
            windValue.setText(windVal);
            humidityValue.setText(humVal);
            sunriseValue.setText(sunrise);
            sunsetValue.setText(sunset);

        }

    }
    public void saveData(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(CITY_NAME, cityLabel.getText().toString());
        editor.putString(WEA_DESC, weatherDesc.getText().toString());
        editor.putString(ICON, icon.getText().toString());
        editor.putString(CUR_CONDS, currentConditions.getText().toString());
        editor.putString(PRESSURE, pressureValue.getText().toString());
        editor.putString(WIND_VAL, windValue.getText().toString());
        editor.putString(HUM_VAL, humidityValue.getText().toString());
        editor.putString(SUNRISE_VAL, sunriseValue.getText().toString());
        editor.putString(SUNSET_VAL, sunsetValue.getText().toString());

        editor.apply();

    }

    public void getSavedData(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        cityLabel.setText(sharedPreferences.getString(CITY_NAME, ""));
        weatherDesc.setText(sharedPreferences.getString(WEA_DESC, ""));
        icon.setText(sharedPreferences.getString(ICON, ""));
        currentConditions.setText(sharedPreferences.getString(CUR_CONDS, ""));
        pressureValue.setText(sharedPreferences.getString(PRESSURE, ""));
        windValue.setText(sharedPreferences.getString(WIND_VAL, ""));
        humidityValue.setText(sharedPreferences.getString(HUM_VAL, ""));
        sunriseValue.setText(sharedPreferences.getString(SUNRISE_VAL, ""));
        sunsetValue.setText(sharedPreferences.getString(SUNSET_VAL, ""));
    }


    @Override
    public void onPause() {
        saveData();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CITY_ID, id);
        super.onPause();
    }

    @Override
    public void onResume(){
        getSavedData();
        super.onResume();
    }


    public void setCurrentConditions(JSONObject jsonObject) {
        try{
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                city = sharedPreferences.getString("Search Term", "");
                Double main = jsonObject.getJSONObject("main").getDouble("temp");
                Integer mainTemp = (int) Math.round(main);
                id = String.valueOf(jsonObject.getInt("id"));
                String windSpeed = jsonObject.getJSONObject("wind").getString("speed") + " MPH";
                Integer windDir = jsonObject.getJSONObject("wind").getInt("deg");
                Integer humidity = jsonObject.getJSONObject("main").getInt("humidity");
                String humidityVal = humidity.toString();
                Integer pressure = jsonObject.getJSONObject("main").getInt("pressure");
                String pressureVal = pressure.toString()+ " in";
                String jsonMain = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                iconID = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
                Long sunrise = jsonObject.getJSONObject("sys").getLong("sunrise") * 1000;
                String sunriseVal = wu.dateFormat(sunrise);
                sunriseValue.setText(sunriseVal);
                Long sunset = jsonObject.getJSONObject("sys").getLong("sunset") * 1000;
                String sunsetVal = wu.dateFormat(sunset);
                sunsetValue.setText(sunsetVal);
                currentConditions.setText(mainTemp.toString() + (char) 0x00B0 + "F");
                cityLabel.setText(city);
                weatherDesc.setText(jsonMain);
                humidityValue.setText(humidityVal + (char) 0x0025);
                pressureValue.setText(pressureVal);
                String windData = wu.setWindDirection(windDir,windSpeed);
                windValue.setText(windData);
                String iconMapper = wu.setIcon(iconID, sunrise, sunset);
                icon.setText(iconMapper);
                saveData();

        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

