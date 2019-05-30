package com.example.mirim.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class PushAlarm {
    private String CHANNEL_ID = "1";

    public void pushAlarmAreRing(Context context){ //알람 함수
        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("ALYOU")
                .setContentText("반경 10M안에 신호등이 있습니다.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //System.out.println("알람이 울려야 하는데");


        MediaPlayer palyer= MediaPlayer.create(context,R.raw.siren);
        palyer.start();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());


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
