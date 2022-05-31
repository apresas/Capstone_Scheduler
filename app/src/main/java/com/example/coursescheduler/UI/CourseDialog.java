package com.example.coursescheduler.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.ScheduledCourse;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CourseDialog extends AppCompatDialogFragment {
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

    private TextInputEditText search;
    private RecyclerView scheduledCourseList;
    private FloatingActionButton confirmBtn;
    private Button addBtn;
    private CourseDialog courseDialog;
    ScheduledCourseViewModel scViewModel;
    CourseAdapter adapter;
    AddEditTermActivity addEditTermActivity;
    Context context;
    TextView courseTitle;
    TextView tID;
    static String termID;
    TextView startDate;
    TextView endDate;
    TextView cID;
    static int courseID;
    View addItem;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.course_popup, null);

        builder.setView(view)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        scheduledCourseList = view.findViewById(R.id.popupCourseRecyclerView);
        addBtn = view.findViewById(R.id.add_course_btn);
        courseTitle = view.findViewById(R.id.text_view_add_course_title);
        tID = view.findViewById(R.id.edit_termID);
        startDate = view.findViewById(R.id.edit_add_course_start);
        endDate = view.findViewById(R.id.edit_add_course_end);
        cID = view.findViewById(R.id.text_view_add_courseID);


//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                saveCourse();
//                System.out.println("Test Save");
//            }
//        });



//        confirmBtn = view.findViewById(R.id.button_add_confirm);

//        //Button
//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                courseDialog.dismiss();
////                closeDialog();
//            }
//        });


        // Recycler View

        scheduledCourseList.setLayoutManager(new LinearLayoutManager(addEditTermActivity));
        scheduledCourseList.setHasFixedSize(true);

        // Adapter
        final ScheduledCourseAdapter adapter = new ScheduledCourseAdapter();
        scheduledCourseList.setAdapter(adapter);


        // View Model
        scViewModel = new ViewModelProvider(this).get(ScheduledCourseViewModel.class);

        scViewModel.getAllScheduledCourses().observe(this, new Observer<List<ScheduledCourse>>() {
            @Override
            public void onChanged(List<ScheduledCourse> sc) {
                adapter.setScheduledCourse(sc);
            }
        });
//        courseViewModel.getAssignedCourses(getActivity().getIntent().getIntExtra(EXTRA_ID, -1)).observe(this, new Observer<List<Course>>() {
//            @Override
//            public void onChanged(List<Course> courses) {
//                adapter.setCourse(courses);
//            }
//        });


        // Touch helper
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(CourseDialog.class, "Course Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(scheduledCourseList);



        // OnClick Fill Form
//        adapter.setOnItemClickListener(new ScheduledCourseAdapter().OnItemClickListener() {
//            @Override
//            public void onItemClick(ScheduledCourse sc) {
//                Intent intent = new Intent(addEditTermActivity, AddEditCourseActivity.class);
//                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY, String.valueOf(course.getCourseID()));
//                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, course.getCourseID());
//                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, String.valueOf(course.getTermID()));
//                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, course.getCourseTitle());
//                intent.putExtra(AddEditCourseActivity.EXTRA_STATUS, course.getStatus());
//                intent.putExtra(AddEditCourseActivity.EXTRA_STATUS_POS, AddEditCourseActivity.statusPosition);
//                intent.putExtra(AddEditCourseActivity.EXTRA_START, course.getStartDate());
//                intent.putExtra(AddEditCourseActivity.EXTRA_END, course.getEndDate());
//                activityUpdateResultLauncher.launch(intent);
//
//
//            }
//        });
        return builder.create();


    }


//    // Save Course
//    private void saveScheduledCourse() {
//        String title = courseTitle.getText().toString();
//        String start = startDate.getText().toString();
//        String end = endDate.getText().toString();
//        String courseID = cID.getText().toString();
////        String termID = tID.getText().toString();
//
//
//        Intent data = new Intent();
//        data.putExtra(EXTRA_TERM_ID, termID);
//        data.putExtra(EXTRA_TITLE, title);
//        data.putExtra(EXTRA_START, start);
//        data.putExtra(EXTRA_END, end);
//        data.putExtra(EXTRA_COURSE_ID, courseID);
//
//
////        int id = addEditTermActivity.getIntent().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
////        System.out.println("Save Course ID: " + id);
////        if (id != -1) {
////            data.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, id);
////            System.out.println("Save Course IF ID: " + id);
////        }
////        System.out.println("Save Course ELSE ID: " + id);
//        addEditTermActivity.setResult(Activity.RESULT_OK, data);
//        addEditTermActivity.finish();
//
////        courseID = id;
////        System.out.println("courseID: " + courseID + "ID: " + id);
//
//
//    }


    private ActivityResultLauncher<Intent> activityUpdateResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(EXTRA_TITLE);
                        String start = result.getData().getStringExtra(EXTRA_START);
                        String end = result.getData().getStringExtra(EXTRA_END);
                        int courseID = result.getData().getIntExtra(EXTRA_COURSE_ID, -1);

                        System.out.println("Set Course ID: " + courseID);

                        ScheduledCourse sc = new ScheduledCourse(title, start, end);
                        sc.setCourseID(courseID);
                        scViewModel.update(sc);


//                        Toast.makeText(addEditTermActivity, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
//                        Toast.makeText(addEditTermActivity, "NOT Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    // Button
    private void closeDialog() {
        courseDialog.dismiss();

    }



    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(EXTRA_TITLE);
                        String start = result.getData().getStringExtra(EXTRA_START);
                        String end = result.getData().getStringExtra(EXTRA_END);
                        String termID = result.getData().getStringExtra(EXTRA_TERM_ID);
                        int ID = Integer.parseInt(termID);

                        ScheduledCourse sc = new ScheduledCourse(title, start, end);

                        scViewModel.insert(sc);

                        int cID = sc.getCourseID();

                        System.out.println("cID: " + cID);

//                        Toast.makeText(addEditTermActivity, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
//                        Toast.makeText(addEditTermActivity, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );



}
