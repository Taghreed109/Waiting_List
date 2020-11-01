package tagreed.app.waitinglist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import tagreed.app.waitinglist.R;
import tagreed.app.waitinglist.database.DatabaseHelper;
import tagreed.app.waitinglist.database.model.Student;

public class EditStudent extends AppCompatActivity {


    Student student;

    TextView StudentName;
    RadioGroup rd_group;
    RadioButton rc_grad;
    RadioButton rc_4th;
    RadioButton rc_3rd;
    RadioButton rc_2nd;
    RadioButton rc_1st;

    Button EditBt;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);


        init();

        EditBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {

                    int selectedId = rd_group.getCheckedRadioButtonId();
                    RadioButton rb_choice = findViewById(selectedId);


                    student.setName(StudentName.getText().toString());
                    student.setPriority(rb_choice.getText().toString());

                    db.updateStudent(student);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Fill All Data", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    boolean Validate() {
        if (!StudentName.getText().toString().isEmpty() && rd_group.getCheckedRadioButtonId() != -1) {
            return true;
        } else {
            return false;
        }
    }

    void init() {

        StudentName = findViewById(R.id.name);
        rd_group = findViewById(R.id.rd_group);

        rc_grad = findViewById(R.id.rb_1);
        rc_4th = findViewById(R.id.rb_2);
        rc_3rd = findViewById(R.id.rb_3);
        rc_2nd = findViewById(R.id.rb_4);
        rc_1st = findViewById(R.id.rb_5);


        EditBt = findViewById(R.id.edit_st);

        db = new DatabaseHelper(getApplicationContext());

        long StudentId = getIntent().getLongExtra("S_Id", 0);
        student = db.getStudent(StudentId);

        StudentName.setText(student.getName());

        if (rc_grad.getText().toString().equals(student.getPriority())) {
            rc_grad.setChecked(true);

        } else if (rc_4th.getText().toString().equals(student.getPriority())) {
            rc_4th.setChecked(true);

        } else if (rc_3rd.getText().toString().equals(student.getPriority())) {
            rc_3rd.setChecked(true);

        } else if (rc_2nd.getText().toString().equals(student.getPriority())) {
            rc_2nd.setChecked(true);

        } else if (rc_1st.getText().toString().equals(student.getPriority())) {
            rc_1st.setChecked(true);
        }

    }

}