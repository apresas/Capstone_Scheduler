package com.example.coursescheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Instructor;
import com.example.coursescheduler.Entity.Note;
//import com.example.coursescheduler.Entity.ScheduledCourse;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;
    public EditText editUsername;
    public EditText editPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        editUsername = findViewById(R.id.emailField);
        editPassword = findViewById(R.id.password_field);

    }

    public void enterHere(View view) {
        String userPassword = editPassword.getText().toString();
        String userUsername = editUsername.getText().toString();
        if (userPassword.equals("password") && userUsername.equals("username")) {
            Intent intent = new Intent(MainActivity.this, TermActivity.class);
            startActivity(intent);
            editUsername.setText("");
            editPassword.setText("");
        } else if (!"password".equals(userPassword)) {
            Toast.makeText(MainActivity.this, "Incorrect password. Please input the correct password.", Toast.LENGTH_SHORT).show();
        } else if (!"username".equals(userUsername)){
            Toast.makeText(MainActivity.this, "Incorrect Username. Please enter the correct Username.", Toast.LENGTH_SHORT).show();
        }
        else if (!"username".equals(userUsername) && !"password".equals(userPassword)) {
            Toast.makeText(MainActivity.this, "Please Enter Valid Username and Password", Toast.LENGTH_SHORT).show();
        }


//        ScheduleRepo repo = new ScheduleRepo(getApplication());
//        ScheduledCourse sc = new ScheduledCourse("Software 1", "01/01/22", "06/01/22", 0, 1);
//        ScheduledCourse sc1 = new ScheduledCourse("Software 2", "01/01/22", "06/01/22", 0, 2);
//        ScheduledCourse sc2 = new ScheduledCourse("Web Development", "01/01/22", "06/01/22", 0, 3);
//        ScheduledCourse sc3 = new ScheduledCourse("Data Management", "01/01/22", "06/01/22", 0, 4);
//        ScheduledCourse sc4 = new ScheduledCourse("Scripting and Programming", "01/01/22", "06/01/22", 0, 5);
//        ScheduledCourse sc5 = new ScheduledCourse("UI Design", "01/01/22", "06/01/22", 0, 6);
//        ScheduledCourse sc6 = new ScheduledCourse("Mobile App Development", "01/01/22", "06/01/22", 0, 7);
//
//
//        repo.insertScheduledCourse(sc);
//        int software = sc.getCourseID();
//        repo.insertScheduledCourse(sc1);
//        int software2 = sc.getCourseID();
//        repo.insertScheduledCourse(sc2);
//        int web = sc.getCourseID();
//        repo.insertScheduledCourse(sc3);
//        int data = sc.getCourseID();
//        repo.insertScheduledCourse(sc4);
//        repo.insertScheduledCourse(sc5);
//        repo.insertScheduledCourse(sc6);


//        ScheduleRepo repo = new ScheduleRepo(getApplication());
//
//        Assessment assessment = new Assessment("Database Project", "Performance","01/01/22", "05/30/22", 1, 1);
//        Assessment assessment1 = new Assessment("Mobile App Project", "Performance","06/01/22", "06/30/22", 7,2);
//        Assessment assessment2 = new Assessment("Software Test", "Objective","07/01/22", "07/30/22", 5, 3);
//        Assessment assessment3 = new Assessment("Design Plan", "Objective","07/01/22", "07/30/22", 6, 4);
//        Assessment assessment4 = new Assessment("Inventory App", "Performance","07/01/22", "07/30/22", 2, 5);
//        Assessment assessment5 = new Assessment("Database Test", "Objective","07/01/22", "07/30/22", 4, 6);
//        Assessment assessment6 = new Assessment("Website Project", "Performance","07/01/22", "07/30/22", 3, 7);
//        repo.insertAssessment(assessment);
//        repo.insertAssessment(assessment1);
//        repo.insertAssessment(assessment2);
//        repo.insertAssessment(assessment3);
//        repo.insertAssessment(assessment4);
//        repo.insertAssessment(assessment5);
//        repo.insertAssessment(assessment6);
//
//
//        Term term = new Term( "Term 1", "01/01/22", "05/30/22");
//        Term term1 = new Term("Term 2", "06/01/22", "01/01/23");
//        Term term2 = new Term( "Spring Term", "03/01/22", "10/01/22");
//        repo.insertTerm(term);
//        repo.insertTerm(term1);
//        repo.insertTerm(term2);
//
//        Course course = new Course("Software 1", "03/01/22", "04/03/22", 0, 1, "John Doe");
//        Course course1 = new Course("Software 2", "01/01/22", "06/01/22", 0, 2, "Robert Roy");
//        Course course2 = new Course("Web Development", "01/01/22", "06/01/22", 0, 3, "James Jameson");
//        Course course3 = new Course("Data Management", "01/01/22", "06/01/22", 0, 4, "Mike Michelson");
//        Course course4 = new Course("Scripting and Programming", "01/01/22", "06/01/22", 0, 5, "Mary Mountain");
//        Course course5 = new Course("UI Design", "01/01/22", "06/01/22", 0, 6, "Patricia Pearson");
//        Course course6 = new Course("Mobile App Development", "01/01/22", "06/01/22", 0, 7, "Jennifer Johansen");
//        repo.insertCourse(course);
//        repo.insertCourse(course1);
//        repo.insertCourse(course2);
//        repo.insertCourse(course3);
//        repo.insertCourse(course4);
//        repo.insertCourse(course5);
//        repo.insertCourse(course6);


//
//        Note note = new Note("Test Title 1", "Test Message Body", 1);
//        Note note1 = new Note("Test Title 2", "Test Message Body", 2);
//        Note note2 = new Note("Test Title 3", "Test Message Body", 3);
//        repo.insertNote(note);
//        repo.insertNote(note1);
//        repo.insertNote(note2);
//
//        Instructor instructor = new Instructor("John Doe", "j.doe@email.com", "222-222-2222", 1);
//        Instructor instructor1 = new Instructor("Jane Doe", "jdoe@email.com", "222-222-2223", 2);
//        Instructor instructor2 = new Instructor("Tim Tom", "t.tom@email.com", "222-222-2224", 3);
//        repo.insertInstructor(instructor);
//        repo.insertInstructor(instructor1);
//        repo.insertInstructor(instructor2);






    }

}