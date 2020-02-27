package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String item;
    String message;
    private static long back_pressed;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Spinner click listener
        spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("FYBCA");
        categories.add("SYBCA");
        categories.add("TYBCA");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void takeAttendanceButton(View v){
        Bundle basket= new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);


        Intent intent = new Intent(Attendance.this, Takeattendance.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

    public void  previous_records(View v){
        Bundle basket= new Bundle();
        basket.putString("class_selected", item);
        basket.putString("tid", message);


        Intent intent = new Intent(this, teacher_Attendancesheet.class);
        intent.putExtras(basket);
        startActivity(intent);
    }

}
