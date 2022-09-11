package android.talorlevy.tjl_pa_final.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.talorlevy.tjl_pa_final.Database.Repository;
import android.talorlevy.tjl_pa_final.Entity.Assessment;
import android.talorlevy.tjl_pa_final.Entity.Course;
import android.talorlevy.tjl_pa_final.Entity.Term;
import android.talorlevy.tjl_pa_final.R;
import android.view.View;

public class Home extends AppCompatActivity {

    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Repository repo = new Repository(getApplication());
//        Term term = new Term(1, "Test Term", "07/01/22", "01/01/23");
//        repo.insert(term);


    }



    public void openTerms(View view) {
        Intent intent=new Intent(Home.this, TermList.class);
        startActivity(intent);
    }


}