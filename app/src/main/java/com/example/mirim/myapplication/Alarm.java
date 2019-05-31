package com.example.mirim.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class Alarm extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private SeekBar seekBar;
    private SeekBar timeSeek;
    private Switch sw;
    private Button btn_apply;
    private Vibrator vib;
    private int strength, second;
    Boolean switchstate;
    private MusicIntentReceiver myReceiver;
    String[] ring = { "사이렌", "아이폰 알림","apex","chimes","playtime","silk"};
    int ring_chk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ring);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        timeSeek = (SeekBar)findViewById(R.id.timeSeek);
        sw = (Switch)findViewById(R.id.sw);
        btn_apply = (Button)findViewById(R.id.btn_apply);
        vib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        myReceiver = new MusicIntentReceiver();

        seekBar.setProgress(5);
        timeSeek.setProgress(5);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                Vibration();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ring_chk=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {

                int state = intent.getIntExtra("state", -1);

                        MediaPlayer palyer=MediaPlayer.create(Alarm.this,R.raw.siren);
                        switch(ring_chk){
                            case 0:palyer= MediaPlayer.create(Alarm.this,R.raw.siren);break;
                            case 1:palyer= MediaPlayer.create(Alarm.this,R.raw.signal);break;
                            case 2:palyer= MediaPlayer.create(Alarm.this,R.raw.apex);break;
                            case 3:palyer= MediaPlayer.create(Alarm.this,R.raw.chimes);break;
                            case 4:palyer= MediaPlayer.create(Alarm.this,R.raw.playtime);break;
                            case 5:palyer= MediaPlayer.create(Alarm.this,R.raw.silk);break;
                        }

                        palyer.start();
            }
        }
    }




    public void Vibration(){
        switchstate = sw.isChecked();

        if(switchstate==true) {

            IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
            registerReceiver(myReceiver, filter);

        }

        strength = (int)seekBar.getProgress();
        second = (int)timeSeek.getProgress();

        if(second==1){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 };

            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==2){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==3){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==4){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==5){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==6){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==7){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==8){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400,((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else if(second==9){
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400,((11 - strength) * 300), 600,((11 - strength) * 300),400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else{
            long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400,((11 - strength) * 300), 600,((11 - strength) * 300),400,((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }


        Toast.makeText(Alarm.this,"설정 완료",Toast.LENGTH_LONG).show();
        finish();
    }
}
