package android.talorlevy.tjl_pa_final.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.talorlevy.tjl_pa_final.Database.Repository;
import android.talorlevy.tjl_pa_final.Entity.Assessment;
import android.talorlevy.tjl_pa_final.Entity.Course;
import android.talorlevy.tjl_pa_final.Entity.Term;
import android.talorlevy.tjl_pa_final.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {

    EditText editTitle;
    EditText editStart;
    EditText editEnd;

    String title;
    String start;
    String end;

    int id;
    Repository repository;

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    final Calendar myCalendarStart = Calendar.getInstance();
    String myFormat;
    SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        editTitle = findViewById(R.id.editTitle);
        editStart = findViewById(R.id.editStart);
        editEnd = findViewById(R.id.editEnd);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");

        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Repository repo = new Repository(getApplication());
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for(Course course : repo.getAllCourses()) {
            if(course.getTermId() == id) filteredCourses.add(course);
        }
        adapter.setCourses(filteredCourses);
        repository = new Repository(getApplication());

        editStart = findViewById(R.id.editStart);
        editEnd = findViewById(R.id.editEnd);
        myFormat = "MM/dd/yy";
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info = editStart.getText().toString();
                if (info.equals("")) info = "07/01/22";
                try {
                    myCalendarStart.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Date date;
                String info = editEnd.getText().toString();
                if (info.equals("")) info = "07/01/22";
                try {
                    myCalendarEnd.setTime(sdf.parse(info));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };

    }


    private void updateLabelStart() {
        editStart.setText(sdf.format(myCalendarStart.getTime()));
    }


    private void updateLabelEnd() {
        editEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_termlist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void openCourseDetails(View view) {
        Intent intent=new Intent(TermDetails.this, CourseDetails.class);
        startActivity(intent);
    }



    public void saveButton(View view) {
        Term term;
        int newId;
        if(repository.getAllTerms().isEmpty()) {
            newId = 1;
            term = new Term(newId, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.insert(term);
            Toast.makeText(TermDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermDetails.this, TermList.class);
            startActivity(intent);
            return;
        }

        if (id == -1) {
            int newID = repository.getAllTerms().get(repository.getAllTerms().size() - 1).getId() + 1;
            term = new Term(newID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.insert(term);
            Toast.makeText(TermDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermDetails.this, TermList.class);
            startActivity(intent);
        }
        else {
            term = new Term(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString());
            repository.update(term);
            Toast.makeText(TermDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermDetails.this, TermList.class);
            startActivity(intent);
        }
    }



    public void refreshCourses(View view) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Repository repo = new Repository(getApplication());
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for(Course course : repo.getAllCourses()) {
            if(course.getTermId() == id) filteredCourses.add(course);
        }
        adapter.setCourses(filteredCourses);
        Toast.makeText(TermDetails.this, "Courses refreshed!", Toast.LENGTH_LONG).show();
    }



    public void deleteTerm(View view) {
        int numCourses = 0;
        for (Course course : repository.getAllCourses()) {
            if (course.getTermId() == id) ++numCourses;
        }
        if (numCourses == 0) {
            for (Term term : repository.getAllTerms()) {
                if (term.getId() == id) repository.delete(term);
            }
            Toast.makeText(TermDetails.this, title + " deleted!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TermDetails.this, TermList.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(TermDetails.this, "Can't delete a term with courses!", Toast.LENGTH_LONG).show();
        }
    }


}