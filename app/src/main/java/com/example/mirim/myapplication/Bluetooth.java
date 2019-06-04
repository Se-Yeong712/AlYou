package com.example.mirim.myapplication;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Bluetooth extends Service {

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test","서비스의  onCreate");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test","서비스의  onStartCommand");
            startBluetooth();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopBluetooth();

        Log.d("test","서비스의  onDestroy");
    }

    BluetoothAdapter bAdapter;
    int i = 0;
    Timer timer = new Timer();

    public void startBluetooth(){
        bAdapter = BluetoothAdapter.getDefaultAdapter();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                bAdapter.startDiscovery();
                i++;
                Log.d("test","서비스의  Timer" + i);
            }
        };

        timer.schedule(timerTask,0,5000);

    }

    public void stopBluetooth(){
        timer.cancel();
        Log.d("test","서비스 timer cancel");

    }


}
