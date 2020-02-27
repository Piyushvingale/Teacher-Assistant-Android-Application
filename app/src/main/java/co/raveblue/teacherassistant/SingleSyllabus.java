package co.raveblue.teacherassistant;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleSyllabus extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mSyllabusSingleName;

    private Button mSingleRemoveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_syllabus);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Syllabus");

        mPost_key = getIntent().getExtras().getString("syllabus_id");
        mSyllabusSingleName = (TextView) findViewById(R.id.syllabus_name);

        mSingleRemoveBtn = (Button) findViewById(R.id.syllabus_remove);

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_name = (String) dataSnapshot.child("filename").getValue();

                mSyllabusSingleName.setText(post_name);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mSingleRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(SingleSyllabus.this, Syllabus.class);
                startActivity(mainIntent);

            }
        });

    }

    public void start_syllabusopen(View view) {

        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_url = (String) dataSnapshot.child("url").getValue();



                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(post_url), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    //            Intent newIntent = Intent.createChooser(intent, "Open File");
  //              newIntent.setDataAndType(Uri.parse(post_url),"application/pdf");
                try{
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(SingleSyllabus.this, "Install Pdf Reader", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
