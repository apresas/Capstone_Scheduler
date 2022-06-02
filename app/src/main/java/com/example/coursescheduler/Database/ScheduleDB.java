package com.example.coursescheduler.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.coursescheduler.DAO.AssessmentDAO;
import com.example.coursescheduler.DAO.CourseDAO;
import com.example.coursescheduler.DAO.InstructorDAO;
import com.example.coursescheduler.DAO.NoteDAO;
import com.example.coursescheduler.DAO.ScheduledCourseDAO;
import com.example.coursescheduler.DAO.TermDAO;
import com.example.coursescheduler.Entity.Assessment;
import com.example.coursescheduler.Entity.Course;
import com.example.coursescheduler.Entity.Instructor;
import com.example.coursescheduler.Entity.Note;
import com.example.coursescheduler.Entity.ScheduledCourse;
import com.example.coursescheduler.Entity.Term;

@Database(entities = {Term.class, Course.class, Assessment.class, Note.class, Instructor.class, ScheduledCourse.class}, version = 28)
public abstract class ScheduleDB extends RoomDatabase {
    private static ScheduleDB instance;
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract NoteDAO noteDAO();
    public abstract InstructorDAO instructorDAO();
    public abstract ScheduledCourseDAO scDAO();

    public static synchronized  ScheduleDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ScheduleDB.class, "scheduler_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDAO termDAO;
        private PopulateDbAsyncTask(ScheduleDB db) {termDAO = db.termDAO();}

        @Override
        protected  Void doInBackground(Void... voids) {
//            termDAO.insert(new Term( 1,"Term 1", "01/01/22", "07/01/22"));
//            termDAO.insert(new Term( 2,"Spring Term", "03/01/22", "09/01/22"));
//            termDAO.insert(new Term( 3, "Term 2", "07/01/22", "01/01/23"));
            return null;
        }
    }

}
