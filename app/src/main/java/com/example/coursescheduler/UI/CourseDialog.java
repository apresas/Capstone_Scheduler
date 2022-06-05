//package com.example.coursescheduler.UI;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.os.Parcel;
//import android.text.Layout;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatDialogFragment;
//import androidx.appcompat.view.menu.MenuView;
//import androidx.fragment.app.DialogFragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.coursescheduler.Database.ScheduleDB;
//import com.example.coursescheduler.Database.ScheduleRepo;
//import com.example.coursescheduler.Entity.Course;
//import com.example.coursescheduler.Entity.ScheduledCourse;
//import com.example.coursescheduler.Entity.Term;
//import com.example.coursescheduler.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.android.material.textfield.TextInputLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CourseDialog extends DialogFragment implements ScheduledCourseAdapter.OnCourseListener {
//    public static final String EXTRA_COURSE_ID_DISPLAY =
//            "com.example.coursescheduler.EXTRA_COURSE_ID_DISPLAY";
//    public static final String EXTRA_COURSE_ID =
//            "com.example.coursescheduler.EXTRA_ID";
//    public static final String EXTRA_TERM_ID =
//            "com.example.coursescheduler.EXTRA_TERM_ID";
//    public static final String EXTRA_TITLE =
//            "com.example.coursescheduler.EXTRA_TITLE";
//    public static final String EXTRA_INSTRUCTOR =
//            "com.example.coursescheduler.EXTRA_INSTRUCTOR";
//    public static final String EXTRA_INSTRUCTOR_POS =
//            "com.example.coursescheduler.EXTRA_INSTRUCTOR_POS";
//    public static final String EXTRA_STATUS =
//            "com.example.coursescheduler.EXTRA_STATUS";
//    public static final String EXTRA_STATUS_POS =
//            "com.example.coursescheduler.EXTRA_STATUS_POS";
//    public static final String EXTRA_START =
//            "com.example.coursescheduler.EXTRA_START";
//    public static final String EXTRA_END =
//            "com.example.coursescheduler.EXTRA_END";
//    public static final String SELECTED_COURSE =
//            "com.example.coursescheduler.SELECTED_COURSE";
//
//    private TextInputEditText search;
//    RecyclerView scheduledCourseList;
//    private List<ScheduledCourse> scList = new ArrayList();
//    private FloatingActionButton confirmBtn;
//    Button addBtn;
//    private CourseDialog courseDialog;
//    ScheduledCourseViewModel scViewModel;
//    CourseAdapter adapter;
//    AddEditTermActivity addEditTermActivity;
//    ScheduleRepo repo;
//    Context context;
//    TextView courseTitle;
//    TextView tID;
//    static String termID;
//    TextView startDate;
//    TextView endDate;
//    TextView cID;
//    static int courseTermID;
//    int ID;
//    View addItem;
//    ScheduledCourseAdapter scAdapter;
//    static ScheduledCourse scCourse;
//
//    private OnInputListener listener;
//    public ScheduledCourseAdapter.OnCourseListener courseListener;
//    ScheduledCourseAdapter.ScheduledCourseHolder scHolder;
//
//
//    public interface OnItemClickListener {
//        void onItemClick(ScheduledCourse sc);
//    }
//    public OnItemClickListener scOnItemClickListener;
//
//
//
//
//    public interface OnInputListener {
//        void sendInput(String title, String start, String end, int courseTermID, int courseID);
//    }
//
//    public OnInputListener mOnInputListener;
//
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
////        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
////        inflater = getActivity().getLayoutInflater();
////        View view = inflater.inflate(R.layout.course_popup, container, true);
//        View view = inflater.inflate(R.layout.course_popup, container, true);
//
//        scheduledCourseList = view.findViewById(R.id.popupCourseRecyclerView);
////        addBtn = scHolder.itemView.findViewById(R.id.add_course_btn);
//        addBtn = view.findViewById(R.id.add_course_btn);
//        courseTitle = view.findViewById(R.id.text_view_add_course_title);
//        tID = view.findViewById(R.id.edit_termID);
//        startDate = view.findViewById(R.id.edit_add_course_start);
//        endDate = view.findViewById(R.id.edit_add_course_end);
//        cID = view.findViewById(R.id.text_view_add_courseID);
//
//
//
////        scAdapter.setOnItemClickListener(new ScheduledCourseAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(ScheduledCourse sc) {
////                Intent intent = new Intent();
////                int position = scHolder.getAdapterPosition();
////                ScheduledCourse scCourse = scAdapter.sc.get(position);
////                String title = scCourse.getCourseTitle();
////                String start = scCourse.getStartDate();
////                String end = scCourse.getEndDate();
////                int courseID = scCourse.getCourseID();
////                int termID = courseTermID;
////
////                intent.putExtra(AddEditTermActivity.EXTRA_TERM_ID, termID);
////                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, title);
////                intent.putExtra(AddEditTermActivity.EXTRA_START, start);
////                intent.putExtra(AddEditTermActivity.EXTRA_END, end);
////                intent.putExtra(AddEditTermActivity.EXTRA_COURSE_ID, courseTermID);
////                activityUpdateResultLauncher.launch(intent);
////            }
////        });
//
//
//
//
////        addBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                System.out.println("Button Test");
////            }
////        });
//
//
////        // Recycler View
////        RecyclerView recyclerView = scHolder.itemView.findViewById(R.id.popupCourseRecyclerView);
////        recyclerView.setLayoutManager(new LinearLayoutManager(context));
////        recyclerView.setHasFixedSize(true);
////
////        final TermAdapter adapter = new TermAdapter();
////        recyclerView.setAdapter(adapter);
////
////
////        scViewModel = new ViewModelProvider(this).get(TermViewModel.class);
////
////        scViewModel.getAllScheduledCourses().observe(this, new Observer<List<ScheduledCourse>() {
////            @Override
////            public void onChanged(List<Term> terms) {
////                adapter.setTerms(terms);
////            }
////        });
////
////
////        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
////                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
////            @Override
////            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
////                return false;
////            }
////
////            public List<ScheduledCourse> setList() {
////                List<ScheduledCourse> scheduledCourseList = new ArrayList<>();
////                for (ScheduledCourse sc : repo.getAllSchduledCourses()) {
////                    if (sc.getCourseID() == ID)scheduledCourseList.add(sc);
////                }
////                return scheduledCourseList;
////            }
////
////            @Override
////            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
////                Term currentTerm = adapter.getTermAt(viewHolder.getAdapterPosition());
////                int termID = currentTerm.getTermID();
////                ID = termID;
////
////                if (setList().isEmpty()) {
////                    repo.deleteTerm(currentTerm);
////                    Toast.makeText(TermActivity.this, "Term Deleted", Toast.LENGTH_SHORT).show();
////                } else {
////                    Toast.makeText(TermActivity.this, currentTerm.getTermTitle() + " Courses assigned. Please delete Courses.", Toast.LENGTH_SHORT).show();
////                    recyclerView.setAdapter(adapter);
////                }
////            }
////        }).attachToRecyclerView(recyclerView);
////
////        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(Term term) {
////                Intent intent = new Intent(TermActivity.this, AddEditTermActivity.class);
////                intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getTermID());
////                intent.putExtra(AddEditTermActivity.EXTRA_ID_DISPLAY, String.valueOf(term.getTermID()));
////                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTermTitle());
////                intent.putExtra(AddEditTermActivity.EXTRA_START, term.getStartDate());
////                intent.putExtra(AddEditTermActivity.EXTRA_END, term.getEndDate());
////
////                activityUpdateResultLauncher.launch(intent);
////            }
////        });
////    }
//
//
////        addBtn = courseHolder.itemView.findViewById(R.id.add_course_btn);
//
//
////        addBtn = courseHolder.itemView.findViewById(R.id.add_course_btn);
//
////        addBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                String title = courseTitle.getText().toString();
////                String start = startDate.getText().toString();
////                String end = endDate.getText().toString();
////                String courseTermIDString = tID.getText().toString();
////                int courseTermID = Integer.parseInt(courseTermIDString);
////                String courseIDString = cID.getText().toString();
////                int courseID = Integer.parseInt(courseIDString);
////                listener.sendInput(title,start,end,courseTermID, courseID);
////            }
////        });
//
////        builder.setView(view)
////                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////
////                    }
////                })
////                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////
////                    }
////                });
//
//
//
//
////        mOnInputListener.sendInput(title);
//
//
////        addBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
//////                saveCourse();
////                System.out.println("Test Save");
////            }
////        });
//
//
//
////        confirmBtn = view.findViewById(R.id.button_add_confirm);
//
////        //Button
////        confirmBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                courseDialog.dismiss();
//////                closeDialog();
////            }
////        });
//
//
//        // Recycler View
////        RecyclerView recyclerView = findViewById(R.id.popupCourseRecyclerView);
////        recyclerView.setLayoutManager(new LinearLayoutManager(addEditTermActivity));
////        recyclerView.setHasFixedSize(true);
//
//        scheduledCourseList.setLayoutManager(new LinearLayoutManager(addEditTermActivity));
//        scheduledCourseList.setHasFixedSize(true);
//
//        // Adapter
//        ScheduledCourseAdapter adapter = new ScheduledCourseAdapter(scList, this);
//        scheduledCourseList.setAdapter(adapter);
//
//
//
//        // View Model
//        scViewModel = new ViewModelProvider(this).get(ScheduledCourseViewModel.class);
//
//        scViewModel.getAllScheduledCourses().observe(this, new Observer<List<ScheduledCourse>>() {
//            @Override
//            public void onChanged(List<ScheduledCourse> sc) {
//                adapter.setScheduledCourse(sc);
//            }
//        });
//
//
//        // Touch helper
//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
//                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
////                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
////                Toast.makeText(CourseDialog.class, "Course Deleted", Toast.LENGTH_SHORT).show();
//            }
//        }).attachToRecyclerView(scheduledCourseList);
//
//
//
//        // OnClick Fill Form
//        adapter.setOnItemClickListener(new ScheduledCourseAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(ScheduledCourse sc) {
//                Intent intent = new Intent(courseDialog.context, AddEditTermActivity.class);
//                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID_DISPLAY, String.valueOf(sc.getCourseID()));
//                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, sc.getCourseID());
//                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, String.valueOf(courseTermID));
//                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, sc.getCourseTitle());
//                intent.putExtra(AddEditCourseActivity.EXTRA_START, sc.getStartDate());
//                intent.putExtra(AddEditCourseActivity.EXTRA_END, sc.getEndDate());
//                activityUpdateResultLauncher.launch(intent);
//
//
//            }
//        });
//        return view;
//    }
//
//
////    // Save Course
////    private void saveScheduledCourse() {
////        String title = courseTitle.getText().toString();
////        String start = startDate.getText().toString();
////        String end = endDate.getText().toString();
////        String courseID = cID.getText().toString();
//////        String termID = tID.getText().toString();
////
////
////        Intent data = new Intent();
////        data.putExtra(EXTRA_TERM_ID, termID);
////        data.putExtra(EXTRA_TITLE, title);
////        data.putExtra(EXTRA_START, start);
////        data.putExtra(EXTRA_END, end);
////        data.putExtra(EXTRA_COURSE_ID, courseID);
////
////
//////        int id = addEditTermActivity.getIntent().getIntExtra(AddEditCourseActivity.EXTRA_COURSE_ID, -1);
//////        System.out.println("Save Course ID: " + id);
//////        if (id != -1) {
//////            data.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, id);
//////            System.out.println("Save Course IF ID: " + id);
//////        }
//////        System.out.println("Save Course ELSE ID: " + id);
////        addEditTermActivity.setResult(Activity.RESULT_OK, data);
////        addEditTermActivity.finish();
////
//////        courseID = id;
//////        System.out.println("courseID: " + courseID + "ID: " + id);
////
////
////    }
//
//
//    private final ActivityResultLauncher<Intent> activityUpdateResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK){
//                        String title = result.getData().getStringExtra(EXTRA_TITLE);
//                        String start = result.getData().getStringExtra(EXTRA_START);
//                        String end = result.getData().getStringExtra(EXTRA_END);
//                        int courseID = result.getData().getIntExtra(EXTRA_COURSE_ID, -1);
//
//                        System.out.println("Set Course ID: " + courseID);
//
//                        ScheduledCourse sc = new ScheduledCourse(title, start, end);
//                        sc.setCourseID(courseID);
//                        scViewModel.update(sc);
//
//
//                        Toast.makeText(addEditTermActivity, "Updated", Toast.LENGTH_SHORT).show();
//
//                    }else {
//                        Toast.makeText(addEditTermActivity, "NOT Updated", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );
//
//    // Button
//    private void closeDialog() {
//        courseDialog.dismiss();
//
//    }
//
//
//
//    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK){
//                        String title = result.getData().getStringExtra(EXTRA_TITLE);
//                        String start = result.getData().getStringExtra(EXTRA_START);
//                        String end = result.getData().getStringExtra(EXTRA_END);
//                        String termID = result.getData().getStringExtra(EXTRA_TERM_ID);
//                        int ID = Integer.parseInt(termID);
//
//                        ScheduledCourse sc = new ScheduledCourse(title, start, end);
//
//                        scViewModel.insert(sc);
//
//                        int cID = sc.getCourseID();
//
//                        System.out.println("cID: " + cID);
//
//                        Toast.makeText(addEditTermActivity, "Saved", Toast.LENGTH_SHORT).show();
//
//                    }else {
//                        Toast.makeText(addEditTermActivity, "Unsuccessful", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );
//
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof ScheduledCourseAdapter.OnCourseListener) {
//            courseListener = (ScheduledCourseAdapter.OnCourseListener) context;
//        } else {
//            throw new RuntimeException(context.toString() + " must implement OnInputListener");
//        }
//        try {
//            courseListener = (ScheduledCourseAdapter.OnCourseListener) getActivity();
//        }catch (ClassCastException e) {
//            System.out.println("onAttach: ClassCastException: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        listener = null;
//    }
//
//
////    private void initRecyclerView() {
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
////        scheduledCourseList.setLayoutManager(linearLayoutManager);
////        scAdapter = new ScheduledCourseAdapter(sc,courseListener);
////        scheduledCourseList.setAdapter(scAdapter);
////
////    }
//
////    public void addCourse() {
////        Intent intent = new Intent();
////        ScheduledCourse sc = intent.getParcelableExtra(SELECTED_COURSE);
////        String title = sc.getCourseTitle();
////        String start = sc.getStartDate();
////        String end = sc.getEndDate();
////        int courseID = sc.getCourseID();
////
////        intent.putExtra(AddEditTermActivity.EXTRA_TITLE, title);
////        intent.putExtra(AddEditTermActivity.EXTRA_START, start);
////        intent.putExtra(AddEditTermActivity.EXTRA_END, end);
////        intent.putExtra(AddEditTermActivity.EXTRA_COURSE_ID, courseID);
////        intent.putExtra(AddEditTermActivity.EXTRA_TERM_ID, courseTermID);
////
////        setResult(RESULT_OK, data);
////        finish();
////    }
//
//    @Override
//    public void onCourseClick(int position) {
//        ScheduledCourse scCourse = scList.get(position);
//        String title = scCourse.getCourseTitle();
//        String start = scCourse.getStartDate();
//        String end = scCourse.getEndDate();
//        int courseID = scCourse.getCourseID();
////        Course course = new Course(title, start, end, CourseDialog.courseTermID, courseID);
//    }
//
////    public void sendCourse(ScheduledCourse sc) {
////        Intent intent = new Intent();
////        intent.putExtra(AddEditTermActivity.SELECTED_COURSE, sc);
////        startActivity(intent);
////
////    }
//
//
//
//}
//
//
