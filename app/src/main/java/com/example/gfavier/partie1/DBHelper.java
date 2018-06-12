package com.example.gfavier.partie1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gfavier on 18/01/18.
 */

public class DBHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Wifi.db";

    //Requête de création de la table
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + DBContract.DBEntry.TABLE_NAME + " (" +
            DBContract.DBEntry._ID + " INTEGER PRIMARY KEY," +
            DBContract.DBEntry.COLUMN_NAME_BSSID + " TEXT," +
            DBContract.DBEntry.COLUMN_NAME_SSID + " TEXT," +
            DBContract.DBEntry.COLUMN_NAME_TIMESTAMP + " INT)";

    //Requête de suppression de la table
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContract.DBEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
