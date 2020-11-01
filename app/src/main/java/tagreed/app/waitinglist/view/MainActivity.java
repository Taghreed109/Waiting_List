package tagreed.app.waitinglist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import tagreed.app.waitinglist.R;
import tagreed.app.waitinglist.database.DatabaseHelper;
import tagreed.app.waitinglist.database.model.Student;

public class MainActivity extends AppCompatActivity {

    RecyclerView students_rv;
    TextView EmptyStudents;
    FloatingActionButton fab;

    private DatabaseHelper db;

    private StudentsAdapter Adapter;
    private ArrayList<Student> StudentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getStudents();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NewStudent.class);
                startActivity(intent);
            }
        });
    }

    void init() {

        EmptyStudents = findViewById(R.id.empty_students);
        students_rv = findViewById(R.id.students_rv);
        fab = findViewById(R.id.fab);

        StudentsList = new ArrayList<>();
        db = new DatabaseHelper(getApplicationContext());
        Adapter = new StudentsAdapter(StudentsList,MainActivity.this,db);
    }
    void getStudents() {
        StudentsList.addAll(db.getAllStudents());
        if (db.getStudentsCount()>0){
            System.out.println("------------------there is items ---------------");
            E_Student(true); // there is students
        }else {
            E_Student(false); // empty students
        }
        setRecyclerData();
    }
    void  setRecyclerData(){
        students_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        students_rv.setItemAnimator(new DefaultItemAnimator());
        students_rv.setAdapter(Adapter);
    }
    void E_Student(boolean found){
        if (found){
            EmptyStudents.setVisibility(View.INVISIBLE);
        }else {
            EmptyStudents.setVisibility(View.VISIBLE);
        }
    }
}