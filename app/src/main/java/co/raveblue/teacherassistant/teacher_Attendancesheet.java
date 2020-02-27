package co.raveblue.teacherassistant;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class teacher_Attendancesheet extends AppCompatActivity {

    ListView listView;
    String teacher_id,class_selected;

    EditText roll;

    EditText date;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    DatabaseReference dbStudent;

    int P1P=0, P1A=0, P2P=0, P2A=0, P3P=0, P3A=0, P4P=0, P4A=0, P5P=0, P5A=0, totallect=0;

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__attendancesheet);

        listView = (ListView) findViewById(R.id.list);
        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");
        roll = (EditText)findViewById(R.id.roll);

        barChart = (BarChart)findViewById(R.id.barchart);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(true);


    }

    public void graph(int p1p, int p2p, int p3p, int p4p, int p5p, int p1a, int p2a, int p3a, int p4a, int p5a)
    {

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,p1p));
        barEntries.add(new BarEntry(2,p2p));
        barEntries.add(new BarEntry(3,p3p));
        barEntries.add(new BarEntry(4,p4p));
        barEntries.add(new BarEntry(5,p5p));

        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        barEntries1.add(new BarEntry(1,p1a + p1p));
        barEntries1.add(new BarEntry(2,p2a + p2p));
        barEntries1.add(new BarEntry(3,p3a + p3p));
        barEntries1.add(new BarEntry(4,p4a + p4p));
        barEntries1.add(new BarEntry(5,p5a + p5p));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Data Set1");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarDataSet barDataSet1 = new BarDataSet(barEntries1, "Data Set2");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(barDataSet,barDataSet1);

        float groupSpace = 0.05f;
        float barSpace = 0.05f;
        float barWidth = 0.30f;

        barChart.setData(data);

        data.setBarWidth(barWidth);
        barChart.groupBars(1, groupSpace, barSpace);

        String[] period = new String[]{"P1","P2","P3","P4","P5"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new MyXAxisValueFormatter(period));
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
    //    xAxis.setGranularity(1);
  //      xAxis.setCenterAxisLabels(true);
//        xAxis.setAxisMinimum(1);



    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter{

        private String[] mValues;
        public MyXAxisValueFormatter(String[] values){
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis){
            return mValues[(int)value];
        }

    }

    public void viewlist(View v) {

        Userlist.clear();
        final String rollno;
        rollno = roll.getText().toString();
        dbStudent = ref.child("Student_Info");
        dbStudent.orderByChild("st_class").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String strl = dsp.child("st_roll").getValue().toString();
                    if (strl.equals(rollno))
                    {
                        Userlist.add(strl); //add result into array list
                    }
                }
                display_list(Userlist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }

    //Date module
    private static List<Date> getDates(String dateString1, String datestring2)
    {
        List<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date date2 = null;

        try{
            date1 = df1.parse(dateString1);
            date2 = df1.parse(datestring2);
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(cal1.before(cal2))
        {
            Date result = cal1.getTime();
            dates.add(result);
            cal1.add(Calendar.DATE, 1);
        }

        return dates;
    }



    public void display_list(final ArrayList userlist) {

        Studentlist.clear();
        dbAttendance = ref.child("Attendance");

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int year1 = c.get(Calendar.YEAR);
        int month = c.get(Calendar.JANUARY);

        String date1 = null;
        String date2 = null;

        if (month < 6)
        {
            year--;
            date1 = "01-06-"+year;
        }
        else
        {
            date1 = "01-06-"+year;
        }

        if (month > 5)
        {
            year1++;
            date2 = "30-05-"+year1;
        }
        else
        {
            date2 = "30-05-"+year1;
        }

        List<Date> dates = getDates(date1,date2);

        totallect = 0;
        P1P = 0;
        P1A = 0;
        P2P = 0;
        P2A = 0;
        P3P = 0;
        P3A = 0;
        P4P = 0;
        P4A = 0;
        P5P = 0;
        P5A = 0;

//        Studentlist.add("      Roll No          " + "               period");

        for (Date date:dates) {

            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String dt = format1.format(date);

            for (final Object sid : userlist) {
                dbAttendance.child(dt).child(sid.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Result will be holded Here

                        //DataSnapshot dsp=dataSnapshot.child(sid.toString());
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            String p1 = dsp.getValue().toString();
                            //Toast.makeText(getApplicationContext(),p1,Toast.LENGTH_LONG).show();
                            if ((p1.equals("A / " + teacher_id)) || (p1.equals("P / " + teacher_id))) {
//                                Studentlist.add(dataSnapshot.getKey().toString() + "            " + p1.substring(0, 1) + "        " + dsp.getKey());


                                if (dsp.getKey().equals("p1"))
                                {
                                    if (p1.equals("P / " + teacher_id))
                                    {
                                        P1P++;
                                    }
                                    else
                                    {
                                        P1A++;
                                    }
                                }
                                if (dsp.getKey().equals("p2"))
                                {
                                    if (p1.equals("P / " + teacher_id))
                                    {
                                        P2P++;
                                    }
                                    else
                                    {
                                        P2A++;
                                    }
                                }
                                if (dsp.getKey().equals("p3"))
                                {
                                    if (p1.equals("P / " + teacher_id))
                                    {
                                        P3P++;
                                    }
                                    else
                                    {
                                        P3A++;
                                    }
                                }
                                if (dsp.getKey().equals("p4"))
                                {
                                    if (p1.equals("P / " + teacher_id))
                                    {
                                        P4P++;
                                    }
                                    else
                                    {
                                        P4A++;
                                    }
                                }
                                if (dsp.getKey().equals("p5"))
                                {
                                    if (p1.equals("P / " + teacher_id))
                                    {
                                        P5P++;
                                    }
                                    else
                                    {
                                        P5A++;
                                    }
                                }

                                totallect++;
                            }
                        }

                        list(Studentlist);

//                        Toast.makeText(getApplicationContext(),totallect+"\n"+P1P+"\n"+P1A ,Toast.LENGTH_LONG).show();

                        graph(P1P,P2P,P3P,P4P,P5P,P1A,P2A,P3A,P4A,P5A);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }

                });


            }


        }
    }
    public void list(ArrayList studentlist){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        // Assign adapter to ListView
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
