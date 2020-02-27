package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class insert_ttmonday extends AppCompatActivity {

    private EditText mPostmonlect;
    private EditText mPostmonfrom;
    private EditText mPostmonto;
    private EditText mPostmoncourse;
    private EditText mPostmondiv;

    private Button mPostmonBtn;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ttmonday);

        mPostmonlect = (EditText)findViewById(R.id.submondayField);
        mPostmonfrom = (EditText)findViewById(R.id.frommondayField);
        mPostmonto = (EditText)findViewById(R.id.tomondayField);
        mPostmoncourse = (EditText)findViewById(R.id.ofcoursemonday);
        mPostmondiv = (EditText)findViewById(R.id.ofdivisionmonday);

        mPostmonBtn = (Button)findViewById(R.id.submitmondayBtn);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Ttmonday");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mPostmonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });

    }

    private void startPosting() {

        final String mondaylect_val = mPostmonlect.getText().toString().trim();
        final String mondayfrom_val = mPostmonfrom.getText().toString().trim();
        final String mondayto_val = mPostmonto.getText().toString().trim();
        final String mondaycou_val = mPostmoncourse.getText().toString().trim();
        final String mondaydiv_val = mPostmondiv.getText().toString().trim();

        if (!TextUtils.isEmpty(mondaylect_val)){

             final DatabaseReference newPost = mDatabase.push();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("mondaylect_name").setValue(mondaylect_val);
                    newPost.child("mondayfrom_time").setValue(mondayfrom_val);
                    newPost.child("mondayto_time").setValue(mondayto_val);
                    newPost.child("mondayof_course").setValue(mondaycou_val);
                    newPost.child("mondayof_division").setValue(mondaydiv_val);
                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(insert_ttmonday.this, ttmonday.class));

                            }
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }


    public void Startadd_reminder(View view) {

        startActivity(new Intent(insert_ttmonday.this, AddReminder.class));

    }
}
