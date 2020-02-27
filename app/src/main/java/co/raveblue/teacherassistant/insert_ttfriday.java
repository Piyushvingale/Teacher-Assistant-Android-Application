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

public class insert_ttfriday extends AppCompatActivity {

    private EditText mPostfrilect;
    private EditText mPostfrifrom;
    private EditText mPostfrito;
    private EditText mPostfricourse;
    private EditText mPostfridiv;

    private Button mPostfriBtn;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ttfriday);

        mPostfrilect = (EditText) findViewById(R.id.subfridayField);
        mPostfrifrom = (EditText) findViewById(R.id.fromfridayField);
        mPostfrito = (EditText) findViewById(R.id.tofridayField);
        mPostfricourse = (EditText) findViewById(R.id.ofcoursefriday);
        mPostfridiv = (EditText) findViewById(R.id.ofdivisionfriday);

        mPostfriBtn = (Button) findViewById(R.id.submitfridayBtn);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttfriday");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mPostfriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {

        final String fridaylect_val = mPostfrilect.getText().toString().trim();
        final String fridayfrom_val = mPostfrifrom.getText().toString().trim();
        final String fridayto_val = mPostfrito.getText().toString().trim();
        final String fridaycou_val = mPostfricourse.getText().toString().trim();
        final String fridaydiv_val = mPostfridiv.getText().toString().trim();

        if (!TextUtils.isEmpty(fridaylect_val)){

            final DatabaseReference newPost = mDatabase.push();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("fridaylect_name").setValue(fridaylect_val);
                    newPost.child("fridayfrom_time").setValue(fridayfrom_val);
                    newPost.child("fridayto_time").setValue(fridayto_val);
                    newPost.child("fridayof_course").setValue(fridaycou_val);
                    newPost.child("fridayof_division").setValue(fridaydiv_val);
                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(insert_ttfriday.this, ttfriday.class));

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
