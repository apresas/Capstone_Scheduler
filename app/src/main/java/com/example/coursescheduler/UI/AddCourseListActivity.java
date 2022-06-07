package com.example.coursescheduler.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.ScheduledCourse;
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

public class AddCourseListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_COURSE_ID =
            "com.example.coursescheduler.EXTRA_COURSE_ID";
    public static final String EXTRA_TERM_ID =
            "com.example.coursescheduler.EXTRA_TERM_ID";
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
    public static final String SELECTED_COURSE =
            "com.example.coursescheduler.SELECTED_COURSE";

    private TextInputEditText search;
    private RecyclerView courseList;
    private FloatingActionButton confirmButton;
    private List<ScheduledCourse> scheduledCourseList = new ArrayList();
    private ScheduledCourseViewModel scheduledCourseViewModel;
    private CourseViewModel courseViewModel;
    AddEditTermActivity addEditTermActivity;
    static int courseTermID;


//    private TextView editTermID;
//    private TextInputEditText termTitle;
//    private TextInputEditText startDate;
//    private TextInputLayout startField;
//    private TextInputEditText endDate;
//    DatePickerDialog.OnDateSetListener startDP;
//    DatePickerDialog.OnDateSetListener endDP;
//    final Calendar calendarStart = Calendar.getInstance();
//    String dateFormat = "MM/dd/yy";
//    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
//    private CourseViewModel courseViewModel;
//    private ScheduledCourseAdapter scheduledCourseAdapter;
//    private NoteViewModel noteViewModel;
//    static int courseTermID;
//    private AlertDialog.Builder dialogBuilder;
//    private AlertDialog dialog;
//    private RecyclerView courseList;
//    private TextView courseTitle;
//    private TextView courseStart;
//    private TextView courseEnd;
//    private TextView courseID;
//    private ScheduledCourseViewModel scViewModel;
//    private TextInputEditText search;
//    private RecyclerView scheduledCourseList;
//    private FloatingActionButton confirmBtn;
//    private Button addBtn;
//    private List<ScheduledCourse> sc = new ArrayList();
//    private ScheduledCourseAdapter.OnItemClickListener listener;
//    static ScheduledCourse scCourse;
//    FrameLayout fragmentContainer;

//    CourseDialog courseDialog;
//    Dialog courseDialog;
    private static final String TAG = "AddEditTermActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        search = findViewById(R.id.search_input);
        courseList = findViewById(R.id.popupCourseRecyclerView);
        confirmButton = findViewById(R.id.button_add_confirm);

        if (getIntent().hasExtra(SELECTED_COURSE)){
            ScheduledCourse scCourse = getIntent().getParcelableExtra("SELECTED_COURSE");
            Log.d(TAG, "onCreate: " + scCourse. toString());
        }


        // Go to AddEditCourse
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                Intent intent = new Intent(AddCourseListActivity.this, AddEditTermActivity.class);
//                startActivity(intent);

                finishActivity(v);


//                activityResultLauncher.launch(intent);

            }
        });



        // Recycler View
//        RecyclerView recyclerView = findViewById(R.id.popupCourseRecyclerView);

        courseList.setLayoutManager(new LinearLayoutManager(this));
        courseList.setHasFixedSize(true);

        // Adapter
        final ScheduledCourseAdapter adapter = new ScheduledCourseAdapter(scheduledCourseList);
        courseList.setAdapter(adapter);


        // View Model
        scheduledCourseViewModel = new ViewModelProvider(this).get(ScheduledCourseViewModel.class);
        scheduledCourseViewModel.getAllScheduledCourses().observe(this, new Observer<List<ScheduledCourse>>() {
            @Override
            public void onChanged(List<ScheduledCourse> scheduledCourses) {
                adapter.setScheduledCourse(scheduledCourses);
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
//                    scheduledCourseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
//                    Toast.makeText(AddCourseListActivity.this, "Course Deleted", Toast.LENGTH_SHORT).show();
                }
        }).attachToRecyclerView(courseList);

        // OnClick Fill Form
        adapter.setOnItemClickListener(new ScheduledCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ScheduledCourse course) {
                Intent intent = new Intent(AddCourseListActivity.this, AddEditCourseActivity.class);
                String cID = String.valueOf(course.getCourseID());
//                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY, String.valueOf(course.getCourseID()));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY, course.getCourseID());
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, course.getCourseID());
                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, courseTermID);
                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(AddEditCourseActivity.EXTRA_START, course.getStartDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_END, course.getEndDate());
                activityUpdateResultLauncher.launch(intent);


            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        //Intent
        Intent intent = getIntent();
//        // Select Label
//        if(intent.hasExtra(EXTRA_ID)) {
//            setTitle("Edit Term");
//            editTermID.setText(intent.getStringExtra(EXTRA_ID_DISPLAY));
//            termTitle.setText(intent.getStringExtra(EXTRA_TITLE));
//            startDate.setText(intent.getStringExtra(EXTRA_START));
//            endDate.setText(intent.getStringExtra(EXTRA_END));
//
//            if (AddEditCourseActivity.EXTRA_TERM_ID == editTermID.getText()) {
//
//            }
//
//        } else {
//            setTitle("Add Term");
//        }


    }


//    private void saveTerm() {
//        String title = termTitle.getText().toString();
//        String start = startDate.getText().toString();
//        String end = endDate.getText().toString();
//
//        if (title.trim().isEmpty()) {
//            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Intent data = new Intent();
//        data.putExtra(EXTRA_TITLE, title);
//        data.putExtra(EXTRA_START, start);
//        data.putExtra(EXTRA_END, end);
//
//        int id = getIntent().getIntExtra(EXTRA_ID, -1);
//        if (id != -1) {
//            data.putExtra(EXTRA_ID, id);
//        }
//        setResult(RESULT_OK, data);
//        finish();
//
//
//    }

//    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK){
//                        String title = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
//                        String start = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_START);
//                        String end = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_END);
//                        String termID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TERM_ID);
//                        String courseIDString = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_COURSE_ID);
//                        int courseID = Integer.parseInt(courseIDString);
//                        int ID = Integer.parseInt(termID);
//
//                        Course course = new Course(title, start, end, courseTermID, courseID);
//
//                        courseViewModel.insert(course);
//
//                        int cID = course.getCourseID();
//
//                        System.out.println("cID: " + cID);
//
//                        Toast.makeText(AddCourseListActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//
//                    }else {
//                        Toast.makeText(AddCourseListActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_terms_menu, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.save_term:
//                saveTerm();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//    }

    private ActivityResultLauncher<Intent> activityUpdateResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
                        String start = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_END);
                        String ID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TERM_ID);
//                        int termID = Integer.parseInt(ID);
                        int courseID = result.getData().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);

                        System.out.println("Set Course ID: " + courseID);

                        ScheduledCourse sc = new ScheduledCourse(title, start, end, courseID);
                        scheduledCourseViewModel.update(sc);


                        Toast.makeText(AddCourseListActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddCourseListActivity.this, "NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );



//    private ActivityResultLauncher<Intent> activityUpdateResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK){
//                        String title = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TITLE);
//                        String start = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_START);
//                        String end = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_END);
//                        String ID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TERM_ID);
//                        int termID = Integer.parseInt(ID);
//                        int courseID = result.getData().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
//
//                        System.out.println("Set Course ID: " + courseID);
//
//                        Course course = new Course(title, start, end, courseTermID, courseID);
//                        course.setCourseID(courseID);
//                        courseViewModel.update(course);
//
//
//                        Toast.makeText(AddCourseListActivity.this, "Updated", Toast.LENGTH_SHORT).show();
//
//                    }else {
//                        Toast.makeText(AddCourseListActivity.this, "NOT Updated", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void finishActivity(View v) {
        finish();
    }
}
