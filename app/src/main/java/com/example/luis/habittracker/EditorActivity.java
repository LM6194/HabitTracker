package com.example.luis.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.luis.habittracker.data.HabitContract.DayEntry;
import com.example.luis.habittracker.data.HabitDbHelper;

/**
 * Created by Luis on 10/8/2017.
 */


public class EditorActivity extends AppCompatActivity {
    /** EditText field to enter the restaurant name*/
    private EditText mRestaurant;
    /** EditText field to enter the type of food */
    private EditText mTypeOfFood;
    /** EditText field to enter the cost of the diner*/
    private EditText mCost;
    /** EditText field to enter the rating for the restaurant*/
    private Spinner mRatingSpinner;

    /**
     * Rating of the restaurant. The possible values are:
     * 0 for not good, 1 for good, 2 for excellent.
     */
    private int mRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mRestaurant = (EditText)findViewById(R.id.edit_restaurant_name);
        mTypeOfFood = (EditText)findViewById(R.id.edit_food_type_name);
        mCost = (EditText)findViewById(R.id.edit_cost);
        mRatingSpinner = (Spinner)findViewById(R.id.spinner_rating);
        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select a rating for
     * the restaurant
     */
    private void setupSpinner(){
        //Create adapter for spinner. The list options are from String array it
        //will use the default layout
        ArrayAdapter ratingSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_rating_options, android.R.layout.simple_spinner_item);

        //Specify dropdown layout style - simple list view with 1 item per line
        ratingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        //Apply the adapter to the spinner
        mRatingSpinner.setAdapter(ratingSpinnerAdapter);

        //Set the integer selected to the constant value
        mRatingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)) {
                    if(selection.equals(getString(R.string.not_good))) {
                        mRating = DayEntry.NOT_GOOD; // not good
                    }else if (selection.equals(getString(R.string.good))) {
                        mRating = DayEntry.GOOD; // good
                    }else {
                        mRating = DayEntry.EXCELLENT; // excellent
                    }
                }
            }
            //Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mRating = DayEntry.GOOD;
            }
        });
    }
    /** Get user input from editor and save new restaurant into database*/
    private void insertRestaurant(){
        String nameString = mRestaurant.getText().toString().trim();
        String typeString = mTypeOfFood.getText().toString().trim();
        String costString = mCost.getText().toString().trim();
        int cost = Integer.parseInt(costString);


        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's attributes are the values.
        ContentValues values = new ContentValues();
        values.put(DayEntry.COLUMN_RESTAURANT, nameString);
        values.put(DayEntry.COLUMN_TYPE, typeString);
        values.put(DayEntry.COLUMN_COST, cost);
        values.put(DayEntry.COLUMN_RATING, mRating);
        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the dining table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(DayEntry.TABLE_NAME, null, values);

        if (newRowId == -1){
            Toast.makeText(this, "Error with saving restaurant", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Restaurant saved with row id: "+ newRowId, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu options from the res/menu/menu_editor.xml file.
        //This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                //Save restaurant to the database
                insertRestaurant();
                // The finish will make the editor close and return to
                // the previous activity which is CatalogActivity.
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
