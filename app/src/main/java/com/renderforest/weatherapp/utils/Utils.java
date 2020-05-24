package com.renderforest.weatherapp.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.renderforest.weatherapp.app.App;
import com.renderforest.weatherapp.base.constants.TemperatureUnit;

public class Utils {

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isGPSAvailable() {
        LocationManager locationManager = ((LocationManager) App.context.getSystemService(Context.LOCATION_SERVICE));
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static double temperatureParser(double temp, int unitIn, int unitOut) {
        double outTemp = temp;
        if (unitIn == TemperatureUnit.KELVIN) {
            if (unitOut == TemperatureUnit.CELSIUS) {
                outTemp = temp - 273.15;
            } else if (unitOut == TemperatureUnit.FAHRENHEIT) {
                outTemp = temp * 9 / 5 - 459.67;
            }
        } else if (unitIn == TemperatureUnit.CELSIUS) {
            if (unitOut == TemperatureUnit.KELVIN) {
                outTemp = temp + 273.15;
            } else if (unitOut == TemperatureUnit.FAHRENHEIT) {
                outTemp = temp * 18 / 10 + 32;
            }
        } else if (unitIn == TemperatureUnit.FAHRENHEIT) {
            if (unitOut == TemperatureUnit.CELSIUS) {
                outTemp = (temp - 32) * 10 / 18;
            } else if (unitOut == TemperatureUnit.KELVIN) {
                outTemp = (temp + 459.67) * 5 / 9;
            }
        }

        return outTemp;
    }
}
