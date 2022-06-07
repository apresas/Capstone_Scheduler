package com.example.coursescheduler.UI;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Instructor;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.Entity.ScheduledCourse;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditCourseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_COURSE_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_COURSE_ID_DISPLAY";
    public static final String EXTRA_COURSE_ID =
            "com.example.coursescheduler.EXTRA_ID";
    public static final String EXTRA_TERM_ID =
            "com.example.coursescheduler.EXTRA_TERM_ID";
    public static final String EXTRA_TITLE =
            "com.example.coursescheduler.EXTRA_TITLE";
    public static final String EXTRA_INSTRUCTOR =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR";
    public static final String EXTRA_INSTRUCTOR_POS =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_POS";
    public static final String EXTRA_STATUS =
            "com.example.coursescheduler.EXTRA_STATUS";
    public static final String EXTRA_STATUS_POS =
            "com.example.coursescheduler.EXTRA_STATUS_POS";
    public static final String EXTRA_START =
            "com.example.coursescheduler.EXTRA_START";
    public static final String EXTRA_END =
            "com.example.coursescheduler.EXTRA_END";
    public static final String SELECTED_COURSE =
            "com.example.coursescheduler.EXTRA_COURSE_ID_DISPLAY";


    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;
    final Calendar calendarStart = Calendar.getInstance();
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private AssessmentViewModel assessmentViewModel;
    CourseViewModel courseViewModel;

    private TextView editTermID;
    private TextInputEditText editCourseID;
    private TextInputEditText courseTitle;
    private TextInputEditText startDate;
    private TextInputEditText endDate;
    static int courseTermID;
    ScheduledCourseAdapter.OnItemClickListener listener;
    AddEditTermActivity addEditTermActivity;



    private static final String TAG = "CourseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTermID = findViewById(R.id.edit_text_termID_holder);
        editCourseID = findViewById(R.id.courseID_input);
        courseTitle = findViewById(R.id.title_input);
        startDate = findViewById(R.id.start_input);
        endDate = findViewById(R.id.end_input);
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);

//        if (getIntent().hasExtra(SELECTED_COURSE)){
//        ScheduledCourse scCourse = getIntent().getParcelableExtra("SELECTED_COURSE");
//            Log.d(TAG, "onCreate: " + scCourse. toString());
//    }




        // Floating Button
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_assessment);

        // Go to AddEditCourse
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(AddEditCourseActivity.this, AddEditAssessmentActivity.class);
                String courseID = editCourseID.getText().toString();
                intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, courseID);
                activityResultLauncher.launch(intent);

            }
        });



        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Adapter
        final AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);

        // View Model
        assessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        assessmentViewModel.getAllAssignedAssessments(getIntent().getIntExtra(EXTRA_COURSE_ID, -1)).observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });

        // Touch helper
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.delete(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AddEditCourseActivity.this, "Course Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        // OnClick Fill Form
        adapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
                Intent intent = new Intent(AddEditCourseActivity.this, AddEditAssessmentActivity.class);
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID_DISPLAY, String.valueOf(assessment.getAssessmentID()));
                intent.putExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, assessment.getAssessmentID());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID, String.valueOf(assessment.getCourseID()));
                intent.putExtra(AddEditAssessmentActivity.EXTRA_TITLE, assessment.getAssessmentTitle());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_TYPE, assessment.getAssessmentType());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_TYPE_POSITION, AddEditAssessmentActivity.typePosition);
                intent.putExtra(AddEditAssessmentActivity.EXTRA_START, assessment.getStartDate());
                intent.putExtra(AddEditAssessmentActivity.EXTRA_END, assessment.getEndDate());

                activityUpdateResultLauncher.launch(intent);

            }
        });


        // Start Date onClick
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date;
                String info = startDate.getText().toString();
                if (info.equals(""))info = "03/03/22";
                try {
                    calendarStart.setTime(sdf.parse(info));
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddEditCourseActivity.this, startDP, calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // End Date onClick
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = endDate.getText().toString();
                if (info.equals(""))info = "03/04/22";
                try {
                    calendarStart.setTime(sdf.parse(info));
                }catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(AddEditCourseActivity.this, endDP, calendarStart.get(Calendar.YEAR),
                        calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // Start Date Picker
        startDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };
        // End Date Picker
        endDP = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //Intent
        Intent intent = getIntent();
//        courseID = Integer.parseInt(editCourseID.getText().toString());

        // Select Label
        if(intent.hasExtra(EXTRA_COURSE_ID)) {
            setTitle("Course Details");
            editTermID.setText(intent.getStringExtra(EXTRA_TERM_ID));
            editCourseID.setText(intent.getStringExtra(EXTRA_COURSE_ID_DISPLAY));
            courseTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            startDate.setText(intent.getStringExtra(EXTRA_START));
            endDate.setText(intent.getStringExtra(EXTRA_END));
        }
//        else {
//            setTitle("Add Course");
//            editTermID.setText(intent.getStringExtra(EXTRA_TERM_ID));
//
//        }


    }


    private void updateLabelStart() {startDate.setText(sdf.format(calendarStart.getTime()));}
    private void updateLabelEnd() {
        endDate.setText(sdf.format(calendarStart.getTime()));
    }

    private void saveCourse() {
        String title = courseTitle.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        String termID = editTermID.getText().toString();
        String courseID = editCourseID.getText().toString();
        int tID = Integer.parseInt(termID);



        Intent data = new Intent(this, AddEditTermActivity.class);

        data.putExtra(AddEditTermActivity.EXTRA_ID, courseTermID);
        data.putExtra(AddEditTermActivity.EXTRA_TERM_ID, courseTermID);
        data.putExtra(AddEditTermActivity.EXTRA_COURSE_ID, courseID);
        data.putExtra(AddEditTermActivity.EXTRA_TITLE, title);
        data.putExtra(AddEditTermActivity.EXTRA_START, start);
        data.putExtra(AddEditTermActivity.EXTRA_END, end);


        System.out.println("Course Title Test: " + title);
        System.out.println(courseTermID);
        setResult(RESULT_OK, data);

        finish();



    }


    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
                        String type = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_TYPE);
                        String start = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_END);
                        String ID = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID);
                        int courseID = Integer.parseInt(ID);

                        Assessment assessment = new Assessment(title, type, start, end, courseID);

                        assessmentViewModel.insert(assessment);


                        Toast.makeText(AddEditCourseActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditCourseActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_courses_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private ActivityResultLauncher<Intent> activityUpdateResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_TITLE);
                        String type = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_TYPE);
                        String start = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_END);
                        String ID = result.getData().getStringExtra(AddEditAssessmentActivity.EXTRA_COURSE_ID);
                        int courseID = Integer.parseInt(ID);
                        int assessmentID = result.getData().getIntExtra(AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID, -1);

                        Assessment assessment = new Assessment(title, type, start, end, courseID);
                        assessment.setAssessmentID(assessmentID);
                        assessmentViewModel.update(assessment);


                        Toast.makeText(AddEditCourseActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditCourseActivity.this, "NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private final ActivityResultLauncher<Intent> activityCourseResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
                        String start = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_END);
                        String termID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TERM_ID);
                        String courseIDString = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_COURSE_ID);
                        int courseID = Integer.parseInt(courseIDString);
                        int ID = Integer.parseInt(termID);

                        Course course = new Course(title, start, end, courseTermID, courseID);

                        courseViewModel.insert(course);

                        int cID = course.getCourseID();

                        System.out.println("cID: " + cID);

                        Toast.makeText(AddEditCourseActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditCourseActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//        String text = parent.getItemAtPosition(position).toString();
//        int pos = parent.getSelectedItemPosition();
//        String positionString = String.valueOf(pos);

//        statusPosition = pos;
//
//        getIntent().putExtra(AddEditCourseActivity.EXTRA_STATUS_POS, positionString);
//        getIntent().putExtra(AddEditCourseActivity.EXTRA_STATUS, text);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}
