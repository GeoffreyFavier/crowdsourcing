package com.example.gfavier.partie1;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by gfavier on 16/01/18.
 */


public class WiFiJobService extends JobService {

    MaTaskDeScan task1;
    DBHelper dbHelper;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob id=" + params.getJobId());

        dbHelper = new DBHelper(this); //On récupère le base de données

        task1 = new MaTaskDeScan();
        task1.execute(params); //On lance la tâche de scan

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob id=" + params.getJobId());

        boolean shouldReschedule = true;

        return shouldReschedule;
    }

    private class MaTaskDeScan extends AsyncTask {



        @Override
        protected Object doInBackground(Object[] objects) {
            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE); //On récupère l'objet WifiManager

            //Scan des réseaux wifi disponibles
            manager.startScan();
            List<ScanResult> results = manager.getScanResults();

            Log.d(TAG, "on recupere la liste de reseaux");
            writeToDB(results);


            jobFinished((JobParameters) objects[0], true);
            return true;
        }

        //Ecrit les résultats dans la base de données
        public void writeToDB(List<ScanResult> results) {


            SQLiteDatabase db = dbHelper.getWritableDatabase();

            for (ScanResult wifi: results) {
                ContentValues values = new ContentValues();
                values.put(DBContract.DBEntry.COLUMN_NAME_BSSID, wifi.BSSID);
                values.put(DBContract.DBEntry.COLUMN_NAME_SSID, wifi.SSID);
                values.put(DBContract.DBEntry.COLUMN_NAME_TIMESTAMP, System.currentTimeMillis());

                long newRowId = db.insert(DBContract.DBEntry.TABLE_NAME, null, values);
            }
        }
    }
}
