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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {

    EditText editType;
    EditText editTitle;
    EditText editStart;
    EditText editEnd;
    EditText editCourseId;

    int id;
    String type;
    String title;
    String start;
    String end;
    int courseId;

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
        setContentView(R.layout.activity_assessment_details);

        editType = findViewById(R.id.editType);
        editTitle = findViewById(R.id.editTitle);
        editStart = findViewById(R.id.editStart);
        editEnd = findViewById(R.id.editEnd);
        editCourseId = findViewById(R.id.editCourseId);

        id = getIntent().getIntExtra("id", -1);
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        start = getIntent().getStringExtra("start");
        end = getIntent().getStringExtra("end");
        courseId = getIntent().getIntExtra("course id", -1);

        editType.setText(type);
        editTitle.setText(title);
        editStart.setText(start);
        editEnd.setText(end);

        if (courseId != -1) editCourseId.setText(Integer.toString(courseId));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                new DatePickerDialog(AssessmentDetails.this, startDate, myCalendarStart
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
                new DatePickerDialog(AssessmentDetails.this, endDate, myCalendarEnd
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
        getMenuInflater().inflate(R.menu.menu_assessmentlist, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifystart:
                String dateFromScreen = editStart.getText().toString();
                Date myDate = null;
                try {
                    myDate = sdf.parse(dateFromScreen);
                } catch (ParseException e) {
                    Toast.makeText(AssessmentDetails.this, "Please enter correctly formatted start date! (MM/dd/yy)", Toast.LENGTH_LONG).show();
                    return true;
                }
                Long trigger = myDate.getTime();
                Intent intent = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent.putExtra("key", "An assessment starts today! (" + editTitle.getText() + ")");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this, Home.numAlert++, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
                Toast.makeText(AssessmentDetails.this, "Notification created for start date!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.notifyend:
                String dateFromScreen1 = editEnd.getText().toString();
                Date myDate1 = null;
                try {
                    myDate1 = sdf.parse(dateFromScreen1);
                } catch (ParseException e) {
                    Toast.makeText(AssessmentDetails.this, "Please enter correctly formatted end date! (MM/dd/yy)", Toast.LENGTH_LONG).show();
                    return true;
                }
                Long trigger1 = myDate1.getTime();
                Intent intent1 = new Intent(AssessmentDetails.this, MyReceiver.class);
                intent1.putExtra("key", "An assessment ends today! (" + editTitle.getText() + ")");
                PendingIntent sender1 = PendingIntent.getBroadcast(AssessmentDetails.this, Home.numAlert++, intent1, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP, trigger1, sender1);
                Toast.makeText(AssessmentDetails.this, "Notification created for end date!", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void saveButton(View view) {

        try {
            Assessment assessment;
            int validCourseId = Integer.parseInt(editCourseId.getText().toString());
            int num = 0;
            int newID;

            for (Course course : repository.getAllCourses()) {
                if (course.getId() == validCourseId) num++;
            }


            if(repository.getAllAssessments().isEmpty() && num != 0) {
                newID = 1;
                assessment = new Assessment(newID, editType.getText().toString(), editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), Integer.parseInt(editCourseId.getText().toString()));
                repository.insert(assessment);
                Toast.makeText(AssessmentDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                startActivity(intent);
                return;
            }


            if (num != 0) {
                if (id == -1) {
                    int newId = repository.getAllAssessments().get(repository.getAllAssessments().size() - 1).getId() + 1;
                    assessment = new Assessment(newId, editType.getText().toString(), editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), Integer.parseInt(editCourseId.getText().toString()));
                    repository.insert(assessment);
                    Toast.makeText(AssessmentDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                    startActivity(intent);
                } else {
                    assessment = new Assessment(id, editType.getText().toString(), editTitle.getText().toString(), editStart.getText().toString(), editEnd.getText().toString(), Integer.parseInt(editCourseId.getText().toString()));
                    repository.update(assessment);
                    Toast.makeText(AssessmentDetails.this, editTitle.getText().toString() + " saved!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AssessmentDetails.this, TermList.class);
                    startActivity(intent);
                }
            }
            if (num == 0) {
                Toast.makeText(AssessmentDetails.this, "Please enter an active course ID!", Toast.LENGTH_LONG).show();
            }
        }
        catch (NumberFormatException e) {
            Toast.makeText(AssessmentDetails.this, "Please enter an active course ID!", Toast.LENGTH_LONG).show();
        }
    }





    public void deleteAssessment(View view) {
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getId() == id) repository.delete(assessment);
        }
        Toast.makeText(AssessmentDetails.this, editTitle.getText().toString() + " deleted!", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(AssessmentDetails.this, TermList.class);
        startActivity(intent);
    }
}