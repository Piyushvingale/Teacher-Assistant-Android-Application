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

public class single_tttuesday extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mLectureSingleNametues;
    private TextView mLectureSingleFromtues;
    private TextView mLectureSingleTotues;
    private TextView mLectureSingleCoursetues;
    private TextView mLectureSingleDivisiontues;

    private Button mLectureRemoveBtntues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_tttuesday);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tttuesday");
        mDatabase.keepSynced(true);

        mPost_key = getIntent().getExtras().getString("tttuesday_id");

        mLectureSingleNametues = (TextView) findViewById(R.id.singleLectureNametues);
        mLectureSingleFromtues = (TextView) findViewById(R.id.singleFromTimetues);
        mLectureSingleTotues = (TextView) findViewById(R.id.singleToTimetues);
        mLectureSingleCoursetues = (TextView) findViewById(R.id.singleOfCoursetues);
        mLectureSingleDivisiontues = (TextView) findViewById(R.id.singleOfDivisiontues);

        mLectureRemoveBtntues = (Button) findViewById(R.id.singleLectureRemoveBtntues);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_lecture = (String) dataSnapshot.child("tuesdaylect_name").getValue();
                String post_from = (String) dataSnapshot.child("tuesdayfrom_time").getValue();
                String post_to = (String) dataSnapshot.child("tuesdayto_time").getValue();
                String post_course = (String) dataSnapshot.child("tuesdayof_course").getValue();
                String post_division = (String) dataSnapshot.child("tuesdayof_division").getValue();

                mLectureSingleNametues.setText(post_lecture);
                mLectureSingleFromtues.setText(post_from);
                mLectureSingleTotues.setText(post_to);
                mLectureSingleCoursetues.setText(post_course);
                mLectureSingleDivisiontues.setText(post_division);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLectureRemoveBtntues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(single_tttuesday.this, tttuesday.class);
                startActivity(mainIntent);

            }
        });

    }
}
