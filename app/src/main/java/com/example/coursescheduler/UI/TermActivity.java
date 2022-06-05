package com.example.coursescheduler.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.Database.ScheduleRepo;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.Entity.Term;
import com.example.coursescheduler.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class TermActivity extends AppCompatActivity {

    private TermViewModel termViewModel;;
//    ScheduleRepo repository;
    int numTermID;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);

        ScheduleRepo repo=new ScheduleRepo(getApplication());

        // Button
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Intent intent = new Intent(TermActivity.this, AddEditTermActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

        // Recycler View
        RecyclerView recyclerView = findViewById(R.id.termRecycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);


        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTerms(terms);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            public List<Course> setList() {
                List<Course> assignedCourses = new ArrayList<>();
                for (Course c : repo.getAssignedTermID(ID)) {
                    if (c.getTermID() == ID)assignedCourses.add(c);
                }
                return assignedCourses;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Term currentTerm = adapter.getTermAt(viewHolder.getAdapterPosition());
                int termID = currentTerm.getTermID();
                ID = termID;

                if (setList().isEmpty()) {
                    repo.deleteTerm(currentTerm);
                    Toast.makeText(TermActivity.this, "Term Deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TermActivity.this, currentTerm.getTermTitle() + " Courses assigned. Please delete Courses.", Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(adapter);
                }
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent(TermActivity.this, AddEditTermActivity.class);
                intent.putExtra(AddEditTermActivity.EXTRA_ID, term.getTermID());
                intent.putExtra(AddEditTermActivity.EXTRA_ID_DISPLAY, String.valueOf(term.getTermID()));
                intent.putExtra(AddEditTermActivity.EXTRA_TITLE, term.getTermTitle());
                intent.putExtra(AddEditTermActivity.EXTRA_START, term.getStartDate());
                intent.putExtra(AddEditTermActivity.EXTRA_END, term.getEndDate());

                activityUpdateResultLauncher.launch(intent);
            }
        });
    }



    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(AddEditTermActivity.EXTRA_TITLE);
                        String start = result.getData().getStringExtra(AddEditTermActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditTermActivity.EXTRA_END);

                        Term term = new Term(title, start, end);

                        termViewModel.insert(term);


                        Toast.makeText(TermActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(TermActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.terms_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.delete_all_terms:
                termViewModel.deleteAllTerms();
                Toast.makeText(this, "All terms deleted", Toast.LENGTH_SHORT).show();
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
                    int ID = result.getData().getIntExtra(AddEditTermActivity.EXTRA_ID, -1);
                    if (result.getResultCode() == Activity.RESULT_OK){
                        String title = result.getData().getStringExtra(AddEditTermActivity.EXTRA_TITLE);
                        String start = result.getData().getStringExtra(AddEditTermActivity.EXTRA_START);
                        String end = result.getData().getStringExtra(AddEditTermActivity.EXTRA_END);

                        Term term = new Term(title, start, end);
                        term.setTermID(ID);
                        termViewModel.update(term);

                        Toast.makeText(TermActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(TermActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}
