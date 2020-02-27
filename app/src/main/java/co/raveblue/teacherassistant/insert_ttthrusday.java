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

public class insert_ttthrusday extends AppCompatActivity {

    private EditText mPostthruslect;
    private EditText mPostthrusfrom;
    private EditText mPostthrusto;
    private EditText mPostthruscourse;
    private EditText mPostthrusdiv;

    private Button mPostthrusBtn;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_ttthrusday);

        mPostthruslect = (EditText) findViewById(R.id.subthrusdayField);
        mPostthrusfrom = (EditText) findViewById(R.id.fromthrusdayField);
        mPostthrusto = (EditText) findViewById(R.id.tothrusdayField);
        mPostthruscourse = (EditText) findViewById(R.id.ofcoursethrusday);
        mPostthrusdiv = (EditText) findViewById(R.id.ofdivisionthrusday);

        mPostthrusBtn = (Button) findViewById(R.id.submitthrusdayBtn);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Ttthrusday");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mPostthrusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });
    }

    private void startPosting() {

        final String thrusdaylect_val = mPostthruslect.getText().toString().trim();
        final String thrusdayfrom_val = mPostthrusfrom.getText().toString().trim();
        final String thrusdayto_val = mPostthrusto.getText().toString().trim();
        final String thrusdaycou_val = mPostthruscourse.getText().toString().trim();
        final String thrusdaydiv_val = mPostthrusdiv.getText().toString().trim();

        if (!TextUtils.isEmpty(thrusdaylect_val)){

            final DatabaseReference newPost = mDatabase.push();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("thrusdaylect_name").setValue(thrusdaylect_val);
                    newPost.child("thrusdayfrom_time").setValue(thrusdayfrom_val);
                    newPost.child("thrusdayto_time").setValue(thrusdayto_val);
                    newPost.child("thrusdayof_course").setValue(thrusdaycou_val);
                    newPost.child("thrusdayof_division").setValue(thrusdaydiv_val);
                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(insert_ttthrusday.this, ttthrusday.class));

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
