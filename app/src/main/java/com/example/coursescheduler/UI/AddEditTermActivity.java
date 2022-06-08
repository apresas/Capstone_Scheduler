package com.example.coursescheduler.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.Entity.ScheduledCourse;
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

public class AddEditTermActivity extends AppCompatActivity implements FragmentCourse.OnFragmentInteractionListener {

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
    private ScheduledCourseAdapter scheduledCourseAdapter;
    private NoteViewModel noteViewModel;
    static int courseTermID;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private RecyclerView courseList;
    private TextView courseTitle;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseID;
    private ScheduledCourseViewModel scViewModel;
    private TextInputEditText search;
    private RecyclerView scheduledCourseList;
    private FloatingActionButton confirmBtn;
    private Button addBtn;
    private List<ScheduledCourse> sc = new ArrayList();
    private ScheduledCourseAdapter.OnItemClickListener listener;
    static ScheduledCourse scCourse;
    FrameLayout fragmentContainer;
    AddEditCourseActivity addEditCourseActivity;
    private List<ScheduledCourse> scList = new ArrayList();

//    CourseDialog courseDialog;
//    Dialog courseDialog;
    private static final String TAG = "AddEditTermActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term_v3);
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

        courseTitle = findViewById(R.id.text_view_add_course_title);
        courseStart = findViewById(R.id.edit_add_course_start);
        courseEnd = findViewById(R.id.edit_add_course_end);
        courseID = findViewById(R.id.text_view_add_courseID);

        fragmentContainer = (FrameLayout) findViewById(R.id.add_course_frame);



        if (getIntent().hasExtra(SELECTED_COURSE)){
            ScheduledCourse scCourse = getIntent().getParcelableExtra("SELECTED_COURSE");
            Log.d(TAG, "onCreate: " + scCourse. toString());
        }

        // Floating Button
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_open_course);



        // Go to AddEditCourse
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                courseDialog.termID = editTermID.getText().toString();
//                scheduledCourseAdapter.termID = editTermID.getText().toString();
//                ScheduledCourseAdapter.courseTermID = Integer.parseInt(editTermID.getText().toString());
//                createNewCourseDialog();
                String termID = editTermID.getText().toString();
                ScheduledCourseAdapter.courseTermID = Integer.parseInt(termID);
                AddCourseListActivity.courseTermID = Integer.parseInt(termID);
                AddEditCourseActivity.courseTermID = Integer.parseInt(termID);
//                int tID = Integer.parseInt(termID);
                Intent intent = new Intent(AddEditTermActivity.this, AddCourseListActivity.class);
                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, termID);
                activityResultLauncher.launch(intent);
//                System.out.println("Term Button Clicked");

//                startActivity(intent);
//                openFragment();


//                CourseDialog.courseTermID = Integer.parseInt(termID);
//                courseTermID = Integer.parseInt(termID);
//                CourseDialog courseDialog = new CourseDialog();
//                courseDialog.show(getSupportFragmentManager(), "Add Course Dialog");





//                openDialog();

            }
        });



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
//                    courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                    adapter.getCourseAt(viewHolder.getAdapterPosition()).setTermID(0);
                    courseViewModel.update(adapter.getCourseAt(viewHolder.getAdapterPosition()));
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
                intent.putExtra(AddEditCourseActivity.EXTRA_START, course.getStartDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_END, course.getEndDate());
//                courseTermID = AddEditCourseActivity.courseTermID;
                activityUpdateResultLauncher.launch(intent);



            }
        });




        // Recycler View
        RecyclerView rv = findViewById(R.id.all_course_RecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        // Adapter
        CourseAdapter ca = new CourseAdapter();
        rv.setAdapter(ca);


        // View Model
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAssignedCourses(0).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                ca.setCourse(courses);
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
        ca.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(AddEditTermActivity.this, AddEditCourseActivity.class);
                String tID = editTermID.getText().toString();
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY, String.valueOf(course.getCourseID()));
                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, course.getCourseID());
                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, tID);
//                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, String.valueOf(course.getTermID()));
                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(AddEditCourseActivity.EXTRA_START, course.getStartDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_END, course.getEndDate());
//                courseTermID = AddEditCourseActivity.courseTermID;
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

                        Toast.makeText(AddEditTermActivity.this, "Saved Course", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditTermActivity.this, "Course Inserted Unsuccessful", Toast.LENGTH_SHORT).show();
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
                        String start = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_END);
                        String ID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_TERM_ID);
                        int termID = Integer.parseInt(ID);

//                        int courseID = result.getData().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
                        String cID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY);
                        int courseID = Integer.parseInt(cID);
                        System.out.println("Set Course ID: " + courseID);

                        Course course = new Course(title, start, end, termID, courseID);
                        course.setCourseID(courseID);
//                        course.setTermID(courseTermID);
                        courseViewModel.update(course);


                        Toast.makeText(AddEditTermActivity.this, "Updated Course", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditTermActivity.this,  "Course NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );



    public void openFragment() {
        FragmentCourse fragment = FragmentCourse.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        transaction.addToBackStack(null);
        transaction.add(R.id.add_course_frame, fragment, "Blank_Fragment").commit();

    }

    @Override
    public void goToCourseDetails(Course course) {
        Intent intent = new Intent(this, AddEditCourseActivity.class);
//        intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, course.getCourseID());
//        intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, courseTermID);
//        intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
//        intent.putExtra(AddEditCourseActivity.EXTRA_START, course.getStartDate());
//        intent.putExtra(AddEditCourseActivity.EXTRA_END, course.getEndDate());

        activityUpdateResultLauncher.launch(intent);
    }

}
