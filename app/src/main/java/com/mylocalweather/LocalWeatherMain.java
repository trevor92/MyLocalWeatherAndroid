package com.mylocalweather;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.Objects;

import static android.view.View.OnClickListener;


public class LocalWeatherMain extends AppCompatActivity implements OnClickListener, WeatherTask.WeatherUpdates, WeatherMapFragment.Radar {

    MainFragment mainFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_weather_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	    TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
	    toolbarTitle.setText("My Local Weather");

           if(savedInstanceState == null){
               mainFragment = new MainFragment();
               FragmentManager fragmentManager = getFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.fragment_container, mainFragment, "Main Fragment");
               fragmentTransaction.commit();
           }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.local_weather_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.set_location:
                setLocation();
                return true;
            case R.id.about:
                setAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void findCurrentWeather(JSONObject jsonObject) {


        CurrentConditionsFragment currentConditionsFragment = new CurrentConditionsFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, currentConditionsFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        getFragmentManager().executePendingTransactions();
        currentConditionsFragment.setCurrentConditions(jsonObject);
    }

    @Override
    public void findForecast(JSONObject jsonObject) {
        ForecastFragment forecastFragment = new ForecastFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, forecastFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        getFragmentManager().executePendingTransactions();
        forecastFragment.setForecast(jsonObject);
    }

    @Override
    public void findHourForecast(JSONObject jsonObject){
        HourlyFragment hourFragment = new HourlyFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, hourFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        getFragmentManager().executePendingTransactions();
        hourFragment.setHourConditions(jsonObject);
    }

    @Override
    public void findMaps(String city, String lon, String lat) {
        WeatherMapFragment mapFragment = new WeatherMapFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mapFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        getFragmentManager().executePendingTransactions();
        mapFragment.setWeatherMap(city, lon, lat);
    }

    public void findHomeCurrentWeather(JSONObject jsonObject){

        mainFragment.setHomeCurrentTemp(jsonObject);


    }

    public void setLocation(){
        SetLocationFragment locationFragment = new SetLocationFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, locationFragment, "Location Fragment");
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        getFragmentManager().executePendingTransactions();

    }

    public void setAbout(){
        AboutFragment aboutFragment = new AboutFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, aboutFragment, "About Fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getFragmentManager().executePendingTransactions();

    }

    public void swapFragment(){
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
    }

}