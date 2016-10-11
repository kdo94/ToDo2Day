package edu.orangecoastcollege.cs273.kdo94.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Let's make a DBHelper reference:
        DBHelper db = new DBHelper(this);

        // Let's make a new task and add it to the database
        //Task newTask = new Task(1, "Study for CS273 Midterm", 0);

        //db.addTask(newTask);

        db.addTask(new Task(1, "Study for CS273 Midterm", 0));
        db.addTask(new Task(2, "Go to the gym", 0));
        db.addTask(new Task(3, "Start working on management app for game", 0));
        db.addTask(new Task(4, "Cry a lot", 0));
        db.addTask(new Task(5, "Avoid studying and watch YouTube Videos", 0));
		
		// Let's get all the tasks from database and print with Log.i()
		ArrayList<Task> allTasks = db.getAllTasks();
    }
}
