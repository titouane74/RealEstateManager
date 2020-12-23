package com.openclassrooms.realestatemanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Philippe on 21/02/2018.
 */

public class Utils {

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param dollars : int : price to convert
     * @return : int : price converted
     */
    public static int convertDollarToEuro(int dollars) {
        return (int) Math.round(dollars * 0.812);
    }

    /**
     * Converting the price of a property (Euros to Dollars)
     * @param pEuros : int : price in euros
     * @return : int : price convert in dollars
     */
    public static int convertEuroToDollar(int pEuros) {
        return (int) Math.round(pEuros * 1.18);
    }

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @return : string : day formatted
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTodayDate() {
        //Change format display
        //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
         DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     *
     * @param context : context
     * @return : boolean : true is wifi is enabled
     */
    public static Boolean isInternetAvailable(Context context) {
        boolean lIsWifi = false;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            ConnectivityManager lConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (lConnectivityManager == null) return false;
            Network lNetwork = lConnectivityManager.getActiveNetwork();
            if (lNetwork == null) return false;

            NetworkCapabilities lCapabilities = lConnectivityManager.getNetworkCapabilities(lNetwork);
            if (lCapabilities != null)
                lIsWifi = lCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || lCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
        } else {
            WifiManager wifi = (WifiManager) context.getApplicationContext()
                    .getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) lIsWifi = wifi.isWifiEnabled();
        }
        return lIsWifi;
        //WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        //return wifi.isWifiEnabled();
    }

}
