package com.mylocalweather;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Trevor on 1/11/2016.
 */
public class ForecastFragment extends Fragment {

    TextView forecastLabel;
    TextView day1_label;
    TextView day2_label;
    TextView day3_label;
    TextView day4_label;
    TextView day5_label;
    TextView day1_icon;
    TextView day2_icon;
    TextView day3_icon;
    TextView day4_icon;
    TextView day5_icon;
    TextView day1_temp;
    TextView day2_high_temp;
    TextView day2_low_temp;
    TextView day3_high_temp;
    TextView day3_low_temp;
    TextView day4_high_temp;
    TextView day4_low_temp;
    TextView day5_high_temp;
    TextView day5_low_temp;
    Typeface weather_font;
    WeatherUtils wu;
    WeatherTask.WeatherUpdates listener;
    String dayNight;
    SharedPreferences sharedPreferences;

    static final String DAY1_LABEL = "Day 1 Label";
    static final String DAY2_LABEL = "Day 2 Label";
    static final String DAY3_LABEL = "Day 3 Label";
    static final String DAY4_LABEL = "Day 4 Label";
    static final String DAY5_LABEL = "Day 5 Label";
    static final String DAY1_ICON = "Day 1 Icon";
    static final String DAY2_ICON = "Day 2 Icon";
    static final String DAY3_ICON = "Day 3 Icon";
    static final String DAY4_ICON = "Day 4 Icon";
    static final String DAY5_ICON = "Day 5 Icon";
    static final String DAY1_TEMP = "Day 1 Temp";
    static final String DAY2_LOW_TEMP = "Day 2 Low Temp";
    static final String DAY2_HIGH_TEMP = "Day 2 High Temp";
    static final String DAY3_LOW_TEMP = "Day 3 Low Temp";
    static final String DAY3_HIGH_TEMP = "Day 3 High Temp";
    static final String DAY4_LOW_TEMP = "Day 4 Low Temp";
    static final String DAY4_HIGH_TEMP = "Day 4 High Temp";
    static final String DAY5_LOW_TEMP = "Day 5 Low Temp";
    static final String DAY5_HIGH_TEMP = "Day 5 High Temp";


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LocalWeatherMain) {
            listener = (LocalWeatherMain) activity;
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
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.forecast_fragment, container, false);
        weather_font = Typeface.createFromAsset(getActivity().getBaseContext().getAssets(),"weathericons.ttf");
        forecastLabel = (TextView) view.findViewById(R.id.forecast_label);
        day1_label = (TextView) view.findViewById(R.id.day1_label);
        day2_label = (TextView) view.findViewById(R.id.day2_label);
        day3_label = (TextView) view.findViewById(R.id.day3_label);
        day4_label = (TextView) view.findViewById(R.id.day4_label);
        day5_label = (TextView) view.findViewById(R.id.day5_label);
        day1_icon = (TextView) view.findViewById(R.id.day1_icon);
        day1_icon.setTypeface(weather_font);
        day2_icon = (TextView) view.findViewById(R.id.day2_icon);
        day2_icon.setTypeface(weather_font);
        day3_icon = (TextView) view.findViewById(R.id.day3_icon);
        day3_icon.setTypeface(weather_font);
        day4_icon = (TextView) view.findViewById(R.id.day4_icon);
        day4_icon.setTypeface(weather_font);
        day5_icon = (TextView) view.findViewById(R.id.day5_icon);
        day5_icon.setTypeface(weather_font);
        day1_temp = (TextView) view.findViewById(R.id.day1_current_temp);
        day2_high_temp = (TextView) view.findViewById(R.id.day2_high_temp);
        day3_high_temp = (TextView) view.findViewById(R.id.day3_high_temp);
        day4_high_temp = (TextView) view.findViewById(R.id.day4_high_temp);
        day5_high_temp = (TextView) view.findViewById(R.id.day5_high_temp);
        day2_low_temp = (TextView) view.findViewById(R.id.day2_low_temp);
        day3_low_temp = (TextView) view.findViewById(R.id.day3_low_temp);
        day4_low_temp = (TextView) view.findViewById(R.id.day4_low_temp);
        day5_low_temp = (TextView) view.findViewById(R.id.day5_low_temp);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setForecast(JSONObject jsonObject) {

       try{
                JSONArray forecast = jsonObject.getJSONArray("list");
                JSONObject day1 = forecast.getJSONObject(0);
                JSONObject day2 = forecast.getJSONObject(1);
                JSONObject day3 = forecast.getJSONObject(2);
                JSONObject day4 = forecast.getJSONObject(3);
                JSONObject day5 = forecast.getJSONObject(4);

                //Day 1 Items
                String iconCode1 = day1.getJSONArray("weather").getJSONObject(0).getString("icon");
                dayNight = wu.getDayOrNight(iconCode1);

                Integer iconID = day1.getJSONArray("weather").getJSONObject(0).getInt("id");
                String iconMapping = wu.setIcon(iconID, 0 ,0);
                day1_icon.setText(iconMapping);

                if(dayNight.equals("d")){
                    day1_label.setText(R.string.today);
                    Double tempD = day1.getJSONObject("temp").getDouble("day");
                    Integer highTemp = (int) Math.round(tempD);
                    day1_temp.setText(highTemp.toString());

                }
                else{
                    day1_label.setText(R.string.tonight);
                    Double tempN = day1.getJSONObject("temp").getDouble("night");
                    Integer lowTemp = (int) Math.round(tempN);
                    day1_temp.setText(lowTemp.toString());

                }
                dayNight = "d";

                //Day 2 Items
                String date2 = wu.forecastDateFormat(day2.getLong("dt"));
                day2_label.setText(date2);
                Integer iconID2 = day2.getJSONArray("weather").getJSONObject(0).getInt("id");
                String iconMapping2 = wu.setIcon(iconID2, 0 , 9999999999999L);
                day2_icon.setText(iconMapping2);
                Double tempD2 = day2.getJSONObject("temp").getDouble("day");
                Integer highTempD2 = (int) Math.round(tempD2);
                day2_high_temp.setText(highTempD2.toString());
                Double tempN2 = day2.getJSONObject("temp").getDouble("night");
                Integer lowTemp2 = (int) Math.round(tempN2);
                day2_low_temp.setText(lowTemp2.toString());

                //Day 3 Items
                String date3 = wu.forecastDateFormat(day3.getLong("dt"));
                day3_label.setText(date3);
                Integer iconID3 = day3.getJSONArray("weather").getJSONObject(0).getInt("id");
                String iconMapping3 = wu.setIcon(iconID3, 0 ,9999999999999L);
                day3_icon.setText(iconMapping3);
                Double tempD3 = day3.getJSONObject("temp").getDouble("day");
                Integer highTempD3 = (int) Math.round(tempD3);
                day3_high_temp.setText(highTempD3.toString());
                Double tempN3 = day3.getJSONObject("temp").getDouble("night");
                Integer lowTemp3 = (int) Math.round(tempN3);
                day3_low_temp.setText(lowTemp3.toString());

                //Day 4 Items
                String date4 = wu.forecastDateFormat(day4.getLong("dt"));
                day4_label.setText(date4);
                Integer iconID4 = day4.getJSONArray("weather").getJSONObject(0).getInt("id");
                String iconMapping4 = wu.setIcon(iconID4, 0 ,9999999999999L);
                day4_icon.setText(iconMapping4);
                Double temp4 = day4.getJSONObject("temp").getDouble("day");
                Integer highTemp4 = (int) Math.round(temp4);
                day4_high_temp.setText(highTemp4.toString());
                Double tempN4 = day4.getJSONObject("temp").getDouble("night");
                Integer lowTemp4 = (int) Math.round(tempN4);
                day4_low_temp.setText(lowTemp4.toString());

                //Day 5 Items
                String date5 = wu.forecastDateFormat(day5.getLong("dt"));
                day5_label.setText(date5);
                Integer iconID5 = day5.getJSONArray("weather").getJSONObject(0).getInt("id");
                String iconMapping5 = wu.setIcon(iconID5, 0 ,9999999999999L);
                day5_icon.setText(iconMapping5);
                Double temp5 = day5.getJSONObject("temp").getDouble("day");
                Integer highTemp5 = (int) Math.round(temp5);
                day5_high_temp.setText(highTemp5.toString());
                Double tempN5 = day5.getJSONObject("temp").getDouble("night");
                Integer lowTemp5 = (int) Math.round(tempN5);
                day5_low_temp.setText(lowTemp5.toString());

       }
       catch (JSONException e){
           e.printStackTrace();
      }
    }

    public void saveData(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DAY1_LABEL, day1_label.getText().toString());
        editor.putString(DAY2_LABEL, day2_label.getText().toString());
        editor.putString(DAY3_LABEL, day3_label.getText().toString());
        editor.putString(DAY4_LABEL, day4_label.getText().toString());
        editor.putString(DAY5_LABEL, day5_label.getText().toString());
        editor.putString(DAY1_ICON, day1_icon.getText().toString());
        editor.putString(DAY2_ICON, day2_icon.getText().toString());
        editor.putString(DAY3_ICON, day3_icon.getText().toString());
        editor.putString(DAY4_ICON, day4_icon.getText().toString());
        editor.putString(DAY5_ICON, day5_icon.getText().toString());
        editor.putString(DAY1_TEMP, day1_temp.getText().toString());
        editor.putString(DAY2_HIGH_TEMP, day2_high_temp.getText().toString());
        editor.putString(DAY2_LOW_TEMP, day2_low_temp.getText().toString());
        editor.putString(DAY3_HIGH_TEMP, day3_high_temp.getText().toString());
        editor.putString(DAY3_LOW_TEMP, day3_low_temp.getText().toString());
        editor.putString(DAY4_HIGH_TEMP, day4_high_temp.getText().toString());
        editor.putString(DAY4_LOW_TEMP, day4_low_temp.getText().toString());
        editor.putString(DAY5_HIGH_TEMP, day5_high_temp.getText().toString());
        editor.putString(DAY5_LOW_TEMP, day5_low_temp.getText().toString());
        editor.apply();

    }

    public void getSavedData(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        day1_label.setText(sharedPreferences.getString(DAY1_LABEL, ""));
        day2_label.setText(sharedPreferences.getString(DAY2_LABEL, ""));
        day3_label.setText(sharedPreferences.getString(DAY3_LABEL, ""));
        day4_label.setText(sharedPreferences.getString(DAY4_LABEL, ""));
        day5_label.setText(sharedPreferences.getString(DAY5_LABEL, ""));
        day1_icon.setText(sharedPreferences.getString(DAY1_ICON,""));
        day2_icon.setText(sharedPreferences.getString(DAY2_ICON,""));
        day3_icon.setText(sharedPreferences.getString(DAY3_ICON,""));
        day4_icon.setText(sharedPreferences.getString(DAY4_ICON,""));
        day5_icon.setText(sharedPreferences.getString(DAY5_ICON,""));
        day1_icon.setText(sharedPreferences.getString(DAY1_TEMP,""));
        day2_high_temp.setText(sharedPreferences.getString(DAY2_HIGH_TEMP,""));
        day2_low_temp.setText(sharedPreferences.getString(DAY2_LOW_TEMP,""));
        day3_high_temp.setText(sharedPreferences.getString(DAY3_HIGH_TEMP,""));
        day3_low_temp.setText(sharedPreferences.getString(DAY3_LOW_TEMP,""));
        day4_high_temp.setText(sharedPreferences.getString(DAY4_HIGH_TEMP,""));
        day4_low_temp.setText(sharedPreferences.getString(DAY4_LOW_TEMP,""));
        day5_high_temp.setText(sharedPreferences.getString(DAY5_HIGH_TEMP,""));
        day5_low_temp.setText(sharedPreferences.getString(DAY5_LOW_TEMP,""));

    }

}
