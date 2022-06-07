package com.example.coursescheduler.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coursescheduler.R;

public class AddEditInstructorActivity extends AppCompatActivity {

    public static final String EXTRA_INSTRUCTOR_NAME =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_NAME";
    public static final String EXTRA_EMAIL =
            "com.example.coursescheduler.EXTRA_EMAIL";
    public static final String EXTRA_PHONE =
            "com.example.coursescheduler.EXTRA_PHONE";
    public static final String EXTRA_INSTRUCTOR_ID =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_ID";
    public static final String EXTRA_INSTRUCTOR_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_ID_DISPLAY";
    public static final String EXTRA_INSTRUCTOR_COURSE_ID =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_COURSE_ID";
    public static final String EXTRA_INSTRUCTOR_COURSE_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_COURSE_ID_DISPLAY";

    private EditText editInstructorName;
    private EditText editEmail;
    private EditText editPhone;
    private TextView instructorCourseID;
    private int instructorCourseIDLocal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editInstructorName = findViewById(R.id.editInstructorName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        instructorCourseID = findViewById(R.id.instructorCourseID);



        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Intent
        Intent data = getIntent();

        // Select Label
        if (data.hasExtra(EXTRA_INSTRUCTOR_ID)) {
            setTitle("Edit Instructor");
            instructorCourseID.setText(String.valueOf(instructorCourseIDLocal));
            editInstructorName.setText(data.getStringExtra(EXTRA_INSTRUCTOR_NAME));
            editEmail.setText(data.getStringExtra(EXTRA_EMAIL));
            editPhone.setText(data.getStringExtra(EXTRA_PHONE));
        } else {
            setTitle("Add Instructor");
            instructorCourseID.setText(String.valueOf(instructorCourseIDLocal));
        }
    }

    public void saveInstructor() {
        int instructorCourseID = instructorCourseIDLocal;
        String instructorName = editInstructorName.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();

        if (instructorName.trim().isEmpty() || email.trim().isEmpty() || phone.trim().isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_INSTRUCTOR_NAME, instructorName);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_PHONE, phone);
        data.putExtra(EXTRA_INSTRUCTOR_COURSE_ID, instructorCourseID);

        int id = getIntent().getIntExtra(EXTRA_INSTRUCTOR_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_INSTRUCTOR_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_instructors_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_instructor:
                saveInstructor();
                return true;
        }return super.onOptionsItemSelected(item);
    }
}
