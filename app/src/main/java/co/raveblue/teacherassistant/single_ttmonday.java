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

public class single_ttmonday extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mLectureSingleName;
    private TextView mLectureSingleFrom;
    private TextView mLectureSingleTo;
    private TextView mLectureSingleCourse;
    private TextView mLectureSingleDivision;

    private Button mLectureRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ttmonday);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttmonday");
        mDatabase.keepSynced(true);

        mPost_key = getIntent().getExtras().getString("ttmonday_id");

        mLectureSingleName = (TextView) findViewById(R.id.singleLectureName);
        mLectureSingleFrom = (TextView) findViewById(R.id.singleFromTime);
        mLectureSingleTo = (TextView) findViewById(R.id.singleToTime);
        mLectureSingleCourse = (TextView) findViewById(R.id.singleOfCourse);
        mLectureSingleDivision = (TextView) findViewById(R.id.singleOfDivision);

        mLectureRemoveBtn = (Button) findViewById(R.id.singleLectureRemoveBtn);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_lecture = (String) dataSnapshot.child("mondaylect_name").getValue();
                String post_from = (String) dataSnapshot.child("mondayfrom_time").getValue();
                String post_to = (String) dataSnapshot.child("mondayto_time").getValue();
                String post_course = (String) dataSnapshot.child("mondayof_course").getValue();
                String post_division = (String) dataSnapshot.child("mondayof_division").getValue();

                mLectureSingleName.setText(post_lecture);
                mLectureSingleFrom.setText(post_from);
                mLectureSingleTo.setText(post_to);
                mLectureSingleCourse.setText(post_course);
                mLectureSingleDivision.setText(post_division);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLectureRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(single_ttmonday.this, ttmonday.class);
                startActivity(mainIntent);

            }
        });

    }

    public void Startedit_notify(View view) {

        Intent intent = new Intent(single_ttmonday.this, ReminderEditActivity.class);
        startActivity(intent);

    }
}
