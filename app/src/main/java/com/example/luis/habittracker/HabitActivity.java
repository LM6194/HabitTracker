package com.example.luis.habittracker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.luis.habittracker.data.HabitContract.DayEntry;
import com.example.luis.habittracker.data.HabitDbHelper;

public class HabitActivity extends AppCompatActivity {
    /** Database helper that will provide us access to the database*/
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        //To access our database, we instantiate our subclass of
        //SQLiteOpenHelper and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);
    }
    /**
     * This method will help to update the main activity xml
     * after the input were edited
     */
    @Override
    protected void onStart(){
        super.onStart();
        queryAllHabits();
    }
    /**
     * Temporary helper method to display information in the onscreen TextView
     * about the state of the exercise database.
     */
    public Cursor queryAllHabits(){
        //Create and/ or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which column from the database
        // you will actually use after this query.
        String[] projection = {
                DayEntry._ID,
                DayEntry.COLUMN_RESTAURANT,
                DayEntry.COLUMN_TYPE,
                DayEntry.COLUMN_COST,
                DayEntry.COLUMN_RATING
        };
        Cursor cursor = db.query(DayEntry.TABLE_NAME,projection,
                null,null,null,null,null);


        // Find the TextView to display the data of the database
        TextView displayView = (TextView) findViewById(R.id.text_habit);

        try{
            // Create a header in the Text View that looks like this;
            //
            //The dining table contain <number of rows in Cursor>that I dining out.
            // _id - restaurant - type of food - cost - rating
            //
            //In the while loop below, iterate through the rows of the cursor
            //and display the information from each column in this order.
            displayView.setText("The dining out table contains " + cursor.getCount() + " days.\n\n");
            displayView.append(DayEntry._ID + " - " + DayEntry.COLUMN_RESTAURANT + " - "
            + DayEntry.COLUMN_TYPE + " - " + DayEntry.COLUMN_COST + " - " + DayEntry.COLUMN_RATING +"\n");

            // figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(DayEntry._ID);
            int restaurantColumnIndex = cursor.getColumnIndex(DayEntry.COLUMN_RESTAURANT);
            int typeColumnIndex = cursor.getColumnIndex(DayEntry.COLUMN_TYPE);
            int costColumnIndex = cursor.getColumnIndex(DayEntry.COLUMN_COST);
            int ratingColumnIndex = cursor.getColumnIndex(DayEntry.COLUMN_RATING);

            //Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()){
                // use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentRestaurant = cursor.getString(restaurantColumnIndex);
                String currentFoodType = cursor.getString(typeColumnIndex);
                int currentCost = cursor.getInt(costColumnIndex);
                int currentRating = cursor.getInt(ratingColumnIndex);
                //Display the values from each column of the current row in the cursor
                // in the TextView
                displayView.append("\n" + currentID + " - " + currentRestaurant + " - " +
                currentFoodType + " - " + currentCost + " - " + currentRating);
            }
        }finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
        return cursor;
    }
}
