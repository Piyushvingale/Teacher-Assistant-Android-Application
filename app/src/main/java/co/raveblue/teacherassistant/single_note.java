package co.raveblue.teacherassistant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class single_note extends AppCompatActivity {

    private String note_key = null;

    private DatabaseReference mDatabase;

    private TextView mNoteSingleTitle;
    private TextView mNoteSingleDesc;

    private Button mNoteRemoveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_note);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Notes");

        note_key = getIntent().getExtras().getString("note_id");

        mNoteSingleTitle = (TextView) findViewById(R.id.singleNoteTitle);
        mNoteSingleDesc = (TextView) findViewById(R.id.singleNoteDesc);

        mNoteRemoveBtn = (Button) findViewById(R.id.singleNoteRemoveBtn);

        mDatabase.child(note_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String post_title = (String) dataSnapshot.child("note_title").getValue();
                String post_desc = (String) dataSnapshot.child("note_desc").getValue();

                mNoteSingleTitle.setText(post_title);
                mNoteSingleDesc.setText(post_desc);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mNoteRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child(note_key).removeValue();

                Intent mainIntent = new Intent(single_note.this, Notes.class);
                startActivity(mainIntent);

            }
        });

    }
}
