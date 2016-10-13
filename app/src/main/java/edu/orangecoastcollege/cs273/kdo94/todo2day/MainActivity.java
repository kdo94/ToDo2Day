package edu.orangecoastcollege.cs273.kdo94.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper database;
    private List<Task> taskList;
    private TaskListAdapter taskListAdapter;
    private EditText taskEditText;
    private ListView taskListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Let's make a DBHelper reference:
        database = new DBHelper(this);

        // Add one dummy task
        //database.addTask(new Task("Dummy Task", 1));

        // Fill the list with tasks from the database
        taskList = database.getAllTasks();

        // Create the custom task list adapter
        // ( Want to associate the adapter with the context, the layout, and the List )
        taskListAdapter = new TaskListAdapter(this, R.layout.task_item, taskList);

        // Connect the ListView with out layout
        taskListView = (ListView) findViewById(R.id.taskListView);

        //Associate the adapter with the ListView
        taskListView.setAdapter(taskListAdapter);

        // Connect the edit text with the layout
        taskEditText = (EditText) findViewById(R.id.taskEditText);
    }

    public void addTask(View view){
        String description = taskEditText.getText().toString();
        if(description.isEmpty()){
            Toast.makeText(this, "Task description cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else{
            Task newTask = new Task(description, 0);
//            // Add the Task to the List
//            taskList.add(newTask);
            // Can do the one below or the one above, both will add two to the database
            // Add Task to the ListAdapter
            taskListAdapter.add(newTask);

            // Add task to the database
            database.addTask(newTask);

            // Clear out the EditText
            taskEditText.setText("");
        }
    }

    public void changeTaskStatus(View view){
        // Checks to see if something is something else
        if (view instanceof  CheckBox) {
            CheckBox selectedCheckBox = (CheckBox) view;
            Task selectedTask = (Task) selectedCheckBox.getTag();
            selectedTask.setIsDone(selectedCheckBox.isChecked() ? 1 : 0);
            // Update in the database
            database.updateTask(selectedTask);
            // No need to update the ListView because the user did the action
        }
    }

    public void clearAllTasks(View view){
        // Clear the list
        taskList.clear();
        // Delete all the records (Tasks) in the database
        database.deleteAllTasks();
        // Tell the TaskListAdapter to update itself
        taskListAdapter.notifyDataSetChanged();
    }
}
