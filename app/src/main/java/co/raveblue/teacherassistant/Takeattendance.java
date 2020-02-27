package co.raveblue.teacherassistant;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Cell;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class Takeattendance extends AppCompatActivity {
    String teacher_id;
    String class_selected;
    Spinner period;
    String periodno;
    ArrayList<String> selectedItems;
    ArrayList<String> nonselectedItems;

    ArrayList<String> ul;
    ListView listView;
    private ArrayAdapter adapter;
    ArrayList Userlist = new ArrayList<>();
    ArrayList Usernames = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takeattendance);

        period = (Spinner) findViewById(R.id.spinner4);

        // ArrayList Userlist;
        selectedItems = new ArrayList<String>();

        TextView classname = (TextView) findViewById(R.id.textView);
        classname.setText("IT-X");

        //to get class name from teacherlogin
        Bundle bundle1 = getIntent().getExtras();
        class_selected = bundle1.getString("class_selected");
        teacher_id = bundle1.getString("tid");
        //  Toast.makeText(getApplicationContext(), teacher_id, Toast.LENGTH_LONG).show();

        classname.setText(class_selected);


        DatabaseReference dbuser = ref.child("Student_Info");

        dbuser.orderByChild("st_class").equalTo(class_selected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                // Result will be holded Here

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("st_roll").getValue().toString()); //add result into array list
                    Usernames.add(dsp.child("st_name").getValue().toString());


                }
                OnStart(Userlist);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void OnStart(ArrayList<String> userlist) {
        nonselectedItems = userlist;
        //create an instance of ListView
        GridView chl = (GridView) findViewById(R.id.checkable_list);
        //set multiple selection mode
        chl.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);

        //supply data itmes to ListView
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.checkable_list_layout, R.id.txt_title, userlist);
        chl.setAdapter(aa);
        //set OnItemClickListener
        chl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // selected item
                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem))
                    selectedItems.remove(selectedItem); //remove deselected item from the list of selected items
                else
                    selectedItems.add(selectedItem); //add selected item to the list of selected items

            }

        });


    }

    public void showSelectedItems(View view) {
        String selItems = "";
        periodno = period.getSelectedItem().toString();
        if (periodno.equals("Select Period")) {
            Toast.makeText(this, "Select a class", Toast.LENGTH_LONG).show();

        } else {
            ref = FirebaseDatabase.getInstance().getReference();

            dbAttendance = ref.child("Attendance").child(date);

            for (String item : selectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                nonselectedItems.remove(item);
                dbAttendance.child(item).child(periodno).setValue("P" + " / " + teacher_id);
                if (selItems == "")
                    selItems = item;
                else
                    selItems += "/" + item;
            }
            // Toast.makeText(this, selItems, Toast.LENGTH_LONG).show();


            //for making absent
            for (String item : nonselectedItems) {
                Toast.makeText(this, "Attendance created Successfully", Toast.LENGTH_SHORT).show();
                dbAttendance.child(item).child(periodno).setValue("A" + " / " + teacher_id);
                //Toast.makeText(this, "absentees:" + nonselectedItems, Toast.LENGTH_LONG).show();

            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


