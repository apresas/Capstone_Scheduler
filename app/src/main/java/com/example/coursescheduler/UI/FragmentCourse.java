//package com.example.coursescheduler.UI;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.coursescheduler.Entity.Assessment;
//import com.example.coursescheduler.Entity.Course;
//import com.example.coursescheduler.Entity.ScheduledCourse;
//import com.example.coursescheduler.R;
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.textfield.TextInputLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link FragmentCourse#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class FragmentCourse extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String TITLE = "title";
//    private static final String START = "start";
//    private static final String END = "end";
//    private static final String COURSEID = "courseID";
//    private static final String TERMID = "termID";
//
//
//
//    // TODO: Rename and change types of parameters
//    private String fragmentTitle;
//    private String fragmentStart;
//    private String fragmentEnd;
//    private int fragmentCourseID;
//    private int fragmentCourseTermID;
//    private TextInputLayout courseSearch;
//    private RecyclerView scheduledCourseRecyclerView;
//    private FloatingActionButton confirmBtn;
//    private Button closeBtn;
//    private Button addBtn;
//    private OnFragmentInteractionListener mListener;
//    private View courseFragment;
//    static int courseTermID;
//    ScheduledCourse scheduledCourse;
//    AddEditTermActivity addEditTermActivity;
//    public List<ScheduledCourse> sc;
//    ScheduledCourseAdapter.OnFragmentInteractionListener iListener;
//    ScheduledCourseAdapter.ScheduledCourseHolder holder;
//    ScheduledCourseViewModel scViewModel;
//
//
//
//
//
//    public FragmentCourse() {
//        // Required empty public constructor
//    }
//
//
//    // TODO: Rename and change types and number of parameters
//    public static FragmentCourse newInstance() {
//        FragmentCourse fragment = new FragmentCourse();
//        Bundle args = new Bundle();
////        args.putString(TITLE, title);
////        args.putString(START, start);
////        args.putString(END, end);
////        args.putInt(COURSEID, courseID);
////        args.putInt(TERMID, courseTermID);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            fragmentTitle = getArguments().getString(TITLE);
//            fragmentStart = getArguments().getString(START);
//            fragmentEnd = getArguments().getString(END);
//            fragmentCourseID = getArguments().getInt(COURSEID);
//            fragmentCourseTermID = getArguments().getInt(TERMID);
//            sc = new ArrayList<>();
//
//
//
//
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        courseFragment = inflater.inflate(R.layout.fragment_course, container, false);
//        courseSearch = (TextInputLayout) courseFragment.findViewById(R.id.search_field);
//        confirmBtn = (FloatingActionButton) courseFragment.findViewById(R.id.button_add_confirm);
//        closeBtn = (Button) courseFragment.findViewById(R.id.close_btn);
//        addBtn = (Button) courseFragment.findViewById(R.id.add_course_btn);
//
//
//        // Recycler View
//
//        scheduledCourseRecyclerView = (RecyclerView) courseFragment.findViewById(R.id.popupCourseRecyclerView);
//        scheduledCourseRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        scheduledCourseRecyclerView.setHasFixedSize(true);
//
//        // Adapter
//        final ScheduledCourseAdapter adapter = new ScheduledCourseAdapter(sc);
//        scheduledCourseRecyclerView.setAdapter(adapter);
//
//        // View Model
//        scViewModel = new ViewModelProvider(this).get(ScheduledCourseViewModel.class);
//
//        scViewModel.getAllScheduledCourses().observe(getViewLifecycleOwner(), new Observer<List<ScheduledCourse>>() {
//            @Override
//            public void onChanged(List<ScheduledCourse> scCourses) {
//                adapter.setScheduledCourse(scCourses);
////                scAdapter.setScheduledCourse(scCourses);
//            }
//        });
//
////        // OnClick Fill Form
////        adapter.setOnItemClickListener(new ScheduledCourseAdapter.OnItemClickListener() {
////            @Override
////            public void onItemClick(ScheduledCourse scheduledCourse) {
//////                goToCourseDetails();
////                Intent intent = new Intent(AddEditCourseActivity.this, AddEditAssessmentActivity.class);
////                intent.putExtra(AddEditCourseActivity.EXTRA_TERM_ID, String.valueOf(courseTermID));
////                intent.putExtra(AddEditCourseActivity.EXTRA_COURSE_ID, scheduledCourse.getCourseID());
////                intent.putExtra(AddEditCourseActivity.EXTRA_TITLE, String.valueOf(scheduledCourse.getCourseTitle()));
////                intent.putExtra(AddEditCourseActivity.EXTRA_START, scheduledCourse.getStartDate());
////                intent.putExtra(AddEditCourseActivity.EXTRA_END, scheduledCourse.getEndDate());
////
////                activityUpdateResultLauncher.launch(intent);
////
////            }
////        });
//
//
//
//        closeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                rootview.getContext().startActivity(intent);
////                sendBack();
//            }
//        });
//
//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//
//        return courseFragment;
//    }
//
////    public void sendCourse(String title, String start, String end, int courseID, int termID) {
////        if (mListener != null) {
////            mListener.onFragmentInteraction(title, start, end, courseID, termID);
////
////        }
////
////    }
//
//    public static class sendCourseViewHolder extends RecyclerView.ViewHolder {
//        Button addCourseBtn;
//        TextView courseTitle, courseID, termID, start, end;
//        public sendCourseViewHolder(@NonNull View itemView, ViewGroup parent) {
//            super(itemView);
//            View v = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.course_item_add, parent, false);
//
//            courseTitle = v.findViewById(R.id.text_view_add_course_title);
//            courseID = v.findViewById(R.id.text_view_add_courseID);
//            termID = v.findViewById(R.id.edit_add_termID);
//            start = itemView.findViewById(R.id.edit_add_course_start);
//            end = itemView.findViewById(R.id.edit_add_course_end);
//            addCourseBtn = itemView.findViewById(R.id.add_course_btn);
//
//            addCourseBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("TEST CLICK");
//                }
//            });
//
//
//        }
//    }
//
//
////    public void sendBack(String title, String start, String end, int courseID, int termID) {
////        if (mListener != null) {
////
////            mListener.onFragmentInteraction(title, start, end, courseID, termID);
////        }
////    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
////        ScheduledCourseAdapter adapter = new ScheduledCourseAdapter(sc, holder);
////        scheduledCourseRecyclerView.setAdapter(adapter);
//    }
//
//
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener)  context;
//        }else {
//            throw new RuntimeException(context + "must implement OnFragmentInteractionListener");
//
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnFragmentInteractionListener {
////        void onFragmentInteraction(String title, String start, String end, int courseID, int termID);
//        void goToCourseDetails(ScheduledCourse course);
//    }
//
//
//
//
//}