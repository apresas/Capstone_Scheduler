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

import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Instructor;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


    private TextView editTermID;
    private TextView editCourseID;
    private EditText courseTitle;
    private TextView startDate;
    private TextView endDate;
    private Spinner statusSpinner;
    private Button noteButton;
    private Button instructorButton;
    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;
    final Calendar calendarStart = Calendar.getInstance();
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    private TextView textViewTitle;
    private TextView textViewNote;
    private AssessmentViewModel assessmentViewModel;
    private NoteViewModel noteViewModel;
    private InstructorViewModel instructorViewModel;
    static int statusPosition;
    static int courseID;
    static int noteCourseID;
    static int instructorCourseID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTermID = findViewById(R.id.edit_ID);
        editCourseID = findViewById(R.id.edit_courseID);
        courseTitle = findViewById(R.id.edit_text_courseTitle);
        startDate = findViewById(R.id.editStart);
        endDate = findViewById(R.id.editEnd);
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);


        textViewTitle = findViewById(R.id.text_view_note_title);
        textViewNote = findViewById(R.id.edit_note_body);


        statusSpinner = findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(sAdapter);
        statusSpinner.setOnItemSelectedListener(this);

        // Note Button
        noteButton = (Button) findViewById(R.id.addNoteBtn);
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditCourseActivity.this, AddEditNoteActivity.class);
                String nCourseID = editCourseID.getText().toString();
                System.out.println("Note Course ID: " + nCourseID);
                noteCourseID = Integer.parseInt(nCourseID);
                System.out.println("Static Note: " + noteCourseID);
                noteActivityResultLauncher.launch(intent);
            }
        });

        // Instructor Button
        instructorButton = (Button) findViewById(R.id.addInstructorBtn);
        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEditCourseActivity.this, AddEditInstructorActivity.class);
                String iCourseID = editCourseID.getText().toString();
                instructorCourseID = Integer.parseInt(iCourseID);
                instructorActivityResultLauncher.launch(intent);
            }
        });


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


        // Recycler View
        RecyclerView noteRecyclerView = findViewById(R.id.note_recycler);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        // Adapter
        final NoteAdapter noteAdapter = new NoteAdapter();
        noteRecyclerView.setAdapter(noteAdapter);

        // View Model
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAssignedNotes(getIntent().getIntExtra(EXTRA_COURSE_ID, -1)).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNotes(notes);
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
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AddEditCourseActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        // OnClick Fill Form
        noteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(AddEditCourseActivity.this, AddEditNoteActivity.class);
                System.out.println("Get NoteID: " + note.getNoteID());
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_COURSE_ID_DISPLAY, noteCourseID);
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_COURSE_ID, note.getCourseID());
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_ID_DISPLAY, String.valueOf(note.getNoteID()));
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_ID, note.getNoteID());
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_TITLE, note.getNoteTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_NOTE_BODY, note.getNoteBody());
                String nCourseID = editCourseID.getText().toString();
                noteCourseID = Integer.parseInt(nCourseID);

                noteActivityUpdateResultLauncher.launch(intent);

            }
        });


        // Recycler View
        RecyclerView instructorRecyclerView = findViewById(R.id.instructor_recycler);
        instructorRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        instructorRecyclerView.setHasFixedSize(true);

        // Adapter
        final InstructorAdapter instructorAdapter = new InstructorAdapter();
        instructorRecyclerView.setAdapter(instructorAdapter);

        // View Model
        instructorViewModel = new ViewModelProvider(this).get(InstructorViewModel.class);

        instructorViewModel.getAssignedInstructors(getIntent().getIntExtra(EXTRA_COURSE_ID, -1)).observe(this, new Observer<List<Instructor>>() {
            @Override
            public void onChanged(List<Instructor> instructors) {
                instructorAdapter.setInstructors(instructors);
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
                instructorViewModel.delete(instructorAdapter.getInstructorAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AddEditCourseActivity.this, "Instructor Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(instructorRecyclerView);

        // OnClick Fill Form
        instructorAdapter.setOnItemClickListener(new InstructorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Instructor instructor) {
                Intent intent = new Intent(AddEditCourseActivity.this, AddEditInstructorActivity.class);
                intent.putExtra(AddEditInstructorActivity.EXTRA_INSTRUCTOR_COURSE_ID_DISPLAY, instructorCourseID);
                intent.putExtra(AddEditInstructorActivity.EXTRA_INSTRUCTOR_COURSE_ID, instructor.getCourseID());
                intent.putExtra(AddEditInstructorActivity.EXTRA_INSTRUCTOR_ID, instructor.getInstructorID());
                intent.putExtra(AddEditInstructorActivity.EXTRA_INSTRUCTOR_NAME, instructor.getInstructorName());
                intent.putExtra(AddEditInstructorActivity.EXTRA_EMAIL, instructor.getEmail());
                intent.putExtra(AddEditInstructorActivity.EXTRA_PHONE, instructor.getPhoneNumber());
                String iCourseID = editCourseID.getText().toString();
                instructorCourseID = Integer.parseInt(iCourseID);

                instructorActivityUpdateResultLauncher.launch(intent);

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
            setTitle("Edit Course");
            editTermID.setText(intent.getStringExtra(EXTRA_TERM_ID));
            editCourseID.setText(intent.getStringExtra(EXTRA_COURSE_ID_DISPLAY));
            courseTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            statusSpinner.setSelection(statusPosition);
            startDate.setText(intent.getStringExtra(EXTRA_START));
            endDate.setText(intent.getStringExtra(EXTRA_END));
        } else {
            setTitle("Add Course");
            editTermID.setText(intent.getStringExtra(EXTRA_TERM_ID));

        }


    }


    private void updateLabelStart() {startDate.setText(sdf.format(calendarStart.getTime()));}
    private void updateLabelEnd() {
        endDate.setText(sdf.format(calendarStart.getTime()));
    }

    private void saveCourse() {
        String title = courseTitle.getText().toString();
        String status = statusSpinner.getSelectedItem().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        String termID = editTermID.getText().toString();

        if (title.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title and instructor name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TERM_ID, termID);
        data.putExtra(EXTRA_STATUS, status);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START, start);
        data.putExtra(EXTRA_END, end);

        int id = getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
        System.out.println("Save Course ID: " + id);
        if (id != -1) {
            data.putExtra(EXTRA_COURSE_ID, id);
            System.out.println("Save Course IF ID: " + id);
        }
        System.out.println("Save Course ELSE ID: " + id);
        setResult(RESULT_OK, data);
        finish();

        courseID = id;
        System.out.println("courseID: " + courseID + "ID: " + id);


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
            case R.id.notify:
                String dateFromScreen = startDate.getText().toString();
                String endFromScreen = endDate.getText().toString();
                Date date = null;
                Date eDate = null;
                try {
                    date = sdf.parse(dateFromScreen);
                    eDate = sdf.parse(endFromScreen);
                } catch (ParseException e){
                    e.printStackTrace();
                }
                Long trigger = date.getTime();
                Intent intent = new Intent(AddEditCourseActivity.this, MyReceiver.class);
                String title = courseTitle.getText().toString();
                intent.putExtra("key", "Course: " + title + " Starts today: " + dateFromScreen + " and ends: " + endFromScreen);
                PendingIntent sender = PendingIntent.getBroadcast(AddEditCourseActivity.this, MainActivity.numAlert++, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        int pos = parent.getSelectedItemPosition();
        String positionString = String.valueOf(pos);

        statusPosition = pos;

        getIntent().putExtra(AddEditCourseActivity.EXTRA_STATUS_POS, positionString);
        getIntent().putExtra(AddEditCourseActivity.EXTRA_STATUS, text);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private final ActivityResultLauncher<Intent> noteActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String noteTitle = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_NOTE_TITLE);
                        String noteComment = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_NOTE_BODY);

                        Note note = new Note(noteTitle, noteComment, noteCourseID);

                        noteViewModel.insert(note);


                        Toast.makeText(AddEditCourseActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditCourseActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> noteActivityUpdateResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String noteTitle = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_NOTE_TITLE);
                        String noteComment = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_NOTE_BODY);
                        String noteCourseID = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_NOTE_COURSE_ID);
                        int noteID = result.getData().getIntExtra(AddEditNoteActivity.EXTRA_NOTE_ID, -1);

                        Note note = new Note(noteTitle, noteComment, Integer.parseInt(noteCourseID));
                        note.setNoteID(noteID);
                        noteViewModel.update(note);

                        Toast.makeText(AddEditCourseActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditCourseActivity.this, "NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );


    private final ActivityResultLauncher<Intent> instructorActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String name = result.getData().getStringExtra(AddEditInstructorActivity.EXTRA_INSTRUCTOR_NAME);
                        String email = result.getData().getStringExtra(AddEditInstructorActivity.EXTRA_EMAIL);
                        String phone = result.getData().getStringExtra(AddEditInstructorActivity.EXTRA_PHONE);

                        Instructor instructor = new Instructor(name, email, phone, instructorCourseID);

                        instructorViewModel.insert(instructor);

                        Toast.makeText(AddEditCourseActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditCourseActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private ActivityResultLauncher<Intent> instructorActivityUpdateResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String name = result.getData().getStringExtra(AddEditInstructorActivity.EXTRA_INSTRUCTOR_NAME);
                        String email = result.getData().getStringExtra(AddEditInstructorActivity.EXTRA_EMAIL);
                        String phone = result.getData().getStringExtra(AddEditInstructorActivity.EXTRA_PHONE);
                        int instructorID = result.getData().getIntExtra(AddEditInstructorActivity.EXTRA_INSTRUCTOR_ID, -1);

                        Instructor instructor = new Instructor(name, email, phone, instructorCourseID);
                        instructor.setInstructorID(instructorID);
                        instructorViewModel.update(instructor);

                        Toast.makeText(AddEditCourseActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(AddEditCourseActivity.this, "NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}
