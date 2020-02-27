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

public class insert_ttwednesday extends AppCompatActivity {

    private EditText mPostwedlect;
    private EditText mPostwedfrom;
    private EditText mPostwedto;
    private EditText mPostwedcourse;
    private EditText mPostweddiv;

    private Button mPostwedBtn;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ttwednesday);

        mPostwedlect = (EditText) findViewById(R.id.subwednesdayField);
        mPostwedfrom = (EditText) findViewById(R.id.fromwednesdayField);
        mPostwedto = (EditText) findViewById(R.id.towednesdayField);
        mPostwedcourse = (EditText) findViewById(R.id.ofcoursewednesday);
        mPostweddiv = (EditText) findViewById(R.id.ofdivisionwednesday);

        mPostwedBtn = (Button) findViewById(R.id.submitwednesdayBtn);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttwednesday");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mPostwedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {

        final String wednesdaylect_val = mPostwedlect.getText().toString().trim();
        final String wednesdayfrom_val = mPostwedfrom.getText().toString().trim();
        final String wednesdayto_val = mPostwedto.getText().toString().trim();
        final String wednesdaycou_val = mPostwedcourse.getText().toString().trim();
        final String wednesdaydiv_val = mPostweddiv.getText().toString().trim();

        if (!TextUtils.isEmpty(wednesdaylect_val)){

            final DatabaseReference newPost = mDatabase.push();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("wednesdaylect_name").setValue(wednesdaylect_val);
                    newPost.child("wednesdayfrom_time").setValue(wednesdayfrom_val);
                    newPost.child("wednesdayto_time").setValue(wednesdayto_val);
                    newPost.child("wednesdayof_course").setValue(wednesdaycou_val);
                    newPost.child("wednesdayof_division").setValue(wednesdaydiv_val);
                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(insert_ttwednesday.this, ttwednesday.class));

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
