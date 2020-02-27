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

public class single_ttthrusday extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mLectureSingleNamethrus;
    private TextView mLectureSingleFromthrus;
    private TextView mLectureSingleTothrus;
    private TextView mLectureSingleCoursethrus;
    private TextView mLectureSingleDivisionthrus;

    private Button mLectureRemoveBtnthrus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ttthrusday);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttthrusday");
        mDatabase.keepSynced(true);

        mPost_key = getIntent().getExtras().getString("ttthrusday_id");

        mLectureSingleNamethrus = (TextView) findViewById(R.id.singleLectureNamethrus);
        mLectureSingleFromthrus = (TextView) findViewById(R.id.singleFromTimethrus);
        mLectureSingleTothrus = (TextView) findViewById(R.id.singleToTimethrus);
        mLectureSingleCoursethrus = (TextView) findViewById(R.id.singleOfCoursethrus);
        mLectureSingleDivisionthrus = (TextView) findViewById(R.id.singleOfDivisionthrus);

        mLectureRemoveBtnthrus = (Button) findViewById(R.id.singleLectureRemoveBtnthrus);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_lecture = (String) dataSnapshot.child("thrusdaylect_name").getValue();
                String post_from = (String) dataSnapshot.child("thrusdayfrom_time").getValue();
                String post_to = (String) dataSnapshot.child("thrusdayto_time").getValue();
                String post_course = (String) dataSnapshot.child("thrusdayof_course").getValue();
                String post_division = (String) dataSnapshot.child("thrusdayof_division").getValue();

                mLectureSingleNamethrus.setText(post_lecture);
                mLectureSingleFromthrus.setText(post_from);
                mLectureSingleTothrus.setText(post_to);
                mLectureSingleCoursethrus.setText(post_course);
                mLectureSingleDivisionthrus.setText(post_division);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLectureRemoveBtnthrus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(single_ttthrusday.this, ttthrusday.class);
                startActivity(mainIntent);

            }
        });

    }
}
