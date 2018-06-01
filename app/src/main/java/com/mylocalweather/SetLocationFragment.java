package com.mylocalweather;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Set Location menu item
 */
public class SetLocationFragment extends Fragment{

    WeatherTask.WeatherUpdates listener;
    LocalWeatherMain swapFragment;
    ArrayList<String> cityState;
    ArrayList<String> cityID;
    CitySelector cs;
    WeatherUtils wu;
    AutoCompleteTextView locationInput;
    Button setDefaultLocation;
    int index;
    String searchType;
    String defaultLocation;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LocalWeatherMain) {
            listener = (LocalWeatherMain) activity;
            swapFragment = (LocalWeatherMain) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement LocalWeatherMain");
        }
    }
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        swapFragment = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LayoutInflater lf = getActivity().getLayoutInflater();
        View view = lf.inflate(R.layout.set_location_fragment, container, false);
        cs = new CitySelector(this.getActivity());
        wu = new WeatherUtils(this.getActivity());
        cs.loadJSONFromAsset();
        cityState = CitySelector.cityState;
        cityID = CitySelector.cityID;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_dropdown_item_1line, cityState);
        locationInput = (AutoCompleteTextView) view.findViewById(R.id.location_input);
        setDefaultLocation = (Button) view.findViewById(R.id.set_default_location);

        locationInput.setAdapter(adapter);
        locationInput.setThreshold(3);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        locationInput.setOnItemClickListener((parent, view1, position, id) -> {
            final InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(locationInput.getWindowToken(), 0);
            String s = parent.getItemAtPosition(position).toString();
            index = cityState.indexOf(s);

        });

        setDefaultLocation.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            searchType = wu.validateSearchString(locationInput.getText().toString());
            switch (searchType) {

                case "Blank":
                    Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                    break;

                case "City ID":
                    defaultLocation = cityID.get(index);
                    searchType = "City ID";
                    editor.putString("Default Location", defaultLocation);
                    editor.apply();
                    break;

                case "Invalid": Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
                    break;

                default:
                    Toast.makeText(getActivity().getBaseContext(), "Please enter valid search term", Toast.LENGTH_LONG).show();
            }
            swapFragment.swapFragment();
        });

        return view;
    }

}
