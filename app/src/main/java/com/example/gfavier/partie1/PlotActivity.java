package com.example.gfavier.partie1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import java.util.Arrays;

/**
 * Created by gfavier on 19/01/18.
 */

public class PlotActivity extends AppCompatActivity {

    DBHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        XYPlot plot = (XYPlot) findViewById(R.id.plot);

        dbhelper = new DBHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT COUNT(bssid), timestamp FROM Wifi GROUP BY timestamp ORDER BY timestamp", null);

        if (c.getCount()>0) {
            Integer[] ids = new Integer[c.getCount()];
            Integer[] times = new Integer[c.getCount()];
            Integer[] nbAps = new Integer[c.getCount()];
            c.moveToFirst();

            for (int i=0 ; i < c.getCount() ; i++) {
                ids[i] = i;
                times[i] = c.getInt(1);
                nbAps[i] = c.getInt(0);
                c.moveToNext();
            }

            XYSeries apsXY = new SimpleXYSeries(Arrays.asList(nbAps), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Nb Aps");

            LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.RED, Color.BLUE, null, null);

            plot.addSeries(apsXY, series1Format);

        } else {
            Log.d("Plot", "No item in result query");
        }
    }
}
