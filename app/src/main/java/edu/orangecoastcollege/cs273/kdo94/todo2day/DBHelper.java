package edu.orangecoastcollege.cs273.kdo94.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, AUTO INCREMENT" // Lets the database
                                                // assign the ids and never have duplicates
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

    // Create a method to adda  brand new task to the database:
    public void addTask(Task newTask){
        // Step 1: Create a reference to the database
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2: Make a key-value pair for each  value you want to insert
        ContentValues values = new ContentValues();
        //values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone());

        // Step 3: Insert values into our db
        db.insert(DATABASE_TABLE, null, values);
		
		// Step 4: CLOSE the database
		db.close();
    }
	
	// Create a method to get all the tasks in the database
	public ArrayList<Task> getAllTasks(){
		// Step 1: Create a reference to the database
		SQLiteDatabase db = this.getReadableDatabase();
		
		// Step 2: Make a new empty ArrayList
		ArrayList<Task> allTasks = new ArrayList<>();
		
		// Step 3: Query the database for all records (all rows) and all fields (all columns)
		// The return type of a query is Cursor
		Cursor results = db.query(DATABASE_TABLE, null, null, null, null, null, null);
		
		// Step 4: Loop through the results, create Task objects add to the ArrayList
		if(results.moveToFirst()){
			do{
				int id = results.getInt(0);
				String description = results.getString(1);
				int isDone = results.getInt(2);
				
				allTasks.add(new Task(id, description, isDone));
			} while(results.moveToNext());
		}
		db.close();
		return allTasks;
	}

    public void updateTask(Task existingTask){
        // Step 1: Create a reference to the database
        SQLiteDatabase db = this.getWritableDatabase();

        // Step 2: Make a key-value pair for each  value you want to insert
        ContentValues values = new ContentValues();
        //values.put(KEY_FIELD_ID, newTask.getId());
        values.put(FIELD_DESCRIPTION, existingTask.getDescription());
        values.put(FIELD_IS_DONE, existingTask.getIsDone());

        // Step 3: Edit (UPDATE in SQLite)values into our db
        db.update(DATABASE_TABLE,   // Which table to update
                values, // What values to update as stated above
                KEY_FIELD_ID + "=?",    // Which one to update, null if you want all
                new String[] {String.valueOf(existingTask.getId())});   // Which ids you are updating
                                                                    // null if you want all

        // Step 4: CLOSE the database
        db.close();
    }

    public void deleteAllTasks(){
        // Step 1: Create a reference to the database
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        // whereClause is to restrict what ids you want to update, null if all
        db.close();
    }
}
