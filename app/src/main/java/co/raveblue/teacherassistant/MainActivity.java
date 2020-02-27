package co.raveblue.teacherassistant;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.VerticalStepView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.apache.log4j.chainsaw.Main;
import org.apache.poi.ss.formula.functions.T;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static co.raveblue.teacherassistant.R.id.date;
import static co.raveblue.teacherassistant.R.id.forever;

public class MainActivity extends AppCompatActivity {

    //Attendance
    DatabaseReference mDatabase;
    DatabaseReference dbAttendance;
    DatabaseReference mCurrentDate;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabaseUsers;

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    String dt = "null";

    VerticalStepView verticalStepView;

    Animation from_top;
    Animation from_bottom;
    Animation from_right;
    Animation from_left;
    LinearLayout agenda;
    LinearLayout secondlayer;
    LinearLayout thirdlayer;
    LinearLayout fourthlayer;
    Button button2;
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        mAuth = FirebaseAuth.getInstance();

        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student_Info");
        dbAttendance = FirebaseDatabase.getInstance().getReference().child("Attendance");
        mCurrentDate = FirebaseDatabase.getInstance().getReference().child("Current_Date");
        mCurrentDate.keepSynced(true);

//        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
  //      indicator.setViewPager(pager);



        //Animation
        from_top = AnimationUtils.loadAnimation(this,R.anim.main_from_top);
        from_bottom = AnimationUtils.loadAnimation(this, R.anim.main_from_bottom);
        from_left = AnimationUtils.loadAnimation(this, R.anim.main_from_left);
        from_right = AnimationUtils.loadAnimation(this, R.anim.main_from_right);
        agenda = findViewById(R.id.agenda);
        thirdlayer = findViewById(R.id.thirdLayer);
        fourthlayer = findViewById(R.id.fourthLayer);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        secondlayer = findViewById(R.id.secondLayer);
        agenda.startAnimation(from_top);
        thirdlayer.startAnimation(from_bottom);
        fourthlayer.startAnimation(from_bottom);
        button2.startAnimation(from_left);
        button3.startAnimation(from_right);
        secondlayer.startAnimation(from_bottom);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null){

                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }

            }
        };

    }

    private class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int pos) {
            switch (pos){

                case 0:
                    return first_fragment.newInstance("First Fragment, Instance 1");
                case 1:
                    return second_fragment.newInstance("Second Fragment, Instance 2");
                default:
                    return first_fragment.newInstance("Third Fragment, Instance 3");
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    public void getDate(){
        mCurrentDate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dt = (String) dataSnapshot.child("date").getValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
        getDate();


    }


    public void startpersonaltimetable(View view) {
        Intent intent = new Intent(MainActivity.this, PersonalTimetable.class);
        startActivity(intent);

    }

    public void startsyllabus(View view) {
        startActivity(new Intent(MainActivity.this, Syllabus.class));
    }

    public void startupdatemarks(View view) {
        startActivity(new Intent(MainActivity.this, Performance.class));
    }

    public void startcalculator(View view) {
        startActivity(new Intent(MainActivity.this, Calculator.class));
    }

    public void startnotes(View view) {
        startActivity(new Intent(MainActivity.this, Notes.class));
    }


    public void startaddstudentdetails(View view) {
        startActivity(new Intent(MainActivity.this, AddStudentDetails.class));
    }

    public void startpuneuniversity(View view) {
        startActivity(new Intent(MainActivity.this, pune_university.class));
    }

    public void startattedance(View view) {

        getDate();

        if (dt.equals("null"))
        {
            Toast.makeText(getApplicationContext(), "Check Internet Connection..!", Toast.LENGTH_LONG).show();
        }
        else {
            if (dt.equals(date)) {
                Intent intent = new Intent(MainActivity.this, Attendance.class);
                startActivity(intent);
            } else {
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String sid, P1 = "-", P2 = "-", P3 = "-", P4 = "-", P5 = "-";
                        Attendance_sheet a = new Attendance_sheet(P1, P2, P3, P4, P5);
                        // Result will be holded Here
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            sid = dsp.child("st_roll").getValue().toString(); //add result into array list
                            dbAttendance.child(date).child(sid).setValue(a);
                        }

                        mCurrentDate.child("date").setValue(date);

                        Toast.makeText(getApplicationContext(), "successfully created " + date + " db", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Attendance.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Toast.makeText(MainActivity.this, "Press back to exit ..!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
/*
        if (item.getItemId() == R.id.action_addprofile){

            Intent intent = new Intent(MainActivity.this, ProfileAdd.class);
            startActivity(intent);

        }
*/
        if (item.getItemId() == R.id.action_logout){

            logout();

        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        mAuth.signOut();

    }

}
