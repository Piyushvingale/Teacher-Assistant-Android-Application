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
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewNote extends AppCompatActivity {

    private EditText mNoteTitle;
    private EditText mNoteDesc;
    private Button mNoteSubmit;

    private String saveday;
    private String savedate;
    private int sd;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mDatabaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        mNoteTitle = (EditText)findViewById(R.id.noteTitle);
        mNoteDesc = (EditText)findViewById(R.id.noteDesc);

        mNoteSubmit = (Button)findViewById(R.id.noteSubmit);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Notes");

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mNoteSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPosting();

            }
        });

    }


    private void startPosting() {

        final String notetitle_val = mNoteTitle.getText().toString().trim();
        final String notedesc_val = mNoteDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(notetitle_val)){

            final DatabaseReference newPost = mDatabase.push();

            //save day as mon, tue
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            saveday = new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());

            //save date as 1, 23
            sd = calendar.get(Calendar.DAY_OF_MONTH);
            savedate = String.valueOf(sd);

            //save data to firebase
            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    newPost.child("day").setValue(saveday);
                    newPost.child("date").setValue(savedate);
                    newPost.child("note_title").setValue(notetitle_val);
                    newPost.child("note_desc").setValue(notedesc_val);
                    newPost.child("uid").setValue(mCurrentUser.getUid());
                    newPost.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                startActivity(new Intent(NewNote.this, Notes.class));

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
