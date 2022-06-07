package com.example.coursescheduler.UI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity  {

    public static final String EXTRA_NOTE_TITLE =
            "com.example.coursescheduler.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_BODY =
            "com.example.coursescheduler.EXTRA_NOTE_BODY";
    public static final String EXTRA_NOTE_ID =
            "com.example.coursescheduler.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_NOTE_ID_DISPLAY";
    public static final String EXTRA_NOTE_COURSE_ID =
            "com.example.coursescheduler.EXTRA_NOTE_COURSE_ID";
    public static final String EXTRA_NOTE_COURSE_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_NOTE_COURSE_ID_DISPLAY";

    private TextView editNoteID;
    private TextView editCourseID;
    private EditText editNoteTitle;
    private EditText editNoteComment;
    private int noteCourseIDLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNoteID = findViewById(R.id.edit_note_ID);
        editCourseID = findViewById(R.id.edit_Course_Note_ID);
        editNoteTitle = findViewById(R.id.edit_text_noteTitle);
        editNoteComment = findViewById(R.id.edit_text_noteComment);




        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //Intent
        Intent data = getIntent();

        // Select Label
        if(data.hasExtra(EXTRA_NOTE_ID)) {
            setTitle("Edit Note");
            editCourseID.setText(String.valueOf(noteCourseIDLocal));
            editNoteID.setText(data.getStringExtra(EXTRA_NOTE_ID_DISPLAY));
            editNoteTitle.setText(data.getStringExtra(EXTRA_NOTE_TITLE));
            editNoteComment.setText(data.getStringExtra(EXTRA_NOTE_BODY));
        } else {
            setTitle("Add Note");
            editCourseID.setText(String.valueOf(noteCourseIDLocal));

        }

    }

    private void saveNote() {
        String noteCourseID = editCourseID.getText().toString();
        String noteTitle = editNoteTitle.getText().toString();
        String noteComment = editNoteComment.getText().toString();



        if (noteTitle.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NOTE_TITLE, noteTitle);
        data.putExtra(EXTRA_NOTE_BODY, noteComment);
        data.putExtra(EXTRA_NOTE_COURSE_ID, noteCourseID);

        int id = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_NOTE_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_notes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String nTitle = editNoteTitle.getText().toString();
                String nBody = editNoteComment.getText().toString();
                sendIntent.putExtra(Intent.EXTRA_TEXT, nBody);
                sendIntent.putExtra(Intent.EXTRA_TITLE, nTitle);
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
