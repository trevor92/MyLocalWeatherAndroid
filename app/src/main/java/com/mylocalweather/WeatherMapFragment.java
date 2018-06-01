package com.mylocalweather;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


/**
 * Created by Trevor on 3/27/2016.
 * Class for Weather Maps
 */
public class WeatherMapFragment extends Fragment {
    private static final String WU_KEY = BuildConfig.WU_API_KEY;
    WeatherTask.WeatherUpdates listener;
    WeatherUtils wu;
    TextView cityLabel;
    WebView radar;
    ProgressDialog dialog;


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
        View view = lf.inflate(R.layout.weather_maps, container, false);
        wu = new WeatherUtils(this.getActivity());
        cityLabel = (TextView) view.findViewById(R.id.radar_label);
        radar = (WebView) view.findViewById(R.id.radar_webview);
        dialog = new ProgressDialog(this.getActivity());
        dialog.setMessage("Loading Map Data!");
        dialog.setCancelable(false);

        return view;
    }

    public void setWeatherMap(String city, String lon, String lat){
        String cityTerm = wu.stripUnderscores(city);
        cityLabel.setText(cityTerm);
        String [] screenDimensions = wu.getDeviceWindowDimensions();
        String width = screenDimensions[0];
        String height = screenDimensions[1];

        radar.setWebViewClient(new MyWebViewClient());
        String url = "http://api.wunderground.com/api/"+WU_KEY+"/animatedradar/image.gif?centerlon="+lon+"&centerlat="+lat+"&newmaps=1&delay=50&radius=75&num=15&width="+width+"&height="+height+"&timelabel=1&timelabel.y=20&noclutter=1";
        System.out.println(url);
        radar.loadUrl(url);
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            dialog.show();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            dialog.dismiss();
        }
    }

    public interface Radar{
        void findMaps(String city, String lon, String lat);
    }
}
