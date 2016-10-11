package edu.orangecoastcollege.cs273.kdo94.todo2day;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {   // Can not make private classes in Java
                                            // public because of sensitive information

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    private static final String DATABASE_NAME = "ToDo2Day"; // Large container that holds multiple tables
    private static final String DATABASE_TABLE = "Tasks"; // Subset of tables of the database
    private static final int DATABASE_VERSION = 1; // If adding new tables, expand fields, update version number


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper (Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

}
