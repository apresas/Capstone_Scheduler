package com.example.coursescheduler.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditTermActivity extends AppCompatActivity {


    public static final String EXTRA_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_ID_DISPLAY";
    public static final String EXTRA_ID =
            "com.example.coursescheduler.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.coursescheduler.EXTRA_TITLE";
    public static final String EXTRA_START =
            "com.example.coursescheduler.EXTRA_START";
    public static final String EXTRA_END =
            "com.example.coursescheduler.EXTRA_END";

    private TextView editTermID;
    private TextInputEditText termTitle;
    private TextInputEditText startDate;
    private TextInputLayout startField;
    private TextInputEditText endDate;
    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;
    final Calendar calendarStart = Calendar.getInstance();
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private CourseViewModel courseViewModel;
    private NoteViewModel noteViewModel;
    static int courseTermID;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private RecyclerView courseList;
    Dialog courseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTermID = findViewById(R.id.edit_text_termID);
        termTitle = findViewById(R.id.text_input);
        startDate = findViewById(R.id.start_input);
        startDate.setKeyListener(null);
        startField = findViewById(R.id.start_field);
        endDate = findViewById(R.id.end_input);
        endDate.setKeyListener(null);
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);

        // Floating Button
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_open_course);

        // Go to AddEditCourse
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

//                courseDialog.setContentView(R.layout.course_popup);
//
//                courseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                courseDialog.show();

//                courseDialog.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                openDialog();


//                Intent intent = new Intent(AddEditTermActivity.this, AddEditCourseActivity.class);
//                String termID = editTermID.getText().toString();
//                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, termID);
//                activityResultLauncher.launch(intent);
            }
        });


//        // Populate Popup
//        courseList = findViewById(R.id.popupCourseRecyclerView);
//
//        courseList.setLayoutManager(new LinearLayoutManager(this));
//        courseList.setHasFixedSize(true);
//
//        final CourseAdapter courseAdapter = new CourseAdapter();
//        courseList.setAdapter(courseAdapter);
//
//        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
//        courseViewModel.getAssignedCourses(getIntent().getIntExtra(EXTRA_ID, -1)).observe(this, new Observer<List<Course>>() {
//            @Override
//            public void onChanged(List<Course> courses) {
//                courseAdapter.setCourse(courses);
//            }
//        });


        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Adapter
        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);


        // View Model
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseViewModel.getAssignedCourses(getIntent().getIntExtra(EXTRA_ID, -1)).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourse(courses);
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
                    courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(AddEditTermActivity.this, "Course Deleted", Toast.LENGTH_SHORT).show();
                }
        }).attachToRecyclerView(recyclerView);

        // OnClick Fill Form
        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(AddEditTermActivity.this, AddEditCourseActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY, String.valueOf(course.getCourseID()));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, course.getCourseID());
                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, String.valueOf(course.getTermID()));
                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(AddEditCourseActivity.EXTRA_STATUS, course.getStatus());
                intent.putExtra(AddEditCourseActivity.EXTRA_STATUS_POS, AddEditCourseActivity.statusPosition);
                intent.putExtra(AddEditCourseActivity.EXTRA_START, course.getStartDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_END, course.getEndDate());
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
                new DatePickerDialog(AddEditTermActivity.this, startDP, calendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AddEditTermActivity.this, endDP, calendarStart.get(Calendar.YEAR),
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
        // Select Label
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Term");
            editTermID.setText(intent.getStringExtra(EXTRA_ID_DISPLAY));
            termTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            startDate.setText(intent.getStringExtra(EXTRA_START));
            endDate.setText(intent.getStringExtra(EXTRA_END));

            if (AddEditCourseActivity.EXTRA_TERM_ID == editTermID.getText()) {

            }

        } else {
            setTitle("Add Term");
        }


    }

    private void updateLabelStart() {startDate.setText(sdf.format(calendarStart.getTime()));}
    private void updateLabelEnd() {
        endDate.setText(sdf.format(calendarStart.getTime()));
    }

    private void saveTerm() {
        String title = termTitle.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START, start);
        data.putExtra(EXTRA_END, end);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();


    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
                        String status = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_STATUS);
                        String start = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_END);
                        String termID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TERM_ID);
                        int ID = Integer.parseInt(termID);

                        Course course = new Course(title, status, start, end, ID);

                        courseViewModel.insert(course);

                        int cID = course.getCourseID();

                        System.out.println("cID: " + cID);

                        Toast.makeText(AddEditTermActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditTermActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_terms_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                saveTerm();
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
                        String title = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
                        String status = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_STATUS);
                        String start = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_END);
                        String ID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TERM_ID);
                        int termID = Integer.parseInt(ID);
                        int courseID = result.getData().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);

                        System.out.println("Set Course ID: " + courseID);

                        Course course = new Course(title, status, start, end, termID);
                        course.setCourseID(courseID);
                        courseViewModel.update(course);


                        Toast.makeText(AddEditTermActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditTermActivity.this, "NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    public void openDialog() {
        CourseDialog courseDialog = new CourseDialog();
        courseDialog.show(getSupportFragmentManager(), "Add Course Dialog");



    }

}
