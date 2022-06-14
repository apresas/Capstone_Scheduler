package com.example.coursescheduler.UI;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditTermActivity extends AppCompatActivity {

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
    public static final String EXTRA_INSTRUCTOR =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR";
    public static final String EXTRA_INSTRUCTOR_POS =
            "com.example.coursescheduler.EXTRA_INSTRUCTOR_POS";
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
    static int courseTermID;
    private RecyclerView courseList;
    private TextView courseTitle;
    private TextView courseStart;
    private TextView courseEnd;
    private TextView courseID;
    private TextInputEditText search;
    private RecyclerView scheduledCourseList;
    private FloatingActionButton confirmBtn;
    AddEditCourseActivity addEditCourseActivity;
    CourseAdapter courseAdapter;
    public List<Course> courses = new ArrayList<>();
    AssessmentViewModel assessmentViewModel;
    int pageWidth = 1200;
    Date dateObj;
    DateFormat pdfDateFormat;
    AssessmentAdapter assessmentAdapter;

    String cTitleSchedule;
    String cInstructorSchedule;
    String cStartSchedule;
    String cEndSchedule;
    int cID;
    String aTitleSchedule;
    String aTypeSchedule;
    String aStartSchedule;
    String aEndSchedule;
    int aID;

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

//        // Create PDF
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//        try {
//            createPDF();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }


        // Floating Button
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_open_course);


        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Adapter
        final CourseAdapter adapter = new CourseAdapter(this, courses);
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
                intent.putExtra(AddEditCourseActivity.EXTRA_INSTRUCTOR, course.getInstructor());
                activityUpdateResultLauncher.launch(intent);



            }
        });




        // Recycler View
        RecyclerView rv = findViewById(R.id.all_course_RecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        // Adapter
        CourseAdapter ca = new CourseAdapter(this, courses);
        rv.setAdapter(ca);



        // View Model
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseViewModel.getAssignedCourses(0).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                ca.setCourse(courses);
                setCourseList(courses);
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
                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(AddEditCourseActivity.EXTRA_START, course.getStartDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_END, course.getEndDate());
                intent.putExtra(AddEditCourseActivity.EXTRA_INSTRUCTOR, course.getInstructor());
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

    private void filterList(String text) {
        List<Course> filteredList = new ArrayList<>();
        System.out.println("Course List Size: " + courses.size());
        System.out.println("Filter List Size: " + filteredList.size());
        for (Course item : courses) {
            if (item.getCourseTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "Not Data Found", Toast.LENGTH_SHORT).show();
        } else {
            courseAdapter.setFilteredList(filteredList);
        }
    }

    private void filter(String text) {
        List<Course> filteredList = new ArrayList<>();
        for (Course item : courses) {
            if (item.getCourseTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        courseAdapter.setFilteredList(filteredList);
//        courseAdapter.filteredList(filteredList);
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
                        String instructor = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_INSTRUCTOR);

                        Course course = new Course(title, start, end, courseTermID, courseID, instructor);

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
            case R.id.report_term:
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
                try {
                    createPDF();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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
                        String cID = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY);
                        int courseID = Integer.parseInt(cID);
                        String instructor = result.getData().getStringExtra(AddEditCourseActivity.EXTRA_INSTRUCTOR);
                        System.out.println("Set Course ID: " + courseID);

                        Course course = new Course(title, start, end, termID, courseID, instructor);
                        course.setCourseID(courseID);
                        courseViewModel.update(course);


                        Toast.makeText(AddEditTermActivity.this, "Updated Course", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditTermActivity.this,  "Course NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void createPDF() throws FileNotFoundException {
        String termStart = startDate.getText().toString();
        String termEnd = endDate.getText().toString();
        int tID = Integer.parseInt(editTermID.getText().toString());
        String tTitle = termTitle.getText().toString();
        String user = "Test User";

        CourseAdapter cAdapter = new CourseAdapter(this, courses);

        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        dateObj = new Date();
        PdfDocument studentSchedule = new PdfDocument();
        Paint paint = new Paint();
        Paint titlePaint = new Paint();

        PdfDocument.PageInfo schedulePageInfo = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
        PdfDocument.Page schedulePage = studentSchedule.startPage(schedulePageInfo);

        Canvas canvas = schedulePage.getCanvas();
//        canvas.drawText("Student Schedule", 40, 50, paint);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(70);
        canvas.drawText("Eastern Senators College", pageWidth/2, 270, titlePaint);

        paint.setColor(Color.rgb(0, 113, 188));
        paint.setTextSize(30f);
        paint.setTextAlign(Paint.Align.CENTER);

        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        titlePaint.setTextSize(70);
        canvas.drawText("Student Schedule", pageWidth/2, 500, titlePaint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(35f);
        paint.setColor(Color.BLACK);
        canvas.drawText("Student Name: " + user, 20, 590, paint);
        canvas.drawText("Term: " + tTitle, 20, 590, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Term ID: " + tID, pageWidth-20, 590, paint);

//        pdfDateFormat = new SimpleDateFormat("MM/dd/yy");
        canvas.drawText("Start: " + termStart, pageWidth-20, 640, paint);
        canvas.drawText("End: " + termEnd, pageWidth-20, 640, paint);

//        courseViewModel.getAssignedCourses(getIntent().getIntExtra(EXTRA_ID, -1)).observe(this, new Observer<List<Course>>() {
//            @Override
//            public void onChanged(List<Course> courseList) {
//                cAdapter.setCourse(courseList);
//                for (Course courses : courseList){
//                    String courseTitle = courses.getCourseTitle();
//                    String coursesInstructor = courses.getInstructor();
//                    String coursesStartDate = courses.getStartDate();
//                    String coursesEndDate = courses.getEndDate();
//                    int courseID = courses.getCourseID();
//                    cID = courseID;
//                    canvas.drawText("Course Title: " + courseTitle+ ": " + courseID, 40, 950, paint);
//                    canvas.drawText("Instructor: " + coursesInstructor, 40, 950, paint);
//                    canvas.drawText("Start: " + coursesStartDate, 40, 950, paint);
//                    canvas.drawText("End: " + coursesEndDate, 40, 950, paint);
//                    break;
//                }
//            }
//        });


//        assessmentViewModel.getAllAssignedAssessments(cID).observe(this, new Observer<List<Assessment>>() {
//            @Override
//            public void onChanged(List<Assessment> assessmentList) {
//                assessmentAdapter.setAssessments(assessmentList);
//                for (Assessment assessments : assessmentList){
//                    String assessmentTitle = assessments.getAssessmentTitle();
//                    String assessmentType = assessments.getAssessmentType();
//                    String assessmentStartDate = assessments.getStartDate();
//                    String assessmentEndDate = assessments.getEndDate();
//                    int assessmentID = assessments.getAssessmentID();
//                    aID = assessmentID;
//                    aTitleSchedule = assessmentTitle;
//                    aTypeSchedule = assessmentType;
//                    aStartSchedule = assessmentStartDate;
//                    aEndSchedule = assessmentEndDate;
//                    canvas.drawText("Assessment Title: " + assessmentTitle + ": " + assessmentID, 60, 1150, paint);
//                    canvas.drawText("Assessment Type: " + assessmentType, 60, 1150, paint);
//                    canvas.drawText("Start: " + assessmentStartDate, 60, 1150, paint);
//                    canvas.drawText("End: " + assessmentEndDate, 60, 1150, paint);
//
//                }
//            }
//        });

//        canvas.drawText("Course Title: ", 40, 950, paint);
//        canvas.drawText("Instructor: ", 40, 950, paint);
//        canvas.drawText("Start: ", 40, 950, paint);
//        canvas.drawText("End: ", 40, 950, paint);
//
//        canvas.drawText("Assessment Title: ", 60, 1150, paint);
//        canvas.drawText("Assessment Type: ", 60, 1150, paint);
//        canvas.drawText("Start: " , 60, 1150, paint);
//        canvas.drawText("End: " , 60, 1150, paint);


//        canvas.drawText("Course Title: " + cTitleSchedule, 40, 950, paint);
//        canvas.drawText("Instructor: " + cInstructorSchedule, 40, 950, paint);
//        canvas.drawText("Start: " + cStartSchedule, 40, 950, paint);
//        canvas.drawText("End: " + cEndSchedule, 40, 950, paint);
//
//        canvas.drawText("Assessment Title: " + aTitleSchedule, 60, 1150, paint);
//        canvas.drawText("Assessment Type: " + aTypeSchedule, 60, 1150, paint);
//        canvas.drawText("Start: " + aStartSchedule, 60, 1150, paint);
//        canvas.drawText("End: " + aEndSchedule, 60, 1150, paint);








        studentSchedule.finishPage(schedulePage);

        File file = new File(pdfPath, "/StudentSchedule.pdf");
//        File file = new File(Environment.getExternalStorageDirectory(), "StudentSchedule.pdf");

        try {
            studentSchedule.writeTo(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show();
        studentSchedule.close();
    }



    public void setCourseList(List<Course> courses) {
        this.courses = courses;
    }

}
