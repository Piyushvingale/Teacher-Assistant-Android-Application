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

public class insert_ttsaturday extends AppCompatActivity {

    private EditText mPostsatlect;
    private EditText mPostsatfrom;
    private EditText mPostsatto;
    private EditText mPostsatcourse;
    private EditText mPostsatdiv;

    private Button mPostsatBtn;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ttsaturday);

        mPostsatlect = (EditText) findViewById(R.id.subsaturdayField);
        mPostsatfrom = (EditText) findViewById(R.id.fromsaturdayField);
        mPostsatto = (EditText) findViewById(R.id.tosaturdayField);
        mPostsatcourse = (EditText) findViewById(R.id.ofcoursesaturday);
        mPostsatdiv = (EditText) findViewById(R.id.ofdivisionsaturday);

        mPostsatBtn = (Button) findViewById(R.id.submitsaturdayBtn);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttsaturday");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mPostsatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {

        final String saturdaylect_val = mPostsatlect.getText().toString().trim();
        final String saturdayfrom_val = mPostsatfrom.getText().toString().trim();
        final String saturdayto_val = mPostsatto.getText().toString().trim();
        final String saturdaycou_val = mPostsatcourse.getText().toString().trim();
        final String saturdaydiv_val = mPostsatdiv.getText().toString().trim();

        if (!TextUtils.isEmpty(saturdaylect_val)){

            final DatabaseReference newPost = mDatabase.push();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("saturdaylect_name").setValue(saturdaylect_val);
                    newPost.child("saturdayfrom_time").setValue(saturdayfrom_val);
                    newPost.child("saturdayto_time").setValue(saturdayto_val);
                    newPost.child("saturdayof_course").setValue(saturdaycou_val);
                    newPost.child("saturdayof_division").setValue(saturdaydiv_val);
                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(insert_ttsaturday.this, ttsaturday.class));

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


}
