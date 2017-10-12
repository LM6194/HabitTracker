package com.example.luis.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.luis.habittracker.data.HabitContract.DayEntry;

/**
 * Created by Luis on 10/8/2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "eating.db";

    /**
     * Database version. If you change the database schema, you must increment the
     * database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link HabitDbHelper}.
     */

    public HabitDbHelper(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the
        // dining out table
        String SQL_CREATE_DINING_TABLE = "CREATE TABLE " + DayEntry.TABLE_NAME + " ("
                + DayEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DayEntry.COLUMN_RESTAURANT + " TEXT NOT NULL, "
                + DayEntry.COLUMN_TYPE + " TEXT, "
                + DayEntry.COLUMN_COST + " REAL NOT NULL, "
                + DayEntry.COLUMN_RATING + " INTEGER NOT NULL);";
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_DINING_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing
        // to be done here.
    }
}
