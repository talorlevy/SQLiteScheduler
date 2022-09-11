package android.talorlevy.tjl_pa_final.DAO;


import android.talorlevy.tjl_pa_final.Entity.Assessment;
import android.talorlevy.tjl_pa_final.Entity.Course;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY id ASC")
    List<Assessment> getAllAssessments();

}
