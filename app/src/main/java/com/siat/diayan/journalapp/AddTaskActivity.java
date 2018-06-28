package com.siat.diayan.journalapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.siat.diayan.journalapp.database.JournalDatabase;
import com.siat.diayan.journalapp.database.TaskEntry;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    public static final String INSTANCE_TASK_ID = "instanceTaskId";

    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    private int mTaskId = DEFAULT_TASK_ID;

    private JournalDatabase mDb;

    EditText mEditext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        mDb = JournalDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)){
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);

            AddTaskViewModelFactory factory = new AddTaskViewModelFactory(mDb, mTaskId);

            final AddTaskViewModel viewModel
                    = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

            viewModel.getTask().observe(this, new Observer<TaskEntry>() {
                @Override
                public void onChanged(@Nullable TaskEntry taskEntry) {
                    viewModel.getTask().removeObserver(this);
                    populateUI(taskEntry);
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INSTANCE_TASK_ID, String.valueOf(mTaskId));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_save:
                saveTask();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initViews(){

        mEditext = (EditText) findViewById(R.id.ed_inputText);
    }

    //populate the ui
    private void populateUI(TaskEntry task){

        if (task == null){
            return;
        }

        mEditext.setText(task.getTaskDescription());
    }



    private void saveTask(){

        String description = mEditext.getText().toString();

        Date date = new Date();

        final TaskEntry task = new TaskEntry(description, date, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mTaskId == DEFAULT_TASK_ID) {
                    // insert new task
                    mDb.taskDao().insertTask(task);
                } else {
                    //update task
                    task.setId(mTaskId);
                    mDb.taskDao().updateTask(task);
                }
                finish();
            }

        });

    }
}
