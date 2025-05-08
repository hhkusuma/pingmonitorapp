package com.example.pingmonitor.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {
    private static final String PREFS = "PingPrefs";

    public static void saveConfig(Context ctx, String ip, String api, int interval) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
        editor.putString("ip", ip);
        editor.putString("api", api);
        editor.putInt("interval", interval);
        editor.apply();
    }

    public static String getIP(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString("ip", "");
    }

    public static String getAPI(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString("api", "");
    }

    public static int getInterval(Context ctx) {
        return ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt("interval", 60);
    }
}
