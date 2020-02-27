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

public class insert_tttuesday extends AppCompatActivity {

    private EditText mPosttueslect;
    private EditText mPosttuesfrom;
    private EditText mPosttuesto;
    private EditText mPosttuescourse;
    private EditText mPosttuesdiv;

    private Button mPosttuesBtn;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_tttuesday);

        mPosttueslect = (EditText) findViewById(R.id.subtuesdayField);
        mPosttuesfrom = (EditText) findViewById(R.id.fromtuesdayField);
        mPosttuesto = (EditText) findViewById(R.id.totuesdayField);
        mPosttuescourse = (EditText) findViewById(R.id.ofcoursetuesday);
        mPosttuesdiv = (EditText) findViewById(R.id.ofdivisiontuesday);

        mPosttuesBtn = (Button) findViewById(R.id.submittuesdayBtn);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tttuesday");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mPosttuesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {

        final String tuesdaylect_val = mPosttueslect.getText().toString().trim();
        final String tuesdayfrom_val = mPosttuesfrom.getText().toString().trim();
        final String tuesdayto_val = mPosttuesto.getText().toString().trim();
        final String tuesdaycou_val = mPosttuescourse.getText().toString().trim();
        final String tuesdaydiv_val = mPosttuesdiv.getText().toString().trim();

        if (!TextUtils.isEmpty(tuesdaylect_val)){

            final DatabaseReference newPost = mDatabase.push();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("tuesdaylect_name").setValue(tuesdaylect_val);
                    newPost.child("tuesdayfrom_time").setValue(tuesdayfrom_val);
                    newPost.child("tuesdayto_time").setValue(tuesdayto_val);
                    newPost.child("tuesdayof_course").setValue(tuesdaycou_val);
                    newPost.child("tuesdayof_division").setValue(tuesdaydiv_val);
                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(insert_tttuesday.this, tttuesday.class));

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

        startActivity(new Intent(insert_tttuesday.this, AddReminder.class));

    }


}
