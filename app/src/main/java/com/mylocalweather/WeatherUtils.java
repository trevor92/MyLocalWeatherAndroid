package com.mylocalweather;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Trevor on 6/26/2016.
 * Weather Utility Class
 */

public class WeatherUtils {
    Context wContext;
    ZipCodeSelector zcs;
    
    public WeatherUtils (Context context){
        wContext = context;
    }

    public String stripUnderscores(String searchString){
            searchString = searchString.replace("_", " ");
            return searchString;
    }
    public String convertSpacesToUnderscores(String searchString){
        searchString = searchString.replace(" ","_");
        return searchString;
    }

    public String validateSearchString(String searchString){

        if(searchString.isEmpty()){
            return "Blank";
        }if(searchString.matches("(\\d{5})")){
            zcs = new ZipCodeSelector(wContext);
            if(zcs.checkZip(searchString) == "Invalid"){
                return "Invalid";
            }
            return "Zip Code";
        }
        if(searchString.matches("([a-zA-Z-\\s]+)(,\\s)([A-Z]{2})")){
            return "City ID";
        }
        return "Invalid";
    }

    public String setIcon(int iconID, long sunrise, long sunset) {

        String iconMapper = "";
        long currentTime = new Date().getTime();

        if(iconID >= 200 && iconID <= 202) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_200);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_200);
            }
        }
        if (iconID >= 210 && iconID <= 221) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_210);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_210);
            }
        }
        if (iconID >= 230 && iconID <= 232) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_230);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_230);
            }
        }
        if (iconID == 300 || iconID == 301 || iconID == 321 || iconID == 500) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_300);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_300);
            }
        }
        if (iconID == 302 || iconID == 311 || iconID == 312 || iconID == 314 ||
                iconID >= 501 && iconID <= 504) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_302);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_302);
            }
        }
        if (iconID == 310) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_310);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_310);
            }
        }
        if (iconID == 313) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_313);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_313);
            }
        }
        if (iconID == 511 || iconID >= 611 && iconID <= 620) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_511);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_511);
            }
        }
        if (iconID >= 520 && iconID <= 522 || iconID == 701) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_520);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_520);
            }
        }
        if (iconID == 531) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_531);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_531);
            }
        }
        if (iconID == 800) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_800);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_800);
            }
        }
        if (iconID == 600 || iconID == 601) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_600);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_600);
            }
        }
        if (iconID == 602) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_602);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_602);
            }
        }
        if (iconID == 621 || iconID == 622) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_621);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_621);
            }
        }
        if (iconID == 711) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_711);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_711);
            }
        }
        if (iconID == 721) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_721);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_721);
            }
        }
        if (iconID == 731 || iconID == 761 || iconID == 762) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_731);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_731);
            }
        }
        if (iconID == 771) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_771);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_771);
            }
        }
        if (iconID == 781) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_781);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_781);
            }
        }
        if (iconID == 800) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_800);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_800);
            }
        }
        if (iconID >= 801 && iconID <= 803) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_801);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_801);
            }
        }
        if (iconID == 804) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_804);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_804);
            }
        }
        if (iconID == 900) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_900);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_900);
            }
        }
        if (iconID == 901) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_901);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_901);
            }
        }
        if (iconID == 902) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_902);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_902);
            }
        }
        if (iconID == 903) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_903);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_903);
            }
        }
        if (iconID == 904) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_904);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_904);
            }
        }
        if (iconID == 905) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_905);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_906);
            }
        }
        if (iconID == 906) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_906);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_906);
            }
        }
        if (iconID == 957) {
            if (currentTime >= sunrise && currentTime < sunset) {
                iconMapper = wContext.getString(R.string.wi_owm_day_957);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_957);
                
            }
        }

        return iconMapper;
    }

    public String setIcon(int iconID, String dayNight) {

        String iconMapper = "";

        if(iconID >= 200 && iconID <= 202) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_200);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_200);
            }
        }

        if (iconID >= 210 && iconID <= 221) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_210);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_210);
            }
        }
        if (iconID >= 230 && iconID <= 232) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_230);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_230);
            }
        }
        if (iconID == 300 || iconID == 301 || iconID == 321 || iconID == 500) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_300);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_300);
            }
        }
        if (iconID == 302 || iconID == 311 || iconID == 312 || iconID == 314 ||
                iconID >= 501 && iconID <= 504) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_302);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_302);
            }
        }
        if (iconID == 310) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_310);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_310);
            }
        }
        if (iconID == 313) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_313);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_313);
            }
        }
        if (iconID == 511 || iconID >= 611 && iconID <= 620) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_511);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_511);
            }
        }
        if (iconID >= 520 && iconID <= 522 || iconID == 701) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_520);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_520);
            }
        }
        if (iconID == 531) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_531);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_531);
            }
        }
        if (iconID == 800) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_800);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_800);
            }
        }
        if (iconID == 600 || iconID == 601) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_600);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_600);
            }
        }
        if (iconID == 602) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_602);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_602);
            }
        }
        if (iconID == 621 || iconID == 622) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_621);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_621);
            }
        }
        if (iconID == 711) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_711);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_711);
            }
        }
        if (iconID == 721) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_721);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_721);
            }
        }
        if (iconID == 731 || iconID == 761 || iconID == 762) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_731);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_731);
            }
        }
        if (iconID == 771) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_771);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_771);
            }
        }
        if (iconID == 781) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_781);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_781);
            }
        }
        if (iconID == 800) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_800);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_800);
            }
        }
        if (iconID >= 801 && iconID <= 803) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_801);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_801);
            }
        }
        if (iconID == 804) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_804);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_804);
            }
        }
        if (iconID == 900) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_900);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_900);
            }
        }
        if (iconID == 901) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_901);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_901);
            }
        }
        if (iconID == 902) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_902);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_902);
            }
        }
        if (iconID == 903) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_903);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_903);
            }
        }
        if (iconID == 904) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_904);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_904);
            }
        }
        if (iconID == 905) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_905);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_906);
            }
        }
        if (iconID == 906) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_906);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_906);
            }
        }
        if (iconID == 957) {
            if (dayNight.equals("d")) {
                iconMapper = wContext.getString(R.string.wi_owm_day_957);
            } else {
                iconMapper = wContext.getString(R.string.wi_owm_night_957);
            }
        }

        return iconMapper;
    }

    public String setWindDirection(int windDirection, String windSpeed){
        String wind = "";

        if(windDirection == 0){
            wind = windSpeed + " N";
        }
        if(windDirection > 0 && windDirection < 45){
            wind = windSpeed + " NNE";
        }
        if(windDirection  == 45){
            wind = windSpeed + " NE";
        }
        if(windDirection > 45 && windDirection < 90){
            wind = windSpeed + " ENE";
        }
        if(windDirection == 90){
            wind = windSpeed + " E";
        }
        if(windDirection > 90 && windDirection < 135){
            wind = windSpeed + " ESE";
        }
        if(windDirection  == 135){
            wind = windSpeed + "  SE";
        }
        if(windDirection > 135 && windDirection < 180){
            wind = windSpeed + "  SSE";
        }
        if(windDirection == 180){
            wind = windSpeed + "  S";
        }
        if(windDirection > 180 && windDirection < 225){
            wind = windSpeed + "  SSW";
        }
        if(windDirection == 225){
            wind = windSpeed + "  SW";
        }
        if(windDirection > 225 && windDirection < 270){
            wind = windSpeed + "  WSW";
        }
        if(windDirection == 270){
            wind = windSpeed + "  W";
        }
        if(windDirection > 270 && windDirection < 315){
            wind = windSpeed + "  WNW";
        }
        if(windDirection == 315){
            wind = windSpeed + "  NW";
        }
        if(windDirection > 315 && windDirection < 360){
            wind = windSpeed + "  NNW";
        }
        if(windDirection == 360){
            wind = windSpeed + " N";
        }
        return wind;

    }

    public String dateFormat(Long timeToConvert){
        Date date = new Date(timeToConvert);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());

        return dateFormat.format(date);
    }

    public String hourDateFormat(Long day) {
        Date date = new Date(day * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h a", Locale.getDefault());

        return dateFormat.format(date);
    }

    public String forecastDateFormat(Long timeToConvert){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        String myDate = dateFormat.format(new Date(timeToConvert*1000));

        return myDate;
    }

    public String getDayOrNight(String iconCode){
        return iconCode.substring(2);
    }

    public boolean hasTenMinsElapsed(Long startTime){
        boolean tenMins;
        long timeNow = System.currentTimeMillis();
        long checkTime = (((timeNow - startTime)/1000)/60);

        if(checkTime<10){
            tenMins = false;
        }
        else{
            tenMins = true;
        }
        return tenMins;
    }
    public String[] getDeviceWindowDimensions(){
        String [] displayDimensions = new String[2];
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) wContext.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int widthInDP = Math.round(dm.widthPixels / dm.density);
        int heightInDP = (Math.round(dm.heightPixels / dm.density));

        displayDimensions[0] = String.valueOf(widthInDP);
        displayDimensions[1] = String.valueOf(heightInDP-98);

        return displayDimensions;
    }

}
