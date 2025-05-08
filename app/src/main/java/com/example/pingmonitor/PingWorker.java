package com.example.pingmonitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.pingmonitor.db.DBHelper;
import com.example.pingmonitor.util.PreferencesHelper;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimerTask;

public class PingWorker extends TimerTask {
    private final Context context;

    public PingWorker(Context ctx) {
        this.context = ctx;
    }

    private boolean pingIP(String ip) {
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/ping -c 1 " + ip);
            return process.waitFor() == 0;
        } catch (Exception ex) {
            return false;
        }
    }

    private boolean pingAPI(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            return conn.getResponseCode() == 200;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void run() {
        String ip = PreferencesHelper.getIP(context);
        String api = PreferencesHelper.getAPI(context);

        boolean icmp = pingIP(ip);
        boolean http = pingAPI(api);

        long now = System.currentTimeMillis();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();

        db.execSQL("INSERT INTO logs (timestamp, ip, api, icmpSuccess, httpSuccess) VALUES (?, ?, ?, ?, ?)",
                new Object[]{now, ip, api, icmp ? 1 : 0, http ? 1 : 0});

        long cutoff = now - (3L * 24 * 60 * 60 * 1000);
        db.execSQL("DELETE FROM logs WHERE timestamp < ?", new Object[]{cutoff});
        db.close();
    }
}
