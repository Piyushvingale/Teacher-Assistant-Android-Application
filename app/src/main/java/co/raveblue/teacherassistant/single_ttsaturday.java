package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class single_ttsaturday extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mLectureSingleNamesat;
    private TextView mLectureSingleFromsat;
    private TextView mLectureSingleTosat;
    private TextView mLectureSingleCoursesat;
    private TextView mLectureSingleDivisionsat;

    private Button mLectureRemoveBtnsat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ttsaturday);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttsaturday");
        mDatabase.keepSynced(true);


        mPost_key = getIntent().getExtras().getString("ttsaturday_id");

        mLectureSingleNamesat = (TextView) findViewById(R.id.singleLectureNamesat);
        mLectureSingleFromsat = (TextView) findViewById(R.id.singleFromTimesat);
        mLectureSingleTosat = (TextView) findViewById(R.id.singleToTimesat);
        mLectureSingleCoursesat = (TextView) findViewById(R.id.singleOfCoursesat);
        mLectureSingleDivisionsat = (TextView) findViewById(R.id.singleOfDivisionsat);

        mLectureRemoveBtnsat = (Button) findViewById(R.id.singleLectureRemoveBtnsat);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_lecture = (String) dataSnapshot.child("saturdaylect_name").getValue();
                String post_from = (String) dataSnapshot.child("saturdayfrom_time").getValue();
                String post_to = (String) dataSnapshot.child("saturdayto_time").getValue();
                String post_course = (String) dataSnapshot.child("saturdayof_course").getValue();
                String post_division = (String) dataSnapshot.child("saturdayof_division").getValue();

                mLectureSingleNamesat.setText(post_lecture);
                mLectureSingleFromsat.setText(post_from);
                mLectureSingleTosat.setText(post_to);
                mLectureSingleCoursesat.setText(post_course);
                mLectureSingleDivisionsat.setText(post_division);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLectureRemoveBtnsat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(single_ttsaturday.this, ttsaturday.class);
                startActivity(mainIntent);

            }
        });

    }
}
