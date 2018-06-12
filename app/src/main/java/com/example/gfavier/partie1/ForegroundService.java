package com.example.gfavier.partie1;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;

import static android.content.ContentValues.TAG;

public class ForegroundService extends Service {
    public ForegroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Mise en place de la notification
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notif = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("NetMonitor is running")
                .setContentText("Click to open app")
                .setUsesChronometer(true)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .build();

        startForeground(1337, notif);


        scheduleJobWiFi();

    }

    //Lance le job de monitoring
    private void scheduleJobWiFi() {

        ComponentName serviceName = new ComponentName(this, WiFiJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(15, serviceName).setPeriodic(1000).build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, "schedule job avec succes", Toast.LENGTH_LONG).show();
        }
    }


}
