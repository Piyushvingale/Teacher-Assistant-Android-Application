package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleEditPerformance extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mStudentSingleName;

    private EditText mPostMarks1;
    private EditText mPostMarks2;
    private EditText mPostAttendance;
    private EditText mPostTotalLectures;
    private EditText mPostOverall;
    private EditText mPostActivites;

    private Button mPostBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_edit_performance);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mPost_key = getIntent().getExtras().getString("student_id");

        mStudentSingleName = (TextView) findViewById(R.id.performance_student_name);

        mPostMarks1 = (EditText)findViewById(R.id.performance_student_marks1);
        mPostMarks2 = (EditText)findViewById(R.id.performance_student_marks2);
        mPostAttendance = (EditText)findViewById(R.id.performance_student_attendance);
        mPostTotalLectures = (EditText)findViewById(R.id.performance_student_totallectures);
        mPostOverall = (EditText)findViewById(R.id.performance_student_overall);
        mPostActivites = (EditText)findViewById(R.id.performance_student_actpart);

        mPostBtn = (Button)findViewById(R.id.performance_student_submit);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_name = (String) dataSnapshot.child("st_name").getValue();
                String post_marks1 = (String) dataSnapshot.child("marks1").getValue();
                String post_marks2 = (String) dataSnapshot.child("marks2").getValue();
                String post_attendance = (String)dataSnapshot.child("attendance").getValue();
                String post_totlect = (String)dataSnapshot.child("totallecture").getValue();
                String post_overall = (String)dataSnapshot.child("overallper").getValue();
                String post_actpart = (String)dataSnapshot.child("actpart").getValue();

                mStudentSingleName.setText(post_name);
                mPostMarks1.setText(post_marks1);
                mPostMarks2.setText(post_marks2);
                mPostAttendance.setText(post_attendance);
                mPostTotalLectures.setText(post_totlect);
                mPostOverall.setText(post_overall);
                mPostActivites.setText(post_actpart);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });


    }

    private void startPosting() {

        String marks1_val = mPostMarks1.getText().toString().trim();
        String marks2_val = mPostMarks2.getText().toString().trim();
        String attendance_val = mPostAttendance.getText().toString().trim();
        String totlect_val = mPostTotalLectures.getText().toString().trim();
        String overall_val = mPostOverall.getText().toString().trim();
        String activities = mPostActivites.getText().toString().trim();


        DatabaseReference newPost = FirebaseDatabase.getInstance().getReference("Student_Info").child(mPost_key);

            newPost.child("marks1").setValue(marks1_val);
            newPost.child("marks2").setValue(marks2_val);
            newPost.child("attendance").setValue(attendance_val);
            newPost.child("totallecture").setValue(totlect_val);
            newPost.child("overallper").setValue(overall_val);
            newPost.child("actpart").setValue(activities);

            startActivity(new Intent(SingleEditPerformance.this, Performance.class));

    }


}
