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

public class single_ttwednesday extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mLectureSingleNamewed;
    private TextView mLectureSingleFromwed;
    private TextView mLectureSingleTowed;
    private TextView mLectureSingleCoursewed;
    private TextView mLectureSingleDivisionwed;

    private Button mLectureRemoveBtnwed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ttwednesday);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttwednesday");
        mDatabase.keepSynced(true);

        mPost_key = getIntent().getExtras().getString("ttwednesday_id");

        mLectureSingleNamewed = (TextView) findViewById(R.id.singleLectureNamewed);
        mLectureSingleFromwed = (TextView) findViewById(R.id.singleFromTimewed);
        mLectureSingleTowed = (TextView) findViewById(R.id.singleToTimewed);
        mLectureSingleCoursewed = (TextView) findViewById(R.id.singleOfCoursewed);
        mLectureSingleDivisionwed = (TextView) findViewById(R.id.singleOfDivisionwed);

        mLectureRemoveBtnwed = (Button) findViewById(R.id.singleLectureRemoveBtnwed);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_lecture = (String) dataSnapshot.child("wednesdaylect_name").getValue();
                String post_from = (String) dataSnapshot.child("wednesdayfrom_time").getValue();
                String post_to = (String) dataSnapshot.child("wednesdayto_time").getValue();
                String post_course = (String) dataSnapshot.child("wednesdayof_course").getValue();
                String post_division = (String) dataSnapshot.child("wednesdayof_division").getValue();

                mLectureSingleNamewed.setText(post_lecture);
                mLectureSingleFromwed.setText(post_from);
                mLectureSingleTowed.setText(post_to);
                mLectureSingleCoursewed.setText(post_course);
                mLectureSingleDivisionwed.setText(post_division);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLectureRemoveBtnwed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(single_ttwednesday.this, ttwednesday.class);
                startActivity(mainIntent);

            }
        });

    }
}
