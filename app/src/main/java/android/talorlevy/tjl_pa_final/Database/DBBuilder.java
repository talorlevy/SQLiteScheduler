package android.talorlevy.tjl_pa_final.Database;


import android.content.Context;
import android.talorlevy.tjl_pa_final.DAO.AssessmentDAO;
import android.talorlevy.tjl_pa_final.DAO.CourseDAO;
import android.talorlevy.tjl_pa_final.DAO.TermDAO;
import android.talorlevy.tjl_pa_final.Entity.Assessment;
import android.talorlevy.tjl_pa_final.Entity.Course;
import android.talorlevy.tjl_pa_final.Entity.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class}, version = 18, exportSchema = false)
public abstract class DBBuilder extends RoomDatabase {

    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile DBBuilder INSTANCE;

    static DBBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Builder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DBBuilder.class, "theDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
