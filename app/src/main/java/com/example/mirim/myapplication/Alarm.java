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
    String[] ring = { "사이렌", "아이폰 알림"};
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

                switch(state) {

                    case 0: // 헤드셋 해제
                        Toast.makeText(Alarm.this,"이어폰과 연결되어 있지 않습니다",Toast.LENGTH_LONG).show();
                        break;

                    case 1: // 헤드셋 연결

                        MediaPlayer palyer=MediaPlayer.create(Alarm.this,R.raw.siren);
                        switch(ring_chk){
                            case 0:palyer= MediaPlayer.create(Alarm.this,R.raw.siren);break;
                            case 1:palyer= MediaPlayer.create(Alarm.this,R.raw.signal);break;
                        }

                        palyer.start();
                        break;
                }
            }
        }
    }




    public void Vibration(){
        switchstate = sw.isChecked();
        Log.e("fdsf",String.valueOf(switchstate));

        if(switchstate==true) {

            IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
            registerReceiver(myReceiver, filter);

        }

        strength = (int)seekBar.getProgress();
        second = (int)timeSeek.getProgress();
        long[] mVibratePattern = new long[]{((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600,((11 - strength) * 300),400 ,((11 - strength) * 300), 600};
        //pat = Vibtime(strength);
        //VibrationEffect effect = VibrationEffect.createWaveform(pat, -1);
        //VibrationEffect effect = VibrationEffect.createWaveform(pat, VibrationEffect.DEFAULT_AMPLITUDE);


        //vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));

        //vib.vibrate(VibrationEffect.createOneShot(strength*1000, VibrationEffect.DEFAULT_AMPLITUDE));




        //진동 지속시간 설정
        Toast.makeText(Alarm.this,"설정 완료",Toast.LENGTH_LONG).show();
        finish();
    }
}
