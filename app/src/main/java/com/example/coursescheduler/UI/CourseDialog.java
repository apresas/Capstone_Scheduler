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
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CourseDialog extends AppCompatDialogFragment {
    public static final String EXTRA_ID =
            "com.example.coursescheduler.EXTRA_ID";

    private TextInputEditText search;
    private RecyclerView courseList;
    private FloatingActionButton confirmBtn;
    private Button addBtn;
    private CourseDialog courseDialog;
    CourseViewModel courseViewModel;
    CourseAdapter adapter;
    AddEditTermActivity addEditTermActivity;
    Context context;
    TextView courseTitle;
    TextView tID;
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

        courseList = view.findViewById(R.id.popupCourseRecyclerView);
        addBtn = view.findViewById(R.id.add_course_btn);
        courseTitle = view.findViewById(R.id.text_view_course_title);
        tID = view.findViewById(R.id.edit_termID);
        startDate = view.findViewById(R.id.edit_course_start);
        endDate = view.findViewById(R.id.edit_course_end);
        cID = view.findViewById(R.id.text_view_courseID);


//        addBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveCourse();
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

        courseList.setLayoutManager(new LinearLayoutManager(addEditTermActivity));
        courseList.setHasFixedSize(true);

        // Adapter
        final AddCourseAdapter adapter = new AddCourseAdapter();
        courseList.setAdapter(adapter);


        // View Model
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourse(courses);
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
        }).attachToRecyclerView(courseList);



        // OnClick Fill Form
        adapter.setOnItemClickListener(new AddCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(addEditTermActivity, AddEditCourseActivity.class);
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
        return builder.create();


    }


    // Save Course
    private void saveCourse() {
        String title = courseTitle.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        String termID = tID.getText().toString();


        Intent data = new Intent();
        data.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, termID);
        data.putExtra(AddEditCourseActivity.EXTRA_TITLE, title);
        data.putExtra(AddEditCourseActivity.EXTRA_START, start);
        data.putExtra(AddEditCourseActivity.EXTRA_END, end);

        int id = addEditTermActivity.getIntent().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
        System.out.println("Save Course ID: " + id);
        if (id != -1) {
            data.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, id);
            System.out.println("Save Course IF ID: " + id);
        }
        System.out.println("Save Course ELSE ID: " + id);

        addEditTermActivity.setResult(Activity.RESULT_OK, data);
        addEditTermActivity.finish();

        courseID = id;
        System.out.println("courseID: " + courseID + "ID: " + id);


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


                        Toast.makeText(addEditTermActivity, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(addEditTermActivity, "NOT Updated", Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(addEditTermActivity, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(addEditTermActivity, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );



}
