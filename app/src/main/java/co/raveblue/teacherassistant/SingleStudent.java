package co.raveblue.teacherassistant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SingleStudent extends AppCompatActivity {

    private String mPost_key = null;

    private DatabaseReference mDatabase;

    private TextView mStudentSingleName;
    private TextView mStudentSingleAddress;
    private TextView mStudentSingleContact;
    private TextView mStudentSingleEmail;
    private TextView mStudentSingleRoll;
    private TextView mStudentSingleCourse;
  //  private ImageButton mCall;
   // private ImageButton mMessage;
    private LinearLayout mContact;

    private Button mSingleRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_student);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student_Info");

        mPost_key = getIntent().getExtras().getString("student_id");

        mStudentSingleAddress = (TextView) findViewById(R.id.singleStudentAddress);
        mStudentSingleName = (TextView) findViewById(R.id.singleStudentName);
        mStudentSingleContact = (TextView) findViewById(R.id.singleStudentContact);
        mStudentSingleEmail = (TextView) findViewById(R.id.singleStudentEmail);
        mStudentSingleRoll = (TextView) findViewById(R.id.singleStudentRoll);
        mStudentSingleCourse = (TextView) findViewById(R.id.singleStudentCourse);
  //      mCall = findViewById(R.id.call);
    //    mMessage = findViewById(R.id.message);

        mContact = findViewById(R.id.contact);
        mSingleRemoveBtn = (Button) findViewById(R.id.singleRemoveBtn);


        mDatabase.child(mPost_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_name = (String) dataSnapshot.child("st_name").getValue();
                String post_address = (String) dataSnapshot.child("st_address").getValue();
                String post_contact = (String) dataSnapshot.child("st_contact").getValue();
                String post_email = (String) dataSnapshot.child("st_email").getValue();
                String post_roll = (String) dataSnapshot.child("st_roll").getValue();
                String post_class = (String) dataSnapshot.child("st_class").getValue();

                mStudentSingleName.setText(post_name);
                mStudentSingleAddress.setText(post_address);
                mStudentSingleContact.setText(post_contact);
                mStudentSingleEmail.setText(post_email);
                mStudentSingleRoll.setText(post_roll);
                mStudentSingleCourse.setText(post_class);

                final String contact = post_contact;
/*
                mContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(contact));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }
                });
8?
   /*
                final String contact = post_contact;

                mCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(contact));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    }
                });

                mMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.putExtra("sms_body","default content");
                        intent1.setType("vnd.android-dir/mms-sms");
                        startActivity(intent1);
                    }
                });
*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mSingleRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(mPost_key).removeValue();

                Intent mainIntent = new Intent(SingleStudent.this, AddStudentDetails.class);
                startActivity(mainIntent);

            }
        });

    }

    public void Start_call_redirect(View view) {

        Intent intent = new Intent(Intent.ACTION_CALL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(intent);

    }
}
