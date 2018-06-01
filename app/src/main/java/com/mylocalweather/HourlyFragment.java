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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Trevor on 1/31/2016.
 */
public class HourlyFragment extends Fragment{
    TextView hourSetLabel1;
    TextView hourSetIcon1;
    TextView hourSetTemp1;
    TextView hourSetLabel2;
    TextView hourSetIcon2;
    TextView hourSetTemp2;
    TextView hourSetLabel3;
    TextView hourSetIcon3;
    TextView hourSetTemp3;
    TextView hourSetLabel4;
    TextView hourSetIcon4;
    TextView hourSetTemp4;
    TextView hourSetLabel5;
    TextView hourSetIcon5;
    TextView hourSetTemp5;
    TextView hourSetLabel6;
    TextView hourSetIcon6;
    TextView hourSetTemp6;
    TextView hourSetLabel7;
    TextView hourSetIcon7;
    TextView hourSetTemp7;
    TextView hourSetLabel8;
    TextView hourSetIcon8;
    TextView hourSetTemp8;
    Typeface weather_font;
    String dayNight;
    WeatherTask.WeatherUpdates listener;
    SharedPreferences sharedPreferences;
    WeatherUtils wu;
    static final String HOUR_TIME_1 = "Hour Time 1";
    static final String HOUR_TIME_2 = "Hour Time 2";
    static final String HOUR_TIME_3 = "Hour Time 3";
    static final String HOUR_TIME_4 = "Hour Time 4";
    static final String HOUR_TIME_5 = "Hour Time 5";
    static final String HOUR_TIME_6 = "Hour Time 6";
    static final String HOUR_TIME_7 = "Hour Time 7";
    static final String HOUR_TIME_8 = "Hour Time 8";
    static final String HOUR_ICON_1 = "Hour Icon 1";
    static final String HOUR_ICON_2 = "Hour Icon 2";
    static final String HOUR_ICON_3 = "Hour Icon 3";
    static final String HOUR_ICON_4 = "Hour Icon 4";
    static final String HOUR_ICON_5 = "Hour Icon 5";
    static final String HOUR_ICON_6 = "Hour Icon 6";
    static final String HOUR_ICON_7 = "Hour Icon 7";
    static final String HOUR_ICON_8 = "Hour Icon 8";
    static final String HOUR_TEMP_1 = "Hour Temp 1";
    static final String HOUR_TEMP_2 = "Hour Temp 2";
    static final String HOUR_TEMP_3 = "Hour Temp 3";
    static final String HOUR_TEMP_4 = "Hour Temp 4";
    static final String HOUR_TEMP_5 = "Hour Temp 5";
    static final String HOUR_TEMP_6 = "Hour Temp 6";
    static final String HOUR_TEMP_7 = "Hour Temp 7";
    static final String HOUR_TEMP_8 = "Hour Temp 8";
    
    
    

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
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.hourly_fragment, container, false);
        wu = new WeatherUtils(this.getActivity().getBaseContext());
        weather_font = Typeface.createFromAsset(getActivity().getBaseContext().getAssets(),"weathericons.ttf");

        hourSetLabel1 = (TextView) view.findViewById(R.id.hour_set1_time_label);
        hourSetIcon1 = (TextView) view.findViewById(R.id.hour_set1_icon);
        hourSetIcon1.setTypeface(weather_font);
        hourSetTemp1 = (TextView) view.findViewById(R.id.hour_set1_temp);

        hourSetLabel2 = (TextView) view.findViewById(R.id.hour_set2_time_label);
        hourSetIcon2 = (TextView) view.findViewById(R.id.hour_set2_icon);
        hourSetIcon2.setTypeface(weather_font);
        hourSetTemp2 = (TextView) view.findViewById(R.id.hour_set2_temp);

        hourSetLabel3 = (TextView) view.findViewById(R.id.hour_set3_time_label);
        hourSetIcon3 = (TextView) view.findViewById(R.id.hour_set3_icon);
        hourSetIcon3.setTypeface(weather_font);
        hourSetTemp3 = (TextView) view.findViewById(R.id.hour_set3_temp);

        hourSetLabel4 = (TextView) view.findViewById(R.id.hour_set4_time_label);
        hourSetIcon4 = (TextView) view.findViewById(R.id.hour_set4_icon);
        hourSetIcon4.setTypeface(weather_font);
        hourSetTemp4 = (TextView) view.findViewById(R.id.hour_set4_temp);

        hourSetLabel5 = (TextView) view.findViewById(R.id.hour_set5_time_label);
        hourSetIcon5 = (TextView) view.findViewById(R.id.hour_set5_icon);
        hourSetIcon5.setTypeface(weather_font);
        hourSetTemp5 = (TextView) view.findViewById(R.id.hour_set5_temp);

        hourSetLabel6 = (TextView) view.findViewById(R.id.hour_set6_time_label);
        hourSetIcon6 = (TextView) view.findViewById(R.id.hour_set6_icon);
        hourSetIcon6.setTypeface(weather_font);
        hourSetTemp6 = (TextView) view.findViewById(R.id.hour_set6_temp);

        hourSetLabel7 = (TextView) view.findViewById(R.id.hour_set7_time_label);
        hourSetIcon7 = (TextView) view.findViewById(R.id.hour_set7_icon);
        hourSetIcon7.setTypeface(weather_font);
        hourSetTemp7 = (TextView) view.findViewById(R.id.hour_set7_temp);

        hourSetLabel8 = (TextView) view.findViewById(R.id.hour_set8_time_label);
        hourSetIcon8 = (TextView) view.findViewById(R.id.hour_set8_icon);
        hourSetIcon8.setTypeface(weather_font);
        hourSetTemp8 = (TextView) view.findViewById(R.id.hour_set8_temp);

        return view;

    }

    public void setHourConditions(JSONObject jsonObject){

        try {
            JSONArray forecast = jsonObject.getJSONArray("list");
            JSONObject hour_set1 = forecast.getJSONObject(0);
            JSONObject hour_set2 = forecast.getJSONObject(1);
            JSONObject hour_set3 = forecast.getJSONObject(2);
            JSONObject hour_set4 = forecast.getJSONObject(3);
            JSONObject hour_set5 = forecast.getJSONObject(4);
            JSONObject hour_set6 = forecast.getJSONObject(5);
            JSONObject hour_set7 = forecast.getJSONObject(6);
            JSONObject hour_set8 = forecast.getJSONObject(7);


            //Hour Set 1
            Long hour_label1 = hour_set1.getLong("dt");
            hourSetLabel1.setText(wu.hourDateFormat(hour_label1));
            System.out.println("Hour set label from setHour: "+hourSetLabel1.getText());
            String iconCode1 = hour_set1.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode1);
            Integer iconID1 = hour_set1.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping1 = wu.setIcon(iconID1, dayNight);
            hourSetIcon1.setText(iconMapping1);
            System.out.println("Hour set label from setHour: "+hourSetIcon1.getText());
            Integer temp1 = hour_set1.getJSONObject("main").getInt("temp");
            hourSetTemp1.setText(temp1.toString() + (char) 0x00B0 + "F");
            System.out.println("Hour set label from setHour: "+hourSetTemp1.getText());

            //Hour Set 2
            Long hour_label2 = hour_set2.getLong("dt");
            hourSetLabel2.setText(wu.hourDateFormat(hour_label2));
            String iconCode2 = hour_set2.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode2);
            Integer iconID2 = hour_set2.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping2 = wu.setIcon(iconID2, dayNight);
            hourSetIcon2.setText(iconMapping2);
            Integer temp2 = hour_set2.getJSONObject("main").getInt("temp");
            hourSetTemp2.setText(temp2.toString() + (char) 0x00B0 + "F");

            //Hour Set 3
            Long hour_label3 = hour_set3.getLong("dt");
            hourSetLabel3.setText(wu.hourDateFormat(hour_label3));
            String iconCode3 = hour_set3.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode3);
            Integer iconID3 = hour_set3.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping3 = wu.setIcon(iconID3, dayNight);
            hourSetIcon3.setText(iconMapping3);
            Integer temp3 = hour_set3.getJSONObject("main").getInt("temp");
            hourSetTemp3.setText(temp3.toString() + (char) 0x00B0 + "F");

            //Hour Set 4
            Long hour_label4 = hour_set4.getLong("dt");
            hourSetLabel4.setText(wu.hourDateFormat(hour_label4));
            String iconCode4 = hour_set4.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode4);
            Integer iconID4 = hour_set4.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping4 = wu.setIcon(iconID4, dayNight);
            hourSetIcon4.setText(iconMapping4);
            Integer temp4 = hour_set4.getJSONObject("main").getInt("temp");
            hourSetTemp4.setText(temp4.toString() + (char) 0x00B0 + "F");

            //Hour Set 5
            Long hour_label5 = hour_set5.getLong("dt");
            hourSetLabel5.setText(wu.hourDateFormat(hour_label5));
            String iconCode5 = hour_set5.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode5);
            Integer iconID5 = hour_set5.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping5 = wu.setIcon(iconID5, dayNight);
            hourSetIcon5.setText(iconMapping5);
            Integer temp5 = hour_set5.getJSONObject("main").getInt("temp");
            hourSetTemp5.setText(temp5.toString() + (char) 0x00B0 + "F");

            //Hour Set 6
            Long hour_label6 = hour_set6.getLong("dt");
            hourSetLabel6.setText(wu.hourDateFormat(hour_label6));
            String iconCode6 = hour_set6.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode6);
            Integer iconID6 = hour_set6.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping6 = wu.setIcon(iconID6, dayNight);
            hourSetIcon6.setText(iconMapping6);
            Integer temp6 = hour_set6.getJSONObject("main").getInt("temp");
            hourSetTemp6.setText(temp6.toString() + (char) 0x00B0 + "F");

            //Hour Set 7
            Long hour_label7 = hour_set7.getLong("dt");
            hourSetLabel7.setText(wu.hourDateFormat(hour_label7));
            String iconCode7 = hour_set7.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode7);
            Integer iconID7 = hour_set7.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping7 = wu.setIcon(iconID7, dayNight);
            hourSetIcon7.setText(iconMapping7);
            Integer temp7 = hour_set7.getJSONObject("main").getInt("temp");
            hourSetTemp7.setText(temp7.toString() + (char) 0x00B0 + "F");

            //Hour Set 8
            Long hour_label8 = hour_set8.getLong("dt");
            hourSetLabel8.setText(wu.hourDateFormat(hour_label8));
            String iconCode8 = hour_set8.getJSONArray("weather").getJSONObject(0).getString("icon");
            dayNight = wu.getDayOrNight(iconCode8);
            Integer iconID8 = hour_set8.getJSONArray("weather").getJSONObject(0).getInt("id");
            String iconMapping8 = wu.setIcon(iconID8, dayNight);
            hourSetIcon8.setText(iconMapping8);
            Integer temp8 = hour_set8.getJSONObject("main").getInt("temp");
            hourSetTemp8.setText(temp8.toString() + (char) 0x00B0 + "F");

            saveData();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void saveData(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(HOUR_TIME_1, hourSetLabel1.getText().toString());
        editor.putString(HOUR_ICON_1, hourSetIcon1.getText().toString());
        editor.putString(HOUR_TEMP_1, hourSetTemp1.getText().toString());
        editor.putString(HOUR_TIME_2, hourSetLabel2.getText().toString());
        editor.putString(HOUR_ICON_2, hourSetIcon2.getText().toString());
        editor.putString(HOUR_TEMP_2, hourSetTemp2.getText().toString());
        editor.putString(HOUR_TIME_3, hourSetLabel3.getText().toString());
        editor.putString(HOUR_ICON_3, hourSetIcon3.getText().toString());
        editor.putString(HOUR_TEMP_3, hourSetTemp3.getText().toString());
        editor.putString(HOUR_TIME_4, hourSetLabel4.getText().toString());
        editor.putString(HOUR_ICON_4, hourSetIcon4.getText().toString());
        editor.putString(HOUR_TEMP_4, hourSetTemp4.getText().toString());
        editor.putString(HOUR_TIME_5, hourSetLabel5.getText().toString());
        editor.putString(HOUR_ICON_5, hourSetIcon5.getText().toString());
        editor.putString(HOUR_TEMP_5, hourSetTemp5.getText().toString());
        editor.putString(HOUR_TIME_6, hourSetLabel6.getText().toString());
        editor.putString(HOUR_ICON_6, hourSetIcon6.getText().toString());
        editor.putString(HOUR_TEMP_6, hourSetTemp6.getText().toString());
        editor.putString(HOUR_TIME_7, hourSetLabel7.getText().toString());
        editor.putString(HOUR_ICON_7, hourSetIcon7.getText().toString());
        editor.putString(HOUR_TEMP_7, hourSetTemp7.getText().toString());
        editor.putString(HOUR_TIME_8, hourSetLabel8.getText().toString());
        editor.putString(HOUR_ICON_8, hourSetIcon8.getText().toString());
        editor.putString(HOUR_TEMP_8, hourSetTemp8.getText().toString());

        System.out.println(sharedPreferences.getString(HOUR_TIME_1,""));
        editor.apply();
    }

    public void getSavedData(){
        //System.out.println(sharedPreferences.getString(HOUR_TIME_1,""));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        hourSetLabel1.setText(sharedPreferences.getString(HOUR_TIME_1, ""));
        hourSetIcon1.setText(sharedPreferences.getString(HOUR_ICON_1, ""));
        hourSetIcon1.setTypeface(weather_font);
        hourSetTemp1.setText(sharedPreferences.getString(HOUR_TEMP_1, ""));
        hourSetLabel2.setText(sharedPreferences.getString(HOUR_TIME_2, ""));
        hourSetIcon2.setText(sharedPreferences.getString(HOUR_ICON_2, ""));
        hourSetIcon2.setTypeface(weather_font);
        hourSetTemp2.setText(sharedPreferences.getString(HOUR_TEMP_2, ""));
        hourSetLabel3.setText(sharedPreferences.getString(HOUR_TIME_3, ""));
        hourSetIcon3.setText(sharedPreferences.getString(HOUR_ICON_3, ""));
        hourSetTemp3.setText(sharedPreferences.getString(HOUR_TEMP_3, ""));
        hourSetLabel4.setText(sharedPreferences.getString(HOUR_TIME_4, ""));
        hourSetIcon4.setText(sharedPreferences.getString(HOUR_ICON_4, ""));
        hourSetTemp4.setText(sharedPreferences.getString(HOUR_TEMP_4, ""));
        hourSetLabel5.setText(sharedPreferences.getString(HOUR_TIME_5, ""));
        hourSetIcon5.setText(sharedPreferences.getString(HOUR_ICON_5, ""));
        hourSetTemp5.setText(sharedPreferences.getString(HOUR_TEMP_5, ""));
        hourSetLabel6.setText(sharedPreferences.getString(HOUR_TIME_6, ""));
        hourSetIcon6.setText(sharedPreferences.getString(HOUR_ICON_6, ""));
        hourSetTemp6.setText(sharedPreferences.getString(HOUR_TEMP_6, ""));
        hourSetLabel7.setText(sharedPreferences.getString(HOUR_TIME_7, ""));
        hourSetIcon7.setText(sharedPreferences.getString(HOUR_ICON_7, ""));
        hourSetTemp7.setText(sharedPreferences.getString(HOUR_TEMP_7, ""));
        hourSetLabel8.setText(sharedPreferences.getString(HOUR_TIME_8, ""));
        hourSetIcon8.setText(sharedPreferences.getString(HOUR_ICON_8, ""));
        hourSetTemp8.setText(sharedPreferences.getString(HOUR_TEMP_8, ""));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
