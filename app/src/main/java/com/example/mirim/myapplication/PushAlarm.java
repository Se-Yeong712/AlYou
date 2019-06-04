package com.example.mirim.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class PushAlarm {
    private String CHANNEL_ID = "1";
    private int strength=0;
    private  int second=0;
    private Vibrator vib;
    long[] mVibratePattern = new long[]{((11 - 5) * 300),400 };
    private Context mContext;

    public void pushAlarmAreRing(Context context){ //알람 함수
        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("ALYOU")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentText("반경 10M안에 신호등이 있습니다.");
        //System.out.println("알람이 울려야 하는데");


/*        MediaPlayer palyer= MediaPlayer.create(context,R.raw.siren);
        palyer.start();*/



        Vibration(context);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());


    }

    public void Vibration(Context context){

        this.mContext = context;
        SharedPreferences sf = mContext.getSharedPreferences("appData",Context.MODE_PRIVATE);
        strength = sf.getInt("strength",1);
        second = sf.getInt("second",1);

        //Toast.makeText(mContext,"11만 아니면 돼 : "+strength + second, Toast.LENGTH_SHORT).show();


        vib = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);


        if (second == 1) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400};

            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 2) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 3) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 4) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 5) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 6) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 7) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 8) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else if (second == 9) {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            long[] mVibratePattern = new long[]{((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600, ((11 - strength) * 300), 400, ((11 - strength) * 300), 600};
            vib.vibrate(VibrationEffect.createWaveform(mVibratePattern, VibrationEffect.DEFAULT_AMPLITUDE));
        }


    }



    private void createNotificationChannel(Context con){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alyou";
            String description = "조심하세요!(임의로준거임)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            Context context;
            context = con;
            NotificationManager notificationManager = con.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
