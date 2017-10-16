package com.example.luis.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Luis on 10/8/2017.
 */

public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract(){}

    /**
     * Inner class that defines constant values for the exercise database table.
     * Each entry in the table represents a single day of exercise.
     */
    public static final class DayEntry implements BaseColumns{

        public static final String TABLE_NAME = "dining";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_RESTAURANT = "restaurant";
        public static final String COLUMN_TYPE = "food";
        public static final String COLUMN_COST = "cost";
        public static final String COLUMN_RATING = "rating";

        /**
         * possible values for having dessert
         */
        public static final int NOT_GOOD = 0;
        public static final int GOOD = 1;
        public static final int EXCELLENT = 2;

    }
}
