package co.raveblue.teacherassistant;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SinglePerformance extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mStudentSingleName;

    private int mPostMarks1 = 0;
    private int mPostMarks2 = 0;
    private int mPostAttendance = 0;
    private int mPostTotalLectures = 0;
    private int mPostOverall = 0;
    private int mPostActivites = 0;
    private int mPostPresent = 0;

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_performance);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mPost_key = getIntent().getExtras().getString("student_id");

        mStudentSingleName = (TextView) findViewById(R.id.studentname);

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
                mPostMarks1 = Integer.parseInt(post_marks1);
                mPostMarks2 = Integer.parseInt(post_marks2);
                mPostPresent = Integer.parseInt(post_attendance);
                mPostTotalLectures = Integer.parseInt(post_totlect);
                mPostOverall = Integer.parseInt(post_overall);
                mPostActivites = Integer.parseInt(post_actpart);
                mPostAttendance = mPostPresent * 100 / mPostTotalLectures;


                if(mPostMarks1 == 0 && mPostMarks2 == 0 && mPostAttendance == 0 && mPostOverall == 0 && mPostActivites == 0)
                {
                    Intent intent = new Intent(SinglePerformance.this, SingleEditPerformance.class);
                    Toast.makeText(SinglePerformance.this, "Fill Details First..!", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }

                pieChart = (PieChart)findViewById(R.id.piechart);

                pieChart.setUsePercentValues(false);
                pieChart.getDescription().setEnabled(false);

                pieChart.setExtraOffsets(5, 10, 5, 5);

                pieChart.setDragDecelerationFrictionCoef(0.99f);

                pieChart.setDrawHoleEnabled(true);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setTransparentCircleRadius(60f);

                ArrayList<PieEntry> yValues = new ArrayList<>();

                yValues.add(new PieEntry(mPostMarks1, "Class Test I "));
                yValues.add(new PieEntry(mPostMarks2, "Class Test II"));
                yValues.add(new PieEntry(mPostAttendance, "Attendance"));
                yValues.add(new PieEntry(mPostOverall, "Performance"));
                yValues.add(new PieEntry(mPostActivites, "Activities"));

                pieChart.animateY(3000, Easing.EasingOption.EaseInOutCubic);

                PieDataSet dataSet = new PieDataSet(yValues, "Performance");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                PieData data =  new PieData(dataSet);
                data.setValueTextSize(20f);
                data.setValueTextColor(Color.WHITE);

                pieChart.setData(data);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
