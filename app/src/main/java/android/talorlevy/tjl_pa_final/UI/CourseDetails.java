package android.talorlevy.tjl_pa_final.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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

public class CourseDetails extends AppCompatActivity {

    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;
    EditText editTermId;
    EditText editOptionalNotes;

    String title;
    String start;
    String end;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    int termId;
    String optionalNotes;

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
        setContentView(R.layout.activity_course_details);

        editTitle = findViewById(R.id.editTitle);
        editStart = findViewById(R.id.editStart);
        editEnd = findViewById(R.id.editEnd);
        editStatus = findViewById(R.id.editStatus);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);
        editTermId = findViewById(R.id.editTermId);
        editOptionalNotes = findViewById(R.id.editOptionalNotes);

        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructor name");
        instructorPhone = getIntent().getStringExtra("instructor phone");
        instructorEmail = getIntent().getStringExtra("instructor email");
        termId = getIntent().getIntExtra("term id", -1);
        optionalNotes = getIntent().getStringExtra("optional notes");

        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);
        editStatus.setText(status);
        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);

        if (optionalNotes != null) editOptionalNotes.setText(optionalNotes);
        if (termId != -1) editTermId.setText(Integer.toString(termId));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Repository repo = new Repository(getApplication());
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment assessment : repo.getAllAssessments()) {
            if(assessment.getId() == id) filteredAssessments.add(assessment);
        }
        adapter.setAssessments(filteredAssessments);
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
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarStart
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
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarEnd
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
        getMenuInflater().inflate(R.menu.menu_courselist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, editOptionalNotes.getText().toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, editTitle.getText().toString());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            case R.id.notifystart:
                String dateFromScreen = editStart.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    Toast.makeText(CourseDetails.this, "Please enter correctly formatted start date! (MM/dd/yy)", Toast.LENGTH_LONG).show();
                    return true;
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(CourseDetails.this, MyReceiver.class);
                intent.putExtra("key", "A course starts today! (" + editTitle.getText() + ")");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this, Home.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(CourseDetails.this, "Notification created for start date!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.notifyend:
                String dateFromScreen1 = editEnd.getText().toString();
                Date myDate1 = null;
                try {
                    myDate1 = sdf.parse(dateFromScreen1);
                } catch (ParseException e) {
                    Toast.makeText(CourseDetails.this, "Please enter correctly formatted end date! (MM/dd/yy)", Toast.LENGTH_LONG).show();
                    return true;
                }
                Long trigger1 = myDate1.getTime();
                Intent intent1 = new Intent(CourseDetails.this, MyReceiver.class);
                intent1.putExtra("key", "A course ends today! (" + editTitle.getText() + ")");
                PendingIntent sender1 = PendingIntent.getBroadcast(CourseDetails.this, Home.numAlert++, intent1, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger1, sender1);
                Toast.makeText(CourseDetails.this, "Notification created for end date!", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void openAssessmentDetails(View view) {
        Intent intent=new Intent(CourseDetails.this, AssessmentDetails.class);
        startActivity(intent);
    }




    public void saveButton(View view) {
        try {
            Course course;
            int validTermId = Integer.parseInt(editTermId.getText().toString());
            int num = 0;
            int newID;
            for (Term term : repository.getAllTerms()) {
                if (term.getId() == validTermId) num++;
            }

            if(repository.getAllCourses().isEmpty() && num != 0) {
                newID = 1;
                course = new Course(newID, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), Integer.parseInt(editTermId.getText().toString()), editOptionalNotes.getText().toString());
                repository.insert(course);
                Toast.makeText(CourseDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CourseDetails.this, TermList.class);
                startActivity(intent);
                return;
            }
            if (num != 0) {
                if (id == -1) {
                    int newId = repository.getAllCourses().get(repository.getAllCourses().size() - 1).getId() + 1;
                    course = new Course(newId, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), Integer.parseInt(editTermId.getText().toString()), editOptionalNotes.getText().toString());
                    repository.insert(course);
                    Toast.makeText(CourseDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CourseDetails.this, TermList.class);
                    startActivity(intent);
                } else {
                    course = new Course(id, editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), editStatus.getText().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString(), Integer.parseInt(editTermId.getText().toString()), editOptionalNotes.getText().toString());
                    repository.update(course);
                    Toast.makeText(CourseDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CourseDetails.this, TermList.class);
                    startActivity(intent);
                }
            }
            if (num == 0) {
                Toast.makeText(CourseDetails.this, "Please enter an active term ID!", Toast.LENGTH_LONG).show();
            }
        }
        catch (NumberFormatException e) {
            Toast.makeText(CourseDetails.this, "Please enter an active term ID!", Toast.LENGTH_LONG).show();
        }
    }


    public void refreshAssessments(View view) {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Repository repo = new Repository(getApplication());
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for(Assessment assessment : repo.getAllAssessments()) {
            if(assessment.getCourseId() == id) filteredAssessments.add(assessment);
        }
        adapter.setAssessments(filteredAssessments);
        Toast.makeText(CourseDetails.this, "Assessments refreshed!", Toast.LENGTH_LONG).show();
    }

    public void deleteCourse(View view) {
        for (Course course : repository.getAllCourses()) {
            if (course.getId() == id) repository.delete(course);
        }

        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getCourseId() == id) repository.delete(assessment);
        }

        Toast.makeText(CourseDetails.this, editTitle.getText().toString() + " and associated assessments deleted!", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(CourseDetails.this, TermList.class);
        startActivity(intent);
    }
}