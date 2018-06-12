package com.example.gfavier.partie1;

import android.provider.BaseColumns;

/**
 * Created by gfavier on 18/01/18.
 */

public final class DBContract {

    private DBContract() {}

    //Définit les colonnes de la base de données
    public static class DBEntry implements BaseColumns {
        public static final String TABLE_NAME = "wifi";
        public static final String COLUMN_NAME_SSID = "ssid";
        public static final String COLUMN_NAME_BSSID = "bssid";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";

    }
}
