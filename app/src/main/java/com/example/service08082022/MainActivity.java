package com.example.service08082022;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnStartService, btnStopService;
    TextView tvCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.button_start_service);
        btnStopService = findViewById(R.id.button_stop_service);
        tvCount = findViewById(R.id.text_view_count);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra("name", "phat");
                startService(intent);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(connection);
            }
        });
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            myBinder.getService().setOnListenerValueChange(new MyService.OnListenValueChange() {
                @Override
                public void onChanged(int i) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvCount.setText("Count " + i);
                        }
                    });
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (isMyServiceRunning(MyService.class)) {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isMyServiceRunning(MyService.class)) {
            unbindService(connection);
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
