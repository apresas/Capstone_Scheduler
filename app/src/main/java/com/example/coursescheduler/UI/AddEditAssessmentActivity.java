package com.example.coursescheduler.UI;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditAssessmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ASSESSMENT_ID_DISPLAY =
            "com.example.coursescheduler.EXTRA_ASSESSMENT_ID_DISPLAY";
    public static final String EXTRA_ASSESSMENT_ID =
            "com.example.coursescheduler.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_COURSE_ID =
            "com.example.coursescheduler.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_ID_DISPlAY =
            "com.example.coursescheduler.EXTRA_COURSE_ID_DISPLAY";
    public static final String EXTRA_TITLE =
            "com.example.coursescheduler.EXTRA_TITLE";
    public static final String EXTRA_TYPE =
            "com.example.coursescheduler.EXTRA_TYPE";
    public static final String EXTRA_TYPE_POSITION =
            "com.example.coursescheduler.EXTRA_TYPE_POSITION";
    public static final String EXTRA_START =
            "com.example.coursescheduler.EXTRA_START";
    public static final String EXTRA_END =
            "com.example.coursescheduler.EXTRA_END";

    private TextView editCourseID;
    private TextView editAssessmentID;
    private EditText assessmentTitle;
    private EditText typeTitle;
    private TextView startDate;
    private TextView endDate;
    private Spinner typeSpinner;
    DatePickerDialog.OnDateSetListener startDP;
    DatePickerDialog.OnDateSetListener endDP;
    final Calendar calendarStart = Calendar.getInstance();
    String dateFormat = "MM/dd/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
    static int typePosition;
    private AssessmentViewModel assessmentViewModel;
    CourseDAO courseDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editCourseID = findViewById(R.id.edit_ID);
        editAssessmentID = findViewById(R.id.edit_assessmentID);
        assessmentTitle = findViewById(R.id.edit_text_assessmentTitle);
//        typeTitle = findViewById(R.id.edit_text_type);
        startDate = findViewById(R.id.editStart);
        endDate = findViewById(R.id.editEnd);
        dateFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(dateFormat, Locale.US);

        typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(sAdapter);
        typeSpinner.setOnItemSelectedListener(this);


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
                new DatePickerDialog(AddEditAssessmentActivity.this, startDP, calendarStart.get(Calendar.YEAR),
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
                new DatePickerDialog(AddEditAssessmentActivity.this, endDP, calendarStart.get(Calendar.YEAR),
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
        if(intent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            setTitle("Edit Assessment");
            editCourseID.setText(intent.getStringExtra(EXTRA_COURSE_ID));
            editAssessmentID.setText(intent.getStringExtra(EXTRA_ASSESSMENT_ID_DISPLAY));
            assessmentTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            typeSpinner.setSelection(typePosition);
//            typeTitle.setText(intent.getStringExtra(EXTRA_TYPE));
            startDate.setText(intent.getStringExtra(EXTRA_START));
            endDate.setText(intent.getStringExtra(EXTRA_END));
        } else {
            setTitle("Add Assessment");
            editCourseID.setText(intent.getStringExtra(EXTRA_COURSE_ID));

        }


    }

    private void updateLabelStart() {startDate.setText(sdf.format(calendarStart.getTime()));}
    private void updateLabelEnd() {
        endDate.setText(sdf.format(calendarStart.getTime()));
    }

    private void saveAssessment() {
        String title = assessmentTitle.getText().toString();
        String type = typeSpinner.getSelectedItem().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        String courseID = editCourseID.getText().toString();

        if (title.trim().isEmpty() || type.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title and type", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_COURSE_ID, courseID);
        data.putExtra(EXTRA_COURSE_ID_DISPlAY, courseID);
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START, start);
        data.putExtra(EXTRA_END, end);

        int id = getIntent().getIntExtra(EXTRA_ASSESSMENT_ID, -1);
        if (id != -1) {
            System.out.println(EXTRA_COURSE_ID);
            data.putExtra(EXTRA_ASSESSMENT_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_assessments_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_assessment:
                saveAssessment();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Text for note field.");
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Note Title");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
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
                Intent intent = new Intent(AddEditAssessmentActivity.this, MyReceiver.class);
                String title = assessmentTitle.getText().toString();
                intent.putExtra("key", "Assessment: " + title + " Starts today: " + dateFromScreen + " and ends: " + endFromScreen);
                PendingIntent sender = PendingIntent.getBroadcast(AddEditAssessmentActivity.this, MainActivity.numAlert++, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();
        int pos = parent.getSelectedItemPosition();
        String positionString = String.valueOf(pos);

        typePosition = pos;

        getIntent().putExtra(AddEditAssessmentActivity.EXTRA_TYPE, text);
        getIntent().putExtra(AddEditAssessmentActivity.EXTRA_TYPE_POSITION, positionString);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

