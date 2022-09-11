package android.talorlevy.tjl_pa_final.DAO;


import android.talorlevy.tjl_pa_final.Entity.Course;
import android.talorlevy.tjl_pa_final.Entity.Term;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY id ASC")
    List<Course> getAllCourses();

}
