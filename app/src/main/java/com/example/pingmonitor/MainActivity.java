package com.example.pingmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pingmonitor.util.PreferencesHelper;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    private EditText ipInput, apiInput, intervalInput;
    private Button startBtn, logsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipInput = findViewById(R.id.ipInput);
        apiInput = findViewById(R.id.apiInput);
        intervalInput = findViewById(R.id.intervalInput);
        startBtn = findViewById(R.id.startBtn);
        logsBtn = findViewById(R.id.logsBtn);

        ipInput.setText(PreferencesHelper.getIP(this));
        apiInput.setText(PreferencesHelper.getAPI(this));
        intervalInput.setText(String.valueOf(PreferencesHelper.getInterval(this)));

        startBtn.setOnClickListener(v -> {
            String ip = ipInput.getText().toString();
            String api = apiInput.getText().toString();
            int interval = Integer.parseInt(intervalInput.getText().toString());

            PreferencesHelper.saveConfig(this, ip, api, interval);

            timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new PingWorker(this), 0, interval * 1000L);
        });

        logsBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, LogActivity.class));
        });
    }
}
