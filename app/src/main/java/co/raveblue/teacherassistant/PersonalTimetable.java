package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class PersonalTimetable extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Animation appear1;
    Animation appear2;
    Animation appear3;
    Animation appear4;
    Animation appear5;
    Animation appear6;
    Animation from_top;
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaltimetable);

        appear1 = AnimationUtils.loadAnimation(this,R.anim.personaltt_appear_btn1);
        appear2 = AnimationUtils.loadAnimation(this,R.anim.personaltt_appear_btn2);
        appear3 = AnimationUtils.loadAnimation(this,R.anim.personaltt_appear_btn3);
        appear4 = AnimationUtils.loadAnimation(this,R.anim.personaltt_appear_btn4);
        appear5 = AnimationUtils.loadAnimation(this,R.anim.personaltt_appear_btn5);
        appear6 = AnimationUtils.loadAnimation(this,R.anim.personaltt_appear_btn6);
        from_top = AnimationUtils.loadAnimation(this,R.anim.main_from_top);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        txt1 = findViewById(R.id.txt1);
        btn1.startAnimation(appear1);
        btn2.startAnimation(appear2);
        btn3.startAnimation(appear3);
        btn4.startAnimation(appear4);
        btn5.startAnimation(appear5);
        btn6.startAnimation(appear6);
        txt1.startAnimation(from_top);

    }

    public void starttimetablemonday(View view) {
        startActivity(new Intent(PersonalTimetable.this, ttmonday.class));
    }

    public void starttimetabletuesday(View view) {
        startActivity(new Intent(PersonalTimetable.this, tttuesday.class));
    }

    public void starttimetablewednesday(View view) {
        startActivity(new Intent(PersonalTimetable.this, ttwednesday.class));
    }

    public void starttimetablethrusday(View view) {
        startActivity(new Intent(PersonalTimetable.this, ttthrusday.class));
    }

    public void starttimetablefriday(View view) {
        startActivity(new Intent(PersonalTimetable.this, ttfriday.class));
    }

    public void starttimetablesaturday(View view) {
        startActivity(new Intent(PersonalTimetable.this, ttsaturday.class));
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(PersonalTimetable.this, MainActivity.class);
        startActivity(intent);

    }

    public void startoverall_timetable(View view) {

        startActivity(new Intent(PersonalTimetable.this, overall_timetable.class));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.alarm_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_viewalarm){

            startActivity(new Intent(PersonalTimetable.this, alarm_alltime.class));

        }

        return super.onOptionsItemSelected(item);
    }

}
