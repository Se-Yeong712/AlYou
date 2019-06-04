package com.example.mirim.myapplication;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Message;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button town;
    private Button alarm;
    private Button start;
    private Button stop;


    private int strength=0;
    private  int second=0;
    private Vibrator vib;
    Context context;
    ArrayAdapter<Object> mArrayAdapter;

    private static final int REQUEST_ENABLE_BT = 10;       //블루투스 활성상태

    BluetoothAdapter bluetoothAdapter;
    private final int PERMISSIONS_REQUEST =1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        Intent intent = getIntent();*/
/*        strength = intent.getExtras().getInt("strength");
        second = intent.getExtras().getInt("second");*/

        town = (Button) findViewById(R.id.btn_town);
        alarm = (Button) findViewById(R.id.btn_alarm);
        start = (Button) findViewById(R.id.btn_start);
        stop = (Button) findViewById(R.id.btn_stop);


        context = getApplicationContext();

        int permssionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (permssionCheck != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "권한 승인이 필요합니다", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "사용을 위해 위치 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_REQUEST);
                Toast.makeText(this, "사용을 위해 위치 권한이 필요합니다.", Toast.LENGTH_LONG).show();

            }
        }


        town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Town.class);
                startActivity(intent);
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Alarm.class);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               Intent intentService = new Intent(getApplicationContext(),Bluetooth.class);
              startService(intentService);

                    CheckBluetooth();


                }
            });

            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intentService = new Intent(getApplicationContext(), Bluetooth.class);
                    stopService(intentService);
                }
            });


        registerReceiver(receiver,new IntentFilter(BluetoothDevice.ACTION_FOUND));


    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, final Intent intent) {
            Log.d("test","서비스 onReceive");

            //Toast.makeText(getApplicationContext(),"onReceive", Toast.LENGTH_LONG).show();
            //String action = intent.getAction();
            final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String device_name = device.getName();



            if(device_name != null && device_name.length() > 4){
                Log.d("Bluetooth Name: ", device_name);
                if(device_name.substring(0,3).equals("Gal")){
                    Log.d("test","서비스 Galaxy if문 안");
                    //bluetooth_device.add(device);
                    getRssi(context,intent);


                }
            }

        }

    };


    public void getRssi(Context context, final Intent intent){
        final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        int rssi = intent.getShortExtra(device.EXTRA_RSSI, Short.MIN_VALUE);
        Log.d("test","서비스 name : " + device.getName() + "  RSSI: " + rssi + "dBm");

        if (rssi > -60) {

                try {
                    rssi = intent.getShortExtra(device.EXTRA_RSSI, Short.MIN_VALUE);
                    Log.d("test","서비스 name : " + device.getName() + "  RSSI: " + rssi + "dBm");
                    Thread.sleep(10000);
                    rssi = intent.getShortExtra(device.EXTRA_RSSI, Short.MIN_VALUE);
                    Log.d("test","서비스 name : " + device.getName() + "  RSSI: " + rssi + "dBm");
                    Toast.makeText(getApplicationContext(), "name : " + device.getName() + "  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();

            }


            PushAlarm push = new PushAlarm();
            push.pushAlarmAreRing(getApplicationContext());
        }
    }

        /*TimerTask Ttask = new TimerTask() {
            @Override
            public void run() {
                int rssi = intent.getShortExtra(device.EXTRA_RSSI, Short.MIN_VALUE);
                Toast.makeText(getApplicationContext(),"name : " + device.getName() +  "  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();

                if(rssi > -60){

                PushAlarm push = new PushAlarm();
                push.pushAlarmAreRing(getApplicationContext());


                }

            }
        };
        Timer timer1 = new Timer();
        timer1.schedule(Ttask,0,5000);*/


        //if(device.getName().equalsIgnoreCase("Galaxy J5 (2016)")){



        //}



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this,"승인이 허가되어 있습니다.",Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this,"아직 승인받지 않았습니다.",Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }


    private void CheckBluetooth(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter ==  null){
            //장치가 블루투스를 지원하지 않을 때
            Toast.makeText(this,"장치를 지원하지 않음",Toast.LENGTH_LONG).show();

        }
        else{
            //장치가 블루투스를 지원
            if(bluetoothAdapter.isEnabled()){
                //블루투스 활성상태
                Toast.makeText(this,"블루투스 활성상태입니다",Toast.LENGTH_LONG).show();

            }
            else{
                //블루투스 비활성상태
                Intent btIntent = new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(btIntent, REQUEST_ENABLE_BT);
            }

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
                    //활성상태 -> 연결
                    Toast.makeText(this,"블루투스 활성상태",Toast.LENGTH_LONG).show();
                }
                else if(resultCode == RESULT_CANCELED) {
                    // 블루투스가 비활성상태
                    Toast.makeText(this,"블루투스 비활성상태",Toast.LENGTH_LONG).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }










}
