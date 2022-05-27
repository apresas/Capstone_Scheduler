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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CourseDialog extends AppCompatDialogFragment {
    public static final String EXTRA_ID =
            "com.example.coursescheduler.EXTRA_ID";

    private TextInputEditText search;
    private RecyclerView courseList;
    private Button confirmBtn;
    private CourseDialog courseDialog;
    CourseViewModel courseViewModel;
    AddEditTermActivity addEditTermActivity;
    Context context;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.course_popup, null);

        builder.setView(view);

        courseList = view.findViewById(R.id.popupCourseRecyclerView);

        // Recycler View

        courseList.setLayoutManager(new LinearLayoutManager(addEditTermActivity));
        courseList.setHasFixedSize(true);

        // Adapter
        final CourseAdapter adapter = new CourseAdapter();
        courseList.setAdapter(adapter);


        // View Model
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);


        courseViewModel.getAssignedCourses(getActivity().getIntent().getIntExtra(EXTRA_ID, -1)).observe(this, new Observer<List<Course>>() {
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
//                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
//                Toast.makeText(CourseDialog.class, "Course Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(courseList);


        // OnClick Fill Form
        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
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


}
