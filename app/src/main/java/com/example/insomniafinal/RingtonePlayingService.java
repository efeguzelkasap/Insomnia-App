package com.example.insomniafinal;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // fetch the extra string from the alarm on/alarm off values
        String state = intent.getExtras().getString("extra");
        // Get choice of alarm sound
        Integer sound = intent.getExtras().getInt("sound");

        // set up the notification service
        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // set up an intent that goes to the Main Activity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        // set up a pending intent
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        // make the notification parameters
        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true)
                .build();




        // this converts the extra strings from the intent
        // to start IDs, values 0 or 1
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        // if there is no music playing, and the user pressed "alarm on"
        // music should start playing
        if (!this.isRunning && startId == 1) {

            this.isRunning = true;
            this.startId = 0;

            // set up the start command for the notification
            notify_manager.notify(0, notification_popup);



            //Play sound based on the users pick.

            if (sound == 0) {
                // play a randomly picked audio file if user did not select sound.

                int minimum_number = 1;
                int maximum_number = 13;

                Random random_number = new Random();
                int number = random_number.nextInt(maximum_number + minimum_number);

                if (number == 1) {
                    // create an instance of the media player
                    media_song = MediaPlayer.create(this, R.raw.ocean);
                    // start the ringtone
                    media_song.start();
                }
                else if (number == 2) {
                    media_song = MediaPlayer.create(this, R.raw.birds);
                    media_song.start();
                }
                else if (number == 3) {
                    media_song = MediaPlayer.create(this, R.raw.rainstorm);
                    media_song.start();
                }
                else {
                    media_song = MediaPlayer.create(this, R.raw.rainstorm);
                    media_song.start();
                }
            }
            else if (sound == 1) {
                // create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.ocean);
                // start the ringtone
                media_song.start();
            }
            else if (sound == 2) {
                // create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.birds);
                // start the ringtone
                media_song.start();
            }
            else if (sound == 3) {
                media_song = MediaPlayer.create(this, R.raw.rainstorm);
                media_song.start();
            }
            else {
                media_song = MediaPlayer.create(this, R.raw.rainstorm);
                media_song.start();
            }

        }

        // if there is music playing, and the user pressed "alarm off"
        // music should stop playing
        else if (this.isRunning && startId == 0) {

            // stop the ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        // these are if the user presses random buttons
        // just to bug-proof the app
        // if there is no music playing, and the user pressed "alarm off"
        // do nothing
        else if (!this.isRunning && startId == 0) {

            this.isRunning = false;
            this.startId = 0;

        }

        // if there is music playing and the user pressed "alarm on"
        // do nothing
        else if (this.isRunning && startId == 1) {

            this.isRunning = true;
            this.startId = 1;

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }



}