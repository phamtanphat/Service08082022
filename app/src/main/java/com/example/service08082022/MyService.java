package com.example.service08082022;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * Created by pphat on 10/26/2022.
 */
public class MyService extends Service {

    // Khi dùng cho bound service
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BBB", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = "";
        if (intent != null) {
           name = intent.getStringExtra("name");
        }
        Toast.makeText(this, "on Start Command, name: " + name, Toast.LENGTH_SHORT).show();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "on Destroy", Toast.LENGTH_SHORT).show();
    }
}
