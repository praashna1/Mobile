package com.example.todo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ToDoDatabaseHelper dbHelper;
    private ListView listView;
    private EditText editTextTask;
    private Button buttonAdd;

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new ToDoDatabaseHelper(this);
        listView = findViewById(R.id.listView);
        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);

        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        listView.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showUpdateDeleteDialog(position);
            }
        });

        loadTasks();
    }

    private void addTask() {
        String task = editTextTask.getText().toString().trim();
        if (task.isEmpty()) {
            Toast.makeText(this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoDatabaseHelper.COLUMN_TASK, task);
        db.insert(ToDoDatabaseHelper.TABLE_TODO, null, values);
        db.close();

        editTextTask.setText("");
        loadTasks();
    }

    private void loadTasks() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ToDoDatabaseHelper.TABLE_TODO, null, null, null, null, null, null);

        taskList.clear();
        while (cursor.moveToNext()) {
            String task = cursor.getString(cursor.getColumnIndex(ToDoDatabaseHelper.COLUMN_TASK));
            taskList.add(task);
        }
        cursor.close();
        db.close();

        adapter.notifyDataSetChanged();
    }

    private void showUpdateDeleteDialog(final int position) {
        final String task = taskList.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an action")
                .setItems(new String[]{"Update", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            showUpdateDialog(position, task);
                        } else {
                            deleteTask(position);
                        }
                    }
                });
        builder.create().show();
    }

    private void showUpdateDialog(final int position, String oldTask) {
        final EditText editText = new EditText(this);
        editText.setText(oldTask);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Task")
                .setView(editText)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTask = editText.getText().toString().trim();
                        if (newTask.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        updateTask(position, newTask);
                    }
                })
                .setNegativeButton("Cancel", null);
        builder.create().show();
    }

    private void updateTask(int position, String newTask) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToDoDatabaseHelper.COLUMN_TASK, newTask);

        String selection = ToDoDatabaseHelper.COLUMN_TASK + " = ?";
        String[] selectionArgs = { taskList.get(position) };

        db.update(ToDoDatabaseHelper.TABLE_TODO, values, selection, selectionArgs);
        db.close();

        loadTasks();
    }

    private void deleteTask(int position) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String task = taskList.get(position);

        String selection = ToDoDatabaseHelper.COLUMN_TASK + " = ?";
        String[] selectionArgs = { task };

        db.delete(ToDoDatabaseHelper.TABLE_TODO, selection, selectionArgs);
        db.close();

        loadTasks();
    }
}
