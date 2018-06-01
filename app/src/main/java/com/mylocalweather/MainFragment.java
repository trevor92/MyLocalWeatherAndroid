package com.mylocalweather;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Fragment Class
 */
public class MainFragment extends Fragment{

    AutoCompleteTextView cityInput;
    TextView homeCity;
    TextView homeWeatherDescription;
    TextView homeWeatherIcon;
    TextView homeCurrentTemp;
    Button refreshButton;
    View divider;
    Button cityWeatherSearch;
    Button cityForecastSearch;
    WeatherTask weatherTask;
    String searchBy;

    private WeatherTask.WeatherUpdates listener;

    static ArrayList<String> cityState = new ArrayList<>();
    static ArrayList<String> cityID = new ArrayList<>();
    static ArrayList<String> zipCode = new ArrayList<>();
    static ArrayList<String> combined = new ArrayList<>();
    CitySelector cs;
    ZipCodeSelector zcs;
    int index;
    String searchType;
    SharedPreferences sharedPreferences;

    WeatherUtils wu;
    Network network;
    Bundle savedData;

    long dlBaseTime;
    long ccBaseTime;

    String id;
    View view;

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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.main_fragment, container, false);
        view.findViewById(R.id.loadingAnimation).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.home_location).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.default_divider).setVisibility(View.INVISIBLE);
        cs = new CitySelector(this.getActivity().getBaseContext());
        zcs = new ZipCodeSelector(this.getActivity().getBaseContext());
        wu = new WeatherUtils(this.getActivity().getBaseContext());
        cs.loadJSONFromAsset();
        zcs.loadJSONFromAsset();
        cityState = CitySelector.cityState;
        cityID = CitySelector.cityID;
        zipCode = ZipCodeSelector.zipCode;
        index = 0;

        dlBaseTime = System.currentTimeMillis();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        network = new Network();
        boolean isConnected = network.checkNetworkConnection(getActivity().getBaseContext());

        if(sharedPreferences.contains("Default Location")){

            String cityStateCombo = sharedPreferences.getString("Default Location", "");
            homeCity = (TextView) view.findViewById(R.id.home_weather_city);
            homeCurrentTemp = (TextView) view.findViewById(R.id.home_current_temp);
            homeWeatherDescription = (TextView) view.findViewById(R.id.home_weather_description);
            homeWeatherIcon = (TextView) view.findViewById(R.id.home_weather_icon);
            refreshButton = (Button) view.findViewById(R.id.refresh_home_weather);
            divider = view.findViewById(R.id.default_divider);
            searchBy = cityStateCombo;
            searchType = "City ID";


            refreshButton.setOnClickListener(v -> {

                if(wu.hasTenMinsElapsed(dlBaseTime) == true){

                    if(isConnected == true){

                        weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                        weatherTask.execute("Home Current Weather", searchBy, searchType);
                    }
                    else{
                        network.showNetworkError(getActivity().getBaseContext());
                    }
                }
            });

                if(isConnected == true){

                    view.findViewById(R.id.home_location).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.default_divider).setVisibility(View.VISIBLE);
                    weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                    weatherTask.execute("Home Current Weather", searchBy, searchType);
                }
                else{
                    network.showNetworkError(getActivity().getBaseContext());
                    view.findViewById(R.id.loadingAnimationHomeCurrentWeather).setVisibility(View.INVISIBLE);
                }



        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_dropdown_item_1line, cityState);

        cityInput = (AutoCompleteTextView) view.findViewById(R.id.city_input);
        cityInput.setAdapter(adapter);
        cityInput.setThreshold(3);

        cityInput.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if ((actionId == EditorInfo.IME_ACTION_DONE) || actionId == EditorInfo.IME_ACTION_SEND) {
                final InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(cityInput.getWindowToken(), 0);
                handled = true;
            }
            return handled;
        });

        cityInput.setOnItemClickListener((parent, view1, position, id) -> {
            String s = parent.getItemAtPosition(position).toString();
            index = cityState.indexOf(s);
        });

        cityWeatherSearch = (Button) view.findViewById(R.id.city_weather_search);
        cityWeatherSearch.setOnClickListener(v -> {
            editor.putString("Search Term", cityInput.getText().toString());
            editor.apply();
            String searchByID = cityID.get(index);
            System.out.println(searchByID);
            String savedID = sharedPreferences.getString("City ID", "");

            if(searchByID.equals(savedID)){

                if(wu.hasTenMinsElapsed(ccBaseTime) == false) {

                    CurrentConditionsFragment currentConditionsFragment = new CurrentConditionsFragment();

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, currentConditionsFragment);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    getFragmentManager().executePendingTransactions();
                    currentConditionsFragment.getSavedData();
                }
                else{
                    if(isConnected == true){

                        ccBaseTime = System.currentTimeMillis();
                        searchType = wu.validateSearchString(cityInput.getText().toString());
                        switch (searchType) {

                            case "Blank":
                                Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                                break;

                            case "City ID":
                                searchBy = cityID.get(index);
                                searchType = "City ID";
                                weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                                weatherTask.execute("Current Weather", searchBy, searchType);
                                editor.putString("City ID CW", searchByID);
                                editor.apply();
                                break;

                            case "Zip Code":
                                searchBy = cityInput.getText().toString();
                                weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                                weatherTask.execute("Current Weather", searchBy, searchType);
                                editor.putInt("Current Index", index);
                                editor.apply();
                                break;

                            case "Invalid":
                                Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                                break;

                            default:
                                Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                        }
                    }else {

                        network.showNetworkError(getActivity().getBaseContext());
                    }
                }

            }

            else {

                if(isConnected == true){

                    ccBaseTime = System.currentTimeMillis();
                    searchType = wu.validateSearchString(cityInput.getText().toString());
                    switch (searchType) {

                        case "Blank":
                            Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                            break;

                        case "City ID":
                            searchBy = cityID.get(index);
                            searchType = "City ID";
                            weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                            weatherTask.execute("Current Weather", searchBy, searchType);
                            editor.putString("City ID CW", searchByID);
                            editor.apply();
                            break;

                        case "Zip Code":
                            searchBy = cityInput.getText().toString();
                            weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                            weatherTask.execute("Current Weather", searchBy, searchType);
                            editor.putString("City ID CW", searchByID);
                            editor.apply();
                            break;

                        case "Invalid":
                            Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                            break;

                        default:
                            Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                    }
                }else {

                    network.showNetworkError(getActivity().getBaseContext());
                }
            }
        });

        cityForecastSearch = (Button) view.findViewById(R.id.city_forecast_search);
        cityForecastSearch.setOnClickListener(v -> {
            editor.putString("Search Term", cityInput.getText().toString());
            editor.apply();
            String searchByID = cityID.get(index);
            String savedID = sharedPreferences.getString("City ID Forecast", "");

            if(searchByID.equals(savedID)) {

                if (wu.hasTenMinsElapsed(ccBaseTime) == false) {
                    System.out.println("same id forecast");
                    ForecastFragment forecastFragment = new ForecastFragment();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, forecastFragment);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    getFragmentManager().executePendingTransactions();
                    forecastFragment.getSavedData();
                } else {

                    if (isConnected == true) {
                        searchType = wu.validateSearchString(cityInput.getText().toString());
                        switch (searchType) {

                            case "Blank":
                                Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                                break;

                            case "City ID":
                                searchBy = cityID.get(index);
                                searchType = "City ID";
                                weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                                weatherTask.execute("Forecast", searchBy, searchType);
                                editor.putString("City ID Forecast", searchByID);
                                editor.apply();
                                break;


                            case "Zip Code":
                                searchBy = cityInput.getText().toString();
                                weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                                weatherTask.execute("Forecast", searchBy, searchType);
                                break;

                            case "Invalid":
                                Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();

                            default:
                                Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        network.showNetworkError(getActivity().getBaseContext());
                    }
                }

            }


            else{

                if (isConnected == true) {
                    searchType = wu.validateSearchString(cityInput.getText().toString());
                    switch (searchType) {

                        case "Blank":
                            Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                            break;

                        case "City ID":
                            searchBy = cityID.get(index);
                            searchType = "City ID";
                            weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                            weatherTask.execute("Forecast", searchBy, searchType);
                            editor.putString("City ID Forecast", searchByID);
                            editor.apply();
                            break;


                        case "Zip Code":
                            searchBy = cityInput.getText().toString();
                            weatherTask = new WeatherTask(getActivity().getBaseContext(), (LocalWeatherMain) listener, (LocalWeatherMain) getActivity());
                            weatherTask.execute("Forecast", searchBy, searchType);
                            break;

                        case "Invalid":
                            Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();

                        default:
                            Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                    }
                } else {

                    network.showNetworkError(getActivity().getBaseContext());
                }
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        cityInput.setText("");

    }

    public void setHomeCurrentTemp(JSONObject jsonObject) {
        try {

            Typeface weather_font = Typeface.createFromAsset(getActivity().getBaseContext().getAssets(),"weathericons.ttf");
            String jsonMain = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            Double main = jsonObject.getJSONObject("main").getDouble("temp");
            Integer mainTemp = (int) Math.round(main);
            String city = jsonObject.getString("name");
            int iconID = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            Long sunrise = jsonObject.getJSONObject("sys").getLong("sunrise") * 1000;
            Long sunset = jsonObject.getJSONObject("sys").getLong("sunset") * 1000;
            view.findViewById(R.id.loadingAnimationHomeCurrentWeather).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.refresh_home_weather).setVisibility(View.VISIBLE);
            homeWeatherDescription.setText(jsonMain);
            homeCurrentTemp.setText(mainTemp.toString() + (char) 0x00B0 + "F");
            homeCity.setText(city);
            homeWeatherIcon.setTypeface(weather_font);
            homeWeatherIcon.setText(wu.setIcon(iconID, sunrise, sunset));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
