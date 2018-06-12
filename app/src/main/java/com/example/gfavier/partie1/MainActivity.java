package com.example.gfavier.partie1;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    DBHelper dbHelper;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, ForegroundService.class)); //Lancement du service pour la persistance

        //Définition de l'ArrayList pour l'affichage des réseaux
        ArrayList wordList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordList);
        ListView lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(adapter);

        //Bouton pour actualiser l'affichage
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        //Création de la base de données
        dbHelper = new DBHelper(this);

    }

    public void onClick(View v) {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); //On récupère la base de données
        if (v.getId() == R.id.button) {
            adapter.clear(); //On vide l'affichage
            Cursor c = db.rawQuery("SELECT * FROM wifi", null); //On requête tous les résultats de la table
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                Date date = new Date(Long.parseLong( c.getString(c.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_TIMESTAMP)))); //Pour afficher la date
                adapter.add(c.getString(c.getColumnIndexOrThrow(DBContract.DBEntry.COLUMN_NAME_SSID)) + " (" + date.toString() + ")"); //affichage de chaque ligne
                c.moveToNext();
            }
        } else if (v.getId() == R.id.button2) {
            Intent playIntent = new Intent(this, PlotActivity.class);
            startActivity(playIntent);
        }
    }



}
