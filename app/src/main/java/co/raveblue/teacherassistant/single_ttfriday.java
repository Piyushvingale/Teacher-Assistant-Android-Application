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

public class single_ttfriday extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mLectureSingleNamefri;
    private TextView mLectureSingleFromfri;
    private TextView mLectureSingleTofri;
    private TextView mLectureSingleCoursefri;
    private TextView mLectureSingleDivisionfri;

    private Button mLectureRemoveBtnfri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_ttfriday);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttfriday");
        mDatabase.keepSynced(true);

        mPost_key = getIntent().getExtras().getString("ttfriday_id");

        mLectureSingleNamefri = (TextView) findViewById(R.id.singleLectureNamefri);
        mLectureSingleFromfri = (TextView) findViewById(R.id.singleFromTimefri);
        mLectureSingleTofri = (TextView) findViewById(R.id.singleToTimefri);
        mLectureSingleCoursefri = (TextView) findViewById(R.id.singleOfCoursefri);
        mLectureSingleDivisionfri = (TextView) findViewById(R.id.singleOfDivisionfri);

        mLectureRemoveBtnfri = (Button) findViewById(R.id.singleLectureRemoveBtnfri);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_lecture = (String) dataSnapshot.child("fridaylect_name").getValue();
                String post_from = (String) dataSnapshot.child("fridayfrom_time").getValue();
                String post_to = (String) dataSnapshot.child("fridayto_time").getValue();
                String post_course = (String) dataSnapshot.child("fridayof_course").getValue();
                String post_division = (String) dataSnapshot.child("fridayof_division").getValue();

                mLectureSingleNamefri.setText(post_lecture);
                mLectureSingleFromfri.setText(post_from);
                mLectureSingleTofri.setText(post_to);
                mLectureSingleCoursefri.setText(post_course);
                mLectureSingleDivisionfri.setText(post_division);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mLectureRemoveBtnfri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(single_ttfriday.this, ttfriday.class);
                startActivity(mainIntent);

            }
        });

    }
}
