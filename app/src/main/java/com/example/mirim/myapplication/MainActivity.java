package com.example.mirim.myapplication;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button town;
    private Button alarm;
    private Button push_alarm;

    Context context;

    private static final int REQUEST_ENABLE_BT = 10;    //블루투스 활성화상태
    private BluetoothAdapter bluetoothAdapter;             //블루투스 어댑터

    //private static final int REQUEST_ENABLE_BT = 3;
    public BluetoothAdapter mBluetoothAdapter = null;
    Set<BluetoothDevice> mDevices;
    int mPairedDeviceCount;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket;
    InputStream mInputStream;
    OutputStream mOutputStream;
    Thread mWorkerThread;
    int readBufferPositon;      //버퍼 내 수신 문자 저장 위치
    byte[] readBuffer;      //수신 버퍼
    byte mDelimiter = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        town = (Button)findViewById(R.id.btn_town);
        alarm = (Button)findViewById(R.id.btn_alarm);
        push_alarm = (Button)findViewById(R.id.push_alarm);

       context = getApplicationContext();


        town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Town.class);
                startActivity(intent);
            }
        });

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,Alarm.class);
                startActivity(intent);
            }
        });

        push_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushAlarm push = new PushAlarm();
                push.pushAlarmAreRing(context);
            }
        });

       checkBlueTooth();


    }




    public void checkBlueTooth() {
        //어댑터 가져오기
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set devices = bluetoothAdapter.getBondedDevices();



        if(bluetoothAdapter == null) {
            // 장치가 블루투스를 지원하지 않음.
            Toast.makeText(this,"어댑터",Toast.LENGTH_LONG).show();
        }

        else {
            // 장치가 블루투스를 지원
            if(!bluetoothAdapter.isEnabled()) {
                //블루투스 비활성상태 -> 활성상태
                Intent btIntent = new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(btIntent, REQUEST_ENABLE_BT);
            }

            else {
                //활성상태
                Toast.makeText(this,"연결 성공",Toast.LENGTH_LONG).show();
                selectDevice();

            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
                    //활성상태 -> 연결
                    Toast.makeText(this,"연결 성공",Toast.LENGTH_LONG).show();
                    selectDevice();
                }
                else if(resultCode == RESULT_CANCELED) {
                    // 블루투스가 비활성상태
                    Toast.makeText(this,"블루투스 비활성",Toast.LENGTH_LONG).show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private void selectDevice() {
        //페어링되었던 기기 목록 획득
        mDevices = bluetoothAdapter.getBondedDevices();
        //페어링되었던 기기 갯수
        mPairedDeviceCount = mDevices.size();
        //Alertdialog 생성(activity에는 context입력)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //AlertDialog 제목 설정
        builder.setTitle("Select device");


        // 페어링 된 블루투스 장치의 이름 목록 작성
        final List<String> listItems = new ArrayList<>();
        for (BluetoothDevice device : mDevices) {
            listItems.add(device.getName());
        }
        if(listItems.size() == 0){
            //no bonded device => searching
            Log.d("Bluetooth", "No bonded device");
        }else{
            Log.d("Bluetooth", "Find bonded device");
            // 취소 항목 추가
            listItems.add("Cancel");

            final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                //각 아이템의 click에 따른 listener를 설정
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Dialog dialog_ = (Dialog) dialog;
                    // 연결할 장치를 선택하지 않고 '취소'를 누른 경우
                    if (which == listItems.size()-1) {
                        Toast.makeText(dialog_.getContext(), "Choose cancel", Toast.LENGTH_SHORT).show();

                    } else {
                        //취소가 아닌 디바이스를 선택한 경우 해당 기기에 연결
                        //connectToSelectedDevice(items[which].toString());
                    }

                }
            });

            builder.setCancelable(false);    // 뒤로 가기 버튼 사용 금지
            AlertDialog alert = builder.create();
            alert.show();   //alert 시작
        }
    }

/*    private void connectToSelectedDevice(final String selectedDeviceName) {
        //블루투스 기기에 연결하는 과정이 시간이 걸리기 때문에 그냥 함수로 수행을 하면 GUI에 영향을 미친다
        //따라서 연결 과정을 thread로 수행하고 thread의 수행 결과를 받아 다음 과정으로 넘어간다.

        //handler는 thread에서 던지는 메세지를 보고 다음 동작을 수행시킨다.
        final Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) // 연결 완료
                {
                    try {
                        //연결이 완료되면 소켓에서 outstream과 inputstream을 얻는다. 블루투스를 통해
                        //데이터를 주고 받는 통로가 된다.
                        mOutputStream = mSocket.getOutputStream();
                        mInputStream = mSocket.getInputStream();
                        // 데이터 수신 준비
                        //beginListenForData();
                        Toast.makeText(context,"디바이스 연결", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {    //연결 실패
                    Toast.makeText(context,"Please check the device", Toast.LENGTH_SHORT).show();
                    try {
                        mSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        //연결과정을 수행할 thread 생성
        Thread thread = new Thread(new Runnable() {
            public void run() {
                //선택된 기기의 이름을 갖는 bluetooth device의 object
                mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
                UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

                try {
                    // 소켓 생성
                    mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
                    // RFCOMM 채널을 통한 연결, socket에 connect하는데 시간이 걸린다. 따라서 ui에 영향을 주지 않기 위해서는
                    // Thread로 연결 과정을 수행해야 한다.
                    mSocket.connect();
                    mHandler.sendEmptyMessage(1);
                } catch (Exception e) {
                    // 블루투스 연결 중 오류 발생
                    mHandler.sendEmptyMessage(-1);
                }
            }
        });

        //연결 thread를 수행한다
        thread.start();
    }

//기기에 저장되어 있는 해당 이름을 갖는 블루투스 디바이스의 bluetoothdevice 객채를 출력하는 함수
//bluetoothdevice객채는 기기의 맥주소뿐만 아니라 다양한 정보를 저장하고 있다.

    BluetoothDevice getDeviceFromBondedList(String name) {
        BluetoothDevice selectedDevice = null;
        mDevices = mBluetoothAdapter.getBondedDevices();
        //pair 목록에서 해당 이름을 갖는 기기 검색, 찾으면 해당 device 출력
        for (BluetoothDevice device : mDevices) {
            if (name.equals(device.getName())) {
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }*/





}
